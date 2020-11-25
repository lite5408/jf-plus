//package com.jf.plus.api.mobile.job.controller;
//
//import java.util.ArrayList;
//import java.util.HashSet;
//import java.util.Iterator;
//import java.util.List;
//import java.util.Set;
//import java.util.concurrent.Executor;
//import java.util.concurrent.Executors;
//
//import org.apache.commons.lang3.StringUtils;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.util.CollectionUtils;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.ResponseBody;
//
//import com.alibaba.fastjson.JSONArray;
//import com.jf.plus.common.core.Result;
//import com.jf.plus.common.core.ResultCode;
//import com.jf.plus.common.core.enums.ProductSource;
//import com.jf.plus.common.core.enums.ProductStatus;
//import com.jf.plus.core.channel.service.ProductAPIService;
//import com.jf.plus.core.product.entity.Product;
//import com.jf.plus.core.product.service.ProductService;
//import com.jf.plus.prod.channel.sn.api.MessageDelAPI;
//import com.jf.plus.prod.channel.sn.api.MessageGetAPI;
//import com.jf.plus.prod.channel.sn.api.ProductStatusAPI;
//import com.jf.plus.prod.channel.sn.api.config.ApiConfig;
//import com.suning.api.entity.govbus.BatchProdSaleStatusGetRequest.SkuIds;
//import com.suning.api.entity.govbus.BatchProdSaleStatusGetResponse;
//import com.suning.api.entity.govbus.MessageGetResponse.ResultInfo;
//
//import cn.iutils.common.utils.DateUtils;
//
//@Controller
//@RequestMapping("/api/middleware/sn")
//public class SNApiSyncManualController {
//
//	@Autowired
//	private ProductService productService;
//	
//	@Autowired
//	private ProductAPIService productAPIService;
//
//	MessageDelAPI messageDelAPI = new MessageDelAPI(ApiConfig.getInstance());
//
//	MessageGetAPI messageGetAPI = new MessageGetAPI(ApiConfig.getInstance());
//	
//	ProductStatusAPI productStatusAPI = new ProductStatusAPI(ApiConfig.getInstance());
//
//	@RequestMapping("/productShevles")
//	@ResponseBody
//	public Result productShevles(@RequestParam String pwd, @RequestParam String message) {
//		System.out.println("message:" + message);
//		Result result = new Result();
//		try {
//			if(!"A1989".equals(pwd)){
//				result.setMsg("授权失败！");
//				return result;
//			}
//			List<ResultInfo> resultInfoList = JSONArray.parseArray(message, ResultInfo.class);
//			StringBuffer xjStringBuffer = new StringBuffer();
//			
//			if (!CollectionUtils.isEmpty(resultInfoList)) {
//				Set<String> xjSKUCodeList = new HashSet<>();
//				Set<String> sjSKUCodeList = new HashSet<>();
//				for (Iterator iterator = resultInfoList.iterator(); iterator.hasNext();) {
//					ResultInfo resultInfo = (ResultInfo) iterator.next();
//					if (resultInfo != null) {
//						messageDelAPI.delMessage(resultInfo.getId());
//						String cmmdtyCode = resultInfo.getCmmdtyCode();
//						String status = resultInfo.getStatus();
//
//						if ("2".equals(status) || "4".equals(status)) {
//							xjSKUCodeList.add(cmmdtyCode);
//							xjStringBuffer.append("select outShelvesProduct(3,'"+ cmmdtyCode +"') from dual;");
//						} else if ("1".equals(status) || "0".equals(status)) {
//							sjSKUCodeList.add(cmmdtyCode);
//						}
//					}
//				}
//
//				productAPIService.syncProduct(ProductSource.SN.getType(), xjSKUCodeList, sjSKUCodeList);
//				
//				if(StringUtils.isNotBlank(xjStringBuffer)){
//					System.out.println("===下架商品===");
//					System.out.println(xjStringBuffer.toString());
//				}
//			}
//			
//			result.setCode(ResultCode.SUCCESS);
//			result.setMsg("同步成功");
//			return result;
//		} catch (Exception e) {
//			e.printStackTrace();
//			return Result.newExceptionInstance();
//		}
//	}
//	
//	@RequestMapping("/productOutShevles")
//	@ResponseBody
//	public Result productOutShevles(@RequestParam String pwd) {
//		Result result = new Result();
//		try {
//			if(!"A1989".equals(pwd)){
//				result.setMsg("授权失败！");
//				return result;
//			}
//			
//			Product entity = new Product();
//			entity.setSource(ProductSource.SN.getType());
//			entity.setOrgId(4L);//招商银行机构
//			entity.setOperStatus(ProductStatus.SHELVES.getType());
//			final List<Product> productList = productService.findList(entity);
//			if(!CollectionUtils.isEmpty(productList)){
//				final String dateTime = DateUtils.getDateTime();
//				System.out.println("[苏宁]商品同步手动同步下架,date:" + dateTime);
//				Executor executor = Executors.newSingleThreadExecutor();
//				executor.execute(new Runnable() {
//					@Override
//					public void run() {
//						StringBuffer xjStringBuffer = new StringBuffer();
//						for(Product product : productList){
//							List<SkuIds> skuIdList = new ArrayList<>();
//							SkuIds skuIds = new SkuIds();
//							skuIds.setSkuId(product.getItemCode());
//							skuIdList.add(skuIds);
//							BatchProdSaleStatusGetResponse response = productStatusAPI.getProductStatus(skuIdList);
//							if(response != null && response.getSnbody() != null 
//									&& response.getSnbody().getGetBatchProdSaleStatus() != null){
//								if("1".equals(response.getSnbody().getGetBatchProdSaleStatus().getOnShelvesList().get(0).getState())){
//									continue;
//								}
//							}
//							xjStringBuffer.append("select outShelvesProduct(3,'"+ product.getItemCode() +"') from dual;[date:" + dateTime + "]");
//						}
//						
//						if(xjStringBuffer.length() > 0){
//							System.out.println(xjStringBuffer.toString());
//						}
//						System.out.println("[苏宁]商品同步手动同步下架,date:" + dateTime);
//					}
//				});
//			}
//			
//			result.setCode(ResultCode.SUCCESS);
//			result.setMsg("同步成功");
//			return result;
//		} catch (Exception e) {
//			e.printStackTrace();
//			return Result.newExceptionInstance();
//		}
//	}
//}
