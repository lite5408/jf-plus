package com.jf.plus.common.core.enums;

public enum WeixinMsgType {
	SUBSCRIBE(1, "扫码关注"), 
	BUY(2, "购买奖励");

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

	private WeixinMsgType(int type, String description) {
		this.type = type;
		this.description = description;
	}

	public static WeixinMsgType getByType(int type) {
		for (WeixinMsgType at : WeixinMsgType.values()) {
			if (at.type == type) {
				return at;
			}
		}
		throw new IllegalArgumentException("Not supported client type:" + type);
	}
}
