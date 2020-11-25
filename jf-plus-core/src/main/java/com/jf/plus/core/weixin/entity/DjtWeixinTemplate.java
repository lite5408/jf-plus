package com.jf.plus.core.weixin.entity;

import cn.iutils.sys.entity.DataEntity;

/**
* 
* @author Tng
* @version 1.0
*/
public class DjtWeixinTemplate extends DataEntity<DjtWeixinTemplate>{

    private static final long serialVersionUID = 1L;

    private Integer accountId;//
    private String messageType;//消息类型
    private String mediaId;//素材id
    private String templateType;//模板类型
    private String keyword;//关键字
    private String reply;//回复

    public DjtWeixinTemplate() {
        super();
    }
    public DjtWeixinTemplate(String id){
        super(id);
    }

    public Integer getAccountId(){
        return accountId;
    }

    public void setAccountId(Integer accountId){
        this.accountId = accountId;
    }
    public String getMessageType(){
        return messageType;
    }

    public void setMessageType(String messageType){
        this.messageType = messageType == null ? null : messageType.trim();
    }
    public String getMediaId(){
        return mediaId;
    }

    public void setMediaId(String mediaId){
        this.mediaId = mediaId == null ? null : mediaId.trim();
    }
    public String getTemplateType(){
        return templateType;
    }

    public void setTemplateType(String templateType){
        this.templateType = templateType == null ? null : templateType.trim();
    }
    public String getKeyword(){
        return keyword;
    }

    public void setKeyword(String keyword){
        this.keyword = keyword == null ? null : keyword.trim();
    }
    public String getReply(){
        return reply;
    }

    public void setReply(String reply){
        this.reply = reply == null ? null : reply.trim();
    }
}
