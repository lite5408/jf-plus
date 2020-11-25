package com.jf.plus.core.product.entity;

import java.util.List;

import cn.iutils.sys.entity.DataEntity;

/**
* 供应商分组表
* @author Tng
* @version 1.0
*/
public class OrgGroup extends DataEntity<OrgGroup>{

    private static final long serialVersionUID = 1L;

    private Long orgId;//公司机构ID
    private String groupName;//分组名称
    private String cateIds;//分类ids，多个逗号分隔
    private String saleAreas;//销售区域，多个逗号分隔
    private Integer sort;//排序
    
    /**
     * 自定义属性
     */

    private String orgName;//所属机构名称
    private List<OrgGroupMerchant> merchantList;
    
    public OrgGroup() {
        super();
    }
    public OrgGroup(String id){
        super(id);
    }

    public Long getOrgId(){
        return orgId;
    }

    public void setOrgId(Long orgId){
        this.orgId = orgId;
    }
    public String getGroupName(){
        return groupName;
    }

    public void setGroupName(String groupName){
        this.groupName = groupName == null ? null : groupName.trim();
    }
    public Integer getSort(){
        return sort;
    }

    public void setSort(Integer sort){
        this.sort = sort;
    }
	public String getCateIds() {
		return cateIds;
	}
	public void setCateIds(String cateIds) {
		this.cateIds = cateIds;
	}
	public String getSaleAreas() {
		return saleAreas;
	}
	public void setSaleAreas(String saleAreas) {
		this.saleAreas = saleAreas;
	}
	public String getOrgName() {
		return orgName;
	}
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
	public List<OrgGroupMerchant> getMerchantList() {
		return merchantList;
	}
	public void setMerchantList(List<OrgGroupMerchant> merchantList) {
		this.merchantList = merchantList;
	}
	
	
	
}
