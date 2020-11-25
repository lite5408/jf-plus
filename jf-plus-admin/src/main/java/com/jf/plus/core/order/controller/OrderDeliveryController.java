package com.jf.plus.core.order.controller;

import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.jf.plus.common.core.enums.OrderAuditStatus;
import com.jf.plus.common.core.enums.OrderTrackState;
import com.jf.plus.core.order.entity.Order;
import com.jf.plus.core.order.entity.OrderDelivery;
import com.jf.plus.core.order.entity.OrderDeliveryDetail;
import com.jf.plus.core.order.entity.OrderDeliveryExport;
import com.jf.plus.core.order.entity.OrderDeliveryExportList;
import com.jf.plus.core.order.entity.OrderDetail;
import com.jf.plus.core.order.entity.OrderDetailSku;
import com.jf.plus.core.order.service.OrderDeliveryDetailService;
import com.jf.plus.core.order.service.OrderDeliveryDetailSkuService;
import com.jf.plus.core.order.service.OrderDeliveryExportService;
import com.jf.plus.core.order.service.OrderDeliveryService;
import com.jf.plus.core.order.service.OrderDetailService;
import com.jf.plus.core.order.service.OrderDetailSkuService;
import com.jf.plus.core.order.service.OrderService;

import cn.iutils.common.BaseController;
import cn.iutils.common.DataScope;
import cn.iutils.common.Page;
import cn.iutils.common.utils.JStringUtils;
import cn.iutils.common.utils.UserUtils;
import cn.iutils.sys.entity.enums.DataScopeEnum;

/**
* 订单发货表 控制器
* @author Tng
* @version 1.0
*/
@Controller
@RequestMapping("${adminPath}/order/orderDelivery")
public class OrderDeliveryController extends BaseController {

    @Autowired
    private OrderDeliveryService orderDeliveryService;
    
    @Autowired
    private OrderService orderService;
    
    @Autowired
    private OrderDetailService orderDetailService;
    
    @Autowired
    private OrderDetailSkuService orderDetailSkuService;
    
    @Autowired
    private OrderDeliveryDetailService orderDeliveryDetailService;
    
    @Autowired
    private OrderDeliveryDetailSkuService orderDeliveryDetailSkuService;
    
    @Autowired
    private OrderDeliveryExportService orderDeliveryExportService;
    
    

    @ModelAttribute
    public OrderDelivery get(@RequestParam(required = false) String id) {
        OrderDelivery entity = null;
        if (JStringUtils.isNotBlank(id)) {
            entity = orderDeliveryService.get(id);
        }
        if (entity == null) {
            entity = new OrderDelivery();
        }
        return entity;
    }

    @RequestMapping(value={"/toDeliveryList",""})
    public String list(Model model, Order order,Page<Order> page) {
    	order.setUser(UserUtils.getLoginUser());
		order.setDataScopeFilter(new DataScope(UserUtils.getLoginUser(), DataScopeEnum.orgFollow, "org", "u"));
		
    	page.setEntity(order);

    	model.addAttribute("page", page.setList(orderService.findToDeliveryPage(page)));
        return "order/orderDelivery/toDeliveryList";
    }
    
    @RequestMapping(value={"/exportPage"})
    public String exportPage(Model model, OrderDelivery orderDelivery,Page<OrderDelivery> page) {
    	orderDelivery.setUser(UserUtils.getLoginUser());
    	orderDelivery.setDataScopeFilter(new DataScope(UserUtils.getLoginUser(), DataScopeEnum.orgFollow, "org", "u"));
		
    	page.setEntity(orderDelivery);

    	model.addAttribute("page", page.setList(orderDeliveryService.findPage(page)));
        return "order/orderDelivery/exportPage";
    }
    
    @RequestMapping(value={"/toDeliveryList_v1"})
    public String list_v1(Model model, Order order,Page<Order> page) {
    	order.setUser(UserUtils.getLoginUser());
		order.setDataScopeFilter(new DataScope(UserUtils.getLoginUser(), DataScopeEnum.orgFollow, "org", "u"));
		
    	page.setEntity(order);

    	model.addAttribute("page", page.setList(orderService.findToDeliveryPage(page)));
        return "order/orderDelivery/toDeliveryList";
    }

    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public String create(OrderDelivery orderDelivery,Model model) {
        model.addAttribute("orderDelivery", orderDelivery);
        return "order/orderDelivery/form";
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String create(OrderDelivery orderDelivery, RedirectAttributes redirectAttributes) {
        orderDeliveryService.save(orderDelivery);
        addMessage(redirectAttributes,"新增成功");
        return "redirect:"+ adminPath +"/order/orderDelivery/update?id="+orderDelivery.getId();
    }

    @RequestMapping(value = "/update", method = RequestMethod.GET)
    public String update(OrderDelivery orderDelivery, Model model) {
        model.addAttribute("orderDelivery", orderDelivery);
        return "order/orderDelivery/form";
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String update(OrderDelivery orderDelivery, RedirectAttributes redirectAttributes) {
        orderDeliveryService.save(orderDelivery);
        addMessage(redirectAttributes,"修改成功");
        return "redirect:"+ adminPath +"/order/orderDelivery/update?id="+orderDelivery.getId();
    }

    @RequestMapping(value = "/confirm", method = RequestMethod.GET)
    public String confirm(String id,int pageNo,int pageSize, RedirectAttributes redirectAttributes) {
        OrderDelivery orderDelivery = new OrderDelivery();
        orderDelivery.setId(id);
        orderDelivery.setIsConfirm(1);
        orderDelivery.setConfirmDate(new Date());
        orderDelivery.setConfirmOperator(UserUtils.getLoginUser().getName());
        orderDeliveryService.save(orderDelivery);
        
        orderDeliveryService.confirmOrderDetail(orderDelivery);

        addMessage(redirectAttributes,"操作成功");
        return "redirect:"+ adminPath +"/order/orderDelivery/exportPage?pageNo="+pageNo+"&pageSize="+pageSize;
    }
    
    
    @RequestMapping(value = "/{id}/delete", method = RequestMethod.GET)
    public String delete(@PathVariable("id") String id,int pageNo,int pageSize, RedirectAttributes redirectAttributes) {
    	if(orderDeliveryService.get(id).getIsConfirm() !=  null && orderDeliveryService.get(id).getIsConfirm() == 1){
    		addMessage(redirectAttributes,"删除失败：订单已确认收货，无法删除");
            return "redirect:"+ adminPath +"/order/orderDelivery/hasDeliveryList?pageNo="+pageNo+"&pageSize="+pageSize;
    	}
        orderDeliveryService.delete(id);
        addMessage(redirectAttributes,"删除成功");
        return "redirect:"+ adminPath +"/order/orderDelivery/hasDeliveryList?pageNo="+pageNo+"&pageSize="+pageSize;
    }
    
    
    @RequestMapping("/detail_v1")
	public String detail_v1(Model model,@RequestParam String orderNo){
		Order order = orderService.getDetail(orderNo);
		OrderDetail detail = new OrderDetail();
		detail.setOperStatus(order.getOperStatus());
		detail.setOrderNo(orderNo);
		detail.setTrackState(OrderTrackState.DELIVERED.getType());//已发货的
		
		order.setOrderDetailList(orderDetailService.findDeliveryList(detail));
		//商品sku
		for(OrderDetail orderDetail : order.getOrderDetailList()){
			Page<OrderDetailSku> skuPage = new Page<>(0, 1000);
			skuPage.getCondition().put("orderDetailId", orderDetail.getId());
			orderDetail.setOrderDetailSkus(orderDetailSkuService.findPage(skuPage));
		}
		
		model.addAttribute("order", order);
		return "order/orderDelivery/delivery_detail_v1";
	}
    
	
	@RequestMapping("toDeliveryProdList")
	public String toDeliveryProdList(Model model,OrderDetail orderDetail , Page<OrderDetail> page){
		
		if(StringUtils.isEmpty(orderDetail.getIsDist())){
			orderDetail.setIsDist("1");
		}
		orderDetail.setOperStatus(OrderAuditStatus.TORECEIVE.getType());
		orderDetail.setTrackState(OrderTrackState.NEW.getType());
		page.setEntity(orderDetail);
		model.addAttribute("page", page.setList(orderDetailService.findDeliveryPage(page)));
		
		return "order/orderDelivery/toDeliveryProdList";
	}
	
	@RequestMapping("toDeliveryProduct")
	public String toDeliveryProduct(Model model,OrderDetail orderDetail){
		orderDetail = orderDetailService.get(orderDetail.getId());
		model.addAttribute("orderDetail", orderDetail);
		
		return "order/orderDelivery/toDeliveryProduct";
	}
	
	
	@RequestMapping("hasDeliveryList")
	public String hasDeliveryList(Model model, OrderDelivery orderDelivery , Page<OrderDelivery> page){
		page.setEntity(orderDelivery);
		model.addAttribute("page",page.setList(orderDeliveryService.findProductDeliveryPage(page)));
		return "order/orderDelivery/hasDeliveryList";
	}
	
	@RequestMapping("deliveryDetailProduct")
	public String deliveryDetailProduct(Model model,OrderDeliveryDetail orderDeliveryDetail){
		
		model.addAttribute("orderDeliveryDetailList",orderDeliveryDetailService.findList(orderDeliveryDetail));
		
		return "order/orderDelivery/deliveryDetailProduct";
	}
	
	@RequestMapping("exportProduct")
	public String exportProduct(Model model,OrderDeliveryDetail orderDeliveryDetail){
		
		model.addAttribute("orderDeliveryDetailList",orderDeliveryDetailService.findList(orderDeliveryDetail));
		
		return "order/orderDelivery/exportProduct";
	}
	
	
	/**
	 * 设置导出单销售价
	 */
	@RequestMapping("/deliveryExport")
	public String deliveryExport(OrderDeliveryExportList orderDeliveryExportList, RedirectAttributes redirectAttributes) {

			Long deliveryId = 0L;
			for (OrderDeliveryExport orderDeliveryExport : orderDeliveryExportList.getOrderDeliveryExports()) {
				deliveryId = orderDeliveryExport.getDeliveryId();
				orderDeliveryExport.setExportDate(new Date());
				orderDeliveryExport.setOperStatus(1);
				orderDeliveryExportService.save(orderDeliveryExport);
			}

			OrderDelivery orderDelivery = new OrderDelivery();
			orderDelivery.setId(deliveryId + "");
			orderDelivery.setOperStatus(1);
			orderDeliveryService.save(orderDelivery);
			
			addMessage(redirectAttributes,"操作成功");
	        return "redirect:"+ adminPath +"/order/orderDelivery/exportPage";
	}
	
}
