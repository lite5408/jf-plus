package com.jf.plus.core.mall.entity;

import java.util.List;

import com.alibaba.fastjson.annotation.JSONField;

import cn.iutils.sys.entity.DataEntity;

/**
 * 商城商品分类
 * 
 * @author Tng
 * @version 1.0
 */
public class MallProductCate extends DataEntity<MallProductCate> {

	private static final long serialVersionUID = 1L;

	private String catCode;// 分类编码
	private String catName;// 类别名称
	private Long catPid;// 父类别id
	@JSONField(serialize = false)
	private String catPids;// 父类别id集合
	private Integer sort;// 排序
	private String icon;//
	private Integer isFront;// 前端是否显示

	/** 自定义属性 */
	private String catPidName;// 父类名称
	private List<MallProductCate> childrens;
	
	public MallProductCate() {
		super();
	}

	public MallProductCate(String id) {
		super(id);
	}

	public String getCatPidName() {
		return catPidName;
	}

	public void setCatPidName(String catPidName) {
		this.catPidName = catPidName;
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

	public Long getCatPid() {
		return catPid;
	}

	public void setCatPid(Long catPid) {
		this.catPid = catPid;
	}

	public Integer getIsFront() {
		return isFront;
	}

	public void setIsFront(Integer isFront) {
		this.isFront = isFront;
	}

	public boolean isRootNode() {
		return catPid == 0;
	}

	public List<MallProductCate> getChildrens() {
		return childrens;
	}

	public void setChildrens(List<MallProductCate> childrens) {
		this.childrens = childrens;
	}

	public String getCatCode() {
		return catCode;
	}

	public void setCatCode(String catCode) {
		this.catCode = catCode;
	}
	
}
