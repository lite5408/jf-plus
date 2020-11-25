package com.jf.plus.api.mobile.export.controller;

import java.io.OutputStream;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
import org.springframework.web.bind.annotation.RequestParam;

import com.google.common.base.Joiner;
import com.jf.plus.core.order.entity.OrderDeliveryExport;
import com.jf.plus.core.order.service.OrderDeliveryExportService;

/**
 * 导出控制层
 *
 * @author Tng
 *
 */
@RequestMapping("/api/export")
@Controller
public class ExportExcelController extends com.jf.plus.api.mobile.controller.BaseController {

	private final static Logger LOGGER = LoggerFactory.getLogger(ExportExcelController.class);

	@Autowired
	OrderDeliveryExportService orderDeliveryExportService;

	/**
	 * 商品订单导出
	 *
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/delivery")
	public void deliveryExcel(HttpServletRequest request, HttpServletResponse response, @RequestParam String token, @RequestParam String deliveryId) {
		try {
			
			String[] excelHeader = new String[] { "商品编码", "名称", "颜色", "尺码" ,"数量", "价格"};

			HSSFWorkbook wb = new HSSFWorkbook();
			HSSFSheet sheet = wb.createSheet("sheet1");
			HSSFRow row = sheet.createRow(0);
			HSSFCellStyle style = wb.createCellStyle();
			style.setAlignment(HSSFCellStyle.ALIGN_CENTER);

			for (int i = 0; i < excelHeader.length; i++) {
				HSSFCell cell = row.createCell(i);
				cell.setCellValue(excelHeader[i]);
				cell.setCellStyle(style);
				sheet.setColumnWidth(i, 7000);
			}

			OrderDeliveryExport entity = new OrderDeliveryExport();
			entity.setDeliveryId(Long.valueOf(deliveryId));
			entity.setStatus("1");
			List<OrderDeliveryExport> dataList = orderDeliveryExportService.findList(entity);
			
			Set<String> names = new HashSet<>();
			for (int i = 0; i < dataList.size(); i++) {
				row = sheet.createRow(i + 1);
				entity = dataList.get(i);
				int j = 0;
				row.createCell(j).setCellValue(nullToStr(entity.getItemCode()));
				j++;
				row.createCell(j).setCellValue(nullToStr(entity.getItemName()));
				names.add(nullToStr(entity.getItemName()));
				j++;
				row.createCell(j).setCellValue(nullToStr(entity.getSpecColorText()));
				j++;
				row.createCell(j).setCellValue(nullToStr(entity.getSpecSizeText()));
				j++;
				row.createCell(j).setCellValue(nullToInt(entity.getDeliveryNum()));
				j++;
				row.createCell(j).setCellValue(nullToStr(entity.getExportPrice()));
			}
			response.setContentType("application/vnd.ms-excel");
			response.setHeader("Content-disposition",
					"attachment;filename=" + new String((Joiner.on("+").join(names)+".xls").getBytes("utf-8"), "ISO-8859-1"));
			OutputStream ouputStream = response.getOutputStream();
			wb.write(ouputStream);
			ouputStream.flush();
			ouputStream.close();
		} catch (Exception e) {
			LOGGER.error("商品导出信息异常:{}", e);
		}
	}
	
}