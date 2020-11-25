
/**   
 * @Title: MemberStatus.java
 * @Package com.jf.plus.common.core.enums
 * @Description: 会员状态
 * @author he   
 * @date 2016-3-13 下午2:51:33
 * @version V1.0   
 */


package com.jf.plus.common.core.enums;


/**
 * @ClassName: MemberStatus
 * @Description: 会员状态
 * @author he
 * @date 2016-3-13 下午2:51:33
 * 
 */

public enum MemberStatus {

	NORMAL(2,"正常"),
	LOCKED(1,"锁定 "),
	BLACK(0,"拉黑 ");
	
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
	private MemberStatus(int type, String description) {
		this.type = type;
		this.description = description;
	}
	public static MemberStatus getByType(int type)
	{
		for(MemberStatus at:MemberStatus.values()){
			if(at.type==type){
				return at;
			}
		}
		throw new IllegalArgumentException("Not supported client type:" + type); 
	}
	
	
}
