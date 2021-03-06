package com.xtt.platform.util.time;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xtt.platform.framework.exception.RDPException;
import com.xtt.platform.util.lang.StringUtil;

/**
 * 
 * @ClassName: DateUtil
 * @UpdateRemark: 说明本次修改内容
 * @Description: 应用服务日期covert
 * @version: V1.0
 */
public class DateFormatUtil extends DateFormatUtils {
    protected static Logger LOGGER = LoggerFactory.getLogger(DateFormatUtil.class);

    public static final String START_TIME_SUF = " 00:00:00";
    public static final String END_TIME_SUF = " 23:59:59";
    /** yyyy年MM月dd日 */
    public static final String FORMAT_DATE_CN = "yyyy年MM月dd日";
    /** yyyy-MM-dd */
    public static final String FORMAT_DATE1 = "yyyy-MM-dd";
    /** yyyy/MM/dd */
    public static final String FORMAT_DATE2 = "yyyy/MM/dd";
    /** MM/dd */
    public static final String FORMAT_DATE3 = "MM/dd";
    /** yyyyMMdd */
    public static final String FORMAT_DATE_YYYYMMDD = "yyyyMMdd";
    /** yyyy-MM */
    public static final String FORMAT_YYYY_MM = "yyyy-MM";
    /** yyyyMM */
    public static final String FORMAT_YYYYMM = "yyyyMM";
    /** yyyy-MM-dd HH:mm:ss */
    public static final String FORMAT_TIME1 = "yyyy-MM-dd HH:mm:ss";
    /** HH:mm */
    public static final String FORMAT_TIME2 = "HH:mm";
    /** HHmmss */
    public static final String FORMAT_TIME3 = "HHmmss";
    /** HH:mm:ss */
    public static final String FORMAT_TIME4 = "HH:mm:ss";
    /** yyyy/MM/dd HH:mm */
    public static final String FORMAT_TIME5 = "yyyy/MM/dd HH:mm";
    public static final String FORMAT_TIME6 = "MM月dd日  HH:mm";

    public static final String HOUR = "hour";
    public static final String MINUTE = "minute";
    public static final String SECOND = "second";
    public static final String FORMDAT_YEAR = "yyyy";

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
     * @return 返回自己所需字符型的日期
     * @throws RDPException
     */
    public static String convertDateToStr(Date date) {

        return convertDateToStr(date, FORMAT_DATE1);

    }

    /**
     * 获得两个String型日期之间相差的天数（第2个减第1个）
     * 
     * @param first
     * @param second
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

    /**
     * 根据当前时间的字符串
     * 
     * @Title: getCurrentDateStr
     * @param formatStr
     *            日期格式
     * @return
     *
     */
    public static String getCurrentDateStr(String formatStr) {
        if (StringUtil.isBlank(formatStr)) {
            formatStr = FORMAT_DATE1;
        }
        return convertDateToStr(new Date(), formatStr);
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
    public static String convertDateToStr(final Date date, final String pattern) {
        if (date == null) {
            return "";
        }
        return DateFormatUtils.format(date, pattern);

    }

    /**
     * 转换string 为date
     * 
     * @Title: convertStrToDate
     * @param date
     *            (yyyy-MM-dd )
     * @return
     * 
     */
    public static Date convertStrToDate(String date) {
        return convertStrToDate(date, FORMAT_DATE1);
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
        if (StringUtil.isBlank(date)) {
            return null;
        }
        try {
            return DateUtils.parseDate(date, pattern);
        } catch (ParseException e) {
            LOGGER.error("convertStrToDate(data:{}) error", date, e);
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
     * 根据日期字符串获取对应的日期 <br/>
     * <note>支持日期格式： <br/>
     * yyyy-MM-dd <br/>
     * yyyy/MM/dd <br/>
     * </note>
     * 
     * @Title: getDateByStr
     * @param dateStr
     * @return
     *
     */
    public static Date getDateByStr(String dateStr) {
        Date date = null;
        if (StringUtil.isBlank(dateStr)) {
            return date;
        }
        String split = "-";
        String[] arr = null;
        if (dateStr.indexOf(split) > 0) {
            arr = dateStr.split("-");
        }
        split = "/";
        if (arr == null && dateStr.indexOf(split) > 0) {
            arr = dateStr.split("/");
        }
        if (arr != null && arr.length == 3) {
            Calendar c = Calendar.getInstance();
            c.set(Calendar.YEAR, Integer.valueOf(arr[0]));
            c.set(Calendar.MONTH, Integer.valueOf(arr[1]) - 1);
            c.set(Calendar.DAY_OF_MONTH, Integer.valueOf(arr[2]));
            return c.getTime();
        }
        return date;
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

    /**
     * 当前时间之前或之后的时间
     * 
     * @param hourTime
     * @param beforeDate
     * @param beforeMonth
     * @return
     */
    public static String beforeMonthDateByDate(Date date, int hourTime, int beforeDate, int beforeMonth, String format) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        DateFormat sdf = new SimpleDateFormat(format);
        if (beforeMonth > 0) {
            calendar.add(Calendar.MONTH, -beforeMonth);
        } else if (beforeDate > 0) {
            calendar.add(Calendar.DATE, -beforeDate);
        } else if (hourTime > 0) {
            calendar.add(Calendar.HOUR_OF_DAY, -hourTime);
        }

        return sdf.format(calendar.getTime());
    }

    /**
     * 获取 系统 时间HH:mm:ss
     *
     * @Title: getSysTime
     * @param date
     * @return
     *
     */
    public static Map<String, String> getSysTime(Date date) {
        Map<String, String> map = new HashMap<String, String>();
        map.put("sysTime", DateUtil.format(date, DateFormatUtil.FORMAT_TIME4));
        return map;
    }

    /**
     *
     * @Title: getSysDate
     * @param date
     * @return yyyy-MM-dd HH:mm:ss
     *
     */
    public static Map<String, String> getSysDate(Date date) {
        Map<String, String> map = new HashMap<String, String>();
        map.put("sysDate", DateUtil.format(date, DateFormatUtil.FORMAT_TIME1));
        return map;
    }

    public static void main(String[] args) {
        System.out.println(convertStrToDate("03/13", FORMAT_DATE3));
    }
}
