package com.jf.plus.api.mobile.weixin.controller;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.sd4324530.fastweixin.api.MenuAPI;
import com.github.sd4324530.fastweixin.api.entity.Menu;
import com.github.sd4324530.fastweixin.api.entity.MenuButton;
import com.github.sd4324530.fastweixin.api.enums.MenuType;
import com.github.sd4324530.fastweixin.api.enums.ResultType;
import com.github.sd4324530.fastweixin.message.BaseMsg;
import com.github.sd4324530.fastweixin.message.CustomMsg;
import com.github.sd4324530.fastweixin.message.req.TextReqMsg;
import com.github.sd4324530.fastweixin.servlet.WeixinControllerSupport;
import com.google.common.collect.Lists;
import com.jf.plus.common.config.WXApiConfigSingle;
import com.jf.plus.common.core.Constants;
import com.jf.plus.common.core.Result;
import com.jf.plus.common.core.ResultCode;
import com.jf.plus.common.utils.StringUtils;
import com.jf.plus.core.weixin.entity.DjtWeixinMenu;
import com.jf.plus.core.weixin.service.DjtWeixinMenuService;

@Controller
@RequestMapping("/api/weixin")
public class WeixinController extends WeixinControllerSupport {

	private final static Logger logger = LoggerFactory.getLogger(WeixinController.class);

	@Autowired
	DjtWeixinMenuService djtWeixinMenuService;

	private static String TOKEN;

	@Override
	protected String getToken() {
		if (null == TOKEN) {
			TOKEN = Constants.WXTOKEN;
		}
		return TOKEN;
	}

	// 重写父类方法，处理对应的微信消息
	@Override
	protected BaseMsg handleTextMsg(TextReqMsg msg) {
		// 其他消息转发客服
		CustomMsg customMsg = new CustomMsg();
		return customMsg;
	}

	@RequestMapping("/createMenu")
	@ResponseBody
	public Result createMenu() {
		Result result = new Result();

		try {
			MenuAPI menuAPI = new MenuAPI(WXApiConfigSingle.getInstance().getApiConfig());
			Menu request = new Menu();

			// 先删除之前的菜单
			menuAPI.deleteMenu();
			DjtWeixinMenu djtWeixinMenu = new DjtWeixinMenu();
			djtWeixinMenu.setPid(0);
			djtWeixinMenu.setStatus("1");
			djtWeixinMenu.setOrderBy("a.seq");
			List<DjtWeixinMenu> weixinMenus = djtWeixinMenuService.findList(djtWeixinMenu);
			List<MenuButton> mainList = new ArrayList<MenuButton>();
			for (Iterator iterator = weixinMenus.iterator(); iterator.hasNext();) {
				DjtWeixinMenu weixinMenu = (DjtWeixinMenu) iterator.next();

				MenuButton main = new MenuButton();
				MenuType menuType = castWexinMenType(weixinMenu.getType());
				main.setType(menuType);
				main.setKey(weixinMenu.getKey());
				main.setName(weixinMenu.getName());
				if (menuType == MenuType.VIEW) {
					main.setUrl(weixinMenu.getUrl());
				}

				djtWeixinMenu.setPid(Integer.valueOf(weixinMenu.getId()));
				List<DjtWeixinMenu> subMenus = djtWeixinMenuService.findList(djtWeixinMenu);
				List<MenuButton> subButton = Lists.newArrayList();
				for (Iterator subIterator = subMenus.iterator(); subIterator.hasNext();) {
					DjtWeixinMenu subWexinMenu = (DjtWeixinMenu) subIterator.next();

					MenuButton sub = new MenuButton();
					MenuType subMenuType = castWexinMenType(subWexinMenu.getType());
					if (subMenuType == MenuType.CLICK && StringUtils.isNotBlank(subWexinMenu.getKey())) {
						sub.setKey(subWexinMenu.getKey());
					}
					sub.setName(subWexinMenu.getName());
					sub.setType(subMenuType);
					if (subMenuType == MenuType.VIEW) {
						sub.setUrl(subWexinMenu.getUrl());
					}

					subButton.add(sub);
				}
				if (CollectionUtils.isNotEmpty(subButton)) {
					main.setSubButton(subButton);
				}

				mainList.add(main);
			}

			request.setButton(mainList);
			logger.debug(request.toJsonString());
			// 创建菜单
			ResultType resultType = menuAPI.createMenu(request);
			logger.debug(resultType.toString());

			if (resultType == ResultType.SUCCESS) {
				result.setCode(ResultCode.SUCCESS);
			} else {
				result.setCode(ResultCode.RETURN_FAILURE);
			}
			result.setMsg(resultType.getDescription());
			result.setCode(ResultCode.SUCCESS);
			return result;
		} catch (Exception e) {
			logger.error("系统异常：{}", e);
			return Result.newExceptionInstance();
		}
	}

	protected MenuType castWexinMenType(String type) {
		MenuType menuType = null;
		if (type.equals(MenuType.CLICK.toString())) {
			menuType = MenuType.CLICK;
		} else if (type.equals(MenuType.VIEW.toString())) {
			menuType = MenuType.VIEW;
		}

		return menuType;
	}

}
