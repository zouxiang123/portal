package com.xtt.platform.util.http;

/**
 * 第三方请求结果返回
 * 
 * @author wolf-yansl
 *
 */
public class HttpClientResultUtil {

    /**
     * 请求状态
     */
    private int status;
    /**
     * 请求内容
     */
    private String context;
    /**
     * 请求异常信息
     */
    private String exceptionMessage;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public String getExceptionMessage() {
        return exceptionMessage;
    }

    public void setExceptionMessage(String exceptionMessage) {
        this.exceptionMessage = exceptionMessage;
    }

    @Override
    public String toString() {
        return "HttpClientResult [status=" + status + ", context=" + context + ", exceptionMessage=" + exceptionMessage + "]";
    }

}
