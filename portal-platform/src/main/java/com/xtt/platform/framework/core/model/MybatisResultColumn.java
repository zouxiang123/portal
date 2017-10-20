/**   
 * @Title: MybatisResultColumn.java 
 * @Package com.xtt.platform.framework.core.model
 * Copyright: Copyright (c) 2015
 * @author: bruce   
 * @date: 2017年3月22日 下午7:27:42 
 *
 */
package com.xtt.platform.framework.core.model;

public class MybatisResultColumn {
    private String asName;// 别名
    private String resultName;

    public static String FORMAT_MSG = "asName:%s,resultName:%s";

    public MybatisResultColumn(String resultName) {
        this.resultName = resultName;
    }

    public MybatisResultColumn(String asName, String resultName) {
        this.asName = asName;
        this.resultName = resultName;
    }

    public String getResultName() {
        return resultName;
    }

    public void setResultName(String resultName) {
        this.resultName = resultName;
    }

    @Override
    public String toString() {

        return String.format(FORMAT_MSG, asName, resultName);
    }

    public String getAsName() {
        return asName;
    }

    public void setAsName(String asName) {
        this.asName = asName;
    }
}
