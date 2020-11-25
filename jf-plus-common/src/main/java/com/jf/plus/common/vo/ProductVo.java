package com.jf.plus.common.vo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;

/**
 * 商品VO
 * @author Tng
 *
 */
public class ProductVo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4958834405349468395L;
	
	private String productId;//商品ID
	private String photos;//商品图片，多个逗号分割
	private String saleType;//商品类型：正价 砍货
	private String itemName;//商品名称
	private String content;//推荐理由
	private String cateId;//分类ID，二级分类逗号分割1232,2393
	private String cateName;//分类名称，多级分类逗号分割
	private Long supplyId;//供应商ID
	private String supplyName;//供应商名称
	private String brand;//品牌名称
	private String area;//地区
	private String yearth;//年份
	private String season;//季节
	private Integer source;//2 供应商商品
	private Long buyerId;//买手id
	private String buyerName;//买手名称
	private String itemCode;//商品编码
	private Integer operStatus;//状态
	
	private List<PrductAttrVo> attrs;//属性
	
	private List<ProductSkuVo> skus;//规格
	
	/**销售规则*/
	private Double salePrice;// 销售价/成本价
	private Double inPrice;//进货价
	private Double zjPrice;//中介费
	private String mgrPrice;//管理费
	private Date shipmentDate;//出货时间
    private Integer limitStock;//限制库存：1限制 -1不限制
    private Integer saleNotice;//销售提醒值
    private String saleNoticeUnit;//销售提醒单位，% 百分比，数字代表件
    private Date endDate;//截止时间
    private String orgGroups;//代理商分组，多个逗号分割
    private String orgGroupsNames;//代理商分组描述
    private Integer stock;//库存
    
    private String token;//token
    
    private String photoUrl;// 封面图链接（转换成URL）
    private Integer saleNum;//销售数量
    
    
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	public String getPhotos() {
		return photos;
	}
	public void setPhotos(String photos) {
		this.photos = photos;
	}
	public String getSaleType() {
		return saleType;
	}
	public void setSaleType(String saleType) {
		this.saleType = saleType;
	}
	public String getItemName() {
		return itemName;
	}
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getCateId() {
		return cateId;
	}
	public void setCateId(String cateId) {
		this.cateId = cateId;
	}
	public Long getSupplyId() {
		return supplyId;
	}
	public void setSupplyId(Long supplyId) {
		this.supplyId = supplyId;
	}
	public String getSupplyName() {
		return supplyName;
	}
	public void setSupplyName(String supplyName) {
		this.supplyName = supplyName;
	}
	public String getBrand() {
		return brand;
	}
	public void setBrand(String brand) {
		this.brand = brand;
	}
	public String getArea() {
		return area;
	}
	public void setArea(String area) {
		this.area = area;
	}
	public String getYearth() {
		return yearth;
	}
	public void setYearth(String yearth) {
		this.yearth = yearth;
	}
	public String getSeason() {
		return season;
	}
	public void setSeason(String season) {
		this.season = season;
	}
	public List<PrductAttrVo> getAttrs() {
		return attrs;
	}
	public void setAttrs(List<PrductAttrVo> attrs) {
		this.attrs = attrs;
	}
	public List<ProductSkuVo> getSkus() {
		return skus;
	}
	public void setSkus(List<ProductSkuVo> skus) {
		this.skus = skus;
	}
	public Double getSalePrice() {
		return salePrice;
	}
	public void setSalePrice(Double salePrice) {
		this.salePrice = salePrice;
	}
	public Double getInPrice() {
		return inPrice;
	}
	public void setInPrice(Double inPrice) {
		this.inPrice = inPrice;
	}
	public Double getZjPrice() {
		return zjPrice;
	}
	public void setZjPrice(Double zjPrice) {
		this.zjPrice = zjPrice;
	}
	public String getMgrPrice() {
		return mgrPrice;
	}
	public void setMgrPrice(String mgrPrice) {
		this.mgrPrice = mgrPrice;
	}
	public Date getShipmentDate() {
		return shipmentDate;
	}
	public void setShipmentDate(Date shipmentDate) {
		this.shipmentDate = shipmentDate;
	}
	public Integer getLimitStock() {
		return limitStock;
	}
	public void setLimitStock(Integer limitStock) {
		this.limitStock = limitStock;
	}
	public Integer getSaleNotice() {
		return saleNotice;
	}
	public void setSaleNotice(Integer saleNotice) {
		this.saleNotice = saleNotice;
	}
	public String getSaleNoticeUnit() {
		return saleNoticeUnit;
	}
	public void setSaleNoticeUnit(String saleNoticeUnit) {
		this.saleNoticeUnit = saleNoticeUnit;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public String getOrgGroups() {
		return orgGroups;
	}
	public void setOrgGroups(String orgGroups) {
		this.orgGroups = orgGroups;
	}
	public Integer getStock() {
		return stock;
	}
	public void setStock(Integer stock) {
		this.stock = stock;
	}
	public Integer getSource() {
		return source;
	}
	public void setSource(Integer source) {
		this.source = source;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public Long getBuyerId() {
		return buyerId;
	}
	public void setBuyerId(Long buyerId) {
		this.buyerId = buyerId;
	}
	public String getBuyerName() {
		return buyerName;
	}
	public void setBuyerName(String buyerName) {
		this.buyerName = buyerName;
	}
    public String getItemCode() {
		return itemCode;
	}
	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}
	public Integer getOperStatus() {
		return operStatus;
	}
	public void setOperStatus(Integer operStatus) {
		this.operStatus = operStatus;
	}
	
	public String getCateName() {
		return cateName;
	}
	public void setCateName(String cateName) {
		this.cateName = cateName;
	}
	
	public String getPhotoUrl() {
		return photoUrl;
	}
	public void setPhotoUrl(String photoUrl) {
		this.photoUrl = photoUrl;
	}
	
	public Integer getSaleNum() {
		return saleNum;
	}
	public void setSaleNum(Integer saleNum) {
		this.saleNum = saleNum;
	}
	
	public String getOrgGroupsNames() {
		return orgGroupsNames;
	}
	public void setOrgGroupsNames(String orgGroupsNames) {
		this.orgGroupsNames = orgGroupsNames;
	}
	public static void main(String[] args) {
		ProductVo productVo = new ProductVo();
		System.out.println(JSON.toJSONString(productVo,SerializerFeature.WriteMapNullValue));
	}
	

}
