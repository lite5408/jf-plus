package com.jf.plus.core.mallSetting.entity;

import cn.iutils.sys.entity.DataEntity;

/**
* 用户扩展信息表
* @author Tng
* @version 1.0
*/
public class UserExtInfo extends DataEntity<UserExtInfo>{

    private static final long serialVersionUID = 1L;

    private String platUserId;//第三方用户标识
    private String mobile;//
    private Long userId;//
    private Long orgId;//机构ID
    private Integer source;//来源

    public UserExtInfo() {
        super();
    }
    public UserExtInfo(String id){
        super(id);
    }

    public String getPlatUserId(){
        return platUserId;
    }

    public void setPlatUserId(String platUserId){
        this.platUserId = platUserId == null ? null : platUserId.trim();
    }
    public String getMobile(){
        return mobile;
    }

    public void setMobile(String mobile){
        this.mobile = mobile == null ? null : mobile.trim();
    }
    public Long getUserId(){
        return userId;
    }

    public void setUserId(Long userId){
        this.userId = userId;
    }
    public Long getOrgId(){
        return orgId;
    }

    public void setOrgId(Long orgId){
        this.orgId = orgId;
    }
    public Integer getSource(){
        return source;
    }

    public void setSource(Integer source){
        this.source = source;
    }
}
