package com.jf.plus.core.setting.entity;

import java.util.Date;

import cn.iutils.sys.entity.DataEntity;

/**
 * 审批设置
 * 
 * @author Tng
 * @version 1.0
 */
public class AuditSettings extends DataEntity<AuditSettings> {

	private static final long serialVersionUID = 1L;

	private Long orgId;// 组织id
	private Integer isAudit;// 是否审核
	private Date auditStartTime;// 审批开始时间
	private Date auditEndTime;// 审批结束时间

	/**
	 * 自定义属性
	 */
	private String auditStartDate;
	private String auditEndDate;

	public AuditSettings() {
		super();
	}

	public AuditSettings(String id) {
		super(id);
	}

	public Long getOrgId() {
		return orgId;
	}

	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}

	public Date getAuditStartTime() {
		return auditStartTime;
	}

	public void setAuditStartTime(Date auditStartTime) {
		this.auditStartTime = auditStartTime;
	}

	public Date getAuditEndTime() {
		return auditEndTime;
	}

	public void setAuditEndTime(Date auditEndTime) {
		this.auditEndTime = auditEndTime;
	}

	public Integer getIsAudit() {
		return isAudit;
	}

	public void setIsAudit(Integer isAudit) {
		this.isAudit = isAudit;
	}

	public String getAuditStartDate() {
		return auditStartDate;
	}

	public void setAuditStartDate(String auditStartDate) {
		this.auditStartDate = auditStartDate;
	}

	public String getAuditEndDate() {
		return auditEndDate;
	}

	public void setAuditEndDate(String auditEndDate) {
		this.auditEndDate = auditEndDate;
	}

}
