package com.jf.plus.core.sys.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jf.plus.common.core.Result;
import com.jf.plus.common.core.ResultCode;
import com.jf.plus.common.core.enums.LoginType;
import com.jf.plus.common.vo.SiteVo;
import com.jf.plus.core.mall.entity.MallSite;
import com.jf.plus.core.mall.service.MallSiteService;

import cn.iutils.common.BaseController;
import cn.iutils.common.ResultVo;
import cn.iutils.common.filter.CustomUserPasswordToken;
import cn.iutils.common.utils.UserUtils;
import cn.iutils.sys.entity.User;
import cn.iutils.sys.entity.enums.UserSource;
import cn.iutils.sys.service.OrganizationService;
import cn.iutils.sys.service.PasswordHelper;
import cn.iutils.sys.service.UserService;

/**
 * 登录控制器
 *
 * @author cc
 */
@Controller
@RequestMapping(value = "${adminPath}")
public class LoginController extends BaseController {

	@Autowired
	UserService userService;

	@Autowired
	OrganizationService organizationService;

	@Autowired
	PasswordHelper passwordHelper;

	@Autowired
	MallSiteService mallSiteService;

	/**
	 * 获取异步登录界面
	 *
	 * @param response
	 * @return
	 */
	@RequestMapping("/ajaxLogin")
	public String ajaxLogin(HttpServletResponse response) {
		ResultVo resultVo = null;
		// 获取用户登录信息 验证已登录，返回登录信息
		String userName = UserUtils.getLoginUserName();
		if (userName != null) {
			resultVo = new ResultVo(ResultVo.SUCCESS, "1", "登录成功", null);
			return renderString(response, resultVo);
		} else {
			return "ajaxLogin";
		}
	}

	/**
	 * 登录处理
	 *
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("/login")
	public String login(HttpServletRequest request, HttpServletResponse response, Model model) {
		// ajax登录失败处理
		if ("XMLHttpRequest".equalsIgnoreCase(request.getHeader("X-Requested-With"))) {
			ResultVo resultVo = new ResultVo(ResultVo.FAILURE, "0", "登录失败", null);
			return renderString(response, resultVo);
		}
		// 获取用户登录信息 验证已登录，跳转到管理页
		// String userName = UserUtils.getLoginUserName();
		// if(userName!=null){
		//
		// String orgId = request.getParameter("orgId");
		// //重新写业务逻辑
		// if(isUserOrg(userName,orgId)){
		// model.addAttribute("msg","账号不在该机构下");
		// return "login";
		// }
		//
		// User user = userService.getUserByUserName(userName);
		// user.setCurrentOrgId(orgId);
		// user.setOrganization(organizationService.get(orgId));
		//
		// UserUtils.setLoginUser(user);
		//
		// return "redirect:" + adminPath;
		// }
		// String exceptionClassName = (String) request
		// .getAttribute("shiroLoginFailure");
		// model.addAttribute("username", request.getParameter("username"));
		// String error = null;
		// if
		// (UnknownAccountException.class.getName().equals(exceptionClassName))
		// {
		// error = "用户名/密码错误";
		// } else if (IncorrectCredentialsException.class.getName().equals(
		// exceptionClassName)) {
		// error = "用户名/密码错误";
		// } else if (ExcessiveAttemptsException.class.getName().equals(
		// exceptionClassName)) {
		// error = "错误次数操作超过限制";
		// } else if (LockedAccountException.class.getName().equals(
		// exceptionClassName)) {
		// error = "帐号被锁定";
		// }else if("jCaptcha.error".equals(exceptionClassName)) {
		// error = "验证码错误";
		// } else if (exceptionClassName != null) {
		// error = "未知的错误";
		// }
		// model.addAttribute("msg", error);
		// if (request.getParameter("forceLogout") != null) {
		// error = "您已经被管理员强制退出，请重新登录";
		// model.addAttribute("msg", error);
		// }

		return "login";
	}

	/**
	 * 机构登录
	 *
	 * @param username
	 * @param password
	 * @param orgId
	 * @param loginType
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	@ResponseBody
	public Result loginPost(String username, String password, String orgId, LoginType loginType,
			HttpServletRequest request, Model model) {
		Result result = new Result();

		if (StringUtils.isBlank(username)) {
			result.setMsg("用户名不能为空");
			return result;
		}
		if (StringUtils.isBlank(password)) {
			result.setMsg("密码不能为空");
			return result;
		}

		Subject user = SecurityUtils.getSubject();
		CustomUserPasswordToken token = new CustomUserPasswordToken(username, password, loginType.getType());
		token.setRememberMe(true);
		String error = "";
		try {
			user.login(token);

			username = UserUtils.getLoginUserName();
			User loginUser = userService.getByUserName(username, UserSource.SYS.getType());
			// 重新写业务逻辑
			if (!isUserOrg(loginUser, orgId)) {
				error = "账号不在该机构下";
			} else {

				MallSite entity = new MallSite();
				entity.setSiteUserIds(loginUser.getId());
				MallSite site = mallSiteService.getEntity(entity);
				if (site != null) {
					error = "您没有登录权限，请稍后再试";
				} else {
					loginUser.setOrganizationId(orgId);
					loginUser.setOrganization(organizationService.get(orgId));

					loginUser.setLoginType(LoginType.USER.getType());
					UserUtils.setLoginUser(loginUser);
				}
			}

		} catch (UnknownAccountException e) {
			error = "用户名/密码错误";
		} catch (IncorrectCredentialsException e) {
			error = "用户名/密码错误";
		} catch (ExcessiveAttemptsException e) {
			error = "错误次数操作超过限制";
		} catch (LockedAccountException e) {
			error = "帐号被锁定";
		} catch (Exception e) {
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

	/**
	 * 站点登录
	 */
	@RequestMapping(value = "/loginSite", method = RequestMethod.POST)
	@ResponseBody
	public Result loginPostSite(String username, String password, String siteId, String siteName, String siteOrgId,
			LoginType loginType, HttpServletRequest request, Model model) {
		Result result = new Result();

		if (StringUtils.isBlank(username)) {
			result.setMsg("用户名不能为空");
			return result;
		}
		if (StringUtils.isBlank(password)) {
			result.setMsg("密码不能为空");
			return result;
		}

		Subject user = SecurityUtils.getSubject();
		CustomUserPasswordToken token = new CustomUserPasswordToken(username, password, loginType.getType());
		token.setRememberMe(true);
		String error = "";
		try {
			user.login(token);

			if (siteId.equals("0")) {
				error = "您没有登录权限";
			} else {
				username = UserUtils.getLoginUserName();
				User loginUser = userService.getByUserName(username, UserSource.SYS.getType());
				loginUser.setOrganizationId(siteOrgId);
				loginUser.setOrganization(organizationService.get(siteOrgId));

				loginUser.setLoginType(LoginType.SITE.getType());

				SiteVo mySite = new SiteVo(siteId, siteName);
				UserUtils.setMySite(mySite);
				UserUtils.setLoginUser(loginUser);
			}

		} catch (UnknownAccountException e) {
			error = "用户名/密码错误";
		} catch (IncorrectCredentialsException e) {
			error = "用户名/密码错误";
		} catch (ExcessiveAttemptsException e) {
			error = "错误次数操作超过限制";
		} catch (LockedAccountException e) {
			error = "帐号被锁定";
		} catch (Exception e) {
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

	private boolean isUserOrg(User user, String orgId) {
		if ((user.getOrganizationIds() + ",").indexOf(orgId + ",") != -1) {
			return true;
		}
		return false;
	}

	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public String register() {
		return "register";
	}

	/**
	 * 用户注册
	 *
	 * @param username
	 *            用户名
	 * @param password
	 *            密码
	 * @param password
	 *            确认密码
	 * @return
	 */
	// @RequestMapping(value = "/register", method = RequestMethod.POST)
	// public String register(String username, String password, String
	// rpassword, Model model) {
	// String url = "register";
	// // 判断是否密码重复
	// if (!password.equals(rpassword)) {
	// addMessage(model, "两次密码不一致");
	// } else {
	// User user = userService.getByUserName(username);
	// if (user == null) {
	// user = new User();
	// user.setUsername(username);
	// user.setPassword(password);
	// user.setOrganizationId("1");
	// user.setRoleIdsStr("3,");
	// passwordHelper.encryptPassword(user);
	// userService.save(user);
	// addMessage(model, "注册成功");
	// url = "login";
	// } else {
	// addMessage(model, "账号已存在");
	// }
	// }
	// return url;
	// }

}
