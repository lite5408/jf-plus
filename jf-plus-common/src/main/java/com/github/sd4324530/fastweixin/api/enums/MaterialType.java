package com.github.sd4324530.fastweixin.api.enums;


/**
 * @author jileilei
 */
public enum MaterialType {

	  /**
     * 文本
     */
    TEXT("text"),
    
    /**
     * 图片
     */
    IMAGE("image"),

    /**
     * 语音
     */
    VOICE("voice"),

    /**
     * 视频
     */
    VIDEO("video"),

    /**
     * 图文消息
     */
    NEWS("news");

    String value;

    MaterialType(String value) {
        this.value = value;
    }

    public static MaterialType getByValue(String value)
	{
		for(MaterialType at:MaterialType.values()){
			if(at.value.equals(value)){
				return at;
			}
		}
		throw new IllegalArgumentException("Not supported client type:" + value); 
	}
    
    @Override
    public String toString() {
        return this.value;
    }
}
