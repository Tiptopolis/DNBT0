package Metatron.X_;

import static Metatron.Core._M.M_Utils.*;

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

import Metatron.Core.Primitive.aValue;
import Metatron.Core.Primitive.Struct.aList;

public class _SQL {

	private static Connection connectSqliteDB(String path, String fileName) {
		// Local DB, copy data from Maria, werx but not implemented atm
		String url = "jdbc:sqlite:" + path + "\\" + fileName; // <storage path, folder must already exist lol

		try {
			// moved from predicate of try(){}, leaving it there auto-closed connection lol
			Connection conn = DriverManager.getConnection(url);
			if (conn == null) {
				DatabaseMetaData meta = conn.getMetaData();
				Log("The driver name is " + meta.getDriverName());
				Log("A new database has been created.");
				// build schema if there were one lolol
				return conn;
			} else {
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

	public static boolean createTable(Connection connection, String tableName) throws SQLException {

		String sql = "";
		if(tableExists(connection, tableName))		
			sql = "DROP TABLE IF EXISTS " + tableName.toLowerCase()+";";
		else
			sql = "CREATE TABLE " + tableName.toLowerCase()+"()";
		
		
		connection.createStatement().executeUpdate(sql);
		
		
		
		return tableExists(connection, tableName);
	}

	public static ResultSet getTable(Connection connection, String tableName) {

		try {
			Statement s = connection.createStatement();
			ResultSet result;
			result = s.executeQuery("SELECT * FROM " + tableName);
			return result;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;

	}

	public static aList<String> getColumnNames(Connection connection, String ofTable) {
		ResultSet rs = getTable(connection, ofTable);
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
	
	
	
	public static void setValue(Connection connection, String tableName, aValue v)
	{
		
	}
	
	public static void insertColumn(Connection connection, String tableName, aValue v)
	{
		
	}
	
}
