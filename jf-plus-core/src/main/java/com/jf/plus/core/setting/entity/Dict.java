package com.jf.plus.core.setting.entity;

import cn.iutils.sys.entity.DataEntity;

/**
 * 字典表
 * 
 * @author Tng
 * @version 1.0
 */
public class Dict extends DataEntity<Dict> {

	private static final long serialVersionUID = 1L;

	private Long orgId;// 机构id
	private String orgName;// 机构名称
	private String dict;// 字典名称
	private String key;// 字典元素标识
	private String val;// 元素值
	private Integer sort;// 排序
	private String avaliable;//是否启用
	
	/**
	 * 针对销售区域扩展字段
	 */
	private String type;// 类型：1 大区 2 省份 3城市
	private String pid;// 父ID
	private String pids;// 父IDs

	public Dict() {
		super();
	}

	public Dict(String id) {
		super(id);
	}

	public Long getOrgId() {
		return orgId;
	}

	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName == null ? null : orgName.trim();
	}

	public String getDict() {
		return dict;
	}

	public void setDict(String dict) {
		this.dict = dict == null ? null : dict.trim();
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key == null ? null : key.trim();
	}

	public String getVal() {
		return val;
	}

	public void setVal(String val) {
		this.val = val == null ? null : val.trim();
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	public String getPids() {
		return pids;
	}

	public void setPids(String pids) {
		this.pids = pids;
	}

	public String getAvaliable() {
		return avaliable;
	}

	public void setAvaliable(String avaliable) {
		this.avaliable = avaliable;
	}
	
	

}
