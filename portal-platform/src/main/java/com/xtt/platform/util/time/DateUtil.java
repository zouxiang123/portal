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
     * 计算年龄
     * 
     * @Title: getAge
     * @param brithday
     *            出生日期
     * @return
     *
     */
    public static int getAge(Date brithday) {
        return getAge(brithday, new Date());
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
     * @param date1
     *            日期1
     * @param date2
     *            日期2
     * @return -1 日期为null
     */
    public static int getDays(Date date1, Date date2) {
        if (date1 == null || date2 == null) {
            return -1;
        }
        long between_days = Math.abs(date1.getTime() - date2.getTime()) / (1000 * 3600 * 24);
        return (int) between_days;
    }

    public static void main(String[] args) {
        Date d1 = DateFormatUtil.convertStrToDate("1999-03-01", "yyyy-MM-dd");
        Date d2 = DateFormatUtil.convertStrToDate("2000-03-01", "yyyy-MM-dd");
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
     * @param field
     *            加减字段标识值（1：年，2：月，3：星期，5：日，10：小时，12：分钟，13：秒）
     * @param amount
     *            加减时长
     * 
     * @return
     */
    public static Date add(Date date, int field, int amount) {
        GregorianCalendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        calendar.add(field, amount);
        return calendar.getTime();
    }

    /**
     * 获取当前日期是星期几
     * 
     * @Title: getWeekOfDate
     * @param dt
     * @return
     *
     */
    public static String getWeekOfDate(Date dt) {
        String[] weekDays = { "星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六" };
        Calendar cal = Calendar.getInstance();
        cal.setTime(dt);
        int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (w < 0)
            w = 0;
        return weekDays[w];
    }

}
