package com.jf.plus.common.core.enums;

public enum WeixinMenuType {
	CLICK("click", "点击推事件"), 
	
	VIEW("view", "跳转URL");
	
	
	// scancode_push("scancode_push","扫码推事件"),
	// scancode_waitmsg("scancode_waitmsg","扫码推事件且弹出“消息接收中”提示框"),
	// pic_sysphoto("pic_sysphoto","弹出系统拍照发图"),
	// pic_photo_or_album("pic_photo_or_album","弹出拍照或者相册发图"),
	// pic_weixin("pic_weixin","弹出微信相册发图器"),
	// location_select("location_select","弹出地理位置选择器"),
	// media_id("media_id","下发消息（除文本消息）"),
	// view_limited("view_limited","跳转图文消息URL");

	//  请注意，3到8的所有事件，仅支持微信iPhone5.4.1以上版本，
	//	和Android5.4以上版本的微信用户，旧版本微信用户点击后将没有回应，
	//	开发者也不能正常接收到事件推送。
	//	9和10，是专门给第三方平台旗下未微信认证（具体而言，是资质认证未通过）的订阅号准备的事件类型，
	//	它们是没有事件推送的，能力相对受限，其他类型的公众号不必使用。

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

	private WeixinMenuType(String type, String description) {
		this.type = type;
		this.description = description;
	}

	public static WeixinMenuType getByType(int type) {
		for (WeixinMenuType at : WeixinMenuType.values()) {
			if (at.type.equals(type)) {
				return at;
			}
		}
		throw new IllegalArgumentException("Not supported client type:" + type);
	}
}
