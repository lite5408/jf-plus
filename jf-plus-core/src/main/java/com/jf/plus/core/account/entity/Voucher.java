package com.jf.plus.core.account.entity;

import java.util.Date;

import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson.annotation.JSONField;

import cn.iutils.common.config.JConfig;
import cn.iutils.sys.entity.DataEntity;

/**
 * 电子券信息表
 * @author Tng
 * @version 1.0
 */
public class Voucher extends DataEntity<Voucher>{

	private static final long serialVersionUID = 1L;

	private Long orgId;//组织id
	private Long siteId;// 站点ID
	private String name;//电子券名称
	private Integer ratio;// 比例
	@JSONField(format = "yyyy-MM-dd")
	private Date validStartDate;//有效开始日期
	@JSONField(format = "yyyy-MM-dd")
	private Date validEndDate;//有效截止日期
	private Integer source; // 1后台自动生成  2站点创建
	private String photoUrl;//电子券图片
	private Integer isMobile;//是否手机端分发（0否，1是）
	private Double price; // 电子券面值(元)  (-1：自定义  大于等于0：固定面值）
	private String detail;//电子券详情
	private Integer operStatus;//电子券状态（0禁用，1启用）

	/**
	 * 自定义属性
	 */
	private Double blance;//余额面值
	private Double totalBlance;//积分面值
	private String showPhotoUrl; // 封面图显示

	public Voucher() {
		super();
	}
	public Voucher(String id){
		super(id);
	}

	public Long getOrgId(){
		return orgId;
	}

	public void setOrgId(Long orgId){
		this.orgId = orgId;
	}
	public String getName(){
		return name;
	}

	public void setName(String name){
		this.name = name == null ? null : name.trim();
	}

	public Date getValidStartDate() {
		return validStartDate;
	}
	public void setValidStartDate(Date validStartDate) {
		this.validStartDate = validStartDate;
	}
	public Date getValidEndDate() {
		return validEndDate;
	}
	public void setValidEndDate(Date validEndDate) {
		this.validEndDate = validEndDate;
	}
	public Integer getRatio() {
		return ratio;
	}
	public void setRatio(Integer ratio) {
		this.ratio = ratio;
	}
	public Long getSiteId() {
		return siteId;
	}
	public void setSiteId(Long siteId) {
		this.siteId = siteId;
	}
	public String getPhotoUrl() {
		return photoUrl;
	}
	public void setPhotoUrl(String photoUrl) {
		this.photoUrl = photoUrl == null ? null : photoUrl.trim();
	}
	public Integer getIsMobile() {
		return isMobile;
	}
	public void setIsMobile(Integer isMobile) {
		this.isMobile = isMobile;
	}
	public String getDetail() {
		return detail;
	}
	public void setDetail(String detail) {
		this.detail = detail == null ? null : detail.trim();;
	}
	public Integer getOperStatus() {
		return operStatus;
	}
	public void setOperStatus(Integer operStatus) {
		this.operStatus = operStatus;
	}
	public Double getBlance() {
		return blance;
	}
	public void setBlance(Double blance) {
		this.blance = blance;
	}
	public Double getTotalBlance() {
		return totalBlance;
	}
	public void setTotalBlance(Double totalBlance) {
		this.totalBlance = totalBlance;
	}
	public Integer getSource() {
		return source;
	}
	public void setSource(Integer source) {
		this.source = source;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	
	public String getShowPhotoUrl() {
		if (StringUtils.isEmpty(photoUrl))
			return null;
		return JConfig.getConfig("url.image") + "/" + photoUrl;
	}

	public void setShowPhotoUrl(String showPhotoUrl) {
		this.showPhotoUrl = showPhotoUrl == null ? null : showPhotoUrl;
	}

}
