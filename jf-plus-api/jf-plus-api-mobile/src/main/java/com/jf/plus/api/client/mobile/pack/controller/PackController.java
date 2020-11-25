package com.jf.plus.api.client.mobile.pack.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jf.plus.api.client.mobile.controller.BaseController;
import com.jf.plus.common.core.Result;
import com.jf.plus.common.core.ResultCode;
import com.jf.plus.common.core.ResultObj;
import com.jf.plus.core.mallSetting.entity.PacksInfo;
import com.jf.plus.core.mallSetting.entity.PacksPresent;
import com.jf.plus.core.mallSetting.entity.PacksProduct;
import com.jf.plus.core.mallSetting.service.PacksInfoService;
import com.jf.plus.core.mallSetting.service.PacksPresentService;
import com.jf.plus.core.mallSetting.service.PacksProductService;

@Controller
@RequestMapping("/api/client")
public class PackController extends BaseController {

	private static Logger LOGGER = LoggerFactory.getLogger(PackController.class);

	@Autowired
	private PacksInfoService packsInfoService;

	@Autowired
	private PacksProductService packsProductService;

	@Autowired
	private PacksPresentService packsPresentService;

	/**
	 * 获取礼包详情
	 *
	 * @param packsId
	 * @return
	 */
	@RequestMapping(value = "/getPacksInfo", method = { RequestMethod.POST })
	@ResponseBody
	public Result getPacksInfo(@RequestParam Long packsId) {
		Result result = Result.newInstance();
		try {
			PacksInfo packsInfo = packsInfoService.get(packsId.toString());
			ResultObj resultObj = new ResultObj();
			resultObj.put("packsInfo", packsInfo);
			result.setObj(resultObj);
			result.setCode(ResultCode.SUCCESS);
			return result;
		} catch (Exception e) {
			LOGGER.error("获取礼包详情失败：", e);
			result = Result.newExceptionInstance();
			return result;
		}
	}

	/**
	 * 获取礼包商品列表
	 *
	 * @param packsId
	 * @return
	 */
	@RequestMapping(value = "/findPacksProductList", method = { RequestMethod.POST })
	@ResponseBody
	public Result findPacksProductList(@RequestParam Long packsId) {
		Result result = Result.newInstance();
		try {
			PacksProduct packsProduct = new PacksProduct();
			packsProduct.setPacksId(packsId);
			List<PacksProduct> productList = packsProductService.findList(packsProduct);
			ResultObj resultObj = new ResultObj();
			resultObj.put("productList", productList);
			result.setObj(resultObj);
			result.setCode(ResultCode.SUCCESS);
			return result;
		} catch (Exception e) {
			LOGGER.error("获取礼包商品列表失败：", e);
			result = Result.newExceptionInstance();
			return result;
		}
	}

	/**
	 * 获取礼包赠送详情
	 *
	 * @param packsId
	 * @return
	 */
	@RequestMapping(value = "/findPresentPacks", method = { RequestMethod.POST })
	@ResponseBody
	public Result findPresentPacks(@RequestParam Long presentId) {
		Result result = Result.newInstance();
		try {
			PacksPresent packsPresent = packsPresentService.get(presentId.toString());
			ResultObj resultObj = new ResultObj();
			resultObj.put("packsPresent", packsPresent);
			result.setObj(resultObj);
			result.setCode(ResultCode.SUCCESS);
			return result;
		} catch (Exception e) {
			LOGGER.error("获取赠送详情失败：", e);
			result = Result.newExceptionInstance();
			return result;
		}
	}

}
