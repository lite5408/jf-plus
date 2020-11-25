package com.jf.plus.core.order.controller;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jf.plus.common.core.Result;
import com.jf.plus.common.core.ResultCode;
import com.jf.plus.common.core.enums.OrderAuditStatus;
import com.jf.plus.common.core.enums.OrderSource;
import com.jf.plus.common.vo.OutOrderVo;
import com.jf.plus.core.channel.service.OrderAPIService;
import com.jf.plus.core.order.entity.Order;
import com.jf.plus.core.order.entity.OrderDetail;
import com.jf.plus.core.order.entity.OrderDetailSku;
import com.jf.plus.core.order.service.OrderDetailService;
import com.jf.plus.core.order.service.OrderDetailSkuService;
import com.jf.plus.core.order.service.OrderService;
import com.jf.plus.core.product.entity.ProductStock;
import com.jf.plus.core.product.service.ProductStockService;
import com.jf.plus.core.site.entity.SiteProduct;
import com.jf.plus.core.site.service.SiteProductService;

import cn.iutils.common.BaseController;
import cn.iutils.common.DataScope;
import cn.iutils.common.Page;
import cn.iutils.common.utils.JStringUtils;
import cn.iutils.common.utils.UserUtils;
import cn.iutils.sys.entity.enums.DataScopeEnum;

/**
 * 订单主表 控制器
 *
 * @author Tng
 * @version 1.0
 */
@Controller
@RequestMapping("${adminPath}/order")
public class OrderController extends BaseController {

	@Autowired
	private OrderService orderService;

	@Autowired
	private OrderAPIService orderAPIService;

	@Autowired
	private OrderDetailService orderDetailService;

	@Autowired
	private OrderDetailSkuService orderDetailSkuService;

	@Autowired
	private SiteProductService siteProductService;
	
	@Autowired
	private ProductStockService productStockService;

	@ModelAttribute
	public Order get(@RequestParam(required = false) String id) {
		Order entity = null;
		if (JStringUtils.isNotBlank(id)) {
			entity = orderService.get(id);
		}
		if (entity == null) {
			entity = new Order();
		}
		return entity;
	}

	@RequestMapping(value = { "/list", "" })
	public String list(Model model, Order order, Page<Order> page) {
		order.setUser(UserUtils.getLoginUser());
		order.setDataScopeFilter(new DataScope(UserUtils.getLoginUser(), DataScopeEnum.orgFollow, "org", "u"));
		order.setOrderFrom(OrderSource.JC.getType());
		page.setEntity(order);

		Integer operStatus = order.getOperStatus();
		if (order.getOperStatus() == null) {
			page.getCondition().put("operStatusLike",
					" ( a.oper_status in (" + OrderAuditStatus.FINISH.getType() + ","
							+ OrderAuditStatus.TORECEIVE.getType() + "," + OrderAuditStatus.LEVEL_1.getType() + ","
							+ OrderAuditStatus.LEVEL_2.getType() + ") ) ");
		} else if (order.getOperStatus() == OrderAuditStatus.LEVEL_1.getType()) {
			order.setOperStatus(null);// 设置为空
			page.getCondition().put("operStatusLike", " ( a.oper_status in (" + OrderAuditStatus.LEVEL_1.getType() + ","
					+ OrderAuditStatus.LEVEL_2.getType() + ") ) ");
		}

		model.addAttribute("page", page.setList(orderService.findOrderPage(page)));
		order.setOperStatus(operStatus);
		return "order/order/list";
	}

	@RequestMapping("toDistList")
	public String toDistList(Model model, OrderDetail orderDetail, Page<OrderDetail> page) {
		if("-1".equals(orderDetail.getIsDist())){//全部
			orderDetail.setIsDist(null);
		}
		page.setEntity(orderDetail);
		
		if(StringUtils.isNotBlank(orderDetail.getShelvesDateRange())){
			String[] rangeDate = orderDetail.getShelvesDateRange().split(" - ");
			page.getCondition().put("shelvesDateStart", rangeDate[0]);
			page.getCondition().put("shelvesDateEnd", rangeDate[1]);
		}
		
		model.addAttribute("page", page.setList(orderDetailService.findToDistPage(page)));

		return "order/order/toDistList";
	}

	@RequestMapping(value = "distListDetail")
	public String distListDetail(Model model, OrderDetail orderDetail, Page<OrderDetail> page) {
		page.setEntity(orderDetail);
		model.addAttribute("page", page.setList(orderDetailService.findDistList(page)));
		model.addAttribute("siteProduct", siteProductService.get(orderDetail.getItemId() + ""));
		Integer saleNum = 0;
		Integer distNum = 0;
		for(OrderDetail orderDetail_2 : page.getList()){
			saleNum += orderDetail_2.getSaleNum();
			distNum += (orderDetail_2.getDistNum() == null ? 0 : orderDetail_2.getDistNum());
		}
		
		model.addAttribute("saleNum", saleNum);
		model.addAttribute("distNum", distNum);
		
		
		if(StringUtils.isEmpty(orderDetail.getIsDist()) || "0".equals(orderDetail.getIsDist())){
			return "order/order/distListDetail";
		}else{
			return "order/order/hasDistListDetail";
		}
	}

	/**
	 * 订单数量分配
	 */
	@RequestMapping("distOrderDetail")
	@ResponseBody
	public Result distOrderDetail(@RequestBody String postdata) {
		Result result = new Result();
		try {
			if(StringUtils.isEmpty(postdata)){
				result.setCode(ResultCode.NOT_DATA);
				result.setMsg("参数缺失");
				return result;
			}
			JSONObject jsonObject = JSONObject.parseObject(postdata);
			
			String orderDetailList = jsonObject.getString("orderDetailList");
			Integer distStock = jsonObject.getInteger("distStock");
			List<OrderDetail> orderDetails = JSONArray.parseArray(orderDetailList, OrderDetail.class);
			
			Long itemId = 0L,orgId = 0L;
			Integer distNum = 0;
			for(OrderDetail orderDetail : orderDetails){
				itemId = orderDetail.getItemId();
				orgId = orderDetail.getOrgId();
				distNum = orderDetail.getDistNum();
				orderDetail.setIsDist("0");
				orderDetail = orderDetailService.getEntity(orderDetail);
				OrderDetail toUpdate = new OrderDetail();
				toUpdate.setId(orderDetail.getId());
				toUpdate.setIsDist("1");
				toUpdate.setDistDate(new Date());
				toUpdate.setDistOperator(UserUtils.getLoginUser().getName());
				toUpdate.setDistNum(distNum);
				
				orderDetailService.save(toUpdate);
			}
			
			if(itemId != 0){
				SiteProduct siteProduct = siteProductService.get(itemId+"");
				ProductStock productStock = new ProductStock();
				productStock.setProductNo(siteProduct.getProductId());
				
				productStock = productStockService.getEntity(productStock);
				if(productStock != null){
					productStock.setDistStock(distStock);
					productStockService.save(productStock);
				}
			}
			

			result.setMsg("分配成功");
			result.setCode(ResultCode.SUCCESS);
			return result;
		} catch (Exception e) {
			logger.error("系统异常:{}", e);
			return Result.newExceptionInstance();
		}
	}

	@RequestMapping(value = { "/jf/list" })
	public String jfList(Model model, Order order, Page<Order> page) {
		order.setUser(UserUtils.getLoginUser());
		order.setDataScopeFilter(new DataScope(UserUtils.getLoginUser(), DataScopeEnum.orgFollow, "org", "u"));
		order.setOrderFrom(OrderSource.JF.getType());
		page.setEntity(order);

		Integer operStatus = order.getOperStatus();
		if (order.getOperStatus() == null) {
			page.getCondition().put("operStatusLike", " ( a.oper_status in (" + OrderAuditStatus.FINISH.getType() + ","
					+ OrderAuditStatus.TORECEIVE.getType() + ") ) ");
		}

		model.addAttribute("page", page.setList(orderService.findJfOrderPage(page)));
		order.setOperStatus(operStatus);
		return "order/order/jfList";
	}

	@RequestMapping(value = { "/jf/flowList"})
	public String jfFlowList(Model model, Order order, Page<Order> page) {
		order.setUser(UserUtils.getLoginUser());
		order.setDataScopeFilter(new DataScope(UserUtils.getLoginUser(), DataScopeEnum.org, "org", "u"));
		order.setOrderFrom(OrderSource.JF.getType());
		page.setEntity(order);

		Integer operStatus = order.getOperStatus();
		if (order.getOperStatus() == null) {
			page.getCondition().put("operStatusLike", " ( a.oper_status in (" + OrderAuditStatus.FINISH.getType() + ","
					+ OrderAuditStatus.TORECEIVE.getType() + ") ) ");
		}

		model.addAttribute("page", page.setList(orderService.findOrderFlowPage(page)));
		order.setOperStatus(operStatus);
		return "order/order/jfFlowList";
	}

	@RequestMapping(value = "/track")
	public String track(OutOrderVo outOrderVo, Model model) {
		Result result = orderAPIService.orderTrack(outOrderVo);
		if (result.isSuccess()) {
			model.addAttribute("orderTrack", result.getObj());
		}

		return "order/order/track";
	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public String create(Order order, Model model) {
		model.addAttribute("order", order);
		return "order/form";
	}

	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public String create(Order order, RedirectAttributes redirectAttributes) {
		orderService.save(order);
		addMessage(redirectAttributes, "新增成功");
		return "redirect:" + adminPath + "/order/update?id=" + order.getId();
	}

	@RequestMapping(value = "/update", method = RequestMethod.GET)
	public String update(Order order, Model model) {
		model.addAttribute("order", order);
		return "order/form";
	}

	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public String update(Order order, RedirectAttributes redirectAttributes) {
		orderService.save(order);
		addMessage(redirectAttributes, "修改成功");
		return "redirect:" + adminPath + "/order/update?id=" + order.getId();
	}

	@RequestMapping(value = "/{id}/delete", method = RequestMethod.GET)
	public String delete(@PathVariable("id") String id, int pageNo, int pageSize,
			RedirectAttributes redirectAttributes) {
		orderService.delete(id);
		addMessage(redirectAttributes, "删除成功");
		return "redirect:" + adminPath + "/order?pageNo=" + pageNo + "&pageSize=" + pageSize;
	}

	/**
	 * 商品订单列表
	 * 
	 * @param model
	 * @param page
	 * @param order
	 * @return
	 */
	@RequestMapping("/list_v1")
	public String list_v1(Model model, Page<Order> page, Order order) {
		order.setUser(UserUtils.getLoginUser());
		order.setDataScopeFilter(new DataScope(UserUtils.getLoginUser(), DataScopeEnum.orgFollow, "org", "u"));
		page.setEntity(order);
		model.addAttribute("page", page.setList(orderService.findPageV1(page)));

		return "order/order/list_v1";
	}

	@RequestMapping("/detail_v1")
	public String detail_v1(Model model, @RequestParam String orderNo) {
		Order order = orderService.getDetail(orderNo);
		OrderDetail detail = new OrderDetail();
		detail.setOperStatus(order.getOperStatus());
		detail.setOrderNo(orderNo);
		order.setOrderDetailList(orderDetailService.findList(detail));
		// 商品sku
		for (OrderDetail orderDetail : order.getOrderDetailList()) {
			Page<OrderDetailSku> skuPage = new Page<>(0, 1000);
			skuPage.getCondition().put("orderDetailId", orderDetail.getId());
			orderDetail.setOrderDetailSkus(orderDetailSkuService.findPage(skuPage));
		}

		model.addAttribute("order", order);
		return "order/order/detail_v1";
	}
}
