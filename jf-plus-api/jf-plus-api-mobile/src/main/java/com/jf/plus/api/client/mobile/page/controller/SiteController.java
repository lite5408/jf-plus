package com.jf.plus.api.client.mobile.page.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.jf.plus.api.client.mobile.controller.BaseController;
import com.jf.plus.common.core.Result;
import com.jf.plus.common.core.ResultCode;
import com.jf.plus.common.core.ResultObj;
import com.jf.plus.core.mall.entity.MallSite;
import com.jf.plus.core.mall.service.MallSiteService;

@Controller
@RequestMapping("/api/client")
public class SiteController extends BaseController {

	private static Logger LOGGER = LoggerFactory.getLogger(SiteController.class);

	@Autowired
	private MallSiteService mallSiteService;

	/**
	 * 获取站点配置
	 *
	 * @param siteId
	 * @return
	 */
	@RequestMapping(value = "/getSiteConfig")
	@ResponseBody
	public Result getSiteConfig(@RequestParam String siteId) {
		Result result = Result.newInstance();
		try {
			MallSite mallSite = mallSiteService.get(siteId);
			ResultObj resultObj = new ResultObj();
			resultObj.put("mallSite", mallSite);
			result.setObj(resultObj);
			result.setCode(ResultCode.SUCCESS);
			return result;
		} catch (Exception e) {
			LOGGER.error("获取站点配置失败：", e);
			result = Result.newExceptionInstance();
			return result;
		}
	}

}
