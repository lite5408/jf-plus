package com.jf.plus.core.mall.entity;

import cn.iutils.sys.entity.DataEntity;

/**
 * 商城站点表
 * 
 * @author Tng
 * @version 1.0
 */
public class MallSite extends DataEntity<MallSite> {

	private static final long serialVersionUID = 1L;

	private Long orgId;// 组织id
	private String siteName;// 站点名称
	private String siteDomain;// 站点域名
	private Integer cashRate;// 积分和现金兑换比例
	private String cashUnit;// 货币单位
	private String frontModule;// 前台模块（nav，cart）
	private String hotline;// 客户电话
	private String siteUserIds;// 站点管理员id集合
	private String siteUserNames;// 管理员名称集合
	private Double balAmount;// 商城积分余额
	private String paywayConf;// 支付方式配置（ye,ycbpay）
	private String loginWay;// 登录方式
	private String siteType;// 站点类型(jc,bg,ls)
	private Integer ratio;// 余额校验比例
	private Integer isDebug;// 调试模式

	/** 自定义属性 */
	private String orgName;

	public MallSite() {
		super();
	}

	public MallSite(String id) {
		super(id);
	}

	public Long getOrgId() {
		return orgId;
	}

	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}

	public String getSiteName() {
		return siteName;
	}

	public void setSiteName(String siteName) {
		this.siteName = siteName;
	}

	public String getSiteDomain() {
		return siteDomain;
	}

	public void setSiteDomain(String siteDomain) {
		this.siteDomain = siteDomain;
	}

	public Integer getCashRate() {
		return cashRate;
	}

	public void setCashRate(Integer cashRate) {
		this.cashRate = cashRate;
	}

	public String getCashUnit() {
		return cashUnit;
	}

	public void setCashUnit(String cashUnit) {
		this.cashUnit = cashUnit;
	}

	public String getFrontModule() {
		return frontModule;
	}

	public void setFrontModule(String frontModule) {
		this.frontModule = frontModule;
	}

	public String getHotline() {
		return hotline;
	}

	public void setHotline(String hotline) {
		this.hotline = hotline;
	}

	public String getSiteUserIds() {
		return siteUserIds;
	}

	public void setSiteUserIds(String siteUserIds) {
		this.siteUserIds = siteUserIds;
	}

	public String getSiteUserNames() {
		return siteUserNames;
	}

	public void setSiteUserNames(String siteUserNames) {
		this.siteUserNames = siteUserNames;
	}

	public Double getBalAmount() {
		return balAmount;
	}

	public void setBalAmount(Double balAmount) {
		this.balAmount = balAmount;
	}

	public String getPaywayConf() {
		return paywayConf;
	}

	public void setPaywayConf(String paywayConf) {
		this.paywayConf = paywayConf;
	}

	public String getLoginWay() {
		return loginWay;
	}

	public void setLoginWay(String loginWay) {
		this.loginWay = loginWay;
	}

	public String getSiteType() {
		return siteType;
	}

	public void setSiteType(String siteType) {
		this.siteType = siteType;
	}

	public Integer getRatio() {
		return ratio;
	}

	public void setRatio(Integer ratio) {
		this.ratio = ratio;
	}

	public Integer getIsDebug() {
		return isDebug;
	}

	public void setIsDebug(Integer isDebug) {
		this.isDebug = isDebug;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

}
