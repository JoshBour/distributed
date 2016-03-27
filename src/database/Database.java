package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Database {
	private static final String DB_HOST = "195.251.252.98";
	private static final String DB_PORT = "3306";
	private static final String DB_USER = "omada18";
	private static final String DB_PASS = "omada18db";
	private static final String DB_NAME = "ds_systems_2016";

	private Connection connection;
	private Statement statement;
	private ResultSet resultSet;

	public Database() {
		try {
			Class.forName("com.mysql.jdbc.Driver");

			connection = DriverManager.getConnection("jdbc:mysql://" + DB_HOST + ":" + DB_PORT + "/" + DB_NAME, DB_USER,
					DB_PASS);

			statement = connection.createStatement();

		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void close() {
		if (resultSet != null)
			try {
				resultSet.close();

				if (statement != null)
					statement.close();

				if (connection != null)
					connection.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

	}
}
