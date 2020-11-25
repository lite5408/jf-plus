package com.jf.plus.core.site.entity;

import java.util.Date;
import java.util.List;

import org.springframework.util.StringUtils;
import org.springframework.web.util.HtmlUtils;

import com.jf.plus.common.core.Constants;
import com.jf.plus.common.core.enums.ProductSource;
import com.jf.plus.core.product.entity.ProductAttr;

import cn.iutils.common.config.JConfig;
import cn.iutils.sys.entity.DataEntity;

/**
 * 站点商品表
 *
 * @author Tng
 * @version 1.0
 */
public class SiteProduct extends DataEntity<SiteProduct> {

	private static final long serialVersionUID = 1L;

	private Long productId;// 商品id
	private Long productNo;// 商品标识
	private Long orgId;// 组织id
	private Long siteId;// 站点id
	private Double supplyPrice;// 供应价
	private Double markPrice;// 市场价
	private Double salePrice;// 销售价
	private Double salePoints;// 销售积分
	private Double profitPercent;// 利润比
	private Integer source;// 商品渠道： 1:京东商品 2：供应商商品
	private Integer operStatus;// 操作状态： 0:下架 1：上架

	/**
	 * 自定义属性
	 */
	//商品属性
	private String itemCode;
	private String itemName;
	private String itemCate;
	private String itemCateName;
	private Long supplyId;
	private String supplyName;
	private String photo;
	private Integer type;
	private String content;// 商品详情
	private String brandName;//品牌
	private String yearth;//年份
	private String area;//地区
	private Long buyerId;//买手ID
	private String buyerName;//买手ID
	private String saleType;//销售类型：正价 ，砍货
	private String season;//季节：春季，夏季，秋季，冬季
	private Integer saleNum;//销量
	private Integer stock;//库存
	private Double inPrice;//进货价
	private Double zjPrice;//中介费
	private String mgrPrice;//管理费
	
	//销售规格属性
	private Date shipmentDate;//出货时间
    private Integer limitStock;//限制库存：1限制 -1不限制
    private Integer saleNotice;//销售提醒值
    private String saleNoticeUnit;//销售提醒单位，% 百分比，数字代表件
    private Date endDate;//截止时间
    private Date shelvesDate;//上架时间
	
	private List<ProductAttr> productAttrList;//商品属性
	
	private java.util.Set<String> spIds;//通过id批量查询
	
	private Integer buyerNum;//用户购买数量

	public SiteProduct() {
		super();
	}

	public SiteProduct(String id) {
		super(id);
	}

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public Long getProductNo() {
		return productNo;
	}

	public void setProductNo(Long productNo) {
		this.productNo = productNo;
	}

	public Long getOrgId() {
		return orgId;
	}

	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}

	public Long getSiteId() {
		return siteId;
	}

	public void setSiteId(Long siteId) {
		this.siteId = siteId;
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

	public Double getSalePrice() {
		return salePrice;
	}

	public void setSalePrice(Double salePrice) {
		this.salePrice = salePrice;
	}

	public Double getProfitPercent() {
		return profitPercent;
	}

	public void setProfitPercent(Double profitPercent) {
		this.profitPercent = profitPercent;
	}

	public Integer getSource() {
		return source;
	}

	public void setSource(Integer source) {
		this.source = source;
	}

	public String getItemCode() {
		return itemCode;
	}

	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public String getItemCate() {
		return itemCate;
	}

	public void setItemCate(String itemCate) {
		this.itemCate = itemCate;
	}

	public String getItemCateName() {
		return itemCateName;
	}

	public void setItemCateName(String itemCateName) {
		this.itemCateName = itemCateName;
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

	public String getPhoto() {
		if (StringUtils.isEmpty(photo))
			return null;
		ProductSource productSource = ProductSource.getByType(getSource());
		switch (productSource) {
		case JD:
			return Constants.JD_URL_IMAGE + "/" + photo;
		case QX:
			return photo;
		case SN:
			return photo;
		case SUPPLY:{
			String[] photos = photo.split(",");
			StringBuffer photosUrl = new StringBuffer();
			for(String s : photos){
				photosUrl.append(JConfig.getConfig("url.image") + "/" + s).append(",");
			}
			return photosUrl.substring(0, photosUrl.length() - 1);
		}
		default:
			return photo;
		}
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getContent() {
		if (StringUtils.isEmpty(content))
			return null;

		return HtmlUtils.htmlUnescape(content);
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Integer getOperStatus() {
		return operStatus;
	}

	public void setOperStatus(Integer operStatus) {
		this.operStatus = operStatus;
	}

	public String getBrandName() {
		return brandName;
	}

	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}

	public Double getSalePoints() {
		return salePoints;
	}

	public void setSalePoints(Double salePoints) {
		this.salePoints = salePoints;
	}

	public java.util.Set<String> getSpIds() {
		return spIds;
	}

	public void setSpIds(java.util.Set<String> spIds) {
		this.spIds = spIds;
	}

	public Integer getSaleNum() {
		return saleNum;
	}

	public void setSaleNum(Integer saleNum) {
		this.saleNum = saleNum;
	}

	public String getYearth() {
		return yearth;
	}

	public void setYearth(String yearth) {
		this.yearth = yearth;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public Long getBuyerId() {
		return buyerId;
	}

	public void setBuyerId(Long buyerId) {
		this.buyerId = buyerId;
	}

	public String getSaleType() {
		return saleType;
	}

	public void setSaleType(String saleType) {
		this.saleType = saleType;
	}

	public String getSeason() {
		return season;
	}

	public void setSeason(String season) {
		this.season = season;
	}

	public Integer getStock() {
		return stock;
	}

	public void setStock(Integer stock) {
		this.stock = stock;
	}

	public List<ProductAttr> getProductAttrList() {
		return productAttrList;
	}

	public void setProductAttrList(List<ProductAttr> productAttrList) {
		this.productAttrList = productAttrList;
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

	public String getBuyerName() {
		return buyerName;
	}

	public void setBuyerName(String buyerName) {
		this.buyerName = buyerName;
	}

	public Integer getBuyerNum() {
		return buyerNum;
	}

	public void setBuyerNum(Integer buyerNum) {
		this.buyerNum = buyerNum;
	}

	public Date getShelvesDate() {
		return shelvesDate;
	}

	public void setShelvesDate(Date shelvesDate) {
		this.shelvesDate = shelvesDate;
	}
	
	
	
	
}
