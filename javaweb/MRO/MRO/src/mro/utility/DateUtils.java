package mro.utility;

import java.util.Calendar;
import java.util.Date;

public class DateUtils {
	/**
     * 取得當天日期,不含時間
     *
     * @param date
     * @return
     */
    public static java.sql.Date getOnlyDate(Date date) {
        return new java.sql.Date(date.getYear(), date.getMonth(), date.getDate());
    }
	
	/**
     * 日期+n
     *
     * @param days
     * @return
     */
    public static Date getAddDate(Date date,int days) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(calendar.DATE,days); // Saturday
        return calendar.getTime();
    }
	/**
     * 取得當前日期所在週的第一天
     *
     * @param date
     * @return
     */
    public static Date getFirstDayOfWeek(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setFirstDayOfWeek(Calendar.SUNDAY);
        calendar.setTime(date);
        calendar.set(Calendar.DAY_OF_WEEK,
                      calendar.getFirstDayOfWeek()); // Sunday
        return calendar.getTime();
    }

    /**
     * 取得當前日期所在週的最後一天
     *
     * @param date
     * @return
     */
    public static Date getLastDayOfWeek(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setFirstDayOfWeek(Calendar.SUNDAY);
        calendar.setTime(date);
        calendar.set(Calendar.DAY_OF_WEEK,
                     calendar.getFirstDayOfWeek() + 6); // Saturday
        return calendar.getTime();
    }
	/**
     * 兩個日期相減的天數
     *
     * @param date
     * @return
     */
    public static long subtractDay(Date beginDate,Date endDate) {
    	Date tempEndDate=endDate==null?new Date():endDate;
		return (getOnlyDate(tempEndDate).getTime()-getOnlyDate(beginDate).getTime())/(24*60*60*1000);  
    }
    
	/**
     * 兩個日期相減的月數
     *
     * @param date
     * @return
     */
    public static long subtractMonth(Date beginDate,Date endDate) {
    	int beginM=beginDate.getYear()*12+beginDate.getMonth();
    	int endM=endDate.getYear()*12+endDate.getMonth();
		return endM- beginM;
    }
}
