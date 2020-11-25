package com.jf.plus.core.product.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
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

import com.alibaba.fastjson.JSON;
import com.jf.plus.common.core.Result;
import com.jf.plus.common.core.ResultCode;
import com.jf.plus.common.core.enums.OrgType;
import com.jf.plus.common.core.enums.ProductSource;
import com.jf.plus.common.core.enums.ProductStatus;
import com.jf.plus.common.utils.StringUtils;
import com.jf.plus.common.vo.PrductAttrVo;
import com.jf.plus.common.vo.ProductSkuVo;
import com.jf.plus.common.vo.ProductVo;
import com.jf.plus.core.product.entity.OrgGroup;
import com.jf.plus.core.product.entity.Product;
import com.jf.plus.core.product.entity.ProductAttr;
import com.jf.plus.core.product.entity.ProductDetail;
import com.jf.plus.core.product.entity.ProductSaleRule;
import com.jf.plus.core.product.entity.ProductSku;
import com.jf.plus.core.product.entity.ProductStatusLog;
import com.jf.plus.core.product.entity.ProductStock;
import com.jf.plus.core.product.service.OrgGroupService;
import com.jf.plus.core.product.service.ProductAttrService;
import com.jf.plus.core.product.service.ProductDetailService;
import com.jf.plus.core.product.service.ProductSaleRuleService;
import com.jf.plus.core.product.service.ProductService;
import com.jf.plus.core.product.service.ProductSkuService;
import com.jf.plus.core.product.service.ProductStatusLogService;
import com.jf.plus.core.product.service.ProductStockService;

import cn.iutils.common.BaseController;
import cn.iutils.common.Page;
import cn.iutils.common.utils.JStringUtils;
import cn.iutils.common.utils.UserUtils;
import cn.iutils.sys.entity.Organization;
import cn.iutils.sys.entity.User;
import cn.iutils.sys.service.OrganizationService;

/**
 * 商品表 控制器
 * 
 * @author Tng
 * @version 1.0
 */
@Controller
@RequestMapping("${adminPath}/product")
public class ProductController extends BaseController {

	@Autowired
	private ProductService productService;

	@Autowired
	private ProductStockService productStockService;

	@Autowired
	private ProductDetailService productDetailService;
	
	@Autowired
	private OrganizationService organizationService;
	
	@Autowired
	private ProductSaleRuleService productSaleRuleService;
	
	@Autowired
	private ProductStatusLogService productStatusLogService;
	
	@Autowired
	private OrgGroupService orgGroupService;
	
	@Autowired
	private ProductSkuService productSkuService;
	
	@Autowired
	private ProductAttrService productAttrService;
	
	@ModelAttribute
	public Product get(@RequestParam(required = false) String id) {
		Product entity = null;
		if (JStringUtils.isNotBlank(id)) {
			entity = productService.get(id);
		}
		if (entity == null) {
			entity = new Product();
		}
		return entity;
	}

	@RequestMapping(value = "/findBrandName")
	public void findBrandName(Product product, HttpServletResponse response, Page<Product> page) {
		product.setOrgId(Long.valueOf(UserUtils.getLoginUser().getOrganization().getParentId()));
		page.setEntity(product);
		List<Product> productList = productService.findbrandNamePage(page);
		if (productList != null) {
			List<String> brandNameList = new ArrayList<String>();
			for (Product product2 : productList) {
				brandNameList.add(product2.getBrandName());
			}
			HashSet<String> brandNameSet = new HashSet<String>(brandNameList);
			brandNameList.clear();
			brandNameList.addAll(brandNameSet);
			String brandNameListJson = JSON.toJSONString(brandNameList);
			try {
				response.setCharacterEncoding("UTF-8");
				response.getWriter().write(brandNameListJson);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}


	@RequestMapping(value = "/list")
	public String list(Model model, Product product, Page<Product> page, String province, String city, String county,
			String profitStart, String profitEnd, String supplyPriceStart, String supplyPriceEnd,String saleType) {
		Organization organization = organizationService.get(UserUtils.getLoginUser().getOrganizationId());
		if(organization.getType() == OrgType.PLAT.getType() || organization.getType() == OrgType.COMP.getType()){
			product.setOrgId(null);//平台总公司查看所有商品
		}else{
			product.setOrgId(Long.valueOf(UserUtils.getLoginUser().getOrganizationId()));
		}
		product.setSaleType(saleType);
		page.setEntity(product);
		
		page.getCondition().put("province", province);
		page.getCondition().put("city", city);
		page.getCondition().put("county", county);
		page.getCondition().put("profitStart", profitStart);
		page.getCondition().put("profitEnd", profitEnd);
		page.getCondition().put("supplyPriceStart", supplyPriceStart);
		page.getCondition().put("supplyPriceEnd", supplyPriceEnd);
		page.setOrderBy("a.id desc");
		
		List<Product> productList = productService.findPage(page);

		if (product.getSource() == null || product.getSource() == 0) {
			page.setTotal(0);
			model.addAttribute("page", page);
			return "product/product/list_all";
		}
		model.addAttribute("page", page.setList(productList));
		if (product.getSource() == ProductSource.SUPPLY.getType()) {
			return "product/product/list_supply";
		}
		return "product/product/list";
	}
	
	/**
	 * 批量下架商品
	 * 
	 * @param product
	 */
	@ResponseBody
	@RequestMapping(value = "/batchOutShevlesProduct")
	public Result batchOutShevlesProduct(String[] productIdArr) {
		Result r = Result.newInstance();
		try{
			for (String id : productIdArr) {
				Product product = productService.get(id);
				productService.outShelves(product.getSource(),product.getItemCode());
			}
			r.setSuccess(true);
			return r;
		} catch (Exception e) {
			logger.error("批量下架商品失败：{}", e);
			return Result.newExceptionInstance();
		}
	}

	/**
	 * 我的商品（上下架）
	 * 
	 * @param model
	 * @param product
	 * @param page
	 * @return
	 */
	@RequestMapping(value = "/list/my")
	public String myProductList(Model model, Product product, Page<Product> page, String province, String city,
			String county, String profitStart, String profitEnd, String supplyPriceStart, String supplyPriceEnd,
			String saleType,
			Integer[] productArr) {
		
		Organization organization = organizationService.get(UserUtils.getLoginUser().getOrganizationId());
		if(organization.getType() == OrgType.PLAT.getType() || organization.getType() == OrgType.COMP.getType()){
			product.setOrgId(null);//平台总公司查看所有商品
		}else{
			product.setOrgId(Long.valueOf(UserUtils.getLoginUser().getOrganizationId()));
		}
		
		product.setSaleType(saleType);
		page.setEntity(product);

		StringBuffer sb = new StringBuffer();
		if (productArr != null && productArr.length != 0) {
			List<Integer> sourceList = new ArrayList<Integer>();
			for (Integer source : productArr) {
				sourceList.add(source);
				sb.append(source + "");
			}
			if (sourceList.contains(0)) {
				sourceList = null;
			}
			page.getCondition().put("sourceList", sourceList);
			
			model.addAttribute("productStr", sb.toString());
		}
		
		
		
		page.getCondition().put("province", province);
		page.getCondition().put("city", city);
		page.getCondition().put("county", county);
		page.getCondition().put("profitStart", profitStart);
		page.getCondition().put("profitEnd", profitEnd);
		page.getCondition().put("supplyPriceStart", supplyPriceStart);
		page.getCondition().put("supplyPriceEnd", supplyPriceEnd);
		page.getCondition().put("saleType", saleType);

		List<Product> productList = productService.findPage(page);
		model.addAttribute("page", page.setList(productList));
		if(product.getSource() == null){
			return "product/product/list_my_all";
		}
		
		if (product.getSource() == ProductSource.SUPPLY.getType()) {
			return "product/product/list_my_supply";
		}
		return "product/product/list_my";
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
				if(operStatus != ProductStatus.OUT_SHELVES.getType() && operStatus != ProductStatus.SHELVES.getType()){
					Product product = new Product();
					product.setId(id);
					product.setOperStatus(operStatus);
					productService.save(product);
				}
				
				//自动上下架
				if (operStatus == ProductStatus.OUT_SHELVES.getType()) {
					Set<String> xjSKUList = new HashSet<>(Arrays.asList(itemCodes));
					productService.batchOutShelves(ProductSource.SUPPLY.getType(), xjSKUList);
					
					//下架日志
					User loginUser = UserUtils.getLoginUser();
					Product product = productService.get(id);
					ProductStatusLog productStatusLog = new ProductStatusLog();
					productStatusLog.setProductId(Long.valueOf(product.getId()));
					productStatusLog.setOperStatus(product.getOperStatus());
					productStatusLog.setOperUserId(Long.valueOf(loginUser.getId()));
					productStatusLog.setOperator(loginUser.getName());
					productStatusLog.setOperTime(new Date());
					productStatusLog.setCreateBy(loginUser.getId());
					productStatusLogService.save(productStatusLog);
					
				} else if (operStatus == ProductStatus.SHELVES.getType()) {
					for (String itemCode : itemCodes) {

						
						Product existsProduct = new Product();
						existsProduct.setItemCode(itemCode);
						existsProduct.setSource(ProductSource.SUPPLY.getType());
						Product srcProduct = productService.getEntity(existsProduct);
						productService.autoShelvesProduct(existsProduct);
						
						
						ProductSaleRule entity = new ProductSaleRule();
						entity.setProductId(Long.valueOf(srcProduct.getId()));
						entity.setStatus("1");
						entity = productSaleRuleService.getEntity(entity );
						if(entity != null && StringUtils.isNotBlank(entity.getOrgGroups())){
							String[] orgGroupList = entity.getOrgGroups().split(",");
							for(String orgGroup: orgGroupList){
								productService.productMsg(srcProduct.getId(),orgGroup);
							}
						}
						
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


	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public String create(Product product, Model model) {
		model.addAttribute("product", product);
		return "product/product/form";
	}

	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public String create(Product product, RedirectAttributes redirectAttributes) {
		productService.save(product);
		addMessage(redirectAttributes, "新增成功");
		return "redirect:" + adminPath + "/product/update?id=" + product.getId();
	}

	@RequestMapping(value = "/update", method = RequestMethod.GET)
	public String update(Product product, Model model) {
		model.addAttribute("product", product);
		return "product/product/form";
	}

	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public String update(Product product, RedirectAttributes redirectAttributes) {
		
		
		productService.save(product);
		addMessage(redirectAttributes, "修改成功");
		return "redirect:" + adminPath + "/product/update?id=" + product.getId();
	}
	
	@RequestMapping(value = "/updateDetail", method = RequestMethod.POST)
	public String updateDetail(Product product, RedirectAttributes redirectAttributes) {
		ProductDetail detail = new ProductDetail();
		detail.setProductNo(Long.valueOf(product.getId()));
		detail = productDetailService.getEntity(detail);
		detail.setContent(product.getContent());
		productDetailService.save(detail);
		
		//修改商品名称
		Product productNew = new Product();
		productNew.setId(product.getId());
		productNew.setItemName(product.getItemName());
		productService.save(productNew);
		
		addMessage(redirectAttributes, "修改成功");
		return "redirect:" + adminPath + "/product/detail?id=" + product.getId();
	}

	@RequestMapping(value = "/{id}/delete", method = RequestMethod.GET)
	public String delete(@PathVariable("id") String id, int pageNo, int pageSize,
			RedirectAttributes redirectAttributes) {
		productService.delete(id);
		addMessage(redirectAttributes, "删除成功");
		return "redirect:" + adminPath + "/product?pageNo=" + pageNo + "&pageSize=" + pageSize;
	}

	/**
	 * 供应商库存
	 * 
	 * @param productNo
	 * @return
	 */
	@RequestMapping(value = "/getStock")
	@ResponseBody
	public Result getStock(Long productNo) {
		Result result = new Result();
		try {
			ProductStock stock = new ProductStock();
			stock.setProductNo(productNo);
			stock = productStockService.getEntity(stock);
			if (stock != null) {
				result.setCode(ResultCode.SUCCESS);
				result.setMsg("查询库存成功");
				result.setObj(stock);
				return result;
			}
			return result;
		} catch (Exception e) {
			logger.error("查询库存失败:{}", e);
			return Result.newExceptionInstance();
		}

	}

	/**
	 * 异步修改
	 * 
	 * @param product
	 * @return
	 */
	@RequestMapping(value = "/ajaxSave")
	@ResponseBody
	public Result ajaxUpdate(Product product) {
		Result result = new Result();

		try {
			if (product.getSupplyPrice() != null) {
				product.setProfitPercent(
						(product.getMarkPrice() - product.getSupplyPrice()) / product.getSupplyPrice() * 100);
			}
			productService.save(product);

			result.setCode(ResultCode.SUCCESS);
			result.setMsg("操作成功");
			return result;
		} catch (Exception e) {
			logger.error("修改商品信息失败:{}", e);
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
		product = productService.get(product.getId());

		ProductVo productVo = new ProductVo();
		// 商品信息
		productVo.setProductId(product.getId());
		productVo.setItemCode(product.getItemCode());
		productVo.setPhotos(product.getPhoto());
		productVo.setSaleType(product.getSaleType());
		productVo.setItemName(product.getItemName());
		productVo.setContent(product.getContent());
		productVo.setCateId(product.getItemCate());
		productVo.setCateName(product.getItemCateName());
		productVo.setSupplyId(product.getSupplyId());
		productVo.setSupplyName(product.getSupplyName());
		productVo.setBrand(product.getBrandName());
		productVo.setArea(product.getArea());
		productVo.setYearth(product.getYearth());
		productVo.setSeason(product.getSeason());
		productVo.setSalePrice(product.getSalePrice());
		productVo.setInPrice(product.getInPrice());
		productVo.setZjPrice(product.getZjPrice());
		productVo.setMgrPrice(product.getMgrPrice());
		productVo.setSource(product.getSource());
		productVo.setOperStatus(product.getOperStatus());
		productVo.setPhotoUrl(product.getPhotoUrl());
		productVo.setSaleNum(product.getSaleNum());
		productVo.setBuyerId(product.getBuyerId());
		productVo.setBuyerName(product.getBuyerName());

		// 详情
		ProductDetail productDetail = new ProductDetail();
		productDetail.setProductNo(product.getProductNo());
		productDetail = productDetailService.getEntity(productDetail);
		if (productDetail != null) {
			productVo.setContent(productDetail.getContent());
		}

		// 库存
		ProductStock productStock = new ProductStock();
		productStock.setProductNo(product.getProductNo());
		productStock = productStockService.getEntity(productStock);
		if (productStock != null) {
			productVo.setStock(productStock.getStockNum());
		}

		// 销售规则
		ProductSaleRule productSaleRule = new ProductSaleRule();
		productSaleRule.setProductId(Long.valueOf(product.getId()));
		productSaleRule = productSaleRuleService.getEntity(productSaleRule);
		if (productSaleRule != null) {
			productVo.setLimitStock(productSaleRule.getLimitStock());
			productVo.setEndDate(productSaleRule.getEndDate());
			productVo.setOrgGroups(productSaleRule.getOrgGroups());
			if (StringUtils.isNotBlank(productSaleRule.getOrgGroups())) {
				for (String orgGroupId : productSaleRule.getOrgGroups().split(",")) {
					OrgGroup orgGroup = orgGroupService.get(orgGroupId);
					if (orgGroup == null)
						continue;

					if (productVo.getOrgGroupsNames() == null) {
						productVo.setOrgGroupsNames(orgGroup.getGroupName());
					} else {
						productVo.setOrgGroupsNames(productVo.getOrgGroupsNames() + "," + orgGroup.getGroupName());
					}
				}
			}
			productVo.setSaleNotice(productSaleRule.getSaleNotice());
			productVo.setSaleNoticeUnit(productSaleRule.getSaleNoticeUnit());
			productVo.setSaleType(productSaleRule.getSaleType());
			productVo.setShipmentDate(productSaleRule.getShipmentDate());
		}

		// sku
		ProductSku productSku = new ProductSku();
		productSku.setProductId(Long.valueOf(product.getId()));
		List<ProductSku> productSkus = productSkuService.findList(productSku);
		if (CollectionUtils.isNotEmpty(productSkus)) {
			List<ProductSkuVo> skus = new ArrayList<>();
			for (ProductSku productSku2 : productSkus) {
				ProductSkuVo skuVo = new ProductSkuVo();
				skuVo.setSpecColor(productSku2.getSpecColor());
				skuVo.setSpecColorText(productSku2.getSpecColorText());
				skuVo.setSpecSize(productSku2.getSpecSize());
				skuVo.setSpecSizeText(productSku2.getSpecSizeText());
				skus.add(skuVo);
			}
			productVo.setSkus(skus);
		}
		// 属性
		ProductAttr productAttr = new ProductAttr();
		productAttr.setProductId(Long.valueOf(product.getId()));
		List<ProductAttr> productAttrs = productAttrService.findList(productAttr);
		if (CollectionUtils.isNotEmpty(productAttrs)) {
			List<PrductAttrVo> attrVos = new ArrayList<>();
			for (ProductAttr productAttr2 : productAttrs) {
				PrductAttrVo prductAttrVo = new PrductAttrVo();
				prductAttrVo.setAttrId(productAttr2.getAttrId());
				prductAttrVo.setAttrInfo(productAttr2.getAttrInfo());
				prductAttrVo.setAttrText(productAttr2.getAttrText());
				attrVos.add(prductAttrVo);
			}
			productVo.setAttrs(attrVos);
		}

		model.addAttribute("product", productVo);
		return "product/product/detail";
	}
	
	@RequestMapping("toEditDetailPage")
	public String toEditDetailPage(Model model,Product product){
		product = productService.get(product.getId());
		
		ProductDetail productDetail = new ProductDetail();
		productDetail.setStatus("1");
		productDetail.setProductNo(product.getProductNo());
		productDetail = productDetailService.getEntity(productDetail);
		if(productDetail != null)
		product.setContent(productDetail.getContent());
		
		model.addAttribute("product", product);
		return "product/product/editDetail";
	}

}
