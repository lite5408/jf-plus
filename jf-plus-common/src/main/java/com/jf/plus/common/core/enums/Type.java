
package com.jf.plus.common.core.enums;

/**
 * 资源类型
 * @author Tng
 *
 */
public enum Type {
	PUBLIC("PUBLIC","平台共有"),
	PRIVATE("PRIVATE","供应商私有");

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

	private Type(String type, String description)
	{
		this.type = type;
		this.description = description;
	}

	public static Type getByType(String type)
	{
		for(Type at:Type.values()){
			if(at.type==type){
				return at;
			}
		}
		throw new IllegalArgumentException("Not supported client type:" + type);
	}
}
