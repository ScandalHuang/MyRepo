package mro.utility.vo;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

import mro.app.util.StringUtilsConvert;

public class DateOption implements Serializable{
	
	private static final long serialVersionUID = 7048912142515159323L;
	private Map year;
	private Map month;
	
	public DateOption(int range){
		year=getYearOption(range);
		month=getMonthOption();
	}
	 /**
     * 取得年選項
     *
     * @param date
     * @return
     */
    private Map getYearOption(int range) {
    	Map map=new LinkedHashMap();
        Calendar calendar = Calendar.getInstance();
    	for(int i=calendar.get(Calendar.YEAR)-range;i<=calendar.get(Calendar.YEAR)+range;i++){
    		map.put(i, i);
    	}
        return map;
    }
    /**
     * 取得月份選項
     *
     * @param date
     * @return
     */
    private  Map getMonthOption() {
    	Map map=new LinkedHashMap();
    	for(int i=1;i<=12;i++){
    		String s=StringUtilsConvert.addZeroForNum(String.valueOf(i),2);
    		map.put(s, s);
    	}
        return map;
    }
	public Map getYear() {
		return year;
	}
	public void setYear(Map year) {
		this.year = year;
	}
	public Map getMonth() {
		return month;
	}
	public void setMonth(Map month) {
		this.month = month;
	}
    
}
