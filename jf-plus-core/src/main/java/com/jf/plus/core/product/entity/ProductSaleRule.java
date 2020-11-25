package com.jf.plus.core.product.entity;

import java.util.Date;
import java.util.List;

import cn.iutils.sys.entity.DataEntity;

/**
* 销售规则
* @author Tng
* @version 1.0
*/
public class ProductSaleRule extends DataEntity<ProductSaleRule>{

    private static final long serialVersionUID = 1L;

    private Long productId;//商品ID
    private String saleType;//冗余销售类型
    private Date shipmentDate;//出货时间
    private Integer limitStock;//限制库存：1限制 -1不限制
    private Integer saleNotice;//销售提醒值
    private String saleNoticeUnit;//销售提醒单位，% 百分比，数字代表件
    private Date endDate;//截止时间
    private String orgGroups;//代理商分组
    
    private List<OrgGroup> orgGroupList;

    public ProductSaleRule() {
        super();
    }
    public ProductSaleRule(String id){
        super(id);
    }

    public Long getProductId(){
        return productId;
    }

    public void setProductId(Long productId){
        this.productId = productId;
    }
    public String getSaleType(){
        return saleType;
    }

    public void setSaleType(String saleType){
        this.saleType = saleType == null ? null : saleType.trim();
    }
    public Date getShipmentDate(){
        return shipmentDate;
    }

    public void setShipmentDate(Date shipmentDate){
        this.shipmentDate = shipmentDate;
    }
    public Integer getLimitStock(){
        return limitStock;
    }

    public void setLimitStock(Integer limitStock){
        this.limitStock = limitStock;
    }
    public Integer getSaleNotice(){
        return saleNotice;
    }

    public void setSaleNotice(Integer saleNotice){
        this.saleNotice = saleNotice;
    }
    public String getSaleNoticeUnit(){
        return saleNoticeUnit;
    }

    public void setSaleNoticeUnit(String saleNoticeUnit){
        this.saleNoticeUnit = saleNoticeUnit == null ? null : saleNoticeUnit.trim();
    }
    public Date getEndDate(){
        return endDate;
    }

    public void setEndDate(Date endDate){
        this.endDate = endDate;
    }
    public String getOrgGroups(){
        return orgGroups;
    }

    public void setOrgGroups(String orgGroups){
        this.orgGroups = orgGroups == null ? null : orgGroups.trim();
    }
	public List<OrgGroup> getOrgGroupList() {
		return orgGroupList;
	}
	public void setOrgGroupList(List<OrgGroup> orgGroupList) {
		this.orgGroupList = orgGroupList;
	}
    
}
