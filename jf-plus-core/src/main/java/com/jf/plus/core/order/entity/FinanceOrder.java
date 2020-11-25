package com.jf.plus.core.order.entity;

import java.util.Date;

import cn.iutils.sys.entity.DataEntity;

/**
* 对账订单明细表
* @author Tng
* @version 1.0
*/
public class FinanceOrder extends DataEntity<FinanceOrder>{

    private static final long serialVersionUID = 1L;

    private Long bookId;//对账单表id
    private String orderNo;//订单号
    private Double payAmount;//订单金额
    private String outTradeNo;//渠道单号
    private Integer orderType;//订单类型（1，订单 2，明细）
    private String itemCode;//商品编码（明细才记录）
    private Double tradeAmount;//渠道支付金额
    private Integer operStatus;//业务状态（0：未结算 1：已结算 2：取消结算）
    private Integer matchStatus;//订单匹配状态（0：新导入 1：已匹配 2：未匹配）
    private String matchRemarks;//匹配备注
    private String auditReason;//采购事由
    
    /**
     * 自定义属性
     */
    private Integer supplyId;
    private String supplyName;
    private String startTime;
	private String endTime;
	private Order order; 
	private String batchNo;
    

    public FinanceOrder() {
        super();
    }
    public FinanceOrder(String id){
        super(id);
    }

    public Long getBookId(){
        return bookId;
    }

    public void setBookId(Long bookId){
        this.bookId = bookId;
    }
    public String getOrderNo(){
        return orderNo;
    }

    public void setOrderNo(String orderNo){
        this.orderNo = orderNo == null ? null : orderNo.trim();
    }
    public Double getPayAmount(){
        return payAmount;
    }

    public void setPayAmount(Double payAmount){
        this.payAmount = payAmount;
    }
    public String getOutTradeNo(){
        return outTradeNo;
    }

    public void setOutTradeNo(String outTradeNo){
        this.outTradeNo = outTradeNo == null ? null : outTradeNo.trim();
    }
    public String getItemCode(){
        return itemCode;
    }

    public void setItemCode(String itemCode){
        this.itemCode = itemCode == null ? null : itemCode.trim();
    }
    public Double getTradeAmount(){
        return tradeAmount;
    }

    public void setTradeAmount(Double tradeAmount){
        this.tradeAmount = tradeAmount;
    }
    public Integer getOperStatus(){
        return operStatus;
    }

    public void setOperStatus(Integer operStatus){
        this.operStatus = operStatus;
    }
	public Integer getOrderType() {
		return orderType;
	}
	public void setOrderType(Integer orderType) {
		this.orderType = orderType;
	}
	public Integer getSupplyId() {
		return supplyId;
	}
	public void setSupplyId(Integer supplyId) {
		this.supplyId = supplyId;
	}
	public String getSupplyName() {
		return supplyName;
	}
	public void setSupplyName(String supplyName) {
		this.supplyName = supplyName == null ? null : supplyName.trim();
	}
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime == null ? null : startTime.trim();
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime == null ? null : endTime.trim();
	}
	public Order getOrder() {
		return order;
	}
	public void setOrder(Order order) {
		this.order = order;
	}
	public Integer getMatchStatus() {
		return matchStatus;
	}
	public void setMatchStatus(Integer matchStatus) {
		this.matchStatus = matchStatus;
	}
	public String getMatchRemarks() {
		return matchRemarks;
	}
	public void setMatchRemarks(String matchRemarks) {
		this.matchRemarks = matchRemarks == null ? null : matchRemarks.trim();
	}
	public String getBatchNo() {
		return batchNo;
	}
	public void setBatchNo(String batchNo) {
		this.batchNo = batchNo == null ? null : batchNo.trim();
	}
	public String getAuditReason() {
		return auditReason;
	}
	public void setAuditReason(String auditReason) {
		this.auditReason = auditReason == null ? null : auditReason.trim();
	}
    
}
