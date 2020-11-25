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
import com.jf.plus.core.order.entity.OrderDetailSku;
import com.jf.plus.core.order.service.OrderDetailSkuService;

/**
* 订单明细表 控制器
* @author Tng
* @version 1.0
*/
@Controller
@RequestMapping("${adminPath}/order/orderDetailSku")
public class OrderDetailSkuController extends BaseController {

    @Autowired
    private OrderDetailSkuService orderDetailSkuService;

    @ModelAttribute
    public OrderDetailSku get(@RequestParam(required = false) String id) {
        OrderDetailSku entity = null;
        if (JStringUtils.isNotBlank(id)) {
            entity = orderDetailSkuService.get(id);
        }
        if (entity == null) {
            entity = new OrderDetailSku();
        }
        return entity;
    }

    @RequestMapping(value={"/list",""})
    public String list(Model model, Page<OrderDetailSku> page) {
        model.addAttribute("page", page.setList(orderDetailSkuService.findPage(page)));
        return "order/orderDetailSku/list";
    }

    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public String create(OrderDetailSku orderDetailSku,Model model) {
        model.addAttribute("orderDetailSku", orderDetailSku);
        return "order/orderDetailSku/form";
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String create(OrderDetailSku orderDetailSku, RedirectAttributes redirectAttributes) {
        orderDetailSkuService.save(orderDetailSku);
        addMessage(redirectAttributes,"新增成功");
        return "redirect:"+ adminPath +"/order/orderDetailSku/update?id="+orderDetailSku.getId();
    }

    @RequestMapping(value = "/update", method = RequestMethod.GET)
    public String update(OrderDetailSku orderDetailSku, Model model) {
        model.addAttribute("orderDetailSku", orderDetailSku);
        return "order/orderDetailSku/form";
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String update(OrderDetailSku orderDetailSku, RedirectAttributes redirectAttributes) {
        orderDetailSkuService.save(orderDetailSku);
        addMessage(redirectAttributes,"修改成功");
        return "redirect:"+ adminPath +"/order/orderDetailSku/update?id="+orderDetailSku.getId();
    }

    @RequestMapping(value = "/{id}/delete", method = RequestMethod.GET)
    public String delete(@PathVariable("id") String id,int pageNo,int pageSize, RedirectAttributes redirectAttributes) {
        orderDetailSkuService.delete(id);
        addMessage(redirectAttributes,"删除成功");
        return "redirect:"+ adminPath +"/order/orderDetailSku?pageNo="+pageNo+"&pageSize="+pageSize;
    }
}
