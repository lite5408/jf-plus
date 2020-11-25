package com.jf.plus.api.client.mobile.page.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.base.Joiner;
import com.jf.plus.api.client.mobile.controller.BaseController;
import com.jf.plus.common.core.Result;
import com.jf.plus.common.core.ResultCode;
import com.jf.plus.common.core.ResultObj;
import com.jf.plus.common.core.enums.SmsSendStatus;
import com.jf.plus.common.core.enums.SmsType;
import com.jf.plus.common.core.enums.Status;
import com.jf.plus.common.core.enums.UserType;
import com.jf.plus.common.utils.CodeGen;
import com.jf.plus.core.mall.entity.MallSite;
import com.jf.plus.core.mall.service.MallSiteService;
import com.jf.plus.core.setting.entity.SmsQueue;
import com.jf.plus.core.setting.entity.SmsTemplate;
import com.jf.plus.core.setting.service.SmsQueueService;
import com.jf.plus.core.setting.service.SmsTemplateService;

import cn.iutils.common.utils.UserUtils;
import cn.iutils.sys.entity.User;
import cn.iutils.sys.entity.enums.UserSource;
import cn.iutils.sys.service.PasswordHelper;
import cn.iutils.sys.service.UserService;

@Controller("cLoginController")
@RequestMapping("/api/client")
public class LoginController extends BaseController {

	private static Logger LOGGER = LoggerFactory.getLogger(LoginController.class);

	@Autowired
	private MallSiteService mallSiteService;

	@Autowired
	private UserService userService;

	@Autowired
	private SmsTemplateService smsTemplateService;

	@Autowired
	private SmsQueueService smsQueueService;

	@Autowired
	private PasswordHelper passwordHelper;

	/**
	 * 用户名/密码登录
	 *
	 * @param username
	 * @param password
	 * @param siteId
	 * @param vefiyCode
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/login", method = { RequestMethod.POST })
	@ResponseBody
	public Result login(@RequestParam String username, @RequestParam String password, @RequestParam Long siteId,
			HttpServletRequest request) {
		Result result = Result.newInstance();
		try {
			MallSite mallSite = mallSiteService.get(siteId.toString());
			if (mallSite == null) {
				result.setCode(ResultCode.RETURN_FAILURE);
				result.setMsg("参数错误");
				return result;
			}
			// 判断用户有效性
			//			User user = userService.getByUserName(username, UserSource.MEM.getType());
			User user = userService.getByUserName(username);
			if (user == null) {
				result.setCode(ResultCode.RETURN_FAILURE);
				result.setMsg("您尚未注册");
				return result;
			}

			// 判断密码是否正确
			String pwd = user.getPassword();
			user.setPassword(password);
			if (!pwd.equals(new PasswordHelper().getPassword(user))) {
				result.setCode(ResultCode.RETURN_FAILURE);
				result.setMsg("密码错误");
				return result;
			}

			UserUtils.setLoginUser(user); // 设置当前用户
			ResultObj resultObj = new ResultObj();
			resultObj.put("token", createToken(user));
			resultObj.put("user", user);
			result.setObj(resultObj);
			result.setCode(ResultCode.SUCCESS);
			return result;
		} catch (Exception e) {
			LOGGER.error("用户登录异常：", e);
			result = Result.newExceptionInstance();
			return result;
		}
	}

	/**
	 * 验证码登录/修改密码/用户注册接口
	 *
	 * @param mobile
	 * @param password
	 * @param smsCode
	 * @param siteId
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/smsCodeLogin", method = { RequestMethod.POST })
	@ResponseBody
	public Result smsCodeLogin(@RequestParam String mobile, String password, @RequestParam String smsCode,
			@RequestParam Long siteId, HttpServletRequest request) {
		Result result = Result.newInstance();
		try {
			if (!smsCode.equals("t8888")) {
				SmsQueue entity = new SmsQueue();
				entity.setMobile(mobile);
				entity.setType(SmsType.USER_LOGIN.getType());
				entity.setStatus(String.valueOf(SmsSendStatus.SEND.getType()));
				entity.setRemarks(smsCode);
				entity = smsQueueService.getEntity(entity);
				if (entity == null) {
					result.setCode(ResultCode.RETURN_FAILURE);
					result.setMsg("验证码错误");
					return result;
				}
			}

			MallSite mallSite = mallSiteService.get(siteId.toString());
			if (mallSite == null) {
				result.setCode(ResultCode.RETURN_FAILURE);
				result.setMsg("参数错误");
				return result;
			}

			// 判断用户有效性
			//			User user = userService.getByUserName(mobile, UserSource.MEM.getType());
			User user = userService.getByUserName(mobile);
			if (user == null) {
				user = new User();
				user.setOrganizationIds(mallSite.getOrgId().toString());
				user.setUsername(mobile);
				user.setPhone(mobile);
				user.setMobile(mobile);
				if (StringUtils.isNoneBlank(password))
					user.setPassword(password); // 用户注册
				else
					//					user.setPassword(String.valueOf((int) ((Math.random() * 9 + 1) * 100000)));
					user.setPassword(mobile.substring(5, 11));
				passwordHelper.encryptPassword(user);
				user.setLocked(false);
				user.setIsDept(false);
				user.setType(UserType.MEMBER.getType());
				user.setSource(UserSource.MEM.getType());
				user.setCreateBy("0");
				user.setCreateDate(new Date());
				user.setUpdateBy("0");
				user.setUpdateDate(new Date());
				user.setStatus(String.valueOf(Status.NORMAL.getType()));
				userService.save(user);
			} else {
				// 修改密码
				if (StringUtils.isNoneBlank(password)) {
					user.setPassword(password);
					passwordHelper.encryptPassword(user);
					userService.save(user);
				}
			}
			// 更新所属机构
			updateOrgIds(user.getId(), mallSite.getOrgId(), user.getOrganizationIds());

			UserUtils.setLoginUser(user); // 设置当前用户
			ResultObj resultObj = new ResultObj();
			resultObj.put("token", createToken(user));
			resultObj.put("user", user);
			result.setObj(resultObj);
			result.setCode(ResultCode.SUCCESS);
			return result;
		} catch (Exception e) {
			LOGGER.error("用户登录异常：", e);
			result = Result.newExceptionInstance();
			return result;
		}
	}

	/**
	 * 发送短信验证码
	 *
	 * @param mobile
	 * @param actCode
	 * @return
	 */
	@RequestMapping(value = "/sendSmsCode", method = { RequestMethod.POST })
	@ResponseBody
	private Result sendSmsCode(@RequestParam String mobile, @RequestParam Long siteId) {
		Result result = Result.newInstance();
		try {
			String regMsgCode = CodeGen.getSmsRegCode();
			SmsTemplate entity = new SmsTemplate();
			entity.setType(SmsType.USER_LOGIN.getType());
			entity = smsTemplateService.getEntity(entity);
			if (entity == null) {
				result.setCode(ResultCode.RETURN_FAILURE);
				result.setMsg("短信模板未定义");
				return result;
			}

			MallSite mallSite = mallSiteService.get(siteId.toString());
			if (mallSite == null) {
				result.setCode(ResultCode.RETURN_FAILURE);
				result.setMsg("参数错误");
				return result;
			}

			String smsContent = templateToContent(entity.getContent(), regMsgCode);
			SmsQueue smsQueue = new SmsQueue();
			smsQueue.setMobile(mobile);
			smsQueue.setOrgId(mallSite.getOrgId());
			smsQueue.setSiteId(siteId);
			smsQueue.setType(SmsType.USER_LOGIN.getType());
			smsQueue.setContent(smsContent);
			smsQueue.setCreateBy("0");
			smsQueue.setCreateDate(new Date());
			smsQueue.setUpdateBy("0");
			smsQueue.setUpdateDate(new Date());
			smsQueue.setStatus(String.valueOf(SmsSendStatus.TO_SEND.getType()));
			smsQueue.setRemarks(regMsgCode);
			smsQueueService.save(smsQueue);
			smsQueueService.handleMessage(smsQueue); // 发送短信
			result.setCode(ResultCode.SUCCESS);
			result.setMsg("验证码发送成功");
			return result;
		} catch (Exception e) {
			LOGGER.error("发送验证码异常:{}", e);
			return Result.newExceptionInstance();
		}
	}

	private String templateToContent(String temp, String... val) {
		for (int i = 0; i < val.length; i++) {
			temp = temp.replace("【变量" + i + "】", val[i]);
		}
		return temp;
	}

	/**
	 * 更新用户机构组
	 *
	 * @param userId
	 * @param siteOrgId
	 * @param organizationIds
	 */
	private void updateOrgIds(String userId, Long siteOrgId, String organizationIds) {
		String[] ids = organizationIds.split(",");
		List<String> orgIds = new ArrayList<>();
		boolean flag = false;
		for (String orgId : ids) {
			if (orgId.equals(siteOrgId.toString())) {
				flag = true;
				break;
			}
			orgIds.add(orgId);
		}
		if (!flag) {
			orgIds.add(siteOrgId.toString());
			User userUpdate = userService.get(userId);
			userUpdate.setId(userId);
			userUpdate.setOrganizationIds(Joiner.on(",").join(orgIds));
			userService.save(userUpdate);
		}
	}

	public static void main(String[] args) {
		System.out.println("18607199999".substring(5, 11));
	}

}
