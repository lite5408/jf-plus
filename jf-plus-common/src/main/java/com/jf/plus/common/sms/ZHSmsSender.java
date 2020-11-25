package com.jf.plus.common.sms;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;
import com.jf.plus.common.utils.NetUtil2;

import cn.iutils.common.config.JConfig;

/**
 *
 * @ClassName: ZHSmsSender
 * @Description: 短信接口
 * @author tangyh
 * @date 2016年4月15日 下午2:52:13
 *
 */
public class ZHSmsSender extends SmsSender {

	private static final Logger LOG = LoggerFactory.getLogger(ZHSmsSender.class);

	private String url = JConfig.getConfig("sms.url");

	private String AccessToken = JConfig.getConfig("sms.AccessToken");

	private String SysNbr = JConfig.getConfig("sms.SysNbr");

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@Override
	public String getContent() {
		return content;
	}

	@Override
	public void setContent(String content) {
		this.content = content;
	}

	@Override
	public String getMobile() {
		return mobile;
	}

	@Override
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	@Override
	public boolean send() {
		boolean success = false;
		try {
			JSONObject params = new JSONObject();
			params.put("AccessToken", AccessToken);
			params.put("SysNbr", SysNbr);
			params.put("MobNbr", mobile);
			params.put("MobTxt", content);

			String ret = NetUtil2.postJSON(url, params.toJSONString());
			if (!NetUtil2.isConnectTimeOut(ret)) {
				JSONObject retJSON = JSONObject.parseObject(ret);
				Integer code = retJSON.getInteger("code");
				if(code != null && code.intValue() == 0){
					success = true;
				}else{
					LOG.error("短信发送失败：手机号:{}，内容:{},返回结果:{}", mobile, content, ret);
				}

				//				if (ret.indexOf("\"code\":0") > -1) {
				//					success = true;
				//				} else {
				//					LOG.error("短信发送失败：手机号:{}，内容:{},返回结果:{}", mobile, content, ret);
				//				}
			}
		} catch (Exception e) {
			LOG.error("短信发送异常：{}", e);
		}
		return success;
	}

	public static void main(String[] args) {
		//		ZHSmsSender zhSmsSender = new ZHSmsSender();
		//		zhSmsSender.setContent("您的集采订单正在确认收货，验证码是【1234】。");
		//		zhSmsSender.setMobile("18607198919");
		//		zhSmsSender.send();
	}
}
