
/**   
 * @Title: BoolStatus.java
 * @Package com.djt.common.core.enums
 * @Description: (用一句话描述该文件做什么)
 * @author hbh   
 * @date 2016-3-22 下午2:44:18
 * @version V1.0   
 */


package com.jf.plus.common.core.enums;


/**
 * @ClassName: BoolStatus
 * @Description: 是否枚举类
 * @author hbh
 * @date 2016-3-22 下午2:44:18
 * 
 */

public enum BoolStatus {
	
	YES(1,"是"),NO(0,"否");
	
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
	

	private BoolStatus(int type, String description)
	{
		this.type = type;
		this.description = description;
	}
	
	public static BoolStatus getByType(int type)
	{
		for(BoolStatus at:BoolStatus.values()){
			if(at.type==type){
				return at;
			}
		}
		throw new IllegalArgumentException("Not supported client type:" + type); 
	}

}
