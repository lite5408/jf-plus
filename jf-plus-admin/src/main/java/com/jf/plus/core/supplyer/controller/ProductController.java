package com.jf.plus.core.supplyer.controller;

import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.HtmlUtils;

import com.jf.plus.common.core.Result;
import com.jf.plus.common.core.ResultCode;
import com.jf.plus.common.core.enums.ProductSource;
import com.jf.plus.common.core.enums.ProductStatus;
import com.jf.plus.common.vo.SupplyerUserVo;
import com.jf.plus.core.mall.entity.MallSupplyer;
import com.jf.plus.core.mall.service.MallSupplyerService;
import com.jf.plus.core.product.entity.Product;
import com.jf.plus.core.product.entity.ProductDetail;
import com.jf.plus.core.product.entity.ProductStock;
import com.jf.plus.core.product.entity.ProductSupplyPriceLog;
import com.jf.plus.core.product.service.ProductDetailService;
import com.jf.plus.core.product.service.ProductService;
import com.jf.plus.core.product.service.ProductStockService;
import com.jf.plus.core.product.service.ProductSupplyPriceLogService;

import cn.iutils.common.BaseController;
import cn.iutils.common.Page;
import cn.iutils.common.utils.JStringUtils;
import cn.iutils.common.utils.UserUtils;
import cn.iutils.sys.service.OrganizationService;

/**
 * 供应商商品 控制器
 * 
 * @author Tng
 * @version 1.0
 */
@Controller("supplyProductController")
@RequestMapping("${supplyPath}/product")
public class ProductController extends BaseController {
	
	@Autowired
	private MallSupplyerService mallSupplyerService;

	@Autowired
	private ProductStockService productStockService;
	
	@Autowired
	private ProductService productService;
	
	@Autowired
	private ProductDetailService productDetailService;
	
	@Autowired
	private OrganizationService organizationService;
	
	@Autowired
	private ProductSupplyPriceLogService productSupplyPriceLogService;

	@ModelAttribute
	public Product get(@RequestParam(required = false) String id) {
		Product entity = null;
		if (JStringUtils.isNotBlank(id)) {
			entity = productService.get(id);
			ProductDetail detail = new ProductDetail();
			detail.setProductNo(entity.getProductNo());
			detail = productDetailService.getEntity(detail);
			entity.setContent(detail.getContent());
			
			ProductStock stock = new ProductStock();
			stock.setProductNo(entity.getProductNo());
			stock = productStockService.getEntity(stock);
			entity.setStock(stock.getStockNum());
		}
		if (entity == null) {
			entity = new Product();
		}
		return entity;
	}
	
	@RequestMapping(value = {"/list",""})
	public String list(Model model,Product product,Page<Product> page) {
		product.setSupplyId(Long.valueOf(UserUtils.getSupplyUser().getSupplyId()));
		page.setEntity(product);
		model.addAttribute("page",page.setList(productService.findSupplyPage(page)));
		return "/mall/mallSupplyer/product/list";
	}
	
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public String create(Product product, Model model) {
		model.addAttribute("product", product);
		return "/mall/mallSupplyer/product/form";
	}

	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public String create(Product product, RedirectAttributes redirectAttributes) {
		try{
			SupplyerUserVo user = UserUtils.getSupplyUser();
			MallSupplyer supplyer = mallSupplyerService.get(user.getSupplyId());
			product.setSupplyId(Long.valueOf(supplyer.getId()));
			product.setSupplyName(supplyer.getCompanyName());
			product.setOrgId(supplyer.getOrgId());
			product.setOrgName(organizationService.get(supplyer.getOrgId().toString()).getName());
			product.setCreateBy("0");
			product.setUpdateBy("0");
			product.setType(1);
			product.setOperStatus(ProductStatus.SHELVES.getType());
			Double profit = (product.getMarkPrice() - product.getSupplyPrice()) / product.getSupplyPrice() * 100;
			product.setProfitPercent(profit);
			
			productService.save(product);
			product.setProductNo(Long.valueOf(product.getId()));
			productService.save(product);
			
			ProductStock productStock = new ProductStock();
			productStock.setItemCode(product.getItemCode());
			productStock.setProductNo(product.getProductNo());
			productStock.setStockNum(product.getStock());
			productStock.setCreateBy("0");
			productStock.setUpdateBy("0");
			productStockService.save(productStock);
			
			ProductDetail productDetail = new ProductDetail();
			productDetail.setContent(HtmlUtils.htmlEscape(product.getContent()));
			productDetail.setItemCode(product.getItemCode());
			productDetail.setItemName(product.getItemName());
			productDetail.setProductNo(product.getProductNo());
			productDetail.setCreateBy("0");
			productDetail.setUpdateBy("0");
			productDetailService.save(productDetail);
			
			addMessage(redirectAttributes, "新增成功");
			return "redirect:" + supplyPath + "/product/update?id=" + product.getId();
		}catch(DuplicateKeyException e){
			addMessage(redirectAttributes, "保存失败，商品编码重复！");
			return "redirect:" + supplyPath + "/product/create";
		}
	}

	@RequestMapping(value = "/update", method = RequestMethod.GET)
	public String update(Product product, Model model) {
		model.addAttribute("product", product);
		return "/mall/mallSupplyer/product/form";
	}

	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public String update(Product product, RedirectAttributes redirectAttributes) {
		Double profit = (product.getMarkPrice() - product.getSupplyPrice()) / product.getSupplyPrice() * 100;
		product.setProfitPercent(profit);
		productService.save(product);
		
		//存储价格变更日志
		ProductSupplyPriceLog productSupplyPriceLog = new ProductSupplyPriceLog();
		productSupplyPriceLog.setSupplyId(product.getSupplyId());
		productSupplyPriceLog.setSource(product.getSource());
		productSupplyPriceLog.setItemCode(product.getItemCode());
		productSupplyPriceLog.setMarkPrice(product.getMarkPrice());
		productSupplyPriceLog.setSupplyPrice(product.getSupplyPrice());
		productSupplyPriceLog.setOperStatus(1);
		productSupplyPriceLog.setCreateBy("0");
		productSupplyPriceLog.setCreateDate(new Date());
		productSupplyPriceLogService.save(productSupplyPriceLog);
		
		ProductDetail detail = new ProductDetail();
		detail.setProductNo(Long.valueOf(product.getId()));
		detail = productDetailService.getEntity(detail);
		detail.setContent(HtmlUtils.htmlEscape(product.getContent()));
		productDetailService.save(detail);
		
		
		ProductStock stock = new ProductStock();
		stock.setProductNo(Long.valueOf(product.getId()));
		stock = productStockService.getEntity(stock);
		stock.setStockNum(product.getStock());
		productStockService.save(stock);
		
		//更新价格
//		Product syncPriceProduct = new Product();
//		syncPriceProduct.setSource(ProductSource.SUPPLY.getType());
//		syncPriceProduct.setItemCode(product.getItemCode());
//		syncPriceProduct.setSupplyPrice(product.getSupplyPrice());
//		syncPriceProduct.setMarkPrice(product.getMarkPrice());
//		productService.batchSnycProductPrice(syncPriceProduct);
		
		addMessage(redirectAttributes, "修改成功");
		return "redirect:" + supplyPath + "/product/update?id=" + product.getId();
	}
	
	/**
	 * 修改商品状态（批量）
	 * 
	 * @param ids
	 * @param operStatus
	 * @return
	 */
	@RequestMapping(value = "/changeStatus")
	@ResponseBody
	public Result changeStatus(String[] ids, Integer operStatus,String[] itemCodes) {
		Result result = Result.newInstance();
		try {
			for (String id : ids) {
				Product product = new Product();
				product.setId(id);
				product.setOperStatus(operStatus);
				productService.save(product);
			}
			
			//自动上下架
			if (itemCodes != null && itemCodes.length > 0) {
				if (operStatus == ProductStatus.OUT_SHELVES.getType()) {
					Set<String> xjSKUList = new HashSet<>(Arrays.asList(itemCodes));
					productService.batchOutShelves(ProductSource.SUPPLY.getType(), xjSKUList);
				} else if (operStatus == ProductStatus.SHELVES.getType()) {
					for (String itemCode : itemCodes) {

						Product existsProduct = new Product();
						existsProduct.setItemCode(itemCode);
						existsProduct.setSource(ProductSource.SUPPLY.getType());
						
						productService.autoShelvesProduct(existsProduct);
					}
				}
			}
			
			result.setCode(ResultCode.SUCCESS);
			result.setMsg("操作成功");
		} catch (Exception e) {
			logger.error("修改商品状态失败：{}", e);
			return Result.newExceptionInstance();
		}

		return result;
	}
	
	@RequestMapping(value = "/getStock")
	@ResponseBody
	public Result getStock(Long productNo){
		Result result = new Result();
		try{
			ProductStock stock = new ProductStock();
			stock.setProductNo(productNo);
			stock = productStockService.getEntity(stock);
			if(stock != null){
				result.setCode(ResultCode.SUCCESS);
				result.setMsg("查询库存成功");
				result.setObj(stock);
				return result;
			}
			return result;
		}catch(Exception e){
			logger.error("查询库存失败:{}",e);
			return Result.newExceptionInstance();
		}
		
	}
	
	
	/**
	 * 商品详情
	 * 
	 * @param product
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/detail")
	public String detail(Product product, Model model) {
		if (product.getId() == null) {
			product.setId(String.valueOf(product.getProductNo()));
		}
		product = productService.getEntity(product);
		ProductDetail entity = new ProductDetail();
		entity.setProductNo(product.getProductNo());
		entity = productDetailService.getEntity(entity);
		if (entity != null) {
			product.setContent(entity.getContent());
		}

		model.addAttribute("product", product);
		return "product/product/detail";
	}
	
}
