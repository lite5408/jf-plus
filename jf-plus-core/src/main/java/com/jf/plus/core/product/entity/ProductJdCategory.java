package com.jf.plus.core.product.entity;

import cn.iutils.sys.entity.DataEntity;

/**
* 
* @author Tng
* @version 1.0
*/
public class ProductJdCategory extends DataEntity<ProductJdCategory>{

    private static final long serialVersionUID = 1L;

    private String catId;//渠道分类id
    private String parentId;//父类别
    private String name;//类别名称
    private String catClass;//分类级别(0为一级分类,1为二级分类,2为三级分类)
    private String state;//分类是否有效(0为无效,1为有效)

    public ProductJdCategory() {
        super();
    }
    public ProductJdCategory(String id){
        super(id);
    }

    public String getCatId(){
        return catId;
    }

    public void setCatId(String catId){
        this.catId = catId == null ? null : catId.trim();
    }
    public String getParentId(){
        return parentId;
    }

    public void setParentId(String parentId){
        this.parentId = parentId == null ? null : parentId.trim();
    }
    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name == null ? null : name.trim();
    }
    public String getCatClass(){
        return catClass;
    }

    public void setCatClass(String catClass){
        this.catClass = catClass == null ? null : catClass.trim();
    }
    public String getState(){
        return state;
    }

    public void setState(String state){
        this.state = state == null ? null : state.trim();
    }
}
