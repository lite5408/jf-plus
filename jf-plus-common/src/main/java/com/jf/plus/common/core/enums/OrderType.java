
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
 * @ClassName: OrderType
 * @Description: 订单类型枚举类
 * @author he
 * @date 2016年8月9日 下午7:11:12
 * 
 */

public enum OrderType {
	PackOrder(1,"礼包订单"),
	GoodsOrder(2,"商品订单"),
	SkillOrder(3,"秒杀订单"),
	MtOrder(4,"拼团订单");
	
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

	private OrderType(int type, String description)
	{
		this.type = type;
		this.description = description;
	}
	
	public static OrderType getByType(int type)
	{
		for(OrderType at:OrderType.values()){
			if(at.type==type){
				return at;
			}
		}
		throw new IllegalArgumentException("Not supported client type:" + type); 
	}
}
