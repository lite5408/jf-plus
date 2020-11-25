package com.jf.plus.core.order.entity;

import java.util.Date;

import cn.iutils.sys.entity.DataEntity;

/**
 * 订单分配表
 * 
 * @author Tng
 * @version 1.0
 */
public class OrderDetailDist extends DataEntity<OrderDetailDist> {

	private static final long serialVersionUID = 1L;

	private String orderDetailId;// 明细ID
	private String isDist;// 是否分发
	private Integer distStock;// 分发总库存
	private Integer distNum;// 分发数量
	private String distOperator;// 分发操作用户
	private Date distDate;// 分发时间
	private String batchNo;// 批次号

	public OrderDetailDist() {
		super();
	}

	public OrderDetailDist(String id) {
		super(id);
	}

	public String getIsDist() {
		return isDist;
	}

	public void setIsDist(String isDist) {
		this.isDist = isDist == null ? null : isDist.trim();
	}

	public Integer getDistStock() {
		return distStock;
	}

	public void setDistStock(Integer distStock) {
		this.distStock = distStock;
	}

	public Integer getDistNum() {
		return distNum;
	}

	public void setDistNum(Integer distNum) {
		this.distNum = distNum;
	}

	public String getDistOperator() {
		return distOperator;
	}

	public void setDistOperator(String distOperator) {
		this.distOperator = distOperator == null ? null : distOperator.trim();
	}

	public Date getDistDate() {
		return distDate;
	}

	public void setDistDate(Date distDate) {
		this.distDate = distDate;
	}

	public String getBatchNo() {
		return batchNo;
	}

	public void setBatchNo(String batchNo) {
		this.batchNo = batchNo == null ? null : batchNo.trim();
	}

	public String getOrderDetailId() {
		return orderDetailId;
	}

	public void setOrderDetailId(String orderDetailId) {
		this.orderDetailId = orderDetailId;
	}

	
	
}
