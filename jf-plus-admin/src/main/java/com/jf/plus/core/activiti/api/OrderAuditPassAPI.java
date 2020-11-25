package com.jf.plus.core.activiti.api;

import com.jf.plus.core.activiti.api.config.ApiConfig;
import com.jf.plus.core.activiti.api.response.BaseResponse;
/**
 * 审核通过API
 * @author Tng
 * 
 */
public class OrderAuditPassAPI extends BaseAPI {
	private final static String METHOD = "/api/orderAuditPass";

	public OrderAuditPassAPI(ApiConfig apiConfig) {
		super(apiConfig, METHOD);
	}

	public BaseResponse submit(String auditList) {
		StringBuffer param_json = new StringBuffer();
		param_json.append("auditList=").append(auditList);
		return executePost(param_json.toString());
	}
}
