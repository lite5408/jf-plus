package com.jf.plus.core.supplyer.controller;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.jf.plus.common.core.Result;
import com.jf.plus.common.core.ResultCode;
import com.jf.plus.common.core.enums.LoginType;
import com.jf.plus.common.vo.SupplyerUserVo;
import com.jf.plus.core.mall.entity.MallSupplyer;
import com.jf.plus.core.mall.service.MallSupplyerService;

import cn.iutils.common.BaseController;
import cn.iutils.common.Servlets;
import cn.iutils.common.utils.UserUtils;

/**
 * 供应商控制器
 * 
 * @author Tng
 * @version 1.0
 */
@Controller("supplyController")
@RequestMapping("${supplyPath}")
public class SupplyController extends BaseController {

	@Autowired
	private MallSupplyerService mallSupplyerService;

	@RequestMapping(method = RequestMethod.GET)
	public String index(Model model) {
		return "/mall/mallSupplyer/index";
	}

	@RequestMapping(value = "/welcome")
	public String welcome(Model model) {
		MallSupplyer mallSupplyer = mallSupplyerService.get(UserUtils.getSupplyUser().getSupplyId());
		model.addAttribute("mallSupplyer", mallSupplyer);
		return "/mall/mallSupplyer/welcome";
	}

	@RequestMapping(value = "login", method = RequestMethod.GET)
	public String login(Model model) {
		return "/mall/mallSupplyer/login";
	}

	@RequestMapping(value = "/loginSupply", method = RequestMethod.POST)
	@ResponseBody
	public Result loginPost(String username, String password, LoginType loginType, HttpServletRequest request,
			Model model) {
		Result result = new Result();

		if (StringUtils.isBlank(username)) {
			result.setMsg("用户名不能为空");
			return result;
		}
		if (StringUtils.isBlank(password)) {
			result.setMsg("密码不能为空");
			return result;
		}

		String error = "";
		try {

			MallSupplyer entity = new MallSupplyer();
			entity.setAdminLoginname(username);
			MallSupplyer mallSupplyer = mallSupplyerService.getEntity(entity);
			if (mallSupplyer == null) {
				throw new UnknownAccountException("账号/密码错误");
			}

			if (!mallSupplyer.getAdminPwd().equals(DigestUtils.md5Hex(password))) {
				throw new IncorrectCredentialsException("账号/密码错误");
			}

			if (mallSupplyer.getAvailable() == Boolean.FALSE) {
				throw new LockedAccountException("帐号被锁定");
			}

			SupplyerUserVo supplyerUserVo = new SupplyerUserVo();
			supplyerUserVo.setAdminLoginName(mallSupplyer.getAdminLoginname());
			supplyerUserVo.setSupplyId(mallSupplyer.getId());
			supplyerUserVo.setSupplyName(mallSupplyer.getCompanyName());
			supplyerUserVo.setLoginDate(new Date());

			UserUtils.setSupplyUser(supplyerUserVo);

		} catch (UnknownAccountException e) {
			error = "用户名/密码错误";
		} catch (IncorrectCredentialsException e) {
			error = "用户名/密码错误";
		} catch (ExcessiveAttemptsException e) {
			error = "错误次数操作超过限制";
		} catch (LockedAccountException e) {
			error = "帐号被锁定";
		} catch (Exception e) {
			logger.error("登录失败：{}", e);
			return Result.newExceptionInstance();
		}

		if (!error.equals("")) {
			result.setCode(ResultCode.RETURN_FAILURE);
			result.setMsg(error);
			return result;
		}

		result.setSuccess(true);
		return result;
	}

	@RequestMapping(value = "/logout")
	public String logout() {
		Servlets.getRequest().getSession().invalidate();
		return "/mall/mallSupplyer/login";
	}

	@RequestMapping(value = "/changePassword", method = { RequestMethod.GET })
	public String changePassword() {
		return "/mall/mallSupplyer/changePassword";
	}
	
	@RequestMapping(value = "/changePassword", method = RequestMethod.POST)
	public String changePassword(String password, String newPassword,
			RedirectAttributes redirectAttributes) {
		try {
			mallSupplyerService.changePassword(UserUtils.getSupplyUser().getSupplyId(), password, newPassword);
			addMessage(redirectAttributes, "修改密码成功");
			return  "redirect:" + supplyPath + "/changePassword";
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			addMessage(redirectAttributes, "修改密码失败");
			return  "redirect:" + supplyPath + "/changePassword";
		}
	}

}
