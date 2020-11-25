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
import com.jf.plus.core.product.entity.ProductSaleRule;
import com.jf.plus.core.product.service.ProductSaleRuleService;

import cn.iutils.common.BaseController;
import cn.iutils.common.Page;
import cn.iutils.common.utils.MPageInfo;

/**
 * 销售规则 接口控制器
 * 
 * @author Tng
 * @version 1.0
 */
@Controller
public class ProductSaleRuleController extends BaseController {

	private static Logger LOGGER = LoggerFactory.getLogger(ProductSaleRuleController.class);

	@Autowired
	private ProductSaleRuleService productSaleRuleService;

	@RequestMapping(value = { "/api/productSaleRuleList" })
	@ResponseBody
	public Result list(Page<ProductSaleRule> page) {
		Result result = Result.newInstance();
		try {
			page.setList(productSaleRuleService.findPage(page));

			ResultObj resultObj = new ResultObj();
			resultObj.put("page", MPageInfo.transform(page));
			result.setObj(resultObj);
			result.setCode(ResultCode.SUCCESS);
			result.setMsg("查询成功");
			return result;
		} catch (Exception e) {
			LOGGER.error("查询销售规则列表异常:{}", e);
			return Result.newExceptionInstance();
		}

	}

	@RequestMapping(value = { "/api/saveProductSaleRule" })
	@ResponseBody
	public Result save(ProductSaleRule productSaleRule) {
		Result result = Result.newInstance();
		try {

			productSaleRuleService.save(productSaleRule);

			result.setCode(ResultCode.SUCCESS);
			result.setMsg("新增成功");
			return result;
		} catch (Exception e) {
			LOGGER.error("新增销售规则异常:{}", e);
			return Result.newExceptionInstance();
		}

	}
}
