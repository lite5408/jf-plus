package com.jf.plus.common.vo;

import java.io.Serializable;
import java.util.List;

/**
 * 通用物流信息
 * @author Tng
 *
 */
public class OrderTrackCommonVO implements Serializable{

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	private String packageId;

	private List<OrderItemIdsVO> itemList;

	private List<OrderLogisticsVO> logistList;

	public String getPackageId() {
		return packageId;
	}

	public void setPackageId(String packageId) {
		this.packageId = packageId;
	}

	public List<OrderItemIdsVO> getItemList() {
		return itemList;
	}

	public void setItemList(List<OrderItemIdsVO> itemList) {
		this.itemList = itemList;
	}

	public List<OrderLogisticsVO> getLogistList() {
		return logistList;
	}

	public void setLogistList(List<OrderLogisticsVO> logistList) {
		this.logistList = logistList;
	}

}
