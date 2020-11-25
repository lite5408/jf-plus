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
import com.jf.plus.core.order.entity.OrderDeliveryExport;
import com.jf.plus.core.order.service.OrderDeliveryExportService;

/**
* 订单导出表 控制器
* @author Tng
* @version 1.0
*/
@Controller
@RequestMapping("${adminPath}/order/orderDeliveryExport")
public class OrderDeliveryExportController extends BaseController {

    @Autowired
    private OrderDeliveryExportService orderDeliveryExportService;

    @ModelAttribute
    public OrderDeliveryExport get(@RequestParam(required = false) String id) {
        OrderDeliveryExport entity = null;
        if (JStringUtils.isNotBlank(id)) {
            entity = orderDeliveryExportService.get(id);
        }
        if (entity == null) {
            entity = new OrderDeliveryExport();
        }
        return entity;
    }

    @RequestMapping(value={"/list",""})
    public String list(Model model, Page<OrderDeliveryExport> page) {
        model.addAttribute("page", page.setList(orderDeliveryExportService.findPage(page)));
        return "order/orderDeliveryExport/list";
    }

    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public String create(OrderDeliveryExport orderDeliveryExport,Model model) {
        model.addAttribute("orderDeliveryExport", orderDeliveryExport);
        return "order/orderDeliveryExport/form";
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String create(OrderDeliveryExport orderDeliveryExport, RedirectAttributes redirectAttributes) {
        orderDeliveryExportService.save(orderDeliveryExport);
        addMessage(redirectAttributes,"新增成功");
        return "redirect:"+ adminPath +"/order/orderDeliveryExport/update?id="+orderDeliveryExport.getId();
    }

    @RequestMapping(value = "/update", method = RequestMethod.GET)
    public String update(OrderDeliveryExport orderDeliveryExport, Model model) {
        model.addAttribute("orderDeliveryExport", orderDeliveryExport);
        return "order/orderDeliveryExport/form";
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String update(OrderDeliveryExport orderDeliveryExport, RedirectAttributes redirectAttributes) {
        orderDeliveryExportService.save(orderDeliveryExport);
        addMessage(redirectAttributes,"修改成功");
        return "redirect:"+ adminPath +"/order/orderDeliveryExport/update?id="+orderDeliveryExport.getId();
    }

    @RequestMapping(value = "/{id}/delete", method = RequestMethod.GET)
    public String delete(@PathVariable("id") String id,int pageNo,int pageSize, RedirectAttributes redirectAttributes) {
        orderDeliveryExportService.delete(id);
        addMessage(redirectAttributes,"删除成功");
        return "redirect:"+ adminPath +"/order/orderDeliveryExport?pageNo="+pageNo+"&pageSize="+pageSize;
    }
}
