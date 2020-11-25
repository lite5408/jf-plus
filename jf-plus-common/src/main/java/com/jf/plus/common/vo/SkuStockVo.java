package com.jf.plus.common.vo;

import java.io.Serializable;

/**
 * 校验库存VO
 * @author Tng
 *
 */
public class SkuStockVo implements Serializable{

	/**
	 *
	 */
	private static final long serialVersionUID = -2579206097489275270L;

	public static final String HAS_STOCK = "1", LACK_STOCK = "2", OUT_SHELVES = "4";

	private String siteId;//站点id
	private String supplyId;//供应商id
	private String itemId;//站点商品id
	private String skuId;//item_code（渠道skuid）
	private String skuName;//商品名称
	private String num;//购买数量
	private Integer source;//渠道来源
	private String stockNum; //库存数量
	private String provinceId;//省份id
	private String cityId;//城市id
	private String countyId;//乡镇id
	private String townId;//街道id

	/**返回结果*/
	private String status;//状态：1库存充足 2库存不足 4下架

	public String getSkuId() {
		return skuId;
	}

	public void setSkuId(String skuId) {
		this.skuId = skuId;
	}

	public String getSkuName() {
		return skuName;
	}

	public void setSkuName(String skuName) {
		this.skuName = skuName;
	}

	public Integer getSource() {
		return source;
	}

	public void setSource(Integer source) {
		this.source = source;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getStockNum() {
		return stockNum;
	}

	public void setStockNum(String stockNum) {
		this.stockNum = stockNum;
	}

	public String getProvinceId() {
		return provinceId;
	}

	public void setProvinceId(String provinceId) {
		this.provinceId = provinceId;
	}

	public String getCityId() {
		return cityId;
	}

	public void setCityId(String cityId) {
		this.cityId = cityId;
	}

	public String getCountyId() {
		return countyId;
	}

	public void setCountyId(String countyId) {
		this.countyId = countyId;
	}

	public String getTownId() {
		return townId;
	}

	public void setTownId(String townId) {
		this.townId = townId;
	}

	public String getNum() {
		return num;
	}

	public void setNum(String num) {
		this.num = num;
	}

	public String getSupplyId() {
		return supplyId;
	}

	public void setSupplyId(String supplyId) {
		this.supplyId = supplyId;
	}

	public String getSiteId() {
		return siteId;
	}

	public void setSiteId(String siteId) {
		this.siteId = siteId;
	}

	public String getItemId() {
		return itemId;
	}

	public void setItemId(String itemId) {
		this.itemId = itemId;
	}

}
