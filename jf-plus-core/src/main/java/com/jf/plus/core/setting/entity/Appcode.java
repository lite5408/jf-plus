package com.jf.plus.core.setting.entity;

import java.util.Date;

import cn.iutils.sys.entity.DataEntity;

/**
* 用户token表
* @author Tng
* @version 1.0
*/
public class Appcode extends DataEntity<Appcode>{

    private static final long serialVersionUID = 1L;

    private Long userId;//会员id
    private String token;//token
    private String mobile;//登录用户名
    private Date expiredDate;//有效期截止日
    private Integer userType;//用户类型(1后台 2会员）

    public Appcode() {
        super();
    }
    public Appcode(String id){
        super(id);
    }

    public Long getUserId(){
        return userId;
    }

    public void setUserId(Long userId){
        this.userId = userId;
    }
    public String getToken(){
        return token;
    }

    public void setToken(String token){
        this.token = token;
    }
    public String getMobile(){
        return mobile;
    }

    public void setMobile(String mobile){
        this.mobile = mobile;
    }
    public Date getExpiredDate(){
        return expiredDate;
    }

    public void setExpiredDate(Date expiredDate){
        this.expiredDate = expiredDate;
    }
    public Integer getUserType(){
        return userType;
    }

    public void setUserType(Integer userType){
        this.userType = userType;
    }
}
