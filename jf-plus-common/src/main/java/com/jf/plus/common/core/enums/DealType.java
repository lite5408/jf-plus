package com.jf.plus.common.core.enums;

/**
 * 新券流水类型枚举
 *
 * @author zhangl
 *
 */
public enum DealType {
	INCOME(1, "收入"), EXPENDITURE(2, "支出");

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

	private DealType(int type, String description) {
		this.type = type;
		this.description = description;
	}

	public static DealType getByType(int type) {
		for (DealType at : DealType.values()) {
			if (at.type == type) {
				return at;
			}
		}
		throw new IllegalArgumentException("Not supported client type:" + type);
	}
}
