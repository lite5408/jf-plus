package com.jf.plus.core.product.entity;

import com.alibaba.fastjson.annotation.JSONField;

import cn.iutils.sys.entity.DataEntity;

/**
* 机构品牌表
* @author Tng
* @version 1.0
*/
public class MallProductBrand extends DataEntity<MallProductBrand>{

    private static final long serialVersionUID = 1L;

    private Long orgId;//公司机构ID
    @JSONField(name="name")
    private String brandName;//品牌名称
    private String brandCode;//品牌标识
    private Integer sort;//排序
    private String type;//品牌类型
    
    private String orgName;//机构名称

    public MallProductBrand() {
        super();
    }
    public MallProductBrand(String id){
        super(id);
    }

    public Long getOrgId(){
        return orgId;
    }

    public void setOrgId(Long orgId){
        this.orgId = orgId;
    }
    public String getBrandName(){
        return brandName;
    }

    public void setBrandName(String brandName){
        this.brandName = brandName == null ? null : brandName.trim();
    }
    public String getBrandCode(){
        return brandCode;
    }

    public void setBrandCode(String brandCode){
        this.brandCode = brandCode == null ? null : brandCode.trim();
    }
    public Integer getSort(){
        return sort;
    }

    public void setSort(Integer sort){
        this.sort = sort;
    }
	public String getOrgName() {
		return orgName;
	}
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	@Override
	public boolean equals(Object obj) {
		if(this == null || obj == null)
			return false;
		
		return this.getBrandCode().equals(((MallProductBrand) obj).getBrandCode());
	}
	
    
}
