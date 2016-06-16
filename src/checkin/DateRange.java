package checkin;

import java.io.Serializable;
import java.util.Date;

public class DateRange implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4106895773339589824L;
	private Date startDate;
	private Date endDate;
	
	public DateRange(String startDate, String endDate){
		this.startDate = new Date(Long.parseLong(startDate) * 1000);
		this.endDate = new Date(Long.parseLong(endDate) * 1000);
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	@Override
	public String toString() {
		return "From: " + startDate + " Until: " + endDate;
	}
	
	
}
