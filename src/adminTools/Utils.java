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
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Utility class for supplying database connection.
 * 
 * @author Murat Artim
 * @date 9 Aug 2018
 * @time 12:13:07
 */
public class Utils {

	/**
	 * Builds and returns connection to <b>AF-Twin MS SQL Server production database</b>. Note that, this method shall be called only once for obtaining connection. The connection is not closed, therefore shall be closed after used.
	 *
	 * @return Newly built connection to AF-Twin MS SQL Server production database.
	 * @throws ClassNotFoundException
	 *             If Derby client driver cannot be loaded.
	 * @throws SQLException
	 *             If database connection cannot be established.
	 */
	public static Connection getConnectionToSQLServerDB() throws ClassNotFoundException, SQLException {

		// set connection properties
		String hostName = "localhost";
		String port = "1433";
		String databaseName = "AFTwin";
		String username = "sa";
		String password = "Martim_17891917";

		// setup connection URL
		String connectionUrl = "jdbc:sqlserver://" + hostName + ":" + port + ";" + "databaseName=" + databaseName + ";user=" + username + ";password=" + password;

		// load Microsoft SQL server driver
		Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");

		// create and return connection
		return DriverManager.getConnection(connectionUrl);
	}
}