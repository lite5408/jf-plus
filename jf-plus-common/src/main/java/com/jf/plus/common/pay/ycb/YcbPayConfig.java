package com.jf.plus.common.pay.ycb;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jf.plus.common.config.Global;

public class YcbPayConfig {
	
	private Logger LOGGER = LoggerFactory.getLogger(YcbPayConfig.class);
	
//↓↓↓↓↓↓↓↓↓↓请在这里配置您的基本信息↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓

	public static String merchant_id = Global.getConfig("ycb.merchant_id");
	
	public static String api_key = Global.getConfig("ycb.api_key");
	
	public static String service_id = Global.getConfig("ycb.service_id");

	public static String notify_url = "/order/ycbpay/notify";


	private static final YcbPayConfig instance = new YcbPayConfig();
	
	private YcbPayConfig(){
	}
	
	public static YcbPayConfig getInstance(){
		return instance;
	}
	/**
     * 
     * 生成签名
     * 
     */
    public String createSign(Map<String, Object> paramMap,String apiKey){
    	if(paramMap == null)
    		return StringUtils.EMPTY;
    	
    	List<String> paramList = new ArrayList<>();
    	for(String key : paramMap.keySet()){
    		paramList.add(key + "=" + paramMap.get(key));
    	}
    	
    	String[] paramArr = paramList.toArray(new String[paramList.size()]);
		String mysign = WxMd5Signature.generateSign(paramArr, apiKey);
		LOGGER.info("mysign:" + mysign);
		return mysign;
    }

}

