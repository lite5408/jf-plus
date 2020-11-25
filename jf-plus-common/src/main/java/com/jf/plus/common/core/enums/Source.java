
package com.jf.plus.common.core.enums;


/**
 * 
 * @ClassName: Source
 * @Description:来源
 * @author tangyh
 * @date 2016年3月9日 下午2:17:58
 *
 */
public enum Source {
	IOS(1,"iOS"),
	ANDROID(2,"Android"),
	H5(3,"H5"),
	PC(4,"PC"),
	OTHER(5,"其他"),
	SYS(6,"系统录入"),
	QUICKREG(7,"快捷注册");
	
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

	private Source(int type, String description)
	{
		this.type = type;
		this.description = description;
	}
	
	public static Source getByType(int type)
	{
		for(Source at:Source.values()){
			if(at.type==type){
				return at;
			}
		}
		throw new IllegalArgumentException("Not supported client type:" + type); 
	}
}
