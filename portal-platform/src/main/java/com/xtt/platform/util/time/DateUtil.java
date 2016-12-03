package com.xtt.platform.util.time;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang3.time.DateUtils;

public class DateUtil extends DateUtils {

	public DateUtil() {
		super();
	}

	/**
	 * 计算年龄
	 * 
	 * @Title: getAge
	 * @param brithday
	 * @return
	 *
	 */
	public static int getAge(Date brithday) {
		int age = 0;
		Calendar born = Calendar.getInstance();
		Calendar now = Calendar.getInstance();
		if (brithday != null) {
			now.setTime(new Date());
			born.setTime(brithday);
			if (born.after(now)) {
				throw new IllegalArgumentException("Can't be born in the future");
			}
			age = now.get(Calendar.YEAR) - born.get(Calendar.YEAR);
			if (now.get(Calendar.DAY_OF_YEAR) < born.get(Calendar.DAY_OF_YEAR)) {
				age -= 1;
			}
		}
		return age;
	}
	
	/**
	 * 功能描述：格式化输出日期
	 * 
	 * @param date
	 *            Date 日期
	 * @param format
	 *            String 格式
	 * @return 返回字符型日期
	 */
	public static String format(Date date, String format) {
		String result = "";
		try {
			if (date != null) {
				SimpleDateFormat dateFormat = new SimpleDateFormat(format);
				result = dateFormat.format(date);
			}
		} catch (Exception e) {
		}
		return result;
	}
	
	/**
	 * 功能描述：格式化日期
	 * 
	 * @param dateStr
	 *            String 字符型日期
	 * @param format
	 *            String 格式
	 * @return Date 日期
	 */
	public static Date parseDate(String dateStr, String format) {
		Date date = null;
		try {
			SimpleDateFormat format2 = new SimpleDateFormat(format);
			date = format2.parse(dateStr);
		} catch (Exception e) {
		}
		return date;
	}
	
	/**
	 * 获取一个时间距当前24点所差的秒数
	 * 
	 * @param date
	 * @return
	 */
	public static int getTo24Second(Date date) {
		int start = (int) (date.getTime() / 1000);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DAY_OF_YEAR, 1);
		Date endDate = calendar.getTime();
		endDate = parseDate(format(endDate, "yyyy-MM-dd"), "yyyy-MM-dd");
		int end = (int) (endDate.getTime() / 1000);
		return end - start;
	}
	/** 
     * 计算两个日期之间相差的天数 
     * @param moreDate 	大日期
     * @param lessDate 	小日期
     * @return 
     * 	-1	日期为null或日期传入相反
     */
	public static int getDays(Date moreDate,Date lessDate){
		if(moreDate == null || lessDate == null){
			return -1;
		}
		
		Calendar cal = Calendar.getInstance();  
		cal.setTime(moreDate);  
		long time1 = cal.getTimeInMillis();               
		cal.setTime(lessDate);  
		long time2 = cal.getTimeInMillis();       
		long between_days=(time1-time2)/(1000*3600*24);  
		return Integer.parseInt(String.valueOf(between_days));         
	}
	
	public static void main(String[] args) {
		Date d1 = new Date();
		Date d2 = DateFormatUtil.convertStrToDate("2016-10-01", "yyyy-MM-dd");
		System.out.println(getDays(d1, d2));
	}
}
