package cn.iutils.common.filter;

import org.apache.shiro.authc.UsernamePasswordToken;

public class CustomUserPasswordToken extends UsernamePasswordToken {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	//登录类型
	private String loginType;

	public CustomUserPasswordToken(final String username, final String password, String loginType) {
		super(username, password);
		this.loginType = loginType;
	}

	public String getLoginType() {
		return loginType;
	}

	public void setLoginType(String loginType) {
		this.loginType = loginType;
	}
}
