package com.jf.plus.common.vo;

import java.io.Serializable;

public class MyProcessVo implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	private String id;
	private String name;
	private String assignee;
	private String status;
	private int isLast;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAssignee() {
		return assignee;
	}

	public void setAssignee(String assignee) {
		this.assignee = assignee;
	}

	public int getIsLast() {
		return isLast;
	}

	public void setIsLast(int isLast) {
		this.isLast = isLast;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}
