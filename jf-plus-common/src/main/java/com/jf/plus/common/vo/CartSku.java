package com.jf.plus.common.vo;

import java.io.Serializable;

/**
 * @ClassName: CartSku
 * @Description: 购物车item的SKU信息
 * @author Tng
 * @date 2016年12月26日 下午4:51:17
 * 
 */

public class CartSku implements Serializable{
	private Long skuId;//SKU ID
	private String specCode;//SKU编码
    private String specColor;//颜色
    private String specColorText;//颜色值
    private String specSize;//尺码
    private String specSizeText;//尺码值
    
    //自定义属性
    private Integer shopNum;//购买数量

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

	public Integer getShopNum() {
		return shopNum;
	}

	public void setShopNum(Integer shopNum) {
		this.shopNum = shopNum;
	}

	public Long getSkuId() {
		return skuId;
	}

	public void setSkuId(Long skuId) {
		this.skuId = skuId;
	}
	
}
