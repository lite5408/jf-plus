package com.jf.plus.api.buyer.mobile.export.controller;

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
import org.springframework.web.bind.annotation.RequestParam;

import com.jf.plus.common.core.Result;
import com.jf.plus.common.core.enums.OrderAuditStatus;
import com.jf.plus.core.order.entity.Order;
import com.jf.plus.core.order.service.OrderDeliveryExportService;
import com.jf.plus.core.order.service.OrderService;
import com.jf.plus.core.product.entity.Product;
import com.jf.plus.core.product.service.ProductService;

import cn.iutils.common.Page;
import cn.iutils.common.utils.DateUtils;
import cn.iutils.sys.entity.User;
import cn.iutils.sys.service.UserService;

/**
 * 导出控制层
 *
 * @author Tng
 *
 */
@RequestMapping("/api/buyer/export/")
@Controller("buyerExportExcelController")
public class ExportExcelController extends com.jf.plus.api.mobile.controller.BaseController {

	private final static Logger LOGGER = LoggerFactory.getLogger(ExportExcelController.class);

	@Autowired
	OrderDeliveryExportService orderDeliveryExportService;
	
	@Autowired
	OrderService orderService;
	
	@Autowired
	ProductService productService;
	
	@Autowired
	UserService userSerivce;

	/**
	 * 商品订单导出
	 *
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/productOrder")
	public void productOrder(HttpServletRequest request, HttpServletResponse response, @RequestParam String token, @RequestParam String productId, Page<Order> page) {
		try {
			
			Result result = appcodeService.checkToken(token);
			String userId = result.getObj().toString();
			User buyer = userSerivce.get(userId);
			
			String[] excelHeader = new String[] { "代理商","收货地址","联系电话","供应商","品牌","商品名称","颜色","尺码","数量","下单日期","买手"};

			HSSFWorkbook wb = new HSSFWorkbook();
			HSSFSheet sheet = wb.createSheet("商品订单导出");
			HSSFRow row = sheet.createRow(0);
			HSSFCellStyle style = wb.createCellStyle();
			style.setAlignment(HSSFCellStyle.ALIGN_CENTER);

			for (int i = 0; i < excelHeader.length; i++) {
				HSSFCell cell = row.createCell(i);
				cell.setCellValue(excelHeader[i]);
				cell.setCellStyle(style);
				sheet.setColumnWidth(i, 7000);
			}

			page.getCondition().put("buyerId", userId);
			page.getCondition().put("status", "1");

			page.getCondition().put("productId", productId);
			if (productId != null) {
				page.getCondition().put("operStatusLike",
						"(" + OrderAuditStatus.TORECEIVE.getType() + "," + OrderAuditStatus.MERGE.getType() + "," + OrderAuditStatus.FINISH.getType() + ")");
			}
			
			Product product = productService.get(productId);
			page.setPageSize(Integer.MAX_VALUE);
			page.setList(orderService.findPageBuyerSpecOrder(page));
			List<Order> dataList = page.getList();
			

			for (int i = 0; i < dataList.size(); i++) {
				row = sheet.createRow(i + 1);
				Order entity = dataList.get(i);
				int j = 0;
				row.createCell(j).setCellValue(nullToStr(entity.getOrgName()));
				j++;
				row.createCell(j).setCellValue(nullToStr(entity.getAddress()));
				j++;
				row.createCell(j).setCellValue(nullToStr(entity.getReceiverPhone()));
				j++;
				row.createCell(j).setCellValue(nullToStr(entity.getSupplyName()));
				j++;
				row.createCell(j).setCellValue(nullToStr(product.getBrandName()));
				j++;
				row.createCell(j).setCellValue(nullToStr(product.getItemName()));
				j++;
				row.createCell(j).setCellValue(nullToStr(entity.getSpecColorText()));
				j++;
				row.createCell(j).setCellValue(nullToStr(entity.getSpecSizeText()));
				j++;
				row.createCell(j).setCellValue(nullToStr(entity.getTotalNum()));
				j++;
				row.createCell(j).setCellValue(DateUtils.formatDateTime(entity.getCashtime()));
				j++;
				row.createCell(j).setCellValue(nullToStr(product.getBuyerName()));
			}
			response.setContentType("application/vnd.ms-excel");
			response.setHeader("Content-disposition",
					"attachment;filename=" + new String((buyer.getName()+"_"+product.getItemName()+"_"+ DateUtils.getDate("yyyy-MM-dd HH:mm")+"的订单.xls").getBytes("utf-8"), "ISO-8859-1"));
			OutputStream ouputStream = response.getOutputStream();
			wb.write(ouputStream);
			ouputStream.flush();
			ouputStream.close();
		} catch (Exception e) {
			LOGGER.error("商品订单导出信息异常:{}", e);
		}
	}
	
}