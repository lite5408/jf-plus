package com.jf.plus.api.mobile.shopping.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jf.plus.api.mobile.controller.BaseController;
import com.jf.plus.common.core.Constants;
import com.jf.plus.common.core.Result;
import com.jf.plus.common.core.ResultCode;
import com.jf.plus.common.core.ResultObj;

import cn.iutils.common.utils.CacheUtils;

@Controller
public class ShopCartController extends BaseController {
	
	private String CART_SHOP = "CART_SHOP_";

	private static Logger LOGGER = LoggerFactory.getLogger(ShopCartController.class);

	@RequestMapping(value = "/api/getShopCart")
	@ResponseBody
	public Result getShopCart(@RequestParam String token) {
		Result result = Result.newInstance();
		try {
			result = this.checkToken(token); // 验证token有效性
			if (result.getCode().intValue() != ResultCode.SUCCESS)
				return result;
			
			ResultObj resultObj = new ResultObj();
			resultObj.put("shopCart", CacheUtils.get(CART_SHOP.concat(result.getObj().toString()), "shopCart"));
			result.setObj(resultObj);
			result.setCode(ResultCode.SUCCESS);
			return result;
		} catch (Exception e) {
			LOGGER.error("获取购物车列表失败：", e);
			result = Result.newExceptionInstance();
			return result;
		}
	}

	@RequestMapping(value = "/api/pushShopCart")
	@ResponseBody
	public Result pushShopCart(@RequestParam String token,@RequestParam String shopCart) {
		Result result = new Result();
		try {
			result = this.checkToken(token); // 验证token有效性
			if (result.getCode().intValue() != ResultCode.SUCCESS)
				return result;
			
			CacheUtils.put(CART_SHOP.concat(result.getObj().toString()), "shopCart", shopCart);
			
			ResultObj resultObj = new ResultObj();
			resultObj.put("shopCart", shopCart);
			result.setObj(resultObj);
			result.setCode(ResultCode.SUCCESS);
			return result;
		} catch (Exception e) {
			LOGGER.error("操作购物车失败：{}", e);
			result.setCode(ResultCode.SERVICE_EXCEPTION);
			result.setMsg(Constants.EXCEPTION_MSG);
			return result;
		}
	}
	
	/**
	 * 立即购买
	 */
	private String BUY_SHOP = "BUY_SHOP_";


	@RequestMapping(value = "/api/getBuyCart")
	@ResponseBody
	public Result getBuyCart(@RequestParam String token) {
		Result result = Result.newInstance();
		try {
			result = this.checkToken(token); // 验证token有效性
			if (result.getCode().intValue() != ResultCode.SUCCESS)
				return result;
			
			ResultObj resultObj = new ResultObj();
			resultObj.put("shopCart", CacheUtils.get(BUY_SHOP.concat(result.getObj().toString()), "shopCart"));
			result.setObj(resultObj);
			result.setCode(ResultCode.SUCCESS);
			return result;
		} catch (Exception e) {
			LOGGER.error("获取购物车列表失败：", e);
			result = Result.newExceptionInstance();
			return result;
		}
	}

	@RequestMapping(value = "/api/pushBuyCart")
	@ResponseBody
	public Result pushBuyCart(@RequestParam String token,@RequestParam String shopCart) {
		Result result = new Result();
		try {
			result = this.checkToken(token); // 验证token有效性
			if (result.getCode().intValue() != ResultCode.SUCCESS)
				return result;
			
			CacheUtils.put(BUY_SHOP.concat(result.getObj().toString()), "shopCart", shopCart);
			
			ResultObj resultObj = new ResultObj();
			resultObj.put("shopCart", shopCart);
			result.setObj(resultObj);
			result.setCode(ResultCode.SUCCESS);
			return result;
		} catch (Exception e) {
			LOGGER.error("操作购物车失败：{}", e);
			result.setCode(ResultCode.SERVICE_EXCEPTION);
			result.setMsg(Constants.EXCEPTION_MSG);
			return result;
		}
	}
}
