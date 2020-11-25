
package com.jf.plus.common.core.enums;

/**
 * 包裹状态
 * @author Tng
 *
 */
public enum PackageStatus {
	WAITING_DELIVERY("WAITING_DELIVERY","等待发货"),
	START_DELIVERY("START_DELIVERY","已发货(等待收货)"),
	WAITING_COMMENT("WAITING_COMMENT","已收货(等待评论)"),
	COMMENTED("COMMENTED","已评价"),
	SYS_CANCEL("SYS_CANCEL","系统取消"),
	USER_CANCEL("USER_CANCEL","用户取消"),
	KF_CANCEL("KF_CANCEL","客服取消"),
	USER_PAYED_CANCEL("USER_PAYED_CANCEL","用户付款后取消");

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

	private PackageStatus(String type, String description)
	{
		this.type = type;
		this.description = description;
	}

	public static PackageStatus getByType(String type)
	{
		for(PackageStatus at:PackageStatus.values()){
			if(at.type==type){
				return at;
			}
		}
		throw new IllegalArgumentException("Not supported client type:" + type);
	}
}
