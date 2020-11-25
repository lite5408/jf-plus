package com.jf.plus.api.mobile.page.controller;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jf.plus.api.mobile.controller.BaseController;
import com.jf.plus.common.core.Result;
import com.jf.plus.common.core.ResultCode;
import com.jf.plus.common.core.ResultObj;
import com.jf.plus.core.mallSetting.entity.UserExtInfo;
import com.jf.plus.core.mallSetting.service.UserExtInfoService;

import cn.iutils.sys.entity.User;
import cn.iutils.sys.service.PasswordHelper;
import cn.iutils.sys.service.UserService;

@Controller
public class MyController extends BaseController {

	private static Logger LOGGER = LoggerFactory.getLogger(MyController.class);

	@Autowired
	private UserService userService;
	
	@Autowired
	private UserExtInfoService userExtInfoService;
	
	@Autowired
	private PasswordHelper passwordHelper;

	/**
	 * 获取用户信息
	 *
	 * @return
	 */
	@RequestMapping(value = "/api/userInfo", method = { RequestMethod.POST ,RequestMethod.GET})
	@ResponseBody
	public Result userInfo(@RequestParam String token) {
		Result result = Result.newInstance();
		try {
			// 验证token有效性
			result = this.checkToken(token);
			if (result.getCode().intValue() != ResultCode.SUCCESS) {
				return result;
			}
			User user = userService.get(result.getObj().toString());
			
			UserExtInfo userExtInfo = new UserExtInfo();
			userExtInfo.setUserId(Long.valueOf(user.getId()));
			userExtInfo = userExtInfoService.getEntity(userExtInfo);
			
			ResultObj resultObj = new ResultObj();
			resultObj.put("token", token);
			resultObj.put("user", user);
			if(userExtInfo != null && StringUtils.isNotBlank(userExtInfo.getPlatUserId())){
				resultObj.put("isBind", 1);
			}else{
				resultObj.put("isBind", 0);
			}
			result.setObj(resultObj);
			result.setCode(ResultCode.SUCCESS);
			return result;
		} catch (Exception e) {
			LOGGER.error("获取用户信息失败：", e);
			result = Result.newExceptionInstance();
			return result;
		}
	}
	
	@RequestMapping("/api/changePwd")
	@ResponseBody
	public Result changePwd(@RequestParam String token,@RequestParam String password, @RequestParam String newPassword) {
		Result result = new Result();

		try {
			// 验证token有效性
			result = this.checkToken(token);
			if (result.getCode().intValue() != ResultCode.SUCCESS) {
				return result;
			}
			
			User user = userService.get(result.getObj().toString());
			String oldPassword = user.getPassword();
			user.setPassword(password);
			String myPassword = passwordHelper.getPassword(user);
			if (oldPassword.equals(myPassword)) {
				user.setPassword(newPassword);
				passwordHelper.encryptPassword(user);
				userService.save(user);
			}else{
				result.setMsg("原密码错误");
				result.setCode(ResultCode.RETURN_FAILURE);
				return result;
			}
			
			result.setMsg("修改密码成功");
			result.setCode(ResultCode.SUCCESS);
			return result;
		} catch (Exception e) {
			LOGGER.error("系统异常：{}", e);
			return Result.newExceptionInstance();
		}
	}
	
	/**
	 * cancelBindWx
	 *
	 * @return
	 */
	@RequestMapping(value = "/api/cancelBindWx", method = { RequestMethod.POST ,RequestMethod.GET})
	@ResponseBody
	public Result cancelBindWx(@RequestParam String token) {
		Result result = Result.newInstance();
		try {
			// 验证token有效性
			result = this.checkToken(token);
			if (result.getCode().intValue() != ResultCode.SUCCESS) {
				return result;
			}
			
			UserExtInfo userExtInfo = new UserExtInfo();
			userExtInfo.setUserId(Long.valueOf(result.getObj().toString()));
			userExtInfoService.deleteEntity(userExtInfo);
			
			result.setMsg("解除成功");
			result.setCode(ResultCode.SUCCESS);
			return result;
		} catch (Exception e) {
			LOGGER.error("解除失败：", e);
			result = Result.newExceptionInstance();
			return result;
		}
	}

}
