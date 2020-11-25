package com.jf.plus.core.order.controller;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import cn.iutils.common.Page;
import cn.iutils.common.utils.JStringUtils;
import cn.iutils.common.BaseController;
import com.jf.plus.core.order.entity.OrderDeliveryDetailSku;
import com.jf.plus.core.order.service.OrderDeliveryDetailSkuService;

/**
* 订单发货SKU表 控制器
* @author Tng
* @version 1.0
*/
@Controller
@RequestMapping("${adminPath}/order/orderDeliveryDetailSku")
public class OrderDeliveryDetailSkuController extends BaseController {

    @Autowired
    private OrderDeliveryDetailSkuService orderDeliveryDetailSkuService;

    @ModelAttribute
    public OrderDeliveryDetailSku get(@RequestParam(required = false) String id) {
        OrderDeliveryDetailSku entity = null;
        if (JStringUtils.isNotBlank(id)) {
            entity = orderDeliveryDetailSkuService.get(id);
        }
        if (entity == null) {
            entity = new OrderDeliveryDetailSku();
        }
        return entity;
    }

    @RequestMapping(value={"/list",""})
    public String list(Model model, Page<OrderDeliveryDetailSku> page) {
        model.addAttribute("page", page.setList(orderDeliveryDetailSkuService.findPage(page)));
        return "order/orderDeliveryDetailSku/list";
    }

    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public String create(OrderDeliveryDetailSku orderDeliveryDetailSku,Model model) {
        model.addAttribute("orderDeliveryDetailSku", orderDeliveryDetailSku);
        return "order/orderDeliveryDetailSku/form";
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String create(OrderDeliveryDetailSku orderDeliveryDetailSku, RedirectAttributes redirectAttributes) {
        orderDeliveryDetailSkuService.save(orderDeliveryDetailSku);
        addMessage(redirectAttributes,"新增成功");
        return "redirect:"+ adminPath +"/order/orderDeliveryDetailSku/update?id="+orderDeliveryDetailSku.getId();
    }

    @RequestMapping(value = "/update", method = RequestMethod.GET)
    public String update(OrderDeliveryDetailSku orderDeliveryDetailSku, Model model) {
        model.addAttribute("orderDeliveryDetailSku", orderDeliveryDetailSku);
        return "order/orderDeliveryDetailSku/form";
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String update(OrderDeliveryDetailSku orderDeliveryDetailSku, RedirectAttributes redirectAttributes) {
        orderDeliveryDetailSkuService.save(orderDeliveryDetailSku);
        addMessage(redirectAttributes,"修改成功");
        return "redirect:"+ adminPath +"/order/orderDeliveryDetailSku/update?id="+orderDeliveryDetailSku.getId();
    }

    @RequestMapping(value = "/{id}/delete", method = RequestMethod.GET)
    public String delete(@PathVariable("id") String id,int pageNo,int pageSize, RedirectAttributes redirectAttributes) {
        orderDeliveryDetailSkuService.delete(id);
        addMessage(redirectAttributes,"删除成功");
        return "redirect:"+ adminPath +"/order/orderDeliveryDetailSku?pageNo="+pageNo+"&pageSize="+pageSize;
    }
}
