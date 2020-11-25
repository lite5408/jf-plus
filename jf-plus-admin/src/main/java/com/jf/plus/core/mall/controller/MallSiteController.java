package com.jf.plus.core.mall.controller;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
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
import com.jf.plus.common.core.enums.Source;
import com.jf.plus.core.mall.entity.MallSite;
import com.jf.plus.core.mall.service.MallSiteService;

import cn.iutils.common.BaseController;
import cn.iutils.common.DataScope;
import cn.iutils.common.Page;
import cn.iutils.common.utils.JStringUtils;
import cn.iutils.common.utils.UserUtils;
import cn.iutils.sys.entity.Organization;
import cn.iutils.sys.entity.User;
import cn.iutils.sys.entity.enums.DataScopeEnum;
import cn.iutils.sys.entity.enums.UserSource;
import cn.iutils.sys.service.OrganizationService;
import cn.iutils.sys.service.UserService;

/**
 * 商城站点表 控制器
 *
 * @author Tng
 * @version 1.0
 */
@Controller
@RequestMapping("${adminPath}/mall/mallSite")
public class MallSiteController extends BaseController {

	@Autowired
	private MallSiteService mallSiteService;

	@Autowired
	private UserService userService;

	@Autowired
	private OrganizationService organizationService;

	@ModelAttribute
	public MallSite get(@RequestParam(required = false) String id) {
		MallSite entity = null;
		if (JStringUtils.isNotBlank(id)) {
			entity = mallSiteService.get(id);
		}
		if (entity == null) {
			entity = new MallSite();
		}
		return entity;
	}

	@RequestMapping(value = "/list")
	public String list(Model model, MallSite mallSite, Page<MallSite> page) {
		page.setEntity(mallSite);
		page.getEntity().setDataScopeFilter(new DataScope(UserUtils.getLoginUser(), DataScopeEnum.org, "org", "a"));

		model.addAttribute("page", page.setList(mallSiteService.findPage(page)));
		return "mall/mallSite/list";
	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public String create(MallSite mallSite, Model model) {

		User user = new User();
		user.setDataScopeFilter(new DataScope(UserUtils.getLoginUser(), DataScopeEnum.org, "b", "a"));
		model.addAttribute("userList", userService.findList(user));

		model.addAttribute("mallSite", mallSite);
		return "mall/mallSite/form";
	}

	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public String create(MallSite mallSite, RedirectAttributes redirectAttributes) {

		mallSite.setOrgId(Long.valueOf(UserUtils.getLoginUser().getOrganizationId()));
		if (StringUtils.isNotBlank(mallSite.getSiteUserIds())) {
			String userNames = "";
			String[] userIds = mallSite.getSiteUserIds().split(",");
			for (String userId : userIds) {
				User user = userService.get(userId);
				if (user != null) {
					userNames += user.getName() + ",";
				}
			}
			userNames = userNames.substring(0, userNames.lastIndexOf(","));
			mallSite.setSiteUserNames(userNames);
		}

		mallSiteService.save(mallSite);
		addMessage(redirectAttributes, "新增成功");
		return "redirect:" + adminPath + "/mall/mallSite/update?id=" + mallSite.getId();
	}

	@RequestMapping(value = "/update", method = RequestMethod.GET)
	public String update(MallSite mallSite, Model model) {

		User user = new User();
		user.setDataScopeFilter(new DataScope(UserUtils.getLoginUser(), DataScopeEnum.orgFollow, "b", "a"));
		model.addAttribute("userList", userService.findList(user));

		model.addAttribute("mallSite", mallSite);
		return "mall/mallSite/form";
	}

	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public String update(MallSite mallSite, RedirectAttributes redirectAttributes) {

		if (StringUtils.isNotBlank(mallSite.getSiteUserIds())) {
			String userNames = "";
			String[] userIds = mallSite.getSiteUserIds().split(",");
			for (String userId : userIds) {
				User user = userService.get(userId);
				if (user != null) {
					userNames += user.getName() + ",";
				}
			}
			userNames = userNames.substring(0, userNames.lastIndexOf(","));
			mallSite.setSiteUserNames(userNames);
		}

		mallSiteService.save(mallSite);
		addMessage(redirectAttributes, "修改成功");
		return "redirect:" + adminPath + "/mall/mallSite/update?id=" + mallSite.getId();
	}

	@RequestMapping(value = "/{id}/delete", method = RequestMethod.GET)
	public String delete(@PathVariable("id") String id, int pageNo, int pageSize,
			RedirectAttributes redirectAttributes) {
		mallSiteService.delete(id);
		addMessage(redirectAttributes, "删除成功");
		return "redirect:" + adminPath + "/mall/mallSite?pageNo=" + pageNo + "&pageSize=" + pageSize;
	}

	@RequestMapping(value = "/getMySite")
	@ResponseBody
	public Result getMySite(String username) {
		Result result = new Result();

		try {
			User user = userService.getByUserName(username, UserSource.SYS.getType());
			if (user != null) {
				MallSite entity = new MallSite();
				entity.setSiteUserIds(user.getId());
				MallSite site = mallSiteService.getEntity(entity);
				if (site != null) {
					result.setObj(site);
					result.setCode(ResultCode.SUCCESS);
					result.setMsg("操作成功");
					return result;
				}
			}
			return result;
		} catch (Exception e) {
			logger.error("系统异常:{}", e);
			return Result.newExceptionInstance();
		}
	}

	@RequestMapping(value = "/getMyOrg")
	@ResponseBody
	public Result getMyOrg(String username) {
		Result result = new Result();

		try {
			User user = userService.getByUserName(username, UserSource.SYS.getType());
			if (user != null) {
				String[] orgIdList = user.getOrganizationIds().split(",");
				List<Organization> myOrgList = new ArrayList<>();
				for (String orgId : orgIdList) {
					myOrgList.add(organizationService.get(orgId));
				}

				if (myOrgList.size() > 0) {
					result.setObj(myOrgList);
					result.setCode(ResultCode.SUCCESS);
					result.setMsg("操作成功");
					return result;
				}
			}
			return result;
		} catch (Exception e) {
			logger.error("系统异常:{}", e);
			return Result.newExceptionInstance();
		}
	}
}
