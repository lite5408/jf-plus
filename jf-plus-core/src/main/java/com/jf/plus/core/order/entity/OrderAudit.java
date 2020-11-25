package com.jf.plus.core.order.entity;

import java.util.Date;

import com.alibaba.fastjson.annotation.JSONField;

import cn.iutils.sys.entity.DataEntity;

/**
 * 订单审核表
 *
 * @author Tng
 * @version 1.0
 */
public class OrderAudit extends DataEntity<OrderAudit> {

	private static final long serialVersionUID = 1L;

	private String orderNo;// 订单号
	private Long orgId;// 组织id
	private Long siteId;// 站点id
	private Integer totalNum;// 商品数量
	private Double totalAmount;// 订单总额
	private Double totalPoints;// 订单总积分
	private Double payAmount;// 支付总额（订单总额+运费-优惠）
	private Double payPoints;// 支付总积分
	private Double freight;// 运费
	private Long userId;// 用户id
	private String userNo;// 用户工号
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	private Date expireDate;// 审核单有效期
	private Integer auditStatus;// 审核状态(1:通过 2：未通过）
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	private Date auditDate;// 审核时间
	private String taskProcessId;// 工作流id
	private String proReasons; // 采购事由
	private String auditReasons; // 审批事由

	// 自定义字段
	private Long parentOrgId; // 上级机构ID
	private String userName;// 用户姓名
	private String orgNo;// 机构No
	private String orgName;// 机构名称
	private String receiver;// 收货人
	private String receiverPhone;// 收货人手机号码
	private String address;// 收货人地址
	private String firstProductId;// 第一个商品id
	private String firstItemName;// 第一个商品名称
	private Integer detailCount;// 商品笔数

	public OrderAudit() {
		super();
	}

	public OrderAudit(String id) {
		super(id);
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public Long getOrgId() {
		return orgId;
	}

	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}

	public Long getSiteId() {
		return siteId;
	}

	public void setSiteId(Long siteId) {
		this.siteId = siteId;
	}

	public Integer getTotalNum() {
		return totalNum;
	}

	public void setTotalNum(Integer totalNum) {
		this.totalNum = totalNum;
	}

	public Double getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(Double totalAmount) {
		this.totalAmount = totalAmount;
	}

	public Double getTotalPoints() {
		return totalPoints;
	}

	public void setTotalPoints(Double totalPoints) {
		this.totalPoints = totalPoints;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getUserNo() {
		return userNo;
	}

	public void setUserNo(String userNo) {
		this.userNo = userNo;
	}

	public Date getExpireDate() {
		return expireDate;
	}

	public void setExpireDate(Date expireDate) {
		this.expireDate = expireDate;
	}

	public Integer getAuditStatus() {
		return auditStatus;
	}

	public void setAuditStatus(Integer auditStatus) {
		this.auditStatus = auditStatus;
	}

	public Date getAuditDate() {
		return auditDate;
	}

	public void setAuditDate(Date auditDate) {
		this.auditDate = auditDate;
	}

	public String getTaskProcessId() {
		return taskProcessId;
	}

	public void setTaskProcessId(String taskProcessId) {
		this.taskProcessId = taskProcessId;
	}

	public Long getParentOrgId() {
		return parentOrgId;
	}

	public void setParentOrgId(Long parentOrgId) {
		this.parentOrgId = parentOrgId;
	}

	public String getProReasons() {
		return proReasons;
	}

	public void setProReasons(String proReasons) {
		this.proReasons = proReasons;
	}

	public String getAuditReasons() {
		return auditReasons;
	}

	public void setAuditReasons(String auditReasons) {
		this.auditReasons = auditReasons;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public String getFirstProductId() {
		return firstProductId;
	}

	public void setFirstProductId(String firstProductId) {
		this.firstProductId = firstProductId;
	}

	public String getFirstItemName() {
		return firstItemName;
	}

	public void setFirstItemName(String firstItemName) {
		this.firstItemName = firstItemName;
	}
	
	public String getReceiver() {
		return receiver;
	}

	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}

	public Integer getDetailCount() {
		return detailCount;
	}

	public void setDetailCount(Integer detailCount) {
		this.detailCount = detailCount;
	}

	public Double getPayAmount() {
		return payAmount;
	}

	public void setPayAmount(Double payAmount) {
		this.payAmount = payAmount;
	}

	public Double getPayPoints() {
		return payPoints;
	}

	public void setPayPoints(Double payPoints) {
		this.payPoints = payPoints;
	}

	public Double getFreight() {
		return freight;
	}

	public void setFreight(Double freight) {
		this.freight = freight;
	}

	public String getOrgNo() {
		return orgNo;
	}

	public void setOrgNo(String orgNo) {
		this.orgNo = orgNo;
	}

	public String getReceiverPhone() {
		return receiverPhone;
	}

	public void setReceiverPhone(String receiverPhone) {
		this.receiverPhone = receiverPhone;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	
	
}
