package com.jf.plus.core.site.entity;

import cn.iutils.sys.entity.DataEntity;

/**
* 商城广告专题商品表
* @author Tng
* @version 1.0
*/
public class AdvertItems extends DataEntity<AdvertItems>{

    private static final long serialVersionUID = 1L;

    private Long orgId;//组织id
    private Long siteId;//站点id
    private Long advertId;//专题id
    private Long itemId;//站点商品id
    private Integer source;//商品渠道

    public AdvertItems() {
        super();
    }
    public AdvertItems(String id){
        super(id);
    }

    public Long getOrgId(){
        return orgId;
    }

    public void setOrgId(Long orgId){
        this.orgId = orgId;
    }
    public Long getSiteId(){
        return siteId;
    }

    public void setSiteId(Long siteId){
        this.siteId = siteId;
    }
    public Long getAdvertId(){
        return advertId;
    }

    public void setAdvertId(Long advertId){
        this.advertId = advertId;
    }
    public Long getItemId(){
        return itemId;
    }

    public void setItemId(Long itemId){
        this.itemId = itemId;
    }
    public Integer getSource(){
        return source;
    }

    public void setSource(Integer source){
        this.source = source;
    }
}
