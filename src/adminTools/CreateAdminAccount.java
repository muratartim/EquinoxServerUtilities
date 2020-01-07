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
import java.sql.ResultSet;
import java.sql.Statement;

import encoder.Base64Encoder;

/**
 * Class for administrator tool to upload administrator account in the database.
 *
 * @author Murat Artim
 * @date 22 Jan 2018
 * @time 14:47:15
 */
public class CreateAdminAccount {

	public static void main(String[] args) throws Exception {

		// get inputs
		String alias = "AURORA";
		String password = "17891917";
		System.out.println("Creating administrator for user alias: '" + alias + "', password: '" + password + "'...");

		// get connection to SQL Server production database server
		try (Connection connection = Utils.getConnectionToSQLServerDB()) {

			try {

				// disable auto-commit
				connection.setAutoCommit(false);

				// create statement
				try (Statement statement = connection.createStatement()) {

					// get user ID
					long userID = -1;
					String sql = "select id from users where alias = '" + alias + "'";
					try (ResultSet resultSet = statement.executeQuery(sql)) {
						if (resultSet.next()) {
							userID = resultSet.getLong("id");
						}
					}

					// no user found
					if (userID == -1)
						throw new Exception("Cannot find user with given alias '" + alias + "'. Aborting operation.");

					// insert administrator
					sql = "insert into admins(user_id, password) values(" + userID + ", '" + Base64Encoder.encodeString(password) + "')";
					statement.executeUpdate(sql);
				}

				// commit updates
				connection.commit();

				// set back auto commit
				connection.setAutoCommit(true);

				// log info
				System.out.println("Administrator successfully created.");
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
