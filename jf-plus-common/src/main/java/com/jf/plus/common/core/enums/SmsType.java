
/**
 * @Title: SmsType.java
 * @Package com.shengbaobao.core.enums
 * @Description: 短信类型
 * @author he
 * @date 2016年7月09日 下午15:11:12
 * @version V1.0
 */

package com.jf.plus.common.core.enums;

/**
 * @ClassName: SmsType
 * @Description: 短信类型
 * @author he
 * @date 2016年7月09日 下午15:11:12
 *
 */

public enum SmsType {
	CONFIRM_ORDER(1, "订单确认验证码短信"), USER_LOGIN(2, "零售用户登录验证码");

	private int type;
	private String description;

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	private SmsType(int type, String description) {
		this.type = type;
		this.description = description;
	}

	public static SmsType getByType(int type) {
		for (SmsType st : SmsType.values()) {
			if (st.type == type) {
				return st;
			}
		}
		throw new IllegalArgumentException("Not supported client type:" + type);
	}
}
