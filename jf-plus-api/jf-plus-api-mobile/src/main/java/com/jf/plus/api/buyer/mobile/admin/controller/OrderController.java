package com.jf.plus.api.buyer.mobile.admin.controller;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jf.plus.common.core.Result;
import com.jf.plus.common.core.ResultCode;
import com.jf.plus.common.core.ResultObj;
import com.jf.plus.common.core.enums.OrderAuditStatus;
import com.jf.plus.core.order.entity.Order;
import com.jf.plus.core.order.entity.OrderDeliveryExport;
import com.jf.plus.core.order.entity.OrderDetail;
import com.jf.plus.core.order.service.OrderDeliveryExportService;
import com.jf.plus.core.order.service.OrderService;
import com.jf.plus.core.product.service.ProductService;
import com.jf.plus.core.setting.service.AppcodeService;

import cn.iutils.common.BaseController;
import cn.iutils.common.Page;
import cn.iutils.common.utils.MPageInfo;

/**
 * 订单控制器
 * 
 * @author Tng
 *
 */
@Controller("buyerOrderController")
public class OrderController extends BaseController {
	private static final Logger LOGGER = LoggerFactory.getLogger(OrderController.class);

	@Autowired
	OrderService orderService;

	@Autowired
	AppcodeService appcodeService;
	
	@Autowired
	ProductService productService;
	
	
	@Autowired
	OrderDeliveryExportService orderDeliveryExportService;

	/**
	 * 订单列表
	 */
	@RequestMapping("/api/buyer/myOrderList")
	@ResponseBody
	public Result orderList(@RequestParam String token, Integer status, Long productId, Page<Order> page) {
		Result result = new Result();
		try {
			result = appcodeService.checkToken(token); // 验证token有效性
			if (result.getCode().intValue() != ResultCode.SUCCESS)
				return result;

			page.getCondition().put("buyerId", result.getObj().toString());
			page.getCondition().put("operStatus", status);
			page.getCondition().put("status", "1");

			page.getCondition().put("productId", productId);
			if (productId != null) {
				page.getCondition().put("operStatusLike",
						"(" + OrderAuditStatus.TORECEIVE.getType() 
						+ "," + OrderAuditStatus.MERGE.getType() 
						+ "," + OrderAuditStatus.FINISH.getType() 
						+ ")");
			}

			page.setList(orderService.findPageBuyerOrder(page));

			ResultObj resObj = new ResultObj();
			resObj.put("page", MPageInfo.transform(page));
			result.setObj(resObj);
			result.setCode(ResultCode.SUCCESS);
			return result;
		} catch (Exception e) {
			LOGGER.error("系统异常:{}", e);
			return Result.newExceptionInstance();
		}
	}
	
	/**
	 * 商品销售列表
	 */
	@RequestMapping("/api/buyer/myProductOrderList")
	@ResponseBody
	public Result myProductOrderList(@RequestParam String token, 
			String itemName,
			String catId,
			String startTime,
			String endTime,
			String brandName,
			Page<Order> page) {
		Result result = new Result();
		try {
			result = appcodeService.checkToken(token); // 验证token有效性
			if (result.getCode().intValue() != ResultCode.SUCCESS)
				return result;

			page.getCondition().put("buyerId", result.getObj().toString());
			page.getCondition().put("itemName", itemName);
			page.getCondition().put("catId", catId);
			page.getCondition().put("startTime", startTime);
			page.getCondition().put("endTime", endTime);
			page.getCondition().put("brandName", brandName);
			page.getCondition().put("operStatusLike",
					"(" + OrderAuditStatus.TORECEIVE.getType() + ","+ OrderAuditStatus.MERGE.getType() +"," + OrderAuditStatus.FINISH.getType() + ")");

			page.setList(orderService.findPageProductOrder(page));

			ResultObj resObj = new ResultObj();
			resObj.put("page", MPageInfo.transform(page));
			result.setObj(resObj);
			result.setCode(ResultCode.SUCCESS);
			return result;
		} catch (Exception e) {
			LOGGER.error("系统异常:{}", e);
			return Result.newExceptionInstance();
		}
	}

	/**
	 * 订单销售报表
	 */
	@RequestMapping("/api/buyer/orderSaleReport")
	@ResponseBody
	public Result orderSaleReport(@RequestParam String token, Long productId, Page<Order> page) {
		Result result = new Result();
		try {
			result = appcodeService.checkToken(token); // 验证token有效性
			if (result.getCode().intValue() != ResultCode.SUCCESS)
				return result;

			page.getCondition().put("status", "1");
			page.getCondition().put("productId", productId);
			if (productId != null) {
				page.getCondition().put("operStatusLike",
						"(" + OrderAuditStatus.TORECEIVE.getType() 
						+ "," + OrderAuditStatus.MERGE.getType() 
						+ "," + OrderAuditStatus.FINISH.getType() + ")");
			}

			Map<String, Object> orderSaleReportMap = orderService.sumOrderSaleReport(page);
			if (orderSaleReportMap == null) {
				orderSaleReportMap = new HashMap<>();
				orderSaleReportMap.put("totalAmount", 0);
				orderSaleReportMap.put("totalNum", 0);
			}

			ResultObj resObj = new ResultObj();
			resObj.put("report", orderSaleReportMap);
			result.setObj(resObj);
			result.setCode(ResultCode.SUCCESS);
			return result;
		} catch (Exception e) {
			LOGGER.error("系统异常:{}", e);
			return Result.newExceptionInstance();
		}
	}
	
	
	/**
	 * 订单销售报表
	 */
	@RequestMapping("/api/buyer/userOrderReport")
	@ResponseBody
	public Result userOrderReport(@RequestParam String token, String itemName,String catId,String brandName,String startTime,String endTime, Page<Order> page) {
		Result result = new Result();
		try {
			result = appcodeService.checkToken(token); // 验证token有效性
			if (result.getCode().intValue() != ResultCode.SUCCESS)
				return result;
			
			page.getCondition().put("buyerId", result.getObj().toString());
			page.getCondition().put("itemName", itemName);
			page.getCondition().put("catId", catId);
			page.getCondition().put("brandName", brandName);
			page.getCondition().put("startTime", startTime);
			page.getCondition().put("endTime", endTime);
			page.getCondition().put("operStatusLike",
					"(" + OrderAuditStatus.TORECEIVE.getType() 
					+ "," + OrderAuditStatus.MERGE.getType() 
					+ "," + OrderAuditStatus.FINISH.getType() 
					+ ")");
			
			Map<String, Object> orderSaleReportMap = orderService.sumOrderBuyerReport(page);
			if (orderSaleReportMap == null) {
				orderSaleReportMap = new HashMap<>();
				orderSaleReportMap.put("totalAmount", 0);
				orderSaleReportMap.put("totalNum", 0);
			}
			
			ResultObj resObj = new ResultObj();
			resObj.put("report", orderSaleReportMap);
			result.setObj(resObj);
			result.setCode(ResultCode.SUCCESS);
			return result;
		} catch (Exception e) {
			LOGGER.error("系统异常:{}", e);
			return Result.newExceptionInstance();
		}
	}

	/**
	 * 取消商品订单
	 */
	@RequestMapping("/api/order/cancelProduct")
	@ResponseBody
	public Result cancelOrderProduct(@RequestParam String token, @RequestParam Long productId) {
		Result result = new Result();
		try {
			result = appcodeService.checkToken(token); // 验证token有效性
			if (result.getCode().intValue() != ResultCode.SUCCESS)
				return result;

			OrderDetail orderDetail = new OrderDetail();
			orderDetail.setProductNo(productId);

			result = orderService.cancelOrderDetail(orderDetail, Long.valueOf(result.getObj().toString()));

			return result;
		} catch (Exception e) {
			LOGGER.error("系统异常:{}", e);
			return Result.newExceptionInstance();
		}
	}
	
	@RequestMapping("/api/buyer/orderExportList")
	@ResponseBody
	public Result orderExportList(@RequestParam String token,String productId,Page<OrderDeliveryExport> page) {
		Result result = new Result();

		try {
			result = appcodeService.checkToken(token); // 验证token有效性
			if (result.getCode().intValue() != ResultCode.SUCCESS)
				return result;
			
			page.getCondition().put("productId", productId);
			page.setList(orderDeliveryExportService.findBuyerPage(page));
			
			ResultObj resultObj = new ResultObj();
			resultObj.put("page", MPageInfo.transform(page));
			result.setObj(resultObj);
			result.setCode(ResultCode.SUCCESS);
			return result;
		} catch (Exception e) {
			logger.error("系统异常：{}", e);
			return Result.newExceptionInstance();
		}
	}
	
	@RequestMapping("/api/buyer/sumExportList")
	@ResponseBody
	public Result sumExportList(@RequestParam String token,String productId,Page<OrderDeliveryExport> page) {
		Result result = new Result();

		try {
			result = appcodeService.checkToken(token); // 验证token有效性
			if (result.getCode().intValue() != ResultCode.SUCCESS)
				return result;
			
			Map<String, Object> sumMap = new HashMap<>();
			
			page.getCondition().put("productId", productId);
			Map<String, Object> sumExportMap = orderDeliveryExportService.sumExport(page);
			sumMap.put("avgPrice", sumExportMap == null ? "0":sumExportMap.get("avgPrice"));
			
			//未设置
			page.getCondition().put("isExport", 0);
			Map<String, Object> sumExportMap2 = orderDeliveryExportService.countExport(page);
			sumMap.put("noExportNum", sumExportMap2 == null ? "0":sumExportMap2.get("num"));
			
			//已设置
			page.getCondition().put("isExport", 1);
			Map<String, Object> sumExportMap3 = orderDeliveryExportService.countExport(page);
			sumMap.put("isExportNum", sumExportMap3 == null ? "0":sumExportMap3.get("num"));
			
			
			ResultObj resultObj = new ResultObj();
			resultObj.put("sumExport", sumMap);
			result.setObj(resultObj);
			result.setCode(ResultCode.SUCCESS);
			return result;
		} catch (Exception e) {
			logger.error("系统异常：{}", e);
			return Result.newExceptionInstance();
		}
	}
}
