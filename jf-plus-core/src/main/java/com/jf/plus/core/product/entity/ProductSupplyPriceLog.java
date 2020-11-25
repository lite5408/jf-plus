package com.jf.plus.core.product.entity;

import java.util.Date;

import cn.iutils.sys.entity.DataEntity;

/**
 *
 * @author Tng
 * @version 1.0
 */
public class ProductSupplyPriceLog extends DataEntity<ProductSupplyPriceLog>{

	private static final long serialVersionUID = 1L;

	private Long supplyId;//供应商Id
	private Integer source;//商品渠道来源（1京东，2供应商，3苏宁，4严选，5齐心）
	private String itemCode;//商品编号
	private Double markPrice;//市场价
	private Double supplyPrice;//最新供应价
	private Integer operStatus;//执行状态(1未执行，2已执行)
	private Date operTime;//执行时间

	public ProductSupplyPriceLog() {
		super();
	}
	public ProductSupplyPriceLog(String id){
		super(id);
	}

	public Long getSupplyId(){
		return supplyId;
	}

	public void setSupplyId(Long supplyId){
		this.supplyId = supplyId;
	}
	public Integer getSource(){
		return source;
	}

	public void setSource(Integer source){
		this.source = source;
	}
	public String getItemCode(){
		return itemCode;
	}

	public void setItemCode(String itemCode){
		this.itemCode = itemCode == null ? null : itemCode.trim();
	}
	public Double getMarkPrice(){
		return markPrice;
	}

	public void setMarkPrice(Double markPrice){
		this.markPrice = markPrice;
	}
	public Double getSupplyPrice(){
		return supplyPrice;
	}

	public void setSupplyPrice(Double supplyPrice){
		this.supplyPrice = supplyPrice;
	}
	public Integer getOperStatus(){
		return operStatus;
	}

	public void setOperStatus(Integer operStatus){
		this.operStatus = operStatus;
	}
	public Date getOperTime(){
		return operTime;
	}

	public void setOperTime(Date operTime){
		this.operTime = operTime;
	}
}
