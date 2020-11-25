
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

public enum WexinTemplateType {
	SUBSCRIBE("weixin_subscribe","关注消息"),
	KEYWORD("weixin_keyword","关键字消息"),
	MENU_MSG("menu_msg","菜单消息")
	;
	
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

	private WexinTemplateType(String type, String description)
	{
		this.type = type;
		this.description = description;
	}
	
	public static WexinTemplateType getByType(String type)
	{
		for(WexinTemplateType at:WexinTemplateType.values()){
			if(at.type.equals(type)){
				return at;
			}
		}
		throw new IllegalArgumentException("Not supported client type:" + type); 
	}
}
