
package com.jf.plus.common.core.enums;

/**
 * 
 * @ClassName: ProductType
 * @Description: 产品所属
 * @author tangyh
 * @date 2016年3月11日 上午10:51:48
 *
 */
public enum ProductType {
	PLAT(1,"平台共有"),
	SUPPLYER(2,"供应商私有")
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

	private ProductType(int type, String description)
	{
		this.type = type;
		this.description = description;
	}
	
	public static ProductType getByType(int type)
	{
		for(ProductType at:ProductType.values()){
			if(at.type==type){
				return at;
			}
		}
		throw new IllegalArgumentException("Not supported client type:" + type); 
	}
}
