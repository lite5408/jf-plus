package com.jf.plus.core.order.controller;

import java.util.Date;
import java.util.List;

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
import com.jf.plus.core.order.entity.OrderDetail;
import com.jf.plus.core.order.entity.OrderDetailDist;
import com.jf.plus.core.order.service.OrderDetailDistService;
import com.jf.plus.core.order.service.OrderDetailService;
import com.jf.plus.core.product.entity.ProductStock;
import com.jf.plus.core.product.service.ProductStockService;
import com.jf.plus.core.site.entity.SiteProduct;
import com.jf.plus.core.site.service.SiteProductService;

import cn.iutils.common.BaseController;
import cn.iutils.common.Page;
import cn.iutils.common.utils.JStringUtils;
import cn.iutils.common.utils.UserUtils;

/**
* 订单分配表 控制器
* @author Tng
* @version 1.0
*/
@Controller
@RequestMapping("${adminPath}/order/orderDetailDist")
public class OrderDetailDistController extends BaseController {

    @Autowired
    private OrderDetailDistService orderDetailDistService;
    
    @Autowired
    private OrderDetailService orderDetailService;
    
    @Autowired
    private SiteProductService siteProductService;
    
    @Autowired
    private ProductStockService productStockService;

    @ModelAttribute
    public OrderDetailDist get(@RequestParam(required = false) String id) {
        OrderDetailDist entity = null;
        if (JStringUtils.isNotBlank(id)) {
            entity = orderDetailDistService.get(id);
        }
        if (entity == null) {
            entity = new OrderDetailDist();
        }
        return entity;
    }

    @RequestMapping(value={"/list",""})
    public String list(Model model, Page<OrderDetailDist> page) {
        model.addAttribute("page", page.setList(orderDetailDistService.findPage(page)));
        return "order/orderDetailDist/list";
    }

    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public String create(OrderDetailDist orderDetailDist,Model model) {
        model.addAttribute("orderDetailDist", orderDetailDist);
        return "order/orderDetailDist/form";
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String create(OrderDetailDist orderDetailDist, RedirectAttributes redirectAttributes) {
        orderDetailDistService.save(orderDetailDist);
        addMessage(redirectAttributes,"新增成功");
        return "redirect:"+ adminPath +"/order/orderDetailDist/update?id="+orderDetailDist.getId();
    }

    @RequestMapping(value = "/update", method = RequestMethod.GET)
    public String update(OrderDetailDist orderDetailDist, Model model) {
        model.addAttribute("orderDetailDist", orderDetailDist);
        return "order/orderDetailDist/form";
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String update(OrderDetailDist orderDetailDist, RedirectAttributes redirectAttributes) {
        orderDetailDistService.save(orderDetailDist);
        addMessage(redirectAttributes,"修改成功");
        return "redirect:"+ adminPath +"/order/orderDetailDist/update?id="+orderDetailDist.getId();
    }

    @RequestMapping(value = "/{id}/delete", method = RequestMethod.GET)
    public String delete(@PathVariable("id") String id,int pageNo,int pageSize, RedirectAttributes redirectAttributes) {
        orderDetailDistService.delete(id);
        addMessage(redirectAttributes,"删除成功");
        return "redirect:"+ adminPath +"/order/orderDetailDist?pageNo="+pageNo+"&pageSize="+pageSize;
    }
    
    /*
     * 自动分配
     */
    @RequestMapping("autoDist")
	@ResponseBody
	public Result autoDist(OrderDetail orderDetail,Page<OrderDetail> page) {
		Result result = new Result();
		
		try {
			if("1".equals(orderDetail.getIsDist()) || orderDetail.getItemId() == null){
				result.setCode(ResultCode.RETURN_FAILURE);
				result.setMsg("参数不正确，请刷新再试");
				return result;
			}
			//所有商品订单全部分配
			List<OrderDetail> orderDetails = orderDetailService.findList(orderDetail);
			
			Integer distStock = 0;
			String batchNo = CodeGen.getBatchNo();
			for(OrderDetail orderDetail_2 : orderDetails){
				distStock += orderDetail_2.getSaleNum();
			}
			
			for(OrderDetail orderDetail_2 : orderDetails){
				//分配按照最早下单时间来分配
				OrderDetail toUpdate = new OrderDetail();
				toUpdate.setId(orderDetail_2.getId());
				toUpdate.setIsDist("1");
				toUpdate.setDistDate(new Date());
				toUpdate.setDistOperator(UserUtils.getLoginUser().getName());
				toUpdate.setDistNum(orderDetail_2.getSaleNum());
				toUpdate.setDistStock(distStock);
				toUpdate.setBatchNo(batchNo);
				
				orderDetailService.save(toUpdate);
				
				//保存分配记录
				OrderDetailDist orderDetailDist = new OrderDetailDist();
				orderDetailDist.setBatchNo(batchNo);
				orderDetailDist.setOrderDetailId(orderDetail_2.getId());
				orderDetailDist.setCreateBy(UserUtils.getLoginUser().getId());
				orderDetailDist.setCreateDate(new Date());
				orderDetailDist.setDistDate(toUpdate.getDistDate());
				orderDetailDist.setDistNum(toUpdate.getDistNum());
				orderDetailDist.setDistOperator(toUpdate.getDistOperator());
				orderDetailDist.setDistStock(distStock);
				orderDetailDist.setIsDist("1");
				orderDetailDist.setRemarks(orderDetail_2.getRemarks());
				orderDetailDist.setStatus("1");
				orderDetailDistService.save(orderDetailDist);
			}
			
			//更新分配订单号
			SiteProduct siteProduct = siteProductService.get(orderDetail.getItemId()+"");
			//if(SaleType.KH.getDescription().equals(siteProduct.getSaleType())){
				page.setEntity(orderDetail);
				page.getEntity().setIsDist("1");
				page.getEntity().setBatchNo(batchNo);
				page.setList(orderDetailService.findDistList(page));
				
				for(OrderDetail orderDetail2 : page.getList()){
					orderDetail2.setItemId(orderDetail.getItemId());
					orderDetail2.setDistOrderNo(orderDetail2.getOrderNo());
					orderDetailService.distOrderNoDetail(orderDetail2);
					
					orderDetailService.mergeOrderDetail(orderDetail2);//标记已合并
				}
//			}else{
//				
//			}
			
			if(orderDetail.getItemId() != 0 && distStock > 0){
				ProductStock productStock = new ProductStock();
				productStock.setProductNo(siteProduct.getProductId());
				
				productStock = productStockService.getEntity(productStock);
				if(productStock != null){
					productStock.setDistStock(distStock);
					productStockService.save(productStock);
				}
			}
			
			result.setObj(batchNo);
			result.setMsg("分配成功");
			result.setCode(ResultCode.SUCCESS);
			return result;
		} catch (Exception e) {
			logger.error("系统异常：{}", e);
			return Result.newExceptionInstance();
		}
	}
}
