package com.jf.plus.common.sms;

public abstract class SmsSender {
	//手机号
	protected String mobile = "";

	//短信内容
	protected String content = "";
	
	public boolean send(){
		return true;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getMobile() {
		return mobile;
	}
}
