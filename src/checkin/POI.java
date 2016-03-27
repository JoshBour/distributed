package checkin;

public class POI {
	private String POI;

	private String POI_category;

	private String POI_category_id;

	private String POI_name;
	
	public POI(String POI, String POI_category, String POI_category_id, String POI_name){
		this.POI = POI;
		this.POI_category = POI_category;
		this.POI_category_id = POI_category_id;
		this.POI_name = POI_name;
	}

	public String getPOI() {
		return POI;
	}

	public void setPOI(String pOI) {
		POI = pOI;
	}

	public String getPOI_category() {
		return POI_category;
	}

	public void setPOI_category(String pOI_category) {
		POI_category = pOI_category;
	}

	public String getPOI_category_id() {
		return POI_category_id;
	}

	public void setPOI_category_id(String pOI_category_id) {
		POI_category_id = pOI_category_id;
	}

	public String getPOI_name() {
		return POI_name;
	}

	public void setPOI_name(String pOI_name) {
		POI_name = pOI_name;
	}
	
	

}
