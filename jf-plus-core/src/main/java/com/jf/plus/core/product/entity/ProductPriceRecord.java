package com.jf.plus.core.product.entity;

import java.util.Date;

import cn.iutils.sys.entity.DataEntity;

/**
* 
* @author Tng
* @version 1.0
*/
public class ProductPriceRecord extends DataEntity<ProductPriceRecord>{

    private static final long serialVersionUID = 1L;

    private Long productId;//商品Id
    private Long ruleRecordId;//商城规则配置记录Id(价格变更记录0)
    private Integer recordSource;//记录变更来源（1.商城配置修改2.接口供应价修改）
    private Double preSupplyPrice;//修改前供应价
    private Double sufSupplyPrice;//修改后供应价
    private Integer operStatus;//执行状态(1未执行，2已执行)
    private Date operTime;//执行时间

    public ProductPriceRecord() {
        super();
    }
    public ProductPriceRecord(String id){
        super(id);
    }

    public Long getProductId(){
        return productId;
    }

    public void setProductId(Long productId){
        this.productId = productId;
    }
    public Long getRuleRecordId(){
        return ruleRecordId;
    }

    public void setRuleRecordId(Long ruleRecordId){
        this.ruleRecordId = ruleRecordId;
    }
    public Integer getRecordSource(){
        return recordSource;
    }

    public void setRecordSource(Integer recordSource){
        this.recordSource = recordSource;
    }
    public Double getPreSupplyPrice(){
        return preSupplyPrice;
    }

    public void setPreSupplyPrice(Double preSupplyPrice){
        this.preSupplyPrice = preSupplyPrice;
    }
    public Double getSufSupplyPrice(){
        return sufSupplyPrice;
    }

    public void setSufSupplyPrice(Double sufSupplyPrice){
        this.sufSupplyPrice = sufSupplyPrice;
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
