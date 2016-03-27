package checkin;

import java.sql.Timestamp;

public class Checkin {
	private int id;
	
	private Location location;

	private String photos;
	
	private POI poi;
	
	private Timestamp time;

	private int user;
	
	public Checkin(int id, Location location, String photos, POI poi, Timestamp time, int user){
		this.id = id;
		this.location = location;
		this.photos = photos;
		this.poi = poi;
		this.time = time;
		this.user = user;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	public String getPhotos() {
		return photos;
	}

	public void setPhotos(String photos) {
		this.photos = photos;
	}

	public POI getPoi() {
		return poi;
	}

	public void setPoi(POI poi) {
		this.poi = poi;
	}

	public Timestamp getTime() {
		return time;
	}

	public void setTime(Timestamp time) {
		this.time = time;
	}

	public int getUser() {
		return user;
	}

	public void setUser(int user) {
		this.user = user;
	}


}
