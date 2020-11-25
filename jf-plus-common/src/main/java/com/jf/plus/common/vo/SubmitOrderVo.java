package com.jf.plus.common.vo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 预占库存订单VO
 *
 * @author Tng
 * @version 1.0
 *
 */
public class SubmitOrderVo extends RequestVo implements Serializable {
	/**
	 *
	 */
	private static final long serialVersionUID = -8049793925719572002L;

	private String orderNo;// 订单编号
	private Integer orderType;// 订单类型
	private Long orgId;// 组织id
	private Long siteId;// 站点id
	private Long supplyId;// 供应商id
	private Long userId;// 用户id
	private Date cashtime;// 兑换时间
	private Integer source;// 订单来源(1:京东 2供应商)

	private String userAddressId;// 收货地址id
	private String payWay;// 支付方式
	private List<CartItem> cartItemList;// 选择的商品

	private String remark; // 备注

	private String paymentType;// 支付方式

	private Long packsAccId; // 礼包id

	/** 电商平台接口返回的信息 **/
	private OutOrderVo outOrderVo;

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public Integer getOrderType() {
		return orderType;
	}

	public void setOrderType(Integer orderType) {
		this.orderType = orderType;
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

	public Long getSupplyId() {
		return supplyId;
	}

	public void setSupplyId(Long supplyId) {
		this.supplyId = supplyId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Date getCashtime() {
		return cashtime;
	}

	public void setCashtime(Date cashtime) {
		this.cashtime = cashtime;
	}

	public Integer getSource() {
		return source;
	}

	public void setSource(Integer source) {
		this.source = source;
	}

	public String getUserAddressId() {
		return userAddressId;
	}

	public void setUserAddressId(String userAddressId) {
		this.userAddressId = userAddressId;
	}

	public String getPayWay() {
		return payWay;
	}

	public void setPayWay(String payWay) {
		this.payWay = payWay;
	}

	public List<CartItem> getCartItemList() {
		return cartItemList;
	}

	public void setCartItemList(List<CartItem> cartItemList) {
		this.cartItemList = cartItemList;
	}

	public OutOrderVo getOutOrderVo() {
		return outOrderVo;
	}

	public void setOutOrderVo(OutOrderVo outOrderVo) {
		this.outOrderVo = outOrderVo;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getPaymentType() {
		return paymentType;
	}

	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}

	public Long getPacksAccId() {
		return packsAccId;
	}

	public void setPacksAccId(Long packsAccId) {
		this.packsAccId = packsAccId;
	}
}
