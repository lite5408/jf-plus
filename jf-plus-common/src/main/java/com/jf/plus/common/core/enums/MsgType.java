package com.jf.plus.common.core.enums;

/**
 * 消息类型
 *
 * @author Tng
 *
 */
public enum MsgType {
	PRODUCT("10", "商品上架"), 
	ORDER_NOTICE("20", "销量提醒"),
	ORDER_DELIVER("21", "订单发货"),
	ORDER_CANCEL("22", "订单取消");

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

	private MsgType(String type, String description) {
		this.type = type;
		this.description = description;
	}

	public static MsgType getByType(int type) {
		for (MsgType at : MsgType.values()) {
			if (at.type.equals(type)) {
				return at;
			}
		}
		throw new IllegalArgumentException("Not supported client type:" + type);
	}
}
