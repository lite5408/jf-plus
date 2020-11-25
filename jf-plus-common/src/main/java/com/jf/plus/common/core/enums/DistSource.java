package com.jf.plus.common.core.enums;

public enum DistSource {
	ORGANIZATION_DIST(1, "机构分发"),
	SITE_DIST(0, "站点管理员分发"),
	MANAGER_DIST(2, "业务经理分发");

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

	private DistSource(int type, String description)
	{
		this.type = type;
		this.description = description;
	}

	public static DistSource getByType(int type)
	{
		for(DistSource at:DistSource.values()){
			if(at.type==type){
				return at;
			}
		}
		throw new IllegalArgumentException("Not supported client type:" + type);
	}
}
