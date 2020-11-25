
package com.jf.plus.common.core.enums;

/**
 * @ClassName: PaymentMode
 * @Description: 支付方式枚举类
 * @author lh
 * @date 2016年4月25日 上午11:02:12
 * 
 */

public enum PaymentMode {
	WX(1,"微信支付"),
	ZFB(2, "支付宝支付");
	
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

	private PaymentMode(int type, String description)
	{
		this.type = type;
		this.description = description;
	}
	
	public static PaymentMode getByType(int type)
	{
		for(PaymentMode at:PaymentMode.values()){
			if(at.type==type){
				return at;
			}
		}
		throw new IllegalArgumentException("Not supported client type:" + type); 
	}
}
