package com.jf.plus.core.mallSetting.entity;

import java.util.Date;

import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson.annotation.JSONField;

import cn.iutils.common.config.JConfig;
import cn.iutils.sys.entity.DataEntity;

/**
 * 礼包信息表
 *
 * @author Tng
 * @version 1.0
 */
public class PacksInfo extends DataEntity<PacksInfo> {

	private static final long serialVersionUID = 1L;

	private Long orgId;//
	private Long siteId;//
	private Integer ratio; // 比例
	private String name;// 礼包名称
	private Double markPrice;// 市场价
	private Double salePrice;// 销售价
	private Integer isMobile;// 是否手机端分发（1是，0否）
	@JSONField(format = "yyyy-MM-dd")
	private Date validStartDate;// 有效期开始
	@JSONField(format = "yyyy-MM-dd")
	private Date validEndDate;// 有效期截止
	private String photoUrl;// 封面图
	private String photoSelectUrl;// 多图
	private String details;// 详情说明
	private String shareName;// 分享标题
	private String shareContent;// 分享内容
	private Integer isShowPrice;// 是否显示价格（1是，0否）
	private Integer operStatus;// 礼包状态（0禁用，1启用）

	/**
	 * 自定义属性
	 */
	private Long accId; // 礼包卡券ID
	private String items;// 礼包内商品
	private String showPhotoUrl; // 封面图显示
	private String showVideoUrl; // 多图显示

	public PacksInfo() {
		super();
	}

	public PacksInfo(String id) {
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name == null ? null : name.trim();
	}

	public Double getMarkPrice() {
		return markPrice;
	}

	public void setMarkPrice(Double markPrice) {
		this.markPrice = markPrice;
	}

	public Double getSalePrice() {
		return salePrice;
	}

	public void setSalePrice(Double salePrice) {
		this.salePrice = salePrice;
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

	public String getPhotoUrl() {
		return photoUrl;
	}

	public void setPhotoUrl(String photoUrl) {
		this.photoUrl = photoUrl == null ? null : photoUrl.trim();
	}

	public String getDetails() {
		return details;
	}

	public void setDetails(String details) {
		this.details = details == null ? null : details.trim();
	}

	public String getShareName() {
		return shareName;
	}

	public void setShareName(String shareName) {
		this.shareName = shareName == null ? null : shareName.trim();
	}

	public String getShareContent() {
		return shareContent;
	}

	public void setShareContent(String shareContent) {
		this.shareContent = shareContent == null ? null : shareContent.trim();
	}

	public Integer getRatio() {
		return ratio;
	}

	public void setRatio(Integer ratio) {
		this.ratio = ratio;
	}

	public Long getAccId() {
		return accId;
	}

	public void setAccId(Long accId) {
		this.accId = accId;
	}

	public String getShowPhotoUrl() {
		if (StringUtils.isEmpty(photoUrl))
			return null;
		return JConfig.getConfig("url.image") + "/" + photoUrl;
	}

	public void setShowPhotoUrl(String showPhotoUrl) {
		this.showPhotoUrl = showPhotoUrl;
	}

	public String getShowVideoUrl() {
		if (StringUtils.isEmpty(photoSelectUrl))
			return null;
		StringBuilder builder = new StringBuilder();
		String[] urls = photoSelectUrl.split(",");
		for (String str : urls) {
			builder.append(JConfig.getConfig("url.image") + "/" + str);
			builder.append(",");
		}
		return builder.substring(0, builder.length() - 1);
	}

	public void setShowVideoUrl(String showVideoUrl) {
		this.showVideoUrl = showVideoUrl;
	}

	public Integer getIsMobile() {
		return isMobile;
	}

	public void setIsMobile(Integer isMobile) {
		this.isMobile = isMobile;
	}

	public String getPhotoSelectUrl() {
		return photoSelectUrl;
	}

	public void setPhotoSelectUrl(String photoSelectUrl) {
		this.photoSelectUrl = photoSelectUrl == null ? null : photoSelectUrl.trim();
	}

	public Integer getIsShowPrice() {
		return isShowPrice;
	}

	public void setIsShowPrice(Integer isShowPrice) {
		this.isShowPrice = isShowPrice;
	}

	public Integer getOperStatus() {
		return operStatus;
	}

	public void setOperStatus(Integer operStatus) {
		this.operStatus = operStatus;
	}

	public String getItems() {
		return items;
	}

	public void setItems(String items) {
		this.items = items;
	}

}
