package com.yrl;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.DriverManager;

/*
 * This is a class for connector to connect to database using JDBC principles.
 */
public class ConnectionFactory {
	/**
	 * User name used to connect to the SQL server
	 */
	public static final String USERNAME = "asubedi5";

	/**
	 * Password used to connect to the SQL server
	 */
	public static final String PASSWORD = "Koh4noYie9xa";

	/**
	 * Connection parameters that may be necessary for server configuration
	 * 
	 */
	public static final String PARAMETERS = "";

	/**
	 * SQL server to connect to
	 */
	public static final String SERVER = "cse-linux-01.unl.edu";

	/**
	 * Fully formatted URL for a JDBC connection
	 */
	public static final String URL = String.format("jdbc:mysql://%s/%s?%s", SERVER, USERNAME, PARAMETERS);

	public static Connection connection() {
		Connection conn = null;
		try {
			conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);

		} catch (SQLException e) {
			// log.error("Error Connecting to the database");
			throw new RuntimeException(e);
		}
		return conn;
	}

	public static void closeConnection(Connection conn) {
		try {
			conn.close();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

}