package com.jf.plus.api.client.mobile.order.controller;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jf.plus.api.mobile.controller.BaseController;
import com.jf.plus.common.core.Result;
import com.jf.plus.common.core.ResultCode;
import com.jf.plus.common.core.ResultObj;
import com.jf.plus.common.core.enums.BoolStatus;
import com.jf.plus.common.core.enums.OrderAuditStatus;
import com.jf.plus.common.core.enums.OrderTrackState;
import com.jf.plus.common.core.enums.UserType;
import com.jf.plus.common.exception.ServiceException;
import com.jf.plus.common.vo.SubmitOrderVo;
import com.jf.plus.core.order.entity.Order;
import com.jf.plus.core.order.entity.OrderDetail;
import com.jf.plus.core.order.entity.OrderDetailSku;
import com.jf.plus.core.order.service.OrderDetailService;
import com.jf.plus.core.order.service.OrderDetailSkuService;
import com.jf.plus.core.order.service.OrderService;

import cn.iutils.common.Page;
import cn.iutils.common.utils.MPageInfo;
import cn.iutils.sys.entity.User;
import cn.iutils.sys.service.UserService;

@Controller("cOrderController")
@RequestMapping("/api/client")
public class OrderController extends BaseController {

	private static Logger LOGGER = LoggerFactory.getLogger(OrderController.class);

	@Autowired
	private OrderService orderService;

	@Autowired
	private UserService userService;
	
	@Autowired
	private OrderDetailService orderDetailService;
	
	@Autowired
	private OrderDetailSkuService orderDetailSkuService;

//	/**
//	 * 商品订单提交
//	 *
//	 * @param submitOrderVo
//	 * @return
//	 */
//	@RequestMapping(value = "/submitOrder", method = { RequestMethod.POST })
//	@ResponseBody
//	public Result submitOrder(@RequestBody SubmitOrderVo submitOrderVo) {
//		Result result = Result.newInstance();
//		try {
//			result = this.checkToken(submitOrderVo.getToken()); // 验证token有效性
//			if (!result.isSuccess())
//				return result;
//			submitOrderVo.setUserId((long) result.getObj());
//			return orderService.submitOrderClient(submitOrderVo);
//		} catch (Exception e) {
//			LOGGER.error("订单预占库存提交失败：", e);
//			if (e != null && e instanceof ServiceException) {
//				result.setCode(ResultCode.SERVICE_EXCEPTION);
//				result.setMsg(e.getMessage());
//				return result;
//			}
//			result = Result.newExceptionInstance();
//			return result;
//		}
//	}

	/**
	 * 商品订单提交（带SKU）
	 *
	 * @param submitOrderVo
	 * @return
	 */
	@RequestMapping(value = "/submitOrderSku", method = { RequestMethod.POST })
	@ResponseBody
	public Result submitOrderSku(@RequestBody SubmitOrderVo submitOrderVo) {
		Result result = Result.newInstance();
		try {
			result = this.checkToken(submitOrderVo.getToken()); // 验证token有效性
			if (!result.isSuccess())
				return result;

			submitOrderVo.setUserId((long) result.getObj());
			User user = userService.get(result.getObj().toString());
			
			//下单检查是否是代理商的权限
			result = checkLoginPromise(UserType.SALER.getType(), user.getRoleGroupIds());
			if(!result.isSuccess()){
				return result;
			}
			
			String orgId = user.getOrganizationIds().split(",")[0];
			submitOrderVo.setOrgId(Long.valueOf(orgId));

			return orderService.submitOrderClientSku(submitOrderVo);
		} catch (Exception e) {
			LOGGER.error("订单预占库存提交失败：", e);
			if (e != null && e instanceof ServiceException) {
				result.setCode(ResultCode.SERVICE_EXCEPTION);
				result.setMsg(e.getMessage());
				return result;
			}
			result = Result.newExceptionInstance();
			return result;
		}
	}

//	/**
//	 * 礼包订单提交
//	 *
//	 * @param submitOrderVo
//	 * @return
//	 */
//	@RequestMapping(value = "/submitPacksOrder", method = { RequestMethod.POST })
//	@ResponseBody
//	public Result submitPacksOrder(@RequestBody SubmitOrderVo submitOrderVo) {
//		Result result = Result.newInstance();
//		try {
//			result = this.checkToken(submitOrderVo.getToken()); // 验证token有效性
//			if (!result.isSuccess())
//				return result;
//			submitOrderVo.setUserId((long) result.getObj());
//			return orderService.submitPacksOrderClient(submitOrderVo);
//		} catch (Exception e) {
//			LOGGER.error("订单预占库存提交失败：", e);
//			if (e != null && e instanceof ServiceException) {
//				result.setCode(ResultCode.SERVICE_EXCEPTION);
//				result.setMsg(e.getMessage());
//				return result;
//			}
//			result = Result.newExceptionInstance();
//			return result;
//		}
//	}

//	/**
//	 * 商品订单支付
//	 *
//	 * @param token
//	 * @param submitOrderVo
//	 * @return
//	 */
//	@RequestMapping(value = "/payOrder", method = { RequestMethod.POST })
//	@ResponseBody
//	public Result payOrder(@RequestBody SubmitOrderVo submitOrderVo) {
//		Result result = Result.newInstance();
//		try {
//			result = this.checkToken(submitOrderVo.getToken()); // 验证token有效性
//			if (!result.isSuccess())
//				return result;
//			submitOrderVo.setUserId((long) result.getObj());
//			return orderService.confirmOrderClient(submitOrderVo);
//		} catch (Exception e) {
//			LOGGER.error("订单支付失败：", e);
//			if (e != null && e instanceof ServiceException) {
//				result.setCode(ResultCode.SERVICE_EXCEPTION);
//				result.setMsg(e.getMessage());
//				return result;
//			}
//			result = Result.newExceptionInstance();
//			return result;
//		}
//	}

	/**
	 * 商品订单支付（带SKU）
	 *
	 * @param token
	 * @param submitOrderVo
	 * @return
	 */
	@RequestMapping(value = "/payOrderSku", method = { RequestMethod.POST })
	@ResponseBody
	public Result payOrderSku(@RequestBody SubmitOrderVo submitOrderVo) {
		Result result = Result.newInstance();
		try {
			result = this.checkToken(submitOrderVo.getToken()); // 验证token有效性
			if (!result.isSuccess())
				return result;
			submitOrderVo.setUserId((long) result.getObj());
			return orderService.confirmOrderClientSku(submitOrderVo);
		} catch (Exception e) {
			LOGGER.error("订单支付失败：", e);
			if (e != null && e instanceof ServiceException) {
				result.setCode(ResultCode.SERVICE_EXCEPTION);
				result.setMsg(e.getMessage());
				return result;
			}
			result = Result.newExceptionInstance();
			return result;
		}
	}

	/**
	 * 订单撤销
	 *
	 * @param token
	 * @param orderNo
	 * @return
	 */
	@RequestMapping(value = "/orderCancelSku", method = { RequestMethod.POST })
	@ResponseBody
	public Result orderCancelSku(@RequestParam String token, @RequestParam String orderNo) {
		Result result = Result.newInstance();
		try {
			result = this.checkToken(token); // 验证token有效性
			if (!result.isSuccess())
				return result;
			result = orderService.userCancelSku(orderNo,result.getObj().toString());
			if (!result.isSuccess())
				return result;
			result.setCode(ResultCode.SUCCESS);
			result.setMsg("订单撤销成功");
			return result;
		} catch (Exception e) {
			LOGGER.error("订单撤销失败：", e);
			result = Result.newExceptionInstance();
			return result;
		}
	}

//	/**
//	 * 礼包订单支付
//	 *
//	 * @param token
//	 * @param submitOrderVo
//	 * @return
//	 */
//	@RequestMapping(value = "/payPacksOrder", method = { RequestMethod.POST })
//	@ResponseBody
//	public Result payPacksOrder(@RequestBody SubmitOrderVo submitOrderVo) {
//		Result result = Result.newInstance();
//		try {
//			result = this.checkToken(submitOrderVo.getToken()); // 验证token有效性
//			if (!result.isSuccess())
//				return result;
//			submitOrderVo.setUserId((long) result.getObj());
//			return orderService.confirmPacksOrderClient(submitOrderVo);
//		} catch (Exception e) {
//			LOGGER.error("订单支付失败：", e);
//			if (e != null && e instanceof ServiceException) {
//				result.setCode(ResultCode.SERVICE_EXCEPTION);
//				result.setMsg(e.getMessage());
//				return result;
//			}
//			result = Result.newExceptionInstance();
//			return result;
//		}
//	}

	/**
	 * 订单销量统计
	 */
	@RequestMapping("/userOrderReport")
	@ResponseBody
	public Result userOrderReport(@RequestParam String token, String startTime, String endTime, String catId,
			Page<Order> page) {
		Result result = new Result();
		try {
			result = appcodeService.checkToken(token);
			if(!result.isSuccess()){
				return result;
			}
			
			page.getCondition().put("userId", result.getObj().toString());
			page.getCondition().put("startTime", startTime);
			page.getCondition().put("endTime", endTime);
			page.getCondition().put("catId", catId);
			//汇总
			Map<String, Object> sumOrderReport = orderService.sumOrderSaleReport(page);
			if(sumOrderReport == null){
				sumOrderReport = new HashMap<>();
				sumOrderReport.put("totalAmount", 0);
				sumOrderReport.put("totalNum", 0);
			}
			
			//待发货
			page.getCondition().put("operStatusLike","(" + 
						OrderAuditStatus.TORECEIVE.getType() + "," + OrderAuditStatus.MERGE.getType() + ")"
					);
			page.getCondition().put("trackState",OrderTrackState.NEW.getType());
			Map<String, Object> toDeliveryReport = orderService.sumToDeliveryReport(page);
			if(toDeliveryReport == null){
				toDeliveryReport = new HashMap<>();
				toDeliveryReport.put("totalAmount", 0);
				toDeliveryReport.put("totalNum", 0);
			}

			// 待收货
			page.getCondition().put("isConfirm", BoolStatus.NO.getType());
			Map<String, Object> toReceiverReport = orderService.sumOrderReport(page);
			if(toReceiverReport == null){
				toReceiverReport = new HashMap<>();
				toReceiverReport.put("totalAmount", 0);
				toReceiverReport.put("totalNum", 0);
			}
			toReceiverReport.put("isConfirm", BoolStatus.NO.getType());
			Map<String, Object> deliveryReport = orderService.sumDeliveryReport(page);
			if(deliveryReport == null){
				deliveryReport = new HashMap<>();
				deliveryReport.put("deliveryNum", 0);
			}
			
			toReceiverReport.putAll(deliveryReport);

			//已收货
			page.getCondition().put("isConfirm", BoolStatus.YES.getType());
			Map<String, Object> finishReport = orderService.sumOrderReport(page);
			if(finishReport == null){
				finishReport = new HashMap<>();
				finishReport.put("totalAmount", 0);
				finishReport.put("totalNum", 0);
			}
			finishReport.put("isConfirm", BoolStatus.YES.getType());
			Map<String, Object> deliveryReport2 = orderService.sumDeliveryReport(page);
			if(deliveryReport2 == null){
				deliveryReport2 = new HashMap<>();
				deliveryReport2.put("deliveryNum", 0);
			}
			finishReport.putAll(deliveryReport2);
			
			
			ResultObj resObj = new ResultObj();
			resObj.put("sumOrderReport", sumOrderReport);//待发货数量
			resObj.put("toDeliveryReport", toDeliveryReport);//待发货数量
			resObj.put("toReceiverReport", toReceiverReport);
			resObj.put("finishReport", finishReport);
			result.setObj(resObj);
			result.setCode(ResultCode.SUCCESS);
			return result;
		} catch (Exception e) {
			LOGGER.error("系统异常:{}", e);
			return Result.newExceptionInstance();
		}
	}
	
	
	/**
	 * 订单明细搜索
	 */
	@RequestMapping("/orderDetailList")
	@ResponseBody
	public Result orderDetailList(@RequestParam String token, String startDate, String endDate, String catId,String trackState,
			String operStatus,
			Page<OrderDetail> page) {
		Result result = new Result();
		try{
			result = appcodeService.checkToken(token);
			if(!result.isSuccess()){
				return result;
			}
			
			page.getCondition().put("userId", result.getObj().toString());
			page.getCondition().put("startTime", startDate);
			page.getCondition().put("endTime", endDate);
			page.getCondition().put("catId", catId);
			if(StringUtils.isBlank(operStatus)){
				page.getCondition().put("operStatusLike", "(" + OrderAuditStatus.TORECEIVE.getType()+","+OrderAuditStatus.MERGE.getType()+")");
			}else{
				page.getCondition().put("operStatus", operStatus);
			}
			if(StringUtils.isBlank(trackState)){
				page.getCondition().put("trackState", OrderTrackState.NEW.getType());
			}else{
				page.getCondition().put("trackState", trackState);
			}
			page.setList(orderDetailService.findReportPage(page));
			if(CollectionUtils.isNotEmpty(page.getList())){
				for(OrderDetail orderDetail : page.getList()){
					Page<OrderDetailSku> skuPage = new Page<>(0, 100000);
					skuPage.getCondition().put("groupBy", "a.spec_color_text,a.spec_size_text");
					skuPage.getCondition().put("itemId", orderDetail.getItemId());
					skuPage.getCondition().put("userId", result.getObj().toString());
					orderDetail.setOrderDetailSkus(orderDetailSkuService.findSumPage(skuPage));
				}
			}
			
			ResultObj resultObj = new ResultObj();
			resultObj.put("page", MPageInfo.transform(page));
			result.setCode(ResultCode.SUCCESS);
			result.setObj(resultObj);
			return result;
			
		}catch(Exception e){
			LOGGER.error("系统异常：",e);
			return Result.newExceptionInstance();
		}
		
	}

}
