
/**   
 * @Title: OrgType.java
 * @Package com.djt.common.core.enums
 * @Description: 组织架构类型
 * @author tangyh   
 * @date 2016年1月27日 下午7:11:12
 * @version V1.0   
 */


package com.jf.plus.common.core.enums;

/**
 * @ClassName: OrgType
 * @Description: 组织架构类型
 * @author tangyh
 * @date 2016年1月27日 下午7:11:12
 * 
 */

public enum OrgType {
	PLAT(1, "平台"),
	COMP(2,"总公司"),
	CHD_COMP(3,"分公司"),
	COMP_TX(4,"代理商");
	
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

	private OrgType(int type, String description)
	{
		this.type = type;
		this.description = description;
	}
	
	public static OrgType getByType(int type)
	{
		for(OrgType at:OrgType.values()){
			if(at.type==type){
				return at;
			}
		}
		throw new IllegalArgumentException("Not supported client type:" + type); 
	}
}
