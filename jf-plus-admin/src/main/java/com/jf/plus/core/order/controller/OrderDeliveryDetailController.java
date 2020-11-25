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
import com.jf.plus.core.order.entity.OrderDeliveryDetail;
import com.jf.plus.core.order.service.OrderDeliveryDetailService;

/**
* 订单发货商品表 控制器
* @author Tng
* @version 1.0
*/
@Controller
@RequestMapping("${adminPath}/order/orderDeliveryDetail")
public class OrderDeliveryDetailController extends BaseController {

    @Autowired
    private OrderDeliveryDetailService orderDeliveryDetailService;

    @ModelAttribute
    public OrderDeliveryDetail get(@RequestParam(required = false) String id) {
        OrderDeliveryDetail entity = null;
        if (JStringUtils.isNotBlank(id)) {
            entity = orderDeliveryDetailService.get(id);
        }
        if (entity == null) {
            entity = new OrderDeliveryDetail();
        }
        return entity;
    }

    @RequestMapping(value={"/list",""})
    public String list(Model model, Page<OrderDeliveryDetail> page) {
        model.addAttribute("page", page.setList(orderDeliveryDetailService.findPage(page)));
        return "order/orderDeliveryDetail/list";
    }

    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public String create(OrderDeliveryDetail orderDeliveryDetail,Model model) {
        model.addAttribute("orderDeliveryDetail", orderDeliveryDetail);
        return "order/orderDeliveryDetail/form";
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String create(OrderDeliveryDetail orderDeliveryDetail, RedirectAttributes redirectAttributes) {
        orderDeliveryDetailService.save(orderDeliveryDetail);
        addMessage(redirectAttributes,"新增成功");
        return "redirect:"+ adminPath +"/order/orderDeliveryDetail/update?id="+orderDeliveryDetail.getId();
    }

    @RequestMapping(value = "/update", method = RequestMethod.GET)
    public String update(OrderDeliveryDetail orderDeliveryDetail, Model model) {
        model.addAttribute("orderDeliveryDetail", orderDeliveryDetail);
        return "order/orderDeliveryDetail/form";
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String update(OrderDeliveryDetail orderDeliveryDetail, RedirectAttributes redirectAttributes) {
        orderDeliveryDetailService.save(orderDeliveryDetail);
        addMessage(redirectAttributes,"修改成功");
        return "redirect:"+ adminPath +"/order/orderDeliveryDetail/update?id="+orderDeliveryDetail.getId();
    }

    @RequestMapping(value = "/{id}/delete", method = RequestMethod.GET)
    public String delete(@PathVariable("id") String id,int pageNo,int pageSize, RedirectAttributes redirectAttributes) {
        orderDeliveryDetailService.delete(id);
        addMessage(redirectAttributes,"删除成功");
        return "redirect:"+ adminPath +"/order/orderDeliveryDetail?pageNo="+pageNo+"&pageSize="+pageSize;
    }
}
