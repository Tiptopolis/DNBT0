package com.Rev.P1_Core.Web;

import static com.Rev.P1_Core.AppUtils.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class JsonServlet extends HttpServlet {
	/*
	 * This will take a simple GET request and respond with "Pong!" and status 202,
	 * indicating the request was accepted.
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setStatus(202);
		Connection connection = connectMariaDB();
		Log(connection);
		resp.getWriter().print("Pongity!"+connection);
	}

	private static Connection connectMariaDB() {
		// Server DB
		Connection connection = null;
		try {
			Properties props = new Properties();
			ClassLoader loader = Thread.currentThread().getContextClassLoader();
			InputStream input = loader.getResourceAsStream("jdbc.properties");
			props.load(input);
			Log(props);

			String connectionString = "jdbc:mariadb://" + props.getProperty("hostname") + ":"
					+ props.getProperty("port") + "/" + props.getProperty("dbname") + "?user="
					+ props.getProperty("username") + "&password=" + props.getProperty("password");

			Class.forName("org.mariadb.jdbc.Driver");

			connection = DriverManager.getConnection(connectionString);

		} catch (IOException | SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return connection;
	}
}