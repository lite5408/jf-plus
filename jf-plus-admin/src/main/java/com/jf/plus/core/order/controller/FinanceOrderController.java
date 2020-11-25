package com.jf.plus.core.order.controller;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.jf.plus.common.core.Result;
import com.jf.plus.common.core.ResultCode;
import com.jf.plus.common.utils.CodeGen;
import com.jf.plus.common.utils.ExcelUtils;
import com.jf.plus.common.utils.StringUtils;
import com.jf.plus.core.mall.entity.MallSupplyer;
import com.jf.plus.core.mall.service.MallSupplyerService;
import com.jf.plus.core.order.entity.FinanceBook;
import com.jf.plus.core.order.entity.FinanceOrder;
import com.jf.plus.core.order.entity.Order;
import com.jf.plus.core.order.service.FinanceBookService;
import com.jf.plus.core.order.service.FinanceOrderService;
import com.jf.plus.core.order.service.OrderService;

import cn.iutils.common.BaseController;
import cn.iutils.common.Page;
import cn.iutils.common.config.JConfig;
import cn.iutils.common.utils.DateUtils;
import cn.iutils.common.utils.JStringUtils;

/**
 * 对账订单明细表 控制器
 * 
 * @author Tng
 * @version 1.0
 */
@Controller
@RequestMapping("${adminPath}/order/financeOrder")
public class FinanceOrderController extends BaseController {
	public final static ExcelUtils EXCEL_UTILS = new ExcelUtils();
	
	@Autowired
	private FinanceOrderService financeOrderService;
	
	@Autowired
	private OrderService orderService;
	
	@Autowired
	private FinanceBookService financeBookService;
	
	@Autowired
	private MallSupplyerService mallSupplyerService;

	@ModelAttribute
	public FinanceOrder get(@RequestParam(required = false) String id) {
		FinanceOrder entity = null;
		if (JStringUtils.isNotBlank(id)) {
			entity = financeOrderService.get(id);
		}
		if (entity == null) {
			entity = new FinanceOrder();
		}
		return entity;
	}
	
	@RequestMapping(value="/importOrder",method = RequestMethod.GET)
	public String importOrder(Model model){
		MallSupplyer mallSupplyer = new MallSupplyer();
		mallSupplyer.setStatus("1");
		//mallSupplyer.setOrgId(Long.valueOf(UserUtils.getLoginUser().getOrganizationId()));
		List<MallSupplyer> mallSupplyerList = mallSupplyerService.findList(mallSupplyer);
		model.addAttribute("mallSupplyerList", mallSupplyerList);
		return "order/financeOrder/importOrder";
	}
	
	@RequestMapping(value="/financeFinish",method = RequestMethod.GET)
	public String financeFinish(Model model,String batchNo){
		model.addAttribute("batchNo", batchNo);
		return "order/financeOrder/financeFinish";
	}
	
	@RequestMapping(value="/importOrder",method = RequestMethod.POST)
	@ResponseBody
	public Result importOrder(@RequestParam String uploadFile,String supplyId,String remarks,HttpServletRequest request,RedirectAttributes redirectAttributes){
		Result result = new Result();
		try {
			String path = JConfig.getConfig(JConfig.FILEUPLOAD);
			InputStream in = new FileInputStream(path+ "/" + uploadFile);
			List<List<Object>> importList = EXCEL_UTILS.getBankListByExcel(in, uploadFile);
			String batchNo = CodeGen.getBatchNo();
			for (List<Object> rowList : importList){
				
				String orderNoOrTradeNo = rowList.get(0) == null ? "" : rowList.get(0).toString();
				
				String tradeAmount = rowList.get(1) == null ? "" : rowList.get(1).toString();
				
				String orderNo = "";
				if(Long.valueOf(supplyId) <= 100){//电商渠道
					Order entity = new Order();
					entity.setOutTradeNo(orderNoOrTradeNo);
					entity.setSupplyId(Long.valueOf(supplyId));
					entity = orderService.getEntity(entity );
					if(entity != null){
						orderNo = entity.getOrderNo();
					}
				}else{
					orderNo = orderNoOrTradeNo;
				}
				
				FinanceBook financeBook = new FinanceBook();
				financeBook.setBatchNo(batchNo);
				financeBook.setBussType(3);
				financeBook.setOperStatus(0);
				financeBook.setOperTime(new Date());
				financeBook.setStatus("1");
				financeBook.setTitle("系统结算单导入");
				financeBook.setSupplyId(Long.valueOf(supplyId));
				financeBook.setRemarks(remarks);
				financeBookService.save(financeBook);
				
				
				FinanceOrder financeOrder = new FinanceOrder();
			    financeOrder.setBookId(Long.valueOf(financeBook.getId()));
				financeOrder.setOperStatus(0);
				financeOrder.setOrderNo(orderNo);
				financeOrder.setPayAmount(0D);
				financeOrder.setTradeAmount(StringUtils.isBlank(tradeAmount) ? null : Double.valueOf(tradeAmount));
				financeOrder.setOutTradeNo(orderNoOrTradeNo);
				financeOrder.setStatus("1");
				financeOrder.setOrderType(1);
				financeOrder.setMatchStatus(0);
				financeBook.setRemarks(remarks);
				
				financeOrderService.save(financeOrder);
			}
			
			financeOrderService.updateMatchStatus(batchNo);
			
			result.setMsg("导入成功");
			result.setCode(ResultCode.SUCCESS);
			result.setObj(batchNo);
			return result;
		} catch (Exception e) {
			logger.error("导入对账单失败:{}",e);
			return Result.newExceptionInstance();
		}
		 
	}

	@RequestMapping(value = { "/list", "" })
	public String list(Model model, FinanceOrder financeOrder, Page<FinanceOrder> page) {
		if (financeOrder.getOperStatus() == null) {
			financeOrder.setOperStatus(1);
		}
		page.setEntity(financeOrder);
		model.addAttribute("page", page.setList(financeOrderService.findPage(page)));

		return "order/financeOrder/list";
	}
	
	@RequestMapping(value = "/showImportResult")
	public String showImportResult(Model model,FinanceOrder financeOrder,Page<FinanceOrder> page){
		page.setEntity(financeOrder);
		financeOrder.setMatchStatus(1);
		int matchSuccCount = financeOrderService.count(page);
		
		financeOrder.setMatchStatus(2);
		page.setEntity(financeOrder);
		int matchFailCount = financeOrderService.count(page);
		
		model.addAttribute("matchSuccCount", matchSuccCount);
		model.addAttribute("matchFailCount", matchFailCount);
		model.addAttribute("batchNo", financeOrder.getBatchNo());
		
		return "order/financeOrder/showImportResult";
	}
	
	@RequestMapping(value = "/batchUpdateStatus")
	@ResponseBody
	public Result batchUpdateStatus(String batchNo,Integer operStatus){
		Result result = new Result();
		
		try{
			FinanceBook financeBook = new FinanceBook();
			financeBook.setBatchNo(batchNo);
			financeBook.setOperStatus(operStatus);
			financeBookService.batchUpdate(financeBook);
			
			result.setCode(ResultCode.SUCCESS);
			return result;
		}catch(Exception e){
			logger.error("批量确认订单状态失败：{}",e);
			return Result.newExceptionInstance();
		}
				
	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public String create(FinanceOrder financeOrder, Model model) {
		model.addAttribute("financeOrder", financeOrder);
		return "order/financeOrder/form";
	}

	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public String create(FinanceOrder financeOrder, RedirectAttributes redirectAttributes) {
		financeOrderService.save(financeOrder);
		addMessage(redirectAttributes, "新增成功");
		return "redirect:" + adminPath + "/order/financeOrder/update?id=" + financeOrder.getId();
	}

	@RequestMapping(value = "/update", method = RequestMethod.GET)
	public String update(FinanceOrder financeOrder, Model model) {
		model.addAttribute("financeOrder", financeOrder);
		return "order/financeOrder/form";
	}

	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public String update(FinanceOrder financeOrder, RedirectAttributes redirectAttributes) {
		financeOrderService.save(financeOrder);
		addMessage(redirectAttributes, "修改成功");
		return "redirect:" + adminPath + "/order/financeOrder/update?id=" + financeOrder.getId();
	}

	@RequestMapping(value = "/{id}/delete", method = RequestMethod.GET)
	public String delete(@PathVariable("id") String id, int pageNo, int pageSize,
			RedirectAttributes redirectAttributes) {
		financeOrderService.delete(id);
		addMessage(redirectAttributes, "删除成功");
		return "redirect:" + adminPath + "/order/financeOrder?pageNo=" + pageNo + "&pageSize=" + pageSize;
	}

	@RequestMapping("/batchFinanceOrder")
	@ResponseBody
	public Result batchFinanceOrder(String ids, Integer operStatus, Integer bussType) {
		Result result = new Result();
		try {
			String batchNo = DateUtils.getDate("MMddssSSS");
			if (StringUtils.isBlank(ids)) {
				result.setCode(ResultCode.ARGUMENT_LACK_ERROR);
				result.setMsg("参数缺失");
				return result;
			}

			String[] idsList = ids.split(",");
			switch (operStatus) {
			case 1:// 标记结算
				for (String id : idsList) {
					FinanceBook financeBook = new FinanceBook();
					financeBook.setBatchNo(batchNo);
					financeBook.setBussType(bussType);
					financeBook.setOperStatus(1);
					financeBook.setOperTime(new Date());
					financeBook.setStatus("1");
					financeBook.setTitle("系统结算单");
					
					Order order = orderService.get(id);
					financeBook.setSupplyId(order.getSupplyId());
					financeBookService.save(financeBook);
					
					FinanceOrder financeOrder = new FinanceOrder();
				    financeOrder.setBookId(Long.valueOf(financeBook.getId()));
					financeOrder.setOperStatus(1);
					financeOrder.setOrderNo(order.getOrderNo());
					financeOrder.setPayAmount(order.getPayAmount());
					financeOrder.setOutTradeNo(order.getOutTradeNo());
					financeOrder.setOperStatus(1);
					financeOrder.setStatus("1");
					financeOrder.setOrderType(1);
					financeOrder.setTradeAmount(order.getPayAmount());
					
					financeOrderService.save(financeOrder);
				}

				break;
			case 2:// 取消结算

				financeOrderService.cancelFinanceOrder(Arrays.asList(idsList));

				break;
			default:
				break;
			}

			result.setCode(com.jf.plus.common.core.ResultCode.SUCCESS);
			result.setMsg("订单标记结算成功");
			return result;
		} catch (Exception e) {
			logger.error("对账失败:{}", e);
			return Result.newExceptionInstance();
		}
	}
	
	@RequestMapping("/submit")
	@ResponseBody
	public Result submit(String batchNo) {
		Result result = new Result();
		try {
			financeOrderService.submit(batchNo);
			
			result.setCode(ResultCode.SUCCESS);
			result.setMsg("订单标记结算成功");
			return result;
		} catch (Exception e) {
			logger.error("对账失败:{}", e);
			return Result.newExceptionInstance();
		}
	}
}
