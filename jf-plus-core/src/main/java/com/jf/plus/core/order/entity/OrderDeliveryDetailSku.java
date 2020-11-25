package com.jf.plus.core.order.entity;

import cn.iutils.sys.entity.DataEntity;

/**
* 订单发货SKU表
* @author Tng
* @version 1.0
*/
public class OrderDeliveryDetailSku extends DataEntity<OrderDeliveryDetailSku>{

    private static final long serialVersionUID = 1L;

    private String orderNo;//订单编号
    private Long deliveryId;//订单发货id
    private Long deliveryDetailId;//站点商品ID
    private Integer deliveryNum;//发货数量
    private String specColorText;
    private String specSizeText;

    public OrderDeliveryDetailSku() {
        super();
    }
    public OrderDeliveryDetailSku(String id){
        super(id);
    }

    public String getOrderNo(){
        return orderNo;
    }

    public void setOrderNo(String orderNo){
        this.orderNo = orderNo == null ? null : orderNo.trim();
    }
    public Long getDeliveryId(){
        return deliveryId;
    }

    public void setDeliveryId(Long deliveryId){
        this.deliveryId = deliveryId;
    }
    public Long getDeliveryDetailId(){
        return deliveryDetailId;
    }

    public void setDeliveryDetailId(Long deliveryDetailId){
        this.deliveryDetailId = deliveryDetailId;
    }
    public Integer getDeliveryNum(){
        return deliveryNum;
    }

    public void setDeliveryNum(Integer deliveryNum){
        this.deliveryNum = deliveryNum;
    }
	public String getSpecColorText() {
		return specColorText;
	}
	public void setSpecColorText(String specColorText) {
		this.specColorText = specColorText;
	}
	public String getSpecSizeText() {
		return specSizeText;
	}
	public void setSpecSizeText(String specSizeText) {
		this.specSizeText = specSizeText;
	}
    
    
}
