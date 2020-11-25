package com.jf.plus.core.mallSetting.entity;

import java.util.Date;

import cn.iutils.sys.entity.DataEntity;

/**
* 用户登录日志
* @author Tng
* @version 1.0
*/
public class UserLogin extends DataEntity<UserLogin>{

    private static final long serialVersionUID = 1L;

    private Long userId;//
    private Long orgId;//
    private Integer loginWay;//登录方式(1:后台登录 2:前端登录 3:授信登录)
    private Date loginDate;//登录时间
    private String loginIp;//登录IP
    private Integer loginType;//登录类型(1:管理员  2:集采用户 3:积分用户 4:供应商 5:审核)

    public UserLogin() {
        super();
    }
    public UserLogin(String id){
        super(id);
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
    public Integer getLoginWay(){
        return loginWay;
    }

    public void setLoginWay(Integer loginWay){
        this.loginWay = loginWay;
    }
    public Date getLoginDate(){
        return loginDate;
    }

    public void setLoginDate(Date loginDate){
        this.loginDate = loginDate;
    }
    public String getLoginIp(){
        return loginIp;
    }

    public void setLoginIp(String loginIp){
        this.loginIp = loginIp == null ? null : loginIp.trim();
    }
    public Integer getLoginType(){
        return loginType;
    }

    public void setLoginType(Integer loginType){
        this.loginType = loginType;
    }
}
