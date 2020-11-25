package com.jf.plus.core.order.entity;

import cn.iutils.sys.entity.DataEntity;

/**
* 订单明细表
* @author Tng
* @version 1.0
*/
public class OrderDetailSku extends DataEntity<OrderDetailSku>{

    private static final long serialVersionUID = 1L;

    private Long orderId;//订单ID
    private String orderNo;//订单号
    private String orderSubno;//订单子单号
    private Long orderDetailId;//商品详细表ID
    private Long itemId;//站点商品id
    private String itemName;//
    private Long supplyId;//供应商id
    private Double supplyPrice;//供应价
    private Double salePrice;//销售价
    private Integer saleNum;//销售数量
    private Double amount;//销售金额
    private Double salePoints;//销售积分价
    private Integer freezeNum;//冻结库存量
    private Long skuId;//SKU ID
    private String specCode;//SKU编码
    private String specColor;//颜色
    private String specColorText;//颜色值
    private String specSize;//尺码
    private String specSizeText;//尺码值
    
    /**
     * 自定义属性
     */
    private String orgName;//代理商
    private String batchNo;//批次号
    private String itemCode;//商品编码
    private String buyerName;//买手名称
    private String isDist;//是否分配

    public OrderDetailSku() {
        super();
    }
    public OrderDetailSku(String id){
        super(id);
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
        this.orderNo = orderNo == null ? null : orderNo.trim();
    }
    public String getOrderSubno(){
        return orderSubno;
    }

    public void setOrderSubno(String orderSubno){
        this.orderSubno = orderSubno == null ? null : orderSubno.trim();
    }
    public Long getOrderDetailId(){
        return orderDetailId;
    }

    public void setOrderDetailId(Long orderDetailId){
        this.orderDetailId = orderDetailId;
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
        this.itemName = itemName == null ? null : itemName.trim();
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
    public Double getSalePoints(){
        return salePoints;
    }

    public void setSalePoints(Double salePoints){
        this.salePoints = salePoints;
    }
    public Integer getFreezeNum(){
        return freezeNum;
    }

    public void setFreezeNum(Integer freezeNum){
        this.freezeNum = freezeNum;
    }
    public Long getSkuId(){
        return skuId;
    }

    public void setSkuId(Long skuId){
        this.skuId = skuId;
    }
	public String getSpecCode() {
		return specCode;
	}
	public void setSpecCode(String specCode) {
		this.specCode = specCode;
	}
	public String getSpecColor() {
		return specColor;
	}
	public void setSpecColor(String specColor) {
		this.specColor = specColor;
	}
	public String getSpecColorText() {
		return specColorText;
	}
	public void setSpecColorText(String specColorText) {
		this.specColorText = specColorText;
	}
	public String getSpecSize() {
		return specSize;
	}
	public void setSpecSize(String specSize) {
		this.specSize = specSize;
	}
	public String getSpecSizeText() {
		return specSizeText;
	}
	public void setSpecSizeText(String specSizeText) {
		this.specSizeText = specSizeText;
	}
	public String getOrgName() {
		return orgName;
	}
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
	public String getBatchNo() {
		return batchNo;
	}
	public void setBatchNo(String batchNo) {
		this.batchNo = batchNo;
	}
	public String getItemCode() {
		return itemCode;
	}
	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}
	public String getBuyerName() {
		return buyerName;
	}
	public void setBuyerName(String buyerName) {
		this.buyerName = buyerName;
	}
	public String getIsDist() {
		return isDist;
	}
	public void setIsDist(String isDist) {
		this.isDist = isDist;
	}
	
	
    
}
