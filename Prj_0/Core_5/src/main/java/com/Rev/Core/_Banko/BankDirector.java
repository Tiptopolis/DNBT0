package com.Rev.Core._Banko;

import static com.Rev.Core.AppUtils.*;

import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import com.Rev.Core._Banko.DBMS._Account;
import com.Rev.Core._Banko.DBMS._User;
import com.Rev.Core._Banko.DBMS.iDataCRUD;
import com.Rev.Core._PRIM.aList;
import com.Rev.Core._PRIM.aMultiMap;
import com.Rev.Core._PRIM.aSet;

public class BankDirector {

	public static Connection DB_Link; // find/create

	public BankDirector() {

		// get or create
		// this.createNewDatabase("RevDB.db");
		DB_Link = getOrCreate();
	}

	public static Connection getOrCreate() {
		Connection connection = null;
		// get

		// connection = connectSqliteDB("RevDB.db");
		connection = connectMariaDB();

		// fill
		// get&drop all tables, then refill to clear out any
		if (isEmpty(connection)) {
			try {
				if (!tableExists(connection, "ACCOUNTS"))
					_CreateAccountsTable(connection);

			} catch (SQLException e) {
				e.printStackTrace();
			}

			try {
				if (!tableExists(connection, "USERS"))
					_CreateUsersTable(connection);

			} catch (SQLException e) {
				e.printStackTrace();
			}

			try {
				if (!tableExists(connection, "ACCOUNTS_USERS"))
					_CreateAccounts_UsersTable(connection);

			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return connection;

	}

	private static Connection connectSqliteDB(String fileName) {
		// Local DB, copy data from Maria, werx but not implemented atm
		String url = "jdbc:sqlite:C:\\Users\\Public\\Rev\\" + fileName; // <storage path, folder must already exist lol

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
		// Server DB
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
				log += "  " + getColumnNames(name) + "\n";
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return log;
	}

	private void __DATA_MGMT___() {

	}

	private static boolean isEmpty(Connection connection) {
		try {
			Log("(ConnectionClosed?)" + connection.isClosed());

			DatabaseMetaData meta = connection.getMetaData();
			ResultSet resultSet;
			resultSet = meta.getTables(null, null, null, new String[] { "TABLE" });
			Log(">> " + resultSet + " : " + resultSet.next());
			return !resultSet.next();

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

	/////////
	//
	public static boolean tableExists(String tableName) throws SQLException {
		DatabaseMetaData meta = DB_Link.getMetaData();
		ResultSet resultSet = meta.getTables(null, null, tableName, new String[] { "TABLE" });

		return resultSet.next();
	}

	public static ResultSet getTable(String tableName) {

		try {
			Statement s = DB_Link.createStatement();
			ResultSet result;
			result = s.executeQuery("SELECT * FROM " + tableName);
			return result;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;

	}

	public static aList<String> getColumnNames(String ofTable) {
		ResultSet rs = getTable(ofTable);
		aList<String> columns = new aList<String>();
		try {

			ResultSetMetaData rsMetaData = rs.getMetaData();
			int count = rsMetaData.getColumnCount();
			for (int i = 1; i <= count; i++) {
				String s = rsMetaData.getColumnName(i);
				columns.append(s);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return columns;
	}

	////////
	private void _DEBUG_TEST_() {
		// add table names to jdbc.properties? cache as TableManifest?

		// while (rs.next()) {
		// int id = rs.getInt("users_id");
		// String name = rs.getString("first_name") + " " + rs.getString("last_name");

		// Log(id + " " + name);
		// }
	}

	private static void _CreateAccountsTable(Connection connection) {
		Log("_Filling[Accounts]");
		try {
			if (tableExists(connection, "ACCOUNTS")) {
				String sql = "DROP TABLE IF EXISTS accounts;";
				connection.createStatement().executeUpdate(sql);
			}
			if (!tableExists(connection, "ACCOUNTS")) {

				// String sql = "CREATE TABLE accounts (type VARCHAR(255))";
				String sql = "CREATE TABLE accounts (" 
						+ "account_ID INT NOT NULL AUTO_INCREMENT," 
						+ "type VARCHAR(16),"
						+ "balance DECIMAL (10, 2)," 
						+ "owner_ID INT NOT NULL,"
						+ "account_num INT NOT NULL,"
						+ "CONSTRAINT accounts_pk PRIMARY KEY (account_ID)" + ")";

				connection.createStatement().executeUpdate(sql);
				Log(".creating ACCOUNTS Table");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	private static void _CreateUsersTable(Connection connection) {
		Log("_Filling[Users]");
		try {
			if (tableExists(connection, "USERS")) {
				String sql = "DROP TABLE IF EXISTS users;";
				connection.createStatement().executeUpdate(sql);
			}
			if (!tableExists(connection, "USERS")) {

				// String sql = "CREATE TABLE users (name VARCHAR(255))";
				// String sql = "CREATE TABLE users (acount_ID INT NOT NULL AUTO_INCREMENT,
				// PRIMARY KEY ( acount_ID ))";

				String sql = "CREATE TABLE users (" 
						+ "user_ID INT NOT NULL AUTO_INCREMENT," 
						+ "first_name VARCHAR(32),"
						+ "last_name VARCHAR(32)," 
						+ "email VARCHAR(64)," 
						+ "password VARCHAR(32),"
						+ "routing_num INT NOT NULL,"
						+ "CONSTRAINT users_pk PRIMARY KEY (user_ID)" 
						+ ")";

				connection.createStatement().executeUpdate(sql);

				Log(".creating USERS Table");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static void _CreateAccounts_UsersTable(Connection connection) {
		Log("_Filling[Accounts_Customers]");
		try {
			if (tableExists(connection, "ACCOUNTS_USERS")) {
				String sql = "DROP TABLE IF EXISTS accounts_users;";
				connection.createStatement().executeUpdate(sql);
			}
			if (!tableExists(connection, "ACCOUNTS_USERS")) {

				String sql = "CREATE TABLE accounts_users (" 
						+ "junction_ID INT AUTO_INCREMENT,"
						+ "account_ID 	INT NOT NULL," 
						+ "user_ID INT NOT NULL," 
						+ "INDEX (account_ID),"
						+ "INDEX (user_ID)," 
						+ "CONSTRAINT accounts_users_pk PRIMARY KEY (junction_ID),"
						+ "CONSTRAINT accounts_fk FOREIGN KEY (account_id) REFERENCES accounts (account_id),"
						+ "CONSTRAINT users_fk FOREIGN KEY (user_id) REFERENCES users (user_id)" 
						+ ")";

				connection.createStatement().executeUpdate(sql);
				Log(".creating ACCOUNTS_CUSTOMERS Table");

				// sql = "ALTER TABLE accounts_users ADD CONSTRAINT fk_accounts FOREIGN KEY
				// (account_id) REFERENCES accounts (account_id)";

				// connection.createStatement().executeUpdate(sql);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}
	
	public static class AccountManager implements iDataCRUD<_Account>{

		@Override
		public void create(_Account entry) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public _Account read(int indx) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public void update(_Account entry) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void delete(int index) {
			// TODO Auto-generated method stub
			
		}
		
	}
	
	public static class UserManager implements iDataCRUD<_User>
	{

		@Override
		public void create(_User entry) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public _User read(int indx) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public void update(_User entry) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void delete(int index) {
			// TODO Auto-generated method stub
			
		}
		
	}

}
