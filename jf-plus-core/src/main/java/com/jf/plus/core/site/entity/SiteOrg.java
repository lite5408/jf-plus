package com.jf.plus.core.site.entity;

import cn.iutils.sys.entity.DataEntity;

/**
* 站点组织关系表
* @author Tng
* @version 1.0
*/
public class SiteOrg extends DataEntity<SiteOrg>{

    private static final long serialVersionUID = 1L;

    private Long siteId;//站点id
    private Long orgId;//组织id

    public SiteOrg() {
        super();
    }
    public SiteOrg(String id){
        super(id);
    }

    public Long getSiteId(){
        return siteId;
    }

    public void setSiteId(Long siteId){
        this.siteId = siteId;
    }
    public Long getOrgId(){
        return orgId;
    }

    public void setOrgId(Long orgId){
        this.orgId = orgId;
    }
}
