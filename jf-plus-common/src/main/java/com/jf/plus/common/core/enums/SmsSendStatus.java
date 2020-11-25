package com.jf.plus.common.core.enums;

public enum SmsSendStatus {
	SEND(1, "发送成功"),
	TO_SEND(0,"待发送"),
	FAIL_SEND(-1,"发送失败")
	;
	
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

	private SmsSendStatus(int type, String description)
	{
		this.type = type;
		this.description = description;
	}
	
	public static SmsSendStatus getByType(int type)
	{
		for(SmsSendStatus at:SmsSendStatus.values()){
			if(at.type==type){
				return at;
			}
		}
		throw new IllegalArgumentException("Not supported client type:" + type); 
	}
}
