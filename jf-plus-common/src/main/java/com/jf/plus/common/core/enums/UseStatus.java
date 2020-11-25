
package com.jf.plus.common.core.enums;

/**
 * 券、礼包使用状态
 *
 * @author zhanglong
 *
 */
public enum UseStatus {
	EXPIRE(-1, "过期"), NOT_USED(0, "未使用"), USED(1, "已使用"), PRESENT(2, "已赠送");

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

	private UseStatus(int type, String description) {
		this.type = type;
		this.description = description;
	}

	public static UseStatus getByType(int type) {
		for (UseStatus at : UseStatus.values()) {
			if (at.type == type) {
				return at;
			}
		}
		throw new IllegalArgumentException("Not supported client type:" + type);
	}
}
