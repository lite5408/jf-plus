package com.jf.plus.core.account.entity;

import java.util.Date;

import com.alibaba.fastjson.annotation.JSONField;

import cn.iutils.sys.entity.DataEntity;

/**
 * 电子券卡号信息表
 * @author Tng
 * @version 1.0
 */
public class VoucherAccCash extends DataEntity<VoucherAccCash>{

	private static final long serialVersionUID = 1L;

	private Long voucherId;//券id
	private String batchNo;//批次号
	private Integer accountType;// 账户类型 (1:集采账户 2:积分账户 3:分发账户)
	private Long distOrgId;//发券机构id
	private Long distUserId;//发放人id
	private Integer distSource;//分发来源(1:机构分发 2:站点管理员分发 3:业务经理分发)
	private Integer delayCount; //延期次数
	private String couponAccount;// 电子券卡号
	private String couponCode;//电子券兑换码
	private Long bindUser;//绑定用户
	private Date bindDate;//绑定日期
	private Double blance;//余额面值（元）
	private Double totalBlance;//分发面值（元）
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	private Date validStartDate;//有效开始日期
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	private Date validEndDate;//有效截止日期
	private Integer operStatus;//券状态(0禁用，1启用)

	/**
	 * 自定义字段
	 */
	private Integer isValidity; //是否有效期
	private String userName;//绑定用户名
	private String mobile;//绑定用户手机号
	private String bindOrder;//最新绑定订单
	private Long siteId; // 站点ID
	private Integer ratio;//积分比例
	private Integer hasBlance; // 是否额度充足

	public VoucherAccCash() {
		super();
	}
	public VoucherAccCash(String id){
		super(id);
	}

	public Long getVoucherId(){
		return voucherId;
	}

	public void setVoucherId(Long voucherId){
		this.voucherId = voucherId;
	}
	public Long getBindUser(){
		return bindUser;
	}

	public void setBindUser(Long bindUser){
		this.bindUser = bindUser;
	}
	public Date getBindDate(){
		return bindDate;
	}

	public void setBindDate(Date bindDate){
		this.bindDate = bindDate;
	}
	public Double getBlance(){
		return blance;
	}

	public void setBlance(Double blance){
		this.blance = blance;
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
	public Long getDistOrgId() {
		return distOrgId;
	}
	public void setDistOrgId(Long distOrgId) {
		this.distOrgId = distOrgId;
	}
	public String getBatchNo() {
		return batchNo;
	}
	public void setBatchNo(String batchNo) {
		this.batchNo = batchNo;
	}
	public Long getDistUserId() {
		return distUserId;
	}
	public void setDistUserId(Long distUserId) {
		this.distUserId = distUserId;
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
	public String getBindOrder() {
		return bindOrder;
	}
	public void setBindOrder(String bindOrder) {
		this.bindOrder = bindOrder;
	}
	public Integer getOperStatus() {
		return operStatus;
	}
	public void setOperStatus(Integer operStatus) {
		this.operStatus = operStatus;
	}
	public Long getSiteId() {
		return siteId;
	}
	public void setSiteId(Long siteId) {
		this.siteId = siteId;
	}
	public Integer getAccountType() {
		return accountType;
	}
	public void setAccountType(Integer accountType) {
		this.accountType = accountType;
	}
	public Integer getDelayCount() {
		return delayCount;
	}
	public void setDelayCount(Integer delayCount) {
		this.delayCount = delayCount;
	}
	public String getCouponAccount() {
		return couponAccount;
	}
	public void setCouponAccount(String couponAccount) {
		this.couponAccount = couponAccount;
	}
	public String getCouponCode() {
		return couponCode;
	}
	public void setCouponCode(String couponCode) {
		this.couponCode = couponCode;
	}
	public Double getTotalBlance() {
		return totalBlance;
	}
	public void setTotalBlance(Double totalBlance) {
		this.totalBlance = totalBlance;
	}
	public Integer getDistSource() {
		return distSource;
	}
	public void setDistSource(Integer distSource) {
		this.distSource = distSource;
	}
	public Integer getRatio() {
		return ratio;
	}
	public void setRatio(Integer ratio) {
		this.ratio = ratio;
	}
	public Integer getHasBlance() {
		return hasBlance;
	}
	public void setHasBlance(Integer hasBlance) {
		this.hasBlance = hasBlance;
	}


}
