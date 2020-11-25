package com.jf.plus.core.site.entity;

import java.util.ArrayList;
import java.util.List;
import cn.iutils.sys.entity.DataEntity;

/**
 * 站点上架商品分类
 * 
 * @author Tng
 * @version 1.0
 */
public class SiteCate extends DataEntity<SiteCate> {

	private static final long serialVersionUID = 1L;

	private Long orgId;// 组织id
	private Long siteId;// 站点id
	private Long cateId;// 分类
	private String cateName;// 分类名称

	/**
	 * 自定义属性
	 */
	private List<SiteCate> childrens = new ArrayList<>(); // 子分类
	private String catPid;// 上级id

	public SiteCate() {
		super();
	}

	public SiteCate(String id) {
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

	public Long getCateId() {
		return cateId;
	}

	public void setCateId(Long cateId) {
		this.cateId = cateId;
	}

	public String getCateName() {
		return cateName;
	}

	public void setCateName(String cateName) {
		this.cateName = cateName;
	}

	public List<SiteCate> getChildrens() {
		return childrens;
	}

	public void setChildrens(List<SiteCate> childrens) {
		this.childrens = childrens;
	}

	public String getCatPid() {
		return catPid;
	}

	public void setCatPid(String catPid) {
		this.catPid = catPid;
	}

	public boolean isRootNode() {
		return catPid.equals("0");
	}

}
