package com.jf.plus.core.site.entity;

import org.apache.commons.lang3.StringUtils;

import cn.iutils.common.config.JConfig;
import cn.iutils.sys.entity.DataEntity;

/**
 * 商城广告专题配置
 * 
 * @author Tng
 * @version 1.0
 */
public class SiteAdvert extends DataEntity<SiteAdvert> {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	private Long orgId;// 组织id
	private Long siteId;// 站点id
	private String name;// 专题名称
	private Integer type;// 类型(1:广告2:商品专题3:分类)
	private Integer showType;// 专题类型(1:一般2:展示3:首页)
	private String photo;// 封面图
	private String url;// 链接
	private Integer sort;// 排序
	private String items;// 专题商品
	private Integer itemsCount;// 商品数量

	/** 自定义属性 */
	private String photoUrl;

	public SiteAdvert() {
		super();
	}

	public SiteAdvert(String id) {
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
		this.name = name;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Integer getShowType() {
		return showType;
	}

	public void setShowType(Integer showType) {
		this.showType = showType;
	}

	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public String getItems() {
		return items;
	}

	public void setItems(String items) {
		this.items = items;
	}

	public Integer getItemsCount() {
		return itemsCount;
	}

	public void setItemsCount(Integer itemsCount) {
		this.itemsCount = itemsCount;
	}

	public String getPhotoUrl() {
		if(StringUtils.isEmpty(photo))
			return null;
		
		return JConfig.getConfig("url.image") + "/" + photo;
	}

	public void setPhotoUrl(String photoUrl) {
		this.photoUrl = photoUrl;
	}

}
