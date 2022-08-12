package com.Rev.P1_Core;

import static com.Rev.P1_Core.AppUtils.*;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import com.Rev.P1_Core.Primitive.aSet;
import com.Rev.P1_Core.Primitive.Data.aDataField;
import com.Rev.P1_Core._MonDexApp.MonDexApp;
import com.Rev.P1_Core._aBankApp._BankApp;

public class Main {

	public static App Main;

	// entreport
	public static void main(String[] args) {
		Log("---");
		//
		Log("ASSMASTER");
		// Main = new App();
		// Main = new _BankApp();
		Main = new MonDexApp();
		Log("___");

	}

	//// get auto-incremented id
	//
	// int newID;
	// ResultSet rs = pstmt.getGeneratedKeys();
	// if(rs.next()) {
	// newID = rs.getInt(1);
	// }

}
