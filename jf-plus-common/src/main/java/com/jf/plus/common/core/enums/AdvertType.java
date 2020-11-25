package com.jf.plus.common.core.enums;

/**
 * @ClassName: AdvertType
 * @Description: 广告类型枚举类
 * @author lh
 * @date 2016年8月23日 下午14:45:12
 *
 */

public enum AdvertType {
	ADVERT(1, "广告"),
	SPECIAL(2,"专题"),
	CLASS(3,"分类");

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

	private AdvertType(int type, String description)
	{
		this.type = type;
		this.description = description;
	}

	public static AdvertType getByType(int type)
	{
		for(AdvertType at:AdvertType.values()){
			if(at.type==type){
				return at;
			}
		}
		throw new IllegalArgumentException("Not supported client type:" + type);
	}
}
