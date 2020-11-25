package com.jf.plus.core.excel.controller;

import java.io.OutputStream;
import java.util.ArrayList;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.google.common.base.Joiner;
import com.jf.plus.common.core.enums.OrderAuditStatus;
import com.jf.plus.common.core.enums.SaleType;
import com.jf.plus.core.order.entity.Order;
import com.jf.plus.core.order.entity.OrderDeliveryExport;
import com.jf.plus.core.order.entity.OrderDetail;
import com.jf.plus.core.order.entity.OrderDetailSku;
import com.jf.plus.core.order.service.OrderDeliveryExportService;
import com.jf.plus.core.order.service.OrderDetailService;
import com.jf.plus.core.order.service.OrderDetailSkuService;
import com.jf.plus.core.order.service.OrderService;
import com.jf.plus.core.site.entity.SiteProduct;
import com.jf.plus.core.site.service.SiteProductService;

import cn.iutils.common.BaseController;
import cn.iutils.common.Page;
import cn.iutils.common.utils.DateUtils;

/**
 * 导出控制层
 *
 * @author Tng
 *
 */
@RequestMapping("${adminPath}/export")
@Controller
public class ExportExcelController extends BaseController {

	private final static Logger LOGGER = LoggerFactory.getLogger(ExportExcelController.class);
	
	@Autowired
	private OrderDetailService orderDetailService;
	
	@Autowired
	private SiteProductService siteProductService;
	
	@Autowired
	OrderDetailSkuService orderDetailSkuService;
	
	@Autowired
	OrderDeliveryExportService orderDeliveryExportService;
	
	@Autowired
	OrderService orderService;

	
	/**
	 * 正价商品导出
	 * @param request
	 * @param response
	 * @param orderDetail
	 * @param page
	 * @throws Exception
	 */
	private void distOrderListExcelZj(HttpServletRequest request, HttpServletResponse response, OrderDetail orderDetail, Page<OrderDetail> page) throws Exception{
		SiteProduct siteProduct = siteProductService.get(orderDetail.getItemId()+"");
		String[] excelHeader = new String[] { "商品编码","商品名称","代理商","颜色","尺寸","订货数量","订单号","备注"};
		
		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFSheet sheet = wb.createSheet("订单列表");
		HSSFRow row = sheet.createRow(0);
		HSSFCellStyle style = wb.createCellStyle();
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);

		for (int i = 0; i < excelHeader.length; i++) {
			HSSFCell cell = row.createCell(i);
			cell.setCellValue(excelHeader[i]);
			cell.setCellStyle(style);
			sheet.setColumnWidth(i, 7000);
		}

		Page<OrderDetailSku> pageSku = new Page<>();
		OrderDetailSku orderDetailSku = new OrderDetailSku();
		orderDetailSku.setItemId(orderDetail.getItemId());
		orderDetailSku.setBatchNo(orderDetail.getBatchNo());
		orderDetailSku.setIsDist(orderDetail.getIsDist());
		pageSku.setEntity(orderDetailSku );
		
		pageSku.setList(orderDetailSkuService.findDistSkuList(pageSku));
		
		List<OrderDetailSku> dataList = pageSku.getList();
		

		for (int i = 0; i < dataList.size(); i++) {
			row = sheet.createRow(i + 1);
			OrderDetailSku entity = dataList.get(i);
			int j = 0;
			row.createCell(j).setCellValue(nullToStr(siteProduct.getItemCode()));
			j++;
			row.createCell(j).setCellValue(nullToStr(siteProduct.getItemName()));
			j++;
			row.createCell(j).setCellValue(nullToStr(entity.getOrgName()));
			j++;
			row.createCell(j).setCellValue(nullToStr(entity.getSpecColorText()));
			j++;
			row.createCell(j).setCellValue(nullToStr(entity.getSpecSizeText()));
			j++;
			row.createCell(j).setCellValue(nullToInt(entity.getSaleNum()));
			j++;
			row.createCell(j).setCellValue(nullToStr(entity.getOrderNo()));
			j++;
			row.createCell(j).setCellValue(filterStr(nullToStr(entity.getRemarks())));
		}
		response.setContentType("application/vnd.ms-excel");
		response.setHeader("Content-disposition",
				"attachment;filename=" + new String((siteProduct.getBuyerName()+"_"+siteProduct.getItemName() + "的订单.xls").getBytes("utf-8"), "ISO-8859-1"));
		OutputStream ouputStream = response.getOutputStream();
		wb.write(ouputStream);
		ouputStream.flush();
		ouputStream.close();
	}
	
	
	/**
	 * 商品订单导出
	 *
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/distOrderList")
	public void distOrderListExcel(HttpServletRequest request, HttpServletResponse response, OrderDetail orderDetail, Page<OrderDetail> page) {
		try {
			
			SiteProduct siteProduct = siteProductService.get(orderDetail.getItemId()+"");
			if(SaleType.ZJ.getDescription().equals(siteProduct.getSaleType())){
				distOrderListExcelZj(request,response,orderDetail,page);
			}else{
				String[] excelHeader = new String[] { "商品编码","商品名称","代理商","颜色","尺寸","订货数量","订单号","备注"};
	
				HSSFWorkbook wb = new HSSFWorkbook();
				HSSFSheet sheet = wb.createSheet("订单列表");
				HSSFRow row = sheet.createRow(0);
				HSSFCellStyle style = wb.createCellStyle();
				style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
	
				for (int i = 0; i < excelHeader.length; i++) {
					HSSFCell cell = row.createCell(i);
					cell.setCellValue(excelHeader[i]);
					cell.setCellStyle(style);
					sheet.setColumnWidth(i, 7000);
				}
	
				page.setEntity(orderDetail);
				page.setList(orderDetailService.findDistList(page));
				
				List<OrderDetail> dataList = page.getList();
				
	
				for (int i = 0; i < dataList.size(); i++) {
					row = sheet.createRow(i + 1);
					OrderDetail entity = dataList.get(i);
					int j = 0;
					row.createCell(j).setCellValue(nullToStr(siteProduct.getItemCode()));
					j++;
					row.createCell(j).setCellValue(nullToStr(siteProduct.getItemName()));
					j++;
					row.createCell(j).setCellValue(nullToStr(entity.getOrgName()));
					j++;
					row.createCell(j).setCellValue(nullToStr(null));
					j++;
					row.createCell(j).setCellValue(nullToStr(null));
					j++;
					row.createCell(j).setCellValue(nullToInt(entity.getSaleNum()));
					j++;
					row.createCell(j).setCellValue(nullToStr(entity.getOrderNo()));
					j++;
					row.createCell(j).setCellValue(filterStr(nullToStr(entity.getRemarks())));
				}
				response.setContentType("application/vnd.ms-excel");
				response.setHeader("Content-disposition",
						"attachment;filename=" + new String((siteProduct.getBuyerName()+"_"+siteProduct.getItemName() + "的订单.xls").getBytes("utf-8"), "ISO-8859-1"));
				OutputStream ouputStream = response.getOutputStream();
				wb.write(ouputStream);
				ouputStream.flush();
				ouputStream.close();
			}
		} catch (Exception e) {
			LOGGER.error("订单分配导出信息异常:{}", e);
		}
	}
	
	private String filterStr(String str){
		if(str == null || str.isEmpty())
			return str;
		
		String[] strings = str.split(",");
		List<String> list = new ArrayList<>();
		for(String s :strings){
			if(StringUtils.isNotBlank(s) && !"null".equals(s)){
				list.add(s);
			}
		}
		return Joiner.on(",").join(list);
	}
	
	/**
	 * 商品订单导出
	 *
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/exportProduct")
	public void exportProduct(HttpServletRequest request, HttpServletResponse response,OrderDeliveryExport orderDeliveryExport) {
		try {
			
				String[] excelHeader = new String[] { "商品编码","商品名称","颜色","尺寸","销售价"};
	
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
	
				List<OrderDeliveryExport> dataList = orderDeliveryExportService.findList(orderDeliveryExport);
				
				
				String expressNo = "0000";
				for (int i = 0; i < dataList.size(); i++) {
					row = sheet.createRow(i + 1);
					OrderDeliveryExport entity = dataList.get(i);
					expressNo = entity.getDeliveryExpressNo();
					int j = 0;
					row.createCell(j).setCellValue(nullToStr(entity.getItemCode()));
					j++;
					row.createCell(j).setCellValue(nullToStr(entity.getItemName()));
					j++;
					row.createCell(j).setCellValue(nullToStr(entity.getSpecColorText()));
					j++;
					row.createCell(j).setCellValue(nullToStr(entity.getSpecSizeText()));
					j++;
					row.createCell(j).setCellValue(nullToStr(entity.getExportPrice()));
				}
				response.setContentType("application/vnd.ms-excel");
				response.setHeader("Content-disposition",
						"attachment;filename=" + new String(("包裹号"+expressNo + "的订单.xls").getBytes("utf-8"), "ISO-8859-1"));
				OutputStream ouputStream = response.getOutputStream();
				wb.write(ouputStream);
				ouputStream.flush();
				ouputStream.close();
		} catch (Exception e) {
			LOGGER.error("订单分配导出信息异常:{}", e);
		}
	}
	
	/**
	 * 订单报表导出
	 *
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/report/excel")
	public void reportExcel(HttpServletRequest request, HttpServletResponse response, Order order, String startTime,
			String endTime, String supplyName, String orgName,
			@RequestParam(required = false, defaultValue = "orderNo") String query, Page<Order> page) {
		try {
			String[] excelHeader = new String[6];
			List<String> title = new ArrayList<>();
			if (query.contains("orgName")) {
				title.add("代理商");
			}
			
			if (query.contains("orderNo")) {
				title.add("订单号");
				title.add("下单人");
				title.add("订单时间");
			}
			if (query.contains("itemCateName")) {
				title.add("品类");
			}
			if (query.contains("buyerName")) {
				title.add("买手");
			}
			if (query.contains("itemName")) {
				title.add("商品编码");
				title.add("商品名称");
			}
			if ((query.contains("itemName") || query.contains("supplyName") || query.contains("orgName"))
					&& !query.contains("orderNo")) {
				title.add("订货数量");
			}
			title.add("总金额");

			if (query.contains("supplyName")) {
				title.add("供应商");
			}
			

			excelHeader = title.toArray(excelHeader);

			HSSFWorkbook wb = new HSSFWorkbook();
			HSSFSheet sheet = wb.createSheet("订单报表");
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
				order.setStartTime(DateUtils.parseDate(startTime));
				order.setEndTime(DateUtils.parseDate(endTime));
				order.setSupplyName(supplyName);
				order.setOrgName(orgName);

				page.setEntity(order);
				page.setOrderBy(query);

				String groupBy = query;
				page.getCondition().put("groupBy", groupBy);

				if (order.getOperStatus() == null) {
					page.getCondition().put("operStatusLike", " ( jod.oper_status in ("
							+ OrderAuditStatus.TO_TRACK.getType() + ","
							+ OrderAuditStatus.FINISH.getType() + "," + OrderAuditStatus.MERGE.getType() + ","+ OrderAuditStatus.TORECEIVE.getType() + ") ) ");
				}

				List<Order> dataList = orderService.findReportList(page);

				for (int i = 0; i < dataList.size(); i++) {
					row = sheet.createRow(i + 1);
					order = dataList.get(i);
					int j = 0;
					if (query.contains("orgName")) {
						row.createCell(j).setCellValue(order.getOrgName());
						j++;
					}
					
					if (query.contains("orderNo")) {
						row.createCell(j).setCellValue(order.getOrderNo());
						j++;
						row.createCell(j).setCellValue(order.getUserName());
						j++;
						row.createCell(j).setCellValue(DateUtils.formatDateTime(order.getCashtime()));
						j++;
					}
					if (query.contains("itemCateName")) {
						row.createCell(j).setCellValue(order.getItemCateName());
						j++;
					}
					if (query.contains("buyerName")) {
						row.createCell(j).setCellValue(order.getBuyerName());
						j++;
					}
					if (query.contains("itemName")) {
						row.createCell(j).setCellValue(order.getItemCode());
						j++;
						row.createCell(j).setCellValue(order.getItemName());
						j++;
					}
					if ((query.contains("itemName") || query.contains("supplyName") || query.contains("orgName"))
							&& !query.contains("orderNo")) {
						row.createCell(j).setCellValue(order.getTotalNum());
						j++;
					}

					row.createCell(j).setCellValue(order.getPayAmount());
					j++;

					if (query.contains("supplyName")) {
						row.createCell(j).setCellValue(order.getSupplyName());
						j++;
					}
					

				}
			}

			// 输出
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