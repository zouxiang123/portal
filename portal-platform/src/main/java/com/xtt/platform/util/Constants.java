package com.xtt.platform.util;

public class Constants {
    // 全局默认日期格式
    public static final String SYS_YYYYMM = "yyyyMM";
    public static final String SYS_YYYYMMDD = "yyyyMMdd";
    public static final String SYS_YYYY_MM_DD = "yyyy-MM-dd";
    public static final String SYS_YYYYMMDDHHMMSS = "yyyyMMddHHmmss";
    public static final String SYS_YYYY_MM_DD_HH = "yyyy-MM-dd HH:mm:ss";
    // 用户对象信息
    public static final String USER_CONTEXT = "UserObject";
    // 登陆成功后跳转到目标页面
    public static final String LOGIN_TO_URL = "LOGIN_TO_URL";
    // 验证码存储信息
    public static final String KAPTCHA_SESSION_KEY = "KAPTCHA";

    /** 警告 */
    public static final String WARNING = "2";
    /** 成功 */
    public static final String SUCCESS = "1";
    /** 失败 */
    public static final String FAILURE = "0";

    /** 分隔符 */
    public static final String SPLIT = ",";
    /** 默认系统租户id */
    public static final int DEFAULT_SYS_TENANT_ID = 10000;
}
