package com.jf.plus.api.mobile.order.controller;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.jf.plus.api.mobile.controller.BaseController;
import com.jf.plus.common.core.Result;
import com.jf.plus.common.core.ResultCode;
import com.jf.plus.common.core.ResultObj;
import com.jf.plus.common.core.enums.BoolStatus;
import com.jf.plus.common.core.enums.OrderAuditStatus;
import com.jf.plus.common.exception.ServiceException;
import com.jf.plus.common.vo.CartItem;
import com.jf.plus.common.vo.OutOrderVo;
import com.jf.plus.common.vo.SubmitOrderVo;
import com.jf.plus.core.channel.service.OrderAPIService;
import com.jf.plus.core.channel.service.ProductAPIService;
import com.jf.plus.core.mall.entity.MallSite;
import com.jf.plus.core.mall.service.MallSiteService;
import com.jf.plus.core.order.entity.Order;
import com.jf.plus.core.order.entity.OrderAudit;
import com.jf.plus.core.order.entity.OrderDetail;
import com.jf.plus.core.order.entity.OrderDetailSku;
import com.jf.plus.core.order.service.OrderAuditService;
import com.jf.plus.core.order.service.OrderDetailService;
import com.jf.plus.core.order.service.OrderDetailSkuService;
import com.jf.plus.core.order.service.OrderService;
import com.jf.plus.core.setting.entity.Dict;
import com.jf.plus.core.setting.service.DictService;

import cn.iutils.common.Page;
import cn.iutils.common.utils.MPageInfo;

@Controller
public class OrderController extends BaseController {

	private static Logger LOGGER = LoggerFactory.getLogger(OrderController.class);

	@Autowired
	private OrderService orderService;

	@Autowired
	private OrderAuditService orderAuditService;

	@Autowired
	private OrderDetailService orderDetailService;

	@Autowired
	private OrderAPIService orderAPIService;

	@Autowired
	private MallSiteService mallSiteService;

	@Autowired
	private ProductAPIService productAPIService;

	@Autowired
	private DictService dictService;
	@Autowired
	private OrderDetailSkuService orderDetailSkuService;

	/**
	 * 我的订单列表
	 *
	 * @param token
	 *            用户token
	 * @param status
	 *            订单状态
	 * @param page
	 *            分页对象
	 * @return
	 */
	@RequestMapping(value = "/api/myOrderList", method = { RequestMethod.POST })
	@ResponseBody
	public Result myOrderList(@RequestParam String token, Integer status, Long siteId, 
			String startTime,String endTime,String buyerId,String area,String brandName
			,Page<Order> page) {
		Result result = Result.newInstance();
		try {
			result = this.checkToken(token); // 验证token有效性
			if (result.getCode().intValue() != ResultCode.SUCCESS)
				return result;
			page.getCondition().put("userId", result.getObj());
			page.getCondition().put("status", status);
			page.getCondition().put("siteId", siteId);
			page.getCondition().put("startTime", startTime);
			page.getCondition().put("endTime", endTime);
			page.getCondition().put("buyerId", buyerId);
			page.getCondition().put("area", area);
			page.getCondition().put("brandName", brandName);
			List<Order> orderList = orderService.findOrderList(page);
			for (Order order : orderList) {
				Page<OrderDetail> dPage = new Page<>(0, 1000);
				dPage.getCondition().put("orderNo", order.getOrderNo());
				order.setOrderDetailList(orderDetailService.findPage(dPage));
				for(OrderDetail orderDetail : order.getOrderDetailList()){
					Page<OrderDetailSku> skuPage = new Page<>(0, 1000);
					skuPage.getCondition().put("orderDetailId", orderDetail.getId());
					orderDetail.setOrderDetailSkus(orderDetailSkuService.findPage(skuPage));
				}
			}
			page.setList(orderList);
			ResultObj resultObj = new ResultObj();
			resultObj.put("page", MPageInfo.transform(page));
			result.setObj(resultObj);
			result.setCode(ResultCode.SUCCESS);
			return result;
		} catch (Exception e) {
			LOGGER.error("获取订单列表失败：", e);
			result = Result.newExceptionInstance();
			return result;
		}
	}

	/**
	 * 我的订单详情
	 *
	 * @param token
	 *            用户token
	 * @param page
	 *            分页对象
	 * @return
	 */
	@RequestMapping(value = "/api/myOrderDetail", method = { RequestMethod.POST })
	@ResponseBody
	public Result myOrderDetail(@RequestParam String orderNo) {
		Result result = Result.newInstance();
		try {
			Order order = orderService.getDetail(orderNo);
			if (order == null) {
				result.setCode(ResultCode.RETURN_FAILURE);
				result.setMsg("订单编号无效");
				return result;
			}
			Page<OrderDetail> dPage = new Page<>(0, 1000);
			dPage.getCondition().put("orderNo", order.getOrderNo());
			order.setOrderDetailList(orderDetailService.findPage(dPage));
			//商品sku
			for(OrderDetail orderDetail : order.getOrderDetailList()){
				Page<OrderDetailSku> skuPage = new Page<>(0, 1000);
				skuPage.getCondition().put("orderDetailId", orderDetail.getId());
				orderDetail.setOrderDetailSkus(orderDetailSkuService.findPage(skuPage));
			}
			
			ResultObj resultObj = new ResultObj();
			resultObj.put("entity", order);
			result.setObj(resultObj);
			result.setCode(ResultCode.SUCCESS);
			return result;
		} catch (Exception e) {
			LOGGER.error("获取订单详情列表失败：", e);
			result = Result.newExceptionInstance();
			return result;
		}
	}

	/**
	 * 订单确认收货
	 *
	 * @param token
	 *            用户token
	 * @param orderNo
	 *            订单编号
	 * @return
	 */
	@RequestMapping(value = "/api/orderConfirm", method = { RequestMethod.POST })
	@ResponseBody
	public Result orderConfirm(@RequestParam String token, @RequestParam String orderNo) {
		Result result = Result.newInstance();
		try {
			result = this.checkToken(token); // 验证token有效性
			if (result.getCode().intValue() != ResultCode.SUCCESS)
				return result;
			orderService.orderConfirm(orderNo);
			result.setMsg("确认收货成功");
			result.setCode(ResultCode.SUCCESS);
			return result;
		} catch (Exception e) {
			LOGGER.error("订单确认收货失败：", e);
			result = Result.newExceptionInstance();
			return result;
		}
	}

	/**
	 * 获取订单物流明细
	 *
	 * @param outTradeNo
	 *            第三方订单号
	 * @param source
	 *            渠道来源
	 * @return
	 */
	@RequestMapping(value = "/api/orderTrackDetail", method = { RequestMethod.POST })
	@ResponseBody
	public Result orderTrackDetail(@RequestParam String outTradeNo, @RequestParam Integer source) {
		Result result = Result.newInstance();
		try {
			OutOrderVo outOrderVo = new OutOrderVo();
			outOrderVo.setOutTradeNo(outTradeNo);
			outOrderVo.setSource(source);
			return orderAPIService.orderTrack(outOrderVo);
		} catch (Exception e) {
			LOGGER.error("获取订单物流明细失败：", e);
			result = Result.newExceptionInstance();
			return result;
		}
	}

//	/**
//	 * 订单提交
//	 *
//	 * @param submitOrderVo
//	 * @return
//	 */
//	@RequestMapping(value = "/api/submitOrder", method = { RequestMethod.POST })
//	@ResponseBody
//	public Result submitOrder(@RequestBody SubmitOrderVo submitOrderVo) {
//		Result result = Result.newInstance();
//		try {
//			result = this.checkToken(submitOrderVo.getToken()); // 验证token有效性
//			if (!result.isSuccess())
//				return result;
//
//			//下单先同步价格
////			Set<String> spIds = new HashSet<>();
////			if(CollectionUtils.isNotEmpty(submitOrderVo.getCartItemList())){
////				for(CartItem cartItem : submitOrderVo.getCartItemList()){
////					spIds.add(cartItem.getItemId());
////				}
////				try{
////					productAPIService.syncPrice(submitOrderVo.getSiteId(), spIds, submitOrderVo.getToken() ,1);//先同步价格
////				}catch(Exception e){
////					//同步失败，不做处理
////				}
////			}
//
//			submitOrderVo.setUserId((long) result.getObj());
//			return orderService.submitOrder(submitOrderVo);
//		} catch (Exception e) {
//			LOGGER.error("订单预占库存提交失败：", e);
//
//			if(e != null && e instanceof ServiceException){
//				result.setCode(ResultCode.SERVICE_EXCEPTION);
//				result.setMsg(e.getMessage());
//				return result;
//			}
//
//			result = Result.newExceptionInstance();
//			return result;
//		}
//	}

	/**
	 * 订单支付
	 *
	 * @param token
	 * @param submitOrderVo
	 * @return
	 */
	@RequestMapping(value = "/api/payOrder", method = { RequestMethod.POST })
	@ResponseBody
	public Result payOrder(@RequestBody SubmitOrderVo submitOrderVo) {
		Result result = Result.newInstance();
		try {
			result = this.checkToken(submitOrderVo.getToken()); // 验证token有效性
			if (!result.isSuccess())
				return result;
			submitOrderVo.setUserId((long) result.getObj());
			return orderService.confirmOrder(submitOrderVo);
		} catch (Exception e) {
			LOGGER.error("订单支付失败：", e);

			if(e != null && e instanceof ServiceException){
				result.setCode(ResultCode.SERVICE_EXCEPTION);
				result.setMsg(e.getMessage());
				return result;
			}

			result = Result.newExceptionInstance();
			return result;
		}
	}

	/**
	 * 获取订单运费
	 *
	 * @param submitOrderVo
	 * @return
	 */
	@RequestMapping(value = "/api/getFreightFare", method = { RequestMethod.POST })
	@ResponseBody
	public Result getFreightFare(@RequestBody SubmitOrderVo submitOrderVo) {
		Result result = Result.newInstance();
		try {
			result = orderAPIService.getOrderFreight(submitOrderVo);
			if (!result.isSuccess())
				return result;
			ResultObj resultObj = new ResultObj();
			MallSite mallSite = mallSiteService.get(submitOrderVo.getSiteId().toString());
			int cashRate = mallSite.getCashRate().intValue();
			resultObj.put("freight", ((OutOrderVo) result.getObj()).getFreight() * cashRate);
			result.setObj(resultObj);
			result.setCode(ResultCode.SUCCESS);
			result.setMsg("查询订单运费成功");
			return result;
		} catch (Exception e) {
			LOGGER.error("获取订单运费失败：", e);
			result = Result.newExceptionInstance();
			return result;
		}
	}


//	/**
//	 * 撤销已完成订单
//	 *
//	 * @param token
//	 * @param orderNo
//	 * @param processInstanceId
//	 * @return
//	 */
//	@RequestMapping(value = "/api/cancel", method = { RequestMethod.POST })
//	@ResponseBody
//	public Result cancelFinishedOrder(@RequestParam String pwd, @RequestParam Long orderId, @RequestParam String reasons) {
//		Result result = Result.newInstance();
//		if (!"A0118".equals(pwd)) {
//			result.setMsg("授权失败");
//			return result;
//		}
//		try {
//			orderService.cancelFinishOrder(orderId, reasons);
//			result.setCode(ResultCode.SUCCESS);
//			result.setMsg("撤销订单已完成，订单Id：" + orderId);
//			LOGGER.info("撤销订单已完成，订单Id：" + orderId);
//			return result;
//		} catch (Exception e) {
//			LOGGER.error("撤销失败：" + e.getMessage());
//			LOGGER.error("撤销失败的订单Id：" + orderId);
//			return Result.newExceptionInstance();
//		}
//
//	}

	/**
	 * 订单撤销
	 *
	 * @param token
	 * @param orderNo
	 * @param processInstanceId
	 * @return
	 */
	@RequestMapping(value = "/api/orderCancel", method = { RequestMethod.POST })
	@ResponseBody
	public Result orderCancel(@RequestParam String token, @RequestParam String orderNo,
			@RequestParam String processInstanceId) {
		Result result = Result.newInstance();
		try {
			result = this.checkToken(token); // 验证token有效性
			if (!result.isSuccess())
				return result;
			result = orderService.userCancel(orderNo, processInstanceId);
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

	/**
	 * 查询是否可撤销
	 *
	 * @param token
	 * @param orderNo
	 * @return
	 */
	@RequestMapping(value = "/api/isCancel", method = { RequestMethod.POST })
	@ResponseBody
	public Result isCancel(@RequestParam String token, @RequestParam String orderNo) {
		Result result = Result.newInstance();
		try {
			result = this.checkToken(token); // 验证token有效性
			if (!result.isSuccess())
				return result;
			int isCancel = BoolStatus.NO.getType();
			OrderAudit oa = new OrderAudit();
			oa.setOrderNo(orderNo);
			oa.setAuditStatus(OrderAuditStatus.LEVEL_1.getType());
			if (orderAuditService.getEntity(oa) != null)
				isCancel = BoolStatus.YES.getType();
			ResultObj resultObj = new ResultObj();
			resultObj.put("isCancel", isCancel);
			result.setObj(resultObj);
			result.setCode(ResultCode.SUCCESS);
			result.setMsg("查询成功");
			return result;
		} catch (Exception e) {
			LOGGER.error("查询失败：", e);
			result = Result.newExceptionInstance();
			return result;
		}
	}

	/**
	 * 订单审批通过
	 *
	 * @param token
	 * @param auditId
	 * @param processInstanceId
	 * @return
	 */
	@RequestMapping(value = "/api/orderAuditPass", method = { RequestMethod.POST })
	@ResponseBody
	public Result orderAuditPass(@RequestParam String token, @RequestParam String auditList) {
		Result result = Result.newInstance();
		try {
			result = this.checkToken(token); // 验证token有效性
			if (!result.isSuccess())
				return result;
			List<OrderAudit> list = JSON.parseArray(auditList, OrderAudit.class);
			result = orderService.orderAuditPass((Long) result.getObj(), list);
			if (!result.isSuccess())
				return result;
			result.setCode(ResultCode.SUCCESS);
			result.setMsg("订单审批成功");
			return result;
		} catch (Exception e) {
			LOGGER.error("订单审批失败：", e);

			if(e != null && e instanceof ServiceException){
				result.setCode(ResultCode.SERVICE_EXCEPTION);
				result.setMsg(e.getMessage());
				return result;
			}

			result = Result.newExceptionInstance();
			return result;
		}
	}

	/**
	 * 订单审批不通过
	 *
	 * @param token
	 * @param auditId
	 * @param processInstanceId
	 * @return
	 */
	@RequestMapping(value = "/api/orderAuditNotPass", method = { RequestMethod.POST })
	@ResponseBody
	public Result orderAuditNotPass(@RequestParam String token, @RequestParam String auditList) {
		Result result = Result.newInstance();
		try {
			result = this.checkToken(token); // 验证token有效性
			if (!result.isSuccess())
				return result;
			List<OrderAudit> list = JSON.parseArray(auditList, OrderAudit.class);
			result = orderService.orderAuditNotPass(list);
			if (!result.isSuccess())
				return result;
			result.setCode(ResultCode.SUCCESS);
			result.setMsg("订单审批成功");
			return result;
		} catch (Exception e) {
			LOGGER.error("订单审批失败：", e);

			if(e != null && e instanceof ServiceException){
				result.setCode(ResultCode.SERVICE_EXCEPTION);
				result.setMsg(e.getMessage());
				return result;
			}

			result = Result.newExceptionInstance();
			return result;
		}
	}

	@RequestMapping(value = "/api/dictByType", method = { RequestMethod.POST , RequestMethod.GET})
	@ResponseBody
	public Result dictByType(@RequestParam String siteId,@RequestParam String dict) {
		Result result = Result.newInstance();
		try {
			MallSite mallSite = mallSiteService.get(siteId);
			Dict entity = new Dict();
			entity.setOrgId(mallSite.getOrgId());
			entity.setDict(dict);
			List<Dict> dictList = dictService.findList(entity);

			ResultObj resultObj = new ResultObj();
			resultObj.put("dictList", dictList);

			result.setObj(resultObj);
			result.setCode(ResultCode.SUCCESS);
			result.setMsg("查询字典成功");
			return result;
		} catch (Exception e) {
			LOGGER.error("查询字典失败：", e);

			result = Result.newExceptionInstance();
			return result;
		}
	}
	
}
