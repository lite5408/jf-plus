package com.jf.plus.core.product.entity;

import cn.iutils.sys.entity.DataEntity;

/**
* 商品属性表
* @author Tng
* @version 1.0
*/
public class ProductAttr extends DataEntity<ProductAttr>{

    private static final long serialVersionUID = 1L;

    private Long productId;//属性ID
    private Long attrId;//属性ID
    private String attrText;//属性名
    private String attrInfo;//属性值

    public ProductAttr() {
        super();
    }
    public ProductAttr(String id){
        super(id);
    }

    public Long getProductId(){
        return productId;
    }

    public void setProductId(Long productId){
        this.productId = productId;
    }
    public Long getAttrId(){
        return attrId;
    }

    public void setAttrId(Long attrId){
        this.attrId = attrId;
    }
    public String getAttrText(){
        return attrText;
    }

    public void setAttrText(String attrText){
        this.attrText = attrText == null ? null : attrText.trim();
    }
    public String getAttrInfo(){
        return attrInfo;
    }

    public void setAttrInfo(String attrInfo){
        this.attrInfo = attrInfo == null ? null : attrInfo.trim();
    }
}
