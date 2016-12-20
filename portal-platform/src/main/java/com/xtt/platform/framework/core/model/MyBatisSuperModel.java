package com.xtt.platform.framework.core.model;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

/***
 * 
 * @ClassName: MyBaitsSuperModel
 * @author: Tik
 * @CreateDate: 2014-3-27 下午12:48:18
 * @UpdateUser: Tik
 * @UpdateDate: 2014-3-27 下午12:48:18
 * @UpdateRemark: 说明本次修改内容
 * @Description: 基础的实体类，用了准备公共参数
 * @version: V1.0
 */
public class MyBatisSuperModel {

	// 租户Id;设置为字符类型，以方便将来扩展
	public String tenantId;
	private int pageNo = 1;// 页码，默认是第一页
	private int pageSize = 15;// 每页显示的记录数，默认是15
	private int totalRecord;// 总记录数
	private int totalPage;// 总页数
	private List results;// 对应的当前页记录
	private Map<String, Object> params;// 其他的参数我们把它分装成一个Map对象

	private List<MybatisOrderByModel> orderByList;// 排序条件集合

	private String tableId;

	public String getTableId() {
		return tableId;
	}

	public void setTableId(String tableId) {
		this.tableId = tableId;
	}

	public void setTotalRecord(Integer totalRecord) {
		this.totalRecord = totalRecord;
		this.totalPage = (totalRecord + this.pageSize - 1) / pageSize;
		this.totalPage = this.totalPage == 0 ? 1 : this.totalPage;
	}

	public Map<String, Object> getParams() {
		return params;
	}

	public void setParams(Map<String, Object> params) {
		this.params = params;
	}

	private boolean ispaging = false;// 是否启动分页，默认不启动;只有启动分页才执行后面分页语句

	public boolean isIspaging() {
		return ispaging;
	}

	public void setIspaging(boolean ispaging) {
		this.ispaging = ispaging;
	}

	// private Map<String, Object> params = new HashMap<String, Object>();//
	// 其他的参数我们把它分装成一个Map对象
	public int getPageNo() {
		return pageNo;
	}

	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getTotalRecord() {
		return totalRecord;
	}

	public int getTotalPage() {
		return totalPage;
	}

	public List getResults() {
		return results;
	}

	public void setResults(List results) {
		this.results = results;
	}

	public String getTenantId() {
		return tenantId;
	}

	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}

	// 分页请求路径
	private String requestPath;

	public String getRequestPath() {
		return requestPath;
	}

	public void setRequestPath(HttpServletRequest request) {
		if (request == null)
			return;
		String servletPath = request.getServletPath();
		if (null != servletPath && servletPath.indexOf(".") != -1) {
			servletPath = servletPath.substring(1, servletPath.indexOf("."));
		} else {
			servletPath = "user/listUser";
		}
		this.requestPath = servletPath;
	}

	public List<MybatisOrderByModel> getOrderByList() {
		return orderByList;
	}

	public void setOrderByList(List<MybatisOrderByModel> orderByList) {
		this.orderByList = orderByList;
	}

}
