
package com.jf.plus.common.core.enums;

/**
 * @ClassName: OrderStatus
 * @Description: 订单状态枚举类
 * @author lh
 * @date 2016年3月15日 上午10:42:12
 *
 */

public enum OrderStatus {
	NEWCREATED(0,"新创建"),
	PAID(10,"已付款");

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

	private OrderStatus(int type, String description)
	{
		this.type = type;
		this.description = description;
	}

	public static OrderStatus getByType(int type)
	{
		for(OrderStatus at:OrderStatus.values()){
			if(at.type==type){
				return at;
			}
		}
		throw new IllegalArgumentException("Not supported client type:" + type);
	}
}
