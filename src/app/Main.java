package app;

import java.sql.SQLException;
import java.util.ArrayList;

import checkin.Checkin;
import checkin.CheckinCollection;

public class Main {

	public static void main(String[] args) {
		try {
			ArrayList<Checkin> checkins = CheckinCollection.getCheckins(10);

			for (Checkin checkin : checkins) {
				System.out.println("##### Checkin " + checkin.getId() + " ######");
				System.out.println("By user " + checkin.getUser());
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

}
