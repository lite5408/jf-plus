
package com.jf.plus.common.core.enums;

/**
 * @ClassName: SplitOrderType
 * @Description: 拆单父子订单
 * @author Tng
 * 
 */

public enum SplitOrderType {
	PARENT(1,"父订单"),
	CHILD(2,"子订单")
	;
	
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

	private SplitOrderType(int type, String description)
	{
		this.type = type;
		this.description = description;
	}
	
	public static SplitOrderType getByType(int type)
	{
		for(SplitOrderType at:SplitOrderType.values()){
			if(at.type==type){
				return at;
			}
		}
		throw new IllegalArgumentException("Not supported client type:" + type); 
	}
}
