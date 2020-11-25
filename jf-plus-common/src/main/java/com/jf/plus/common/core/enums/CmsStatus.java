
package com.jf.plus.common.core.enums;

/**
 * @ClassName: CmsStatus
 * @Description: 内容状态
 * @author Tng
 * 
 */

public enum CmsStatus {
	NEW_CREATE(0, "新创建"), 
	PUBLIC(1, "发布"), 
	DELETE(-1, "删除");

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

	private CmsStatus(int type, String description) {
		this.type = type;
		this.description = description;
	}

	public static CmsStatus getByType(int type) {
		for (CmsStatus at : CmsStatus.values()) {
			if (at.type == type) {
				return at;
			}
		}
		throw new IllegalArgumentException("Not supported client type:" + type);
	}
}
