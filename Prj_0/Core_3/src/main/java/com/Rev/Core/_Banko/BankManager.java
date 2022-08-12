package com.Rev.Core._Banko;

import static com.Rev.Core.AppUtils.*;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.Rev.Core._Banko.MGMT._Account;
import com.Rev.Core._Banko.MGMT._UserProfile;
import com.Rev.Core._PRIM.aList;
import com.Rev.Core._PRIM.aSet;

public class BankManager {

	public static Connection DB_Link; // find/create

	private aSet<_Account> AccountCache;
	private aList<_UserProfile> UserCache;

	public BankManager() {

		// get or create
		// this.createNewDatabase("RevDB.db");
		DB_Link = getOrCreate();

	}

	public void update(float deltaTime) {

	}

	public static Connection getOrCreate() {
		Connection connection = null;
		// get

		connection = connectDB("RevDB.db");
		
		// fill
		if (isEmpty(connection)) {
			_fillAccounts(connection);
			_fillUsers(connection);
		}

		return connection;

	}

	private static Connection connectDB(String fileName) {

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

	private static boolean isEmpty(Connection connection) {
		try {
			Log("(ConnectionClosed?)" + connection.isClosed());

			DatabaseMetaData meta = connection.getMetaData();
			ResultSet resultSet;
			resultSet = meta.getTables(null, null, null, new String[] { "TABLE" });
			Log(">> " + resultSet+ " : " + resultSet.next());
			return !resultSet.next();
			//while (resultSet.next()) {
				//String name = resultSet.getString("TABLE_NAME");
				//String schema = resultSet.getString("TABLE_SCHEM");
				//Log(name + " on schema " + schema);
			//}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return false;
	}

	private static boolean tableExists(Connection connection, String tableName) throws SQLException {
		DatabaseMetaData meta = connection.getMetaData();
		ResultSet resultSet = meta.getTables(null, null, tableName, new String[] { "TABLE" });

		return resultSet.next();
	}
	
	private static void _fillAccounts(Connection connection) {
		Log("_FillingAccounts");
		try {
			if(!tableExists(connection, "ACCOUNTS"))
			{
				//connection.createStatement().executeUpdate("create table ACCOUNTS (id int primary key auto_increment, name VARCHAR(255))");
				String sql = "CREATE TABLE accounts (name VARCHAR(255))";
				connection.createStatement().executeUpdate(sql);
				Log("Creating ACCOUNTS table");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private static void _fillUsers(Connection connection) {
		Log("_FillingUsers");
		try {
			if(!tableExists(connection, "USERS"))
			{
				//connection.createStatement().executeUpdate("create table ACCOUNTS (id int primary key auto_increment, name VARCHAR(255))");
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
