package com.jf.plus.api.mobile.order.controller;

import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.jf.plus.api.mobile.controller.BaseController;
import com.jf.plus.common.core.Result;
import com.jf.plus.common.core.ResultCode;
import com.jf.plus.common.core.ResultObj;
import com.jf.plus.core.order.entity.OrderDelivery;
import com.jf.plus.core.order.entity.OrderDeliveryDetail;
import com.jf.plus.core.order.entity.OrderDeliveryExport;
import com.jf.plus.core.order.service.OrderDeliveryDetailService;
import com.jf.plus.core.order.service.OrderDeliveryExportService;
import com.jf.plus.core.order.service.OrderDeliveryService;
import com.jf.plus.core.order.service.OrderDetailService;
import com.jf.plus.core.order.service.OrderDetailSkuService;
import com.jf.plus.core.site.service.SiteProductService;

import cn.iutils.common.Page;
import cn.iutils.common.utils.MPageInfo;
import cn.iutils.sys.entity.User;
import cn.iutils.sys.service.UserService;

/**
 * 订单发货表 接口控制器
 * 
 * @author Tng
 * @version 1.0
 */
@Controller
public class OrderDeliveryController extends BaseController {

	private static Logger LOGGER = LoggerFactory.getLogger(OrderDeliveryController.class);

	@Autowired
	private OrderDeliveryService orderDeliveryService;

	@Autowired
	private OrderDeliveryDetailService orderDeliveryDetailService;

	@Autowired
	private OrderDetailService orderDetailService;

	@Autowired
	private UserService userService;

	@Autowired
	private OrderDeliveryExportService orderDeliveryExportService;
	
	@Autowired
	private SiteProductService siteProductService;
	
	@Autowired
	private OrderDetailSkuService orderDetailSkuService;

	@RequestMapping(value = { "/api/orderDeliveryList" })
	@ResponseBody
	public Result list(@RequestParam String token, Page<OrderDelivery> page, String catId,String startDate,String endDate,OrderDelivery orderDelivery) {
		Result result = Result.newInstance();
		try {

			result = appcodeService.checkToken(token);
			if (!result.isSuccess())
				return result;

			orderDelivery.setUserId(Long.valueOf(result.getObj().toString()));
			page.setEntity(orderDelivery);
			page.getCondition().put("catId", catId);
			page.getCondition().put("startTime", startDate);
			page.getCondition().put("endTime", endDate);
			page.setOrderBy("a.confirm_date desc,a.id desc");
			page.setList(orderDeliveryService.findPage(page));

			List<OrderDelivery> orderDeliveries = page.getList();
			if (CollectionUtils.isNotEmpty(orderDeliveries)) {
				for (OrderDelivery orderDelivery2 : orderDeliveries) {
					OrderDeliveryDetail orderDeliveryDetail = new OrderDeliveryDetail();
					orderDeliveryDetail.setDeliveryId(Long.valueOf(orderDelivery2.getId()));
					orderDeliveryDetail.setStatus("1");
					List<OrderDeliveryDetail> orderDeliveryDetails = orderDeliveryDetailService
							.findList(orderDeliveryDetail);
//					for (OrderDeliveryDetail orderDeliveryDetail2 : orderDeliveryDetails) {
////						
//						
//						Page<OrderDetailSku> skuPage = new Page<>(0, 1000);
//						skuPage.getCondition().put("orderDetailId", orderDetail.getId());
//						orderDeliveryDetail2.setOrderDetailSkus(orderDetailSkuService.findPage(skuPage));
						
//					}
					orderDelivery2.setOrderDeliveryDetails(orderDeliveryDetails);
				}
			}

			ResultObj resultObj = new ResultObj();
			resultObj.put("page", MPageInfo.transform(page));
			result.setObj(resultObj);
			result.setCode(ResultCode.SUCCESS);
			result.setMsg("查询成功");
			return result;
		} catch (Exception e) {
			LOGGER.error("查询订单发货表列表异常:{}", e);
			return Result.newExceptionInstance();
		}

	}

	@RequestMapping(value = { "/api/changeDeliveryStatus" })
	@ResponseBody
	public Result changeDeliveryStatus(@RequestParam String token, OrderDelivery orderDelivery) {
		Result result = Result.newInstance();
		try {

			result = appcodeService.checkToken(token);
			if (!result.isSuccess())
				return result;

			if (StringUtils.isBlank(orderDelivery.getId())) {
				result.setCode(ResultCode.RETURN_FAILURE);
				result.setMsg("参数缺失");
				return result;
			}

			if (orderDelivery.getIsConfirm() == 1) {
				User user = userService.get(result.getObj().toString());
				orderDelivery.setConfirmDate(new Date());
				orderDelivery.setConfirmOperator(user.getName());
			}
			orderDeliveryService.save(orderDelivery);
			
			//明细确认收货
			if (orderDelivery.getIsConfirm() == 1) {
				orderDeliveryService.confirmOrderDetail(orderDelivery);
			}

			result.setCode(ResultCode.SUCCESS);
			result.setMsg("操作成功");
			return result;
		} catch (Exception e) {
			LOGGER.error("系统异常:{}", e);
			return Result.newExceptionInstance();
		}

	}
	
	
	@RequestMapping(value = { "/api/batchConfirmDelivery" })
	@ResponseBody
	public Result batchConfirmDelivery(@RequestParam String token, String[] ids,String remarks) {
		Result result = Result.newInstance();
		try {

			result = appcodeService.checkToken(token);
			if (!result.isSuccess())
				return result;

			if (ids == null || ids.length == 0) {
				result.setCode(ResultCode.RETURN_FAILURE);
				result.setMsg("参数缺失");
				return result;
			}
			
			User user = userService.get(result.getObj().toString());
			for(String id:ids){
				OrderDelivery orderDelivery = new OrderDelivery();
				orderDelivery.setId(id);
				orderDelivery.setIsConfirm(1);
				orderDelivery.setConfirmDate(new Date());
				orderDelivery.setConfirmOperator(user.getName());
				orderDelivery.setRemarks(remarks);
				orderDeliveryService.save(orderDelivery);
				
				orderDeliveryService.confirmOrderDetail(orderDelivery);
			}

			result.setCode(ResultCode.SUCCESS);
			result.setMsg("操作成功");
			return result;
		} catch (Exception e) {
			LOGGER.error("系统异常:{}", e);
			return Result.newExceptionInstance();
		}

	}
	
	@RequestMapping(value = { "/api/searchDelivery" })
	@ResponseBody
	public Result searchDelivery(@RequestParam String token,OrderDelivery orderDelivery) {
		Result result = Result.newInstance();
		try {

			result = appcodeService.checkToken(token);
			if (!result.isSuccess())
				return result;

			orderDelivery.setUserId(Long.valueOf(result.getObj().toString()));
			orderDelivery = orderDeliveryService.getSumEntity(orderDelivery);
			if(orderDelivery == null){
				result.setObj(null);
				result.setMsg("未查询到该包裹号");
				return result;
			}

			ResultObj resultObj = new ResultObj();
			resultObj.put("orderDelivery", orderDelivery);
			result.setObj(resultObj);
			result.setCode(ResultCode.SUCCESS);
			result.setMsg("查询成功");
			return result;
		} catch (Exception e) {
			LOGGER.error("查询订单发货表列表异常:{}", e);
			return Result.newExceptionInstance();
		}

	}

	/**
	 * 设置导出单销售价
	 */
	@RequestMapping("/api/delivery/export")
	@ResponseBody
	public Result deliveryExport(@RequestParam String token, @RequestParam String deliveryJSON) {
		Result result = new Result();
		try {
			result = appcodeService.checkToken(token);
			if (!result.isSuccess())
				return result;
			String userId = result.getObj().toString();
			
			List<OrderDeliveryExport> orderDeliveryExports = JSON.parseArray(deliveryJSON, OrderDeliveryExport.class);
			if (CollectionUtils.isEmpty(orderDeliveryExports)) {
				result.setCode(ResultCode.ARGUMENT_LACK_ERROR);
				result.setMsg("参数缺失");
				return result;
			}

			Long deliveryId = 0L;
			for (OrderDeliveryExport orderDeliveryExport : orderDeliveryExports) {
				deliveryId = orderDeliveryExport.getDeliveryId();
				
				OrderDelivery orderDelivery = orderDeliveryService.get(deliveryId+"");
				if(orderDelivery.getOperStatus() != null && orderDelivery.getOperStatus() == 1){
					result.setMsg("该包裹已导出，请不要重复操作！");
					result.setCode(ResultCode.RETURN_FAILURE);
					return result;
				}
				orderDeliveryExport.setExportOperator(userId);
				orderDeliveryExport.setExportDate(new Date());
				orderDeliveryExport.setOperStatus(1);
				orderDeliveryExportService.save(orderDeliveryExport);
			}

			OrderDelivery orderDelivery = new OrderDelivery();
			orderDelivery.setId(deliveryId + "");
			orderDelivery.setOperStatus(1);
			orderDeliveryService.save(orderDelivery);

			ResultObj resultObj = new ResultObj();
			resultObj.put("deliveryId", deliveryId);
			result.setObj(resultObj);
			result.setMsg("导出成功");
			result.setCode(ResultCode.SUCCESS);
			return result;
		} catch (Exception e) {
			LOGGER.error("系统异常:{}", e);
			return Result.newExceptionInstance();
		}
	}

	/**
	 * 导出销售单详情
	 */
	@RequestMapping("/api/delivery/exportDetail")
	@ResponseBody
	public Result deliveryExportDetail(@RequestParam String token, @RequestParam String deliveryId) {
		Result result = new Result();
		try {
			result = appcodeService.checkToken(token);
			if (!result.isSuccess())
				return result;

			OrderDelivery orderDelivery = orderDeliveryService.get(deliveryId);
			if (orderDelivery == null || orderDelivery.getOperStatus() != 1) {
				result.setCode(ResultCode.ARGUMENT_LACK_ERROR);
				result.setMsg("参数不正确");
				return result;
			}

			OrderDeliveryExport entity = new OrderDeliveryExport();
			entity.setDeliveryId(Long.valueOf(deliveryId));
			entity.setStatus("1");
			List<OrderDeliveryExport> orderDeliveryExports = orderDeliveryExportService.findList(entity);
			if(CollectionUtils.isNotEmpty(orderDeliveryExports)){
//				for(OrderDeliveryExport orderDeliveryExport : orderDeliveryExports){
					
					//sku
//					Page<OrderDetailSku> skuPage = new Page<>(0, 1000);
//					OrderDetailSku orderDetailSku = new OrderDetailSku();
//					orderDetailSku.setItemId(orderDeliveryExport.getItemId());
//					orderDetailSku.setOrderId(orderDeliveryExport.getOrderId());
//					skuPage.setEntity(orderDetailSku);
//					orderDeliveryExport.setOrderDetailSkus(orderDetailSkuService.findPage(skuPage));
//				}
			}

			ResultObj resObj = new ResultObj();
			resObj.put("orderDelivery", orderDelivery);
			resObj.put("orderDeliveryExports", orderDeliveryExports);
			result.setObj(resObj);
			result.setCode(ResultCode.SUCCESS);
			return result;
		} catch (Exception e) {
			LOGGER.error("系统异常:{}", e);
			return Result.newExceptionInstance();
		}
	}
}
