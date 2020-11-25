package com.jf.plus.common.core.enums;

/**
 * @ClassName: ImageType
 * @Description: 图片枚举类
 * @author tangyh
 * @date 2016年1月27日 下午7:11:12
 * 
 */

public enum ImageType {
	PRODUCT(1,"商品"),
	MERCHANT(2,"商户"),
	PACKSINFO(3,"礼包"),
	CATE(4,"商品类别"),
	ADVERT(5,"广告"),
	SPECIAL(6,"专题");
	
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

	private ImageType(int type, String description)
	{
		this.type = type;
		this.description = description;
	}
	
	public static ImageType getByType(int type)
	{
		for(ImageType at:ImageType.values()){
			if(at.type==type){
				return at;
			}
		}
		throw new IllegalArgumentException("Not supported client type:" + type); 
	}
	
	public static ImageType getEnumFromString(String string){
         if(string!=null){
            try{
                return Enum.valueOf(ImageType.class, string.trim());
             }
             catch(IllegalArgumentException ex){  
            	 throw new IllegalArgumentException("Not supported client string :" + string); 
             }
        }
        return null;
     }
}
