
package com.jf.plus.common.core.enums;

/**
 * 工作流value
 *
 * @author Tng
 *
 */
public enum ActivitiStatus {
	ALL(0, "所有"), UNFINISHED(1, "未完成"), FINISHED(2, "已完成");

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

	private ActivitiStatus(int type, String description) {
		this.type = type;
		this.description = description;
	}

	public static ActivitiStatus getByType(int type) {
		for (ActivitiStatus at : ActivitiStatus.values()) {
			if (at.type == type) {
				return at;
			}
		}
		throw new IllegalArgumentException("Not supported client type:" + type);
	}
}
