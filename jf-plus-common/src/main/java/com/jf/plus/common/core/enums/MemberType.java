
/**   
 * @Title: MemberType.java
 * @Package com.jf.plus.common.core.enums
 * @Description: 会员类型
 * @author hbh   
 * @date 2016-3-13 下午2:51:33
 * @version V1.0   
 */


package com.jf.plus.common.core.enums;


/**
 * @ClassName: MemberType
 * @Description: 会员类型
 * @author hbh
 * @date 2016-3-13 下午2:51:33
 * 
 */

public enum MemberType {
	
	MEMBER(2,"员工"),
	BOSS(1,"老板 ");
	
	private int type;
	private String description;
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	private MemberType(int type, String description) {
		this.type = type;
		this.description = description;
	}
	public static MemberType getByType(int type)
	{
		for(MemberType at:MemberType.values()){
			if(at.type==type){
				return at;
			}
		}
		throw new IllegalArgumentException("Not supported client type:" + type); 
	}
	
	
}
