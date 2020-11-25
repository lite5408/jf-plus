package com.jf.plus.core.setting.entity;

import java.util.Date;

import com.alibaba.fastjson.annotation.JSONField;

import cn.iutils.sys.entity.DataEntity;

/**
* 消息发送记录表
* @author Tng
* @version 1.0
*/
public class MsgSendRecord extends DataEntity<MsgSendRecord>{

    private static final long serialVersionUID = 1L;

    private Long msgId;//消息ID
    private String msgType;//消息类型 (10:商品上架 20:订单销量提醒 21:订单发货 22:取消订单）
    private Long toUserId;//收件人ID
    private String targetId;//消息关联对象ID
    private String content;//消息内容
    @JSONField(serialize = false)
    private String msgJson;//消息JSON值
    private String link;//消息链接URL
    @JSONField(serialize = false)
    private String noticeWay;//提醒方式：weixin : 微信  mobile：短信提醒
    @JSONField(serialize = false)
    private String weixinTemplate;//微信消息模板
    private String sendStatus;//发送状态：0 未发送 1：已发送 2：发送失败
    private Date sendDate;//发送时间
    private String isRead;//是否已读 0：未读 1：已读
    private Date readDate;//读取时间
    private Integer sort;//排序
    
    private Date msgDate;
    
    private String wxOpenId;//微信openId

    public MsgSendRecord() {
        super();
    }
    public MsgSendRecord(String id){
        super(id);
    }

    public Long getMsgId(){
        return msgId;
    }

    public void setMsgId(Long msgId){
        this.msgId = msgId;
    }
    public String getMsgType(){
        return msgType;
    }

    public void setMsgType(String msgType){
        this.msgType = msgType == null ? null : msgType.trim();
    }
    public Long getToUserId(){
        return toUserId;
    }

    public void setToUserId(Long toUserId){
        this.toUserId = toUserId;
    }
    public String getTargetId(){
        return targetId;
    }

    public void setTargetId(String targetId){
        this.targetId = targetId == null ? null : targetId.trim();
    }
    public String getContent(){
        return content;
    }

    public void setContent(String content){
        this.content = content == null ? null : content.trim();
    }
    public String getMsgJson(){
        return msgJson;
    }

    public void setMsgJson(String msgJson){
        this.msgJson = msgJson == null ? null : msgJson.trim();
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
    public String getSendStatus(){
        return sendStatus;
    }

    public void setSendStatus(String sendStatus){
        this.sendStatus = sendStatus == null ? null : sendStatus.trim();
    }
    public Date getSendDate(){
        return sendDate;
    }

    public void setSendDate(Date sendDate){
        this.sendDate = sendDate;
    }
    public String getIsRead(){
        return isRead;
    }

    public void setIsRead(String isRead){
        this.isRead = isRead == null ? null : isRead.trim();
    }
    public Date getReadDate(){
        return readDate;
    }

    public void setReadDate(Date readDate){
        this.readDate = readDate;
    }
    public Integer getSort(){
        return sort;
    }

    public void setSort(Integer sort){
        this.sort = sort;
    }
    
    public Date getMsgDate() {
		return createDate;
	}
	
    
	public String getWxOpenId() {
		return wxOpenId;
	}
	public void setWxOpenId(String wxOpenId) {
		this.wxOpenId = wxOpenId;
	}
    
    
}
