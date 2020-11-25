package com.jf.plus.core.mall.entity;

import java.util.Date;

import com.alibaba.fastjson.annotation.JSONField;

import cn.iutils.sys.entity.DataEntity;

/**
 * 供应商表
 * 
 * @author Tng
 * @version 1.0
 */
public class MallSupplyer extends DataEntity<MallSupplyer> {

	private static final long serialVersionUID = 1L;

	private Long orgId;// 组织id
	private String companyName;// 公司名称
	@JSONField(name="name")
	private String companyTitle;// 公司简称
	@JSONField(serialize = false)
	private Date regTime;// 开户时间
	@JSONField(serialize = false)
	private String contact;// 联系人
	@JSONField(serialize = false)
	private String contactLink;// 联系方式
	@JSONField(serialize = false)
	private String doucuments;// 证件执照
	@JSONField(serialize = false)
	private String adminLoginname;// 管理员账号
	@JSONField(serialize = false)
	private String adminPwd;// 管理员密码
	@JSONField(serialize = false)
	private Date loginDate;// 最新登录日期
	@JSONField(serialize = false)
	private Boolean available = Boolean.TRUE;
	@JSONField(serialize = false)
	private Date startDate;//有效期（开始日期）
	@JSONField(serialize = false)
	private Date endDate;//有效期（截止日期）
	private String area;//货源地区
	private String brandIds;//品牌编码

	/** 自定义属性 */
	@JSONField(serialize = false)
	private String orgName;// 组织机构
	@JSONField(serialize = false)
	private String areaName;

	public MallSupplyer() {
		super();
	}

	public MallSupplyer(String id) {
		super(id);
	}

	public Long getOrgId() {
		return orgId;
	}

	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getCompanyTitle() {
		return companyTitle;
	}

	public void setCompanyTitle(String companyTitle) {
		this.companyTitle = companyTitle;
	}

	public Date getRegTime() {
		return regTime;
	}

	public void setRegTime(Date regTime) {
		this.regTime = regTime;
	}

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	public String getContactLink() {
		return contactLink;
	}

	public void setContactLink(String contactLink) {
		this.contactLink = contactLink;
	}

	public String getDoucuments() {
		return doucuments;
	}

	public void setDoucuments(String doucuments) {
		this.doucuments = doucuments;
	}

	public String getAdminLoginname() {
		return adminLoginname;
	}

	public void setAdminLoginname(String adminLoginname) {
		this.adminLoginname = adminLoginname;
	}

	public String getAdminPwd() {
		return adminPwd;
	}

	public void setAdminPwd(String adminPwd) {
		this.adminPwd = adminPwd;
	}

	public Date getLoginDate() {
		return loginDate;
	}

	public void setLoginDate(Date loginDate) {
		this.loginDate = loginDate;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public Boolean getAvailable() {
		return available;
	}

	public void setAvailable(Boolean available) {
		this.available = available;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getBrandIds() {
		return brandIds;
	}

	public void setBrandIds(String brandIds) {
		this.brandIds = brandIds;
	}

	public String getAreaName() {
		return areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}
	
	
}
