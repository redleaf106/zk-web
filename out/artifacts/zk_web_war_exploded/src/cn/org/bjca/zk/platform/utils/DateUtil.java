package cn.org.bjca.zk.platform.utils;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/***************************************************************************
 * <pre>日期工具类</pre>
 * @文件名称:  DateUtil.java
 * @包   路   径： cn.org.bjca.zk.platform.sdk.utils
 * @版权所有：北京数字认证股份有限公司 (C) 2015
 *
 * @类描述:  
 * @版本: V2.0
 * @创建人： guzhixian
 * @创建时间：2015-4-16 下午5:22:02
 ***************************************************************************/
public class DateUtil {

	private static Logger log = LoggerFactory.getLogger(DateUtil.class);// 日志

	public static final int INT_24 = 24;

	public static final int INT_60 = 60;

	public static final int INT_1000 = 1000;

	public static String SHORT_DATE_PATTERN = "yyyyMMddHHmmss";
	
	public static String STANDARD_DATE_PATTERN = "yyyy年MM月dd日 HH时mm分ss秒 EEEE";

	public static String SHORT_DATE_PATTERN_YMD = "yyyyMMdd";

	public static String CH_DATE_PATTERN_YMD = "yyyy年MM月dd日";

	public static String DATE_PATTERN = "yyyy-MM-dd HH:mm:ss";

	public static String DATE_PATTERN_YMD = "yyyy-MM-dd";

	/**
	 * This method generates a string representation of a date's date/time in
	 * the format you specify on input
	 *
	 * @param aMask the date pattern the string is in
	 * @param aDate a date object
	 * @return a formatted string representation of the date
	 * @see java.text.SimpleDateFormat
	 */
	public static String convertDateToString(String aMask, Date aDate) {
		SimpleDateFormat df;
		String returnValue = "";

		if (aDate == null) {
			log.error("[getDateTime]aDate is null!");
		} else {
			df = new SimpleDateFormat(aMask);
			returnValue = df.format(aDate);
		}

		return (returnValue);
	}

	/**
	 * This method generates a string representation of a date based on the
	 * System Property 'dateFormat' in the format you specify on input
	 *
	 * @param aDate A date to convert
	 * @return a string representation of the date
	 */
	public static String convertDateToShortString(Date aDate) {
		return convertDateToString(SHORT_DATE_PATTERN, aDate);
	}
	
	public static String getCurrTime(Date aDate) {
		return convertDateToString(STANDARD_DATE_PATTERN, aDate);
	}

	/**
	 * This method generates a string representation of a date/time in the
	 * format you specify on input
	 *
	 * @param aMask   the date pattern the string is in
	 * @param strDate a string representation of a date
	 * @return a converted Date object
	 * @throws java.text.ParseException
	 * @see java.text.SimpleDateFormat
	 */
	public static Date convertStringToDate(String aMask, String strDate) {
		SimpleDateFormat df = new SimpleDateFormat(aMask);
		Date date = null;
		try {
			date = df.parse(strDate);
		} catch (ParseException pe) {
			log.error("Could not convert  a date, throwing exception", pe);
		}
		return (date);
	}

	public static Date convertStringToDate(String strDate) {
		return convertStringToDate(DATE_PATTERN, strDate);
	}

	public static Timestamp convertStringToTimestamp(String aMask, String strDate) throws ParseException {
		return new Timestamp(convertStringToDate(aMask, strDate).getTime());
	}

	/**
	 * This method converts a String to a date using the datePattern
	 * @param strDate the date to convert (in format MM/dd/yyyy)
	 * @return  a date object
	 */
	public static Date convertShortStringToDate(String strDate) {
		return convertStringToDate(SHORT_DATE_PATTERN, strDate);
	}

	/**
	 * This method returns the current date time in the format: MM/dd/yyyy HH:MM
	 * a
	 *
	 * @param theTime the current time
	 * @return the current date/time
	 */
	public static String getTimeNow(Date theTime) {
		String timePattern = "HH:mm";
		return convertDateToString(timePattern, theTime);
	}

	/**
	 * This method returns the current date in the format: MM/dd/yyyy
	 *
	 * @return the current date
	 * @throws java.text.ParseException
	 */
	public static Calendar getToday() throws ParseException {
		Date today = new Date();
		SimpleDateFormat df = new SimpleDateFormat(SHORT_DATE_PATTERN);

		String todayAsString = df.format(today);
		Calendar cal = new GregorianCalendar();
		cal.setTime(convertShortStringToDate(todayAsString));
		return cal;
	}

	public static String convertDateToString(Date aDate) {
		return convertDateToString(DATE_PATTERN, aDate);
	}

	public static String addDays(Date dt, int n) {
		try {
			return convertDateToShortString(addDaysDate(dt, n));
		} catch (Exception ex) {
			log.error("Could not modify a date, throwing exception", ex);
			return "";
		}
	}

	public static Date addDaysDate(Date dt, int n) {
		try {
			Calendar rightNow = Calendar.getInstance();
			rightNow.setTime(dt);
			rightNow.add(Calendar.DATE, n);
			return rightNow.getTime();
		} catch (Exception ex) {
			log.error("Could not modify a date, throwing exception", ex);
			return null;
		}
	}

	/**
	  * <p>获取某日期的下一年日期字符串</p>
	  * @Description:
	  * @param dateStr
	  * @return
	 */
	public static String getNextYear(String dateStr) {
		String finalDate = "";
		try {
			Date date = convertStringToDate(dateStr);
			finalDate = convertDateToString(getNextYearDate(date));
		} catch (Exception e) {
			log.error("Could not modify a date, throwing exception", e);
		}
		return finalDate;
	}

	public static Date getNextYearDate(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.YEAR, 1);
		return calendar.getTime();
	}

	public static long dateDiff(Date date1, Date date2) {
		long l = date2.getTime() - date1.getTime();
		return l / (INT_24 * INT_60 * INT_60 * INT_1000);

	}

	public static Date addDateDays(Date dt, int n) {
		try {
			Calendar rightNow = Calendar.getInstance();
			rightNow.setTime(dt);
			rightNow.add(Calendar.DATE, n);
			return rightNow.getTime();
		} catch (Exception ex) {
			log.error("Could not modify a date, throwing exception", ex);
			return null;
		}
	}

	public static Date addDateMinutes(Date dt, int n) {
		try {
			Calendar rightNow = Calendar.getInstance();
			rightNow.setTime(dt);
			rightNow.add(Calendar.MINUTE, n);
			return rightNow.getTime();
		} catch (Exception ex) {
			log.error("Could not modify a date, throwing exception", ex);
			return null;
		}
	}

	public static Date addDateHour(Date dt, int n) {
		try {
			Calendar rightNow = Calendar.getInstance();
			rightNow.setTime(dt);
			rightNow.add(Calendar.HOUR_OF_DAY, n);
			return rightNow.getTime();
		} catch (Exception ex) {
			log.error("Could not modify a date, throwing exception", ex);
			return null;
		}
	}

	/**
	 * <p>日期格式化</p>
	 * @Description:
	 * @param date 日期
	 * @return
	 */
	public static String dateFormat(Date date) {
		return convertDateToShortString(date);
	}

	/** 
     * 计算两个日期之间相差的天数 
     * @param smdate 较小的时间
     * @param bdate  较大的时间
     * @return 相差天数
  * @throws ParseException 
     */  
    public static int daysBetween(Date smdate,Date bdate) throws ParseException  
    {  
     SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
     smdate=sdf.parse(sdf.format(smdate));
     bdate=sdf.parse(sdf.format(bdate));
        Calendar cal = Calendar.getInstance();  
        cal.setTime(smdate);  
        long time1 = cal.getTimeInMillis();               
        cal.setTime(bdate);  
        long time2 = cal.getTimeInMillis();       
        long between_days=(time2-time1)/(1000*3600*24);
          
       return Integer.parseInt(String.valueOf(between_days));         
    }  
    
    /**
	 * <p>计算当前日期与截止有效期间隔(毫秒)</p>
	 * @Description:
	 * @param date1 截止日期
	 * @param date2 当前日期
	 * @return
	 */
    public static long millSecondDiff(Date date1, Date date2) {
		return date2.getTime() - date1.getTime();
	}
    
    /**
      * <p>获取日期中的年份</p>
      * @Description:
      * @param date
      * @return
     */
    public static int getYear(Date date){
    	Calendar calendar = Calendar.getInstance();
    	calendar.setTime(date);
		int year = calendar.get(Calendar.YEAR);
		return year;
    }
    
    /**
     * 
      * <p>获取时间中的天</p>
      * @Description:
      * @param date
      * @return
     */
    public static int getMonth(Date date){
    	Calendar calendar = Calendar.getInstance();
    	calendar.setTime(date);
		int month = calendar.get(Calendar.MONTH) + 1;
		return month;
    }
    
    /**
      * <p>得到时间中日</p>
      * @Description:
      * @param date
      * @return
     */
    public static int getDay(Date date){
    	Calendar calendar = Calendar.getInstance();
    	calendar.setTime(date);
		int day = calendar.get(Calendar.DAY_OF_MONTH);
		return day;
    }
    
    /**
      * <p>计算两个时间年份相差几年</p>
      * @Description:
      * @param date1 
      * @param date2
      * @return
     */
    public static int getYearDiffer(String date1,String date2){
    	Date startDate = convertStringToDate(date1);
    	Date endDate = convertStringToDate(date2);
    	
    	int startYear = getYear(startDate);
    	int endYear = getYear(endDate);
    	
		return endYear - startYear;
    }
    
    /**
      * <p>比较两个时间月份相差几个月</p>
      * @Description:
      * @param date1
      * @param date2
      * @return
     */
    public static int getMonthDiffer(String date1,String date2){
    	Date startDate = convertStringToDate(date1);
    	Date endDate = convertStringToDate(date2);
    	
    	int startMonth = getMonth(startDate);
    	int endMonth = getMonth(endDate);
    	
		return endMonth - startMonth;
    }
    
    /**
      * <p>比较两个天数相差几天</p>
      * @Description:
      * @param date1
      * @param date2
      * @return
     */
    public static int getDayDiffer(String date1,String date2){
    	Date startDate = convertStringToDate(date1);
    	Date endDate = convertStringToDate(date2);
    	
    	int startDay = getDay(startDate);
    	int endDay = getDay(endDate);
    	
		return endDay - startDay;
    }
}
