package com.jf.plus.api.buyer.mobile.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;

import com.jf.plus.common.core.Result;
import com.jf.plus.core.setting.service.AppcodeService;

import cn.iutils.sys.entity.User;

public class BaseController {

	@Autowired
	protected AppcodeService appcodeService;

	protected String BASE_PATH = "";

	protected String createToken(User user) {
		return appcodeService.createToken(user);
	}

	protected Result checkToken(String token) {
		return appcodeService.checkToken(token);
	}

	public String getBaseURL(HttpServletRequest request) {
		if (request.getServerPort() == 80) {
			return request.getScheme() + "://" + request.getServerName() + BASE_PATH;
		}
		return request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + BASE_PATH;
	}

}
