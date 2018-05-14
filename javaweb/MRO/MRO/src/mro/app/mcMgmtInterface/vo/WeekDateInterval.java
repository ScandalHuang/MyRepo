package mro.app.mcMgmtInterface.vo;

import java.util.Date;

import mro.utility.DateUtils;

public class WeekDateInterval {
	
	private Date dateStart;
	private Date dateEnd;
	
	public WeekDateInterval(Date date){
		Date today=new java.sql.Date(date.getYear(), date.getMonth(), date.getDate());
		dateStart=DateUtils.getFirstDayOfWeek(today);
		dateEnd=DateUtils.getLastDayOfWeek(today);
	}
	

	public Date getDateStart() {
		return dateStart;
	}

	public void setDateStart(Date dateStart) {
		this.dateStart = dateStart;
	}

	public Date getDateEnd() {
		return dateEnd;
	}

	public void setDateEnd(Date dateEnd) {
		this.dateEnd = dateEnd;
	}
    
}
