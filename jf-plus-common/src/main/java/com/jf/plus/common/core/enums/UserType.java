
package com.jf.plus.common.core.enums;

/**
 * 用户类型
 * @author Tng
 *
 */
public enum UserType {
	ADMIN(1,"管理员"),
	SALER(2,"采购员"),
	MEMBER(3,"C端会员"),
	BUYER(4,"买手");

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

	private UserType(int type, String description)
	{
		this.type = type;
		this.description = description;
	}

	public static UserType getByType(int type)
	{
		for(UserType at:UserType.values()){
			if(at.type==type){
				return at;
			}
		}
		throw new IllegalArgumentException("Not supported client type:" + type);
	}
}
