package com.xtt.platform.util.time;

/**
 * describe:定义时间日期显示格式 fileUrl:IDateConst.java author：ZhouZuan email:zhou_zuan@sina.com date:2013-9-25
 **/
public interface IDateConst {
    int TIME_DAY_MILLISECOND = 86400000;

    String YYYY = "yyyy";
    String YYYYMMDD = "yyyyMMdd";
    String YYYYMMDD_HHMMSS = "yyyyMMddHHmmss";
    String YYYYMMDD_HHMM = "yyyyMMddHH";
    String HHMMSS = "HHmmss";

    String MONTH_FORMAT = "yyyy-MM";
    String DAY_FORMAT = "yyyyMMdd";
    String DATE_FORMAT = "yyyy-MM-dd";
    String DATE_FORMAT_CN = "yyyy年MM月dd日";
    String HOUR_FORMAT = "yyyy-MM-dd HH:mm";
    String HOUR_FORMAT_CN = "yyyy年MM月dd日 HH:mm";
    String TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    String TIME_FORMAT_CN = "yyyy年MM月dd日 HH:mm:ss";

}