package com.xtt.platform.util.time;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import com.xtt.platform.framework.exception.RDPException;

/**
 * 
 * @ClassName: DateUtil
 * @UpdateRemark: 说明本次修改内容
 * @Description: 应用服务日期covert
 * @version: V1.0
 */
public class DateFormatUtil extends DateFormatUtils {
	public static final String START_TIME_SUF = " 00:00:00";
	public static final String END_TIME_SUF = " 23:59:59";

	public static final String FORMAT_DATE_CN = "yyyy年MM月dd日";
	public static final String FORMAT_DATE1 = "yyyy-MM-dd";
	public static final String FORMAT_DATE2 = "yyyy/MM/dd";
	public static final String FORMAT_DATE3 = "MM/dd";
	public static final String FORMAT_YYYY_MM = "yyyy-MM";
	public static final String FORMAT_TIME1 = "yyyy-MM-dd HH:mm:ss";
	public static final String FORMAT_TIME2 = "HH:mm";
	public static final String FORMAT_TIME3 = "HHmmss";
	public static final String FORMAT_TIME4 = "HH:mm:ss";

	public static final String HOUR = "hour";
	public static final String MINUTE = "minute";
	public static final String SECOND = "second";

	/**
	 * 
	 * @Title: convertDateToStr
	 * @author: Tik
	 * @CreateDate: 2014-3-28 下午4:22:28
	 * @UpdateUser: Tik
	 * @UpdateDate: 2014-3-28 下午4:22:28
	 * @UpdateRemark: 说明本次修改内容
	 * @Description: 返回自己所需格式的日期，如2014-12-13 yyyy-MM-dd
	 * @version: V1.0
	 * @param date
	 *            日期函数
	 * @param pattern
	 *            返回自己所需字符型的日期
	 * @return
	 * @throws RDPException
	 */
	public static String convertDateToStr(Date date) {

		return convertDateToStr(date, FORMAT_DATE1);

	}

	/**
	 * 获得两个String型日期之间相差的天数（第2个减第1个）
	 * 
	 * @param String
	 *            first, String second
	 * @return int 相差的天数
	 */
	public static int getDaysBetweenDates(String first, String second) {
		Date d1 = getFormatDateTime(first, IDateConst.DATE_FORMAT);
		Date d2 = getFormatDateTime(second, IDateConst.DATE_FORMAT);

		Long mils = (d2.getTime() - d1.getTime()) / (IDateConst.TIME_DAY_MILLISECOND);

		return mils.intValue();
	}

	/**
	 * 根据格式得到格式化后的时间
	 * 
	 * @param currDate
	 *            要格式化的时间
	 * @param format
	 *            时间格式，如yyyy-MM-dd HH:mm:ss
	 * @see java.text.SimpleDateFormat#parse(java.lang.String)
	 * @return Date 返回格式化后的时间，格式由参数<code>format</code>定义，如yyyy-MM-dd HH:mm:ss
	 */
	public static Date getFormatDateTime(String currDate, String format) {
		if (currDate == null) {
			return null;
		}
		SimpleDateFormat dtFormatdB = null;
		try {
			dtFormatdB = new SimpleDateFormat(format);
			return dtFormatdB.parse(currDate);
		} catch (Exception e) {
			dtFormatdB = new SimpleDateFormat(IDateConst.TIME_FORMAT);
			try {
				return dtFormatdB.parse(currDate);
			} catch (Exception ex) {
			}
		}
		return null;
	}

	public static String getCurrentDateStr(String formatStr) {
		String strDate = "";
		DateFormat dateFormat = new SimpleDateFormat(formatStr);
		strDate = dateFormat.format(new Date(System.currentTimeMillis()));
		return strDate;
	}

	/**
	 * 
	 * @Title: convertDateToStr
	 * @author: Tik
	 * @CreateDate: 2014-3-28 下午4:22:28
	 * @UpdateUser: Tik
	 * @UpdateDate: 2014-3-28 下午4:22:28
	 * @UpdateRemark: 说明本次修改内容
	 * @Description: 返回自己所需格式的日期，如2014-12-13 yyyy-MM-dd
	 * @version: V1.0
	 * @param date
	 *            日期函数
	 * @param pattern
	 *            返回自己所需字符型的日期
	 * @return
	 * @throws RDPException
	 */
	public static String convertDateToStr(Date date, String pattern) {
		final DateTimeFormatter formatter = DateTimeFormat.forPattern(pattern);
		if (date != null) {
			return formatter.print(new DateTime(date.getTime()));
		}
		return "";

	}

	/**
	 * 转换string 为date
	 * 
	 * @Title: convertStrToDate
	 * @param date
	 * @return
	 * 
	 */
	public static Date convertStrToDate(String date) {
		final DateTimeFormatter formatter = DateTimeFormat.forPattern(FORMAT_DATE1);
		if (StringUtils.isNotEmpty(date)) {
			return formatter.parseDateTime(date).toDate();
		}
		return null;

	}

	/**
	 * 
	 * @Title: convertStrToDate
	 * @UpdateRemark: 说明本次修改内容
	 * @Description: 字符日期转换自己所需格式的日期
	 * @version: V1.0
	 * @param date
	 * @param pattern
	 * @return
	 * @throws RDPException
	 */
	public static Date convertStrToDate(String date, String pattern) {
		final DateTimeFormatter formatter = DateTimeFormat.forPattern(pattern);
		if (StringUtils.isNotEmpty(date)) {
			return formatter.parseDateTime(date).toDate();
		}
		return null;

	}

	/**
	 * 
	 * @Title: getDate
	 * @version: V1.0
	 * @return
	 * @throws RDPException
	 */
	public static Date getDate() {
		return new DateTime().toDate();

	}

	/**
	 * 根据时分秒查询组装日期
	 * 
	 * @Title: getDate
	 * @param hour
	 * @param minute
	 * @param second
	 * @return
	 * 
	 */
	public static Date getDate(Integer hour, Integer minute, Integer second) {

		return getDate(null, hour, minute, second);
	}

	/**
	 * 根据时分秒查询组装日期
	 * 
	 * @Title: getDate
	 * @param hour
	 * @param minute
	 * @param second
	 * @return
	 * 
	 */
	public static Date getDate(Date date, Integer hour, Integer minute, Integer second) {
		Calendar cal = Calendar.getInstance();
		if (date != null) {
			cal.setTime(date);
		}
		if (hour == null && minute == null && second == null) {
			return cal.getTime();
		}
		cal.set(Calendar.HOUR_OF_DAY, hour == null ? 0 : hour);
		cal.set(Calendar.MINUTE, minute == null ? 0 : minute);
		cal.set(Calendar.SECOND, second == null ? 0 : second);
		return cal.getTime();
	}

	/**
	 * 根据时间获取十分秒，存入map
	 * 
	 * @Title: getHourMinuteSecond
	 * @param date
	 * @return
	 * 
	 */
	public static Map<String, Integer> getHourMinuteSecond(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		Map<String, Integer> map = new HashMap<String, Integer>();
		map.put(HOUR, cal.get(Calendar.HOUR_OF_DAY));
		map.put(MINUTE, cal.get(Calendar.MINUTE));
		return map;
	}

	public static String getStartTimeStr(String startDateStr) {
		return startDateStr + START_TIME_SUF;
	}

	public static String getEndTimeStr(String endDateStr) {
		return endDateStr + END_TIME_SUF;
	}

	public static Date getStartTime(Date date) {
		String startDateStr = convertDateToStr(date);
		startDateStr = getStartTimeStr(startDateStr);
		return convertStrToDate(startDateStr, FORMAT_TIME1);
	}

	public static Date getEndTime(Date date) {
		String endDateStr = convertDateToStr(date);
		endDateStr = getEndTimeStr(endDateStr);
		return convertStrToDate(endDateStr, FORMAT_TIME1);
	}

	public static Date getStartTime(String startDateStr) {
		return convertStrToDate(getStartTimeStr(startDateStr), FORMAT_TIME1);
	}

	public static Date getEndTime(String endDateStr) {
		return convertStrToDate(getEndTimeStr(endDateStr), FORMAT_TIME1);
	}

	/**
	 * 当前时间之前或之后的时间
	 * 
	 * @param hourTime
	 * @param beforeDate
	 * @param beforeMonth
	 * @return
	 */
	public static String beforeMonthDate(int hourTime, int beforeDate, int beforeMonth, String format) {
		String dateStr = "";
		Calendar calendar = Calendar.getInstance();
		DateFormat sdf = new SimpleDateFormat(format);
		if (beforeMonth > 0) {
			calendar.add(Calendar.MONTH, -beforeMonth);
		} else if (beforeDate > 0) {
			calendar.add(Calendar.DATE, -beforeDate);
		} else if (hourTime > 0) {
			calendar.add(Calendar.HOUR_OF_DAY, -hourTime);
		}
		Date date = calendar.getTime();
		dateStr = sdf.format(date);

		return dateStr;
	}

}
