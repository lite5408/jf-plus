package com.jf.plus.core.account.entity;

import java.util.Date;

import org.springframework.util.StringUtils;

import com.jf.plus.common.core.Constants;

import cn.iutils.sys.entity.DataEntity;

/**
 * 组织资金账户交易流水表
 *
 * @author Tng
 * @version 1.0
 */
public class OrgAccountRecharge extends DataEntity<OrgAccountRecharge> {

	private static final long serialVersionUID = 1L;

	private Long accountId;// 账户ID
	private Double amount;// 交易金额
	private String accountType;// 账户类型（采购账户purchase 积分账户points)
	private Integer dealType;// 交易类型 1:收入 2:支出
	private Long targetId;// 关联表ID(收入账户ID、订单ID)
	private String abstractType;// 摘要类型(额度分发,订单下单,订单退单,订单撤销,订单驳回,订单过期)
	private Date operTime;// 交易时间
	private String attachments; // 附件

	/**
	 * 自定义属性
	 */
	private String orgName; // 机构名称
	private String attachmentsUrl; // 附件链接
	private String parentOrgId;// 父级机构

	public OrgAccountRecharge() {
		super();
	}

	public OrgAccountRecharge(String id) {
		super(id);
	}

	public Long getAccountId() {
		return accountId;
	}

	public void setAccountId(Long accountId) {
		this.accountId = accountId;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public String getAccountType() {
		return accountType;
	}

	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}

	public Date getOperTime() {
		return operTime;
	}

	public void setOperTime(Date operTime) {
		this.operTime = operTime;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public Integer getDealType() {
		return dealType;
	}

	public void setDealType(Integer dealType) {
		this.dealType = dealType;
	}

	public Long getTargetId() {
		return targetId;
	}

	public void setTargetId(Long targetId) {
		this.targetId = targetId;
	}

	public String getAbstractType() {
		return abstractType;
	}

	public void setAbstractType(String abstractType) {
		this.abstractType = abstractType;
	}

	public String getAttachments() {
		return attachments;
	}

	public void setAttachments(String attachments) {
		this.attachments = attachments;
	}

	public String getAttachmentsUrl() {
		if (StringUtils.isEmpty(getAttachments()))
			return null;
		return Constants.URL_IMAGE + "/" + getAttachments();
	}

	public void setAttachmentsUrl(String attachmentsUrl) {
		this.attachmentsUrl = attachmentsUrl;
	}

	public String getParentOrgId() {
		return this.parentOrgId;
	}

	public void setParentOrgId(String parentOrgId) {
		this.parentOrgId = parentOrgId == null ? null : parentOrgId.trim();
	}

}
