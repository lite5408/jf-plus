package com.jf.plus.common.sms;

public class SmsSenderFactory {

	public static SmsSender buildZHSmsSender() {
		SmsSender smsSender = new ZHSmsSender();
		return smsSender;
	}

	public static SmsSender buildOASmsSender() {
		SmsSender smsSender = new OASmsSender();
		return smsSender;
	}

}
