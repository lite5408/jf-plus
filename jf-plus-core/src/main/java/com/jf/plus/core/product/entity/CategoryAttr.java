package com.jf.plus.core.product.entity;

import java.util.List;

import cn.iutils.sys.entity.DataEntity;

/**
* 分类属性定义表
* @author Tng
* @version 1.0
*/
public class CategoryAttr extends DataEntity<CategoryAttr>{

    private static final long serialVersionUID = 1L;

    private Long cateId;//分类ID
    private String attrText;//属性名称
    private Long parentId;//父属性ID（级联属性）
    
    /**
     * 自定义属性
     */
    private List<CategoryAttrInfo> categoryAttrInfos;
    private String cateName;//分类名称
    private String attrInfos;//属性选择值

    public CategoryAttr() {
        super();
    }
    public CategoryAttr(String id){
        super(id);
    }

    public Long getCateId(){
        return cateId;
    }

    public void setCateId(Long cateId){
        this.cateId = cateId;
    }
    public String getAttrText(){
        return attrText;
    }

    public void setAttrText(String attrText){
        this.attrText = attrText == null ? null : attrText.trim();
    }
    public Long getParentId(){
        return parentId;
    }

    public void setParentId(Long parentId){
        this.parentId = parentId;
    }
	public List<CategoryAttrInfo> getCategoryAttrInfos() {
		return categoryAttrInfos;
	}
	public void setCategoryAttrInfos(List<CategoryAttrInfo> categoryAttrInfos) {
		this.categoryAttrInfos = categoryAttrInfos;
	}
	public String getCateName() {
		return cateName;
	}
	public void setCateName(String cateName) {
		this.cateName = cateName;
	}
	public String getAttrInfos() {
		return attrInfos;
	}
	public void setAttrInfos(String attrInfos) {
		this.attrInfos = attrInfos;
	}
    
    
}
