package com.jf.plus.core.product.entity;

import com.alibaba.fastjson.annotation.JSONField;

import cn.iutils.sys.entity.DataEntity;

/**
* 销售规则分组机构表
* @author Tng
* @version 1.0
*/
public class ProductSaleRuleMerchant extends DataEntity<ProductSaleRuleMerchant>{

    private static final long serialVersionUID = 1L;

    private Long productId;//商品ID
    private Long saleRuleId;//销售规则id
    private String orgGroup;//代理商分组
    private String groupMerchant;//分组的机构id

    public ProductSaleRuleMerchant() {
        super();
    }
    public ProductSaleRuleMerchant(String id){
        super(id);
    }

    public Long getProductId(){
        return productId;
    }

    public void setProductId(Long productId){
        this.productId = productId;
    }
    public Long getSaleRuleId(){
        return saleRuleId;
    }

    public void setSaleRuleId(Long saleRuleId){
        this.saleRuleId = saleRuleId;
    }
    public String getOrgGroup(){
        return orgGroup;
    }

    public void setOrgGroup(String orgGroup){
        this.orgGroup = orgGroup == null ? null : orgGroup.trim();
    }
    public String getGroupMerchant(){
        return groupMerchant;
    }

    public void setGroupMerchant(String groupMerchant){
        this.groupMerchant = groupMerchant == null ? null : groupMerchant.trim();
    }
}
