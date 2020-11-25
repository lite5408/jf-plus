//package com.jf.plus.core.order.service;
//
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.HashMap;
//import java.util.HashSet;
//import java.util.List;
//import java.util.Map;
//import java.util.Set;
//
//import org.apache.commons.collections.CollectionUtils;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.BeanUtils;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Propagation;
//import org.springframework.transaction.annotation.Transactional;
//
//import com.alibaba.fastjson.JSON;
//import com.alibaba.fastjson.JSONArray;
//import com.jf.plus.common.core.Result;
//import com.jf.plus.common.core.ResultCode;
//import com.jf.plus.common.core.enums.ProductSource;
//import com.jf.plus.common.vo.AddressVo;
//import com.jf.plus.common.vo.CartItem;
//import com.jf.plus.common.vo.OutOrderVo;
//import com.jf.plus.common.vo.SubmitOrderVo;
//import com.jf.plus.core.channel.service.ProductAPIService;
//import com.jf.plus.core.mall.service.MallSiteService;
//import com.jf.plus.core.order.dao.OrderBatchDao;
//import com.jf.plus.core.order.entity.OrderBatch;
//import com.jf.plus.core.setting.entity.UserAddress;
//import com.jf.plus.core.setting.service.AppcodeService;
//import com.jf.plus.core.setting.service.UserAddressService;
//import com.jf.plus.core.site.entity.SiteProduct;
//import com.jf.plus.core.site.service.SiteProductService;
//
//import cn.iutils.common.service.CrudService;
//import cn.iutils.sys.entity.User;
//
///**
//*  Service层
//* @author Tng
//* @version 1.0
//*/
//@Service
//@Transactional(readOnly = false)
//public class OrderBatchService extends CrudService<OrderBatchDao, OrderBatch> {
//	
//	private static Logger LOGGER = LoggerFactory.getLogger(OrderBatchService.class);
//
//	@Autowired
//	private ProductAPIService productAPIService;
//
//	@Autowired
//	private UserAddressService userAddressService;
//
//	@Autowired
//	private OrderService orderService;
//
//	@Autowired
//	private SiteProductService siteProductService;
//	
//	@Autowired
//	private AppcodeService appcodeService;
//	
//	@Autowired
//	private MallSiteService mallSiteService;
//	
//	@Transactional(propagation=Propagation.NOT_SUPPORTED)
//	public Result orderBatchConfirm(User user, Integer source, String siteId, String batchNo,String remarks) {
//		Result result = Result.newInstance();
//		try{
//			String token = appcodeService.createToken(user);
//			//String remarks = "批量下单，批次号：" + batchNo + "，下单人：" + user.getName();
//			
//			Map<String,String> errProductMap = new HashMap<>();
//			Map<String, SubmitOrderVo> orderMap = new HashMap<>();
//			Map<String, OrderBatch> addressMap = new HashMap<>();
//			Integer debug = mallSiteService.get(siteId).getIsDebug();
//			
//			SubmitOrderVo submitOrderVo = null;
//			List<CartItem> cartItemList = null;
//	
//			UserAddress userAddress = new UserAddress();
//			userAddress.setUserId(Long.valueOf(user.getId()));
//			userAddress.setSiteId(Long.valueOf(siteId));
//			userAddress = userAddressService.getEntity(userAddress);
//			
//			if (userAddress == null) {
//				//无地址信息新建一个
//				userAddress = new UserAddress();
//				userAddress.setUserId(Long.valueOf(user.getId()));
//				userAddress.setSiteId(Long.valueOf(siteId));
//				userAddress.setReceiverName(user.getName());
//				userAddress.setReceiverPhone(user.getMobile());
//				userAddress.setIsDefault(1);
//				userAddress.setCreateBy(user.getId());
//				userAddressService.save(userAddress);
//			}
//			
//			UserAddress ud = new UserAddress();
//			BeanUtils.copyProperties(userAddress, ud);
//			
//			OrderBatch entity = new OrderBatch();
//			entity.setSource(source);
//			entity.setBatchNo(batchNo);
//			entity.setOperStatus("0");
//			List<OrderBatch> OrderBatchList = findList(entity);
//			
//			for (OrderBatch orderBatch : OrderBatchList) {
//				String key = orderBatch.getReceiveName() + orderBatch.getMobile() + orderBatch.getAddress();
//				if(orderMap.get(key) == null){
//					submitOrderVo = new SubmitOrderVo();
//					cartItemList = new ArrayList<>();
//					submitOrderVo.setCartItemList(cartItemList);
//					orderMap.put(key, submitOrderVo);
//					
//					addressMap.put(key, orderBatch);
//				}
//				
//				submitOrderVo = orderMap.get(key);
//				cartItemList = submitOrderVo.getCartItemList();
//	
//				SiteProduct siteProduct = new SiteProduct();
//				siteProduct.setSiteId(Long.valueOf(siteId));
//				siteProduct.setItemCode(orderBatch.getItemCode());
//				siteProduct.setSource(source);
//				siteProduct = siteProductService.getEntity(siteProduct);
//				if (siteProduct == null) {
//					//商品不存在商品池
//					errProductMap.put(key, "订单中商品编号为：" + orderBatch.getItemCode() + "的商品不存在");
//					continue;
//				}
//				submitOrderVo.setSupplyId(siteProduct.getSupplyId());
//				boolean flag = true;
//				if (cartItemList.size() != 0) {
//					for (CartItem ci : cartItemList) {
//						if(ci.getItemId().equals(siteProduct.getId())){
//							ci.setShopNum(ci.getShopNum() + orderBatch.getItemNum());
//							flag = false;
//						}
//					}
//				}
//				if (flag) {
//					CartItem cartItem = new CartItem();
//					cartItem.setItemId(siteProduct.getId());
//					cartItem.setShopNum(orderBatch.getItemNum());
//					cartItemList.add(cartItem);
//				}
//				
//			}
//			
//			Set<String> keySet = orderMap.keySet();
//			for(String key: keySet){
//				OrderBatch addressOrderBatch = addressMap.get(key);
//				
//				OrderBatch orderBatch = new OrderBatch();
//				orderBatch.setReceiveName(addressOrderBatch.getReceiveName());
//				orderBatch.setMobile(addressOrderBatch.getMobile());
//				orderBatch.setAddress(addressOrderBatch.getAddress());
//				orderBatch.setBatchNo(batchNo);
//				orderBatch.setSource(addressOrderBatch.getSource());
//				//商品不存在
//				if(errProductMap.get(key) != null){
//					orderBatch.setOperStatus("2");
//					orderBatch.setFailureReason(errProductMap.get(key));
//					updateOrderBatch(orderBatch);
//					continue;
//				}
//				
//				submitOrderVo = orderMap.get(key);
//				submitOrderVo.setOrgId(Long.valueOf(user.getOrganization().getId()));
//				submitOrderVo.setSiteId(Long.valueOf(siteId));
//				submitOrderVo.setToken(token);
//				submitOrderVo.setUserAddressId(userAddress.getId());
//				submitOrderVo.setPayWay("ye");
//				submitOrderVo.setSource(source);
//				submitOrderVo.setUserId(Long.valueOf(user.getId()));
//				submitOrderVo.setRemark(remarks);
//				
//				// 更新用户的收货信息
//				userAddress.setReceiverPhone(addressOrderBatch.getMobile());
//				userAddress.setReceiverName(addressOrderBatch.getReceiveName());
//				userAddress.setAddressDetail(addressOrderBatch.getAddress());
//				List<AddressVo> addressVoList = JSONArray.parseArray(userAddress.getAddress(), AddressVo.class);
//				for (AddressVo addressVo : addressVoList) {
//					if (source == ProductSource.YX.getType() || source == ProductSource.SUPPLY.getType()) {
//						if (addressVo.getSource() == ProductSource.JD.getType()) {
//							addressVo.setCity(addressOrderBatch.getCity());
//							addressVo.setCityName(addressOrderBatch.getCityName());
//							addressVo.setCounty(addressOrderBatch.getCounty());
//							addressVo.setCountyName(addressOrderBatch.getCountyName());
//							addressVo.setProvince(addressOrderBatch.getProvince());
//							addressVo.setProvinceName(addressOrderBatch.getProvinceName());
//							addressVo.setTown(addressOrderBatch.getTown());
//							addressVo.setTownName(addressOrderBatch.getTownName());
//						}
//					}else if(addressVo.getSource() == source) {
//						addressVo.setCity(addressOrderBatch.getCity());
//						addressVo.setCityName(addressOrderBatch.getCityName());
//						addressVo.setCounty(addressOrderBatch.getCounty());
//						addressVo.setCountyName(addressOrderBatch.getCountyName());
//						addressVo.setProvince(addressOrderBatch.getProvince());
//						addressVo.setProvinceName(addressOrderBatch.getProvinceName());
//						addressVo.setTown(addressOrderBatch.getTown());
//						addressVo.setTownName(addressOrderBatch.getTownName());
//					}
//				}
//				userAddress.setAddress(JSON.toJSONString(addressVoList));
//				userAddressService.save(userAddress);
//				
//				try{
//					// 下单先同步价格
//					Set<String> spIds = new HashSet<>();
//					if (CollectionUtils.isNotEmpty(submitOrderVo.getCartItemList())) {
//						for (CartItem cartItem : submitOrderVo.getCartItemList()) {
//							spIds.add(cartItem.getItemId());
//						}
//						productAPIService.syncPrice(submitOrderVo.getSiteId(), spIds, submitOrderVo.getToken(), 1);// 先同步价格
//					}
//					result = orderService.submitOrder(submitOrderVo);
//					OutOrderVo outOrderVo = null;
//					
//					if(result.isSuccess()){
//						outOrderVo = submitOrderVo.getOutOrderVo();
//						result = orderService.confirmOrder_notCheck(submitOrderVo,debug);
//					}
//					if (result.isSuccess()) {
//						orderBatch.setOperStatus("1");
//						orderBatch.setOutTradeNo(outOrderVo.getOutTradeNo());
//						orderBatch.setConfirmDate(new Date());
//						updateOrderBatch(orderBatch);
//					}else{
//						orderBatch.setOperStatus("2");
//						orderBatch.setFailureReason(result.getMsg());
//						updateOrderBatch(orderBatch);
//						LOGGER.error("下单失败返回结果：" + JSON.toJSONString(result));
//					}
//				}catch(Exception e){
//					orderBatch.setOperStatus("2");
//					orderBatch.setFailureReason(e.getMessage());
//					updateOrderBatch(orderBatch);
//					LOGGER.error("调用下单接口失败：" + e.getMessage());
//				}
//			}
//			userAddressService.save(ud);
//		}catch(Exception e){
//			OrderBatch orderBatch = new OrderBatch();
//			orderBatch.setBatchNo(batchNo);
//			orderBatch.setSource(source);
//			orderBatch.setOperStatus("0");
//			orderBatch.setFailureReason("系统错误，下单失败，请联系管理员");
//			updateAllOrderBatchToErr(orderBatch);
//		}
//		
//		result.setCode(ResultCode.SUCCESS);
//		return result;
//	}
//
//
//	private void updateAllOrderBatchToErr(OrderBatch orderBatch) {
//		dao.updateAllOrderBatchToErr(orderBatch);
//	}
//
//
//	private void updateOrderBatch(OrderBatch orderBatch) {
//		dao.updateOrderBatch(orderBatch);
//	}
//	
//	@Transactional(readOnly = false)
//	public int updateAreaAndStatus(OrderBatch entity) {
//		if (entity.getSource() == ProductSource.SN.getType()) {
//			return dao.updateSNAreaAndStatus(entity);
//		}
//		return dao.updateAreaAndStatus(entity);
//	}
//
//}
