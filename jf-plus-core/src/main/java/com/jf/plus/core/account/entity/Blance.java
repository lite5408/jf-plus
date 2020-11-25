package com.jf.plus.core.account.entity;

import cn.iutils.sys.entity.DataEntity;

/**
 * 余额
 * 
 * @author Tng
 * @version 1.0
 */
public class Blance extends DataEntity<Blance> {

	private static final long serialVersionUID = 1L;

	private Long orgId;//机构Id
	private String userId;//用户Id

	public Blance() {
		super();
	}

	public Blance(String id) {
		super(id);
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Long getOrgId() {
		return orgId;
	}

	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}
	

}
