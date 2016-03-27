package checkin;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import database.Database;

public class CheckinCollection extends ArrayList<Checkin> {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5928156190685121100L;
	public static final String TABLE_NAME = "checkins";

	public static ArrayList<Checkin> getCheckins() throws SQLException {
		Database db = Database.getInstance();
		ArrayList<Checkin> checkins = hydrateData(db.findAll(TABLE_NAME));
		return checkins;
	}

	public static ArrayList<Checkin> getCheckins(int limit) throws SQLException {
		Database db = Database.getInstance();
		ArrayList<Checkin> checkins = hydrateData(db.findByLimit(TABLE_NAME, limit));
		return checkins;

	}

	private static ArrayList<Checkin> hydrateData(ResultSet fetchedData) throws SQLException {
		ArrayList<Checkin> checkins = new ArrayList<Checkin>();
		while (fetchedData.next()) {
			POI poi = new POI(fetchedData.getString("POI"), fetchedData.getString("POI_category"),
					fetchedData.getString("POI_category_id"), fetchedData.getString("POI_name"));

			Location location = new Location(fetchedData.getDouble("latitude"), fetchedData.getDouble("longitude"));

			Checkin checkin = new Checkin(fetchedData.getInt("id"), location, fetchedData.getString("photos"), poi,
					fetchedData.getTimestamp("time"), fetchedData.getInt("user"));

			checkins.add(checkin);
		}
		fetchedData.close();
		return checkins;
	}

}
