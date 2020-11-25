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
import com.jf.plus.core.order.entity.OrderDetail;
import com.jf.plus.core.order.service.OrderDetailService;

/**
* 订单明细表 控制器
* @author Tng
* @version 1.0
*/
@Controller
@RequestMapping("${adminPath}/order/orderDetail")
public class OrderDetailController extends BaseController {

    @Autowired
    private OrderDetailService orderDetailService;

    @ModelAttribute
    public OrderDetail get(@RequestParam(required = false) String id) {
        OrderDetail entity = null;
        if (JStringUtils.isNotBlank(id)) {
            entity = orderDetailService.get(id);
        }
        if (entity == null) {
            entity = new OrderDetail();
        }
        return entity;
    }

    @RequestMapping(value="/list")
    public String list(Model model, Page<OrderDetail> page) {
        model.addAttribute("page", page.setList(orderDetailService.findPage(page)));
        return "order/orderDetail/list";
    }

    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public String create(OrderDetail orderDetail,Model model) {
        model.addAttribute("orderDetail", orderDetail);
        return "order/orderDetail/form";
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String create(OrderDetail orderDetail, RedirectAttributes redirectAttributes) {
        orderDetailService.save(orderDetail);
        addMessage(redirectAttributes,"新增成功");
        return "redirect:"+ adminPath +"/order/orderDetail/update?id="+orderDetail.getId();
    }

    @RequestMapping(value = "/update", method = RequestMethod.GET)
    public String update(OrderDetail orderDetail, Model model) {
        model.addAttribute("orderDetail", orderDetail);
        return "order/orderDetail/form";
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String update(OrderDetail orderDetail, RedirectAttributes redirectAttributes) {
        orderDetailService.save(orderDetail);
        addMessage(redirectAttributes,"修改成功");
        return "redirect:"+ adminPath +"/order/orderDetail/update?id="+orderDetail.getId();
    }

    @RequestMapping(value = "/{id}/delete", method = RequestMethod.GET)
    public String delete(@PathVariable("id") String id,int pageNo,int pageSize, RedirectAttributes redirectAttributes) {
        orderDetailService.delete(id);
        addMessage(redirectAttributes,"删除成功");
        return "redirect:"+ adminPath +"/order/orderDetail?pageNo="+pageNo+"&pageSize="+pageSize;
    }
}
