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

import java.io.Serializable;
import java.util.HashMap;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

/**
 * Class for shared file info.
 *
 * @author Murat Artim
 * @date 14 Feb 2018
 * @time 12:22:54
 */
public class SharedFileInfo implements Serializable {

	/** Serial id. */
	private static final long serialVersionUID = 1L;

	/** File type. */
	public static final int VIEW = 0, FILE = 1, WORKSPACE = 2, SPECTRUM = 3, INSTRUCTION_SET = 4;

	/**
	 * Enumeration for shared file info type.
	 *
	 * @author Murat Artim
	 * @date 14 Feb 2018
	 * @time 12:15:07
	 */
	public enum SharedFileInfoType {

		/** Exchange data column. */
		OWNER("owner"), FILE_TYPE("file_type"), FILE_NAME("file_name"), DATA_SIZE("data_size"), DATA_URL("data_url");

		/** Database column name. */
		private final String columnName_;

		/**
		 * Creates shared file info type constant.
		 *
		 * @param columnName
		 *            Database column name.
		 */
		SharedFileInfoType(String columnName) {
			columnName_ = columnName;
		}

		/**
		 * Returns the database column name.
		 *
		 * @return Database column name.
		 */
		public String getColumnName() {
			return columnName_;
		}
	}

	/** Map containing the info. */
	private final HashMap<SharedFileInfoType, Object> info_ = new HashMap<>();

	/**
	 * No argument constructor for serialization.
	 */
	public SharedFileInfo() {
	}

	/**
	 * Returns the demanded help video info.
	 *
	 * @param type
	 *            Info type.
	 * @return The demanded help video info.
	 */
	public Object getInfo(SharedFileInfoType type) {
		return info_.get(type);
	}

	/**
	 * Sets help video info.
	 *
	 * @param type
	 *            Info type.
	 * @param info
	 *            Info to set.
	 */
	public void setInfo(SharedFileInfoType type, Object info) {
		info_.put(type, info);
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder(17, 31).append(info_).toHashCode();
	}

	@Override
	public boolean equals(Object o) {
		if (!(o instanceof SharedFileInfo))
			return false;
		if (o == this)
			return true;
		SharedFileInfo info = (SharedFileInfo) o;
		return new EqualsBuilder().append(info_, info.info_).isEquals();
	}
}
