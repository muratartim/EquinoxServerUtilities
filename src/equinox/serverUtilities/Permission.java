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

import java.util.Arrays;

/**
 * Enumeration for permissions.
 *
 * @author Murat Artim
 * @date 18 Dec 2017
 * @time 15:53:11
 */
public enum Permission {

	/** Permission. */
	// @formatter:off

	// server side checked permissions
	LOGIN_AS_ADMINISTRATOR("Login as administrator", false, true), SEE_CONNECTED_USERS("See connected users", false, true), SEND_CHAT_MESSAGE("Send chat messages", false, true),
	SHARE_FILE("Share files", false, true), CLOSE_BUG_REPORT("Close bug report in AF-Twin database", true, true), DELETE_HELP_VIDEO("Delete help video from AF-Twin database", true, true),
	DELETE_MULTIPLICATION_TABLE("Delete multiplication table from AF-Twin database", true, true),
	UPDATE_MULTIPLICATION_TABLE_INFO("Update multiplication table info in AF-Twin database", true, true), DELETE_PILOT_POINT("Delete pilot point from AF-Twin database", true, true),
	UPDATE_PILOT_POINT_INFO("Update pilot point in AF-Twin database", true, true), DELETE_SPECTRUM("Delete spectrum from AF-Twin database", true, true),
	UPDATE_SPECTRUM_INFO("Update spectrum info in AF-Twin database", true, true), EXECUTE_GENERIC_SQL_STATEMENT("Execute generic SQL statement on AF-Twin database", true, true),
	RESET_EXCHANGE_DATA("Reset file exchange data in AF-Twin database", true, true),
	UPLOAD_EQUINOX_UPDATE("Upload Equinox update to AF-Twin database", true, true), UPLOAD_MATERIALS("Upload material data to AF-Twin database", true, true),
	UPLOAD_DAMAGE_CONTRIBUTIONS("Upload damage contributions to AF-Twin database", true, true), UPLOAD_MULTIPLICATION_TABLES("Upload multiplication tables to AF-Twin database", true, true),
	UPLOAD_PILOT_POINTS("Upload pilot points to AF-Twin database", true, true), UPLOAD_EQUINOX_PLUGIN("Upload Equinox plugin to AF-Twin database", true, true),
	UPLOAD_SAMPLE_INPUTS("Upload sample inputs to AF-Twin database", true, true), UPLOAD_SPECTRA("Upload spectra to AF-Twin database", true, true),
	UPLOAD_HELP_VIDEO("Upload help video to AF-Twin database", true, true), DELETE_EQUINOX_PLUGIN("Delete Equinox plugin from AF-Twin database", true, true),
	CLOSE_WISH("Close user wish in AF-Twin database", true, true), SAVE_PILOT_POINT_IMAGE("Save pilot point image to AF-Twin database", true, true),
	BROADCAST_ANNOUNCEMENT("Broadcast server announcements", true, true), SEARCH_MULTIPLICATION_TABLE("Search loadcase factor file in AF-Twin database", false, true),
	SEARCH_PILOT_POINT("Search pilot point in AF-Twin database", false, true), SEARCH_SPECTRUM("Search spectrum in AF-Twin database", false, true),
	CHECK_FOR_EQUINOX_UPDATES("Check for Equinox updates in AF-Twin database", false, true), CHECK_FOR_MATERIAL_UPDATES("Check for material updates in AF-Twin database", false, true),
	GET_EQUINOX_PLUGIN_INFO("Get Equinox plugin info from AF-Twin database", false, true), INSTALL_EQUINOX_PLUGIN("Install Equinox plugin", false, true),
	DOWNLOAD_HELP_VIDEO("Download help video from AF-Twin database", false, true),
	DOWNLOAD_PILOT_POINT("Download pilot point from AF-Twin database", false, true), DOWNLOAD_SAMPLE_INPUT("Download input sample from AF-Twin database", false, true),
	DOWNLOAD_MULTIPLICATION_TABLE("Download loadcase factor file from AF-Twin database", false, true), DOWNLOAD_SPECTRUM("Download spectrum archive from AF-Twin database", false, true),
	GET_BUG_REPORTS("Get bug reports from AF-Twin database", false, true), GET_HELP_VIDEOS("Get help videos from AF-Twin database", false, true),
	GET_WISHES("Get wishes from AF-Twin database", false, true), PLOT_CONTRIBUTION_STATISTICS("Plot damage contribution statistics from AF-Twin database", false, true),
	PLOT_PILOT_POINT_COUNT("Plot pilot point count from AF-Twin database", false, true), PLOT_SPECTRUM_COUNT("Plot spectrum count from AF-Twin database", false, true),
	PLOT_SPECTRUM_SIZE("Plot spectrum size from AF-Twin database", false, true), SHOW_SERVER_DIAGNOSTICS("Show server diagnostics from AF-Twin database", true, true),
	SUBMIT_BUG_REPORT("Submit bug report to AF-Twin database", false, true), SUBMIT_WISH("Submit wish to AF-Twin database", false, true),
	GET_MATERIALS("Get materials from AF-Twin database", false, true), GET_PILOT_POINT_IMAGES("Get pilot point images from AF-Twin database", false, true),
	ADD_NEW_USER("Add new user to AF-Twin database", true, true), DELETE_USER("Delete user from AF-Twin database", true, true),
	EDIT_USER_PERMISSIONS("Edit user permissions in Af-Twin database", true, true), GET_USER_PERMISSIONS("Get user permissions from AF-Twin database", true, true),
	EQUIVALENT_STRESS_ANALYSIS("Perform equivalent stress analysis", false, true), DAMAGE_CONTRIBUTION_ANALYSIS("Perform damage contribution analysis", false, true),
	DAMAGE_ANGLE_ANALYSIS("Perform damage angle analysis", false, true), SUBMIT_ACCESS_REQUEST("Submit user access request to AF-Twin database", false, true),
	GET_ACCESS_REQUESTS("Get user access requests from AF-Twin database", true, true), CLOSE_ACCESS_REQUEST("Close user access request in AF-Twin database", true, true),
	CLOSE_ADMIN_ACCESS_REQUEST("Close administrator access request in AF-Twin database", true, true),

	// client side checked permissions
	ADD_NEW_SPECTRUM("Add/load new spectrum in Equinox client application", false, false), ADD_NEW_STRESS_SEQUENCE("Add/load new stress sequence in Equinox client application", false, false),
	ADD_NEW_AIRCRAFT_MODEL("Add/load new aircraft model in Equinox client application", false, false),
	RUN_RFORT_EXTENDED_PLUGIN("Run Rfort Extended plugin in Equinox client application", false, false), RUN_ADAPT_DRF_PLUGIN("Run Adapt DRF plugin in Equinox client application", false, false),
	RUN_EXCALIBUR_PLUGIN("Run Excalibur plugin in Equinox client application", false, false), RUN_MYCHECK_PLUGIN("Run MyCheck plugin in Equinox client application", false, false),
	CHECK_FOR_PLUGIN_UPDATES("Check for Equinox plugin updates in AF-Twin database", false, false), ADD_NEW_STF_FILE("Add/load new STF file in Equinox client application", false, false),
	CREATE_DUMMY_STF_FILE("Create dummy STF file in Equinox client application", false, false), EDIT_SPECTRUM_INFO("Edit spectrum info in Equinox client application", false, false),
	ASSIGN_MISSION_PARAMETERS_TO_SPECTRUM("Assign mission parameters to spectrum in Equinox client application", false, false), RENAME_FILE("Rename file in Equinox client application", false, false),
	SAVE_FILE("Save file in Equinox client application", false, false), EXPORT_SPECTRUM("Export spectrum from Equinox client application", false, false),
	EXPORT_LOADCASE_FACTOR_FILE("Export loadcase factor file from Equinox client application", false, false), DELETE_FILE("Delete file in Equinox client application", false, false),
	GENERATE_STRESS_SEQUENCE("Generate stress sequence", false, false), EDIT_PILOT_POINT_INFO("Edit pilot point info in Equinox client application", false, false),
	SET_PILOT_POINT_IMAGE("Set pilot point image in Equinox client application", false, false),
	EXPORT_PILOT_POINT("Export pilot points from Equinox client application", false, false), EXPORT_DAMAGE_CONTRIBUTIONS("Export damage contributions from Equinox client application", false, false),
	EXPORT_AIRCRAFT_MODEL("Export aircraft model from Equinox client application", false, false), PLOT_TYPICAL_FLIGHT("Plot typical flight in Equinox client application", false, false),
	PLOT_MISSION_PROFILE("Plot mission profile in Equinox client application", false, false), PLOT_LEVEL_CROSSINGS("Plot level crossings in Equinox client application", false, false),
	GENERATE_LIFE_FACTORS("Generate life factors in Equinox client application", false, false), REMOVE_PLUGIN("Remove plugin from Equinox client application", false, false),
	PLOT_TYPICAL_FLIGHT_STATISTICS("Plot typical flight statistics in Equinox client application", false, false),
	PLOT_EQUIVALENT_STRESS_COMPARISON("Plot equivalent stress comparison in Equinox client application", false, false),
	PLOT_TYPICAL_FLIGHT_COMPARISON("Plot typical flight comparison in Equinox client application", false, false),
	PLOT_STRESS_SEQUENCE_COMPARISON("Plot stress sequence comparison in Equinox client application", false, false),
	PLOT_LOADCASE_COMPARISON("Plot loadcase comparison in Equinox client application", false, false),
	PLOT_ELEMENT_STRESS_COMPARISON("Plot element stress comparison in Equinox client application", false, false), PLOT_ELEMENT_STRESSES("Plot element stresses in Equinox client application", false, false),
	GENERATE_EQUIVALENT_STRESS_RATIOS("Generate equivalent stress ratios in Equinox client application", false, false);
	// @formatter:on

	/** Permission description. */
	private final String description;

	/** True if this is an administrative permission. */
	private final boolean isAdminPermission, isServerSideChecked;

	/**
	 * Creates permission constant.
	 *
	 * @param description
	 *            Permission description.
	 * @param isAdminPermission
	 *            True if this is an administrator permission.
	 * @param isServerSideChecked
	 *            True if this permission is checked on server side.
	 */
	Permission(String description, boolean isAdminPermission, boolean isServerSideChecked) {
		this.description = description;
		this.isAdminPermission = isAdminPermission;
		this.isServerSideChecked = isServerSideChecked;
	}

	/**
	 * Returns permission description.
	 *
	 * @return Permission description.
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Returns true if this is and administrator permission.
	 *
	 * @return True if this is and administrator permission.
	 */
	public boolean isAdminPermission() {
		return isAdminPermission;
	}

	/**
	 * Returns true if this permission is checked on server side.
	 *
	 * @return True if this permission is checked on server side.
	 */
	public boolean isServerSideChecked() {
		return isServerSideChecked;
	}

	/**
	 * Prints all permissions.
	 */
	public static void print() {
		Arrays.stream(Permission.values()).forEach(p -> {
			System.out.println(p.name().toUpperCase() + ":");
			System.out.println("Description: " + p.description);
			System.out.println("Administrator pemission: " + p.isAdminPermission);
			System.out.println("Checked on server side: " + p.isServerSideChecked);
			System.out.println();
		});
	}
}
