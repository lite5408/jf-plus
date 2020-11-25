package com.jf.plus.core.mall.entity;

import cn.iutils.sys.entity.DataEntity;

/**
 * 渠道商品分类表
 *
 * @author Tng
 * @version 1.0
 */
public class MallChannelCate extends DataEntity<MallChannelCate> {

	private static final long serialVersionUID = 1L;

	private String catId;// 分类id
	private String catName;// 类别名称
	private Long catPid;// 父类别id
	private String catPids;// 父类别id集合
	private Integer sort;// 排序
	private String icon;//
	private Integer channelType;// 渠道类型(1京东 2供应商 3齐心)
	private String productCateIds;// 所属商品分类id集合

	/** 自定义属性 */
	private String productCateName;// 商品分类名称

	public MallChannelCate() {
		super();
	}

	public MallChannelCate(String id) {
		super(id);
	}

	public String getCatId() {
		return catId;
	}

	public void setCatId(String catId) {
		this.catId = catId;
	}

	public String getCatName() {
		return catName;
	}

	public void setCatName(String catName) {
		this.catName = catName;
	}

	public String getCatPids() {
		return catPids;
	}

	public void setCatPids(String catPids) {
		this.catPids = catPids;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public Integer getChannelType() {
		return channelType;
	}

	public void setChannelType(Integer channelType) {
		this.channelType = channelType;
	}

	public String getProductCateIds() {
		return productCateIds;
	}

	public void setProductCateIds(String productCateIds) {
		this.productCateIds = productCateIds;
	}

	public Long getCatPid() {
		return catPid;
	}

	public void setCatPid(Long catPid) {
		this.catPid = catPid;
	}

	public String getProductCateName() {
		return productCateName;
	}

	public void setProductCateName(String productCateName) {
		this.productCateName = productCateName;
	}
}
