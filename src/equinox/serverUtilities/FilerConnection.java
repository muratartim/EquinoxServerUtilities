/*
 * Copyright 2018 Murat Artim (muratartim@gmail.com).
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package equinox.serverUtilities;

import java.io.Closeable;
import java.io.IOException;
import java.util.Vector;
import java.util.logging.Logger;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.ChannelSftp.LsEntry;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;

/**
 * Utility class for filer connection.
 *
 * @author Murat Artim
 * @date 02.11.2017
 * @time 13:10:29
 *
 */
public class FilerConnection implements Closeable {

	/** Filer directory name. */
	// @formatter:off
	public static final String EXCHANGE = "exchange",
			INPUT_SAMPLES = "inputSamples",
			MULT_TABLES = "multTables",
			PILOT_POINTS = "pilotPoints",
			PP_DAMAGE_ANGLE_PLOTS = "pilotPoints/damageAnglePlots",
			PP_DATA = "pilotPoints/data",
			PP_LOADCASE_DAMAGE_CONTRIBUTION_PLOTS = "pilotPoints/loadcaseDamageContributionPlots",
			PP_IMAGES = "pilotPoints/images",
			PP_LEVEL_CROSSING_PLOTS = "pilotPoints/levelCrossingPlots",
			PP_MISSION_PROFILE_PLOTS = "pilotPoints/missionProfilePlots",
			PP_FLIGHT_OCCURRENCE_PLOTS = "pilotPoints/flightOccurrencePlots",
			PP_NUMBER_OF_PEAKS_PLOTS = "pilotPoints/numberOfPeaksPlots",
			PP_RAINFLOW_HISTOGRAM_PLOTS = "pilotPoints/rainflowHistogramPlots",
			PP_TYPICAL_FLIGHT_DAMAGE_CONTRIBUTION_PLOTS = "pilotPoints/typicalFlightDamageContributionPlots",
			PP_TYPICAL_FLIGHT_WITH_HIGHEST_OCCURRENCE_PLOTS = "pilotPoints/typicalFlightWithHighestOccurrencePlots",
			PP_TYPICAL_FLIGHT_WITH_HIGHEST_STRESS_PLOTS = "pilotPoints/typicalFlightWithHighestStressPlots",
			PP_LONGEST_TYPICAL_FLIGHT_PLOTS = "pilotPoints/longestTypicalFlightPlots",
			PP_ATTRIBUTES = "pilotPoints/attributes",
			PLUGINS = "plugins",
			SPECTRA = "spectra",
			UPDATE = "update",
			CONTAINER = "container",
			USERS = "users",
			VIDEOS = "videos",
			SETTINGS = "settings",
			DATA_SERVER_CONN_FILE = "dataServer.con",
			EXCHANGE_SERVER_CONN_FILE = "exchangeServer.con",
			ANALYSIS_SERVER_CONN_FILE = "analysisServer.con";
	// @formatter:on

	/** Session. */
	private final Session session;

	/** Channel. */
	private final Channel channel;

	/** SFTP channel. */
	private final ChannelSftp sftpChannel;

	/** Logger. */
	private final Logger logger;

	/** The path string. */
	private final String rootPath;

	/**
	 * Creates filer connection object.
	 *
	 * @param session
	 *            Session.
	 * @param channel
	 *            Channel.
	 * @param sftpChannel
	 *            SFTP channel.
	 * @param logger
	 *            Logger.
	 * @param rootPath
	 *            Filer root path.
	 */
	public FilerConnection(Session session, Channel channel, ChannelSftp sftpChannel, Logger logger, String rootPath) {
		this.session = session;
		this.channel = channel;
		this.sftpChannel = sftpChannel;
		this.logger = logger;
		this.rootPath = rootPath;
		logger.info("Filer connection created.");
	}

	/**
	 * Returns session.
	 *
	 * @return Session.
	 */
	public Session getSession() {
		return session;
	}

	/**
	 * Returns channel.
	 *
	 * @return Channel.
	 */
	public Channel getChannel() {
		return channel;
	}

	/**
	 * Returns SFTP channel.
	 *
	 * @return SFTP channel.
	 */
	public ChannelSftp getSftpChannel() {
		return sftpChannel;
	}

	/**
	 * Returns the root path string.
	 *
	 * @return The root path string.
	 */
	public String getRootPath() {
		return rootPath;
	}

	/**
	 * Returns the path to requested directory.
	 *
	 * @param directory
	 *            Directory name.
	 * @return The path to requested directory.
	 */
	public String getDirectoryPath(String directory) {
		return rootPath.concat(directory);
	}

	/**
	 * Disconnects all filer connection objects.
	 */
	public void disconnect() {
		sftpChannel.disconnect();
		logger.info("SFTP channel disconnected.");
		channel.disconnect();
		logger.info("Channel disconnected.");
		session.disconnect();
		logger.info("Session disconnected.");
	}

	/**
	 * Returns true if file or directory at given path exists.
	 *
	 * @param path
	 *            Path to file or directory to check.
	 * @return True if file or directory at given path exists, otherwise false.
	 * @throws SftpException
	 *             If exception occurs during checking file/directory existence.
	 */
	public boolean fileExists(String path) throws SftpException {

		// null path
		if (path == null)
			return false;

		// check if file/directory exists
		try {
			sftpChannel.stat(path);
			return true;
		}

		// exception occurred
		catch (SftpException e) {

			// no such file
			if (e.id == ChannelSftp.SSH_FX_NO_SUCH_FILE)
				return false;

			// some other exception occurred (propagate)
			throw e;
		}
	}

	/**
	 * Deletes all files under the given parent directory and with given extension.
	 *
	 * @param directory
	 *            Parent directory.
	 * @param extension
	 *            File extension.Null can be given to delete all files.
	 * @throws SftpException
	 *             If exception occurs during deleting files.
	 */
	public void deleteFilesInDirectory(String directory, String extension) throws SftpException {

		// list all files under directory
		Vector<LsEntry> entries = sftpChannel.ls(directory);

		// loop over entries
		for (LsEntry entry : entries) {

			// get file name
			String fileName = entry.getFilename();

			// hidden or temporary file
			if (fileName.startsWith(".")) {
				continue;
			}

			// set file path
			String filePath = directory + "/" + fileName;

			// file doesn't exist
			if (!fileExists(filePath)) {
				continue;
			}

			// directory
			if (sftpChannel.stat(filePath).isDir()) {
				deleteFilesInDirectory(filePath, extension);
				sftpChannel.rmdir(filePath);
				continue;
			}

			// extension mismatch
			if (extension != null && !fileName.toLowerCase().endsWith(extension.toLowerCase())) {
				continue;
			}

			// delete file
			sftpChannel.rm(filePath);
			logger.info(fileName + " deleted.");
		}
	}

	/**
	 * Creates directories.
	 *
	 * @param parentPath
	 *            Path to parent directory.
	 * @param directoryNames
	 *            Names of directories from outer most to deepest.
	 * @return The path to deepest directory.
	 * @throws SftpException
	 *             If exception occurs during process.
	 */
	public String createDirectories(String parentPath, String... directoryNames) throws SftpException {

		// create parent directory if it doesn't exist
		if (!fileExists(parentPath)) {
			sftpChannel.mkdir(parentPath);
		}

		// create directories
		String path = parentPath;
		for (String directoryName : directoryNames) {
			path += "/" + directoryName;
			if (!fileExists(path)) {
				sftpChannel.mkdir(path);
			}
		}

		// return path
		return path;
	}

	@Override
	public void close() throws IOException {
		disconnect();
	}
}
