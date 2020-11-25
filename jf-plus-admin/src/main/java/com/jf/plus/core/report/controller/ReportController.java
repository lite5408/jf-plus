package com.jf.plus.core.report.controller;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jf.plus.common.core.Result;
import com.jf.plus.common.core.ResultCode;
import com.jf.plus.common.core.enums.OrderAuditStatus;
import com.jf.plus.common.core.enums.OrgType;
import com.jf.plus.core.mall.entity.MallSupplyer;
import com.jf.plus.core.mall.service.MallSupplyerService;
import com.jf.plus.core.mall.service.ReportService;
import com.jf.plus.core.order.entity.Order;
import com.jf.plus.core.order.service.OrderService;

import cn.iutils.common.BaseController;
import cn.iutils.common.Page;
import cn.iutils.common.utils.DateUtils;
import cn.iutils.sys.entity.Organization;
import cn.iutils.sys.service.OrganizationService;

/**
 * 报表控制器
 *
 * @author Tng
 * @version 1.0
 */
@Controller
@RequestMapping("${adminPath}/report")
public class ReportController extends BaseController {

	@Autowired
	private OrderService orderService;

	@Autowired
	private ReportService reportService;

	@Autowired
	private MallSupplyerService mallSupplyerService;

	@Autowired
	private OrganizationService organizationService;

	@RequestMapping(value = { "/list", "" })
	public String list(Model model, Order order, @RequestParam(required = false, defaultValue = "orderNo") String query,
			Page<Order> page) {
		try {

			Organization organization = organizationService
					.get(cn.iutils.common.utils.UserUtils.getLoginUser().getOrganizationId());

			if (organization != null && organization.getType() == OrgType.COMP_TX.getType()) {
				model.addAttribute("isDL", 1);
				order.setOrgName(organization.getName());
				page.setEntity(order);
			}

			if (order.getStartTime() != null || order.getEndTime() != null) {
				page.setEntity(order);
				page.setOrderBy(query);

				String groupBy = query;
				page.getCondition().put("groupBy", groupBy);

				if (order.getOperStatus() == null) {
					// page.getCondition().put("operStatusLike", " (
					// a.oper_status in ("
					// + OrderAuditStatus.FINISH.getType() + "," +
					// OrderAuditStatus.TORECEIVE.getType() + ") ) ");
					page.getCondition().put("operStatusLike",
							" ( jod.oper_status in (" + OrderAuditStatus.FINISH.getType() + ","
									+ OrderAuditStatus.TORECEIVE.getType() + "," + OrderAuditStatus.MERGE.getType()
									+ ") ) ");
				}
				model.addAttribute("page", page.setList(orderService.findReportList(page)));
				model.addAttribute("query", query);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return "/report/list";
	}

	@RequestMapping("/salesReport")
	@ResponseBody
	public Result salesReport(Order order) {
		Result result = new Result();
		try {
			List<Map<String, Object>> reportList = reportService.findSaleReport(order);

			List<String> monthList = DateUtils.getMonthBetweenTwoDate(order.getStartTime(), order.getEndTime());
			Map saleDataMap = new LinkedHashMap();
			for (String key : monthList) {
				saleDataMap.put(key, 0);
			}

			for (Map<String, Object> rowMap : reportList) {
				if (rowMap != null) {
					saleDataMap.put(rowMap.get("cashtime").toString(), rowMap.get("saleamount"));
				}
			}

			result.setCode(ResultCode.SUCCESS);
			result.setObj(saleDataMap);
			return result;
		} catch (Exception e) {
			logger.error("查询销量报表异常：{}", e);
			return Result.newExceptionInstance();
		}

	}

	@RequestMapping("/supplyReport")
	@ResponseBody
	public Result supplyReport(Order order) {
		Result result = new Result();
		try {

			List<String> monthList = DateUtils.getMonthBetweenTwoDate(order.getStartTime(), order.getEndTime());
			MallSupplyer entity = new MallSupplyer();
			// entity.setOrgId(Long.valueOf(UserUtils.getLoginUser().getOrganizationId()));
			entity.setStatus("1");
			List<MallSupplyer> supplyerList = mallSupplyerService.findList(entity);

			Map<String, Map<String, Object>> saleDataMap = new LinkedHashMap<String, Map<String, Object>>();

			for (MallSupplyer mallSupplyer : supplyerList) {
				Map<String, Object> supplySaleMap = new LinkedHashMap<>();
				for (String key : monthList) {
					order.setCashMonth(DateUtils.parseDate(key));
					order.setSupplyId(Long.valueOf(mallSupplyer.getId()));
					List<Map<String, Object>> reportList = reportService.findSupplyReport(order);
					if (reportList != null && reportList.size() > 0 && reportList.get(0) != null) {
						supplySaleMap.put(key, reportList.get(0).get("saleamount").toString());
					} else {
						supplySaleMap.put(key, "0");
					}
				}
				saleDataMap.put(mallSupplyer.getCompanyTitle(), supplySaleMap);
			}

			result.setCode(ResultCode.SUCCESS);
			result.setObj(saleDataMap);
			return result;
		} catch (Exception e) {
			logger.error("查询供应商采购额报表异常：{}", e);
			return Result.newExceptionInstance();
		}

	}

	@RequestMapping("/topCateSaleReport")
	@ResponseBody
	public Result topCateSaleReport(Order order) {
		Result result = new Result();
		try {

			Map<String, Map<String, Object>> saleDataMap = new LinkedHashMap<String, Map<String, Object>>();

			List<Map<String, Object>> reportList = reportService.findTopCateReport(order);
			for (Map<String, Object> rowMap : reportList) {
				if (rowMap != null) {
					saleDataMap.put(rowMap.get("catName").toString(), rowMap);
				}
			}

			result.setCode(ResultCode.SUCCESS);
			result.setObj(saleDataMap);
			return result;
		} catch (Exception e) {
			logger.error("查询采购交易榜单报表异常：{}", e);
			return Result.newExceptionInstance();
		}

	}

	@RequestMapping("/topSupplySaleReport")
	@ResponseBody
	public Result topSupplySaleReport(Order order) {
		Result result = new Result();
		try {

			Map<String, Map<String, Object>> saleDataMap = new LinkedHashMap<String, Map<String, Object>>();

			List<Map<String, Object>> reportList = reportService.findTopSupplyReport(order);
			for (Map<String, Object> rowMap : reportList) {
				if (rowMap != null) {
					saleDataMap.put(rowMap.get("supplyName").toString(), rowMap);
				}
			}

			result.setCode(ResultCode.SUCCESS);
			result.setObj(saleDataMap);
			return result;
		} catch (Exception e) {
			logger.error("查询供应商交易榜单报表异常：{}", e);
			return Result.newExceptionInstance();
		}
	}

	@RequestMapping("/topCateSalePieReport")
	@ResponseBody
	public Result topCateSalePieReport(Order order) {
		Result result = new Result();
		try {

			Map<String, Map<String, Object>> saleDataMap = new LinkedHashMap<String, Map<String, Object>>();

			List<Map<String, Object>> reportList = reportService.findTopCateReport(order);
			List excludeCate = new ArrayList<>();
			for (Map<String, Object> rowMap : reportList) {
				if (rowMap != null) {
					saleDataMap.put(rowMap.get("catName").toString(), rowMap);
					excludeCate.add(rowMap.get("cateId"));
				}
			}

			// 其他
			order.setExcludeCate(excludeCate);
			List<Map<String, Object>> otherReportList = reportService.findTopOtherReport(order);
			if (otherReportList != null && otherReportList.size() > 0) {
				Map<String, Object> rowMap = otherReportList.get(0);
				if (rowMap != null) {
					rowMap.put("catName", "其他");
					saleDataMap.put(rowMap.get("catName").toString(), rowMap);
				}
			}

			result.setCode(ResultCode.SUCCESS);
			result.setObj(saleDataMap);
			return result;
		} catch (Exception e) {
			logger.error("查询采购交易榜单报表异常：{}", e);
			return Result.newExceptionInstance();
		}

	}

	@RequestMapping("/topSupplySalePieReport")
	@ResponseBody
	public Result topSupplySalePieReport(Order order) {
		Result result = new Result();
		try {
			Map<String, Map<String, Object>> saleDataMap = new LinkedHashMap<String, Map<String, Object>>();
			List excludeSupply = new ArrayList<>();

			List<Map<String, Object>> reportList = reportService.findTopSupplyReport(order);
			for (Map<String, Object> rowMap : reportList) {
				if (rowMap != null) {
					saleDataMap.put(rowMap.get("supplyName").toString(), rowMap);
					excludeSupply.add(rowMap.get("supplyId"));
				}
			}

			// 其他
			order.setExcludeSupply(excludeSupply);
			List<Map<String, Object>> otherReportList = reportService.findTopOtherReport(order);
			if (otherReportList != null && otherReportList.size() > 0) {
				Map<String, Object> rowMap = otherReportList.get(0);
				if (rowMap != null) {
					rowMap.put("supplyName", "其他");
					saleDataMap.put(rowMap.get("supplyName").toString(), rowMap);
				}
			}

			result.setCode(ResultCode.SUCCESS);
			result.setObj(saleDataMap);
			return result;
		} catch (Exception e) {
			logger.error("查询采购交易榜单报表异常：{}", e);
			return Result.newExceptionInstance();
		}

	}

}
