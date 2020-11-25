package com.jf.plus.common.config;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jf.plus.common.core.Constants;
import com.jf.plus.common.utils.SignatureUtils;

public class ApiConfig {
	private static final Logger LOG = LoggerFactory.getLogger(ApiConfig.class);

	private final String appKey;

	private String sign;

	private static final ApiConfig instance = new ApiConfig();

	private ApiConfig() {
		this.appKey = Constants.APP_KEY;
	}

	public static ApiConfig getInstance() {
		return instance;
	}

	/**
	 * 
	 * 生成签名
	 * 
	 */
	public String createSign(String mobile) {
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("mobile", mobile);
		paramMap.put("timestamp", String.valueOf(System.currentTimeMillis()));

		String mysign = SignatureUtils.buildSign(paramMap, appKey);
		LOG.info("mysign:" + mysign);
		return mysign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public String getSign() {
		return sign;
	}

	public static void main(String[] args) {
		System.out.println(ApiConfig.getInstance().getSign());
	}

}
