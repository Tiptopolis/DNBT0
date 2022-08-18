package Metatron.Core.System.Script._SQL;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class SQL_Adapter {

	public static final String module = "jdbc";
	public static String DBMS = "";

	protected SQL_Adapter(String url) {

	}

	protected SQL_Adapter(Path p) {

	}

	protected SQL_Adapter(File f) {

	}

	protected SQL_Adapter(Connection c) {

	}

	//////

	// Sqlite //
	public static SQL_Adapter SqliteAdapter(String path, String filename) {
		return null;
	}

	// Maria //
	public static SQL_Adapter MariaDB_Adapter() {
		return null;
	}

	//////

}
