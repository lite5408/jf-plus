package com.jf.plus.core.site.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.jf.plus.core.order.entity.Order;
import com.jf.plus.core.order.entity.OrderDetail;
import com.jf.plus.core.order.service.OrderService;

import cn.iutils.common.BaseController;
import cn.iutils.common.Page;
import cn.iutils.common.utils.JStringUtils;
import cn.iutils.common.utils.UserUtils;

/**
* 订单主表 控制器
* @author Tng
* @version 1.0
*/
@Controller
@RequestMapping("${adminPath}/site/orderDetail")
public class SiteOrderDetailController extends BaseController {

    @Autowired
    private OrderService orderService;

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

    @RequestMapping(value={"/list",""})
    public String list(Model model, Order order, OrderDetail orderDetail, Page<OrderDetail> page) {
    	if(UserUtils.getMySite() == null){
			model.addAttribute("errorMsg", "您没有管理站点权限，请联系管理员");
			return "error/400";
		}
    	
    	page.getCondition().put("siteId", UserUtils.getMySite().getSiteId());
    	
        model.addAttribute("page", page.setList(orderService.findPageSiteOrderDetail(page)));
        return "site/siteOrder/list_detail";
    }
}
