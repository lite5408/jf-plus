
package com.jf.plus.common.core.enums;

/**
 * 工作流value
 * @author Tng
 *
 */
public enum ActivitiValue {
	TRUE("true","通过"),
	FALSE("false","不通过"),
	NONE("none","空");

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

	private ActivitiValue(String type, String description)
	{
		this.type = type;
		this.description = description;
	}

	public static ActivitiValue getByType(String type)
	{
		for(ActivitiValue at:ActivitiValue.values()){
			if(at.type==type){
				return at;
			}
		}
		throw new IllegalArgumentException("Not supported client type:" + type);
	}
}
