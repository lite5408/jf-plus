package com.jf.plus.core.activiti.api;

import com.jf.plus.core.activiti.api.config.ApiConfig;
import com.jf.plus.core.activiti.api.response.BaseResponse;

public class OrderAuditListAPI extends BaseAPI {
	private final static String METHOD = "/api/act/findHistoryTaskList";

	public OrderAuditListAPI(ApiConfig apiConfig) {
		super(apiConfig, METHOD);
	}

	public BaseResponse submit(Integer status) {
		StringBuffer param_json = new StringBuffer();
		param_json.append("auditStatus=").append(status);
		return executePost(param_json.toString());
	}
}
