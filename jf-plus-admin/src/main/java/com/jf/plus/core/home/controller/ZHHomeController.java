package com.jf.plus.core.home.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jf.plus.common.core.Result;
import com.jf.plus.common.core.ResultCode;
import com.jf.plus.common.core.ResultObj;
import com.jf.plus.common.core.enums.OrderAuditStatus;
import com.jf.plus.common.sms.SmsSender;
import com.jf.plus.common.sms.SmsSenderFactory;
import com.jf.plus.core.order.entity.Order;
import com.jf.plus.core.order.service.OrderService;
import com.jf.plus.core.product.service.ProductService;

import cn.iutils.common.BaseController;
import cn.iutils.common.DataScope;
import cn.iutils.common.Page;
import cn.iutils.common.utils.DateUtils;
import cn.iutils.common.utils.UserUtils;
import cn.iutils.sys.entity.enums.DataScopeEnum;

@Controller
@RequestMapping("${adminPath}/zh/home")
public class ZHHomeController extends BaseController {

	@Autowired
	private OrderService orderService;
	
	@Autowired
	private ProductService productService;

	@RequestMapping("/goodsOrderRPT")
	@ResponseBody
	public Result goodsOrderRPT(Order order, Page<Order> page) {
		Result result = new Result();
		try {
			order.setUser(UserUtils.getLoginUser());
			order.setDataScopeFilter(new DataScope(UserUtils.getLoginUser(), DataScopeEnum.orgFollow, "org", "u"));
			page.setEntity(order);

			if (order.getOperStatus() == null) {
				page.getCondition().put("operStatusLike", " ( jod.oper_status in (" + OrderAuditStatus.FINISH.getType() + "," + OrderAuditStatus.MERGE.getType()
						+ "," + OrderAuditStatus.TO_TRACK.getType()
						+ "," + OrderAuditStatus.TORECEIVE.getType() + ") ) ");
			}

			int orderCount = orderService.sumProdSales(page);

			Double orderAmount = orderService.sumOrderAmount(page);
			
			int prodCount = productService.sumProdCount(page);


			ResultObj resultObj = new ResultObj();
			resultObj.put("orderCount", orderCount);
			resultObj.put("orderAmount", orderAmount);
			resultObj.put("prodCount", prodCount);
			result.setObj(resultObj);
			result.setCode(ResultCode.SUCCESS);
			return result;
		} catch (Exception e) {
			logger.error("系统异常：{}", e);
			return Result.newExceptionInstance();
		}

	}
	
	@RequestMapping("/reportCount")
	@ResponseBody
	public Result reportCount(Order order, Page<Order> page) {
		Result result = new Result();
		try {
			order.setUser(UserUtils.getLoginUser());
			order.setDataScopeFilter(new DataScope(UserUtils.getLoginUser(), DataScopeEnum.orgFollow, "org", "u"));
			page.setEntity(order);
			page.getCondition().put("day", "'" + DateUtils.getDate() + "'");
			if (order.getOperStatus() == null) {
				page.getCondition().put("operStatusLike", " ( jod.oper_status in (" + OrderAuditStatus.FINISH.getType() + "," + OrderAuditStatus.MERGE.getType()
				+ "," + OrderAuditStatus.TO_TRACK.getType()
				+ "," + OrderAuditStatus.TORECEIVE.getType() + ") ) ");
			}
			int dayOrderCount = orderService.sumProdSales(page);

			Double dayOrderAmount = orderService.sumOrderAmount(page);
			
			int dayProdCount = productService.sumProdCount(page);

			ResultObj resultObj = new ResultObj();
			resultObj.put("orderCount", dayOrderCount);
			resultObj.put("orderAmount", dayOrderAmount);
			resultObj.put("prodCount", dayProdCount);
			result.setObj(resultObj);
			result.setCode(ResultCode.SUCCESS);
			return result;
		} catch (Exception e) {
			logger.error("系统异常：{}", e);
			return Result.newExceptionInstance();
		}

	}
	
	@RequestMapping(value = "/mock/sendSms")
	@ResponseBody
	public Result sendSms(@RequestParam String pwd){
		if(!"t8888".equals(pwd)){
			return Result.newExceptionInstance();
		}
		Result result = new Result(); 
		SmsSender zhSmsSender = SmsSenderFactory.buildZHSmsSender();
		zhSmsSender.setContent("您的集采订单正在确认收货，验证码是【1234】。");
		zhSmsSender.setMobile("18607198919");
		boolean isSucc = zhSmsSender.send();
		result.setCode(isSucc ? ResultCode.SUCCESS : ResultCode.RETURN_FAILURE);
		return result;
	}
}
