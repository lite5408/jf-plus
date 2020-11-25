
package com.jf.plus.common.core.enums;

/**
 * @ClassName: Gender
 * @Description: 团购申请状态
 * @author tangyh
 * @date 2016年1月27日 下午7:11:12
 * 
 */

public enum Gender {
	MAIL(2, "女"),
	FEMAIL(1, "男"),
	UNKOWN(0, "保密");
	
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

	private Gender(int type, String description)
	{
		this.type = type;
		this.description = description;
	}
	
	public static Gender getByType(int type)
	{
		for(Gender at:Gender.values()){
			if(at.type==type){
				return at;
			}
		}
		throw new IllegalArgumentException("Not supported client type:" + type); 
	}
}
