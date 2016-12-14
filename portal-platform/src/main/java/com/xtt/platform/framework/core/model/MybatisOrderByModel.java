/**   
 * @Title: MybatisOrderByModel.java 
 * @Package com.xtt.platform.framework.core.model
 * Copyright: Copyright (c) 2015
 * @author: bruce   
 * @date: 2016年12月12日 下午7:55:26 
 *
 */
package com.xtt.platform.framework.core.model;

public class MybatisOrderByModel {
	private String paraName;
	private String paraAsc;

	public static String PARA_ASC = "ASC";
	public static String PARA_DESC = "DESC";
	public static String FORMAT_MSG = "paraName:%s,paraAsc:%s";

	public MybatisOrderByModel(String paraName) {
		this.paraName = paraName;
		this.paraAsc = PARA_ASC;
	}

	public MybatisOrderByModel(String paraName, String paraAsc) {
		this.paraName = paraName;
		this.paraAsc = paraAsc;
	}

	public String getParaName() {
		return paraName;
	}

	public void setParaName(String paraName) {
		this.paraName = paraName;
	}

	public String getParaAsc() {
		return paraAsc;
	}

	public void setParaAsc(String paraAsc) {
		this.paraAsc = paraAsc;
	}

	@Override
	public String toString() {

		return String.format(FORMAT_MSG, paraName, paraAsc);
	}

}
