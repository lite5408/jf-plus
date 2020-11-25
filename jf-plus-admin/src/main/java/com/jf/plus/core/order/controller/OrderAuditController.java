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

import com.jf.plus.common.core.enums.OrgType;
import com.jf.plus.core.order.entity.OrderAudit;
import com.jf.plus.core.order.entity.OrderDetail;
import com.jf.plus.core.order.service.OrderAuditService;
import com.jf.plus.core.order.service.OrderDetailService;

import cn.iutils.common.BaseController;
import cn.iutils.common.DataScope;
import cn.iutils.common.Page;
import cn.iutils.common.utils.JStringUtils;
import cn.iutils.common.utils.UserUtils;
import cn.iutils.sys.entity.Organization;
import cn.iutils.sys.entity.enums.DataScopeEnum;

/**
 * 订单审核表 控制器
 * 
 * @author Tng
 * @version 1.0
 */
@Controller
@RequestMapping("${adminPath}/order/orderAudit")
public class OrderAuditController extends BaseController {

	@Autowired
	private OrderAuditService orderAuditService;
	
	@Autowired
	private OrderDetailService orderDetailService;

	@ModelAttribute
	public OrderAudit get(@RequestParam(required = false) String id) {
		OrderAudit entity = null;
		if (JStringUtils.isNotBlank(id)) {
			entity = orderAuditService.get(id);
		}
		if (entity == null) {
			entity = new OrderAudit();
		}
		return entity;
	}

	@RequestMapping(value = "/list")
	public String list(Model model, Integer operStatus, OrderAudit orderAudit, Page<OrderAudit> page) {
		orderAudit.setUser(UserUtils.getLoginUser());
		orderAudit.setDataScopeFilter(new DataScope(UserUtils.getLoginUser(), DataScopeEnum.orgFollow, "org", "u"));
		page.setEntity(orderAudit);
		page.getCondition().put("operStatus", convertStatus(operStatus));
		model.addAttribute("page", page.setList(orderAuditService.findAuditPage(page)));
		model.addAttribute("status", operStatus);
		if(operStatus == 2 || operStatus == 21 || operStatus == 22){
			return "order/orderAudit/history";
		}
		return "order/orderAudit/list";
	}

	/**
	 * 转换状态
	 * 
	 * @param operStatus
	 * @return
	 */
	private String convertStatus(int operStatus) {
		Organization organization = UserUtils.getLoginUser().getOrganization();
		OrgType orgType = OrgType.getByType(organization.getType());
		switch (orgType) {
		case COMP_TX:
			if (operStatus == 1) {
				return " (a.audit_status = 12) ";
			}

			if (operStatus == 2) {
				return " (a.audit_status LIKE '2%') ";
			}
			
			if(operStatus == 21){
				return " (a.audit_status = 21) ";
			}
			
			if(operStatus == 22){
				return " (a.audit_status = 22 or a.audit_status = 23) ";
			}
		default:
			return "";
		}

	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public String create(OrderAudit orderAudit, Model model) {
		model.addAttribute("orderAudit", orderAudit);
		return "order/orderAudit/form";
	}

	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public String create(OrderAudit orderAudit, RedirectAttributes redirectAttributes) {
		orderAuditService.save(orderAudit);
		addMessage(redirectAttributes, "新增成功");
		return "redirect:" + adminPath + "/order/orderAudit/update?id=" + orderAudit.getId();
	}

	@RequestMapping(value = "/update", method = RequestMethod.GET)
	public String update(OrderAudit orderAudit, Model model) {
		model.addAttribute("orderAudit", orderAudit);
		return "order/orderAudit/form";
	}

	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public String update(OrderAudit orderAudit, RedirectAttributes redirectAttributes) {
		orderAuditService.save(orderAudit);
		addMessage(redirectAttributes, "修改成功");
		return "redirect:" + adminPath + "/order/orderAudit/update?id=" + orderAudit.getId();
	}

	@RequestMapping(value = "/{id}/delete", method = RequestMethod.GET)
	public String delete(@PathVariable("id") String id, int pageNo, int pageSize,
			RedirectAttributes redirectAttributes) {
		orderAuditService.delete(id);
		addMessage(redirectAttributes, "删除成功");
		return "redirect:" + adminPath + "/order/orderAudit?pageNo=" + pageNo + "&pageSize=" + pageSize;
	}
	
	
	@RequestMapping(value = "/myProductList")
	public String myProductList(OrderDetail orderDetail,Model model){
		model.addAttribute("orderDetailList", orderDetailService.findList(orderDetail));
		return "order/orderAudit/myProductList";
	}
}
