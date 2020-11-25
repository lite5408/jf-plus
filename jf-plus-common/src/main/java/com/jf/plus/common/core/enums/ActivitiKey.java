
package com.jf.plus.common.core.enums;

/**
 * 用户类型
 * @author Tng
 *
 */
public enum ActivitiKey {
	AUDIT_1("audit1","一级审批"),
	AUDIT_2("audit2","二级审批"),
	ADMIN_1("admin1","一级审批人"),
	ADMIN_2("admin2","二级审批人"),
	AOTO_1("aoto1","一级自动通过"),
	AOTO_2("aoto2","二级自动通过"),
	CANCEL("cancel","用户撤销"),
	EXPIRED("expired","过期取消"),
	ORDERID("orderId","订单ID"),
	ORGID("orgId","机构ID"),
	REASONS("reasons","拒绝原因");

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

	private ActivitiKey(String type, String description)
	{
		this.type = type;
		this.description = description;
	}

	public static ActivitiKey getByType(String type)
	{
		for(ActivitiKey at:ActivitiKey.values()){
			if(at.type==type){
				return at;
			}
		}
		throw new IllegalArgumentException("Not supported client type:" + type);
	}
}
