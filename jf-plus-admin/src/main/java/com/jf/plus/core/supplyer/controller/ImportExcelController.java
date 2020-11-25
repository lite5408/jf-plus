package com.jf.plus.core.supplyer.controller;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.jf.plus.common.core.Constants;
import com.jf.plus.common.core.Result;
import com.jf.plus.common.core.ResultCode;
import com.jf.plus.common.core.enums.Status;
import com.jf.plus.common.utils.ExcelUtils;
import com.jf.plus.core.order.entity.OrderDistrib;
import com.jf.plus.core.order.service.OrderDistribService;

import cn.iutils.common.BaseController;
import cn.iutils.common.utils.JUploadUtils;
import cn.iutils.common.utils.UserUtils;

/**
 * 导入控制层
 *
 * @author Tng
 *
 */
@Controller("supplyImportExcelController")
@RequestMapping("${supplyPath}/import")
public class ImportExcelController extends BaseController {
	public final static ExcelUtils EXCEL_UTILS = new ExcelUtils();

	@Autowired
	private OrderDistribService orderDistribService;

	@RequestMapping(value = { "/logistics/batchImport" })
	public String logisticsBatchImport(@RequestParam(value = "file", required = true) MultipartFile file,
			HttpServletRequest request, RedirectAttributes redirectAttributes) {
		Result result = new Result();
		try {
			InputStream in = file.getInputStream();
			List<List<Object>> importList = null;
			try {
				importList = EXCEL_UTILS.getBankListByExcel(in, file.getOriginalFilename());
			} catch (Exception e) {
				result.setCode(ResultCode.ARGUMENT_LACK_ERROR);
				result.setMsg("Excel文件中存在空缺的单元格，请排查后再下单。");
				result.setObj(0);
				request.setAttribute("result", result);
				return "/excel/impLogisticsBatchResult";
			}

			OrderDistrib orderDistrib = null;
			List<OrderDistrib> list = new ArrayList<>();

			for (List<Object> rowList : importList) {

				String remarks = "";

				String orderNo = rowList.get(0) == null ? "" : rowList.get(0).toString();
				String type = rowList.get(1) == null ? "" : rowList.get(1).toString();
				String express = rowList.get(2) == null ? "" : rowList.get(2).toString();
				String expressNo = rowList.get(3) == null ? "" : rowList.get(3).toString();
				if (rowList.size() > 4)
					remarks = rowList.get(4) == null ? "" : rowList.get(4).toString();

				orderDistrib = new OrderDistrib();
				if (type.equals("快递")) {
					orderDistrib.setType(1);
					orderDistrib.setExpress(express);
					orderDistrib.setExpressNo(expressNo);
				} else {
					orderDistrib.setType(0);
					orderDistrib.setDelivery(express);
					orderDistrib.setDeliveryMobile(expressNo);
				}
				orderDistrib.setOrderNo(orderNo);
				orderDistrib.setDeliveryTime(new Date());
				orderDistrib.setRemarks(remarks);
				orderDistrib.setStatus(String.valueOf(Status.NORMAL.getType()));
				orderDistrib.setCreateBy("0");
				orderDistrib.setCreateDate(new Date());
				list.add(orderDistrib);
			}
			result = orderDistribService.batchUpdateLogistics(list, UserUtils.getSupplyUser().getSupplyId());
			result.setObj(list);
			try {
				JUploadUtils.save(file, request);// 存档
			} catch (Exception e) {
				// 上传文件有异常不捕获
			}
		} catch (Exception e) {
			logger.error("导入异常：{}", e);
			result.setCode(ResultCode.SERVICE_EXCEPTION);
			result.setMsg(Constants.EXCEPTION_MSG);
			result.setObj(0);
		}
		addMessage(redirectAttributes, "批量导入物流信息成功");
		request.setAttribute("result", result);
		return "/excel/impLogisticsBatchResult";
	}

}
