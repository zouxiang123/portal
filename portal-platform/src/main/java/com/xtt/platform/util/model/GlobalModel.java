package com.xtt.platform.util.model;

public class GlobalModel {

	private String id;
	private String name;
	private String type;

	// 依赖的模块编号，关联本表
	// 被依赖的模块类型一定='D'
	// 如
	// OB(mode_type='T')的依赖模块为CALLBASE(mode_type='D')
	private String depId;

	// 默认是否选中 =1 默认选中
	private String defChk;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getDepId() {
		return depId;
	}

	public void setDepId(String depId) {
		this.depId = depId;
	}

	public String getDefChk() {
		return defChk;
	}

	public void setDefChk(String defChk) {
		this.defChk = defChk;
	}

}
