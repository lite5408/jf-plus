package com.jf.plus.api.mobile.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.jf.plus.common.core.Result;
import com.jf.plus.common.core.ResultCode;
import com.jf.plus.common.core.enums.RoleType;
import com.jf.plus.common.core.enums.UserType;
import com.jf.plus.core.setting.service.AppcodeService;

import cn.iutils.sys.entity.User;
import cn.iutils.sys.service.RoleService;

public class BaseController {

	@Autowired
	protected AppcodeService appcodeService;
	
	
	@Autowired
	protected RoleService roleService;

	protected String BASE_PATH = "";

	protected String createToken(User user) {
		return appcodeService.createToken(user);
	}

	protected Result checkToken(String token) {
		return appcodeService.checkToken(token);
	}
	
	protected String nullToStr(Object str) {
		return str == null ? "":str+"";
	}
	
	protected Integer nullToInt(Object str) {
		return str == null ? 0 : (Integer) str;
	}
	
	protected Result checkLoginPromise(Integer type, String roleGroupIds) {
		Result result = new Result();
		int isPromise = 0;
		if(StringUtils.isBlank(roleGroupIds)){
			result.setCode(ResultCode.RETURN_FAILURE);
			result.setMsg("暂无权限");
			return result;
		}else{
			List<String> groupList = Arrays.asList(roleGroupIds.split(","));
			Set<String> roleSet = roleService.findRolesByGroups(groupList);
			for(String role : roleSet){
				if(role == null )
					continue;
				if(type == UserType.SALER.getType() 
						&& RoleType.USER_CG.getType().equals(role)){//代理商下单
					isPromise = 1;
					break;
				}else if(type == UserType.BUYER.getType() && (RoleType.USER_BUYER.getType().equals(role) || RoleType.USER_BUYER_KF.getType().equals(role))){//买手端
					isPromise = 1;
					break;
				}
			}
		}
		
		if(isPromise == 0){
			result.setCode(ResultCode.RETURN_FAILURE);
			result.setMsg("暂无权限");
			return result;
		}
		
		result.setCode(ResultCode.SUCCESS);
		return result;
	}
	
	/**
	 * 库房
	 * @param type
	 * @param roleGroupIds
	 * @return
	 */
	protected Result checkLoginPromiseKf(String roleGroupIds) {
		Result result = new Result();
		int isPromise = 0;
		if(StringUtils.isBlank(roleGroupIds)){
			result.setCode(ResultCode.RETURN_FAILURE);
			result.setMsg("暂无权限");
			return result;
		}else{
			List<String> groupList = Arrays.asList(roleGroupIds.split(","));
			Set<String> roleSet = roleService.findRolesByGroups(groupList);
			for(String role : roleSet){
				if(role == null )
					continue;
				if(RoleType.USER_BUYER_KF.getType().equals(role)){//库房买手端
					isPromise = 1;
					break;
				}
			}
		}
		
		if(isPromise == 0){
			result.setCode(ResultCode.RETURN_FAILURE);
			result.setMsg("暂无权限");
			return result;
		}
		
		result.setCode(ResultCode.SUCCESS);
		return result;
	}

	public String getBaseURL(HttpServletRequest request) {
		if (request.getServerPort() == 80) {
			return request.getScheme() + "://" + request.getServerName() + BASE_PATH;
		}
		return request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + BASE_PATH;
	}

}
