package com.jf.plus.common.utils;

import com.jf.plus.common.config.WXApiConfigSingle;
import com.github.sd4324530.fastweixin.api.TemplateMsgAPI;
import com.github.sd4324530.fastweixin.api.config.ApiConfig;
import com.github.sd4324530.fastweixin.api.entity.TemplateMsg;
import com.github.sd4324530.fastweixin.api.response.SendTemplateResponse;

public class WXTempMsgUtils {
	
	public static SendTemplateResponse sendMsg(TemplateMsg msg){
		ApiConfig apiConfig = WXApiConfigSingle.getInstance().getApiConfig();
		TemplateMsgAPI templateMsgAPI = new TemplateMsgAPI(apiConfig);
		return templateMsgAPI.send(msg);
	}
}
