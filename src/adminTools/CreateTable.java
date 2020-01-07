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
package adminTools;

import java.sql.Connection;
import java.sql.Statement;

/**
 * Class for creating new tables in central database.
 *
 * @author Murat Artim
 * @date 9 Aug 2018
 * @time 12:12:20
 */
public class CreateTable {

	public static void main(String[] args) throws Exception {

		// get connection to SQL Server production database server
		try (Connection connection = Utils.getConnectionToSQLServerDB()) {

			try {

				// disable auto-commit
				connection.setAutoCommit(false);

				// create statement
				try (Statement statement = connection.createStatement()) {

					// drop table
					String sql = "DROP TABLE PP_DESC";
					statement.executeUpdate(sql);

					// create table
					sql = "CREATE TABLE PP_DESC(ID BIGINT IDENTITY(1, 1), AC_PROGRAM VARCHAR(100) NOT NULL, AC VARCHAR(100) NOT NULL, WEIGHT_VARIANT VARCHAR(100), \n" + "MOD VARCHAR(100), AC_SECTION VARCHAR(100) NOT NULL, PILOT_POINT_NAME VARCHAR(100) NOT NULL, DESCRIPTION VARCHAR(500), \n"
							+ "FRAME_RIB_POSITION VARCHAR(100), STRINGER_POSITION VARCHAR(100), AC_SIDE VARCHAR(100), IMAGE_URL VARCHAR(500), DRAWING_REF VARCHAR(100), \n" + "PRIMARY KEY(ID))";
					statement.executeUpdate(sql);

					// create indices
					sql = "CREATE INDEX SEARCH_PP_AC_PROGRAM ON PP_DESC(AC_PROGRAM)";
					statement.executeUpdate(sql);
					sql = "CREATE INDEX SEARCH_PP_AC ON PP_DESC(AC)";
					statement.executeUpdate(sql);
					sql = "CREATE INDEX SEARCH_PP_WEIGHT_VARIANT ON PP_DESC(WEIGHT_VARIANT)";
					statement.executeUpdate(sql);
					sql = "CREATE INDEX SEARCH_PP_AC_SECTION ON PP_DESC(AC_SECTION)";
					statement.executeUpdate(sql);
					sql = "CREATE INDEX SEARCH_PP_PILOT_POINT_NAME ON PP_DESC(PILOT_POINT_NAME)";
					statement.executeUpdate(sql);
				}

				// commit updates
				connection.commit();

				// set back auto commit
				connection.setAutoCommit(true);

				// log info
				System.out.println("Table(s) successfully created.");
			}

			// exception occurred
			catch (Exception e) {

				// roll back updates
				if (connection != null) {
					connection.rollback();
					connection.setAutoCommit(true);
				}

				// throw exception
				throw e;
			}
		}
	}

}
