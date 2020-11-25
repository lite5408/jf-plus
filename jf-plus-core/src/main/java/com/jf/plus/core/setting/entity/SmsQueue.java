package com.jf.plus.core.setting.entity;

import java.util.Date;

import cn.iutils.sys.entity.DataEntity;

/**
 * 短信队列表
 * 
 * @author Tng
 * @version 1.0
 */
public class SmsQueue extends DataEntity<SmsQueue> {

	private static final long serialVersionUID = 1L;

	private Long orgId;// 组织id
	private Long siteId;// 站点id
	private String mobile;// 手机号
	private Integer type;// 类型
	private String content;// 模板内容
	private Date processTime;// 发送时间
	private Date expiredTime;// 有效期时间

	public SmsQueue() {
		super();
	}

	public SmsQueue(String id) {
		super(id);
	}

	public Long getOrgId() {
		return orgId;
	}

	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}

	public Long getSiteId() {
		return siteId;
	}

	public void setSiteId(Long siteId) {
		this.siteId = siteId;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getProcessTime() {
		return processTime;
	}

	public void setProcessTime(Date processTime) {
		this.processTime = processTime;
	}

	public Date getExpiredTime() {
		return expiredTime;
	}

	public void setExpiredTime(Date expiredTime) {
		this.expiredTime = expiredTime;
	}

}
