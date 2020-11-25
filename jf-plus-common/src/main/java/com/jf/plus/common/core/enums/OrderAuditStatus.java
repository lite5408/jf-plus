
package com.jf.plus.common.core.enums;

/**
 * @ClassName: OrderAuditStatus
 * @Description: 审批单状态枚举类
 * @author lh
 * @date 2016年3月15日 上午10:42:12
 *
 */

public enum OrderAuditStatus {
	NEWCREATED(0,"新创建"),
	LEVEL_1(11,"一级审批"),
	LEVEL_2(12,"二级审批"),
	TO_TRACK(21,"待发货"),
	TORECEIVE(22,"待收货"),
	FINISH(23,"已完成"),
	MERGE(24,"已合并"),
	CANCEL(4,"撤销"),
	BUYER_CACEL(41,"买手取消"),
	EXPIRE(42,"过期取消");

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

	private OrderAuditStatus(int type, String description)
	{
		this.type = type;
		this.description = description;
	}

	public static OrderAuditStatus getByType(int type)
	{
		for(OrderAuditStatus at:OrderAuditStatus.values()){
			if(at.type==type){
				return at;
			}
		}
		throw new IllegalArgumentException("Not supported client type:" + type);
	}
}
