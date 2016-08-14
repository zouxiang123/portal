/**   
 * @Title: ttt.java 
 * @Package com.xtt.platform.util.model 
 * @Description: 用一句话描述该文件做什么 
 * Copyright: Copyright (c) 2014 
 * Company:Tik team by iss
 * @author: Tik   
 * @date: 2014年6月12日 下午3:25:43 
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

public class MultiMediaConfigModel {
	private String id;// 配置类型 1录音质检 2语音留言 3报表 4传真
	private String accWay;// 访问方式 1FTP 2映射网络驱动器
	private String ftpAdd;// IP地址
	private String driverName;// 网络驱动器
	private String ftpFilePath;// 文件在FTP的存放路径
	private String downloadPath;// 文件下载到
	private String ftpUserName;// 用户名
	private String ftpPassword;// 密码
	private int ftpPort;// ftp 端口
	private String ftpFileIntercept; // 截取长度
	private String serverPath;// 应用服务器地址

	// 文件服务器
	private String sharedAdd;// 文件服务器IP
	private String sharedUserName;// 文件服务器用户名
	private String sharedPassword;// 文件服务器密码
	private String sharedFilePath;// 共享文件夹
	private String description;// 配置描述

	private String tenantId;

	public String getServerPath() {
		return serverPath;
	}

	public void setServerPath(String serverPath) {
		this.serverPath = serverPath;
	}

	public int getFtpPort() {
		return ftpPort;
	}

	public void setFtpPort(int ftpPort) {
		this.ftpPort = ftpPort;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getAccWay() {
		return accWay;
	}

	public void setAccWay(String accWay) {
		this.accWay = accWay;
	}

	public String getDownloadPath() {
		return downloadPath;
	}

	public void setDownloadPath(String downloadPath) {
		this.downloadPath = downloadPath;
	}

	public String getDescription() {
		return description;
	}

	public String getFtpAdd() {
		return ftpAdd;
	}

	public void setFtpAdd(String ftpAdd) {
		this.ftpAdd = ftpAdd;
	}

	public String getFtpFilePath() {
		return ftpFilePath;
	}

	public void setFtpFilePath(String ftpFilePath) {
		this.ftpFilePath = ftpFilePath;
	}

	public String getFtpUserName() {
		return ftpUserName;
	}

	public void setFtpUserName(String ftpUserName) {
		this.ftpUserName = ftpUserName;
	}

	public String getFtpPassword() {
		return ftpPassword;
	}

	public void setFtpPassword(String ftpPassword) {
		this.ftpPassword = ftpPassword;
	}

	public String getSharedAdd() {
		return sharedAdd;
	}

	public void setSharedAdd(String sharedAdd) {
		this.sharedAdd = sharedAdd;
	}

	public String getSharedUserName() {
		return sharedUserName;
	}

	public void setSharedUserName(String sharedUserName) {
		this.sharedUserName = sharedUserName;
	}

	public String getSharedPassword() {
		return sharedPassword;
	}

	public void setSharedPassword(String sharedPassword) {
		this.sharedPassword = sharedPassword;
	}

	public String getSharedFilePath() {
		return sharedFilePath;
	}

	public void setSharedFilePath(String sharedFilePath) {
		this.sharedFilePath = sharedFilePath;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getTenantId() {
		return tenantId;
	}

	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}

	public String getDriverName() {
		return driverName;
	}

	public void setDriverName(String driverName) {
		this.driverName = driverName;
	}

	public String getFtpFileIntercept() {
		return ftpFileIntercept;
	}

	public void setFtpFileIntercept(String ftpFileIntercept) {
		this.ftpFileIntercept = ftpFileIntercept;
	}

}
