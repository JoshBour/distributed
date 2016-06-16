package checkin;

import java.io.Serializable;

public class LocRectangle implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4863440415417671586L;
	private LatLn bottomLeft;
	private LatLn bottomRight;
	private LatLn topLeft;
	private LatLn topRight;

	public LocRectangle(LatLn bottomLeft, LatLn bottomRight, LatLn topLeft, LatLn topRight) {
		this.bottomLeft = bottomLeft;
		this.bottomRight = bottomRight;
		this.topLeft = topLeft;
		this.topRight = topRight;
	}

	public LocRectangle(double lat1, double lat2, double ln1, double ln2) {
		double minLn = Math.min(ln1, ln2), maxLn = Math.max(ln1, ln2), minLat = Math.min(lat1, lat2),
				maxLat = Math.max(lat1, lat2);

		bottomLeft = new LatLn(minLat, minLn);
		bottomRight = new LatLn(minLat, maxLn);
		topLeft = new LatLn(maxLat, minLn);
		topRight = new LatLn(maxLat, maxLn);
	}

	public LocRectangle[] split(int limit) {
		double splitLength = (topLeft.getLat() - bottomLeft.getLat()) / limit;
		LocRectangle[] subSquares = new LocRectangle[limit];
		LatLn topLeftTracer = new LatLn(bottomLeft);
		LatLn topRightTracer = new LatLn(bottomRight);

		for (int i = 0; i < limit; i++) {
			// the new bottom borders are the top of the previous
			LatLn splitBottomLeft = new LatLn(topLeftTracer);
			LatLn splitBottomRight = new LatLn(topRightTracer);
			
			// we calculate the new lat and update the top borders
			double newLat = topLeftTracer.getLat()+splitLength;
			topLeftTracer.setLat(newLat);
			topRightTracer.setLat(newLat);
			
			subSquares[i] = new LocRectangle(splitBottomLeft,splitBottomRight, topLeftTracer,topRightTracer);
		}

		return subSquares;
	}

	public LatLn getBottomLeft() {
		return bottomLeft;
	}

	public void setBottomLeft(LatLn bottomLeft) {
		this.bottomLeft = bottomLeft;
	}

	public LatLn getBottomRight() {
		return bottomRight;
	}

	public void setBottomRight(LatLn bottomRight) {
		this.bottomRight = bottomRight;
	}

	public LatLn getTopLeft() {
		return topLeft;
	}

	public void setTopLeft(LatLn topLeft) {
		this.topLeft = topLeft;
	}

	public LatLn getTopRight() {
		return topRight;
	}

	public void setTopRight(LatLn topRight) {
		this.topRight = topRight;
	}

	@Override
	public String toString() {
		return "Top left: " + topLeft + " \r\n" + "Bottom left: " + bottomLeft + " \r\n" + "Top right: " + topRight
				+ " \r\n" + "Bottom right: " + bottomRight + " \r\n";
	}

}
