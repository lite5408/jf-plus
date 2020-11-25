package com.jf.plus.core.order.entity;

import cn.iutils.sys.entity.DataEntity;

/**
* 订单支付记录
* @author Tng
* @version 1.0
*/
public class OrderRecords extends DataEntity<OrderRecords>{

    private static final long serialVersionUID = 1L;

    private Long orderId;//订单id
    private String orderNo;//订单编号
    private Double payAmount;//支付金额
    private Integer payMode;//支付方式（1积分）
    private String tradeNo;//支付流水号
    private String tradeTime;//支付时间

    public OrderRecords() {
        super();
    }
    public OrderRecords(String id){
        super(id);
    }

    public Long getOrderId(){
        return orderId;
    }

    public void setOrderId(Long orderId){
        this.orderId = orderId;
    }
    public String getOrderNo(){
        return orderNo;
    }

    public void setOrderNo(String orderNo){
        this.orderNo = orderNo;
    }
    public Double getPayAmount(){
        return payAmount;
    }

    public void setPayAmount(Double payAmount){
        this.payAmount = payAmount;
    }
    public Integer getPayMode(){
        return payMode;
    }

    public void setPayMode(Integer payMode){
        this.payMode = payMode;
    }
    public String getTradeNo(){
        return tradeNo;
    }

    public void setTradeNo(String tradeNo){
        this.tradeNo = tradeNo;
    }
    public String getTradeTime(){
        return tradeTime;
    }

    public void setTradeTime(String tradeTime){
        this.tradeTime = tradeTime;
    }
}
