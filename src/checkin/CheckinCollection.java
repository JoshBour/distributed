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

	public static CheckinCollection getCheckinsBySquareAndDate(LocRectangle square, DateRange dateRange){
		Database db = Database.getInstance();
		CheckinCollection checkins = new CheckinCollection();
		try {
			checkins.hydrateData(db.findBySquareAndDate(TABLE_NAME, square, dateRange));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return checkins;
	}
	
	private void hydrateData(ResultSet fetchedData) throws SQLException {
		while (fetchedData.next()) {
			POI poi = new POI(fetchedData.getString("POI"), fetchedData.getString("POI_category"),
					fetchedData.getString("POI_category_id"), fetchedData.getString("POI_name"));

			LatLn location = new LatLn(fetchedData.getDouble("latitude"), fetchedData.getDouble("longitude"));

			Checkin checkin = new Checkin(fetchedData.getInt("id"), location, fetchedData.getString("photos"), poi,
					fetchedData.getTimestamp("time"), fetchedData.getInt("user"));

			this.add(checkin);
		}
		fetchedData.close();
	}

}
