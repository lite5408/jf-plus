package com.jf.plus.common.core.enums;

/**
 * @ClassName: SiteType
 * @Description: 站点类型枚举类
 * @author zhangl
 * @date 2017年3月9日11:02
 *
 */

public enum SiteType {

	JICAI(1, "集采"), JIFEN(2, "积分");

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

	private SiteType(int type, String description) {
		this.type = type;
		this.description = description;
	}

	public static SiteType getByType(int type) {
		for (SiteType at : SiteType.values()) {
			if (at.type == type) {
				return at;
			}
		}
		throw new IllegalArgumentException("Not supported client type:" + type);
	}
}
