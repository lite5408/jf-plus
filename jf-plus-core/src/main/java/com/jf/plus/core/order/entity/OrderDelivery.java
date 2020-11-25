package com.jf.plus.core.order.entity;

import java.util.Date;
import java.util.List;

import com.alibaba.fastjson.annotation.JSONField;

import cn.iutils.sys.entity.DataEntity;

/**
* 订单发货表
* @author Tng
* @version 1.0
*/
public class OrderDelivery extends DataEntity<OrderDelivery>{

    private static final long serialVersionUID = 1L;

    private String orderNo;//订单编号
    private Integer deliveryType;//发货类型（1：部分发货 2：全部发货）
    private Date deliveryDate;//发货时间
    @JSONField(serialize = false)
    private String deliveryOperator;//发货员
    @JSONField(serialize = false)
    private String deliveryStore;//仓库
    private Integer deliveryExpressType;//发货方式：1：快递 2：自配
    private String deliveryExpress;//快递公司/配送员
    private String deliveryExpressNo;//快递单号/配送员电话
    private Integer operStatus;//导出状态：0未导出 1已导出
    @JSONField(serialize = false)
    private String ckNo;//出库编号
    private Integer isConfirm;//是否确认收货
    @JSONField(serialize = false)
    private String confirmOperator;//确认收货人
    @JSONField(serialize = false)
    private Date confirmDate;//确认收货日期
    
    /**
     * 自定义属性
     */
    
    private List<OrderDeliveryDetail> orderDeliveryDetails;
    private Long userId;//下单人
    private Integer deliveryNum;
    private String orgName;
    

    public OrderDelivery() {
        super();
    }
    public OrderDelivery(String id){
        super(id);
    }

    public String getOrderNo(){
        return orderNo;
    }

    public void setOrderNo(String orderNo){
        this.orderNo = orderNo == null ? null : orderNo.trim();
    }
    public Integer getDeliveryType(){
        return deliveryType;
    }

    public void setDeliveryType(Integer deliveryType){
        this.deliveryType = deliveryType;
    }
    public Date getDeliveryDate(){
        return deliveryDate;
    }

    public void setDeliveryDate(Date deliveryDate){
        this.deliveryDate = deliveryDate;
    }
    public String getDeliveryOperator(){
        return deliveryOperator;
    }

    public void setDeliveryOperator(String deliveryOperator){
        this.deliveryOperator = deliveryOperator == null ? null : deliveryOperator.trim();
    }
    public String getDeliveryStore(){
        return deliveryStore;
    }

    public void setDeliveryStore(String deliveryStore){
        this.deliveryStore = deliveryStore == null ? null : deliveryStore.trim();
    }
    public Integer getDeliveryExpressType(){
        return deliveryExpressType;
    }

    public void setDeliveryExpressType(Integer deliveryExpressType){
        this.deliveryExpressType = deliveryExpressType;
    }
    public String getDeliveryExpress(){
        return deliveryExpress;
    }

    public void setDeliveryExpress(String deliveryExpress){
        this.deliveryExpress = deliveryExpress == null ? null : deliveryExpress.trim();
    }
    public String getDeliveryExpressNo(){
        return deliveryExpressNo;
    }

    public void setDeliveryExpressNo(String deliveryExpressNo){
        this.deliveryExpressNo = deliveryExpressNo == null ? null : deliveryExpressNo.trim();
    }
    public Integer getOperStatus(){
        return operStatus;
    }

    public void setOperStatus(Integer operStatus){
        this.operStatus = operStatus;
    }
	public String getCkNo() {
		return ckNo;
	}
	public void setCkNo(String ckNo) {
		this.ckNo = ckNo;
	}
	public List<OrderDeliveryDetail> getOrderDeliveryDetails() {
		return orderDeliveryDetails;
	}
	public void setOrderDeliveryDetails(List<OrderDeliveryDetail> orderDeliveryDetails) {
		this.orderDeliveryDetails = orderDeliveryDetails;
	}
	public Integer getIsConfirm() {
		return isConfirm;
	}
	public void setIsConfirm(Integer isConfirm) {
		this.isConfirm = isConfirm;
	}
	public String getConfirmOperator() {
		return confirmOperator;
	}
	public void setConfirmOperator(String confirmOperator) {
		this.confirmOperator = confirmOperator;
	}
	public Date getConfirmDate() {
		return confirmDate;
	}
	public void setConfirmDate(Date confirmDate) {
		this.confirmDate = confirmDate;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public Integer getDeliveryNum() {
		return deliveryNum;
	}
	public void setDeliveryNum(Integer deliveryNum) {
		this.deliveryNum = deliveryNum;
	}
	public String getOrgName() {
		return orgName;
	}
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		OrderDelivery other = (OrderDelivery) obj;
		if (deliveryDate == null) {
			if (other.deliveryDate != null)
				return false;
		} else if (!deliveryDate.equals(other.deliveryDate))
			return false;
		if (deliveryExpressNo == null) {
			if (other.deliveryExpressNo != null)
				return false;
		} else if (!deliveryExpressNo.equals(other.deliveryExpressNo))
			return false;
		if (orderNo == null) {
			if (other.orderNo != null)
				return false;
		} else if (!orderNo.equals(other.orderNo))
			return false;
		return true;
	}
	
	
}
