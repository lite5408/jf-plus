
package com.jf.plus.common.core.enums;

/**
 * 
 * @ClassName: ProductType
 * @Description: 团长分组
 * @author tangyh
 * @date 2016年3月11日 上午10:51:48
 *
 */
public enum PersonType {
	TZ("inviter_tz","普通团长"),
	KF("inviter_kf","客服团长"),
	DT("inviter_dt","推广团长");
	
	private String type;
	private String description;

	public String getType()
	{
		return type;
	}

	public void setType(String type)
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

	private PersonType(String type, String description)
	{
		this.type = type;
		this.description = description;
	}
	
	public static PersonType getByType(String type)
	{
		for(PersonType at:PersonType.values()){
			if(at.type==type){
				return at;
			}
		}
		throw new IllegalArgumentException("Not supported client type:" + type); 
	}
}
