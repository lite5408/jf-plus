package com.jf.plus.core.product.entity;

import java.util.Date;

import cn.iutils.sys.entity.DataEntity;

/**
* 商品状态变更表
* @author Tng
* @version 1.0
*/
public class ProductStatusLog extends DataEntity<ProductStatusLog>{

    private static final long serialVersionUID = 1L;

    private Long productId;//商品id
    private Integer operStatus;//操作状态
    private String operator;//操作人
    private Long operUserId;//操作用户id
    private Date operTime;//操作时间

    public ProductStatusLog() {
        super();
    }
    public ProductStatusLog(String id){
        super(id);
    }

    public Long getProductId(){
        return productId;
    }

    public void setProductId(Long productId){
        this.productId = productId;
    }
    public Integer getOperStatus(){
        return operStatus;
    }

    public void setOperStatus(Integer operStatus){
        this.operStatus = operStatus;
    }
    public String getOperator(){
        return operator;
    }

    public void setOperator(String operator){
        this.operator = operator == null ? null : operator.trim();
    }
    public Long getOperUserId(){
        return operUserId;
    }

    public void setOperUserId(Long operUserId){
        this.operUserId = operUserId;
    }
    public Date getOperTime(){
        return operTime;
    }

    public void setOperTime(Date operTime){
        this.operTime = operTime;
    }
}
