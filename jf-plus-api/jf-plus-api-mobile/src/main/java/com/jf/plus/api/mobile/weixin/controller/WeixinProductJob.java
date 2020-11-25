//package com.jf.plus.api.mobile.weixin.controller;
//
//import java.util.Date;
//import java.util.HashMap;
//import java.util.Map;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.stereotype.Component;
//
//import com.alibaba.fastjson.JSONObject;
//import com.github.sd4324530.fastweixin.api.entity.TemplateMsg;
//import com.github.sd4324530.fastweixin.api.entity.TemplateParam;
//import com.github.sd4324530.fastweixin.api.response.SendTemplateResponse;
//import com.jf.plus.common.utils.DateUtils;
//import com.jf.plus.common.utils.StringUtils;
//import com.jf.plus.common.utils.WXTempMsgUtils;
//import com.jf.plus.core.product.entity.Product;
//import com.jf.plus.core.product.service.ProductService;
//import com.jf.plus.core.setting.entity.MsgSendRecord;
//import com.jf.plus.core.setting.entity.MsgTemplate;
//import com.jf.plus.core.setting.service.MsgSendRecordService;
//import com.jf.plus.core.setting.service.MsgTemplateService;
//
//import cn.iutils.common.Page;
//
///**
// * 微信Job
// * 
// * @author tang
// *
// */
//@Component
//public class WeixinProductJob {
//
//	private static final Logger logger = LoggerFactory.getLogger(WeixinProductJob.class);
//
//	@Autowired
//	MsgSendRecordService msgSendRecordService;
//
//	@Autowired
//	MsgTemplateService msgTemplateService;
//
//	@Autowired
//	ProductService productService;
//
//	private static final Integer PAGE_SIZE = 100;
//
//	/**
//	 * 每30秒执行一次
//	 */
//	@Scheduled(cron = "0/30 * * * * ? ")
//	public void sendProductMsg() {
//		long batchNo = System.currentTimeMillis();
//		logger.info("[微信推送]推送商品上架，批次号：" + batchNo + "，startTime:" + DateUtils.getDateTime());
//
//		int sendCount = 0;
//
//		MsgTemplate tmplate = getTemplate("10");
//
//		// 先更新未绑定微信的消息未已推送
//		MsgSendRecord msgSendRecord1 = new MsgSendRecord();
//		msgSendRecord1.setSendStatus("1");
//		msgSendRecord1.setSendDate(new Date());
//		msgSendRecord1.setWxOpenId("0");
//		msgSendRecordService.updateEntity(msgSendRecord1);
//
//		// 处理微信消息
//		if (tmplate != null && "1".equals(tmplate.getAvaliable())
//				&& StringUtils.isNotBlank(tmplate.getWeixinTemplate())) {
//			Page<MsgSendRecord> page = new Page<>();
//			MsgSendRecord msgRecored = new MsgSendRecord();
//			msgRecored.setMsgType(tmplate.getMsgType());
//			msgRecored.setSendStatus("0");
//			page.setEntity(msgRecored);
//			page.setPageSize(PAGE_SIZE);
//			page.setList(msgSendRecordService.findPage(page));
//
//			for (MsgSendRecord msgSendRecord : page.getList()) {
//				if (msgSendRecord == null || StringUtils.isBlank(msgSendRecord.getWxOpenId()))
//					continue;
//
//				msgSendRecord.setWeixinTemplate(tmplate.getWeixinTemplate());
//
//				String isSend = "1";
//				msgSendRecord.setSendStatus(isSend);
//				msgSendRecord.setSendDate(new Date());
//				msgSendRecord.setNoticeWay("weixin");
//				msgSendRecordService.save(msgSendRecord);
//				
//				isSend = sendWxProductMsg(msgSendRecord);
//
//				sendCount++;
//			}
//		}
//		logger.info("[微信推送]推送商品上架，批次号：" + batchNo + "，endTime:{},推送条数：{}", DateUtils.getDateTime(), sendCount);
//	}
//
//	/**
//	 * {{first.DATA}} 店铺名称：{{keyword1.DATA}} 商品名称：{{keyword2.DATA}}
//	 * 商品价格：{{keyword3.DATA}} {{remark.DATA}}
//	 * 
//	 * @param msgSendRecord
//	 * @return
//	 */
//	private String sendWxProductMsg(MsgSendRecord msgSendRecord) {
//
//		JSONObject json = JSONObject.parseObject(msgSendRecord.getMsgJson());
//		Product product = productService.get(json.getString("productId"));
//
//		TemplateMsg templateMsg = new TemplateMsg();
//		templateMsg.setTouser(msgSendRecord.getWxOpenId());
//		templateMsg.setTemplateId(msgSendRecord.getWeixinTemplate());
//		templateMsg.setTopcolor("#FF0000");
//		templateMsg.setUrl(msgSendRecord.getLink());
//
//		Map<String, TemplateParam> data = new HashMap<>();
//		TemplateParam param = new TemplateParam();
//		param.setValue(msgSendRecord.getContent());
//		param.setColor("#173177");
//		data.put("first", param);
//
//		TemplateParam param1 = new TemplateParam();
//		param1.setValue(product.getBuyerName());
//		param1.setColor("#173177");
//		data.put("keyword1", param1);
//
//		TemplateParam param2 = new TemplateParam();
//		param2.setValue(product.getItemName());
//		param2.setColor("#173177");
//		data.put("keyword2", param2);
//
//		TemplateParam param3 = new TemplateParam();
//		param3.setValue(product.getSalePrice().doubleValue() + "");
//		param3.setColor("#173177");
//		data.put("keyword3", param3);
//
//		TemplateParam param4 = new TemplateParam();
//		param4.setValue("感谢关注支持");
//		param4.setColor("#173177");
//		data.put("remark", param4);
//
//		templateMsg.setData(data);
//
//		SendTemplateResponse sendResult = WXTempMsgUtils.sendMsg(templateMsg);
//		if (sendResult != null && sendResult.getErrcode().equals("0")) {
//			return "1";// 成功
//		} else {
//			logger.error("微信消息：{},发送失败：{}", templateMsg, sendResult);
//			return "0";// 失败
//		}
//	}
//
//	private MsgTemplate getTemplate(String type) {
//		MsgTemplate entity = new MsgTemplate();
//		entity.setMsgType(type);
//		entity.setStatus("1");
//		entity.setAvaliable("1");
//		entity = msgTemplateService.getEntity(entity);
//		return entity;
//	}
//}
