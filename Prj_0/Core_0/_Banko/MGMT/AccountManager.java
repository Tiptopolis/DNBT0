package Core._Banko.MGMT;

import static Core.AppUtils.Log;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.SQLException;

import Core._Banko._Account;
import Core._Banko._CustomerProfile;
import Core._PRIM.aList;
import Core._PRIM.aSet;

public class AccountManager {

	public static Connection DB_Link; // find/create

	private aSet<_Account> AccountCache;
	private aList<_CustomerProfile> UserCache;

	public AccountManager() {

		// get or create
		this.createNewDatabase("RevDB.db");
	}

	public void update(float deltaTime) {

	}

	public static void createNewDatabase(String fileName) {

		String url = "jdbc:sqlite:C:\\Users\\SU\\" + fileName; // <storage path

		try (Connection conn = DriverManager.getConnection(url)) {
			if (conn != null) {
				DB_Link = conn;
				DatabaseMetaData meta = conn.getMetaData();
				Log("The driver name is " + meta.getDriverName());
				Log("A new database has been created.");
			}

		} catch (SQLException e) {
			Log(e.getMessage());
		}

		// build schema

	}

}
