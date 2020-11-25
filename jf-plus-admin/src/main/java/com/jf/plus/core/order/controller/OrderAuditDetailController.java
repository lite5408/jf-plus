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
import com.jf.plus.core.order.entity.OrderAuditDetail;
import com.jf.plus.core.order.service.OrderAuditDetailService;

/**
* 审核单明细 控制器
* @author Tng
* @version 1.0
*/
@Controller
@RequestMapping("${adminPath}/order/orderAuditDetail")
public class OrderAuditDetailController extends BaseController {

    @Autowired
    private OrderAuditDetailService orderAuditDetailService;

    @ModelAttribute
    public OrderAuditDetail get(@RequestParam(required = false) String id) {
        OrderAuditDetail entity = null;
        if (JStringUtils.isNotBlank(id)) {
            entity = orderAuditDetailService.get(id);
        }
        if (entity == null) {
            entity = new OrderAuditDetail();
        }
        return entity;
    }

    @RequestMapping(value="/list")
    public String list(Model model, Page<OrderAuditDetail> page) {
        model.addAttribute("page", page.setList(orderAuditDetailService.findPage(page)));
        return "order/orderAuditDetail/list";
    }

    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public String create(OrderAuditDetail orderAuditDetail,Model model) {
        model.addAttribute("orderAuditDetail", orderAuditDetail);
        return "order/orderAuditDetail/form";
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String create(OrderAuditDetail orderAuditDetail, RedirectAttributes redirectAttributes) {
        orderAuditDetailService.save(orderAuditDetail);
        addMessage(redirectAttributes,"新增成功");
        return "redirect:"+ adminPath +"/order/orderAuditDetail/update?id="+orderAuditDetail.getId();
    }

    @RequestMapping(value = "/update", method = RequestMethod.GET)
    public String update(OrderAuditDetail orderAuditDetail, Model model) {
        model.addAttribute("orderAuditDetail", orderAuditDetail);
        return "order/orderAuditDetail/form";
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String update(OrderAuditDetail orderAuditDetail, RedirectAttributes redirectAttributes) {
        orderAuditDetailService.save(orderAuditDetail);
        addMessage(redirectAttributes,"修改成功");
        return "redirect:"+ adminPath +"/order/orderAuditDetail/update?id="+orderAuditDetail.getId();
    }

    @RequestMapping(value = "/{id}/delete", method = RequestMethod.GET)
    public String delete(@PathVariable("id") String id,int pageNo,int pageSize, RedirectAttributes redirectAttributes) {
        orderAuditDetailService.delete(id);
        addMessage(redirectAttributes,"删除成功");
        return "redirect:"+ adminPath +"/order/orderAuditDetail?pageNo="+pageNo+"&pageSize="+pageSize;
    }
}
