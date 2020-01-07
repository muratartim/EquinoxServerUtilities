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
 * Interface for big message. Big messages are split into partial messages before being sent, and combined together after being received.
 *
 * @version 1.0
 * @author Murat Artim
 * @time 11:54:48 PM
 * @date Jul 16, 2011
 *
 */
public interface BigMessage extends NetworkMessage {

	/**
	 * Returns true if this message is really a big message.
	 *
	 * @return True if this message is really a big message.
	 */
	boolean isReallyBig();
}
