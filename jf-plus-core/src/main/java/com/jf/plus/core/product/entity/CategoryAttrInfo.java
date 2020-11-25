package com.jf.plus.core.product.entity;

import cn.iutils.sys.entity.DataEntity;

/**
* 分类属性值表
* @author Tng
* @version 1.0
*/
public class CategoryAttrInfo extends DataEntity<CategoryAttrInfo>{

    private static final long serialVersionUID = 1L;

    private Long attrId;//属性ID
    private String attrInfo;//属性值

    public CategoryAttrInfo() {
        super();
    }
    public CategoryAttrInfo(String id){
        super(id);
    }

    public Long getAttrId(){
        return attrId;
    }

    public void setAttrId(Long attrId){
        this.attrId = attrId;
    }
    public String getAttrInfo(){
        return attrInfo;
    }

    public void setAttrInfo(String attrInfo){
        this.attrInfo = attrInfo == null ? null : attrInfo.trim();
    }
}
