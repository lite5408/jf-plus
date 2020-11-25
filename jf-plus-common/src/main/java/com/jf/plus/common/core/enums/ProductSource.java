
package com.jf.plus.common.core.enums;

public enum ProductSource {
	JD(1, "京东商城商品"), 
	SUPPLY(2, "供应商商品"), 
	SN(3, "苏宁易购商品"), 
	YX(4, "网易严选商品"), 
	QX(5, "齐心商品");

	private int type;
	private String description;

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	private ProductSource(int type, String description) {
		this.type = type;
		this.description = description;
	}

	public static ProductSource getByType(int type) {
		for (ProductSource at : ProductSource.values()) {
			if (at.type == type) {
				return at;
			}
		}
		throw new IllegalArgumentException("Not supported client type:" + type);
	}
}
