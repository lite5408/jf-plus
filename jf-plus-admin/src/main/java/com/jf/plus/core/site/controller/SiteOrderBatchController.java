//package com.jf.plus.core.site.controller;
//
//import java.util.List;
//
//import javax.servlet.http.HttpServletRequest;
//
//import org.apache.commons.collections.CollectionUtils;
//import org.apache.commons.lang3.StringUtils;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.ModelAttribute;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.ResponseBody;
//
//import com.jf.plus.common.core.Result;
//import com.jf.plus.common.core.ResultCode;
//import com.jf.plus.common.core.enums.DictType;
//import com.jf.plus.core.mall.entity.MallSite;
//import com.jf.plus.core.mall.service.MallSiteService;
//import com.jf.plus.core.order.entity.OrderBatch;
//import com.jf.plus.core.order.service.OrderBatchService;
//import com.jf.plus.core.setting.entity.Dict;
//import com.jf.plus.core.setting.service.DictService;
//
//import cn.iutils.common.BaseController;
//import cn.iutils.common.Page;
//import cn.iutils.common.utils.JStringUtils;
//import cn.iutils.common.utils.UserUtils;
//import cn.iutils.sys.entity.User;
//
///**
//* 订单主表 控制器
//* @author Tng
//* @version 1.0
//*/
//@Controller
//@RequestMapping("${adminPath}/site/orderBatch")
//public class SiteOrderBatchController extends BaseController {
//
//    @Autowired
//    private OrderBatchService orderBatchService;
//    
//    @Autowired
//    private MallSiteService mallSiteService;
//    
//    @Autowired
//    private DictService dictService;
//
//    @ModelAttribute
//    public OrderBatch get(@RequestParam(required = false) String id) {
//        OrderBatch entity = null;
//        if (JStringUtils.isNotBlank(id)) {
//            entity = orderBatchService.get(id);
//        }
//        if (entity == null) {
//            entity = new OrderBatch();
//        }
//        return entity;
//    }
//
//    @RequestMapping(value={"/list",""})
//    public String list(Model model,OrderBatch orderBatch, Page<OrderBatch> page) {
//		if (StringUtils.isNotBlank(orderBatch.getBatchNo()) || StringUtils.isNotBlank(orderBatch.getReceiveName())
//				|| StringUtils.isNotBlank(orderBatch.getMobile()) || StringUtils.isNotBlank(orderBatch.getOperStatus())) {
//			User user = UserUtils.getLoginUser();
//			orderBatch.setTradePerson(user.getName() + "_" + user.getNo());
//			page.setEntity(orderBatch);
//            model.addAttribute("page", page.setList(orderBatchService.findPage(page)));
//    	}else {
//    		if ("".equals(orderBatch.getBatchNo())) {
//    			model.addAttribute("msg","请填写条件后再进行查询操作");
//			}
//    		model.addAttribute("page", page.setList(null));
//		}
//        return "site/siteOrder/order_batch_list";
//    }
//    
//    @RequestMapping(value={"/orderBatchConfirm",""})
//    public String orderBatchConfirm(Model model,Page<MallSite> page) {
////    	model.addAttribute("mallSiteList", mallSiteService.findList(null));
////    	Dict dict = new Dict();
////    	dict.setDict(DictType.BUY_REASON.getType());
////    	model.addAttribute("dictList",dictService.findList(dict));
//        return "site/siteOrder/order_batch_confirm";
//    }
//    
//    @RequestMapping(value = "/getSessionStatus")
//	@ResponseBody
//	public Result getSessionStatus(HttpServletRequest request, String batchNo) {
//		Result result = Result.newInstance();
//		try {
//			result.setSuccess(true);
//			result.setCode(ResultCode.SUCCESS);
//			result.setObj(request.getSession().getAttribute(batchNo));
//			return result;
//		} catch (Exception e) {
//			logger.error("系统异常:{}", e);
//			return Result.newExceptionInstance();
//		}
//	}
//    
//	@RequestMapping(value = "/getBatchResult")
//	@ResponseBody
//	public Result getBatchResult(HttpServletRequest request, String batchNo) {
//		Result result = Result.newInstance();
//		try {
//			OrderBatch orderBatch = new OrderBatch();
//			orderBatch.setBatchNo(batchNo);
//			orderBatch.setOperStatus("2");
//			List<OrderBatch> validFailList = orderBatchService.findList(orderBatch);
//			
//			if (CollectionUtils.isNotEmpty(validFailList)) {
//				result.setCode(ResultCode.RETURN_FAILURE);
//				result.setMsg("订单中存在下单失败的订单");
//				result.setObj(validFailList);
//			}else {
//				result.setCode(ResultCode.SUCCESS);
//			}
//			result.setSuccess(true);
//			return result;
//		} catch (Exception e) {
//			logger.error("系统异常:{}", e);
//			return Result.newExceptionInstance();
//		}
//	}
//}
