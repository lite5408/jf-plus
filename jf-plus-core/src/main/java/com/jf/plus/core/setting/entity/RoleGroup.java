package com.jf.plus.core.setting.entity;

import cn.iutils.sys.entity.DataEntity;

/**
 * 角色组
 * 
 * @author Tng
 * @version 1.0
 */
public class RoleGroup extends DataEntity<RoleGroup> {

	private static final long serialVersionUID = 1L;

	private String groupName;// 角色组名称
	private String groupRoleIds;// 角色ids
	private Long orgId;// 所属机构
	private String available;// 是否可用：1可用 0禁用

	/** 自定义属性 */
	private String orgName;// 机构名称
	private String groupRoleNames;// 角色名称

	public RoleGroup() {
		super();
	}

	public RoleGroup(String id) {
		super(id);
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public String getGroupRoleIds() {
		return groupRoleIds;
	}

	public void setGroupRoleIds(String groupRoleIds) {
		this.groupRoleIds = groupRoleIds;
	}

	public Long getOrgId() {
		return orgId;
	}

	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}

	public String getAvailable() {
		return available;
	}

	public void setAvailable(String available) {
		this.available = available;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public String getGroupRoleNames() {
		return groupRoleNames;
	}

	public void setGroupRoleNames(String groupRoleNames) {
		this.groupRoleNames = groupRoleNames;
	}

}
