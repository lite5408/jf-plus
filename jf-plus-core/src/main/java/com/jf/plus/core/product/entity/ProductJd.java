package com.jf.plus.core.product.entity;

import cn.iutils.sys.entity.DataEntity;

/**
* 
* 商品表
* 
* @author Tng
* @version 1.0
*/
public class ProductJd extends DataEntity<ProductJd>{

    private static final long serialVersionUID = 1L;

    private String brandName;//品牌名称
    private String category;//商品类别
    private String imagePath;//主图URL
    private String introduction;//详细描述
    private String name;//商品名称
    private Double supplyPrice;// 供应价
    private Double markPrice;// 市场价
	private Double profitPercent;// 利润比
	private String param;//产品参数
	private String productArea;//产地
	private String saleUnit;//销售单位
	private String sku;//商品编号
	private String state;//商品状态0为下架,1为上架
	private String upc;//条形码
	private String wareQd;//供应商编号
	private Double weight;//商品重量
	
    public ProductJd() {
        super();
    }
    public ProductJd(String id){
        super(id);
    }

    public String getBrandName(){
        return brandName;
    }

    public void setBrandName(String brandName){
        this.brandName = brandName == null ? null : brandName.trim();
    }
    public String getCategory(){
        return category;
    }

    public void setCategory(String category){
        this.category = category == null ? null : category.trim();
    }
    public String getIntroduction(){
    return introduction;
    }

    public void setIntroduction(String introduction){
    this.introduction = introduction == null ? null : introduction.trim();
    }
    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name == null ? null : name.trim();
    }
    
    public Double getSupplyPrice() {
		return supplyPrice;
	}
	public void setSupplyPrice(Double supplyPrice) {
		this.supplyPrice = supplyPrice;
	}
	public Double getMarkPrice() {
		return markPrice;
	}
    
	public void setMarkPrice(Double markPrice) {
		this.markPrice = markPrice;
	}
	
    public Double getProfitPercent() {
		return profitPercent;
	}
	public void setProfitPercent(Double profitPercent) {
		this.profitPercent = profitPercent;
	}
	public String getParam(){
    return param;
    }

    public void setParam(String param){
    this.param = param == null ? null : param.trim();
    }
    public String getProductArea(){
        return productArea;
    }

    public void setProductArea(String productArea){
        this.productArea = productArea == null ? null : productArea.trim();
    }
    public String getSaleUnit(){
        return saleUnit;
    }

    public void setSaleUnit(String saleUnit){
        this.saleUnit = saleUnit == null ? null : saleUnit.trim();
    }
    public String getSku(){
        return sku;
    }

    public void setSku(String sku){
        this.sku = sku == null ? null : sku.trim();
    }
    public String getState(){
        return state;
    }

    public void setState(String state){
        this.state = state == null ? null : state.trim();
    }
    public String getUpc(){
        return upc;
    }

    public void setUpc(String upc){
        this.upc = upc == null ? null : upc.trim();
    }
    public String getWareQd(){
        return wareQd;
    }

    public void setWareQd(String wareQd){
        this.wareQd = wareQd == null ? null : wareQd.trim();
    }
    public Double getWeight(){
        return weight;
    }

    public void setWeight(Double weight){
        this.weight = weight;
    }
    public String getImagePath(){
        return imagePath;
    }

    public void setImagePath(String imagePath){
        this.imagePath = imagePath == null ? null : imagePath.trim();
    }
}
