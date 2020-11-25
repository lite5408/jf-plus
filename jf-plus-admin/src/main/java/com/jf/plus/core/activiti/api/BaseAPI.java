package com.jf.plus.core.activiti.api;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.jf.plus.common.core.ResultCode;
import com.jf.plus.common.utils.DateUtils;
import com.jf.plus.common.utils.NetUtil2;
import com.jf.plus.core.activiti.api.config.ApiConfig;
import com.jf.plus.core.activiti.api.response.BaseResponse;

import cn.iutils.common.config.JConfig;

/**
 * 基础API
 * 
 * @ClassName: BaseAPI
 * @author Tng
 * @date 2016年8月24日 上午11:10:15
 *
 */
public abstract class BaseAPI {

	private static final Logger LOGGER = LoggerFactory.getLogger(BaseAPI.class);

	protected static final String BASE_API_URL = JConfig.getConfig("url.audit");

	protected final ApiConfig apiConfig;

	protected final String contextPath;

	protected BaseAPI(ApiConfig apiConfig, String contextPath) {
		this.apiConfig = apiConfig;
		this.contextPath = contextPath;
	}

	protected BaseResponse executePost(String param_json) {
		BaseResponse baseResponse = execute(param_json + "&token=" + apiConfig.getToken());
		// token过期，刷新
		if (baseResponse.getCode() != null && baseResponse.getCode() == ResultCode.TOKEN_TIMEOUT) {
			apiConfig.refreshToken();
			// 重试一次
			baseResponse = execute(param_json + "&token=" + apiConfig.getToken());
		}
		return baseResponse;
	}

	/**
	 * 获得当前时间戳
	 */
	protected String getTimestamp() {
		return DateUtils.formatDateTime(new Date());
	}

	/**
	 * 子类需实现
	 * 
	 * @param params
	 * @return
	 */
	private BaseResponse execute(String params) {
		String ret = NetUtil2.post(BASE_API_URL + contextPath, params);
		BaseResponse baseResponse = new BaseResponse();
		if (!NetUtil2.isConnectTimeOut(ret)) {
			baseResponse = JSON.parseObject(ret, BaseResponse.class);
		}
		return baseResponse;
	}

	/**
	 * 
	 * 判断本次处理是否成功
	 * 
	 */
	public boolean isSuccess(BaseResponse r) {
		return r != null && r.isSuccess();
	}

}
