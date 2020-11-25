
/**   
 * @Title: WexinMsgType.java
 * @Package com.djt.common.core.enums
 * @Description: 消息类型
 * @author hbh   
 * @date 2016年1月27日 下午7:11:12
 * @version V1.0   
 */

package com.jf.plus.common.core.enums;

/**
 * @ClassName: WexinMsgType
 * @Description: 消息类型
 * @author hbh
 * @date 2016年1月27日 下午7:11:12
 * 
 */

public enum WexinMsgType {
	
	/**
	 * 文本
	 */
	TEXT("text","文本"),
	
    /**
     * 图片
     */
    IMAGE("image","图片"),

    /**
     * 语音
     */
    //VOICE("voice","语音"),

    /**
     * 视频
     */
    //VIDEO("video","视频"),

    /**
     * 图文消息
     */
    NEWS("news","图文消息");

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

	private WexinMsgType(String type, String description)
	{
		this.type = type;
		this.description = description;
	}
	
	public static WexinMsgType getByType(String type)
	{
		for(WexinMsgType at:WexinMsgType.values()){
			if(at.type.equals(type)){
				return at;
			}
		}
		throw new IllegalArgumentException("Not supported client type:" + type); 
	}
}
