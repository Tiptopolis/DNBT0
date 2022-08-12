package com.Rev.Core._Banko;

import static com.Rev.Core.AppUtils.*;

import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

import com.Rev.Core._Banko.MGMT._Account;
import com.Rev.Core._Banko.MGMT._UserProfile;
import com.Rev.Core._PRIM.aList;
import com.Rev.Core._PRIM.aSet;

public class BankDirector {

	public static Connection DB_Link; // find/create

	private aSet<_Account> AccountCache;
	private aList<_UserProfile> UserCache;

	public BankDirector() {

		// get or create
		// this.createNewDatabase("RevDB.db");
		DB_Link = getOrCreate();

	}

	public void update(float deltaTime) {

	}

	public static Connection getOrCreate() {
		Connection connection = null;
		// get

		// connection = connectSqliteDB("RevDB.db");
		connection = connectMariaDB();

		// fill
		if (isEmpty(connection)) {
			try {
				if (!tableExists(connection, "ACCOUNTS"))
					_FillDebugAccounts(connection);

			} catch (SQLException e) {
				e.printStackTrace();
			}

			try {
				if (!tableExists(connection, "USERS"))
					_FillDebugUsers(connection);

			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return connection;

	}

	private static Connection connectSqliteDB(String fileName) {

		String url = "jdbc:sqlite:C:\\Users\\Public\\Rev\\" + fileName; // <storage path

		try {
			// moved from predicate of try(){}, leaving it there auto-closed connection lol
			Connection conn = DriverManager.getConnection(url);
			if (conn == null) {
				DB_Link = conn;
				DatabaseMetaData meta = conn.getMetaData();
				Log("The driver name is " + meta.getDriverName());
				Log("A new database has been created.");
				// build schema
				return conn;
			} else {
				DB_Link = conn;
				DatabaseMetaData meta = conn.getMetaData();
				Log("got Database");
				Log("The driver name is " + meta.getDriverName());
				return conn;
			}

		} catch (SQLException e) {
			Log(e.getMessage());
			return null;
		}

		// return null;
	}

	private static Connection connectMariaDB() {
		Connection connection = null;
		try {
			Properties props = new Properties();
			FileReader fr = new FileReader("src/main/resources/jdbc.properties");			
			props.load(fr);
			Log(props);			

			String connectionString = "jdbc:mariadb://" + props.getProperty("hostname") + ":"
					+ props.getProperty("port") + "/" + props.getProperty("dbname") + "?user="
					+ props.getProperty("username") + "&password=" + props.getProperty("password");

			// Class.forName("org.mariadb.jdbc.Driver");

			connection = DriverManager.getConnection(connectionString);

		} catch (IOException | SQLException e) {
			e.printStackTrace();
		}
		return connection;
	}

	private static boolean isEmpty(Connection connection) {
		try {
			Log("(ConnectionClosed?)" + connection.isClosed());

			DatabaseMetaData meta = connection.getMetaData();
			ResultSet resultSet;
			resultSet = meta.getTables(null, null, null, new String[] { "TABLE" });
			Log(">> " + resultSet + " : " + resultSet.next());
			return !resultSet.next();
			// while (resultSet.next()) {
			// String name = resultSet.getString("TABLE_NAME");
			// String schema = resultSet.getString("TABLE_SCHEM");
			// Log(name + " on schema " + schema);
			// }

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return false;
	}

	private static boolean tableExists(Connection connection, String tableName) throws SQLException {
		DatabaseMetaData meta = connection.getMetaData();
		ResultSet resultSet = meta.getTables(null, null, tableName, new String[] { "TABLE" });

		return resultSet.next();
	}

	public String toLog() {
		String log = "";
		try {
			DatabaseMetaData meta = DB_Link.getMetaData();
			ResultSet resultSet;
			resultSet = meta.getTables(null, null, null, new String[] { "TABLE" });
			// log += (">> " + resultSet + " : " + resultSet.next());
			while (resultSet.next()) {
				String name = resultSet.getString("TABLE_NAME");
				String schema = resultSet.getString("TABLE_SCHEM");
				log += ("[" + name.toUpperCase() + "] on schema (" + schema + ")\n");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return log;
	}

	////////
	private void _DEBUG_TEST_() {

	}

	private static void _FillDebugAccounts(Connection connection) {
		Log("_FillingAccounts");
		try {
			if(tableExists(connection, "ACCOUNTS"))
			{
				String sql = "DROP TABLE IF EXISTS accounts;";
				connection.createStatement().executeUpdate(sql);
			}
			if (!tableExists(connection, "ACCOUNTS")) {

				//String sql = "CREATE TABLE accounts (type VARCHAR(255))";
				//ID INT PRIMARY KEY AUTO_INCREMENT,
				
				String sql = "CREATE TABLE accounts (acount_ID INT NOT NULL AUTO_INCREMENT, PRIMARY KEY ( acount_ID ))";
				connection.createStatement().executeUpdate(sql);
				Log("Creating ACCOUNTS table");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private static void _FillDebugUsers(Connection connection) {
		Log("_FillingUsers");
		try {
			if (!tableExists(connection, "USERS")) {

				String sql = "CREATE TABLE users (name VARCHAR(255))";

				connection.createStatement().executeUpdate(sql);
				Log("Creating USERS table");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
