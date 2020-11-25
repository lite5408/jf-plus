package com.jf.plus.core.account.entity;

import cn.iutils.sys.entity.DataEntity;

/**
 * 组织资金账户表
 * 
 * @author Tng
 * @version 1.0
 */
public class OrgAccount extends DataEntity<OrgAccount> {

	private static final long serialVersionUID = 1L;

	private Long orgId;// 组织id
	private Double purchaseBlance;// 采购资金余额
	private Double purchaseTotalAmount;// 采购资金总额度
	private Double pointsBlance;// 采购资金余额
	private Double pointsTotalAmount;// 采购资金总额度

	/** 自定义属性 */
	private Long organizationId;// 组织机构表id
	private String orgName;// 组织架构名称
	private String orgNo;// 组织编号
	private Integer orgType;// 组织类型
	private String attachments; // 附件
	
	private Long parentOrgId;
	private String parentOrgName;
	
	private Double hasFhAmount;//已发货额度
	private Double notFhAmount;//未发货额度

	public OrgAccount() {
		super();
	}

	public OrgAccount(String id) {
		super(id);
	}

	public Long getOrgId() {
		return orgId;
	}

	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}

	public Double getPurchaseBlance() {
		return purchaseBlance;
	}

	public void setPurchaseBlance(Double purchaseBlance) {
		this.purchaseBlance = purchaseBlance;
	}

	public Double getPurchaseTotalAmount() {
		return purchaseTotalAmount;
	}

	public void setPurchaseTotalAmount(Double purchaseTotalAmount) {
		this.purchaseTotalAmount = purchaseTotalAmount;
	}

	public Double getPointsBlance() {
		return pointsBlance;
	}

	public void setPointsBlance(Double pointsBlance) {
		this.pointsBlance = pointsBlance;
	}

	public Double getPointsTotalAmount() {
		return pointsTotalAmount;
	}

	public void setPointsTotalAmount(Double pointsTotalAmount) {
		this.pointsTotalAmount = pointsTotalAmount;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public String getOrgNo() {
		return orgNo;
	}

	public void setOrgNo(String orgNo) {
		this.orgNo = orgNo;
	}

	public Integer getOrgType() {
		return orgType;
	}

	public void setOrgType(Integer orgType) {
		this.orgType = orgType;
	}

	public Long getOrganizationId() {
		return organizationId;
	}

	public void setOrganizationId(Long organizationId) {
		this.organizationId = organizationId;
	}

	public String getAttachments() {
		return attachments;
	}

	public void setAttachments(String attachments) {
		this.attachments = attachments;
	}

	public Long getParentOrgId() {
		return parentOrgId;
	}

	public void setParentOrgId(Long parentOrgId) {
		this.parentOrgId = parentOrgId;
	}

	public String getParentOrgName() {
		return parentOrgName;
	}

	public void setParentOrgName(String parentOrgName) {
		this.parentOrgName = parentOrgName;
	}

	public Double getHasFhAmount() {
		return hasFhAmount;
	}

	public void setHasFhAmount(Double hasFhAmount) {
		this.hasFhAmount = hasFhAmount;
	}

	public Double getNotFhAmount() {
		return notFhAmount;
	}

	public void setNotFhAmount(Double notFhAmount) {
		this.notFhAmount = notFhAmount;
	}
	
	

}
