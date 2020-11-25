package com.jf.plus.core.weixin.entity;

import cn.iutils.sys.entity.DataEntity;

/**
* 
* @author Tng
* @version 1.0
*/
public class DjtWeixinAccount extends DataEntity<DjtWeixinAccount>{

    private static final long serialVersionUID = 1L;

    private Long orgId;//
    private String name;//名称
    private String appId;//应用ID
    private String appSecret;//应用密钥
    private String merchantId;//商户ID
    private String merchantKey;//商户KEY
    private String url;//服务器地址
    private String token;//令牌
    private String aeskey;//消息加解密密钥

    public DjtWeixinAccount() {
        super();
    }
    public DjtWeixinAccount(String id){
        super(id);
    }

    public Long getOrgId(){
        return orgId;
    }

    public void setOrgId(Long orgId){
        this.orgId = orgId;
    }
    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name == null ? null : name.trim();
    }
    public String getAppId(){
        return appId;
    }

    public void setAppId(String appId){
        this.appId = appId == null ? null : appId.trim();
    }
    public String getAppSecret(){
        return appSecret;
    }

    public void setAppSecret(String appSecret){
        this.appSecret = appSecret == null ? null : appSecret.trim();
    }
    public String getMerchantId(){
        return merchantId;
    }

    public void setMerchantId(String merchantId){
        this.merchantId = merchantId == null ? null : merchantId.trim();
    }
    public String getMerchantKey(){
        return merchantKey;
    }

    public void setMerchantKey(String merchantKey){
        this.merchantKey = merchantKey == null ? null : merchantKey.trim();
    }
    public String getUrl(){
        return url;
    }

    public void setUrl(String url){
        this.url = url == null ? null : url.trim();
    }
    public String getToken(){
        return token;
    }

    public void setToken(String token){
        this.token = token == null ? null : token.trim();
    }
    public String getAeskey(){
        return aeskey;
    }

    public void setAeskey(String aeskey){
        this.aeskey = aeskey == null ? null : aeskey.trim();
    }
}
