package com.jf.plus.core.activiti.api.config;

import java.io.Serializable;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jf.plus.core.setting.service.AppcodeService;

import cn.iutils.common.Servlets;
import cn.iutils.common.spring.SpringUtils;
import cn.iutils.common.utils.UserUtils;

public final class ApiConfig implements Serializable {

	private static final Logger LOGGER = LoggerFactory.getLogger(ApiConfig.class);

	private String token;
	private AppcodeService appcodeService = SpringUtils.getBean(AppcodeService.class);

	private static final ApiConfig instance = new ApiConfig();

	private ApiConfig() {
		token = initToken();
	}

	public static ApiConfig getInstance() {
		return instance;
	}

	public String initToken() {
		token = appcodeService.createToken(UserUtils.getLoginUser());
		//token放在session里面（防止退出还在）
		Servlets.getRequest().getSession().setAttribute("session_token", token);
		return token;
	}

	public void refreshToken() {
		initToken();
	}
	
	public String getToken(){
		String token = (String) Servlets.getRequest().getSession().getAttribute("session_token");
		if(StringUtils.isEmpty(token)){
			refreshToken();
		}
		return this.token;
	}

}
