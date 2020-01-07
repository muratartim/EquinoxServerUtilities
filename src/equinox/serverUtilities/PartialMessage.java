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
 * Class for partial network message. Partial messages are constructed by splitting big messages into pieces.
 *
 * @version 1.0
 * @author Murat Artim
 * @time 1:06:16 AM
 * @date Jul 18, 2011
 *
 */
public final class PartialMessage implements NetworkMessage {

	/** Serial ID. */
	private static final long serialVersionUID = 1L;

	/** The data array of the partial message. */
	private byte[] data_;

	/** The ID, number of parts and index of this partial message. */
	private int id_, numParts_, index_;

	/** Fully qualified class name of the ancestor. */
	private String className_;

	/**
	 * No argument constructor for serialization.
	 */
	public PartialMessage() {
	}

	/**
	 * Creates partial message.
	 *
	 * @param data
	 *            The data of partial message.
	 * @param id
	 *            The ID of partial message. This is the hash-code of it's ancestor.
	 * @param numParts
	 *            Number of parts of the ancestor.
	 * @param index
	 *            The index of this partial message.
	 * @param className
	 *            Fully qualified class name of the ancestor.
	 */
	public PartialMessage(byte[] data, int id, int numParts, int index, String className) {
		data_ = data;
		id_ = id;
		numParts_ = numParts;
		index_ = index;
		className_ = className;
	}

	/**
	 * Returns the data of the partial message.
	 *
	 * @return Array containing the data of the partial message.
	 */
	public byte[] getData() {
		return data_;
	}

	/**
	 * Returns the ID of the partial message.
	 *
	 * @return The ID of the partial message.
	 */
	public int getID() {
		return id_;
	}

	/**
	 * Returns the number of parts of the ancestor of this partial message.
	 *
	 * @return The number of parts of the ancestor of this partial message.
	 */
	public int getNumParts() {
		return numParts_;
	}

	/**
	 * Returns the index of the partial message.
	 *
	 * @return The index of the partial message.
	 */
	public int getIndex() {
		return index_;
	}

	/**
	 * Returns the class of the ancestor.
	 *
	 * @return The class of the ancestor.
	 * @throws ClassNotFoundException
	 *             If the class is not found.
	 */
	public Class<?> getMessageClass() throws ClassNotFoundException {
		return Class.forName(className_);
	}
}
