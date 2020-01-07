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

import java.io.BufferedReader;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.StringTokenizer;

/**
 * Utility class for inserting sample data to AF-Twin central database.
 *
 * @author Murat Artim
 * @date 29 Dec 2018
 * @time 23:43:41
 */
public class InsertSampleData {

	public static void main(String[] args) throws Exception {

		// // users
		// Path file = Paths.get("/Users/aurora/EclipseWorkspace/Equinox/docs/data/sampleData/users.data");
		// String table = "users";
		// String sql = "insert into users(id, alias, username, organization, email, image_url) values(?,?,?,?,?,?)";

		// // admins
		// Path file = Paths.get("/Users/aurora/EclipseWorkspace/Equinox/docs/data/sampleData/admins.data");
		// String table = "admins";
		// String sql = "insert into admins(id, user_id, password) values(?,?,?)";

		// // permissions
		// Path file = Paths.get("/Users/aurora/EclipseWorkspace/Equinox/docs/data/sampleData/permissions.data");
		// String table = "permissions";
		// String sql = "insert into permissions(id, user_id, name, is_admin) values(?,?,?,?)";

		// // fatigue_materials
		// Path file = Paths.get("/Users/aurora/EclipseWorkspace/Equinox/docs/data/sampleData/fatigue_materials.data");
		// String table = "fatigue_materials";
		// String sql = "insert into fatigue_materials(id, name, specification, library_version, family, orientation, configuration, par_p, par_q, par_m, isami_version) values(?,?,?,?,?,?,?,?,?,?,?)";

		// // linear_materials
		// Path file = Paths.get("/Users/aurora/EclipseWorkspace/Equinox/docs/data/sampleData/linear_materials.data");
		// String table = "linear_materials";
		// String sql = "insert into linear_materials(id, name, specification, library_version, family, orientation, configuration, par_ceff, par_m, par_a, par_b, par_c, par_ftu, par_fty, isami_version) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

		// // preffas_materials
		// Path file = Paths.get("/Users/aurora/EclipseWorkspace/Equinox/docs/data/sampleData/preffas_materials.data");
		// String table = "preffas_materials";
		// String sql = "insert into preffas_materials(id, name, specification, library_version, family, orientation, configuration, par_ceff, par_m, par_a, par_b, par_c, par_ftu, par_fty, isami_version) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

		// // damage_contributions
		// Path file = Paths.get("/Users/aurora/EclipseWorkspace/Equinox/docs/data/sampleData/damage_contributions.data");
		// String table = "damage_contributions";
		// String sql = "insert into damage_contributions(id, spectrum_name, pp_name, ac_program, ac_section, fat_mission) values(?,?,?,?,?,?)";

		// // increment_contributions
		// Path file = Paths.get("/Users/aurora/EclipseWorkspace/Equinox/docs/data/sampleData/increment_contributions.data");
		// String table = "increment_contributions";
		// String sql = "insert into increment_contributions(id, damcont_id, event, contribution) values(?,?,?,?)";

		// steady_contributions
		Path file = Paths.get("/Users/aurora/EclipseWorkspace/Equinox/docs/data/sampleData/steady_contributions.data");
		String table = "steady_contributions";
		String sql = "insert into steady_contributions(id, damcont_id, gag, dp, dt, oneg) values(?,?,?,?,?,?)";

		// get connection to SQL Server production database server
		try (Connection connection = Utils.getConnectionToSQLServerDB()) {

			try {

				// disable auto-commit
				connection.setAutoCommit(false);

				// create statement
				try (Statement statement = connection.createStatement()) {

					// delete all content
					statement.executeUpdate("delete from " + table);

					// re-seed auto increment columns
					statement.executeUpdate("dbcc checkident(" + table + ", reseed, 0)");

					// enable identity insert
					statement.executeUpdate("set identity_insert " + table + " on");
				}

				// insert
				try (PreparedStatement statement = connection.prepareStatement(sql)) {

					// create file reader
					try (BufferedReader reader = Files.newBufferedReader(file, Charset.defaultCharset())) {

						// read file till the end
						String line;
						while ((line = reader.readLine()) != null) {
							System.out.println("Inserting " + line);
							StringTokenizer t = new StringTokenizer(line, "|");
							int i = 1;
							while (t.hasMoreTokens()) {
								String token = t.nextToken();
								if (token.equals("NULL")) {
									token = null;
								}
								statement.setObject(i++, token);
							}
							statement.executeUpdate();
						}
					}
				}

				// commit updates
				connection.commit();

				// set back auto commit
				connection.setAutoCommit(true);

				// log info
				System.out.println("Completed.");
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
