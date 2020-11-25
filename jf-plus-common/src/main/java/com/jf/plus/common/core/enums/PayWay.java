package com.jf.plus.common.core.enums;

/**
 * @ClassName: PayWay
 * @Description: 支付方式枚举类
 * @author lh
 * @date 2016年4月25日 上午11:02:12
 *
 */

public enum PayWay {
	YE(1, "余额支付"),
	LB(2, "礼包券兑换");

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

	private PayWay(int type, String description) {
		this.type = type;
		this.description = description;
	}

	public static PayWay getByType(int type) {
		for (PayWay at : PayWay.values()) {
			if (at.type == type) {
				return at;
			}
		}
		throw new IllegalArgumentException("Not supported client type:" + type);
	}
}
