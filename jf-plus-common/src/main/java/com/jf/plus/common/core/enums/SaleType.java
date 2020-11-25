package com.jf.plus.common.core.enums;

/**
 * 商品销售类型
 *
 * @author Tng
 *
 */
public enum SaleType {
	ZJ("正价"), KH("砍货");

	private String description;


	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	private SaleType(String description) {
		this.description = description;
	}
}
