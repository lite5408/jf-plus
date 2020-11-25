package com.jf.plus.common.core.enums;

public enum AbstractType {

	AMOUNT_DIST("额度分发", "额度分发"),
	AMOUNT_NEGATIVE("额度负充值", "额度负充值"),
	AMOUNT_CONVERT("额度互拨", "额度互拨"),
	AMOUNT_EXPIRE_BACK("额度过期回退", "额度过期回退"),
	ORDER("订单下单","订单下单"),
	ORDER_BACK("订单退单", "订单退单"),
	ORDER_CANCEL("订单撤销", "订单撤销"),
	BUYER_CANCEL("买手取消", "买手取消"),
	ORDER_REJECT("订单驳回", "订单驳回"),
	ORDER_EXPIRE("订单过期", "订单过期"),
	OTHER("其他", "其他");

	private String type;
	private String description;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	private AbstractType(String type, String description) {
		this.type = type;
		this.description = description;
	}

	public static AbstractType getByType(String type) {
		for (AbstractType at : AbstractType.values()) {
			if (at.type == type) {
				return at;
			}
		}
		throw new IllegalArgumentException("Not supported client type:" + type);
	}

}
