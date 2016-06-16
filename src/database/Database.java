package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;

import checkin.DateRange;
import checkin.LatLn;
import checkin.LocRectangle;

public class Database {
	private static final String DB_HOST = "83.212.117.76";
	private static final String DB_PORT = "3306";
	private static final String DB_USER = "omada18";
	private static final String DB_PASS = "omada18db";
	private static final String DB_NAME = "ds_systems_2016";

	private static Database instance = null;

	private Connection connection;
	private Statement statement;
	private ResultSet resultSet;

	public static Database getInstance() {
		if (null == instance)
			instance = new Database();
		return instance;
	}

	private Database() {
		try {
			Class.forName("com.mysql.jdbc.Driver");

			connection = DriverManager.getConnection("jdbc:mysql://" + DB_HOST + ":" + DB_PORT + "/" + DB_NAME, DB_USER,
					DB_PASS);

			statement = connection.createStatement();

		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
	}

	public ResultSet findAll(String tableName) {
		String query = "select * from " + tableName;
		try {
			resultSet = statement.executeQuery(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return resultSet;
	}

	public ResultSet findBySquareAndDate(String tableName, LocRectangle square, DateRange dateRange) {
		// we cache the bottomleft and topright since they contain
		// all the loc info we are gonna need
		LatLn bottomLeft = square.getBottomLeft(), topRight = square.getTopRight();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String query = "select * from " + tableName;
		query += " where (latitude between " + bottomLeft.getLat() + " and " + topRight.getLat() + ")";
		query += " and (longitude between " + bottomLeft.getLn() + " and " + topRight.getLn() + ")";
		query += " and (time between '" + sdf.format(dateRange.getStartDate()) + "' and '"
				+ sdf.format(dateRange.getEndDate()) + "')";
		try {
			resultSet = statement.executeQuery(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return resultSet;
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
