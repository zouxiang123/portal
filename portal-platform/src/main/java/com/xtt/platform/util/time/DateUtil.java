package com.xtt.platform.util.time;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.apache.commons.lang3.time.DateUtils;

public class DateUtil extends DateUtils {

    public DateUtil() {
        super();
    }

    /**
     * 根据两个时间差来计算年龄
     * 
     * @Title: getAge
     * @param brithday:出生日期
     * @param paramDate:对比日期
     * @return
     *
     */
    public static int getAge(Date brithday, Date paramDate) {
        int age = 0;
        Calendar born = Calendar.getInstance();
        Calendar now = Calendar.getInstance();
        if (brithday != null && paramDate != null) {
            now.setTime(paramDate);
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
     * 
     * @param moreDate
     *            大日期
     * @param lessDate
     *            小日期
     * @return -1 日期为null或日期传入相反
     */
    public static int getDays(Date moreDate, Date lessDate) {
        if (moreDate == null || lessDate == null) {
            return -1;
        }

        Calendar cal = Calendar.getInstance();
        cal.setTime(moreDate);
        long time1 = cal.getTimeInMillis();
        cal.setTime(lessDate);
        long time2 = cal.getTimeInMillis();
        long between_days = (time1 - time2) / (1000 * 3600 * 24);
        return Integer.parseInt(String.valueOf(between_days));
    }

    public static void main(String[] args) {
        Date d1 = new Date();
        Date d2 = DateFormatUtil.convertStrToDate("2016-10-01", "yyyy-MM-dd");
        System.out.println(getDays(d1, d2));
    }

    /**
     * 取得当前系统时间，返回java.util.Date类型
     * 
     * @see java.util.Date
     * @return java.util.Date 返回服务器当前系统时间
     */
    public static java.util.Date getCurrDate() {
        return new java.util.Date();
    }

    /**
     * 获得指定日期的前一天
     * 
     * @param specifiedDay
     * @return
     * @throws Exception
     */
    public static String getSpecifiedDayBefore(String specifiedDay) {
        Calendar c = Calendar.getInstance();
        Date date = null;
        try {
            date = new SimpleDateFormat("yy-MM-dd").parse(specifiedDay);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        c.setTime(date);
        int day = c.get(Calendar.DATE);
        c.set(Calendar.DATE, day - 1);

        String dayBefore = new SimpleDateFormat("yyyy-MM-dd").format(c.getTime());
        return dayBefore;
    }

    /**
     * 获得指定日期的后一天
     * 
     * @param specifiedDay
     * @return
     */
    public static String getSpecifiedDayAfter(String specifiedDay) {
        Calendar c = Calendar.getInstance();
        Date date = null;
        try {
            date = new SimpleDateFormat("yy-MM-dd").parse(specifiedDay);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        c.setTime(date);
        int day = c.get(Calendar.DATE);
        c.set(Calendar.DATE, day + 1);

        String dayAfter = new SimpleDateFormat("yyyy-MM-dd").format(c.getTime());
        return dayAfter;
    }

    /**
     * 在指定时间上加减时长
     * 
     * @param date
     *            日期
     * @param amount
     *            加减时长
     * @param field
     *            加减字段标识值（1：年，2：月，3：星期，5：日，10：小时，12：分钟，13：秒）
     * @return
     */
    public static Date calendarAddDate(Date date, int amount, int field) {
        GregorianCalendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        calendar.add(field, amount);
        return calendar.getTime();
    }

}
