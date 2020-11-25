package com.jf.plus.common.core.enums;

/**
 * 
 * @ClassName: PayWayConf
 * @Description: 支付方式配置
 * @author Tng
 * @date 2016年11月26日 下午4:28:18
 *
 */
public enum PayWayConf {
	YEPAY("ye", "余额支付");
	
	private String type;
	private String description;

	

	public String getType() {
		return type;
	}

	public void setType(String type) {
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

	private PayWayConf(String type, String description)
	{
		this.type = type;
		this.description = description;
	}
	
	public static PayWayConf getByType(int type)
	{
		for(PayWayConf at:PayWayConf.values()){
			if(at.type.equals(type)){
				return at;
			}
		}
		throw new IllegalArgumentException("Not supported client type:" + type); 
	}
}
