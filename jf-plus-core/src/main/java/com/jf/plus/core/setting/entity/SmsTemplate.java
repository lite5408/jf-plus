package com.jf.plus.core.setting.entity;

import cn.iutils.sys.entity.DataEntity;

/**
* 短信模板表
* @author Tng
* @version 1.0
*/
public class SmsTemplate extends DataEntity<SmsTemplate>{

    private static final long serialVersionUID = 1L;

    private Long orgId;//组织id
    private Long siteId;//站点id
    private Integer type;//类型
    private String name;//名称
    private String content;//模板内容
    private String description;//描述

    public SmsTemplate() {
        super();
    }
    public SmsTemplate(String id){
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
    public Integer getType(){
        return type;
    }

    public void setType(Integer type){
        this.type = type;
    }
    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }
    public String getContent(){
        return content;
    }

    public void setContent(String content){
        this.content = content;
    }
    public String getDescription(){
        return description;
    }

    public void setDescription(String description){
        this.description = description;
    }
}
