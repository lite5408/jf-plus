package com.jf.plus.core.product.entity;

import cn.iutils.sys.entity.DataEntity;

/**
* 供应商分组表
* @author Tng
* @version 1.0
*/
public class OrgGroupMerchant extends DataEntity<OrgGroupMerchant>{

    private static final long serialVersionUID = 1L;

    private Long groupId;//分组ID
    private Long merchantId;//代理商ID
    private Integer sort;//排序
    
    /**
     * 自定义属性
     * 
     */
    private Long orgId;//机构id
    private String merchantName;//代理商名称

    public OrgGroupMerchant() {
        super();
    }
    public OrgGroupMerchant(String id){
        super(id);
    }

    public Long getGroupId(){
        return groupId;
    }

    public void setGroupId(Long groupId){
        this.groupId = groupId;
    }
    public Long getMerchantId() {
		return merchantId;
	}
	public void setMerchantId(Long merchantId) {
		this.merchantId = merchantId;
	}
	public Integer getSort(){
        return sort;
    }
    public void setSort(Integer sort){
        this.sort = sort;
    }
	public String getMerchantName() {
		return merchantName;
	}
	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}
	public Long getOrgId() {
		return orgId;
	}
	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}
	
    
    
}
