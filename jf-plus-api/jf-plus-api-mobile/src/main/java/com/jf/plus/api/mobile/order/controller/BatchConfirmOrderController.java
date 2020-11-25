//package com.jf.plus.api.mobile.order.controller;
//
//import java.util.List;
//
//import org.apache.commons.collections.CollectionUtils;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.ResponseBody;
//
//import com.jf.plus.api.mobile.controller.BaseController;
//import com.jf.plus.common.core.Constants;
//import com.jf.plus.common.core.Result;
//import com.jf.plus.common.core.ResultCode;
//import com.jf.plus.core.order.entity.OrderBatch;
//import com.jf.plus.core.order.service.OrderBatchService;
//
//import cn.iutils.sys.entity.User;
//import cn.iutils.sys.service.UserService;
//
//@Controller
//public class BatchConfirmOrderController extends BaseController {
//
//	private static Logger LOGGER = LoggerFactory.getLogger(BatchConfirmOrderController.class);
//
//	@Autowired
//	private OrderBatchService orderBatchService;
//	
//	@Autowired
//	private UserService userService;
//
//	/**
//	 *
//	 * 批量下单
//	 * 
//	 */
//	@RequestMapping(value = "/api/batchConfirmOrder", method = { RequestMethod.POST })
//	@ResponseBody
//	public Result comfirmOrder(@RequestParam String pwd,@RequestParam String no, @RequestParam String batchNo, @RequestParam Integer source,@RequestParam String siteId,@RequestParam String remarks) {
//		Result result = new Result();
//		try{
//			if(!"A1989".equals(pwd)){
//				result.setMsg("授权失败");
//				return result;
//			}
//			User user = new User();
//			user.setNo(no);
//			user = userService.getEntity(user);
//			Result ret = orderBatchService.orderBatchConfirm(user,source,siteId,batchNo,remarks);
//			
//			OrderBatch orderBatch = new OrderBatch();
//			orderBatch.setBatchNo(batchNo);
//			orderBatch.setSource(source);
//			orderBatch.setOperStatus("2");
//			List<OrderBatch> validFailList = orderBatchService.findList(orderBatch);
//			
//			if (CollectionUtils.isNotEmpty(validFailList)) {
//				result.setCode(ResultCode.RETURN_FAILURE);
//				result.setMsg("订单中存在下单失败的订单,失败条数：" + validFailList.size());
//				//result.setObj(validFailList);
//			}else {
//				result.setCode(ResultCode.SUCCESS);
//			}
//			return result;
//		}catch(Exception e){
//			LOGGER.error("下单失败：{}", e);
//			result.setCode(ResultCode.SERVICE_EXCEPTION);
//			result.setMsg(Constants.EXCEPTION_MSG);
//			return result;
//		}
//	}
//
//}
