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
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Arrays;
import java.util.List;

import equinox.serverUtilities.Permission;

/**
 * Class for administrator tool to set user permissions.
 *
 * @author Murat Artim
 * @date 22 Jan 2018
 * @time 15:43:56
 */
public class SetPermissions {

	public static void main(String[] args) throws Exception {

		// inputs
		setPermissions("AURORA", Arrays.asList(Permission.values()), true);
		// setPermissions("ts1359", Arrays.asList(Permission.values()), false);
	}

	private static void setPermissions(String alias, List<Permission> permissions, boolean truncatePermissionsTable) throws Exception {

		// get connection to SQL Server production database server
		try (Connection connection = Utils.getConnectionToSQLServerDB()) {

			try {

				// create statement
				try (Statement statement = connection.createStatement()) {

					// truncate permissions table
					if (truncatePermissionsTable) {
						String sql = "truncate table permissions";
						statement.executeUpdate(sql);
					}

					// disable auto-commit
					connection.setAutoCommit(false);

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

					// check if administrator
					boolean isAdmin = false;
					sql = "select id from admins where user_id = " + userID;
					try (ResultSet resultSet = statement.executeQuery(sql)) {
						isAdmin = resultSet.next();
					}

					// insert permissions
					sql = "insert into permissions(user_id, name, is_admin) values(" + userID + ", ?, ?)";
					try (PreparedStatement statement2 = connection.prepareStatement(sql)) {
						for (Permission p : permissions) {
							if (p.isAdminPermission()) {
								if (isAdmin) {
									statement2.setString(1, p.toString());
									statement2.setBoolean(2, p.isAdminPermission());
									statement2.executeUpdate();
								}
								else {
									System.out.println("Cannot add permission '" + p.toString() + "' due to insufficient user privileges.");
								}
							}
							else {
								statement2.setString(1, p.toString());
								statement2.setBoolean(2, p.isAdminPermission());
								statement2.executeUpdate();
							}
						}
					}
				}

				// commit updates
				connection.commit();

				// set back auto commit
				connection.setAutoCommit(true);

				// log info
				System.out.println("Permissions successfully set.");
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
