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
import com.jf.plus.core.product.entity.SpecLib;
import com.jf.plus.core.product.service.SpecLibService;

import cn.iutils.common.BaseController;
import cn.iutils.common.Page;
import cn.iutils.common.utils.MPageInfo;

/**
 * 机构规格库 接口控制器
 * 
 * @author Tng
 * @version 1.0
 */
@Controller
public class SpecLibController extends BaseController {

	private static Logger LOGGER = LoggerFactory.getLogger(SpecLibController.class);

	@Autowired
	private SpecLibService specLibService;

	@RequestMapping(value = { "/api/specLibList" })
	@ResponseBody
	public Result list(Page<SpecLib> page) {
		Result result = Result.newInstance();
		try {
			page.setList(specLibService.findPage(page));

			ResultObj resultObj = new ResultObj();
			resultObj.put("page", MPageInfo.transform(page));
			result.setObj(resultObj);
			result.setCode(ResultCode.SUCCESS);
			result.setMsg("查询成功");
			return result;
		} catch (Exception e) {
			LOGGER.error("查询机构规格库列表异常:{}", e);
			return Result.newExceptionInstance();
		}

	}

	@RequestMapping(value = { "/api/saveSpecLib" })
	@ResponseBody
	public Result save(SpecLib specLib) {
		Result result = Result.newInstance();
		try {

			specLibService.save(specLib);

			result.setCode(ResultCode.SUCCESS);
			result.setMsg("新增成功");
			return result;
		} catch (Exception e) {
			LOGGER.error("新增机构规格库异常:{}", e);
			return Result.newExceptionInstance();
		}

	}
}
