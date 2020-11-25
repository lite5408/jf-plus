package com.jf.plus.core.supplyer.controller;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.jf.plus.common.core.enums.OrderAuditStatus;
import com.jf.plus.common.core.enums.OrderTrackState;
import com.jf.plus.core.order.entity.Order;
import com.jf.plus.core.order.entity.OrderDistrib;
import com.jf.plus.core.order.service.OrderDistribService;
import com.jf.plus.core.order.service.OrderService;

import cn.iutils.common.BaseController;
import cn.iutils.common.Page;
import cn.iutils.common.utils.DateUtils;
import cn.iutils.common.utils.UserUtils;

/**
 * 供应商订单 控制器
 *
 * @author Tng
 * @version 1.0
 */
@Controller("supplyOrderController")
@RequestMapping("${supplyPath}/order")
public class OrderController extends BaseController {

	private static Logger LOGGER = LoggerFactory.getLogger(OrderController.class);

	@Autowired
	private OrderService orderService;

	@Autowired
	private OrderDistribService orderDistribService;

	@RequestMapping(value = { "/list", "" })
	public String list(Model model, Order order, Page<Order> page) {
		order.setSupplyId(Long.valueOf(UserUtils.getSupplyUser().getSupplyId()));
		page.setEntity(order);

		if (order.getOperStatus() == null) {
			page.getCondition().put("operStatusLike", " ( a.oper_status in (" + OrderAuditStatus.FINISH.getType() + ","
					+ OrderAuditStatus.TORECEIVE.getType() + ") ) ");
		}

		model.addAttribute("page", page.setList(orderService.findPageSupplyOrder(page)));
		return "/mall/mallSupplyer/order/list";
	}

	@RequestMapping(value = "/delivered", method = RequestMethod.GET)
	public String delivered(Model model, String orderId) {
		model.addAttribute("orderId", orderId);
		return "/mall/mallSupplyer/order/delivered";
	}

	@RequestMapping(value = "/delivered", method = RequestMethod.POST)
	public String delivered(OrderDistrib orderDistrib, RedirectAttributes redirectAttributes) {

		try {
			Order order = orderService.get(orderDistrib.getOrderId().toString());
			order.setTrackState(OrderTrackState.DELIVERED.getType());
			order.setUpdateDate(new Date());
			orderService.save(order);

			orderDistrib.setCreateBy("0");
			orderDistrib.setCreateDate(new Date());
			orderDistribService.save(orderDistrib);
			addMessage(redirectAttributes, "发货成功");
		} catch (Exception e) {
			addMessage(redirectAttributes, "操作失败");
			LOGGER.error("供应商发货失败：", e);
		}

		return "redirect:" + supplyPath + "/order/list";
	}

	@RequestMapping(value = "/track", method = RequestMethod.GET)
	public String track(Long orderId, Model model) {
		OrderDistrib orderDistrib = new OrderDistrib();
		orderDistrib.setOrderId(orderId);
		orderDistrib = orderDistribService.getEntity(orderDistrib);
		model.addAttribute("orderDistrib", orderDistrib);
		return "/mall/mallSupplyer/order/track";
	}

	@RequestMapping("/confirm")
	public String confirm(String orderId, Model model) {
		Order order = orderService.get(orderId);
		model.addAttribute("order", order);
		return "/mall/mallSupplyer/order/confirm";
	}

	@RequestMapping(value = "/report")
	public String reportList(Model model, Order order, String query, Page<Order> page) {
		try {
			if (order.getStartTime() != null || order.getEndTime() != null) {
				page.setEntity(order);
				if (StringUtils.isBlank(query) || "jod.id".equals(query))
					query = "jod.id";
				else
					page.setOrderBy(query);
				page.getCondition().put("groupBy", query);

				if (order.getOperStatus() == null) {
					page.getCondition().put("operStatusLike", " ( a.oper_status in (" + OrderAuditStatus.FINISH.getType()
					+ "," + OrderAuditStatus.TORECEIVE.getType() + ") ) ");
				}
				model.addAttribute("page", page.setList(orderService.findReportList(page)));
				model.addAttribute("query", query);
			}
			model.addAttribute("supplyId", UserUtils.getSupplyUser().getSupplyId());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "/mall/mallSupplyer/report/list";
	}

	/**
	 * 订单报表导出
	 *
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/report/excel")
	public void reportExcel(HttpServletRequest request, HttpServletResponse response, Order order, String startTime,
			String endTime, Long supplyId, String query, Page<Order> page) {
		try {
			String[] excelHeader = new String[6];
			List<String> title = new ArrayList<>();
			if (query.contains("jod.id")) {
				title.add("订单编号");
				title.add("下单人");
				title.add("商品编码");
				title.add("商品名称");
				title.add("销售单价");
			}
			title.add("供应商");
			if (query.contains("org.`name`")) {
				title.add("机构");
			}
			title.add("销售数量");
			title.add("销售总金额");
			excelHeader = title.toArray(excelHeader);

			HSSFWorkbook wb = new HSSFWorkbook();
			HSSFSheet sheet = wb.createSheet("订单报表信息");
			HSSFRow row = sheet.createRow(0);
			HSSFCellStyle style = wb.createCellStyle();
			style.setAlignment(HSSFCellStyle.ALIGN_CENTER);

			for (int i = 0; i < excelHeader.length; i++) {
				HSSFCell cell = row.createCell(i);
				cell.setCellValue(excelHeader[i]);
				cell.setCellStyle(style);
				sheet.setColumnWidth(i, 7000);
			}

			page.setPageSize(65536 - 1);

			if (StringUtils.isNotBlank(startTime) || StringUtils.isNotBlank(endTime)) {
				order.setSupplyId(supplyId);
				order.setStartTime(DateUtils.parseDate(startTime));
				order.setEndTime(DateUtils.parseDate(endTime));
				page.setEntity(order);
				if (StringUtils.isBlank(query) || "jod.id".equals(query))
					query = "jod.id";
				else
					page.setOrderBy(query);
				page.getCondition().put("groupBy", query);

				if (order.getOperStatus() == null) {
					page.getCondition().put("operStatusLike", " ( a.oper_status in ("
							+ OrderAuditStatus.FINISH.getType() + "," + OrderAuditStatus.TORECEIVE.getType() + ") ) ");
				}

				List<Order> dataList = orderService.findReportList(page);

				for (int i = 0; i < dataList.size(); i++) {
					row = sheet.createRow(i + 1);
					order = dataList.get(i);
					int j = 0;
					if (query.contains("jod.id")) {
						row.createCell(j).setCellValue(order.getOrderNo());
						j++;
						row.createCell(j).setCellValue(order.getUserName());
						j++;
						row.createCell(j).setCellValue(order.getItemCode());
						j++;
						row.createCell(j).setCellValue(order.getItemName());
						j++;
						row.createCell(j).setCellValue(order.getSalePrice());
						j++;
					}
					row.createCell(j).setCellValue(order.getSupplyName());
					j++;
					if (query.contains("org.`name`")) {
						row.createCell(j).setCellValue(order.getOrgName());
						j++;
					}
					row.createCell(j).setCellValue(order.getTotalNum());
					j++;
					row.createCell(j).setCellValue(order.getTotalAmount());

				}
			}
			response.setContentType("application/vnd.ms-excel");
			response.setHeader("Content-disposition",
					"attachment;filename=" + new String("订单报表信息.xls".getBytes("utf-8"), "ISO-8859-1"));
			OutputStream ouputStream = response.getOutputStream();
			wb.write(ouputStream);
			ouputStream.flush();
			ouputStream.close();
		} catch (Exception e) {
			LOGGER.error("订单报表导出异常:{}", e);
		}
	}

}
