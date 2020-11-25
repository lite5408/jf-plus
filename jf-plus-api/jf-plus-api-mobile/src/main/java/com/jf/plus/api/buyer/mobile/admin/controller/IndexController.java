/**
 * 
 */
package com.jf.plus.api.buyer.mobile.admin.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
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
import com.jf.plus.common.core.enums.RoleType;
import com.jf.plus.core.mall.entity.MallSite;
import com.jf.plus.core.mall.service.MallSiteService;
import com.jf.plus.core.product.service.ProductService;

import cn.iutils.common.BaseController;
import cn.iutils.common.Page;
import cn.iutils.sys.entity.User;
import cn.iutils.sys.service.UserService;

/**
 * 买手工作台 控制器
 * @author Tng
 * @version 1.0
 */
@Controller("buyerIndexController")
public class IndexController extends BaseController{

	private static final Logger LOGGER = LoggerFactory.getLogger(IndexController.class);
	
	@Autowired
	MallSiteService mallSiteService;
	
	@Autowired
	UserService userService;
	
	@Autowired
	ProductService productService;
	
	/**
	 * 买手列表
	 * @param siteId
	 * @return
	 */
	@RequestMapping(value = "/api/buyer/list")
	@ResponseBody
	public Result buyerList(@RequestParam String siteId){
		Result result = new Result();
		try {
			MallSite mallSite = mallSiteService.get(siteId);
			
			User entity = new User();
//			entity.setOrganizationId(mallSite.getOrgId()+"");
			entity.setRole(RoleType.USER_BUYER.getType());
			List<User> userList = userService.findList(entity);
			Map<String,User> userMaps = new HashMap<>();
			if(CollectionUtils.isNotEmpty(userList)){
				for(User user :userList){
					userMaps.put(user.getUsername(), user);
				}
			}
			
			//库房发布
			entity.setRole(RoleType.USER_BUYER_KF.getType());
			List<User> kfUserList = userService.findList(entity);
			if(CollectionUtils.isNotEmpty(kfUserList)){
				User kfUser = kfUserList.get(0);
				kfUser.setName("库房发布");
				userMaps.put("kffb", kfUser);
			}
			
			
			List<User> newUserList = new ArrayList<>();
			for(User user : userMaps.values()){
				newUserList.add(user);
			}
			result.setObj(newUserList);
			result.setCode(ResultCode.SUCCESS);
			return result;
		} catch (Exception e) {
			LOGGER.error("获取买手列表异常：{}",e);
			return Result.newExceptionInstance();
		}
		
	}
	
	/**
	 * 买手店铺信息
	 * 
	 * @param buyerId
	 * @return
	 */
	@RequestMapping("/api/buyer/shopInfo")
	@ResponseBody
	public Result buyerShop(@RequestParam String buyerId) {
		Result result = new Result();

		try {
			Page<Map<String, Object>> page = new Page<>();
			page.getCondition().put("buyerId", buyerId);
			
			
			User user = userService.get(buyerId);
			String buyerName = user.getName();
			page.getCondition().put("isKf", 0);
			Result cfResult  = checkLoginPromiseKf(user.getRoleGroupIds());
			if(cfResult.isSuccess()){
				page.getCondition().put("isKf", 1);
			}
			
			Map<String, Object> prodReport = productService.buyerProdReport(page);
			if(prodReport == null || prodReport.isEmpty()){
				prodReport = new HashMap<>();
				prodReport.put("totalProdCount", 0);
				prodReport.put("dayProdCount", 0);
			}
			
			Map<String, Object> orderReport = productService.buyerOrderReport(page);
			if(orderReport == null || orderReport.isEmpty()){
				orderReport = new HashMap<>();
				orderReport.put("totalOrderCount", 0);
				orderReport.put("dayOrderCount", 0);
			}
			
			
			ResultObj resultObj = new ResultObj();
			resultObj.put("buyerId", buyerId);
			resultObj.put("buyerName", buyerName);
			resultObj.put("prodReport", prodReport);
			resultObj.put("orderReport", orderReport);
			result.setObj(resultObj);
			result.setCode(ResultCode.SUCCESS);
			return result;
		} catch (Exception e) {
			logger.error("系统异常：{}", e);
			return Result.newExceptionInstance();
		}
	}
	
	
}
