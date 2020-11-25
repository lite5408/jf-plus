
package com.jf.plus.common.core.enums;

/**
 * 
 * @ClassName: ProductStatus
 * @Description: 商品状态
 * @author Tng
 * @date 2016年8月5日 下午2:12:47
 *
 */
public enum ProductStatus {
	TO_PICK(1,"待挑选"),
	TO_AUDIT(11,"待审核"),
	PICK(2,"已挑选"),
	SHELVES(3,"上架"),
	OUT_SHELVES(4,"下架"),
	AUDIT_FAIL(41,"审核不通过")
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

	private ProductStatus(int type, String description)
	{
		this.type = type;
		this.description = description;
	}
	
	public static ProductStatus getByType(int type)
	{
		for(ProductStatus at:ProductStatus.values()){
			if(at.type==type){
				return at;
			}
		}
		throw new IllegalArgumentException("Not supported client type:" + type); 
	}
}
