package com.jf.plus.core.supplyer.controller;

import java.io.OutputStream;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.jf.plus.common.core.enums.OrderAuditStatus;
import com.jf.plus.core.order.entity.Order;
import com.jf.plus.core.order.service.OrderService;

import cn.iutils.common.BaseController;
import cn.iutils.common.Page;
import cn.iutils.common.utils.DateUtils;
import cn.iutils.common.utils.UserUtils;

/**
 * 导出控制层
 * 
 * @author Tng
 *
 */
@RequestMapping("${supplyPath}/export")
@Controller("supplyExportExcelController")
public class ExportExcelController extends BaseController {

	private final static Logger LOGGER = LoggerFactory.getLogger(ExportExcelController.class);

	@Autowired
	private OrderService orderService;

		/**
	 * 站点订单导出
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/order/excel")
	public void orderExcel(HttpServletRequest request, HttpServletResponse response, Order order, Page<Order> page) {
		try {
			String[] excelHeader = new String[] {
					"订单号", 
					"商品信息", 
					"数量", 
					"订单总价", 
					"下单时间", 
					"收货人", 
					"收货人号码", 
					"收货地址", 
					"状态"
			};
			
			HSSFWorkbook wb = new HSSFWorkbook();
			HSSFSheet sheet = wb.createSheet("订单信息");
			HSSFRow row = sheet.createRow((int) 0);
			HSSFCellStyle style = wb.createCellStyle();
			style.setAlignment(HSSFCellStyle.ALIGN_CENTER);

			for (int i = 0; i < excelHeader.length; i++) {
				HSSFCell cell = row.createCell(i);
				cell.setCellValue(excelHeader[i]);
				cell.setCellStyle(style);
				sheet.setColumnWidth(i, 7000);
			}

			page.setPageSize(65536 - 1);

			order.setSupplyId(Long.valueOf(UserUtils.getSupplyUser().getSupplyId()));
			page.setEntity(order);
			
			if (order.getOperStatus() == null) {
				page.getCondition().put("operStatusLike", " ( a.oper_status in (" + OrderAuditStatus.FINISH.getType() + ","
						+ OrderAuditStatus.TORECEIVE.getType() + ") ) ");
			}

			List<Order> dataList = orderService.findPageSupplyOrder(page);
			for (int i = 0; i < dataList.size(); i++) {
				row = sheet.createRow(i + 1);
				int j = 0;
				order = dataList.get(i);
				row.createCell(j).setCellValue(order.getOrderNo());
				j++;
				row.createCell(j).setCellValue(order.getItemName());
				j++;
				row.createCell(j).setCellValue(order.getTotalNum());
				j++;
				row.createCell(j).setCellValue(order.getPayAmount());
				j++;
				row.createCell(j).setCellValue(DateUtils.formatDateTime(order.getCashtime()));
				j++;
				row.createCell(j).setCellValue(order.getReceiver());
				j++;
				row.createCell(j).setCellValue(order.getReceiverPhone());
				j++;
				row.createCell(j).setCellValue(order.getAddress());
				j++;
				row.createCell(j).setCellValue(OrderAuditStatus.getByType(order.getOperStatus()).getDescription());

			}
			response.setContentType("application/vnd.ms-excel");
			response.setHeader("Content-disposition",
					"attachment;filename=" + new String("订单信息.xls".getBytes("utf-8"), "ISO-8859-1"));
			OutputStream ouputStream = response.getOutputStream();
			wb.write(ouputStream);
			ouputStream.flush();
			ouputStream.close();
		} catch (Exception e) {
			LOGGER.error("订单导出异常:{}", e);
		}
	}

}