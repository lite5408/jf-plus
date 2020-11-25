//package com.jf.plus.core.product.entity;
//
//import java.util.List;
//
//import com.jf.plus.prod.channel.qx.api.entity.SpecParam;
//
//import cn.iutils.sys.entity.DataEntity;
//
///**
// * 商品表
// *
// * @author Tng
// * @version 1.0
// */
//public class ProductQx extends DataEntity<ProductQx> {
//
//	private static final long serialVersionUID = 1L;
//
//	private String supCode; // 供应商编号
//	private String category; // 商品类别
//	private String productName; // 商品名称
//	private String productNo; // 商品编号
//	private String upc; // 条形码
//	private String materialCode; // 物料编码
//	private String brand; // 商品品牌
//	private Double mktPrice; // 市场价格
//	private Double unitPrice; // 销售价格
//	private Double nakedPrice; // 商品裸价
//	private Double taxPrice; // 商品税价
//	private Double pxPrice; // 结算价
//	private Double taxRate; // 税率
//	private Integer store; // 库存
//	private String imagePath; // 主图URL
//	private String otherImage; // 其他图片
//	private String productArear; // 产地
//	private String weight; // 重量
//	private String saleUnit; // 销售单位
//	private String introduction; // 详细描述
//	private String prdParam; // 产品参数
//	private String packingList; // 包装清单
//	private List<SpecParam> specParamList; // 规格参数
//	private String relateFlag; // 关联标识串
//	private Integer minBuy; // 起订量
//	private String productUrl; // 商品齐心商城地址
//	private String updateTime; // 商品更新时间
//	private String signData; // 签名数据
//
//	private String specParamJson; // 参数Json字符串
//
//	public String getSupCode() {
//		return supCode;
//	}
//
//	public void setSupCode(String supCode) {
//		this.supCode = supCode;
//	}
//
//	public String getCategory() {
//		return category;
//	}
//
//	public void setCategory(String category) {
//		this.category = category;
//	}
//
//	public String getProductName() {
//		return productName;
//	}
//
//	public void setProductName(String productName) {
//		this.productName = productName;
//	}
//
//	public String getProductNo() {
//		return productNo;
//	}
//
//	public void setProductNo(String productNo) {
//		this.productNo = productNo;
//	}
//
//	public String getUpc() {
//		return upc;
//	}
//
//	public void setUpc(String upc) {
//		this.upc = upc;
//	}
//
//	public String getMaterialCode() {
//		return materialCode;
//	}
//
//	public void setMaterialCode(String materialCode) {
//		this.materialCode = materialCode;
//	}
//
//	public String getBrand() {
//		return brand;
//	}
//
//	public void setBrand(String brand) {
//		this.brand = brand;
//	}
//
//	public Double getMktPrice() {
//		return mktPrice;
//	}
//
//	public void setMktPrice(Double mktPrice) {
//		this.mktPrice = mktPrice;
//	}
//
//	public Double getUnitPrice() {
//		return unitPrice;
//	}
//
//	public void setUnitPrice(Double unitPrice) {
//		this.unitPrice = unitPrice;
//	}
//
//	public Double getNakedPrice() {
//		return nakedPrice;
//	}
//
//	public void setNakedPrice(Double nakedPrice) {
//		this.nakedPrice = nakedPrice;
//	}
//
//	public Double getTaxPrice() {
//		return taxPrice;
//	}
//
//	public void setTaxPrice(Double taxPrice) {
//		this.taxPrice = taxPrice;
//	}
//
//	public Double getPxPrice() {
//		return pxPrice;
//	}
//
//	public void setPxPrice(Double pxPrice) {
//		this.pxPrice = pxPrice;
//	}
//
//	public Double getTaxRate() {
//		return taxRate;
//	}
//
//	public void setTaxRate(Double taxRate) {
//		this.taxRate = taxRate;
//	}
//
//	public Integer getStore() {
//		return store;
//	}
//
//	public void setStore(Integer store) {
//		this.store = store;
//	}
//
//	public String getImagePath() {
//		return imagePath;
//	}
//
//	public void setImagePath(String imagePath) {
//		this.imagePath = imagePath;
//	}
//
//	public String getOtherImage() {
//		return otherImage;
//	}
//
//	public void setOtherImage(String otherImage) {
//		this.otherImage = otherImage;
//	}
//
//	public String getProductArear() {
//		return productArear;
//	}
//
//	public void setProductArear(String productArear) {
//		this.productArear = productArear;
//	}
//
//	public String getWeight() {
//		return weight;
//	}
//
//	public void setWeight(String weight) {
//		this.weight = weight;
//	}
//
//	public String getSaleUnit() {
//		return saleUnit;
//	}
//
//	public void setSaleUnit(String saleUnit) {
//		this.saleUnit = saleUnit;
//	}
//
//	public String getIntroduction() {
//		return introduction;
//	}
//
//	public void setIntroduction(String introduction) {
//		this.introduction = introduction;
//	}
//
//	public String getPrdParam() {
//		return prdParam;
//	}
//
//	public void setPrdParam(String prdParam) {
//		this.prdParam = prdParam;
//	}
//
//	public String getPackingList() {
//		return packingList;
//	}
//
//	public void setPackingList(String packingList) {
//		this.packingList = packingList;
//	}
//
//	public List<SpecParam> getSpecParamList() {
//		return specParamList;
//	}
//
//	public void setSpecParamList(List<SpecParam> specParamList) {
//		this.specParamList = specParamList;
//	}
//
//	public String getRelateFlag() {
//		return relateFlag;
//	}
//
//	public void setRelateFlag(String relateFlag) {
//		this.relateFlag = relateFlag;
//	}
//
//	public Integer getMinBuy() {
//		return minBuy;
//	}
//
//	public void setMinBuy(Integer minBuy) {
//		this.minBuy = minBuy;
//	}
//
//	public String getProductUrl() {
//		return productUrl;
//	}
//
//	public void setProductUrl(String productUrl) {
//		this.productUrl = productUrl;
//	}
//
//	public String getUpdateTime() {
//		return updateTime;
//	}
//
//	public void setUpdateTime(String updateTime) {
//		this.updateTime = updateTime;
//	}
//
//	public String getSignData() {
//		return signData;
//	}
//
//	public void setSignData(String signData) {
//		this.signData = signData;
//	}
//
//	public String getSpecParamJson() {
//		return specParamJson;
//	}
//
//	public void setSpecParamJson(String specParamJson) {
//		this.specParamJson = specParamJson;
//	}
//
//}
