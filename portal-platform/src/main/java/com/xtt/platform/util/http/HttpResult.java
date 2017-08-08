/**   
 * @Title: Result.java 
 * @Package com.xtt.platform.util.http
 * Copyright: Copyright (c) 2015
 * @author: Tik   
 * @date: 2017年8月3日 下午7:22:26 
 *
 */
package com.xtt.platform.util.http;

/**
 * @ClassName: Result
 * @date: 2017年8月3日 下午7:22:26
 * @version: V1.0
 *
 */
public class HttpResult {

    /** 失败 */
    public static final String FAILURE = "0";
    /** 失败 */
    public static final String SUCCESS = "1";
    /** 警告 */
    public static final String WARNING = "2";
    /** 无效的token */
    public static final String INVALIDATE_TOKEN = "3";
    /** 重复 */
    public static final String REPETITION = "4";

    /** 返回状态 */
    private String status;
    /** 错误消息 */
    private String errmsg;
    /** 返回结果 */
    private Object rs;

    /** 错误消息(移动端用) */
    private String token;
    /** 错误代码(移动端用) */
    private String errcode;

    /**
     * @return the status
     */
    public String getStatus() {
        return status;
    }

    /**
     * @param status
     *            the status to set
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * @return the errmsg
     */
    public String getErrmsg() {
        return errmsg;
    }

    /**
     * @param errmsg
     *            the errmsg to set
     */
    public void setErrmsg(String errmsg) {
        this.errmsg = errmsg;
    }

    /**
     * @return the rs
     */
    public Object getRs() {
        return rs;
    }

    /**
     * @param rs
     *            the rs to set
     */
    public void setRs(Object rs) {
        this.rs = rs;
    }

    /**
     * @return the token
     */
    public String getToken() {
        return token;
    }

    /**
     * @param token
     *            the token to set
     */
    public void setToken(String token) {
        this.token = token;
    }

    /**
     * @return the errcode
     */
    public String getErrcode() {
        return errcode;
    }

    /**
     * @param errcode
     *            the errcode to set
     */
    public void setErrcode(String errcode) {
        this.errcode = errcode;
    }
}
