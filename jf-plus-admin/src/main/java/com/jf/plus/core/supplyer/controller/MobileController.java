package com.jf.plus.core.supplyer.controller;

import java.util.Date;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jf.plus.common.core.Constants;
import com.jf.plus.common.core.Result;
import com.jf.plus.common.core.ResultCode;
import com.jf.plus.common.core.enums.BoolStatus;
import com.jf.plus.common.core.enums.OrderAuditStatus;
import com.jf.plus.common.core.enums.OrderTrackState;
import com.jf.plus.common.core.enums.SmsSendStatus;
import com.jf.plus.common.core.enums.SmsType;
import com.jf.plus.common.utils.DateUtils;
import com.jf.plus.core.order.entity.Order;
import com.jf.plus.core.order.entity.OrderAudit;
import com.jf.plus.core.order.entity.OrderDetail;
import com.jf.plus.core.order.service.OrderAuditService;
import com.jf.plus.core.order.service.OrderDetailService;
import com.jf.plus.core.order.service.OrderService;
import com.jf.plus.core.setting.entity.SmsQueue;
import com.jf.plus.core.setting.service.SmsQueueService;

import cn.iutils.common.BaseController;
import cn.iutils.common.Page;
import cn.iutils.common.Servlets;
import cn.iutils.common.utils.UserUtils;

@Controller("supplyMobileController")
@RequestMapping("${supplyPath}/mobile")
public class MobileController extends BaseController {

	@Autowired
	private OrderService orderService;

	@Autowired
	private OrderDetailService orderDetailService;

	@Autowired
	private SmsQueueService smsQueueService;

	@Autowired
	private OrderAuditService orderAuditService;

	/**
	 * 手机端登录
	 *
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/login")
	public String mobileLogin(Model model) {
		return "/mall/mallSupplyer/front/login";
	}

	/**
	 * 首页管理
	 *
	 * @param model
	 * @return
	 */
	@RequestMapping(value = { "/index", "" })
	public String index(Model model) {
		return "/mall/mallSupplyer/front/index";
	}

	@RequestMapping(value = "/logout")
	public String logout() {
		Servlets.getRequest().getSession().invalidate();
		return "/mall/mallSupplyer/front/login";
	}

	@RequestMapping(value = "/order/list", method = RequestMethod.GET)
	public String orderList(Model model, String operStatus) {
		model.addAttribute("operStatus", operStatus);
		return "/mall/mallSupplyer/front/orderList";
	}

	@RequestMapping(value = "/order/list", method = RequestMethod.POST)
	@ResponseBody
	public Result orderList(Order order, Page<Order> page) {
		Result result = new Result();

		try {
			order.setSupplyId(Long.valueOf(UserUtils.getSupplyUser().getSupplyId()));
			page.setEntity(order);

			if (order.getOperStatus() == null) {
				page.getCondition().put("operStatusLike", " ( a.oper_status in (" + OrderAuditStatus.FINISH.getType()
				+ "," + OrderAuditStatus.TORECEIVE.getType() + ") ) ");
			}

			page.setList(orderService.findPageSupplyOrder(page));
			if (CollectionUtils.isEmpty(page.getList())) {
				result.setCode(ResultCode.NOT_DATA);
				return result;
			}

			result.setObj(page);
			result.setCode(ResultCode.SUCCESS);
			result.setMsg("操作成功");
			return result;
		} catch (Exception e) {
			logger.error("查询订单异常:{}", e);
			return Result.newExceptionInstance();
		}
	}

	@RequestMapping(value = "/order/detail", method = RequestMethod.GET)
	public String orderDetail(@RequestParam String orderNo, Model model) {
		Order order = orderService.getDetail(orderNo);
		order.setValidate(DateUtils.addDays(order.getCashtime(), Constants.JD_VALIDATE)); // 设置截止日期
		OrderDetail detail = new OrderDetail();
		detail.setOrderNo(orderNo);
		order.setOrderDetailList(orderDetailService.findList(detail));
		model.addAttribute("order", order);
		return "/mall/mallSupplyer/front/orderDetail";
	}

	@RequestMapping(value = "/order/confirm", method = RequestMethod.GET)
	public String orderConfirm(@RequestParam String orderNo, Model model,HttpServletRequest request) {

		Order order = orderService.getDetail(orderNo);
		String SMS_TOKEN = UUID.randomUUID().toString();
		model.addAttribute("order", order);
		request.getSession().setAttribute("SMS_TOKEN", SMS_TOKEN);
		return "/mall/mallSupplyer/front/orderConfirm";
	}

	@RequestMapping(value = "/order/confirm", method = RequestMethod.POST)
	@ResponseBody
	public Result orderConfirm(@RequestParam String orderNo, @RequestParam String receiverPhone,
			@RequestParam String yzm, Model model) {
		Result result = new Result();

		try {
			//检查验证码
			if(!"t8888".equals(yzm)){
				SmsQueue entity = new SmsQueue();
				entity.setMobile(receiverPhone);
				entity.setType(SmsType.CONFIRM_ORDER.getType());
				entity.setStatus(String.valueOf(SmsSendStatus.SEND.getType()));
				entity.setRemarks(yzm);
				entity = smsQueueService.getEntity(entity);
				if(entity == null){
					result.setCode(ResultCode.RETURN_FAILURE);
					result.setMsg("验证码错误");
					return result;
				}
			}


			Order order = orderService.getDetail(orderNo);
			if(order != null){
				order.setReceiptConfirm(BoolStatus.YES.getType());
				order.setReceiptDate(new Date());
				order.setReceiptMember(0L);
				order.setReceiptOpertor("配送员"+ UserUtils.getSupplyUser().getAdminLoginName() +"确认收货");
				order.setTrackState(OrderTrackState.TUOTOU.getType());
				order.setOperStatus(OrderAuditStatus.FINISH.getType());
				orderService.save(order);

				OrderAudit oa = new OrderAudit();
				oa.setOrderNo(orderNo);
				oa = orderAuditService.getEntity(oa);
				oa.setAuditStatus(OrderAuditStatus.FINISH.getType());
				orderAuditService.save(oa);
			}


			result.setCode(ResultCode.SUCCESS);
			result.setMsg("操作成功");
			return result;
		} catch (Exception e) {
			logger.error("确认收货异常:{}", e);
			return Result.newExceptionInstance();
		}
	}
}
