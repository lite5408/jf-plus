package com.jf.plus.common.core.enums;

public enum AccountType {
	JC(1, "集采账户"), JF(2, "积分账户"), FF(3, "分发账户"),LB(4, "礼包账户");

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

	private AccountType(int type, String description) {
		this.type = type;
		this.description = description;
	}

	public static AccountType getByType(int type) {
		for (AccountType at : AccountType.values()) {
			if (at.type == type) {
				return at;
			}
		}
		throw new IllegalArgumentException("Not supported client type:" + type);
	}
}
