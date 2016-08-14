/**   
* @Title: OrgModel.java 
* @Package com.xtt.platform.util.model 
* @Description: 用一句话描述该文件做什么 
* Copyright: Copyright (c) 2014 
* Company:Tik team by iss
* @author: Tik   
* @date: 2014年6月4日 下午6:39:07 
* @version: V1.0
* update Release(文件修正记录)
* <pre>
* author--updateDate--description----------------------Flag————
* Tik    2014-5-1    测试codesyle                      #Tik001
*
*
*
* </pre>
*
*/
package com.xtt.platform.util.model;

public class OrgModel {

	private String orgId;
	private String orgtype;
	private String orgname;
	private String orgpid;

	public String getOrgId() {
		this.orgId = "27";
		return orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}

	public String getOrgtype() {
		return orgtype;
	}

	public void setOrgtype(String orgtype) {
		this.orgtype = orgtype;
	}

	public String getOrgname() {
		orgname = "酱油组";
		return orgname;
	}

	public void setOrgname(String orgname) {
		this.orgname = orgname;
	}

	public String getOrgpid() {
		return orgpid;
	}

	public void setOrgpid(String orgpid) {
		this.orgpid = orgpid;
	}

}
