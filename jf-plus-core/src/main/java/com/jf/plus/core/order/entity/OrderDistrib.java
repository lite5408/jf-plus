package com.jf.plus.core.order.entity;

import java.util.Date;

import cn.iutils.sys.entity.DataEntity;

/**
 *
 * @author Tng
 * @version 1.0
 */
public class OrderDistrib extends DataEntity<OrderDistrib>{

	private static final long serialVersionUID = 1L;

	private Integer type;//
	private Long orderId;//
	private String delivery;//送货员
	private String deliveryMobile;//送货员手机
	private String express;//快递公司
	private String expressNo;//快递单号
	private Date deliveryTime;//发货时间

	//自定义属性
	private String orderNo; // 订单号

	public OrderDistrib() {
		super();
	}
	public OrderDistrib(String id){
		super(id);
	}

	public Integer getType(){
		return type;
	}

	public void setType(Integer type){
		this.type = type;
	}
	public Long getOrderId(){
		return orderId;
	}

	public void setOrderId(Long orderId){
		this.orderId = orderId;
	}
	public String getDelivery(){
		return delivery;
	}

	public void setDelivery(String delivery){
		this.delivery = delivery;
	}
	public String getDeliveryMobile(){
		return deliveryMobile;
	}

	public void setDeliveryMobile(String deliveryMobile){
		this.deliveryMobile = deliveryMobile;
	}
	public String getExpress(){
		return express;
	}

	public void setExpress(String express){
		this.express = express;
	}
	public String getExpressNo(){
		return expressNo;
	}

	public void setExpressNo(String expressNo){
		this.expressNo = expressNo;
	}
	public Date getDeliveryTime(){
		return deliveryTime;
	}

	public void setDeliveryTime(Date deliveryTime){
		this.deliveryTime = deliveryTime;
	}
	public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
}
