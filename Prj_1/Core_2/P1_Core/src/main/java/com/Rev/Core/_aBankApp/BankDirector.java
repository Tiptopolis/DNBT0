package com.Rev.Core._aBankApp;

import static com.Rev.Core.AppUtils.*;

import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import com.Rev.Core.Console.UI.iDataCRUD;
import com.Rev.Core.Primitive.aList;
import com.Rev.Core.Primitive.aMultiMap;
import com.Rev.Core.Primitive.aSet;
import com.Rev.Core._aBankApp.Data._Account;
import com.Rev.Core._aBankApp.Data._User;

public class BankDirector {

	// The Cental Bureaucracy

	public static Connection DB_Link; // find/create
	public static AccountManager Accounts;
	public static UserManager Users;

	static {
		Accounts = new AccountManager();
		Users = new UserManager();
	}

	public BankDirector() {

		// get or create
		// this.createNewDatabase("RevDB.db");
		DB_Link = getOrCreate();
	}

	// if (DB exists on this connection)-> getIt;~ else -> createIt;
	// if creatIt -> fillDataTables;
	// //need to add in check by table instead of just isEmpty;
	// :~:that means: if(!isEmpty)-> getTableNames,
	// determine which one(s) am missing, run its associated fill method
	// //need to flesh out aDataTable; change it to _DataTable...?
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
				// build schema if there were one lolol
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
			// FileReader fr = new FileReader("src/main/resources/jdbcLOCAL.properties");
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
		// this is a section note lolol
		// they can be useful for organizing complicated stoof
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
	private void _DB_SETUPSTUFF() {
		// currently, only creates tables if whole DB empty
		// to reset, delete all tables and run from scratch
		// quick n dirty

	}

	//
	private static void _CreateAccountsTable(Connection connection) {
		Log("_Filling[Accounts]");
		try {
			if (tableExists(connection, "ACCOUNTS")) {
				String sql = "DROP TABLE IF EXISTS accounts;";
				connection.createStatement().executeUpdate(sql);
			}
			if (!tableExists(connection, "ACCOUNTS")) {

				String sql = "CREATE TABLE accounts (" //
						+ "account_ID INT NOT NULL AUTO_INCREMENT," //
						+ "type VARCHAR(16),"//
						+ "balance DECIMAL (10, 2)," //
						+ "owner_ID INT NOT NULL," //
						+ "account_num INT NOT NULL,"//
						+ "CONSTRAINT accounts_pk PRIMARY KEY (account_ID)" //
						+ ")";//

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

				String sql = "CREATE TABLE users (" + "user_ID INT NOT NULL AUTO_INCREMENT," //
						+ "first_name VARCHAR(32),"//
						+ "last_name VARCHAR(32)," //
						+ "email VARCHAR(64)," //
						+ "password VARCHAR(32),"//
						+ "routing_num INT NOT NULL," //
						+ "CONSTRAINT users_pk PRIMARY KEY (user_ID)," //
						+ "CONSTRAINT UNIQUE(email)"//
						+ ")";//

				connection.createStatement().executeUpdate(sql);

				Log(".creating USERS Table");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// unused atm
	private static void _CreateAccounts_UsersTable(Connection connection) {
		Log("_Filling[Accounts_Customers]");
		try {
			if (tableExists(connection, "ACCOUNTS_USERS")) {
				String sql = "DROP TABLE IF EXISTS accounts_users;";
				connection.createStatement().executeUpdate(sql);
			}
			if (!tableExists(connection, "ACCOUNTS_USERS")) {

				String sql = "CREATE TABLE accounts_users (" //
						+ "junction_ID INT AUTO_INCREMENT,"//
						+ "account_ID 	INT NOT NULL," //
						+ "user_ID INT NOT NULL," //
						+ "INDEX (account_ID),"//
						+ "INDEX (user_ID)," //
						+ "CONSTRAINT accounts_users_pk PRIMARY KEY (junction_ID),"//
						+ "CONSTRAINT accounts_fk FOREIGN KEY (account_id) REFERENCES accounts (account_id),"//
						+ "CONSTRAINT users_fk FOREIGN KEY (user_id) REFERENCES users (user_id)" //
						+ ")";//

				connection.createStatement().executeUpdate(sql);

				Log(".creating ACCOUNTS_CUSTOMERS Table");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	// thing what does CRUD ops on/for/to _Accounts
	public static class AccountManager implements iDataCRUD<_Account> {

		@Override
		public void create(_Account entry) {

			try {
				String sql = "INSERT INTO accounts(type, balance, owner_ID, account_num) VALUES(?,?,?,?)";
				PreparedStatement preparedStatement = DB_Link.prepareStatement(sql);
				preparedStatement.setInt(1, entry.Type());
				preparedStatement.setFloat(2, 0f);
				preparedStatement.setInt(3, entry.Owner());
				preparedStatement.setInt(4, entry.hashCode());

				preparedStatement.executeUpdate();

			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		@Override
		public _Account read(int indx) {
			try {
				String sql = "SELECT * FROM accounts WHERE account_id = ?";
				PreparedStatement preparedStatement = DB_Link.prepareStatement(sql);
				preparedStatement.setInt(1, indx);

				ResultSet rs = preparedStatement.executeQuery();

				int actnum = -1;
				int type = -1;
				float bal = 0f;
				int owner = -1;

				while (rs.next()) {
					actnum = (rs.getInt("account_num"));
					type = (rs.getInt("type"));
					bal = (rs.getFloat("balance"));
					owner = (rs.getInt("owner_ID"));

				}

				return new _Account(owner, type, bal, actnum);
			} catch (SQLException e) {
				e.printStackTrace();
			}

			return null;
		}

		@Override
		public void update(int at, _Account entry) {
			try {
				String sql = "UPDATE accounts SET balance = ? WHERE account_id = ?";

				PreparedStatement preparedStatement = DB_Link.prepareStatement(sql);
				preparedStatement.setObject(1, entry.Balance());
				;
				preparedStatement.setInt(2, at);

				preparedStatement.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
			}

		}

		@Override
		public void delete(int index) {
			try {
				String sql = "DELETE FROM accounts WHERE account_id = ?";
				PreparedStatement preparedStatement = DB_Link.prepareStatement(sql);
				preparedStatement.setInt(1, index);
				preparedStatement.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
			}

		}

	}

	// thing what does CRUD ops on/for/to _Users
	public static class UserManager implements iDataCRUD<_User> {

		@Override
		public void create(_User entry) {
			try {
				String sql = "INSERT INTO users(first_name, last_name, email, password, routing_num) VALUES(?,?,?,?,?)";
				PreparedStatement preparedStatement = DB_Link.prepareStatement(sql);
				preparedStatement.setString(1, entry.FirstName());
				preparedStatement.setString(2, entry.LastName());
				preparedStatement.setString(3, entry.Email());
				preparedStatement.setString(4, entry.Password());
				preparedStatement.setInt(5, entry.hashCode());

				preparedStatement.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		@Override
		public _User read(int indx) {
			try {
				String sql = "SELECT * FROM users WHERE user_id = ?";
				PreparedStatement preparedStatement = DB_Link.prepareStatement(sql);
				preparedStatement.setInt(1, indx);

				ResultSet rs = preparedStatement.executeQuery();

				int u_id = -1;
				String fn = "";
				String ln = "";
				String em = "";
				String pw = "";
				while (rs.next()) {
					u_id = (rs.getInt("user_ID"));
					fn = (rs.getString("first_name"));
					ln = (rs.getString("last_name"));
					em = (rs.getString("email"));
					pw = (rs.getString("password"));
				}

				return new _User(u_id, fn, ln, em, pw);
			} catch (SQLException e) {
				e.printStackTrace();
			}

			return null;
		}

		@Override
		public void update(int at, _User entry) {
			try {
				String sql = "UPDATE users SET users = ? WHERE user_id = ?";
				PreparedStatement preparedStatement = DB_Link.prepareStatement(sql);
				preparedStatement.setInt(at, at);

				preparedStatement.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
			}

		}

		@Override
		public void delete(int index) {
			try {
				String sql = "DELETE FROM users WHERE user_id = ?";
				PreparedStatement preparedStatement = DB_Link.prepareStatement(sql);
				preparedStatement.setInt(1, index);
				preparedStatement.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

	}

}
