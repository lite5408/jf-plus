package com.jf.plus.core.channel.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.web.util.HtmlUtils;

import com.alibaba.fastjson.JSON;
import com.jf.plus.common.core.Result;
import com.jf.plus.common.core.ResultCode;
import com.jf.plus.common.core.enums.ProductSource;
import com.jf.plus.common.core.enums.SaleType;
import com.jf.plus.common.utils.DateUtils;
import com.jf.plus.common.vo.AddressVo;
import com.jf.plus.common.vo.CartItem;
import com.jf.plus.common.vo.OrderItemIdsVO;
import com.jf.plus.common.vo.OrderLogisticsVO;
import com.jf.plus.common.vo.OrderTrackCommonVO;
import com.jf.plus.common.vo.OutOrderVo;
import com.jf.plus.common.vo.SkuStockVo;
import com.jf.plus.common.vo.SubmitOrderVo;
import com.jf.plus.core.mall.entity.MallSite;
import com.jf.plus.core.mall.service.MallSiteService;
import com.jf.plus.core.order.entity.Order;
import com.jf.plus.core.order.entity.OrderDistrib;
import com.jf.plus.core.order.entity.OrderFreightRule;
import com.jf.plus.core.order.service.OrderDetailService;
import com.jf.plus.core.order.service.OrderDistribService;
import com.jf.plus.core.order.service.OrderFreightRuleService;
import com.jf.plus.core.order.service.OrderService;
import com.jf.plus.core.product.entity.Product;
import com.jf.plus.core.product.service.ProductService;
import com.jf.plus.core.setting.entity.UserAddress;
import com.jf.plus.core.setting.service.UserAddressService;
import com.jf.plus.core.site.entity.SiteProduct;
import com.jf.plus.core.site.service.SiteProductService;

import cn.iutils.sys.service.UserService;

/**
 *
 * 订单渠道通用接口
 *
 * @author Tng
 *
 */

@Service
public class OrderAPIService {
	private final static Logger LOGGER = LoggerFactory.getLogger(OrderAPIService.class);

	@Autowired
	ProductService productService;

	@Autowired
	SiteProductService siteProductService;

	@Autowired
	UserService userService;

	@Autowired
	UserAddressService userAddressService;

	@Autowired
	MallSiteService mallSiteService;

	@Autowired
	OrderDetailService orderDetailService;

	@Autowired
	OrderService orderService;

	@Autowired
	OrderDistribService orderDistribService;

	@Autowired
	OrderFreightRuleService orderFreightRuleService;

	@Autowired
	ProductAPIService productAPIService;

	/**
	 * 预占库存订单
	 *
	 * @param submitOrderVo
	 * @return
	 */
	public Result submitPreConfirmOrder(SubmitOrderVo submitOrderVo) {
		Result result = new Result();

		try {
			if (StringUtils.isBlank(submitOrderVo.getOrderNo()) || submitOrderVo.getSource() == null
					|| CollectionUtils.isEmpty(submitOrderVo.getCartItemList())) {
				result.setCode(ResultCode.ARGUMENT_LACK_ERROR);
				result.setMsg("参数缺失");
				return result;
			}

			ProductSource productSource = ProductSource.getByType(Integer.valueOf(submitOrderVo.getSource()));
			Assert.notNull(productSource, "商品渠道不存在");

			// 下单前校验购物车中商品的上下架/库存情况
			if (!checkStock(submitOrderVo)) {
				// 订单中的商品已经下架或者库存不足
				result.setCode(ResultCode.RETURN_FAILURE);
				result.setMsg("订单中存在库存不足的商品，请重新下单");
				return result;
			}

			switch (productSource) {
			case SUPPLY:
				result = submitOrderBySupply(submitOrderVo);
				break;
			default:
				break;
			}

			return result;
		} catch (Exception e) {
			LOGGER.error("预占库存订单异常:{}", e);
			return Result.newExceptionInstance();
		}
	}
	
	/**
	 * 预占库存订单（带SKU）
	 *
	 * @param submitOrderVo
	 * @return
	 */
	public Result submitPreConfirmOrderSku(SubmitOrderVo submitOrderVo) {
		Result result = new Result();

		try {
			if (StringUtils.isBlank(submitOrderVo.getOrderNo()) || submitOrderVo.getSource() == null
					|| CollectionUtils.isEmpty(submitOrderVo.getCartItemList())) {
				result.setCode(ResultCode.ARGUMENT_LACK_ERROR);
				result.setMsg("参数缺失");
				return result;
			}

			ProductSource productSource = ProductSource.getByType(Integer.valueOf(submitOrderVo.getSource()));
			Assert.notNull(productSource, "商品渠道不存在");

			// 下单前校验购物车中商品的上下架/库存情况
			if (!checkStock(submitOrderVo)) {
				// 订单中的商品已经下架或者库存不足
				result.setCode(ResultCode.RETURN_FAILURE);
				result.setMsg("订单中存在库存不足的商品，请重新下单");
				return result;
			}

			switch (productSource) {
			case SUPPLY:
				result = submitOrderBySupplySku(submitOrderVo);
				break;
			default:
				break;
			}

			return result;
		} catch (Exception e) {
			LOGGER.error("预占库存订单异常:{}", e);
			return Result.newExceptionInstance();
		}
	}
	
	private Result submitOrderBySupplySku(SubmitOrderVo submitOrderVo) {
		Result result = new Result();

		MallSite mallSite = mallSiteService.get(String.valueOf(submitOrderVo.getSiteId()));
		int isDebug = mallSite.getIsDebug();

		OutOrderVo outOrderVo = new OutOrderVo();
		outOrderVo.setOutTradeNo(submitOrderVo.getOrderNo());

		if (isDebug == 1) // 调式模式
			outOrderVo.setOutTradeNo("0000");

		Result result_ = getOrderFreight(submitOrderVo);
		if (result_.isSuccess()) {
			outOrderVo.setFreight(((OutOrderVo) result_.getObj()).getFreight() == null ? 0D
					: ((OutOrderVo) result_.getObj()).getFreight());
		} else {
			outOrderVo.setFreight(0D);
		}
		
		for(CartItem cartItem : submitOrderVo.getCartItemList()){
			if(CollectionUtils.isEmpty(cartItem.getSkus())){//没有规格商品
				SiteProduct siteProduct = siteProductService.get(cartItem.getItemId());
				//砍货商品
				if(SaleType.KH.getDescription().equals(siteProduct.getSaleType())){
					int ret = productService.minusStock(siteProduct.getProductNo(), cartItem.getShopNum());
					if(ret <= 0){
						result.setCode(ResultCode.RETURN_FAILURE);
						result.setMsg("预占库存失败");
						return result;
					}
				}
			}
		}
		
		submitOrderVo.setOutOrderVo(outOrderVo);

		result.setCode(ResultCode.SUCCESS);
		result.setMsg("预占库存成功");
		result.setObj(submitOrderVo);
		return result;
	}

	// 校验订单中商品的库存和上下架情况的信息
	private boolean checkStock(SubmitOrderVo submitOrderVo) {
		List<SkuStockVo> skus = new ArrayList<SkuStockVo>();
		AddressVo addressVo = getAddressVo(userAddressService.get(submitOrderVo.getUserAddressId()),
				submitOrderVo.getSource());
		List<CartItem> cartItemList = submitOrderVo.getCartItemList();
		for (CartItem cartItem : cartItemList) {
			SkuStockVo skuStockVo = new SkuStockVo();
			skuStockVo.setSource(submitOrderVo.getSource());
			skuStockVo.setItemId(cartItem.getItemId());
			skuStockVo.setNum(cartItem.getShopNum() + "");
			skuStockVo.setSkuId(siteProductService.get(cartItem.getItemId()).getItemCode());
			skuStockVo.setProvinceId(addressVo.getProvince());
			skuStockVo.setCityId(addressVo.getCity());
			skuStockVo.setCountyId(addressVo.getCounty());
			skuStockVo.setTownId(addressVo.getTown());
			skus.add(skuStockVo);
		}
		Result checkStockResult = productAPIService.checkStock(skus);
		boolean flag = true;
		if (checkStockResult.getCode() == ResultCode.SUCCESS) {
			skus = (List<SkuStockVo>) checkStockResult.getObj();
			if (CollectionUtils.isNotEmpty(skus)) {
				for (SkuStockVo skuStockVo : skus) {
					if (!SkuStockVo.HAS_STOCK.equals(skuStockVo.getStatus())) {
						flag = false;
						break;
					}
				}
			} else {
				flag = false;
			}
		} else {
			flag = false;
		}
		return flag;
	}

	private AddressVo getAddressVo(UserAddress userAddress, Integer source) {
		AddressVo addr = new AddressVo();
		List<AddressVo> voList = JSON.parseArray(userAddress.getAddress(), AddressVo.class);

		for (AddressVo addressVo : voList) {
			if (source == addressVo.getSource().intValue()
					|| ProductSource.JD.getType() == addressVo.getSource().intValue())
				addr = addressVo;
		}

		return addr;
	}

	private Result submitOrderBySupply(SubmitOrderVo submitOrderVo) {
		Result result = new Result();

		MallSite mallSite = mallSiteService.get(String.valueOf(submitOrderVo.getSiteId()));
		int isDebug = mallSite.getIsDebug();

		OutOrderVo outOrderVo = new OutOrderVo();
		outOrderVo.setOutTradeNo(submitOrderVo.getOrderNo());

		if (isDebug == 1) // 调式模式
			outOrderVo.setOutTradeNo("0000");

		Result result_ = getOrderFreight(submitOrderVo);
		if (result_.isSuccess()) {
			outOrderVo.setFreight(((OutOrderVo) result_.getObj()).getFreight() == null ? 0D
					: ((OutOrderVo) result_.getObj()).getFreight());
		} else {
			outOrderVo.setFreight(0D);
		}

		submitOrderVo.setOutOrderVo(outOrderVo);

		result.setCode(ResultCode.SUCCESS);
		result.setMsg("预占库存成功");
		result.setObj(submitOrderVo);
		return result;
	}

	/**
	 * 确认订单
	 *
	 * @param outOrderVo
	 * @return
	 */
	public Result confirmOrder(OutOrderVo outOrderVo) {
		Result result = new Result();

		try {
			if (outOrderVo.getSource() == null || StringUtils.isBlank(outOrderVo.getOutTradeNo())) {
				result.setCode(ResultCode.ARGUMENT_LACK_ERROR);
				result.setMsg("参数缺失");
				return result;
			}

			ProductSource productSource = ProductSource.getByType(outOrderVo.getSource());
			Assert.notNull(productSource, "商品渠道不能为空");

			switch (productSource) {
			case SUPPLY:
				result = confirmOrderBySupply(outOrderVo);
				break;
			default:
				break;
			}

			return result;
		} catch (Exception e) {
			LOGGER.error("提交预占库存订单异常:{}", e);
			return Result.newExceptionInstance();
		}
	}

	private Result confirmOrderBySupply(OutOrderVo outOrderVo) {
		Result result = new Result();
		Order entity = new Order();
		entity.setOutTradeNo(outOrderVo.getOutTradeNo());
		entity.setSource(outOrderVo.getSource());
		//Order order = orderService.getEntity(entity);

//		OrderDetail detailEntity = new OrderDetail();
//		detailEntity.setOrderNo(order.getOrderNo());
//		List<OrderDetail> orderDetailList = orderDetailService.findList(detailEntity);

		//for (OrderDetail orderDetail : orderDetailList) {
			//if(SaleType.KH.getDescription().equals(orderDetail.getSaleType())){//砍货扣减库存
				//int ret = productService.minusStock(orderDetail.getProductNo(), orderDetail.getSaleNum());
			//}
			 //TODO 扣库存不验证
	//		 if (ret <= 0) {
	//			 result.setCode(ResultCode.RETURN_FAILURE);
//				 result.setMsg("扣减供应商库存失败");
//				 return result;
//			 }
//		}

		result.setCode(ResultCode.SUCCESS);
		result.setMsg("提交订单成功");
		return result;

	}

	/**
	 * 物流查询
	 *
	 * @param outOrderVo
	 * @return
	 */
	public Result orderTrack(OutOrderVo outOrderVo) {
		Result result = new Result();

		try {
			if (outOrderVo.getSource() == null || StringUtils.isBlank(outOrderVo.getOutTradeNo())) {
				result.setCode(ResultCode.ARGUMENT_LACK_ERROR);
				result.setMsg("参数缺失");
				return result;
			}

			ProductSource productSource = ProductSource.getByType(outOrderVo.getSource());
			Assert.notNull(productSource, "商品渠道不能为空");

			switch (productSource) {
			case SUPPLY:
				result = orderTrackBySupply(outOrderVo);
				break;
			default:
				break;
			}

			return result;
		} catch (Exception e) {
			LOGGER.error("物流查询异常:{}", e);
			return Result.newExceptionInstance();
		}
	}

	private Result orderTrackBySupply(OutOrderVo outOrderVo) {
		Result result = new Result();

		Order order = new Order();
		order.setOrderNo(outOrderVo.getOutTradeNo());
		order = orderService.getEntity(order);

		if (order != null) {

			OrderDistrib orderDistrib = new OrderDistrib();
			orderDistrib.setOrderId(Long.valueOf(order.getId()));
			orderDistrib = orderDistribService.getEntity(orderDistrib);

			if (orderDistrib != null) {
				List<OrderTrackCommonVO> trackList = new ArrayList<>();
				OrderTrackCommonVO orderTrackCommonVO = new OrderTrackCommonVO();
				OrderItemIdsVO itemVO = new OrderItemIdsVO();
				OrderLogisticsVO logistVO = new OrderLogisticsVO();
				List<OrderItemIdsVO> itemList = new ArrayList<>();
				List<OrderLogisticsVO> logistList = new ArrayList<>();

				logistVO.setOrderItemId(order.getOrderNo());
				logistList.add(logistVO);

				itemVO.setMsgTime(DateUtils.formatDateTime(orderDistrib.getCreateDate()));
				if (orderDistrib.getType() == 0) {
					itemVO.setContent("配送员电话：" + orderDistrib.getDeliveryMobile());
					itemVO.setOperator(orderDistrib.getDelivery());
					itemVO.setDistrName("自配");
					itemList.add(itemVO);
				} else {
					itemVO.setContent("快递单号：" + orderDistrib.getExpressNo());
					itemVO.setOperator(orderDistrib.getExpress());
					itemVO.setDistrName("快递");
					itemList.add(itemVO);
				}

				orderTrackCommonVO.setPackageId("1");
				orderTrackCommonVO.setItemList(itemList);
				orderTrackCommonVO.setLogistList(logistList);
				trackList.add(orderTrackCommonVO);

				result.setObj(trackList);
				result.setCode(ResultCode.SUCCESS);
				return result;
			}
		}

		result.setCode(ResultCode.RETURN_FAILURE);
		result.setMsg("暂无物流信息");
		return result;

	}

	/**
	 * 获取运费
	 *
	 * @param submitOrderVo
	 * @return Result
	 */
	public Result getOrderFreight(SubmitOrderVo submitOrderVo) {
		Result result = new Result();

		try {
			if (submitOrderVo.getSource() == null || CollectionUtils.isEmpty(submitOrderVo.getCartItemList())) {
				result.setCode(ResultCode.ARGUMENT_LACK_ERROR);
				result.setMsg("参数缺失");
				return result;
			}

			ProductSource productSource = ProductSource.getByType(submitOrderVo.getSource());
			Assert.notNull(productSource, "商品渠道不能为空");

			// 获取运费模板配置
			OrderFreightRule entity = new OrderFreightRule();
			entity.setSource(submitOrderVo.getSource());
			entity = orderFreightRuleService.getEntity(entity);

			if (entity == null) {
				OutOrderVo outOrderVo = new OutOrderVo();
				outOrderVo.setFreight(0D);
				result.setCode(ResultCode.SUCCESS);
				result.setObj(outOrderVo);
				return result;
			}

			// 渠道规则
			if (StringUtils.isBlank(entity.getFreightRule())) {
				result.setMsg("运费规则没有维护");
				return result;
			}

			// 按照规则配置
			if (entity.getFreightRule().equals("rule")) {
				return getFreightByRule(submitOrderVo, entity);
			}

			// 按照接口获取运费
			switch (productSource) {
			case SUPPLY:
				result = getFreightBySupplyApi(submitOrderVo);
				break;
			default:
				break;
			}

			return result;
		} catch (Exception e) {
			LOGGER.error("查询订单运费异常:{}", e);
			return Result.newExceptionInstance();
		}
	}

	private Result getFreightByRule(SubmitOrderVo submitOrderVo, OrderFreightRule entity) {
		Result result = new Result();

		List<CartItem> cartItemList = submitOrderVo.getCartItemList();
		Double totalAmount = 0D;
		for (CartItem cartItem : cartItemList) {
			SiteProduct siteProduct = siteProductService.get(cartItem.getItemId());
			Product product = productService.get(String.valueOf(siteProduct.getProductNo()));
			totalAmount += product.getSupplyPrice() * cartItem.getShopNum();
		}

		// 供应商订单按供应商配置
		if (submitOrderVo.getSource() == ProductSource.SUPPLY.getType()) {
			OrderFreightRule entitySupplyFreightRule = new OrderFreightRule();
			entitySupplyFreightRule.setSource(submitOrderVo.getSource());
			entitySupplyFreightRule.setType(4);
			entitySupplyFreightRule.setTypeVal(submitOrderVo.getSupplyId() + "");
			entitySupplyFreightRule = orderFreightRuleService.getEntity(entitySupplyFreightRule);
			if (entitySupplyFreightRule != null && totalAmount < entitySupplyFreightRule.getFreeRule()) {// 没有设置或者订单金额不足包邮金额
				OutOrderVo outOrderVo = new OutOrderVo();
				outOrderVo.setFreight(entitySupplyFreightRule.getFreight());
				result.setCode(ResultCode.SUCCESS);
				result.setObj(outOrderVo);
				return result;
			} else {
				OutOrderVo outOrderVo = new OutOrderVo();
				outOrderVo.setFreight(0D);
				result.setCode(ResultCode.SUCCESS);
				result.setObj(outOrderVo);
				return result;
			}
		}

		if (totalAmount >= entity.getFreeRule()) {// 大于等于包邮价格
			OutOrderVo outOrderVo = new OutOrderVo();
			outOrderVo.setFreight(0D);
			result.setCode(ResultCode.SUCCESS);
			result.setObj(outOrderVo);
			return result;
		} else {
			OutOrderVo outOrderVo = new OutOrderVo();
			outOrderVo.setFreight(entity.getFreight());
			result.setCode(ResultCode.SUCCESS);
			result.setObj(outOrderVo);
			return result;
		}
	}

	private Result getFreightBySupplyApi(SubmitOrderVo submitOrderVo) {
		Result result = new Result();
		result.setCode(ResultCode.SUCCESS);
		OutOrderVo outOrderVo = new OutOrderVo();
		outOrderVo.setFreight(0D);

		result.setObj(outOrderVo);
		result.setMsg("查询运费成功");
		return result;
	}

	private Result getFreightByYXApi(SubmitOrderVo submitOrderVo) {
		Result result = new Result();
		result.setCode(ResultCode.SUCCESS);
		OutOrderVo outOrderVo = new OutOrderVo();
		outOrderVo.setFreight(0D);
		result.setObj(outOrderVo);
		result.setMsg("查询运费成功");
		return result;
	}

	private Result getFreightByQXApi(SubmitOrderVo submitOrderVo) {
		Result result = new Result();
		result.setCode(ResultCode.SUCCESS);
		OutOrderVo outOrderVo = new OutOrderVo();
		outOrderVo.setFreight(0D);
		result.setObj(outOrderVo);
		result.setMsg("查询运费成功");
		return result;
	}


	/**
	 * 回退库存
	 *
	 * @param submitVo
	 * @return
	 */
	public Result backStock(SubmitOrderVo submitOrderVo) {
		Result result = new Result();

		try {
			Assert.notNull(submitOrderVo, "订单不能为空");
			Assert.notNull(submitOrderVo.getOutOrderVo(), "订单不能为空");

			ProductSource source = ProductSource.getByType(submitOrderVo.getSource());
			Assert.notNull(source, "渠道来源不能为空");

			switch (source) {
			case SUPPLY:
				result = backStockBySupply(submitOrderVo);
				break;
			default:
				break;
			}

			return result;
		} catch (Exception e) {
			LOGGER.error("退还库存异常:{}", e);
			return Result.newExceptionInstance();
		}
	}

	private Result backStockBySupply(SubmitOrderVo submitOrderVo) {
		Result result = new Result();
		 orderDetailService.backStockBySupply(submitOrderVo.getOrderNo());

		result.setCode(ResultCode.SUCCESS);
		result.setMsg("回退库存成功");
		return result;
	}

}
