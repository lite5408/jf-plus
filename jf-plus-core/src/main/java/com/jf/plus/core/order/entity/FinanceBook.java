package com.jf.plus.core.order.entity;

import java.util.Date;

import cn.iutils.sys.entity.DataEntity;

/**
* 财务对账簿表
* @author Tng
* @version 1.0
*/
public class FinanceBook extends DataEntity<FinanceBook>{

    private static final long serialVersionUID = 1L;

    private String title;//对账标题
    private String batchNo;//批次号
    private Date operTime;//对账时间
    private Long supplyId;//供应商
    private String supplyName;//供应商名称
    private Integer operStatus;//对账状态（0:未结算 1：已结算 2：取消结算）
    private Integer bussType;//结算方式（1单个结算，2批量结算，3导入结算）
    private Date confirmDate;//确认时间

    public FinanceBook() {
        super();
    }
    public FinanceBook(String id){
        super(id);
    }

    public String getTitle(){
        return title;
    }

    public void setTitle(String title){
        this.title = title == null ? null : title.trim();
    }
    public String getBatchNo(){
        return batchNo;
    }

    public void setBatchNo(String batchNo){
        this.batchNo = batchNo == null ? null : batchNo.trim();
    }
    public Date getOperTime(){
        return operTime;
    }

    public void setOperTime(Date operTime){
        this.operTime = operTime;
    }
    public Long getSupplyId(){
        return supplyId;
    }

    public void setSupplyId(Long supplyId){
        this.supplyId = supplyId;
    }
    public String getSupplyName(){
        return supplyName;
    }

    public void setSupplyName(String supplyName){
        this.supplyName = supplyName == null ? null : supplyName.trim();
    }
    public Integer getOperStatus(){
        return operStatus;
    }

    public void setOperStatus(Integer operStatus){
        this.operStatus = operStatus;
    }
    public Date getConfirmDate(){
        return confirmDate;
    }

    public void setConfirmDate(Date confirmDate){
        this.confirmDate = confirmDate;
    }
	public Integer getBussType() {
		return bussType;
	}
	public void setBussType(Integer bussType) {
		this.bussType = bussType;
	}
    
    
}
