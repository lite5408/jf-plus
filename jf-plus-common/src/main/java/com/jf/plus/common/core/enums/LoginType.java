
package com.jf.plus.common.core.enums;

public enum LoginType {

	USER("User", "机构登录"), SITE("Site","站点登录"), SUPPLY("Supply", "供应商登录");

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

	private LoginType(String type, String description) {
		this.type = type;
		this.description = description;
	}

}
