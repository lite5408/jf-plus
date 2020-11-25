package com.jf.plus.api.mobile.product.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jf.plus.common.core.Result;
import com.jf.plus.common.core.ResultCode;
import com.jf.plus.common.core.ResultObj;
import com.jf.plus.core.product.entity.OrgGroupMerchant;
import com.jf.plus.core.product.service.OrgGroupMerchantService;

import cn.iutils.common.BaseController;
import cn.iutils.common.Page;
import cn.iutils.common.utils.MPageInfo;

/**
 * 供应商分组表 接口控制器
 * 
 * @author Tng
 * @version 1.0
 */
@Controller
public class OrgGroupMerchantController extends BaseController {

	private static Logger LOGGER = LoggerFactory.getLogger(OrgGroupMerchantController.class);

	@Autowired
	private OrgGroupMerchantService orgGroupMerchantService;

	@RequestMapping(value = { "/api/orgGroupMerchantList" })
	@ResponseBody
	public Result list(Page<OrgGroupMerchant> page) {
		Result result = Result.newInstance();
		try {
			page.setList(orgGroupMerchantService.findPage(page));

			ResultObj resultObj = new ResultObj();
			resultObj.put("page", MPageInfo.transform(page));
			result.setObj(resultObj);
			result.setCode(ResultCode.SUCCESS);
			result.setMsg("查询成功");
			return result;
		} catch (Exception e) {
			LOGGER.error("查询供应商分组表列表异常:{}", e);
			return Result.newExceptionInstance();
		}

	}

	@RequestMapping(value = { "/api/saveOrgGroupMerchant" })
	@ResponseBody
	public Result save(OrgGroupMerchant orgGroupMerchant) {
		Result result = Result.newInstance();
		try {

			orgGroupMerchantService.save(orgGroupMerchant);

			result.setCode(ResultCode.SUCCESS);
			result.setMsg("新增成功");
			return result;
		} catch (Exception e) {
			LOGGER.error("新增供应商分组表异常:{}", e);
			return Result.newExceptionInstance();
		}

	}
}
