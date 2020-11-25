package com.jf.plus.api.mobile.advert.controller;

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
import com.jf.plus.common.core.enums.Status;
import com.jf.plus.core.site.entity.SiteAdvert;
import com.jf.plus.core.site.service.SiteAdvertService;

import cn.iutils.common.Page;
import cn.iutils.common.utils.MPageInfo;

@Controller
public class AdvertController extends BaseController {

	private static Logger LOGGER = LoggerFactory.getLogger(AdvertController.class);

	@Autowired
	private SiteAdvertService siteAdvertService;

	/**
	 * 获取商城首页专题列表
	 *
	 * @param siteId
	 * @param page
	 * @return
	 */
	@RequestMapping(value = "/api/advertList", method = { RequestMethod.POST })
	@ResponseBody
	public Result advertList(@RequestParam Long siteId, Page<SiteAdvert> page) {
		Result result = Result.newInstance();
		try {
			page.setPageSize(Integer.MAX_VALUE);
			page.getCondition().put("siteId", siteId);
			page.getCondition().put("status", Status.NORMAL.getType());
			page.setOrderBy("a.sort asc");
			page.setList(siteAdvertService.findPage(page));
			ResultObj resultObj = new ResultObj();
			resultObj.put("page", MPageInfo.transform(page));
			result.setObj(resultObj);
			result.setCode(ResultCode.SUCCESS);
			return result;
		} catch (Exception e) {
			LOGGER.error("获取首页专题信息失败：", e);
			result = Result.newExceptionInstance();
			return result;
		}
	}

}
