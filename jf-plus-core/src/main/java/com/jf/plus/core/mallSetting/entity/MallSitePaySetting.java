package com.jf.plus.core.mallSetting.entity;

import cn.iutils.sys.entity.DataEntity;

/**
* 站点支付配置表
* @author Tng
* @version 1.0
*/
public class MallSitePaySetting extends DataEntity<MallSitePaySetting>{

    private static final long serialVersionUID = 1L;

    private Long siteId;//
    private Integer payWay;//支付方式(1:微信支付 2:驿车宝支付)
    private String payInfo;//支付json字符串  key-value

    public MallSitePaySetting() {
        super();
    }
    public MallSitePaySetting(String id){
        super(id);
    }

    public Long getSiteId(){
        return siteId;
    }

    public void setSiteId(Long siteId){
        this.siteId = siteId;
    }
    public Integer getPayWay(){
        return payWay;
    }

    public void setPayWay(Integer payWay){
        this.payWay = payWay;
    }
    public String getPayInfo(){
        return payInfo;
    }

    public void setPayInfo(String payInfo){
        this.payInfo = payInfo == null ? null : payInfo.trim();
    }
}
