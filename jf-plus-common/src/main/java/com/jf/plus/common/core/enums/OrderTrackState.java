package com.jf.plus.common.core.enums;

/**
 * @ClassName: OrderTrackState
 * @Description: 订单配送状态
 * @author Tng
 * @date 2016年11月7日
 * 
 */

public enum OrderTrackState {
	NEW(0, "新建"),
	DELIVERED(1,"已发货"),
	REFUSED(2,"拒收"),
	TUOTOU(3,"妥投");
	
	private int type;
	private String description;

	public int getType()
	{
		return type;
	}

	public void setType(int type)
	{
		this.type = type;
	}

	public String getDescription()
	{
		return description;
	}

	public void setDescription(String description)
	{
		this.description = description;
	}

	private OrderTrackState(int type, String description)
	{
		this.type = type;
		this.description = description;
	}
	
	public static OrderTrackState getByType(int type)
	{
		for(OrderTrackState at:OrderTrackState.values()){
			if(at.type==type){
				return at;
			}
		}
		throw new IllegalArgumentException("Not supported client type:" + type); 
	}
}
