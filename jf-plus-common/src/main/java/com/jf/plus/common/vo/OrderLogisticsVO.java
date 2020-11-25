package com.jf.plus.common.vo;

import java.io.Serializable;

/**
 * 通用物流信息
 * @author Tng
 *
 */
public class OrderLogisticsVO implements Serializable{

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	private String orderItemId;

	private String skuId;

	public String getOrderItemId() {
		return orderItemId;
	}

	public void setOrderItemId(String orderItemId) {
		this.orderItemId = orderItemId;
	}

	public String getSkuId() {
		return skuId;
	}

	public void setSkuId(String skuId) {
		this.skuId = skuId;
	}

}
