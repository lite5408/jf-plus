
/**   
 * @Title: DictType.java
 * @Package com.djt.common.core.enums
 * @Description: (用一句话描述该文件做什么)
 * @author hbh   
 * @date 2016-3-5 下午5:51:18
 * @version V1.0   
 */


package com.jf.plus.common.core.enums;


/**
 * @ClassName: DictType
 * @Description: 字典类型
 * @author hbh
 * @date 2016-3-5 下午5:51:18
 * 
 */

public enum DictType {
	
	AREA("area_dict","货源地区"),
	SALE_AREA("sale_area_dict","销售区域");
	
	private String type;
	private String description;
	
	public String getType() {
		return type;
	}


	public void setType(String type) {
		this.type = type;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	private DictType(String type,String description){
		this.type = type;
		this.description = description;
	}
	
	public static DictType getByType(String type)
	{
		for(DictType at:DictType.values()){
			if(at.type.equals(type)){
				return at;
			}
		}
		throw new IllegalArgumentException("Not supported client type:" + type); 
	}
}
