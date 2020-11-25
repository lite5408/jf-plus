
package com.jf.plus.common.core.enums;

/**
 * 用户类型
 * @author Tng
 *
 */
public enum M_LoginType {
	SALER(0,"采购员"),
	AUDIT(1,"审核员"),
	APP(2,"APP授权");

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

	private M_LoginType(int type, String description)
	{
		this.type = type;
		this.description = description;
	}

	public static M_LoginType getByType(int type)
	{
		for(M_LoginType at:M_LoginType.values()){
			if(at.type==type){
				return at;
			}
		}
		throw new IllegalArgumentException("Not supported client type:" + type);
	}
}
