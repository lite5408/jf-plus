package com.jf.plus.core.account.entity;

import java.util.Date;

import com.alibaba.fastjson.annotation.JSONField;

import cn.iutils.sys.entity.DataEntity;

/**
 * 电子券卡号交易记录表
 * @author Tng
 * @version 1.0
 */
public class VoucherFlow extends DataEntity<VoucherFlow>{

	private static final long serialVersionUID = 1L;

	private Long accId;//券账户ID
	private Long userId;//用户id
	private Long targetId;//关联表ID 机构账户ID、订单ID、券ID
	private Integer accountType;//账户类型(1:集采账户 2:积分账户 3:分发账户 4:礼包账户)
	private Integer dealType;//交易类型  1：收入 2：支出
	private String abstractType; //摘要类型(额度分发,额度互拨,额度过期回退,订单下单,订单退单,订单撤销,订单驳回,订单过期)
	private Double tradeAmount;//交易金额(元)
	@JSONField(format = "yyyy-MM-dd")
	private Date tradeDate;//交易日期
	private Integer ratio;// 比例

	/**
	 * 自定义字段
	 */
	private String showName;
	private Integer type;
	private String mobile;

	public VoucherFlow() {
		super();
	}
	public VoucherFlow(String id){
		super(id);
	}

	public Integer getDealType(){
		return dealType;
	}

	public void setDealType(Integer dealType){
		this.dealType = dealType;
	}
	public Long getUserId(){
		return userId;
	}

	public void setUserId(Long userId){
		this.userId = userId;
	}
	public Integer getRatio() {
		return ratio;
	}
	public void setRatio(Integer ratio) {
		this.ratio = ratio;
	}
	public Long getAccId() {
		return accId;
	}
	public void setAccId(Long accId) {
		this.accId = accId;
	}
	public String getShowName() {
		return showName;
	}
	public void setShowName(String showName) {
		this.showName = showName;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public Long getTargetId() {
		return targetId;
	}
	public void setTargetId(Long targetId) {
		this.targetId = targetId;
	}
	public Integer getAccountType() {
		return accountType;
	}
	public void setAccountType(Integer accountType) {
		this.accountType = accountType;
	}
	public String getAbstractType() {
		return abstractType;
	}
	public void setAbstractType(String abstractType) {
		this.abstractType = abstractType;
	}
	public Double getTradeAmount() {
		return tradeAmount;
	}
	public void setTradeAmount(Double tradeAmount) {
		this.tradeAmount = tradeAmount;
	}
	public Date getTradeDate() {
		return tradeDate;
	}
	public void setTradeDate(Date tradeDate) {
		this.tradeDate = tradeDate;
	}
}
