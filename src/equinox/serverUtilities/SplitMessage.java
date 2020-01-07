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

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * Utility class for splitting and combining big network messages.
 *
 * @version 1.0
 * @author Murat Artim
 * @time 11:58:28 PM
 * @date Jul 16, 2011
 *
 */
public final class SplitMessage {

	/** Part size in bytes. */
	private static final int PART_SIZE = 8000;

	/**
	 * Splits given big message into partial messages.
	 *
	 * @param message
	 *            Big network message to split.
	 * @return An array of partial messages, or null if there is no need to split message.
	 * @throws Exception
	 *             If exception occurs during process.
	 */
	public static final PartialMessage[] splitMessage(BigMessage message) throws Exception {

		// create object buffer and serialize object with kryo
		byte[] bytes = getBytes(message);

		// compute number of parts
		int numParts = bytes.length / PART_SIZE;

		// there is no need to split
		if (numParts == 0)
			return null;

		// create partial messages
		int id = message.hashCode();
		String className = message.getClass().getName();
		int remain = bytes.length % PART_SIZE;
		PartialMessage[] parts = new PartialMessage[numParts + (remain == 0 ? 0 : 1)];

		// copy bytes to full arrays
		for (int i = 0; i < numParts; i++) {
			parts[i] = new PartialMessage(new byte[PART_SIZE], id, parts.length, i, className);
			System.arraycopy(bytes, i * PART_SIZE, parts[i].getData(), 0, PART_SIZE);
		}

		// copy remaining bytes
		if (remain > 0) {
			parts[numParts] = new PartialMessage(new byte[remain], id, parts.length, numParts, className);
			System.arraycopy(bytes, numParts * PART_SIZE, parts[numParts].getData(), 0, remain);
		}

		// return parts
		return parts;
	}

	/**
	 * Combines given partial messages into a big network message.
	 *
	 * @param parts
	 *            Array containing the parts of the message.
	 * @return The combined message.
	 * @throws Exception
	 *             If exception occurs during process.
	 */
	public static final BigMessage combineMessages(PartialMessage[] parts) throws Exception {

		// compute size of data
		int size = 0;
		for (PartialMessage part : parts) {
			size += part.getData().length;
		}

		// create byte array
		byte[] data = new byte[size];
		byte[] partData;
		int k = 0;
		for (PartialMessage part : parts) {
			partData = part.getData();
			for (byte element : partData) {
				data[k] = element;
				k++;
			}
		}

		// read data and create message
		return (BigMessage) toObject(data);
	}

	/**
	 * Writes given object into byte array.
	 *
	 * @param obj
	 *            Object to write.
	 * @return The byte array.
	 * @throws Exception
	 *             If exception occurs during process.
	 */
	private static final byte[] getBytes(Object obj) throws Exception {
		try (ByteArrayOutputStream bos = new ByteArrayOutputStream()) {
			try (ObjectOutputStream oos = new ObjectOutputStream(bos)) {
				oos.writeObject(obj);
				oos.flush();
				return bos.toByteArray();
			}
		}
	}

	/**
	 * Reads object from the byte array.
	 *
	 * @param bytes
	 *            Byte array to read the object.
	 * @return The object.
	 * @throws IOException
	 *             If object cannot be read.
	 * @throws ClassNotFoundException
	 *             If object's class doesn't exist in class path.
	 */
	private static final Object toObject(byte[] bytes) throws IOException, ClassNotFoundException {
		try (ByteArrayInputStream bis = new ByteArrayInputStream(bytes)) {
			try (ObjectInputStream ois = new ObjectInputStream(bis)) {
				return ois.readObject();
			}
		}
	}
}
