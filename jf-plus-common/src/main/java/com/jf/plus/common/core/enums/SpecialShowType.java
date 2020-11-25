
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
 * @ClassName: SpecialShowType
 * @Description: 专题类型枚举类
 * @author lh
 * @date 2016年8月23日 下午14:05:12
 * 
 */

public enum SpecialShowType {
	NORMAL(1, "一般专题"),
	SHOW(2,"展示专题");
	
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

	private SpecialShowType(int type, String description)
	{
		this.type = type;
		this.description = description;
	}
	
	public static SpecialShowType getByType(int type)
	{
		for(SpecialShowType at:SpecialShowType.values()){
			if(at.type==type){
				return at;
			}
		}
		throw new IllegalArgumentException("Not supported client type:" + type); 
	}
}
