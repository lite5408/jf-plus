package com.jf.plus.core.order.entity;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.jf.plus.common.core.Constants;

import cn.iutils.sys.entity.DataEntity;

/**
* 订单导出表
* @author Tng
* @version 1.0
*/
public class OrderDeliveryExport extends DataEntity<OrderDeliveryExport>{

    private static final long serialVersionUID = 1L;

    private String orderNo;//订单编号
    private Long deliveryId;//订单发货id
    private String itemCode;//商品编码
    private String specColorText;//颜色
    private String specSizeText;//尺码
    private String itemName;//商品名称
    private Integer deliveryNum;//商品数量
    private Double exportPrice;//导出销售价格
    private Date exportDate;//导出时间
    private String exportOperator;//导出操作人
    private Integer operStatus;//操作状态
    
    /**
     * 自定义属性
     */
    private String buyerId;//买手ID
	private String buyerName;//买手名称
	private String saleType;//销售类型
	private String photoUrl;//商品图片
	private Double salePrice;//成本价
	private String deliveryExpressNo;
	private Integer totalNum;//导出总数量
	private Double totalAmount;//导出总价
	private String orgName;//机构名称
    
	private List<OrderDetailSku> orderDetailSkus;//商品SKU列表

    public OrderDeliveryExport() {
        super();
    }
    public OrderDeliveryExport(String id){
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

    public String getItemName(){
        return itemName;
    }

    public void setItemName(String itemName){
        this.itemName = itemName == null ? null : itemName.trim();
    }
    public Integer getDeliveryNum(){
        return deliveryNum;
    }

    public void setDeliveryNum(Integer deliveryNum){
        this.deliveryNum = deliveryNum;
    }
    public Double getExportPrice(){
        return exportPrice;
    }

    public void setExportPrice(Double exportPrice){
        this.exportPrice = exportPrice;
    }
    public String getItemCode(){
        return itemCode;
    }

    public void setItemCode(String itemCode){
        this.itemCode = itemCode == null ? null : itemCode.trim();
    }
    public Date getExportDate(){
        return exportDate;
    }

    public void setExportDate(Date exportDate){
        this.exportDate = exportDate;
    }
    public Integer getOperStatus(){
        return operStatus;
    }

    public void setOperStatus(Integer operStatus){
        this.operStatus = operStatus;
    }
	public String getBuyerId() {
		return buyerId;
	}
	public void setBuyerId(String buyerId) {
		this.buyerId = buyerId;
	}
	public String getBuyerName() {
		return buyerName;
	}
	public void setBuyerName(String buyerName) {
		this.buyerName = buyerName;
	}
	public String getSaleType() {
		return saleType;
	}
	public void setSaleType(String saleType) {
		this.saleType = saleType;
	}
	public String getPhotoUrl() {
		if(StringUtils.isNotBlank(photoUrl)){
			return Constants.URL_IMAGE + "/" + photoUrl.split(",")[0];
		}
		return photoUrl;
	}
	public void setPhotoUrl(String photoUrl) {
		this.photoUrl = photoUrl;
	}
	public Double getSalePrice() {
		return salePrice;
	}
	public void setSalePrice(Double salePrice) {
		this.salePrice = salePrice;
	}
	public List<OrderDetailSku> getOrderDetailSkus() {
		return orderDetailSkus;
	}
	public void setOrderDetailSkus(List<OrderDetailSku> orderDetailSkus) {
		this.orderDetailSkus = orderDetailSkus;
	}
	public String getExportOperator() {
		return exportOperator;
	}
	public void setExportOperator(String exportOperator) {
		this.exportOperator = exportOperator;
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
	public String getDeliveryExpressNo() {
		return deliveryExpressNo;
	}
	public void setDeliveryExpressNo(String deliveryExpressNo) {
		this.deliveryExpressNo = deliveryExpressNo;
	}
	public Integer getTotalNum() {
		return totalNum;
	}
	public void setTotalNum(Integer totalNum) {
		this.totalNum = totalNum;
	}
	public Double getTotalAmount() {
		return totalAmount;
	}
	public void setTotalAmount(Double totalAmount) {
		this.totalAmount = totalAmount;
	}
	public String getOrgName() {
		return orgName;
	}
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
	
    
    
}
