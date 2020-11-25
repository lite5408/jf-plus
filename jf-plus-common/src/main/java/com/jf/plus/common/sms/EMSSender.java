//package com.jf.plus.common.sms;
//
//import java.net.URLEncoder;
//
//import org.apache.commons.codec.digest.DigestUtils;
//import org.apache.commons.lang3.StringUtils;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import com.jf.plus.common.utils.NetUtil;
//
///**
// * 
// * @ClassName: EMSSender
// * @Description: 短信接口
// * @author tangyh
// * @date 2016年4月15日 下午2:52:13
// *
// */
//public class EMSSender extends SmsSender {
//	private static final Logger LOG = LoggerFactory.getLogger(EMSSender.class);
//	//private String url = "http://121.201.38.146/sendSMS.action";
//	private String url = "http://113.108.68.228:8001/sendSMS.action";
//
//	private String corpAccount = "16532";
//	
//	private String account = "admin";
//
//	private String password = "123456abc";
//
//	public String getUrl() {
//		return url;
//	}
//
//	public void setUrl(String url) {
//		this.url = url;
//	}
//
//	public String getAccount() {
//		return account;
//	}
//
//	public void setAccount(String account) {
//		this.account = account;
//	}
//
//	public String getPassword() {
//		return password;
//	}
//
//	public void setPassword(String password) {
//		this.password = password;
//	}
//
//	public String getContent() {
//		return content;
//	}
//
//	public void setContent(String content) {
//		this.content = content;
//	}
//
//	public String getMobile() {
//		return mobile;
//	}
//
//	public void setMobile(String mobile) {
//		this.mobile = mobile;
//	}
//
//	@Override
//	public boolean send() {
//		boolean success = false;
//		StringBuffer stringBuffer = new StringBuffer();
//		stringBuffer.append("enterpriseID=").append(corpAccount);
//		stringBuffer.append("&loginName=").append(account);
//		stringBuffer.append("&password=").append(DigestUtils.md5Hex(password).toLowerCase());
//		stringBuffer.append("&mobiles=").append(mobile);
//		try{
//			stringBuffer.append("&content=").append(URLEncoder.encode(content, "UTF-8"));
//		}catch(Exception e){
//			e.printStackTrace();
//		}
//		
//		try {
//			String ret = NetUtil.postURLStr(url, stringBuffer.toString());
//			LOG.info("发送短信响应参数：" + ret);
//			if (StringUtils.isNotBlank(ret)) {
//				if (ret.indexOf("<Result>0</Result>") > -1) {
//					success = true;
//				}else{
//					LOG.error("发送短信失败，响应参数：" + stringBuffer.toString());
//				}
//			}
//		} catch (Exception e) {
//			LOG.error("短信发送异常：{}", e);
//			//重试
//			String ret = NetUtil.postURLStr(url, stringBuffer.toString());
//			if (StringUtils.isNotBlank(ret)) {
//				if (ret.indexOf("<Result>0</Result>") > -1) {
//					success = true;
//				}else{
//					LOG.error("发送短信失败，响应参数：" + stringBuffer.toString());
//				}
//			}
//		}
//		return success;
//	}
//}
