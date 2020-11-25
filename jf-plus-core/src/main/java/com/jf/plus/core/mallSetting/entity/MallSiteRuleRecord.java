package com.jf.plus.core.mallSetting.entity;

import java.util.Date;

import cn.iutils.sys.entity.DataEntity;

/**
* 
* @author Tng
* @version 1.0
*/
public class MallSiteRuleRecord extends DataEntity<MallSiteRuleRecord>{

    private static final long serialVersionUID = 1L;

    private Long orgId;//机构Id
    private Integer productSource;//商品渠道(1:京东  2供应商  3苏宁 4严选 5齐心)
    private Integer recordType;//本次记录的类型(1新增记录，2修改前记录，3修改后记录)
    private String batchNo;//批次号
    private Integer distributeType;//分销类型(1:按折扣 2:按固定值)
    private Double distributeValue;//分销值
    private Integer allowExceedMarketPrice;//是否允许超出市场价
    private Double exceedRatio;//超出市场价比例
    private Integer operStatus;//执行状态(0不执行，1未执行，2已执行)
    private Date operTime;//执行时间

    public MallSiteRuleRecord() {
        super();
    }
    public MallSiteRuleRecord(String id){
        super(id);
    }

    public Long getOrgId(){
        return orgId;
    }

    public void setOrgId(Long orgId){
        this.orgId = orgId;
    }
    public Integer getProductSource(){
        return productSource;
    }

    public void setProductSource(Integer productSource){
        this.productSource = productSource;
    }
    public Integer getRecordType(){
        return recordType;
    }

    public void setRecordType(Integer recordType){
        this.recordType = recordType;
    }
    public String getBatchNo(){
        return batchNo;
    }

    public void setBatchNo(String batchNo){
        this.batchNo = batchNo == null ? null : batchNo.trim();
    }
    public Integer getDistributeType(){
        return distributeType;
    }

    public void setDistributeType(Integer distributeType){
        this.distributeType = distributeType;
    }
    public Double getDistributeValue(){
        return distributeValue;
    }

    public void setDistributeValue(Double distributeValue){
        this.distributeValue = distributeValue;
    }
    public Integer getAllowExceedMarketPrice(){
        return allowExceedMarketPrice;
    }

    public void setAllowExceedMarketPrice(Integer allowExceedMarketPrice){
        this.allowExceedMarketPrice = allowExceedMarketPrice;
    }
    public Double getExceedRatio(){
        return exceedRatio;
    }

    public void setExceedRatio(Double exceedRatio){
        this.exceedRatio = exceedRatio;
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
