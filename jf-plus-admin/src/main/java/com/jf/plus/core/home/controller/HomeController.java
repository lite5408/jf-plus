package com.jf.plus.core.home.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.jf.plus.common.core.enums.LoginType;
import com.jf.plus.core.mall.entity.MallSite;
import com.jf.plus.core.mall.service.MallSiteService;
import com.jf.plus.core.setting.entity.OrgSetting;
import com.jf.plus.core.setting.service.OrgSettingService;

import cn.iutils.common.BaseController;
import cn.iutils.common.utils.UserUtils;

/**
 * 首页控制器
 * 
 * @author cc
 */
@Controller
@RequestMapping(value = "${adminPath}/welcome")
public class HomeController extends BaseController {

	@Autowired
	private MallSiteService mallSiteService;

	@Autowired
	private OrgSettingService orgSettingService;

	@RequestMapping(method = RequestMethod.GET)
	public String welcome(Model model) {
		if (UserUtils.getLoginUser().getLoginType() != null
				&& UserUtils.getLoginUser().getLoginType().equals(LoginType.USER.getType())) {
			OrgSetting entity = new OrgSetting();
			entity.setOrgId(Long.valueOf(UserUtils.getLoginUser().getOrganizationId()));
			entity = orgSettingService.getEntity(entity);
			if (entity != null) {
				return entity.getWelcomePage();
			} else {
				return "welcome/default";
			}
		} else {
			MallSite mallSite = mallSiteService.get(UserUtils.getMySite().getSiteId());
			model.addAttribute("mySite", mallSite);
			return "welcome/site_default";
		}

	}

}
