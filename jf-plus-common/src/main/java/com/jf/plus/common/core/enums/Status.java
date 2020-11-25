
/**   
 * @Title: Status.java
 * @Package com.djt.common.core.enums
 * @Description: 状态枚举类
 * @author tangyh   
 * @date 2016年1月27日 下午7:11:12
 * @version V1.0   
 */


package com.jf.plus.common.core.enums;

/**
 * @ClassName: Status
 * @Description: 状态枚举类
 * @author tangyh
 * @date 2016年1月27日 下午7:11:12
 * 
 */

public enum Status {
	NORMAL(1, "正常"),
	LOCK(0,"禁用"),
	DELETE(-1,"删除");
	
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

	private Status(int type, String description)
	{
		this.type = type;
		this.description = description;
	}
	
	public static Status getByType(int type)
	{
		for(Status at:Status.values()){
			if(at.type==type){
				return at;
			}
		}
		throw new IllegalArgumentException("Not supported client type:" + type); 
	}
}
