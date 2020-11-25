
/**
 * @Title: OrderType.java
 * @Package com.jf.plus.common.core.enums
 * @Description: 订单类型枚举类
 * @author he
 * @date 2016年8月9日 下午7:11:12
 * @version V1.0
 */

package com.jf.plus.common.core.enums;

/**
 * 订单来源
 *
 * @author zhangl
 *
 */
public enum OrderSource {
	JC(1, "集采订单"), JF(2, "积分订单");

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

	private OrderSource(int type, String description) {
		this.type = type;
		this.description = description;
	}

	public static OrderSource getByType(int type) {
		for (OrderSource at : OrderSource.values()) {
			if (at.type == type) {
				return at;
			}
		}
		throw new IllegalArgumentException("Not supported client type:" + type);
	}
}
