package com.jf.plus.core.activiti.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jf.plus.common.core.Result;
import com.jf.plus.core.activiti.api.OrderAuditListAPI;
import com.jf.plus.core.activiti.api.OrderAuditNotPassAPI;
import com.jf.plus.core.activiti.api.OrderAuditPassAPI;
import com.jf.plus.core.activiti.api.config.ApiConfig;
import com.jf.plus.core.activiti.api.response.BaseResponse;
import com.jf.plus.core.order.entity.OrderAudit;

import cn.iutils.common.BaseController;
import cn.iutils.common.Page;

/**
 * 订单审批控制器
 * @author Tng
 * @version 1.0
 *
 */
@Controller
@RequestMapping("${adminPath}/order/act")
public class ActivitiController extends BaseController{
	
	/**
	 * 我的审批订单
	 * @return
	 */
	@RequestMapping(value = "/myOrderAudit")
	public String myOrderAudit(Model model,Integer status,OrderAudit orderAudit,Page<OrderAudit> page){
		OrderAuditListAPI orderAuditListAPI = new OrderAuditListAPI(ApiConfig.getInstance());
		BaseResponse baseResponse = orderAuditListAPI.submit(status);
		if(baseResponse.isSuccess()){
			JSONObject resultJSON = JSONObject.parseObject(baseResponse.getObj().toString());
			JSONObject pageJSON = resultJSON.getJSONObject("page");
			JSONObject _page = pageJSON.getJSONObject("page");
			page.setTotal(_page.getInteger("total"));
			page.setPageNo(_page.getInteger("nowpage"));
			page.setPageSize(_page.getInteger("records"));
			
			List<OrderAudit> orderAuditList = JSON.parseArray(pageJSON.getString("rows"), OrderAudit.class);
			page.setList(orderAuditList);
			
		}
		model.addAttribute("status", status);
		model.addAttribute("page", page);
		if(status == 1){
			return "/activiti/orderAudit/list";
		}else{
			return "/activiti/orderAudit/history";
		}
	}
	
	@RequestMapping(value = "/audit")
	@ResponseBody
	public Result audit(String auditList,@RequestParam Integer status){
		Result result = new Result();
		
		try {
			OrderAuditPassAPI orderAuditPassAPI = new OrderAuditPassAPI(ApiConfig.getInstance());
			OrderAuditNotPassAPI orderAuditNotPassAPI = new OrderAuditNotPassAPI(ApiConfig.getInstance());
			
			BaseResponse response = new BaseResponse();
			switch (status) {
			case 1:
				response = orderAuditPassAPI.submit(auditList);
				break;
			case 2:
				response = orderAuditNotPassAPI.submit(auditList);
				break;
			default:
				break;
			}
			
			
			if(response.isSuccess()){
				return response;
			}else{
				result.setMsg(response.getMsg());
			}
			
			return result;
		} catch (Exception e) {
			logger.error("审批异常:{}", e);
			return Result.newExceptionInstance();
		}
	}
	
}
