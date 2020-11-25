package com.jf.plus.common.core.enums;

public enum AccountStatus {
	ACTIVATE(1, "激活"),
	FORBIDDEN(0, "禁用");
	
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

	private AccountStatus(int type, String description)
	{
		this.type = type;
		this.description = description;
	}
	
	public static AccountStatus getByType(int type)
	{
		for(AccountStatus at:AccountStatus.values()){
			if(at.type==type){
				return at;
			}
		}
		throw new IllegalArgumentException("Not supported client type:" + type); 
	}
}
