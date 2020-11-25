package com.jf.plus.common.vo;

import java.io.Serializable;
import java.util.Date;

public class SupplyerUserVo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String adminLoginName;
	
	private String adminPwd;
	
	private String supplyId;
	
	private String supplyName;
	
	private Date loginDate;

	public String getAdminLoginName() {
		return adminLoginName;
	}

	public void setAdminLoginName(String adminLoginName) {
		this.adminLoginName = adminLoginName;
	}

	public String getAdminPwd() {
		return adminPwd;
	}

	public void setAdminPwd(String adminPwd) {
		this.adminPwd = adminPwd;
	}

	public String getSupplyId() {
		return supplyId;
	}

	public void setSupplyId(String supplyId) {
		this.supplyId = supplyId;
	}

	public String getSupplyName() {
		return supplyName;
	}

	public void setSupplyName(String supplyName) {
		this.supplyName = supplyName;
	}

	public Date getLoginDate() {
		return loginDate;
	}

	public void setLoginDate(Date loginDate) {
		this.loginDate = loginDate;
	}
	
	
}
