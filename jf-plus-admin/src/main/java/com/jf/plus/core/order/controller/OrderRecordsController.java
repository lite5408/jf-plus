package com.jf.plus.core.order.controller;

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
import com.jf.plus.core.order.entity.OrderRecords;
import com.jf.plus.core.order.service.OrderRecordsService;

/**
* 订单支付记录 控制器
* @author Tng
* @version 1.0
*/
@Controller
@RequestMapping("${adminPath}/order/orderRecords")
public class OrderRecordsController extends BaseController {

    @Autowired
    private OrderRecordsService orderRecordsService;

    @ModelAttribute
    public OrderRecords get(@RequestParam(required = false) String id) {
        OrderRecords entity = null;
        if (JStringUtils.isNotBlank(id)) {
            entity = orderRecordsService.get(id);
        }
        if (entity == null) {
            entity = new OrderRecords();
        }
        return entity;
    }

    @RequestMapping(value="/list")
    public String list(Model model, Page<OrderRecords> page) {
        model.addAttribute("page", page.setList(orderRecordsService.findPage(page)));
        return "order/orderRecords/list";
    }

    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public String create(OrderRecords orderRecords,Model model) {
        model.addAttribute("orderRecords", orderRecords);
        return "order/orderRecords/form";
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String create(OrderRecords orderRecords, RedirectAttributes redirectAttributes) {
        orderRecordsService.save(orderRecords);
        addMessage(redirectAttributes,"新增成功");
        return "redirect:"+ adminPath +"/order/orderRecords/update?id="+orderRecords.getId();
    }

    @RequestMapping(value = "/update", method = RequestMethod.GET)
    public String update(OrderRecords orderRecords, Model model) {
        model.addAttribute("orderRecords", orderRecords);
        return "order/orderRecords/form";
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String update(OrderRecords orderRecords, RedirectAttributes redirectAttributes) {
        orderRecordsService.save(orderRecords);
        addMessage(redirectAttributes,"修改成功");
        return "redirect:"+ adminPath +"/order/orderRecords/update?id="+orderRecords.getId();
    }

    @RequestMapping(value = "/{id}/delete", method = RequestMethod.GET)
    public String delete(@PathVariable("id") String id,int pageNo,int pageSize, RedirectAttributes redirectAttributes) {
        orderRecordsService.delete(id);
        addMessage(redirectAttributes,"删除成功");
        return "redirect:"+ adminPath +"/order/orderRecords?pageNo="+pageNo+"&pageSize="+pageSize;
    }
}
