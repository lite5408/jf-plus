package com.jf.plus.common.core.enums;

public enum VoucherSource {
	CRASH(1, "后台自动生成"), BUY(2, "站点创建");

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

	private VoucherSource(int type, String description) {
		this.type = type;
		this.description = description;
	}

	public static VoucherSource getByType(int type) {
		for (VoucherSource at : VoucherSource.values()) {
			if (at.type == type) {
				return at;
			}
		}
		throw new IllegalArgumentException("Not supported client type:" + type);
	}
}
