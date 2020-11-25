package com.jf.plus.core.sys.controller;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.jf.plus.common.core.Result;
import com.jf.plus.common.core.ResultCode;
import com.jf.plus.common.core.enums.RoleType;
import com.jf.plus.core.mallSetting.entity.UserExtInfo;
import com.jf.plus.core.mallSetting.service.UserExtInfoService;
import com.jf.plus.core.setting.entity.RoleGroup;
import com.jf.plus.core.setting.service.RoleGroupService;

import cn.iutils.common.BaseController;
import cn.iutils.common.DataScope;
import cn.iutils.common.Page;
import cn.iutils.common.ResultVo;
import cn.iutils.common.utils.CacheUtils;
import cn.iutils.common.utils.DateUtils;
import cn.iutils.common.utils.JStringUtils;
import cn.iutils.common.utils.UserUtils;
import cn.iutils.sys.entity.Organization;
import cn.iutils.sys.entity.Role;
import cn.iutils.sys.entity.User;
import cn.iutils.sys.entity.enums.DataScopeEnum;
import cn.iutils.sys.entity.enums.UserSource;
import cn.iutils.sys.service.OrganizationService;
import cn.iutils.sys.service.PasswordHelper;
import cn.iutils.sys.service.RoleService;
import cn.iutils.sys.service.UserService;

@Controller
@RequestMapping("${adminPath}/user")
public class UserController extends BaseController {

	@Autowired
	private UserService userService;

	@Autowired
	private OrganizationService organizationService;
	@Autowired
	private RoleService roleService;
	@Autowired
	private PasswordHelper passwordHelper;

	@Autowired
	private RoleGroupService roleGroupService;

	@Autowired
	private UserExtInfoService userExtInfoService;

	@ModelAttribute
	public User get(@RequestParam(required = false) String id) {
		User entity = null;
		if (JStringUtils.isNotBlank(id)) {
			entity = userService.get(id);
		}
		if (entity == null) {
			entity = new User();
		}
		return entity;
	}

	/**
	 * 用户列表
	 *
	 * @param model
	 * @param page
	 * @return
	 */
	@RequiresPermissions("sys:user:view")
	@RequestMapping(value = "/list")
	public String list(User user, Model model, Page<User> page) {
		Organization organization1 = new Organization();
		organization1.setUser(UserUtils.getLoginUser());
		organization1.setDataScopeFilter(new DataScope(UserUtils.getLoginUser(), DataScopeEnum.orgFollow, "a", "a"));
		organization1.setOrderBy("a.parent_ids asc");
		List<Organization> organizationList = organizationService.findList(organization1);
		model.addAttribute("organizationList", organizationList);
		// 初始化加载第一个
		if (JStringUtils.isBlank(user.getOrganizationId()) && organizationList.size() > 0) {
			user.setOrganizationId(organizationList.get(0).getId());
		}
		page.setEntity(user);
		model.addAttribute("page", page.setList(userService.findPage(page)));
		return "sys/user/list";
	}

	/**
	 * 用户新增
	 *
	 * @param model
	 * @return
	 */
	@RequiresPermissions("sys:user:create")
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public String create(User user, Model model) {
		Organization organization1 = new Organization();
		organization1.setUser(UserUtils.getLoginUser());
		organization1.setDataScopeFilter(new DataScope(UserUtils.getLoginUser(), DataScopeEnum.orgFollow, "a", "a"));
		organization1.setOrderBy("a.parent_ids asc");
		model.addAttribute("organizationList", organizationService.findList(organization1));
		Role role = new Role();
		role.setUser(UserUtils.getLoginUser());
		model.addAttribute("roleList", roleService.findList(role));
		user.setOrganization(organizationService.get(user.getOrganizationId()));
		model.addAttribute("user", user);

		return "sys/user/edit";
	}

	/**
	 * 用户新增保存
	 *
	 * @param user
	 * @param redirectAttributes
	 * @return
	 */
	@RequiresPermissions("sys:user:create")
	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public String create(User user, RedirectAttributes redirectAttributes) {

		User users = userService.getByUserNameOrNo(user.getNo(), user.getUsername());
		if (users != null)
			addMessage(redirectAttributes, "保存失败：用户名/编码重复！");
		else {
			passwordHelper.encryptPassword(user);
			user.setSource(UserSource.SYS.getType());
			user.setOpenId(user.getPhone());

			// 判断是否是部门管理员（权限可以管理用户的）
			if (StringUtils.isNotBlank(user.getRoleGroupIds())) {
				String[] roleGroupIds = user.getRoleGroupIds().split(",");
				for (String roleGroupId : roleGroupIds) {
					RoleGroup roleGroup = roleGroupService.get(roleGroupId);
					String[] roleIds;
					if (roleGroup != null && StringUtils.isNotBlank(roleGroup.getGroupRoleIds())
							&& (roleIds = roleGroup.getGroupRoleIds().split(",")) != null) {
						for (String roleId : roleIds) {
							Role role = roleService.get(roleId);
							if (role != null && RoleType.USER.getType().equals(role.getRole())) {
								user.setIsDept(true);
							}
						}
					}
				}
			}

			userService.save(user);
			addMessage(redirectAttributes, "保存成功");
		}
		return "redirect:" + adminPath + "/user/update?id=" + user.getId();
	}

	/**
	 * 用户修改
	 *
	 * @param user
	 * @param model
	 * @return
	 */
	@RequiresPermissions("sys:user:update")
	@RequestMapping(value = "/update", method = RequestMethod.GET)
	public String update(User user, Model model) {
		Organization organization = new Organization();
		organization.setUser(UserUtils.getLoginUser());
		model.addAttribute("organizationList", organizationService.findList(organization));
		Role role = new Role();
		role.setUser(UserUtils.getLoginUser());
		model.addAttribute("roleList", roleService.findList(role));
		model.addAttribute("user", user);

		return "sys/user/edit";
	}

	/**
	 * 用户修改保存
	 *
	 * @param user
	 * @param redirectAttributes
	 * @return
	 */
	@RequiresPermissions("sys:user:update")
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public String update(User user, RedirectAttributes redirectAttributes) {
		// 不是部门经理，不能修改，超级管理员可以修改，自己的信息可以修改
		User loginUser = UserUtils.getLoginUser();

		userService.save(user);
		CacheUtils.remove(user.getUsername());
		addMessage(redirectAttributes, "保存成功");

		return "redirect:" + adminPath + "/user/update?id=" + user.getId();
	}

	/**
	 * 用户删除
	 *
	 * @param user
	 * @param redirectAttributes
	 * @return
	 */
	@RequiresPermissions("sys:user:delete")
	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public String delete(User user, int pageNo, int pageSize, RedirectAttributes redirectAttributes) {
		if (user.isAdmin() && user.getId().equals(UserUtils.getLoginUser().getId())) {
			// 不能删除超级用户，和当前登录用户
			addMessage(redirectAttributes, "不能删除");
		} else {
			userService.delete(user);
			CacheUtils.remove(user.getUsername());
			addMessage(redirectAttributes, "删除成功");
		}
		return "redirect:" + adminPath + "/user/list?organizationId=" + user.getOrganizationId() + "&pageNo=" + pageNo
				+ "&pageSize=" + pageSize;
	}

	/**
	 * 修改密码
	 *
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/{id}/changePassword", method = RequestMethod.GET)
	public String showChangePasswordForm(@PathVariable("id") String id, Model model) {
		model.addAttribute("id", id);

		UserExtInfo userExtInfo = new UserExtInfo();
		userExtInfo.setUserId(Long.valueOf(id));
		userExtInfo = userExtInfoService.getEntity(userExtInfo);
		if (userExtInfo != null && StringUtils.isNotBlank(userExtInfo.getPlatUserId())) {
			model.addAttribute("isWxBind", 1);
		}

		return "sys/user/changePassword";
	}

	/**
	 * 修改密码保存
	 *
	 * @param id
	 * @param password
	 * @param newPassword
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping(value = "/{id}/changePassword", method = RequestMethod.POST)
	public String changePassword(@PathVariable("id") String id, String password, String newPassword,
			RedirectAttributes redirectAttributes) {
		try {
			userService.changePassword(id, password, newPassword);
			addMessage(redirectAttributes, "修改密码成功");
			return "redirect:" + adminPath + "/user/" + id + "/changePassword";
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			addMessage(redirectAttributes, "修改密码失败");
			return "redirect:" + adminPath + "/user/" + id + "/changePassword";
		}
	}

	/**
	 * 修改密码保存
	 *
	 * @param newPassword
	 * @return
	 */
	@RequestMapping(value = "/changePassword", method = RequestMethod.POST)
	public @ResponseBody ResultVo changePassword(String newPassword) {
		ResultVo resultVo = null;
		try {
			userService.changePassword(UserUtils.getLoginUser(), newPassword);
			resultVo = new ResultVo(ResultVo.SUCCESS, "1", "调用成功", null);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			resultVo = new ResultVo(ResultVo.FAILURE, "-1", "调用失败", null);
		}
		return resultVo;
	}

	/**
	 * 用户资料
	 *
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/userInfo", method = RequestMethod.GET)
	public String userInfo(Model model) {
		User user = UserUtils.getLoginUser();
		model.addAttribute("user", user);
		return "sys/user/config-userInfo";
	}

	/**
	 * 保存用户资料
	 *
	 * @param user
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/saveUserInfo", method = RequestMethod.POST)
	public String saveUserInfo(User user, Model model) {
		userService.save(user);
		CacheUtils.remove(user.getUsername());
		model.addAttribute("user", user);
		addMessage(model, "保存资料成功");
		return "sys/user/config-userInfo";
	}

	/**
	 * 用户选择弹出界面
	 *
	 * @param users
	 *            选择用户列表
	 * @param user
	 * @param model
	 * @param page
	 * @return
	 */
	@RequestMapping(value = "/selectUser")
	public String selectUser(String[] users, User user, Model model, Page<User> page) {
		Organization organization = new Organization();
		organization.setUser(UserUtils.getLoginUser());
		List<Organization> organizationList = organizationService.findList(organization);
		model.addAttribute("organizationList", organizationList);
		// 初始化加载第一个
		if (JStringUtils.isBlank(user.getOrganizationId()) && organizationList.size() > 0) {
			user.setOrganizationId(organizationList.get(0).getId());
		}
		page.setEntity(user);
		model.addAttribute("page", page.setList(userService.findPage(page)));
		model.addAttribute("users", users);
		return "sys/user/selectUser";
	}

	/**
	 * 获取用户列表
	 *
	 * @param users
	 * @return
	 */
	@RequestMapping(value = "/getUsers", method = RequestMethod.GET)
	public @ResponseBody ResultVo getUsers(String[] users) {
		ResultVo resultVo = null;
		try {
			List<Map> list = userService.getUsers(users);
			resultVo = new ResultVo(ResultVo.SUCCESS, "1", "获取用户列表调用成功", list);
		} catch (Exception e) {
			logger.error("获取用户列表调用失败", e.getMessage());
			resultVo = new ResultVo(ResultVo.FAILURE, "-1", "获取用户列表调用失败", null);
		}
		return resultVo;
	}

	@RequestMapping("/checkPhone")
	@ResponseBody
	public Result checkPhone(String phone) {
		Result result = new Result();
		try {
			boolean success = phone.equals(UserUtils.getLoginUser().getPhone());
			if (success) {
				result.setCode(ResultCode.SUCCESS);
				result.setMsg("手机号正确");
			} else {
				result.setCode(ResultCode.RETURN_FAILURE);
				result.setMsg("手机号不正确");
			}
		} catch (Exception e) {
			logger.error("校验用户手机号失败：{}", e);
			return Result.newExceptionInstance();
		}
		return result;
	}

	@RequestMapping("/unique")
	@ResponseBody
	public Result unqiue(User user) {
		Result result = new Result();

		try {
			int count = userService.findUniqueCount(user);

			result.setObj(count);
			result.setCode(ResultCode.SUCCESS);
			return result;
		} catch (Exception e) {
			logger.error("系统异常：{}", e);
			return Result.newExceptionInstance();
		}
	}

	@RequestMapping("/{id}/cancelWxBind")
	public String cancelWxBind(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
		UserExtInfo userExtInfo = new UserExtInfo();
		userExtInfo.setUserId(id);
		userExtInfo = userExtInfoService.getEntity(userExtInfo);
		if (userExtInfo != null && !"".equals(userExtInfo.getPlatUserId())) {
			userExtInfo.setPlatUserId("");
			userExtInfoService.save(userExtInfo);
		}
		addMessage(redirectAttributes, "解绑成功");

		return "redirect:" + adminPath + "/user/" + id + "/changePassword";
	}

	@RequestMapping("batchChangePwd")
	@ResponseBody
	public Result batchChangePwd(@RequestParam String pwd, String id, String roleGroupIds ,@RequestParam String createDate) {
		Result result = new Result();

		try {
			User user = new User();
			user.setId(id);
			user.setRoleGroupIds(roleGroupIds);
			user.setCreateDate(DateUtils.parseDate(createDate));
			List<User> userList = userService.findList(user);
			for (User user2 : userList) {
				userService.changePassword(user2.getId(), "123456", user2.getPhone());
			}

			result.setMsg("修改成功");
			result.setCode(ResultCode.SUCCESS);
			return result;
		} catch (Exception e) {
			logger.error("系统异常：{}", e);
			return Result.newExceptionInstance();
		}
	}

}
