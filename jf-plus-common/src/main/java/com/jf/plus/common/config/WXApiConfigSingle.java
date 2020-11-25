package com.jf.plus.common.config;

import com.github.sd4324530.fastweixin.api.config.ApiConfig;
import com.jf.plus.common.core.Constants;

/**
 * 
 * @ClassName: WXApiConfigSingle
 * @Description: WX配置单利模式，解决微信对token请求限制10000次/天
 * @author Tng
 * @date 2017年2月23日 下午10:12:05
 *
 */
public class WXApiConfigSingle {
	
	private ApiConfig apiConfig;
	
	
	private static WXApiConfigSingle wxApiConfigSingle;
	
	private WXApiConfigSingle(){
		
	}
	
	public static synchronized WXApiConfigSingle getInstance(){
		if(wxApiConfigSingle == null){
			wxApiConfigSingle = new WXApiConfigSingle();
		}
		return wxApiConfigSingle;
	}
	
	
	public void setApiConfig(ApiConfig apiConfig){
		this.apiConfig = apiConfig;
	}
	
	public ApiConfig getApiConfig(){
		if(apiConfig == null){
			apiConfig = new ApiConfig(Constants.APPID, Constants.APPSECRET,true);
		}
		return apiConfig;
	}
	
	
}
