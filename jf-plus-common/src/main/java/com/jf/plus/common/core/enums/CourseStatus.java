
package com.jf.plus.common.core.enums;

/**
 * @ClassName: CourseStatus
 * @Description: 课程状态
 * @author Tng
 * 
 */

public enum CourseStatus {
	NEW_CREATE(0, "新创建"), 
	ING(1, "进行中"), 
	END(2,"已结束"),
	REPLAY(3,"回访"),
	NOTICE(4,"预告"),
	DELETE(-1, "删除");
	
	//状态（ 状态 1：进行中 2：已结束 3：回放 4：预告）

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

	private CourseStatus(int type, String description) {
		this.type = type;
		this.description = description;
	}

	public static CourseStatus getByType(int type) {
		for (CourseStatus at : CourseStatus.values()) {
			if (at.type == type) {
				return at;
			}
		}
		throw new IllegalArgumentException("Not supported client type:" + type);
	}
}
