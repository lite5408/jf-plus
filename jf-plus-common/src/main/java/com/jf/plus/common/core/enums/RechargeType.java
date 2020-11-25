package com.jf.plus.common.core.enums;

/**
 * @ClassName: RechargeType
 * @Description: 流水类型
 * @author zl
 * @date 2017年8月14日00:35:00
 *
 */

public enum RechargeType {
	CZ(1, "充值"), XF(2, "消费"), TH(3, "退回");

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

	private RechargeType(int type, String description) {
		this.type = type;
		this.description = description;
	}

	public static RechargeType getByType(int type) {
		for (RechargeType at : RechargeType.values()) {
			if (at.type == type) {
				return at;
			}
		}
		throw new IllegalArgumentException("Not supported client type:" + type);
	}
}
