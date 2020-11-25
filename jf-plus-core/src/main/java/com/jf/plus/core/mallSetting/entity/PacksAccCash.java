package com.jf.plus.core.mallSetting.entity;

import java.util.Date;

import com.alibaba.fastjson.annotation.JSONField;

import cn.iutils.sys.entity.DataEntity;

/**
 * 礼包卡券表
 *
 * @author Tng
 * @version 1.0
 */
public class PacksAccCash extends DataEntity<PacksAccCash> {

	private static final long serialVersionUID = 1L;

	private Long packsId;// 礼包ID
	private String batchNo;// 批次号
	private Long distOrgId;// 分发机构ID
	private Long distUserId;// 分发人ID
	private Integer delayCount;// 延期次数
	private String couponAccount;// 礼包券卡号
	private String couponCode;// 礼包券兑换码
	private Long bindOrder;// 绑定订单号
	private Long bindUser;// 绑定用户
	private Date bindDate;// 绑定日期
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	private Date validStartDate;// 有效期开始
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	private Date validEndDate;// 有效期截止
	private Date cashDate;// 兑换日期
	private Integer operStatus;// 券状态(0禁用，1启用)

	/**
	 * 自定义字段
	 */
	private Integer isValidity; // 是否有效期
	private Integer isCash; // 是否兑换
	private String mobile;// 绑定人手机号
	private String userName;// 绑定人用户名
	private String packsName; // 礼包名称
	private Long siteId; // 站点ID

	public PacksAccCash() {
		super();
	}

	public PacksAccCash(String id) {
		super(id);
	}

	public Long getPacksId() {
		return packsId;
	}

	public void setPacksId(Long packsId) {
		this.packsId = packsId;
	}

	public Long getDistOrgId() {
		return distOrgId;
	}

	public void setDistOrgId(Long distOrgId) {
		this.distOrgId = distOrgId;
	}

	public String getCouponAccount() {
		return couponAccount;
	}

	public void setCouponAccount(String couponAccount) {
		this.couponAccount = couponAccount == null ? null : couponAccount.trim();
	}

	public Long getBindUser() {
		return bindUser;
	}

	public void setBindUser(Long bindUser) {
		this.bindUser = bindUser;
	}

	public Long getBindOrder() {
		return bindOrder;
	}

	public void setBindOrder(Long bindOrder) {
		this.bindOrder = bindOrder;
	}

	public String getBatchNo() {
		return batchNo;
	}

	public void setBatchNo(String batchNo) {
		this.batchNo = batchNo == null ? null : batchNo.trim();
	}

	public Date getCashDate() {
		return cashDate;
	}

	public void setCashDate(Date cashDate) {
		this.cashDate = cashDate;
	}

	public Date getBindDate() {
		return bindDate;
	}

	public void setBindDate(Date bindDate) {
		this.bindDate = bindDate;
	}

	public Long getDistUserId() {
		return distUserId;
	}

	public void setDistUserId(Long distUserId) {
		this.distUserId = distUserId;
	}

	public Date getValidStartDate() {
		return validStartDate;
	}

	public void setValidStartDate(Date validStartDate) {
		this.validStartDate = validStartDate;
	}

	public Date getValidEndDate() {
		return validEndDate;
	}

	public void setValidEndDate(Date validEndDate) {
		this.validEndDate = validEndDate;
	}

	public Integer getIsValidity() {
		return isValidity;
	}

	public void setIsValidity(Integer isValidity) {
		this.isValidity = isValidity;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Integer getOperStatus() {
		return operStatus;
	}

	public void setOperStatus(Integer operStatus) {
		this.operStatus = operStatus;
	}

	public String getPacksName() {
		return packsName;
	}

	public void setPacksName(String packsName) {
		this.packsName = packsName;
	}

	public Long getSiteId() {
		return siteId;
	}

	public void setSiteId(Long siteId) {
		this.siteId = siteId;
	}

	public Integer getDelayCount() {
		return delayCount;
	}

	public void setDelayCount(Integer delayCount) {
		this.delayCount = delayCount;
	}

	public String getCouponCode() {
		return couponCode;
	}

	public void setCouponCode(String couponCode) {
		this.couponCode = couponCode;
	}

	public Integer getIsCash() {
		return isCash;
	}

	public void setIsCash(Integer isCash) {
		this.isCash = isCash;
	}

}
