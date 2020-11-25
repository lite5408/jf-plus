package com.jf.plus.core.order.entity;

import cn.iutils.sys.entity.DataEntity;

/**
* 审核单明细
* @author Tng
* @version 1.0
*/
public class OrderAuditDetail extends DataEntity<OrderAuditDetail>{

    private static final long serialVersionUID = 1L;

    private Long orderAuditId;//
    private Long orderId;//订单id
    private String orderNo;//订单编号
    private Long itemId;//站点商品id
    private String itemName;//商品名称
    private Long supplyId;//供应商id
    private Double supplyPrice;//供应价
    private Double salePrice;//销售价
    private Double salePoints;//销售积分价
    private Integer saleNum;//销售数量
    private Double amount;//销售总价
    private Double amountPoints;//销售总积分价

    public OrderAuditDetail() {
        super();
    }
    public OrderAuditDetail(String id){
        super(id);
    }

    public Long getOrderAuditId(){
        return orderAuditId;
    }

    public void setOrderAuditId(Long orderAuditId){
        this.orderAuditId = orderAuditId;
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
    public Long getItemId(){
        return itemId;
    }

    public void setItemId(Long itemId){
        this.itemId = itemId;
    }
    public String getItemName(){
        return itemName;
    }

    public void setItemName(String itemName){
        this.itemName = itemName;
    }
    public Long getSupplyId(){
        return supplyId;
    }

    public void setSupplyId(Long supplyId){
        this.supplyId = supplyId;
    }
    public Double getSupplyPrice(){
        return supplyPrice;
    }

    public void setSupplyPrice(Double supplyPrice){
        this.supplyPrice = supplyPrice;
    }
    public Double getSalePrice(){
        return salePrice;
    }

    public void setSalePrice(Double salePrice){
        this.salePrice = salePrice;
    }
    public Double getSalePoints(){
        return salePoints;
    }

    public void setSalePoints(Double salePoints){
        this.salePoints = salePoints;
    }
    public Integer getSaleNum(){
        return saleNum;
    }

    public void setSaleNum(Integer saleNum){
        this.saleNum = saleNum;
    }
    public Double getAmount(){
        return amount;
    }

    public void setAmount(Double amount){
        this.amount = amount;
    }
    public Double getAmountPoints(){
        return amountPoints;
    }

    public void setAmountPoints(Double amountPoints){
        this.amountPoints = amountPoints;
    }
}
