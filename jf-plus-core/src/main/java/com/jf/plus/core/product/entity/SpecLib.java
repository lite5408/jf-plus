package com.jf.plus.core.product.entity;

import cn.iutils.sys.entity.DataEntity;

/**
* 机构规格库
* @author Tng
* @version 1.0
*/
public class SpecLib extends DataEntity<SpecLib>{

    private static final long serialVersionUID = 1L;

    private Long orgId;//公司机构ID
    private Long cateId;//分类ID
    private String specType;//SKU类型：color 颜色 size 尺码
    private String specName;//sku值
    private String specCode;//sku代码，颜色#EEEEE编码，尺码
    private Integer sort;//排序

    public SpecLib() {
        super();
    }
    public SpecLib(String id){
        super(id);
    }

    public Long getOrgId(){
        return orgId;
    }

    public void setOrgId(Long orgId){
        this.orgId = orgId;
    }
    public Long getCateId(){
        return cateId;
    }

    public void setCateId(Long cateId){
        this.cateId = cateId;
    }
    public String getSpecType(){
        return specType;
    }

    public void setSpecType(String specType){
        this.specType = specType == null ? null : specType.trim();
    }
    public String getSpecName(){
        return specName;
    }

    public void setSpecName(String specName){
        this.specName = specName == null ? null : specName.trim();
    }
    public String getSpecCode(){
        return specCode;
    }

    public void setSpecCode(String specCode){
        this.specCode = specCode == null ? null : specCode.trim();
    }
    public Integer getSort(){
        return sort;
    }

    public void setSort(Integer sort){
        this.sort = sort;
    }
}
