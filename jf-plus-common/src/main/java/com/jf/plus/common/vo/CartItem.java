package com.jf.plus.common.vo;

import java.io.Serializable;
import java.util.List;

/**
 * @ClassName: CartItem
 * @Description: 购物车item
 * @author Tng
 * @date 2016年12月26日 下午4:51:17
 * 
 */

public class CartItem implements Serializable{

	private static final long serialVersionUID = 4403873668685324709L;

	private Long siteId;//站点id
	
	private Long supplyId;//供应商id
	
	private String supplyName;//供应商名称
	
	private String itemId;//站点商品id
	
	private Double salePrice;//销售价
	
	private String itemImg;//商品图片url
	
	private String itemName;//商品名称
	
	private Integer source;//来源
	
	private Integer shopNum;//购买数量
	
	private List<CartSku> skus;//sku列表
	

	public String getItemId() {
		return itemId;
	}

	public void setItemId(String itemId) {
		this.itemId = itemId;
	}

	public Double getSalePrice() {
		return salePrice;
	}

	public void setSalePrice(Double salePrice) {
		this.salePrice = salePrice;
	}

	public String getItemImg() {
		return itemImg;
	}

	public void setItemImg(String itemImg) {
		this.itemImg = itemImg;
	}

	public Integer getShopNum() {
		return shopNum;
	}

	public void setShopNum(Integer shopNum) {
		this.shopNum = shopNum;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public Integer getSource() {
		return source;
	}

	public void setSource(Integer source) {
		this.source = source;
	}
	
	public Long getSiteId() {
		return siteId;
	}

	public void setSiteId(Long siteId) {
		this.siteId = siteId;
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

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CartItem other = (CartItem) obj;
		if(itemId.equals(other.getItemId())){
			return false;
		}
		
		return true;
	}

	public List<CartSku> getSkus() {
		return skus;
	}

	public void setSkus(List<CartSku> skus) {
		this.skus = skus;
	}
}
