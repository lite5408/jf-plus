package com.jf.plus.core.order.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.google.common.base.Objects;
import com.jf.plus.common.core.Constants;
import com.jf.plus.common.core.Result;
import com.jf.plus.common.core.ResultCode;
import com.jf.plus.common.core.enums.AbstractType;
import com.jf.plus.common.core.enums.BoolStatus;
import com.jf.plus.common.core.enums.DealType;
import com.jf.plus.common.core.enums.OrderAuditStatus;
import com.jf.plus.common.core.enums.OrderSource;
import com.jf.plus.common.core.enums.OrderStatus;
import com.jf.plus.common.core.enums.OrderTrackState;
import com.jf.plus.common.core.enums.OrderType;
import com.jf.plus.common.core.enums.PayWay;
import com.jf.plus.common.core.enums.PayWayConf;
import com.jf.plus.common.core.enums.ProductSource;
import com.jf.plus.common.core.enums.ProductStatus;
import com.jf.plus.common.core.enums.RoleType;
import com.jf.plus.common.core.enums.SplitOrderType;
import com.jf.plus.common.core.enums.Status;
import com.jf.plus.common.exception.ServiceException;
import com.jf.plus.common.utils.CodeGen;
import com.jf.plus.common.utils.DateUtils;
import com.jf.plus.common.vo.AddressVo;
import com.jf.plus.common.vo.CartItem;
import com.jf.plus.common.vo.CartSku;
import com.jf.plus.common.vo.OutOrderVo;
import com.jf.plus.common.vo.SubmitOrderVo;
import com.jf.plus.core.account.dao.OrgAccountDao;
import com.jf.plus.core.account.dao.VoucherFlowDao;
import com.jf.plus.core.account.entity.OrgAccount;
import com.jf.plus.core.account.entity.OrgAccountRecharge;
import com.jf.plus.core.channel.service.OrderAPIService;
import com.jf.plus.core.fund.service.VoucherFundExchangeFlowService;
import com.jf.plus.core.mall.dao.MallSiteDao;
import com.jf.plus.core.mall.entity.MallSite;
import com.jf.plus.core.order.dao.OrderAuditDao;
import com.jf.plus.core.order.dao.OrderAuditDetailDao;
import com.jf.plus.core.order.dao.OrderDao;
import com.jf.plus.core.order.dao.OrderDeliveryDao;
import com.jf.plus.core.order.dao.OrderDetailDao;
import com.jf.plus.core.order.dao.OrderDetailSkuDao;
import com.jf.plus.core.order.dao.OrderRecordsDao;
import com.jf.plus.core.order.entity.Order;
import com.jf.plus.core.order.entity.OrderAudit;
import com.jf.plus.core.order.entity.OrderAuditDetail;
import com.jf.plus.core.order.entity.OrderDelivery;
import com.jf.plus.core.order.entity.OrderDetail;
import com.jf.plus.core.order.entity.OrderDetailSku;
import com.jf.plus.core.order.entity.OrderRecords;
import com.jf.plus.core.setting.dao.AuditSettingsDao;
import com.jf.plus.core.setting.dao.UserAddressDao;
import com.jf.plus.core.setting.entity.AuditSettings;
import com.jf.plus.core.setting.entity.UserAddress;
import com.jf.plus.core.site.dao.SiteProductDao;
import com.jf.plus.core.site.entity.SiteProduct;

import cn.iutils.common.DataScope;
import cn.iutils.common.Page;
import cn.iutils.common.service.CrudService;
import cn.iutils.sys.dao.IOrganizationDao;
import cn.iutils.sys.dao.IUserDao;
import cn.iutils.sys.entity.Organization;
import cn.iutils.sys.entity.User;

/**
 * 订单主表 Service层
 *
 * @author Tng
 * @version 1.0
 */
@Service
@Transactional(readOnly = true)
public class OrderService extends CrudService<OrderDao, Order> {

	@Autowired
	private OrderAPIService orderAPIService;

	@Autowired
	private SiteProductDao siteProductDao;

	@Autowired
	private MallSiteDao mallSiteDao;

	@Autowired
	private UserAddressDao userAddressDao;

	@Autowired
	private OrgAccountDao orgAccountDao;

	@Autowired
	private IOrganizationDao iOrganizationDao;

	@Autowired
	private OrderAuditDao orderAuditDao;

	@Autowired
	private OrderAuditDetailDao orderAuditDetailDao;

	@Autowired
	private AuditSettingsDao auditSettingsDao;

	@Autowired
	private OrderDetailDao orderDetailDao;

	@Autowired
	private OrderRecordsDao orderRecordsDao;

	@Autowired
	private VoucherFlowDao voucherFlowDao;

	@Autowired
	private IUserDao iUserDao;

	@Autowired
	private VoucherFundExchangeFlowService voucherFundExchangeFlowService;
	
	@Autowired
	private OrderDetailSkuDao orderDetailSkuDao;
	
	@Autowired
	private OrderDeliveryDao orderDeliveryDao;

	/**
	 * 查询我的订单列表
	 *
	 * @param page
	 * @return
	 */
	public List<Order> findOrderList(Page<Order> page) {
		page.setTotal(dao.findOrderCount(page));
		return dao.findOrderList(page);
	}

	/**
	 * 查询订单报表列表
	 *
	 * @param page
	 * @return
	 */
	public List<Order> findReportList(Page<Order> page) {
		page.setTotal(dao.findReportCount(page));
		return dao.findReportList(page);
	}

	/**
	 * 根据编号查询订单
	 *
	 * @param page
	 * @return
	 */
	public Order getDetail(String orderNo) {
		return dao.getDetail(orderNo);
	}

	
	/**
	 * 积分提交预占库存订单（带SKU）
	 *
	 * @param submitOrderVo
	 * @return
	 */
	@Transactional(readOnly = false)
	public Result submitOrderClientSku(SubmitOrderVo submitOrderVo) {
		Result result = Result.newInstance();
		MallSite mallSite = mallSiteDao.get(submitOrderVo.getSiteId().toString());
		int cashRate = mallSite.getCashRate().intValue();
		result = checkPayClientSku(submitOrderVo); // 下单校验
		if (!result.isSuccess())
			return result;
		createOrderClientSku(submitOrderVo, cashRate, submitOrderVo.getOrgId()); // 创建订单
		result = orderAPIService.submitPreConfirmOrderSku(submitOrderVo); // 调用预占库存
		if (!result.isSuccess())
			throw new ServiceException(result.getMsg());
		writeBackOrderClient(submitOrderVo, cashRate); // 回写订单信息
		writeBackOrderAudit(submitOrderVo);// 回写订单审核信息
		return result;
	}
	
	/**
	 * 用户撤销
	 *
	 * @param orderNo
	 * @return
	 */
	@Transactional(readOnly = false)
	public Result userCancelSku(String orderNo,String userId) {
		Result result = Result.newInstance();

		// 验证是否可以撤销
		Order entity = new Order();
		entity.setOrderNo(orderNo);
		Order order = dao.getEntity(entity );
		if(!(order.getTrackState() == OrderTrackState.NEW.getType())){
			result.setCode(ResultCode.RETURN_FAILURE);
			result.setMsg("撤销失败：订单已发货无法撤销");
			return result;
		}
		
		//update by 2018-06-23 增加撤销人判断
		if(order.getUserId().longValue() != Long.valueOf(userId).longValue()){
			result.setCode(ResultCode.RETURN_FAILURE);
			result.setMsg("撤销失败：只能撤销自己的订单");
			return result;
		}

		Page<OrderDetail> dPage = new Page<>(0, 1000);
		dPage.getCondition().put("orderNo", order.getOrderNo());
		order.setOrderDetailList(orderDetailDao.findPage(dPage));
		for(OrderDetail orderDetail : order.getOrderDetailList()){
			if(orderDetail.getpOperStatus() == ProductStatus.OUT_SHELVES.getType()){
				result.setCode(ResultCode.RETURN_FAILURE);
				result.setMsg("撤销失败：订单商品已下架无法撤销");
				return result;
			}
			
			if("1".equals(orderDetail.getIsDist())){
				result.setCode(ResultCode.RETURN_FAILURE);
				result.setMsg("撤销失败："+ orderDetail.getItemName() +"已分配，无法撤销");
				return result;
			}
		}
		
		
		//取消订单
		order = updateOrderAndAudit(Long.valueOf(order.getId()), OrderAuditStatus.CANCEL.getType(),"用户取消");

		// 更新机构资金账户
		refundClientSku(order, order.getPayAmount(), order.getPaySupply());
		
		// 回退库存
		SubmitOrderVo submitOrderVo = new SubmitOrderVo();
		submitOrderVo.setOrderNo(order.getOrderNo());
		submitOrderVo.setSource(order.getSource());
		OutOrderVo outOrderVo = new OutOrderVo();
		outOrderVo.setOutTradeNo(order.getOutTradeNo());
		submitOrderVo.setOutOrderVo(outOrderVo);
		orderAPIService.backStock(submitOrderVo);
		
		result.setCode(ResultCode.SUCCESS);
		result.setMsg("订单撤销成功");
		return result;
	}


	/**
	 * 订单支付
	 *
	 * @param outOrderVo
	 * @return
	 */
	@Transactional(readOnly = false)
	public Result confirmOrder(SubmitOrderVo submitOrderVo) {
		Result result = Result.newInstance();

		result.setCode(ResultCode.SUCCESS);
		result.setMsg("订单支付成功");
		return result;
	}

	/**
	 * 积分订单支付（带SKU）
	 *
	 * @param outOrderVo
	 * @return
	 */
	@Transactional(readOnly = false)
	public Result confirmOrderClientSku(SubmitOrderVo submitOrderVo) {
		Result result = Result.newInstance();
		Order order = new Order();
		order.setOrderNo(submitOrderVo.getOrderNo());
		order = dao.getEntity(order);
		if (order != null && OrderStatus.NEWCREATED.getType() != order.getOperStatus()) {
			result.setCode(ResultCode.RETURN_FAILURE);
			result.setMsg("该订单已支付，请不要重复操作！");
			return result;
		}
		MallSite mallSite = mallSiteDao.get(order.getSiteId().toString());
		submitOrderVo.setSiteId(order.getSiteId());
		submitOrderVo.setUserId(order.getUserId());
		result = checkPayClientSku(submitOrderVo); // 支付校验
		if (!result.isSuccess())
			return result;
		@SuppressWarnings("unchecked")
		Map<String, Double> priceMap = (Map<String, Double>) result.getObj();
		Double toPayBlance = priceMap.get(Constants.XSJ);
		Double supAmount = priceMap.get(Constants.GYJ);
		deductionClientSku(order, toPayBlance, supAmount); // 扣减电子券

		insertOrderRecords(order, PayWay.YE.getType());

		// 提交订单
		OrderAudit orderAudit = new OrderAudit();
		orderAudit.setOrderNo(order.getOrderNo());
		orderAudit = orderAuditDao.getEntity(orderAudit);
		orderAudit.setAuditStatus(OrderAuditStatus.TORECEIVE.getType());
		orderAuditDao.update(orderAudit);
		updateOrder(order, OrderAuditStatus.TORECEIVE.getType());

		OutOrderVo outOrderVo = new OutOrderVo();
		outOrderVo.setSource(order.getSource());
		outOrderVo.setIsDebug(mallSite.getIsDebug());
		outOrderVo.setOutTradeNo(order.getOutTradeNo());
		result = orderAPIService.confirmOrder(outOrderVo); // 调用下单接口
		if (!result.isSuccess())
			throw new ServiceException(result.getMsg());

		result.setCode(ResultCode.SUCCESS);
		result.setMsg("订单支付成功");
		return result;
	}


	/**
	 * 积分支付校验（带SKU）
	 *
	 * @param submitOrderVo
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Result checkPayClientSku(SubmitOrderVo submitOrderVo) {
		Result result = new Result();
		Map<String, Double> priceMap = new HashMap<>();
		if (StringUtils.isNotBlank(submitOrderVo.getOrderNo()))
			result = getPayBlanceForPayClient(submitOrderVo); // 获取支付总金额
		else
			result = getPayBlanceForOrderClient(submitOrderVo); // 获取下单总金额
		if (!result.isSuccess())
			return result;
		priceMap = (Map<String, Double>) result.getObj();
		if (Objects.equal(submitOrderVo.getPayWay(), PayWayConf.YEPAY.getType())) {
			//验证机构余额
			result = checkYeOrg(submitOrderVo.getOrgId(), priceMap.get(Constants.GYJ)); // 校验机构余额
			if (!result.isSuccess())
				return result;
		}
		if (StringUtils.isNotBlank(submitOrderVo.getOrderNo()))
			result.setObj(priceMap);
		result.setCode(ResultCode.SUCCESS);
		result.setMsg("订单校验成功");
		return result;
	}


	/**
	 * 获取支付总金额
	 *
	 * @param cartItems
	 * @return
	 */
	public Result getPayBlanceForOrder(SubmitOrderVo submitOrderVo) {
		Result result = new Result();
		double payAmount = 0, supAmount = 0;
		Map<String, Double> priceMap = new HashMap<>();
		SiteProduct siteProduct = null;
		for (CartItem cartItem : submitOrderVo.getCartItemList()) {
			if (cartItem.getShopNum() <= 0) {
				result.setCode(ResultCode.RETURN_FAILURE);
				result.setMsg("订单失败：商品数量不能小于0");
				return result;
			}
			siteProduct = siteProductDao.get(cartItem.getItemId());
			payAmount += siteProduct.getSalePrice() * cartItem.getShopNum(); // 站点商品销售价
			supAmount += siteProduct.getSupplyPrice() * cartItem.getShopNum(); // 站点商品供应价
		}

		result = orderAPIService.getOrderFreight(submitOrderVo); // 获取运费
		if (!result.isSuccess())
			return result;
		supAmount += ((OutOrderVo) result.getObj()).getFreight();
		payAmount += ((OutOrderVo) result.getObj()).getFreight();

		priceMap.put(Constants.XSJ, payAmount);
		priceMap.put(Constants.GYJ, supAmount);
		result.setCode(ResultCode.SUCCESS);
		result.setObj(priceMap);
		return result;
	}

	/**
	 * 积分获取支付总金额
	 *
	 * @param cartItems
	 * @return
	 */
	public Result getPayBlanceForOrderClient(SubmitOrderVo submitOrderVo) {
		Result result = new Result();
		double payAmount = 0, supAmount = 0;
		Map<String, Double> priceMap = new HashMap<>();
		SiteProduct siteProduct = null;
		for (CartItem cartItem : submitOrderVo.getCartItemList()) {
			if (cartItem.getShopNum() <= 0) {
				result.setCode(ResultCode.RETURN_FAILURE);
				result.setMsg("订单失败：商品数量不能小于0");
				return result;
			}
			siteProduct = siteProductDao.get(cartItem.getItemId());
			payAmount += siteProduct.getSalePrice() * cartItem.getShopNum(); // 站点商品销售价
			supAmount += siteProduct.getSupplyPrice() * cartItem.getShopNum(); // 站点商品供应价
		}

		result = orderAPIService.getOrderFreight(submitOrderVo); // 获取运费
		if (!result.isSuccess())
			return result;
		supAmount += ((OutOrderVo) result.getObj()).getFreight();
		payAmount += ((OutOrderVo) result.getObj()).getFreight();

		priceMap.put(Constants.XSJ, payAmount);
		priceMap.put(Constants.GYJ, supAmount);
		result.setCode(ResultCode.SUCCESS);
		result.setObj(priceMap);
		return result;
	}

	/**
	 * 积分礼包获取支付总金额
	 *
	 * @param submitOrderVo
	 * @return
	 */
	public Result getPacksPayBlanceForOrderClient(SubmitOrderVo submitOrderVo) {
		Result result = new Result();
		double supAmount = 0;
		Map<String, Double> priceMap = new HashMap<>();
		SiteProduct siteProduct = null;
		for (CartItem cartItem : submitOrderVo.getCartItemList()) {
			if (cartItem.getShopNum() <= 0) {
				result.setCode(ResultCode.RETURN_FAILURE);
				result.setMsg("订单失败：商品数量不能小于0");
				return result;
			}
			siteProduct = siteProductDao.get(cartItem.getItemId());
			supAmount += siteProduct.getSupplyPrice() * cartItem.getShopNum(); // 站点商品供应价
		}

		result = orderAPIService.getOrderFreight(submitOrderVo); // 获取运费
		if (!result.isSuccess())
			return result;
		supAmount += ((OutOrderVo) result.getObj()).getFreight();

		priceMap.put(Constants.GYJ, supAmount);
		result.setCode(ResultCode.SUCCESS);
		result.setObj(priceMap);
		return result;
	}

	/**
	 * 获取支付总金额
	 *
	 * @param orderNo
	 * @return
	 */
	public Result getPayBlanceForPay(SubmitOrderVo submitOrderVo) {
		Result result = new Result();
		double payAmount = 0, supAmount = 0;
		Map<String, Double> priceMap = new HashMap<>();
		Order order = new Order();
		order.setOrderNo(submitOrderVo.getOrderNo());
		order = dao.getEntity(order);
		if (order == null) {
			result.setCode(ResultCode.RETURN_FAILURE);
			result.setMsg("订单失败：订单无效");
			return result;
		}
		OrderDetail detail = new OrderDetail();
		detail.setOrderNo(submitOrderVo.getOrderNo());
		List<OrderDetail> orderDetailList = orderDetailDao.findList(detail);
		for (OrderDetail orderDetail : orderDetailList) {
			if (orderDetail.getSaleNum() <= 0) {
				result.setCode(ResultCode.RETURN_FAILURE);
				result.setMsg("订单失败：商品明细数量不能小于0");
				return result;
			}
			supAmount += orderDetail.getSupplyPrice() * orderDetail.getSaleNum();
		}

		supAmount += order.getFreight();
		payAmount = order.getPayAmount();

		priceMap.put(Constants.XSJ, payAmount);
		priceMap.put(Constants.GYJ, supAmount);
		result.setCode(ResultCode.SUCCESS);
		result.setObj(priceMap);
		return result;
	}

	/**
	 * 积分获取支付总金额
	 *
	 * @param orderNo
	 * @return
	 */
	public Result getPayBlanceForPayClient(SubmitOrderVo submitOrderVo) {
		Result result = new Result();
		Map<String, Double> priceMap = new HashMap<>();
		Order order = new Order();
		order.setOrderNo(submitOrderVo.getOrderNo());
		order = dao.getEntity(order);
		if (order == null) {
			result.setCode(ResultCode.RETURN_FAILURE);
			result.setMsg("订单失败：订单无效");
			return result;
		}
		priceMap.put(Constants.XSJ, order.getPayAmount());
		priceMap.put(Constants.GYJ, order.getPaySupply());
		result.setCode(ResultCode.SUCCESS);
		result.setObj(priceMap);
		return result;
	}

	/**
	 * 积分礼包获取支付总金额
	 *
	 * @param submitOrderVo
	 * @return
	 */
	public Result getPacksPayBlanceForPayClient(SubmitOrderVo submitOrderVo) {
		Result result = new Result();
		double payAmount = 0, supAmount = 0;
		Map<String, Double> priceMap = new HashMap<>();
		Order order = new Order();
		order.setOrderNo(submitOrderVo.getOrderNo());
		order = dao.getEntity(order);
		if (order == null) {
			result.setCode(ResultCode.RETURN_FAILURE);
			result.setMsg("订单失败：订单无效");
			return result;
		}
		OrderDetail detail = new OrderDetail();
		detail.setOrderNo(submitOrderVo.getOrderNo());
		List<OrderDetail> orderDetailList = orderDetailDao.findList(detail);
		for (OrderDetail orderDetail : orderDetailList) {
			if (orderDetail.getSaleNum() <= 0) {
				result.setCode(ResultCode.RETURN_FAILURE);
				result.setMsg("订单失败：商品明细数量不能小于0");
				return result;
			}
			supAmount += orderDetail.getSupplyPrice() * orderDetail.getSaleNum();
		}
		supAmount += order.getFreight();
		payAmount = order.getPayAmount();

		priceMap.put(Constants.XSJ, payAmount);
		priceMap.put(Constants.GYJ, supAmount);
		result.setCode(ResultCode.SUCCESS);
		result.setObj(priceMap);
		return result;
	}

	/**
	 * 校验机构余额
	 *
	 * @param orgId
	 * @param supAmount
	 * @return
	 */
	public Result checkYeOrg(Long orgId, double supAmount) {
		Result result = new Result();
		OrgAccount orgAccount = new OrgAccount();
		orgAccount.setOrgId(orgId);
		orgAccount = orgAccountDao.getEntity(orgAccount);
		
		if (orgAccount == null || supAmount > orgAccount.getPurchaseBlance()) {
			result.setCode(ResultCode.RETURN_FAILURE);
			result.setMsg("订单失败：机构采购余额不足");
			return result;
		}
		result.setCode(ResultCode.SUCCESS);
		return result;
	}


	/**
	 * 生成订单
	 *
	 * @param submitOrderVo
	 * @param cashRate
	 * @return
	 */
	public void createOrder(SubmitOrderVo submitOrderVo, int cashRate, Long orgId) {
		double payAmount = 0;
		SiteProduct siteProduct = null;
		AddressVo addr = null;
		List<AddressVo> voList = new ArrayList<>();
		UserAddress userAddress = userAddressDao.get(submitOrderVo.getUserAddressId());
		voList = JSON.parseArray(userAddress.getAddress(), AddressVo.class);

		int source = submitOrderVo.getSource().intValue();
		int sourceTemp = source;
		if (sourceTemp == ProductSource.SUPPLY.getType() || sourceTemp == ProductSource.YX.getType())
			sourceTemp = ProductSource.JD.getType();
		for (AddressVo addressVo : voList) {
			if (sourceTemp == addressVo.getSource().intValue()) {
				addr = addressVo;
				break;
			}
		}

		Double paySupply = 0D;
		List<OrderDetail> orderDetailList = new ArrayList<>();
		for (CartItem cartItem : submitOrderVo.getCartItemList()) {
			siteProduct = siteProductDao.get(cartItem.getItemId());
			payAmount += cartItem.getShopNum() * siteProduct.getSalePrice();
			paySupply += cartItem.getShopNum() * siteProduct.getSupplyPrice();

			// 插入订单明细信息
			OrderDetail orderDetail = new OrderDetail();
			orderDetail.setOrderSubno(CodeGen.getOrderSubNo());
			orderDetail.setItemId(Long.valueOf(siteProduct.getId()));
			orderDetail.setItemName(siteProduct.getItemName());
			orderDetail.setSupplyId(siteProduct.getSupplyId());
			orderDetail.setSupplyPrice(siteProduct.getSupplyPrice());
			orderDetail.setSalePrice(siteProduct.getSalePrice());
			orderDetail.setSalePoints(siteProduct.getSalePrice() * cashRate);
			orderDetail.setSaleNum(cartItem.getShopNum());
			orderDetail.setAmount(cartItem.getShopNum() * siteProduct.getSalePrice());
			orderDetail.setStatus(String.valueOf(Status.NORMAL.getType()));
			orderDetail.setCreateDate(new Date());
			orderDetail.setCreateBy(submitOrderVo.getUserId().toString());
			orderDetail.setUpdateBy(submitOrderVo.getUserId().toString());
			orderDetail.setUpdateDate(new Date());
			orderDetailList.add(orderDetail);
		}
		Order order = new Order();
		order.setOrderNo(CodeGen.getOrderNo());
		order.setOrderType(OrderType.GoodsOrder.getType());
		order.setOrderFrom(OrderSource.JC.getType());
		order.setRatio(cashRate);
		order.setOrgId(orgId);
		order.setSource(submitOrderVo.getSource());
		order.setSiteId(submitOrderVo.getSiteId());
		order.setSupplyId(submitOrderVo.getSupplyId());
		order.setTotalNum(submitOrderVo.getCartItemList().size());
		order.setTotalAmount(payAmount);
		order.setTotalPoints(payAmount * cashRate);// 订单积分金额
		order.setPaySupply(paySupply);
		order.setUserId(submitOrderVo.getUserId());
		order.setReceiver(userAddress.getReceiverName());
		order.setReceiverPhone(userAddress.getReceiverPhone());
		String address = addr.getProvinceName() + addr.getCityName();
		if (StringUtils.isNotBlank(addr.getCountyName())) {
			address += addr.getCountyName();
		}
		if (StringUtils.isNotBlank(addr.getTownName())) {
			address += addr.getTownName();
		}
		address += userAddress.getAddressDetail();

		if (addr.getProvince() != null) {
			order.setProvince(addr.getProvinceName() + "N" + addr.getProvince());
		}
		if (addr.getCity() != null) {
			order.setCity(addr.getCityName() + "N" + addr.getCity());
		}
		if (addr.getTown() != null) {
			order.setTown(addr.getTownName() + "N" + addr.getTown());
		}
		if (addr.getCounty() != null) {
			order.setCounty(addr.getCountyName() + "N" + addr.getCounty());
		}
		order.setAddress(address);
		order.setCashtime(new Date());
		order.setValidate(DateUtils.getOrderExpiredDate());
		order.setOperStatus(OrderStatus.NEWCREATED.getType());// 新创建
		order.setTrackState(OrderTrackState.NEW.getType());
		order.setCreateDate(new Date());
		order.setCreateBy(submitOrderVo.getUserId().toString());
		order.setUpdateBy(submitOrderVo.getUserId().toString());
		order.setUpdateDate(new Date());
		order.setRemarks(submitOrderVo.getRemark());
		order.setPid(0L);// 默认没有父订单（通过消息处理）
		order.setPtype(SplitOrderType.CHILD.getType());// 默认子订单
		dao.insert(order);
		for (OrderDetail orderDetail : orderDetailList) {
			orderDetail.setOrderId(Long.valueOf(order.getId()));
			orderDetail.setOrderNo(order.getOrderNo());
			orderDetailDao.insert(orderDetail);
		}

		// 创建审批单
		User user = iUserDao.get(order.getUserId().toString());
		Date expireDate = DateUtils.getOrderExpiredDate();
		OrderAudit oa = new OrderAudit();
		oa.setOrderNo(order.getOrderNo());
		oa.setOrgId(order.getOrgId());
		oa.setSiteId(order.getSiteId());
		oa.setTotalNum(order.getTotalNum());
		oa.setTotalAmount(order.getTotalAmount());
		oa.setTotalPoints(order.getTotalPoints());
		oa.setUserId(order.getUserId());
		oa.setUserNo(user.getNo());
		oa.setExpireDate(expireDate);
		oa.setAuditStatus(OrderAuditStatus.NEWCREATED.getType());
		oa.setAuditDate(new Date());
		oa.setTaskProcessId(null); // 流程ID为null
		oa.setProReasons(order.getRemarks());
		oa.setCreateBy(order.getCreateBy());
		oa.setCreateDate(new Date());
		oa.setUpdateBy(order.getUpdateBy());
		oa.setUpdateDate(new Date());
		orderAuditDao.insert(oa);

		OrderDetail od = new OrderDetail();
		od.setOrderId(Long.valueOf(order.getId()));
		List<OrderDetail> odList = orderDetailDao.findList(od);

		for (OrderDetail orderDetail : odList) {
			OrderAuditDetail detail = new OrderAuditDetail();
			detail.setOrderAuditId(Long.valueOf(oa.getId()));
			detail.setOrderId(Long.valueOf(order.getId()));
			detail.setOrderNo(order.getOrderNo());
			detail.setItemId(orderDetail.getItemId());
			detail.setItemName(orderDetail.getItemName());
			detail.setSupplyId(orderDetail.getSupplyId());
			detail.setSupplyPrice(orderDetail.getSupplyPrice());
			detail.setSalePrice(orderDetail.getSalePrice());
			detail.setSalePoints(orderDetail.getSalePoints());
			detail.setSaleNum(orderDetail.getSaleNum());
			detail.setAmount(orderDetail.getAmount());
			detail.setCreateBy(order.getCreateBy());
			detail.setCreateDate(new Date());
			detail.setUpdateBy(order.getUpdateBy());
			detail.setUpdateDate(new Date());
			orderAuditDetailDao.insert(detail);
		}

		submitOrderVo.setOrderNo(order.getOrderNo());
	}

	/**
	 * 积分生成订单
	 *
	 * @param submitOrderVo
	 * @param cashRate
	 * @return
	 */
	public void createOrderClient(SubmitOrderVo submitOrderVo, int cashRate, Long orgId) {
		double payAmount = 0, supAmount = 0;
		SiteProduct siteProduct = null;
		AddressVo addr = null;
		List<AddressVo> voList = new ArrayList<>();
		UserAddress userAddress = userAddressDao.get(submitOrderVo.getUserAddressId());
		voList = JSON.parseArray(userAddress.getAddress(), AddressVo.class);

		int source = submitOrderVo.getSource().intValue();
		int sourceTemp = source;
		if (sourceTemp == ProductSource.SUPPLY.getType() || sourceTemp == ProductSource.YX.getType())
			sourceTemp = ProductSource.JD.getType();
		for (AddressVo addressVo : voList) {
			if (sourceTemp == addressVo.getSource().intValue()) {
				addr = addressVo;
				break;
			}
		}

		List<OrderDetail> orderDetailList = new ArrayList<>();
		for (CartItem cartItem : submitOrderVo.getCartItemList()) {
			siteProduct = siteProductDao.get(cartItem.getItemId());
			payAmount += cartItem.getShopNum() * siteProduct.getSalePrice();
			supAmount += cartItem.getShopNum() * siteProduct.getSupplyPrice();
			// 插入订单明细信息
			OrderDetail orderDetail = new OrderDetail();
			orderDetail.setOperStatus(OrderStatus.NEWCREATED.getType());// 新创建
			orderDetail.setOrderSubno(CodeGen.getOrderSubNo());
			orderDetail.setItemId(Long.valueOf(siteProduct.getId()));
			orderDetail.setItemName(siteProduct.getItemName());
			orderDetail.setSupplyId(siteProduct.getSupplyId());
			orderDetail.setSupplyPrice(siteProduct.getSupplyPrice());
			orderDetail.setSalePrice(siteProduct.getSalePrice());
			orderDetail.setSalePoints(siteProduct.getSalePrice() * cashRate);
			orderDetail.setSaleNum(cartItem.getShopNum());
			orderDetail.setAmount(cartItem.getShopNum() * siteProduct.getSalePrice());
			orderDetail.setStatus(String.valueOf(Status.NORMAL.getType()));
			orderDetail.setCreateDate(new Date());
			orderDetail.setCreateBy(submitOrderVo.getUserId().toString());
			orderDetail.setUpdateBy(submitOrderVo.getUserId().toString());
			orderDetail.setUpdateDate(new Date());
			orderDetailList.add(orderDetail);
		}
		Order order = new Order();
		order.setOrderNo(CodeGen.getOrderNo());
		order.setOrderType(OrderType.GoodsOrder.getType());
		order.setOrderFrom(OrderSource.JF.getType());
		order.setRatio(cashRate);
		order.setOrgId(orgId);
		order.setSource(submitOrderVo.getSource());
		order.setSiteId(submitOrderVo.getSiteId());
		order.setSupplyId(submitOrderVo.getSupplyId());
		order.setTotalNum(submitOrderVo.getCartItemList().size());
		order.setTotalAmount(payAmount);
		order.setTotalPoints(payAmount * cashRate);// 订单积分金额
		order.setPaySupply(supAmount); // 总供应价(需回写运费)
		order.setUserId(submitOrderVo.getUserId());
		order.setReceiver(userAddress.getReceiverName());
		order.setReceiverPhone(userAddress.getReceiverPhone());
		String address = addr.getProvinceName() + addr.getCityName();
		if (StringUtils.isNotBlank(addr.getCountyName())) {
			address += addr.getCountyName();
		}
		if (StringUtils.isNotBlank(addr.getTownName())) {
			address += addr.getTownName();
		}
		address += userAddress.getAddressDetail();

		if (addr.getProvince() != null) {
			order.setProvince(addr.getProvinceName() + "N" + addr.getProvince());
		}
		if (addr.getCity() != null) {
			order.setCity(addr.getCityName() + "N" + addr.getCity());
		}
		if (addr.getTown() != null) {
			order.setTown(addr.getTownName() + "N" + addr.getTown());
		}
		if (addr.getCounty() != null) {
			order.setCounty(addr.getCountyName() + "N" + addr.getCounty());
		}
		order.setAddress(address);
		order.setCashtime(new Date());
		order.setValidate(DateUtils.getOrderExpiredDate());
		order.setOperStatus(OrderStatus.NEWCREATED.getType());// 新创建
		order.setTrackState(OrderTrackState.NEW.getType());
		order.setCreateDate(new Date());
		order.setCreateBy(submitOrderVo.getUserId().toString());
		order.setUpdateBy(submitOrderVo.getUserId().toString());
		order.setUpdateDate(new Date());
		order.setRemarks(submitOrderVo.getRemark());
		order.setPid(0L);// 默认没有父订单（通过消息处理）
		order.setPtype(SplitOrderType.CHILD.getType());// 默认子订单
		dao.insert(order);
		for (OrderDetail orderDetail : orderDetailList) {
			orderDetail.setOrderId(Long.valueOf(order.getId()));
			orderDetail.setOrderNo(order.getOrderNo());
			orderDetailDao.insert(orderDetail);
		}

		// 创建审批单
		User user = iUserDao.get(order.getUserId().toString());
		Date expireDate = DateUtils.getOrderExpiredDate();
		OrderAudit oa = new OrderAudit();
		oa.setOrderNo(order.getOrderNo());
		oa.setOrgId(order.getOrgId());
		oa.setSiteId(order.getSiteId());
		oa.setTotalNum(order.getTotalNum());
		oa.setTotalAmount(order.getTotalAmount());
		oa.setTotalPoints(order.getTotalPoints());
		oa.setUserId(order.getUserId());
		oa.setUserNo(user.getNo());
		oa.setExpireDate(expireDate);
		oa.setAuditStatus(OrderAuditStatus.NEWCREATED.getType());
		oa.setAuditDate(new Date());
		oa.setTaskProcessId(null); // 流程ID为null
		oa.setProReasons(order.getRemarks());
		oa.setCreateBy(order.getCreateBy());
		oa.setCreateDate(new Date());
		oa.setUpdateBy(order.getUpdateBy());
		oa.setUpdateDate(new Date());
		orderAuditDao.insert(oa);

		OrderDetail od = new OrderDetail();
		od.setOrderId(Long.valueOf(order.getId()));
		List<OrderDetail> odList = orderDetailDao.findList(od);

		for (OrderDetail orderDetail : odList) {
			OrderAuditDetail detail = new OrderAuditDetail();
			detail.setOrderAuditId(Long.valueOf(oa.getId()));
			detail.setOrderId(Long.valueOf(order.getId()));
			detail.setOrderNo(order.getOrderNo());
			detail.setItemId(orderDetail.getItemId());
			detail.setItemName(orderDetail.getItemName());
			detail.setSupplyId(orderDetail.getSupplyId());
			detail.setSupplyPrice(orderDetail.getSupplyPrice());
			detail.setSalePrice(orderDetail.getSalePrice());
			detail.setSalePoints(orderDetail.getSalePoints());
			detail.setSaleNum(orderDetail.getSaleNum());
			detail.setAmount(orderDetail.getAmount());
			detail.setCreateBy(order.getCreateBy());
			detail.setCreateDate(new Date());
			detail.setUpdateBy(order.getUpdateBy());
			detail.setUpdateDate(new Date());
			orderAuditDetailDao.insert(detail);
		}

		submitOrderVo.setOrderNo(order.getOrderNo());
	}
	
	/**
	 * 积分生成订单（带SKU）
	 *
	 * @param submitOrderVo
	 * @param cashRate
	 * @return
	 */
	public void createOrderClientSku(SubmitOrderVo submitOrderVo, int cashRate, Long orgId) {
		double payAmount = 0, supAmount = 0;
		SiteProduct siteProduct = null;
		AddressVo addr = null;
		List<AddressVo> voList = new ArrayList<>();
		UserAddress userAddress = userAddressDao.get(submitOrderVo.getUserAddressId());
		voList = JSON.parseArray(userAddress.getAddress(), AddressVo.class);

		int source = submitOrderVo.getSource().intValue();
		for (AddressVo addressVo : voList) {
			if (source == addressVo.getSource().intValue()) {
				addr = addressVo;
				break;
			}
		}

		int totalNum = 0;
		
		List<OrderDetail> orderDetailList = new ArrayList<>();
		for (CartItem cartItem : submitOrderVo.getCartItemList()) {
			if(cartItem == null || cartItem.getShopNum() <= 0)
				continue;
			
			siteProduct = siteProductDao.get(cartItem.getItemId());
			payAmount += cartItem.getShopNum() * siteProduct.getSalePrice();
			supAmount += cartItem.getShopNum() * siteProduct.getSupplyPrice();
			
			
			// 插入订单明细信息
			OrderDetail orderDetail = new OrderDetail();
			orderDetail.setOrderSubno(CodeGen.getOrderSubNo());
			orderDetail.setItemId(Long.valueOf(siteProduct.getId()));
			orderDetail.setItemName(siteProduct.getItemName());
			orderDetail.setSupplyId(siteProduct.getSupplyId());
			orderDetail.setSupplyPrice(siteProduct.getSupplyPrice());
			orderDetail.setSalePrice(siteProduct.getSalePrice());
			orderDetail.setSalePoints(siteProduct.getSalePrice() * cashRate);
			orderDetail.setSaleNum(cartItem.getShopNum());
			orderDetail.setAmount(cartItem.getShopNum() * siteProduct.getSalePrice());
			orderDetail.setOperStatus(OrderAuditStatus.NEWCREATED.getType());
			orderDetail.setTrackState(OrderTrackState.NEW.getType());
			orderDetail.setStatus(String.valueOf(Status.NORMAL.getType()));
			orderDetail.setCreateDate(new Date());
			orderDetail.setCreateBy(submitOrderVo.getUserId().toString());
			orderDetail.setUpdateBy(submitOrderVo.getUserId().toString());
			orderDetail.setUpdateDate(new Date());
			orderDetailList.add(orderDetail);
			
			//订单SKU
			if(CollectionUtils.isNotEmpty(cartItem.getSkus())){
				int detailSaleNum = 0;
				List<OrderDetailSku> orderDetailSkus = new ArrayList<>();
				for(CartSku cartSku : cartItem.getSkus()){
					if(cartSku == null || cartSku.getShopNum() <= 0)
						continue;
					
					OrderDetailSku orderDetailSku = new OrderDetailSku();
					try{
						totalNum += cartSku.getShopNum();
						
						BeanUtils.copyProperties(orderDetailSku, orderDetail);
						orderDetailSku.setId(null);//新增
						orderDetailSku.setSalePrice(orderDetail.getSalePrice() * cartSku.getShopNum());
						orderDetailSku.setSalePoints(orderDetailSku.getSalePrice() * cashRate);
						orderDetailSku.setSaleNum(cartSku.getShopNum());
						orderDetailSku.setFreezeNum(cartSku.getShopNum());
						orderDetailSku.setSkuId(cartSku.getSkuId());
						orderDetailSku.setSpecCode(cartSku.getSpecCode());//SKU
						orderDetailSku.setSpecColor(cartSku.getSpecColor());//颜色
						orderDetailSku.setSpecColorText(cartSku.getSpecColorText());
						orderDetailSku.setSpecSize(cartSku.getSpecSize());//尺码 
						orderDetailSku.setSpecSizeText(cartSku.getSpecSizeText());
						
						orderDetailSkus.add(orderDetailSku);
						
						detailSaleNum += cartSku.getShopNum();
					}catch(Exception e){
						logger.error("提交订单-生成订单行异常：{}",e);
					}
				}
				orderDetail.setSaleNum(detailSaleNum);
				orderDetail.setFreezeNum(detailSaleNum);
				orderDetail.setOrderDetailSkus(orderDetailSkus);
			}else{
				totalNum += cartItem.getShopNum();
			}
		}
		Order order = new Order();
		order.setOrderNo(CodeGen.getOrderNo());
		order.setOrderType(OrderType.GoodsOrder.getType());
		order.setOrderFrom(OrderSource.JF.getType());
		order.setRatio(cashRate);
		order.setOrgId(orgId);
		order.setSource(submitOrderVo.getSource());
		order.setSiteId(submitOrderVo.getSiteId());
		order.setSupplyId(submitOrderVo.getSupplyId());
		order.setTotalNum(totalNum);
		order.setTotalAmount(payAmount);
		order.setTotalPoints(payAmount * cashRate);// 订单积分金额
		order.setPaySupply(supAmount); // 总供应价(需回写运费)
		order.setUserId(submitOrderVo.getUserId());
		order.setReceiver(userAddress.getReceiverName());
		order.setReceiverPhone(userAddress.getReceiverPhone());
		order.setRemarks(submitOrderVo.getRemark());
		String address = addr.getProvinceName() + addr.getCityName();
		if (StringUtils.isNotBlank(addr.getCountyName())) {
			address += addr.getCountyName();
		}
		if (StringUtils.isNotBlank(addr.getTownName())) {
			address += addr.getTownName();
		}
		address += userAddress.getAddressDetail();

		if (addr.getProvince() != null) {
			order.setProvince(addr.getProvinceName() + "N" + addr.getProvince());
		}
		if (addr.getCity() != null) {
			order.setCity(addr.getCityName() + "N" + addr.getCity());
		}
		if (addr.getTown() != null) {
			order.setTown(addr.getTownName() + "N" + addr.getTown());
		}
		if (addr.getCounty() != null) {
			order.setCounty(addr.getCountyName() + "N" + addr.getCounty());
		}
		order.setAddress(address);
		order.setCashtime(new Date());
		order.setValidate(DateUtils.getOrderExpiredDate());
		order.setOperStatus(OrderStatus.NEWCREATED.getType());// 新创建
		order.setCreateDate(new Date());
		order.setCreateBy(submitOrderVo.getUserId().toString());
		order.setUpdateBy(submitOrderVo.getUserId().toString());
		order.setUpdateDate(new Date());
		order.setRemarks(submitOrderVo.getRemark());
		order.setPid(0L);// 默认没有父订单（通过消息处理）
		order.setPtype(SplitOrderType.CHILD.getType());// 默认子订单
		dao.insert(order);
		for (OrderDetail orderDetail : orderDetailList) {
			orderDetail.setOrderId(Long.valueOf(order.getId()));
			orderDetail.setOrderNo(order.getOrderNo());
			orderDetailDao.insert(orderDetail);
			
			//SKU
			if(CollectionUtils.isNotEmpty(orderDetail.getOrderDetailSkus())){
				for(OrderDetailSku orderDetailSku:orderDetail.getOrderDetailSkus()){
					orderDetailSku.setOrderId(orderDetail.getOrderId());
					orderDetailSku.setOrderNo(orderDetail.getOrderNo());
					orderDetailSku.setOrderDetailId(Long.valueOf(orderDetail.getId()));
					orderDetailSkuDao.insert(orderDetailSku);
				}
			}
		}

		// 创建审批单
		User user = iUserDao.get(order.getUserId().toString());
		Date expireDate = DateUtils.getOrderExpiredDate();
		OrderAudit oa = new OrderAudit();
		oa.setOrderNo(order.getOrderNo());
		oa.setOrgId(order.getOrgId());
		oa.setSiteId(order.getSiteId());
		oa.setTotalNum(order.getTotalNum());
		oa.setTotalAmount(order.getTotalAmount());
		oa.setTotalPoints(order.getTotalPoints());
		oa.setUserId(order.getUserId());
		oa.setUserNo(user.getNo());
		oa.setExpireDate(expireDate);
		oa.setAuditStatus(OrderAuditStatus.NEWCREATED.getType());
		oa.setAuditDate(new Date());
		oa.setTaskProcessId(null); // 流程ID为null
		oa.setProReasons(order.getRemarks());
		oa.setCreateBy(order.getCreateBy());
		oa.setCreateDate(new Date());
		oa.setUpdateBy(order.getUpdateBy());
		oa.setUpdateDate(new Date());
		orderAuditDao.insert(oa);

		OrderDetail od = new OrderDetail();
		od.setOrderId(Long.valueOf(order.getId()));
		List<OrderDetail> odList = orderDetailDao.findList(od);

		for (OrderDetail orderDetail : odList) {
			OrderAuditDetail detail = new OrderAuditDetail();
			detail.setOrderAuditId(Long.valueOf(oa.getId()));
			detail.setOrderId(Long.valueOf(order.getId()));
			detail.setOrderNo(order.getOrderNo());
			detail.setItemId(orderDetail.getItemId());
			detail.setItemName(orderDetail.getItemName());
			detail.setSupplyId(orderDetail.getSupplyId());
			detail.setSupplyPrice(orderDetail.getSupplyPrice());
			detail.setSalePrice(orderDetail.getSalePrice());
			detail.setSalePoints(orderDetail.getSalePoints());
			detail.setSaleNum(orderDetail.getSaleNum());
			detail.setAmount(orderDetail.getAmount());
			detail.setCreateBy(order.getCreateBy());
			detail.setCreateDate(new Date());
			detail.setUpdateBy(order.getUpdateBy());
			detail.setUpdateDate(new Date());
			orderAuditDetailDao.insert(detail);
		}

		submitOrderVo.setOrderNo(order.getOrderNo());
	}

	/**
	 * 积分礼包生成订单
	 *
	 * @param submitOrderVo
	 * @param cashRate
	 * @return
	 */
	public void createPacksOrderClient(SubmitOrderVo submitOrderVo, Double salePrice, int cashRate, Long orgId) {
		SiteProduct siteProduct = null;
		AddressVo addr = null;
		List<AddressVo> voList = new ArrayList<>();
		UserAddress userAddress = userAddressDao.get(submitOrderVo.getUserAddressId());
		voList = JSON.parseArray(userAddress.getAddress(), AddressVo.class);

		int source = submitOrderVo.getSource().intValue();
		int sourceTemp = source;
		if (sourceTemp == ProductSource.SUPPLY.getType() || sourceTemp == ProductSource.YX.getType())
			sourceTemp = ProductSource.JD.getType();
		for (AddressVo addressVo : voList) {
			if (sourceTemp == addressVo.getSource().intValue()) {
				addr = addressVo;
				break;
			}
		}

		List<OrderDetail> orderDetailList = new ArrayList<>();
		for (CartItem cartItem : submitOrderVo.getCartItemList()) {
			siteProduct = siteProductDao.get(cartItem.getItemId());
			// 插入订单明细信息
			OrderDetail orderDetail = new OrderDetail();
			orderDetail.setOrderSubno(CodeGen.getOrderSubNo());
			orderDetail.setItemId(Long.valueOf(siteProduct.getId()));
			orderDetail.setItemName(siteProduct.getItemName());
			orderDetail.setSupplyId(siteProduct.getSupplyId());
			orderDetail.setSupplyPrice(siteProduct.getSupplyPrice());
			orderDetail.setSalePrice(siteProduct.getSalePrice());
			orderDetail.setSalePoints(siteProduct.getSalePrice() * cashRate);
			orderDetail.setSaleNum(cartItem.getShopNum());
			orderDetail.setAmount(cartItem.getShopNum() * siteProduct.getSalePrice());
			orderDetail.setStatus(String.valueOf(Status.NORMAL.getType()));
			orderDetail.setCreateDate(new Date());
			orderDetail.setCreateBy(submitOrderVo.getUserId().toString());
			orderDetail.setUpdateBy(submitOrderVo.getUserId().toString());
			orderDetail.setUpdateDate(new Date());
			orderDetailList.add(orderDetail);
		}
		Order order = new Order();
		order.setOrderNo(CodeGen.getOrderNo());
		order.setOrderType(OrderType.PackOrder.getType());
		order.setOrderFrom(OrderSource.JF.getType());
		order.setRatio(cashRate);
		order.setOrgId(orgId);
		order.setPacksAccId(submitOrderVo.getPacksAccId());
		order.setSource(submitOrderVo.getSource());
		order.setSiteId(submitOrderVo.getSiteId());
		order.setSupplyId(submitOrderVo.getSupplyId());
		order.setTotalNum(submitOrderVo.getCartItemList().size());
		order.setTotalAmount(salePrice);
		order.setTotalPoints(salePrice.doubleValue() * cashRate);// 订单积分金额
		order.setUserId(submitOrderVo.getUserId());
		order.setReceiver(userAddress.getReceiverName());
		order.setReceiverPhone(userAddress.getReceiverPhone());
		String address = addr.getProvinceName() + addr.getCityName();
		if (StringUtils.isNotBlank(addr.getCountyName())) {
			address += addr.getCountyName();
		}
		if (StringUtils.isNotBlank(addr.getTownName())) {
			address += addr.getTownName();
		}
		address += userAddress.getAddressDetail();

		if (addr.getProvince() != null) {
			order.setProvince(addr.getProvinceName() + "N" + addr.getProvince());
		}
		if (addr.getCity() != null) {
			order.setCity(addr.getCityName() + "N" + addr.getCity());
		}
		if (addr.getTown() != null) {
			order.setTown(addr.getTownName() + "N" + addr.getTown());
		}
		if (addr.getCounty() != null) {
			order.setCounty(addr.getCountyName() + "N" + addr.getCounty());
		}
		order.setAddress(address);
		order.setCashtime(new Date());
		order.setValidate(DateUtils.getOrderExpiredDate());
		order.setOperStatus(OrderStatus.NEWCREATED.getType());// 新创建
		order.setTrackState(OrderTrackState.NEW.getType());
		order.setCreateDate(new Date());
		order.setCreateBy(submitOrderVo.getUserId().toString());
		order.setUpdateBy(submitOrderVo.getUserId().toString());
		order.setUpdateDate(new Date());
		order.setRemarks(submitOrderVo.getRemark());
		order.setPid(0L);// 默认没有父订单（通过消息处理）
		order.setPtype(SplitOrderType.CHILD.getType());// 默认子订单
		dao.insert(order);
		for (OrderDetail orderDetail : orderDetailList) {
			orderDetail.setOrderId(Long.valueOf(order.getId()));
			orderDetail.setOrderNo(order.getOrderNo());
			orderDetailDao.insert(orderDetail);
		}

		// 创建审批单
		User user = iUserDao.get(order.getUserId().toString());
		Date expireDate = DateUtils.getOrderExpiredDate();
		OrderAudit oa = new OrderAudit();
		oa.setOrderNo(order.getOrderNo());
		oa.setOrgId(order.getOrgId());
		oa.setSiteId(order.getSiteId());
		oa.setTotalNum(order.getTotalNum());
		oa.setTotalAmount(order.getTotalAmount());
		oa.setTotalPoints(order.getTotalPoints());
		oa.setUserId(order.getUserId());
		oa.setUserNo(user.getNo());
		oa.setExpireDate(expireDate);
		oa.setAuditStatus(OrderAuditStatus.NEWCREATED.getType());
		oa.setAuditDate(new Date());
		oa.setTaskProcessId(null); // 流程ID为null
		oa.setProReasons(order.getRemarks());
		oa.setCreateBy(order.getCreateBy());
		oa.setCreateDate(new Date());
		oa.setUpdateBy(order.getUpdateBy());
		oa.setUpdateDate(new Date());
		orderAuditDao.insert(oa);

		OrderDetail od = new OrderDetail();
		od.setOrderId(Long.valueOf(order.getId()));
		List<OrderDetail> odList = orderDetailDao.findList(od);

		for (OrderDetail orderDetail : odList) {
			OrderAuditDetail detail = new OrderAuditDetail();
			detail.setOrderAuditId(Long.valueOf(oa.getId()));
			detail.setOrderId(Long.valueOf(order.getId()));
			detail.setOrderNo(order.getOrderNo());
			detail.setItemId(orderDetail.getItemId());
			detail.setItemName(orderDetail.getItemName());
			detail.setSupplyId(orderDetail.getSupplyId());
			detail.setSupplyPrice(orderDetail.getSupplyPrice());
			detail.setSalePrice(orderDetail.getSalePrice());
			detail.setSalePoints(orderDetail.getSalePoints());
			detail.setSaleNum(orderDetail.getSaleNum());
			detail.setAmount(orderDetail.getAmount());
			detail.setCreateBy(order.getCreateBy());
			detail.setCreateDate(new Date());
			detail.setUpdateBy(order.getUpdateBy());
			detail.setUpdateDate(new Date());
			orderAuditDetailDao.insert(detail);
		}

		submitOrderVo.setOrderNo(order.getOrderNo());
	}

	/**
	 * 回写订单信息
	 *
	 * @param submitOrderVo
	 */
	public void writeBackOrder(SubmitOrderVo submitOrderVo, int cashRate) {
		Order order = new Order();
		order.setOrderNo(submitOrderVo.getOrderNo());
		order = dao.getEntity(order);
		order.setOutTradeNo(submitOrderVo.getOutOrderVo().getOutTradeNo());
		order.setFreight(submitOrderVo.getOutOrderVo().getFreight());
		order.setPayAmount(order.getTotalAmount() + order.getFreight());
		order.setPaySupply(order.getPaySupply() + order.getFreight());
		order.setPayPoints(order.getTotalPoints() + order.getFreight() * cashRate);
		dao.update(order);
	}

	/**
	 * 积分回写订单信息
	 *
	 * @param submitOrderVo
	 */
	public void writeBackOrderClient(SubmitOrderVo submitOrderVo, int cashRate) {
		Order order = new Order();
		order.setOrderNo(submitOrderVo.getOrderNo());
		order = dao.getEntity(order);
		order.setOutTradeNo(submitOrderVo.getOutOrderVo().getOutTradeNo());
		order.setFreight(submitOrderVo.getOutOrderVo().getFreight());
		order.setPayAmount(order.getTotalAmount() + order.getFreight());
		order.setPaySupply(order.getPaySupply() + order.getFreight());
		order.setPayPoints(order.getTotalPoints() + order.getFreight() * cashRate);
		dao.update(order);
	}

	/**
	 * 积分礼包回写订单信息
	 *
	 * @param submitOrderVo
	 */
	public void writeBackPacksOrderClient(SubmitOrderVo submitOrderVo) {
		Order order = new Order();
		order.setOrderNo(submitOrderVo.getOrderNo());
		order = dao.getEntity(order);
		order.setOutTradeNo(submitOrderVo.getOutOrderVo().getOutTradeNo());
		order.setFreight(submitOrderVo.getOutOrderVo().getFreight());
		order.setPayAmount(order.getTotalAmount());
		order.setPayPoints(order.getTotalPoints());
		dao.update(order);
	}

	/**
	 * 回写订单审核信息
	 *
	 * @param submitOrderVo
	 */
	public void writeBackOrderAudit(SubmitOrderVo submitOrderVo) {
		Order order = new Order();
		order.setOrderNo(submitOrderVo.getOrderNo());
		order = dao.getEntity(order);
		OrderAudit orderAudit = new OrderAudit();
		orderAudit.setOrderNo(submitOrderVo.getOrderNo());
		orderAudit = orderAuditDao.getEntity(orderAudit);
		orderAudit.setFreight(order.getFreight());
		orderAudit.setPayAmount(order.getPayAmount());
		orderAudit.setPayPoints(order.getPayPoints());
		orderAuditDao.update(orderAudit);
	}


	/**
	 * 积分扣减电子券（带SKU）
	 *
	 * @param submitOrderVo
	 * @return
	 */
	public void deductionClientSku(Order order, Double toPayBlance, Double supAmount) {
		OrgAccount orgAccount = updateOrgAccount(order.getOrgId(), supAmount);
		
		// 记录机构账户流水
		OrgAccountRecharge outOrgAccountRecharge = new OrgAccountRecharge();
		outOrgAccountRecharge.setAbstractType(AbstractType.ORDER.getType());
		outOrgAccountRecharge.setAccountId(Long.valueOf(orgAccount.getId()));// 账户
		outOrgAccountRecharge.setAccountType("purchase");
		outOrgAccountRecharge.setDealType(DealType.EXPENDITURE.getType());
		outOrgAccountRecharge.setAmount(-supAmount);
		outOrgAccountRecharge.setAttachments("");
		outOrgAccountRecharge.setOperTime(new Date());
		outOrgAccountRecharge.setTargetId(Long.valueOf(order.getId()));// 订单ID
		voucherFundExchangeFlowService.orderSubmitFlow(outOrgAccountRecharge);
	}
	
	/**
	 * 回退金额
	 *
	 * @param submitOrderVo
	 * @return
	 */
	public void refundClientSku(Order order, Double toPayBlance, Double supAmount) {
		OrgAccount orgAccount = updateOrgAccount(order.getOrgId(), -supAmount);
		
		// 记录机构账户流水
		OrgAccountRecharge outOrgAccountRecharge = new OrgAccountRecharge();
		outOrgAccountRecharge.setAbstractType(AbstractType.ORDER_CANCEL.getType());
		outOrgAccountRecharge.setAccountId(Long.valueOf(orgAccount.getId()));// 账户
		outOrgAccountRecharge.setAccountType("purchase");
		outOrgAccountRecharge.setDealType(DealType.INCOME.getType());
		outOrgAccountRecharge.setAmount(supAmount);
		outOrgAccountRecharge.setAttachments("");
		outOrgAccountRecharge.setOperTime(new Date());
		outOrgAccountRecharge.setTargetId(Long.valueOf(order.getId()));// 订单ID
		voucherFundExchangeFlowService.orderSubmitFlow(outOrgAccountRecharge);
	}

	/**
	 * 更新机构资金账户
	 *
	 * @param siteId
	 * @param balAmount
	 */
	private OrgAccount updateOrgAccount(Long orgId, Double supAmount) {
		OrgAccount orgAccount = new OrgAccount();
		orgAccount.setOrgId(orgId);
		orgAccount = orgAccountDao.getEntity(orgAccount);
		orgAccount.setPurchaseBlance(orgAccount.getPurchaseBlance() - supAmount);
		orgAccountDao.update(orgAccount);
		return orgAccount;
	}

	/**
	 * 更新订单
	 *
	 * @param order
	 */
	private void updateOrder(Order order, Integer status) {
		order.setOperStatus(status);
		if(status == OrderAuditStatus.TORECEIVE.getType()){
			order.setTrackState(OrderTrackState.NEW.getType());
		}
		dao.update(order);
		
		OrderDetail orderDetail = new OrderDetail();
		if(status == OrderAuditStatus.CANCEL.getType()){
			//如果是取消，就把待收货的订单明细取消掉
			orderDetail.setOperStatus(OrderAuditStatus.TORECEIVE.getType());
		}
		orderDetail.setOrderId(Long.valueOf(order.getId()));
		orderDetail.setTargetOperStatus(status);
		
		orderDetailDao.updateByEntity(orderDetail);
	}

	/**
	 * 订单支付流水
	 *
	 * @param order
	 * @param payMode
	 */
	private void insertOrderRecords(Order order, int payMode) {
		OrderRecords orderRecords = new OrderRecords();
		orderRecords.setOrderId(Long.valueOf(order.getId()));
		orderRecords.setOrderNo(order.getId());
		orderRecords.setPayMode(payMode);
		orderRecords.setPayAmount(order.getPayAmount());
		orderRecords.setTradeNo(order.getOrderNo());
		orderRecords.setTradeTime(DateUtils.formatDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
		orderRecordsDao.insert(orderRecords);
	}

	/**
	 * 机构审核配置
	 *
	 * @param orgId
	 *            机构ID
	 * @return
	 */
	public AuditSettings checkIsAudit(Long orgId) {
		AuditSettings auditSettings = new AuditSettings();
		auditSettings.setOrgId(orgId);
		auditSettings.setIsAudit(BoolStatus.YES.getType());
		return auditSettingsDao.getEntity(auditSettings);
	}

	/**
	 * 获取机构管理员
	 *
	 * @param orgId
	 * @return
	 */
	private User getAdminByOrgId(Long orgId) {
		User user = new User();
		user.setOrganizationId(orgId.toString());
		user.setRole(RoleType.AUDIT_ORDER.getType());
		return iUserDao.getEntity(user);
	}

	private boolean checkAuditDate(AuditSettings auditSettings) {
		if (auditSettings.getAuditEndTime() != null && auditSettings.getAuditStartTime() != null) {
			logger.info("checkAuditDate start");
			Date now = new Date();
			if (auditSettings.getAuditStartTime().before(now) && auditSettings.getAuditEndTime().after(now))
				return true;
			else
				return false;
		}
		logger.info("checkAuditDate end");
		return true;
	}

	/**
	 * 用户撤销
	 *
	 * @param processInstanceId
	 * @return
	 */
	@Transactional(readOnly = false)
	public Result userCancel(String orderNo, String processInstanceId) {
		Result result = Result.newInstance();

		// 验证是否可以撤销
		OrderAudit oa = new OrderAudit();
		oa.setOrderNo(orderNo);
		oa.setAuditStatus(OrderAuditStatus.LEVEL_1.getType());
		if (orderAuditDao.getEntity(oa) == null) {
			result.setCode(ResultCode.RETURN_FAILURE);
			result.setMsg("撤销失败：订单无法撤销");
			return result;
		}

		result.setCode(ResultCode.SUCCESS);
		result.setMsg("订单撤销成功");
		return result;
	}

	/**
	 * 订单审批通过
	 *
	 * @param auditId
	 * @param processInstanceId
	 * @return
	 */
	@Transactional(readOnly = false)
	public Result orderAuditPass(Long userId, List<OrderAudit> auditList) {
		Result result = Result.newInstance();
		AuditSettings auditSettings = null;
		Long parentOrgId = 0L;
		result.setCode(ResultCode.SUCCESS);
		result.setMsg("订单审批成功");
		return result;
	}

	/**
	 * 订单审批不通过
	 *
	 * @param auditId
	 * @param processInstanceId
	 * @return
	 */
	@Transactional(readOnly = false)
	public Result orderAuditNotPass(List<OrderAudit> auditList) {
		Result result = Result.newInstance();
		result.setCode(ResultCode.SUCCESS);
		result.setMsg("订单审批成功");
		return result;
	}

	/**
	 * 完成订单
	 *
	 * @param orderId
	 * @param processInstanceId
	 * @return
	 */
	@Transactional(readOnly = false)
	public Result finishOrder(Long orderId, String processInstanceId) {
		Result result = Result.newInstance();
		Order order = updateOrderAndAudit(orderId, OrderAuditStatus.TORECEIVE.getType(), null);
		MallSite site = mallSiteDao.get(order.getSiteId().toString());
		OutOrderVo outOrderVo = new OutOrderVo();
		outOrderVo.setSource(order.getSource());
		outOrderVo.setIsDebug(site.getIsDebug());
		outOrderVo.setOutTradeNo(order.getOutTradeNo());
		result = orderAPIService.confirmOrder(outOrderVo); // 调用下单接口
		if (!result.isSuccess())
			throw new ServiceException(result.getMsg());
		result.setCode(ResultCode.SUCCESS);
		result.setMsg("订单已完成");
		return result;
	}

	/**
	 * 创建审批单
	 *
	 * @param order
	 * @param expireDate
	 *            审核有效期
	 * @param taskProcessId
	 *            流程ID
	 * @return
	 */
	@Transactional(readOnly = false)
	public void createOrderAudit(Long orderId, String taskProcessId) {
		Order order = dao.get(orderId.toString());
		User user = iUserDao.get(order.getUserId().toString());
		Date expireDate = DateUtils.getOrderExpiredDate();
		OrderAudit oa = new OrderAudit();
		oa.setOrderNo(order.getOrderNo());
		oa.setOrgId(order.getOrgId());
		oa.setSiteId(order.getSiteId());
		oa.setTotalNum(order.getTotalNum());
		oa.setTotalAmount(order.getTotalAmount());
		oa.setTotalPoints(order.getTotalPoints());
		oa.setUserId(order.getUserId());
		oa.setUserNo(user.getNo());
		oa.setExpireDate(expireDate);
		oa.setAuditStatus(OrderAuditStatus.LEVEL_1.getType());
		oa.setAuditDate(new Date());
		oa.setTaskProcessId(taskProcessId);
		oa.setProReasons(order.getRemarks());
		oa.setCreateBy(order.getCreateBy());
		oa.setCreateDate(new Date());
		oa.setUpdateBy(order.getUpdateBy());
		oa.setUpdateDate(new Date());
		orderAuditDao.insert(oa);

		OrderDetail od = new OrderDetail();
		od.setOrderId(orderId);
		List<OrderDetail> odList = orderDetailDao.findList(od);

		for (OrderDetail orderDetail : odList) {
			OrderAuditDetail detail = new OrderAuditDetail();
			detail.setOrderAuditId(Long.valueOf(oa.getId()));
			detail.setOrderId(orderId);
			detail.setOrderNo(order.getOrderNo());
			detail.setItemId(orderDetail.getItemId());
			detail.setItemName(orderDetail.getItemName());
			detail.setSupplyId(orderDetail.getSupplyId());
			detail.setSupplyPrice(orderDetail.getSupplyPrice());
			detail.setSalePrice(orderDetail.getSalePrice());
			detail.setSalePoints(orderDetail.getSalePoints());
			detail.setSaleNum(orderDetail.getSaleNum());
			detail.setAmount(orderDetail.getAmount());
			detail.setCreateBy(order.getCreateBy());
			detail.setCreateDate(new Date());
			detail.setUpdateBy(order.getUpdateBy());
			detail.setUpdateDate(new Date());
			orderAuditDetailDao.insert(detail);
		}

		updateOrder(order, OrderAuditStatus.LEVEL_1.getType());

	}

	/**
	 * 修改审批单状态
	 *
	 * @param orderId
	 * @param taskProcessId
	 * @param status
	 */
	public Order updateOrderAndAudit(Long orderId, Integer status, String reasons) {
		Order order = dao.get(orderId.toString());
		OrderAudit orderAudit = new OrderAudit();
		orderAudit.setOrderNo(order.getOrderNo());
		orderAudit = orderAuditDao.getEntity(orderAudit);
		orderAudit.setAuditStatus(status);
		orderAudit.setAuditReasons(reasons);
		orderAuditDao.update(orderAudit);
		updateOrder(order, status);
		return order;
	}

	@Transactional(readOnly = false)
	public void changeOrder(Long orderId, Integer status) {
		updateOrderAndAudit(orderId, status, null);
	}

	/**
	 * 系统自动确认收货
	 *
	 * @param orderNo
	 */
	@Transactional(readOnly = false)
	public void orderSystemConfirm(String orderNo) {
		Order order = new Order();
		order.setOrderNo(orderNo);
		order = dao.getEntity(order);
		order.setTrackState(OrderTrackState.TUOTOU.getType());
		order.setOperStatus(OrderAuditStatus.FINISH.getType());
		order.setReceiptConfirm(BoolStatus.YES.getType());
		order.setReceiptMember(order.getUserId());
		order.setReceiptOpertor("系统自动确认收货");
		order.setReceiptDate(new Date());
		dao.update(order);

		OrderAudit oa = new OrderAudit();
		oa.setOrderNo(orderNo);
		oa = orderAuditDao.getEntity(oa);
		oa.setAuditStatus(OrderAuditStatus.FINISH.getType());
		orderAuditDao.update(oa);
	}

	/**
	 * 下单人确认收货
	 *
	 * @param orderNo
	 */
	@Transactional(readOnly = false)
	public void orderConfirm(String orderNo) {
		Order order = new Order();
		order.setOrderNo(orderNo);
		order = dao.getEntity(order);
		order.setTrackState(OrderTrackState.TUOTOU.getType());
		order.setOperStatus(OrderAuditStatus.FINISH.getType());
		order.setReceiptConfirm(BoolStatus.YES.getType());
		order.setReceiptMember(order.getUserId());
		order.setReceiptOpertor("下单人确认收货");
		order.setReceiptDate(new Date());
		dao.update(order);

		OrderAudit oa = new OrderAudit();
		oa.setOrderNo(orderNo);
		oa = orderAuditDao.getEntity(oa);
		oa.setAuditStatus(OrderAuditStatus.FINISH.getType());
		orderAuditDao.update(oa);
		
		
		OrderDetail orderDetail = new OrderDetail();
		orderDetail.setOrderNo(orderNo);
		orderDetail.setOperStatus(OrderAuditStatus.TORECEIVE.getType());
		orderDetail.setTargetOperStatus(OrderAuditStatus.FINISH.getType());
		orderDetailDao.updateByEntity(orderDetail );
		
		//发货单确认收货
		OrderDelivery orderDelivery = new OrderDelivery();
		orderDelivery.setOrderNo(orderNo);
		orderDelivery.setConfirmDate(new Date());
		orderDelivery.setConfirmOperator("下单人确认收货");
		orderDelivery.setIsConfirm(1);
		orderDeliveryDao.updateEntity(orderDelivery);
		
	}

	public List<Order> findPageSiteOrder(Page<Order> page) {
		page.setTotal(dao.countSiteOrder(page));
		return dao.findPageSiteOrder(page);
	}

	public List<OrderDetail> findPageSiteOrderDetail(Page<OrderDetail> page) {
		page.setTotal(orderDetailDao.countSiteOrderDetail(page));
		return orderDetailDao.findPageSiteOrderDetail(page);
	}
	
	public List<Order> findPageBuyerOrder(Page<Order> page){
		page.setTotal(dao.countBuyerOrder(page));
		return dao.findPageBuyerOrder(page);
	}

	/**
	 * 查询供应商订单
	 *
	 * @param page
	 * @return
	 */
	public List<Order> findPageSupplyOrder(Page<Order> page) {
		page.setTotal(dao.countSupplyOrder(page));
		return dao.findPageSupplyOrder(page);
	}

	public List<Order> findOrderPage(Page<Order> page) {
		DataScope dataScope;
		if (page != null && page.getEntity() != null && (dataScope = page.getEntity().getDataScopeFilter()) != null) {
			dataScope.setDataScopeSql(dataScopeFilter(dataScope.getUser(), dataScope.getDataScopeEnum(),
					dataScope.getOrgAlias(), dataScope.getUserAlias()));
		}

		page.setTotal(dao.countOrderPage(page));
		return dao.findOrderPage(page);
	}

	public List<Order> findOrderFlowPage(Page<Order> page) {
		DataScope dataScope;
		if (page != null && page.getEntity() != null && (dataScope = page.getEntity().getDataScopeFilter()) != null) {
			dataScope.setDataScopeSql(dataScopeFilter(dataScope.getUser(), dataScope.getDataScopeEnum(),
					dataScope.getOrgAlias(), dataScope.getUserAlias()));
		}

		page.setTotal(dao.countOrderFlowPage(page));
		return dao.findOrderFlowPage(page);
	}

	public List<Order> findJfOrderPage(Page<Order> page) {
		DataScope dataScope;
		if (page != null && page.getEntity() != null && (dataScope = page.getEntity().getDataScopeFilter()) != null) {
			dataScope.setDataScopeSql(dataScopeFilter(dataScope.getUser(), dataScope.getDataScopeEnum(),
					dataScope.getOrgAlias(), dataScope.getUserAlias()));
		}

		page.setTotal(dao.countJfOrderPage(page));
		return dao.findJfOrderPage(page);
	}

	public int countOrderPage(Page<Order> page) {
		return dao.countOrderPage(page);
	}

	/**
	 * 获取上级机构ID
	 *
	 * @param orgId
	 * @return
	 */
	public Long getParentOrgId(Long orgId) {
		Organization org = iOrganizationDao.get(orgId.toString());
		if (org != null && StringUtils.isNotBlank(org.getParentId()))
			return Long.valueOf(org.getParentId());
		else
			return null;
	}

	/**
	 * 一级审批监听
	 *
	 * @param processInstanceId
	 * @param orderId
	 * @return
	 */
	@Transactional(readOnly = false)
	public Result firstTask(String processInstanceId, String taskId, String orderId) {

		Result result = Result.newInstance();

		result.setCode(ResultCode.SUCCESS);
		return result;
	}

	/**
	 * 二级审批监听
	 *
	 * @param processInstanceId
	 * @param orderId
	 * @return
	 */
	@Transactional(readOnly = false)
	public Result secondTask(String processInstanceId, String taskId, String orderId) {

		Result result = Result.newInstance();

		result.setCode(ResultCode.SUCCESS);
		return result;

	}

	/**
	 * 确认发货
	 *
	 * @param taskId
	 * @param orderId
	 * @return
	 */
	@Transactional(readOnly = false)
	public Result finishTask(String taskId, String orderId) {

		Result result = Result.newInstance();

		result.setCode(ResultCode.SUCCESS);
		return result;
	}

	/**
	 * 订单撤销
	 *
	 * @param orderId
	 * @param status
	 */
	@Transactional(readOnly = false)
	public void orderCancel(String taskId, Long orderId, Integer status, String reasons) {
		String abstractType = AbstractType.ORDER_CANCEL.getType();
		if (OrderAuditStatus.CANCEL.getType() == status) {
			abstractType = AbstractType.ORDER_CANCEL.getType();
		} else if (OrderAuditStatus.TO_TRACK.getType() == status) {
			abstractType = AbstractType.ORDER_REJECT.getType();
		} else if (OrderAuditStatus.EXPIRE.getType() == status) {
			abstractType = AbstractType.ORDER_EXPIRE.getType();
		}
	}

	public Double sumOrderAmount(Page<Order> page) {
		return dao.sumOrderAmount(page);
	}

	public List<Order> findBatchOrderList(Order o) {
		return dao.findBatchOrderList(o);
	}

	public List<Order> findYXBatchOrderList(Order o) {
		return dao.findYXBatchOrderList(o);
	}

	public Map<String, Object> sumOrderSaleReport(Page<Order> page) {
		return dao.sumOrderSaleReport(page);
	}

	public List<Order> findPageV1(Page<Order> page) {
		page.setTotal(dao.countOrderV1(page));
		return dao.findPageV1(page);
	}

	public List<Order> findToDeliveryPage(Page<Order> page) {
		page.setTotal(dao.countToDeliverOrder(page));
		return dao.findPageToDeliveryOrder(page);
	}

	@Transactional(readOnly = false)
	public Result cancelOrderDetail(OrderDetail orderDetail,Long cancelUserId) {
		Result result = new Result();
		List<OrderDetail> toCancelOrderDetails = new ArrayList<>();
		List<OrderDetail> orderDetails = orderDetailDao.findList(orderDetail);
		for(OrderDetail orderDetail_1 : orderDetails){
			//未分配，未发货
			if(StringUtils.isBlank(orderDetail_1.getIsDist()) && orderDetail_1.getOperStatus() == OrderAuditStatus.TORECEIVE.getType() && orderDetail_1.getTrackState() == OrderTrackState.NEW.getType()){
				toCancelOrderDetails.add(orderDetail_1);
			}
		}
		
		for(OrderDetail orderDetail2 : toCancelOrderDetails){
			orderDetail2.setOperStatus(OrderAuditStatus.BUYER_CACEL.getType());
			orderDetail2.setCancelUserId(cancelUserId);
			orderDetail2.setCancelDate(new Date());
			orderDetail2.setCancelOperator("买手取消");
			orderDetailDao.update(orderDetail2);
		}
		
		if(CollectionUtils.isEmpty(toCancelOrderDetails)){
			result.setCode(ResultCode.RETURN_FAILURE);
			result.setMsg("取消失败：只能取消“未分配”或者“未发货”的订单");
			return result;
		}
		result.setCode(ResultCode.SUCCESS);
		result.setMsg("取消成功");
		return result;
		
	}

	public Map<String, Object> sumOrderReport(Page<Order> page) {
		return dao.sumOrderReport(page);
	}

	public List<Order> findPageProductOrder(Page<Order> page) {
		page.setTotal(dao.countProductOrder(page));
		return dao.findPageProductOrder(page);
	}

	@Transactional(readOnly =false)
	public int updateEntity(Order toUpdateOrder) {
		return dao.updateEntity(toUpdateOrder);
		
	}

	public List<Order> findPageBuyerSpecOrder(Page<Order> page) {
		return dao.findPageBuyerSpecOrder(page);
	}

	public Map<String, Object> sumDeliveryReport(Page<Order> page) {
		return dao.sumDeliveryReport(page);
	}

	public Map<String, Object> sumToDeliveryReport(Page<Order> page) {
		return dao.sumToDeliveryReport(page);
	}

	public Map<String, Object> sumOrderBuyerReport(Page<Order> page) {
		return dao.sumOrderBuyerReport(page);
	}

	public int sumProdSales(Page<Order> page) {
		return dao.sumProdSales(page);
	}

}