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

/**
 * Utility class for supplying server side methods and constants.
 *
 * @author Murat Artim
 * @date 26 Jan 2018
 * @time 10:31:12
 */
public class ServerUtility {

	/** Operating system name and architecture. */
	public static final String MACOS = "macos", WINDOWS = "windows", LINUX = "linux", X86 = "x86", X64 = "x64";

	/**
	 * Returns the operating system type.
	 *
	 * @return The operating system type.
	 * @throws Exception
	 *             If exception occurs during process.
	 */
	public static String getOSType() throws Exception {

		// get OS name
		String osName = System.getProperty("os.name");

		// Mac OS X
		if (osName.contains("Mac OS X"))
			return MACOS;

		// Windows
		else if (osName.contains("Windows"))
			return WINDOWS;

		// Linux
		else if (osName.contains("Linux"))
			return LINUX;

		// unrecognized OS
		return null;
	}

	/**
	 * Returns the operating system architecture.
	 *
	 * @return The operating system architecture.
	 * @throws Exception
	 *             If exception occurs during process.
	 */
	public static String getOSArch() throws Exception {

		// get architecture
		String osArch = System.getProperty("os.arch");

		// 64 bit
		if (osArch.contains("64"))
			return X64;

		// 32 bit
		return X86;
	}
}
