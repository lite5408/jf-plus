package com.jf.plus.core.site.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.jf.plus.common.core.enums.OrderAuditStatus;
import com.jf.plus.common.core.enums.OrderSource;
import com.jf.plus.common.vo.SiteVo;
import com.jf.plus.core.order.entity.Order;
import com.jf.plus.core.order.service.OrderService;
import com.jf.plus.core.site.entity.SiteOrg;
import com.jf.plus.core.site.service.SiteOrgService;

import cn.iutils.common.BaseController;
import cn.iutils.common.Page;
import cn.iutils.common.utils.JStringUtils;
import cn.iutils.common.utils.UserUtils;
import cn.iutils.sys.entity.User;
import cn.iutils.sys.service.UserService;

/**
 * 订单主表 控制器
 * @author Tng
 * @version 1.0
 */
@Controller
@RequestMapping("${adminPath}/site/order")
public class SiteOrderController extends BaseController {

	@Autowired
	private OrderService orderService;

	@Autowired
	private UserService userService;

	@Autowired
	private SiteOrgService siteOrgService;

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
	public String list(Model model,Order order, Page<Order> page) {
		if(UserUtils.getMySite() == null){
			model.addAttribute("errorMsg", "您没有管理站点权限，请联系管理员");
			return "error/400";
		}
		order.setOrderFrom(OrderSource.JC.getType());
		order.setSiteId(Long.valueOf(UserUtils.getMySite().getSiteId()));
		page.setEntity(order);

		if (order.getOperStatus() == null) {
			page.getCondition().put("operStatusLike", " ( a.oper_status in (" + OrderAuditStatus.FINISH.getType() + ","
					+ OrderAuditStatus.TORECEIVE.getType() + ") ) ");
		}

		model.addAttribute("page", page.setList(orderService.findPageSiteOrder(page)));
		return "site/siteOrder/list";
	}

	@RequestMapping(value={"/jf/list",""})
	public String jfList(Model model,Order order, Page<Order> page) {
		if(UserUtils.getMySite() == null){
			model.addAttribute("errorMsg", "您没有管理站点权限，请联系管理员");
			return "error/400";
		}
		order.setOrderFrom(OrderSource.JF.getType());
		order.setSiteId(Long.valueOf(UserUtils.getMySite().getSiteId()));
		page.setEntity(order);

		if (order.getOperStatus() == null) {
			page.getCondition().put("operStatusLike", " ( a.oper_status in (" + OrderAuditStatus.FINISH.getType() + ","
					+ OrderAuditStatus.TORECEIVE.getType() + ") ) ");
		}

		model.addAttribute("page", page.setList(orderService.findPageSiteOrder(page)));
		return "site/siteOrder/jfList";
	}

}
