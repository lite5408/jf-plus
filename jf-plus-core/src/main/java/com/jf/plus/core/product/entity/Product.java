package com.jf.plus.core.product.entity;

import java.util.Date;

import org.springframework.util.StringUtils;
import org.springframework.web.util.HtmlUtils;

import com.jf.plus.common.core.Constants;
import com.jf.plus.common.core.enums.ProductSource;

import cn.iutils.common.config.JConfig;
import cn.iutils.sys.entity.DataEntity;

/**
 * 商品表
 * 
 * @author Tng
 * @version 1.0
 */
public class Product extends DataEntity<Product> {

	private static final long serialVersionUID = 1L;

	private Long productNo;// 商品标识
	private String itemCode;// 商品编码
	private String itemName;// 商品名称
	private String brandName;// 商品品牌
	private Long orgId;// 组织id
	private String orgName;// 组织名称
	private String itemCate;//
	private String itemCateName;//
	private Long supplyId;// 供应商id
	private String supplyName;// 供应商名称
	private Double supplyPrice;// 供应价
	private Double markPrice;// 市场价
	private Double salePrice;// 销售价/成本价
	private Double inPrice;//进货价
	private Double zjPrice;//中介费
	private String mgrPrice;//管理费
	private Double profitPercent;// 利润比
	private String photo;// 封面图
	private Integer source;// 商品渠道： 1:京东商品 2：供应商商品
	private Integer type;// 1实物商品 2话费充值
	private Integer operStatus;// 操作状态：ProductStatus
	private String yearth;//年份
	private String area;//地区
	private Long buyerId;//买手ID
	private String buyerName;//买手名称
	private String saleType;//销售类型：正价 ，砍货
	private String season;//季节：春季，夏季，秋季，冬季
	private Date shelvesDate;
	

	/** 自定义属性 */
	private Integer stock;// 库存
	private String content;// 商品详情
	private String contentHTML;// 商品详情(转义之后)
	private String photoUrl;// 封面图链接（转换成URL）
	private Integer saleNum;//销售数量

	public Product() {
		super();
	}

	public Product(String id) {
		super(id);
	}

	public Long getProductNo() {
		return productNo;
	}

	public void setProductNo(Long productNo) {
		this.productNo = productNo;
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

	public Long getOrgId() {
		return orgId;
	}

	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
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

	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	public Integer getSource() {
		return source;
	}

	public void setSource(Integer source) {
		this.source = source;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Integer getOperStatus() {
		return operStatus;
	}

	public void setOperStatus(Integer operStatus) {
		this.operStatus = operStatus;
	}

	public Integer getStock() {
		return stock;
	}

	public void setStock(Integer stock) {
		this.stock = stock;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getPhotoUrl() {
		if (StringUtils.isEmpty(getPhoto()))
			return null;

		ProductSource productSource = ProductSource.getByType(getSource());

		switch (productSource) {
		case JD:
			return Constants.JD_URL_IMAGE + "/" + getPhoto();
		case QX:
			return getPhoto();
		case SN:
			return getPhoto();
		case SUPPLY:{
			String[] photos = getPhoto().split(",");
			StringBuffer photosUrl = new StringBuffer();
			for(String s : photos){
				photosUrl.append(JConfig.getConfig("url.image") + "/" + s).append(",");
			}
			return photosUrl.substring(0, photosUrl.length() - 1);
		}
		default:
			return getPhoto();
		}
	}

	public void setPhotoUrl(String photoUrl) {
		this.photoUrl = photoUrl;
	}

	public String getContentHTML() {
		if (StringUtils.isEmpty(content))
			return null;

		return HtmlUtils.htmlUnescape(content);
	}

	public void setContentHTML(String contentHTML) {
		this.contentHTML = contentHTML;
	}

	public String getBrandName() {
		return brandName;
	}

	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}

	public String getYearth() {
		return yearth;
	}

	public void setYearth(String yearth) {
		this.yearth = yearth == null ? null : yearth.trim();
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area == null ? null : area.trim();
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
		this.saleType = saleType == null ? null : saleType.trim();
	}

	public Integer getSaleNum() {
		return saleNum;
	}

	public void setSaleNum(Integer saleNum) {
		this.saleNum = saleNum;
	}

	public String getSeason() {
		return season;
	}

	public void setSeason(String season) {
		this.season = season == null ? null : season.trim();
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

	public Date getShelvesDate() {
		return shelvesDate;
	}

	public void setShelvesDate(Date shelvesDate) {
		this.shelvesDate = shelvesDate;
	}
	
	
	
}
