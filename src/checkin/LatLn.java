package checkin;

import java.io.Serializable;

public class LatLn implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -7863697679357770274L;

	private double lat;
	
	private double ln;
	
	/**
	 * Clone constructor
	 * @param objToCopy
	 */
	public LatLn(LatLn objToCopy){
		this.lat = objToCopy.getLat();
		this.ln = objToCopy.getLn();
	}
	
	public LatLn(double lat, double ln){
		this.lat = lat;
		this.ln = ln;
	}

	public double getLat() {
		return lat;
	}

	public void setLat(double lat) {
		this.lat = lat;
	}

	public double getLn() {
		return ln;
	}

	public void setLn(double ln) {
		this.ln = ln;
	}

	@Override
	public String toString() {
		return String.valueOf(lat) + ',' + String.valueOf(ln);
	}
	
	
}
