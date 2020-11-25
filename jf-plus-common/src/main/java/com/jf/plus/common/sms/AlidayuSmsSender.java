//package com.jf.plus.common.sms;
//
//import org.apache.commons.lang3.StringUtils;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import com.jf.plus.common.core.Constants;
//import com.taobao.api.DefaultTaobaoClient;
//import com.taobao.api.TaobaoClient;
//import com.taobao.api.request.AlibabaAliqinFcSmsNumSendRequest;
//import com.taobao.api.response.AlibabaAliqinFcSmsNumSendResponse;
//
//public class AlidayuSmsSender extends SmsSender {
//	private final static Logger LOGGER = LoggerFactory.getLogger(AlidayuSmsSender.class);
//
//	private final static String url = Constants.ALIDAYU_URL;
//
//	private final static String appkey = Constants.ALIDAYU_APPKEY;
//
//	private final static String secret = Constants.ALIDAYU_SECRET;
//
//	private final static String signName = Constants.ALIDAYU_SIGN_NAME;
//
//	private final static String templateId = Constants.ALIDAYU_TMPID;
//
//	private String yzm;
//
//	public String getYzm() {
//		return yzm;
//	}
//
//	public void setYzm(String yzm) {
//		this.yzm = yzm;
//	}
//
//	@Override
//	public boolean send() {
//		if (StringUtils.isBlank(yzm)) {
//			return false;
//		}
//		
//		boolean success = false;
//
//		try {
//			TaobaoClient client = new DefaultTaobaoClient(url, appkey, secret);
//			AlibabaAliqinFcSmsNumSendRequest req = new AlibabaAliqinFcSmsNumSendRequest();
//			req.setSmsType("normal");
//			req.setSmsFreeSignName(signName);
//			req.setSmsParamString("{\"yzm\":\"" + yzm + "\"}");
//			req.setRecNum(mobile);
//			req.setSmsTemplateCode(templateId);
//			AlibabaAliqinFcSmsNumSendResponse rsp = client.execute(req);
//			if(rsp != null && rsp.getErrorCode().equals("0")){
//				success = true;
//			}else{
//				LOGGER.error("发送短信失败：" + rsp.toString());
//			}
//		} catch (Exception e) {
//			LOGGER.error("发送短信异常[调用阿里大于接口失败]：{}", e);
//		}
//
//		return success;
//	}
//
//}
