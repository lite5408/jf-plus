package com.jf.plus.core.setting.entity;

import cn.iutils.sys.entity.DataEntity;

/**
* 消息模板表
* @author Tng
* @version 1.0
*/
public class MsgTemplate extends DataEntity<MsgTemplate>{

    private static final long serialVersionUID = 1L;

    private Long orgId;//公司机构ID
    private String msgType;//消息类型 (10:商品上架 20:订单销量提醒 21:订单发货 22:取消订单）
    private String templateContent;//模板内容
    private String link;//消息链接URL
    private String noticeWay;//提醒方式：weixin : 微信  mobile：短信提醒
    private String weixinTemplate;//微信消息模板
    private Integer sort;//排序
    private String avaliable;//可用

    public MsgTemplate() {
        super();
    }
    public MsgTemplate(String id){
        super(id);
    }

    public Long getOrgId(){
        return orgId;
    }

    public void setOrgId(Long orgId){
        this.orgId = orgId;
    }
    public String getMsgType(){
        return msgType;
    }

    public void setMsgType(String msgType){
        this.msgType = msgType == null ? null : msgType.trim();
    }
    public String getTemplateContent(){
        return templateContent;
    }

    public void setTemplateContent(String templateContent){
        this.templateContent = templateContent == null ? null : templateContent.trim();
    }
    public String getLink(){
        return link;
    }

    public void setLink(String link){
        this.link = link == null ? null : link.trim();
    }
    public String getNoticeWay(){
        return noticeWay;
    }

    public void setNoticeWay(String noticeWay){
        this.noticeWay = noticeWay == null ? null : noticeWay.trim();
    }
    public String getWeixinTemplate(){
        return weixinTemplate;
    }

    public void setWeixinTemplate(String weixinTemplate){
        this.weixinTemplate = weixinTemplate == null ? null : weixinTemplate.trim();
    }
    public Integer getSort(){
        return sort;
    }

    public void setSort(Integer sort){
        this.sort = sort;
    }
	public String getAvaliable() {
		return avaliable;
	}
	public void setAvaliable(String avaliable) {
		this.avaliable = avaliable;
	}
    
    
}
