package com.jf.plus.api.buyer.mobile.admin.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.google.common.base.Joiner;
import com.jf.plus.api.buyer.mobile.controller.BaseController;
import com.jf.plus.common.core.Result;
import com.jf.plus.common.core.ResultCode;
import com.jf.plus.common.core.ResultObj;
import com.jf.plus.common.core.enums.ProductSource;
import com.jf.plus.common.core.enums.ProductStatus;
import com.jf.plus.common.core.enums.SaleType;
import com.jf.plus.common.utils.StringUtils;
import com.jf.plus.common.utils.jiandao.JianDaoProductAPI;
import com.jf.plus.common.vo.PrductAttrVo;
import com.jf.plus.common.vo.ProductSkuVo;
import com.jf.plus.common.vo.ProductVo;
import com.jf.plus.core.mall.entity.MallProductCate;
import com.jf.plus.core.mall.entity.MallSupplyer;
import com.jf.plus.core.mall.service.MallProductCateService;
import com.jf.plus.core.mall.service.MallSupplyerService;
import com.jf.plus.core.product.entity.CategoryAttr;
import com.jf.plus.core.product.entity.CategoryAttrInfo;
import com.jf.plus.core.product.entity.MallProductBrand;
import com.jf.plus.core.product.entity.OrgGroup;
import com.jf.plus.core.product.entity.OrgGroupMerchant;
import com.jf.plus.core.product.entity.Product;
import com.jf.plus.core.product.entity.ProductAttr;
import com.jf.plus.core.product.entity.ProductDetail;
import com.jf.plus.core.product.entity.ProductSaleRule;
import com.jf.plus.core.product.entity.ProductSaleRuleMerchant;
import com.jf.plus.core.product.entity.ProductSku;
import com.jf.plus.core.product.entity.ProductStatusLog;
import com.jf.plus.core.product.entity.ProductStock;
import com.jf.plus.core.product.entity.SpecLib;
import com.jf.plus.core.product.service.CategoryAttrInfoService;
import com.jf.plus.core.product.service.CategoryAttrService;
import com.jf.plus.core.product.service.MallProductBrandService;
import com.jf.plus.core.product.service.OrgGroupMerchantService;
import com.jf.plus.core.product.service.OrgGroupService;
import com.jf.plus.core.product.service.ProductAttrService;
import com.jf.plus.core.product.service.ProductDetailService;
import com.jf.plus.core.product.service.ProductSaleRuleMerchantService;
import com.jf.plus.core.product.service.ProductSaleRuleService;
import com.jf.plus.core.product.service.ProductService;
import com.jf.plus.core.product.service.ProductSkuService;
import com.jf.plus.core.product.service.ProductStatusLogService;
import com.jf.plus.core.product.service.ProductStockService;
import com.jf.plus.core.product.service.SpecLibService;
import com.jf.plus.core.setting.service.AppcodeService;

import cn.iutils.common.Page;
import cn.iutils.common.utils.MPageInfo;
import cn.iutils.sys.entity.User;
import cn.iutils.sys.service.UserService;

/**
 * 买手端-商品 控制器
 * 
 * @author Tng
 *
 */
@Controller("buyerProductController")
public class ProductController extends BaseController {
	private static final Logger LOGGER = LoggerFactory.getLogger(ProductController.class);

	@Autowired
	AppcodeService appcodeService;
	@Autowired
	UserService userService;
	@Autowired
	MallSupplyerService mallSupplyerService;
	@Autowired
	ProductService productService;
	@Autowired
	CategoryAttrService categoryAttrService;
	@Autowired
	CategoryAttrInfoService categoryAttrInfoService;
	@Autowired
	SpecLibService specLibService;
	@Autowired
	OrgGroupService orgGroupService;
	@Autowired
	ProductDetailService productDetailService;
	@Autowired
	ProductStockService productStockService;
	@Autowired
	ProductSaleRuleService productSaleRuleService;
	@Autowired
	ProductSkuService productSkuService;
	@Autowired
	ProductAttrService productAttrService;
	@Autowired
	MallProductBrandService mallProductBrandService;
	@Autowired
	MallProductCateService mallProductCateService;
	
	@Autowired
	ProductStatusLogService productStatusLogService;
	
	@Autowired
	OrgGroupMerchantService orgGroupMerchantService;
	
	@Autowired
	ProductSaleRuleMerchantService productSaleRuleMerchantService;

	/**
	 * 发布商品
	 */
	@RequestMapping("/api/buyer/submitProduct")
	@ResponseBody
	public Result submitProduct(@RequestBody ProductVo productVo) {
		Result result = new Result();
		try {
			if (productVo == null || productVo.getItemName() == null || productVo.getSaleType() == null) {
				result.setCode(ResultCode.ARGUMENT_LACK_ERROR);
				result.setMsg("参数不完整");
				return result;
			}

			result = appcodeService.checkToken(productVo.getToken());
			if (!result.isSuccess()) {
				return result;
			}

			User buyer = userService.get(result.getObj().toString());
			productVo.setBuyerId(Long.valueOf(buyer.getId()));
			productVo.setBuyerName(buyer.getName());

			// ID存在编辑
			if (StringUtils.isNotBlank(productVo.getProductId())) {
				result = productService.updateProduct(productVo);
			} else {
				result = productService.submitProduct(productVo);
			}
			if (!result.isSuccess()) {
				return result;
			}

			ResultObj resObj = new ResultObj();
			resObj.put("productId", result.getObj());
			result.setObj(resObj);
			result.setCode(ResultCode.SUCCESS);
			return result;
		} catch (Exception e) {
			LOGGER.error("系统异常:{}", e);
			return Result.newExceptionInstance();
		}

	}

	/**
	 * 管理费生成规则
	 */
	@RequestMapping("/api/buyer/calcMgrPrice")
	@ResponseBody
	public Result calcMgrPrice(ProductVo productVo) {
		Result result = new Result();
		try {

			if (StringUtils.isBlank(productVo.getSaleType()) || StringUtils.isBlank(productVo.getCateName())
					|| productVo.getInPrice() == null || productVo.getZjPrice() == null) {
				result.setMsg("参数不正确");
				result.setCode(ResultCode.ARGUMENT_LACK_ERROR);
				ResultObj resObj = new ResultObj();
				resObj.put("mgrPrice", 0);
				result.setObj(resObj);
				return result;
			}

			Double mgrPrice = productService.calcMgrPrice(productVo);

			ResultObj resObj = new ResultObj();
			resObj.put("mgrPrice", mgrPrice.doubleValue());
			result.setObj(resObj);
			result.setCode(ResultCode.SUCCESS);
			return result;
		} catch (Exception e) {
			LOGGER.error("系统异常:{}", e);
			return Result.newExceptionInstance();
		}
	}

	/**
	 * 供应商列表
	 */
	@RequestMapping("/api/buyer/supplyerList")
	@ResponseBody
	public Result supplyList(String token,String area) {
		Result result = new Result();
		try {
			MallSupplyer entity = new MallSupplyer();
			entity.setStatus("1");
			entity.setArea(area);
			List<MallSupplyer> mallSupplyerList = mallSupplyerService.findList(entity);

			ResultObj resObj = new ResultObj();
			resObj.put("supplyerList", mallSupplyerList);
			result.setObj(resObj);
			result.setCode(ResultCode.SUCCESS);
			return result;
		} catch (Exception e) {
			LOGGER.error("系统异常:{}", e);
			return Result.newExceptionInstance();
		}
	}

	/**
	 * 品牌列表
	 */
	@RequestMapping("/api/buyer/brandList")
	@ResponseBody
	public Result brandList(@RequestParam String token,String supplyId) {
		Result result = new Result();
		try {
			result = appcodeService.checkToken(token);
			if (!result.isSuccess()) {
				return result;
			}
			User user = userService.get(result.getObj().toString());

			MallProductBrand mallProductBrand = new MallProductBrand();
			//mallProductBrand.setOrgId(Long.valueOf(user.getOrganizationIds().split(",")[0]));
			mallProductBrand.setType("PUBLIC");//公有
			mallProductBrand.setStatus("1");
			List<MallProductBrand> brandList = mallProductBrandService.findList(mallProductBrand);
			
			if(StringUtils.isNotBlank(supplyId)){
				MallSupplyer mallSupplyer = mallSupplyerService.get(supplyId);
				if(StringUtils.isNotBlank(mallSupplyer.getBrandIds())){
					for(String id : mallSupplyer.getBrandIds().split(",")){
						brandList.add(mallProductBrandService.get(id));
					}
				}
			}
			
			//去重复
			Set<MallProductBrand> brandSet = new HashSet<MallProductBrand>(brandList);
			brandList.clear();
			brandList.addAll(brandSet);

			ResultObj resObj = new ResultObj();
			resObj.put("brandList", brandList);
			result.setObj(resObj);
			result.setCode(ResultCode.SUCCESS);
			return result;
		} catch (Exception e) {
			LOGGER.error("系统异常:{}", e);
			return Result.newExceptionInstance();
		}
	}

	/**
	 * 获取站点商品分类列表
	 *
	 * @param siteId
	 * @param page
	 * @return
	 */
	@RequestMapping(value = "/api/buyer/cateList", method = { RequestMethod.POST })
	@ResponseBody
	public Result cateList(@RequestParam String token) {
		Result result = Result.newInstance();
		try {
			result = appcodeService.checkToken(token);
			if (!result.isSuccess()) {
				return result;
			}

			MallProductCate entity = new MallProductCate();
			entity.setCatPid(1L);
			entity.setStatus("1");
			entity.setOrderBy("a.sort asc,a.id desc");
			List<MallProductCate> cateList = mallProductCateService.findList(entity);
			for (MallProductCate mallProductCate : cateList) {
				entity.setCatPid(Long.valueOf(mallProductCate.getId()));
				mallProductCate.setChildrens(mallProductCateService.findList(entity));
			}

			ResultObj resultObj = new ResultObj();
			resultObj.put("cateList", cateList);
			result.setObj(resultObj);
			result.setCode(ResultCode.SUCCESS);
			return result;
		} catch (Exception e) {
			LOGGER.error("获取站点商品分类列表失败：", e);
			result = Result.newExceptionInstance();
			return result;
		}
	}

	/**
	 * 商品属性列表
	 */
	@RequestMapping("/api/buyer/cateAttrList")
	@ResponseBody
	public Result cateAttrList(@RequestParam Long cateId) {
		Result result = new Result();
		try {
			CategoryAttr entity = new CategoryAttr();
			entity.setCateId(cateId);
			entity.setStatus("1");
			List<CategoryAttr> categoryAttrs = categoryAttrService.findList(entity);
			if (CollectionUtils.isNotEmpty(categoryAttrs)) {
				for (CategoryAttr categoryAttr : categoryAttrs) {
					CategoryAttrInfo _entity = new CategoryAttrInfo();
					_entity.setAttrId(Long.valueOf(categoryAttr.getId()));
					_entity.setStatus("1");
					categoryAttr.setCategoryAttrInfos(categoryAttrInfoService.findList(_entity));
				}
			}

			ResultObj resObj = new ResultObj();
			resObj.put("cateAttrList", categoryAttrs);
			result.setObj(resObj);
			result.setCode(ResultCode.SUCCESS);
			return result;
		} catch (Exception e) {
			LOGGER.error("系统异常:{}", e);
			return Result.newExceptionInstance();
		}
	}

	/**
	 * 商品详情
	 */
	@RequestMapping("/api/buyer/productDetail")
	@ResponseBody
	public Result productDetail(@RequestParam String productId) {
		Result result = new Result();
		try {

			Product product = productService.get(productId);
			if (product == null) {
				result.setCode(ResultCode.ARGUMENT_LACK_ERROR);
				result.setMsg("商品不存在");
				return result;
			}

			ProductVo productVo = new ProductVo();
			// 商品信息
			productVo.setProductId(productId);
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
					List<OrgGroup> orgGroupList = new ArrayList<>();
					for (String orgGroupId : productSaleRule.getOrgGroups().split(",")) {
						OrgGroup orgGroup = orgGroupService.get(orgGroupId);
						orgGroupList.add(orgGroup);
						
						if (orgGroup == null)
							continue;

						if (productVo.getOrgGroupsNames() == null) {
							productVo.setOrgGroupsNames(orgGroup.getGroupName());
						} else {
							productVo.setOrgGroupsNames(productVo.getOrgGroupsNames() + "," + orgGroup.getGroupName());
						}
						
						ProductSaleRuleMerchant productSaleRuleMerchant  = new ProductSaleRuleMerchant();
						productSaleRuleMerchant.setOrgGroup(orgGroupId);
						productSaleRuleMerchant.setSaleRuleId(Long.valueOf(productSaleRule.getId()));
						productSaleRuleMerchant.setOrderBy(" a.id asc ");
						List<ProductSaleRuleMerchant> productSaleRuleMerchants = productSaleRuleMerchantService.findList(productSaleRuleMerchant);
						if(CollectionUtils.isNotEmpty(productSaleRuleMerchants)){
							List<OrgGroupMerchant> merchantList = new ArrayList<>();
							
							for(ProductSaleRuleMerchant productSaleRuleMerchant2 : productSaleRuleMerchants){
								OrgGroupMerchant orgGroupMerchant = new OrgGroupMerchant();
								orgGroupMerchant.setMerchantId(Long.valueOf(productSaleRuleMerchant2.getGroupMerchant()));
								
								merchantList.add(orgGroupMerchant);
							}
							
							orgGroup.setMerchantList(merchantList);
						}
						
						
					}
					productVo.setOrgGroups(JSONArray.toJSONString(orgGroupList));
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

			ResultObj resObj = new ResultObj();
			resObj.put("productDetail", productVo);
			result.setObj(resObj);
			result.setCode(ResultCode.SUCCESS);
			return result;
		} catch (Exception e) {
			LOGGER.error("系统异常:{}", e);
			return Result.newExceptionInstance();
		}
	}

	/**
	 * 規格列表
	 */
	@RequestMapping("/api/buyer/specLibList")
	@ResponseBody
	public Result specLibList() {
		Result result = new Result();
		try {

			SpecLib entity = new SpecLib();
			entity.setStatus("1");
			List<SpecLib> specLibList = specLibService.findList(entity);

			ResultObj resObj = new ResultObj();
			resObj.put("specLibList", specLibList);
			result.setObj(resObj);
			result.setCode(ResultCode.SUCCESS);
			return result;
		} catch (Exception e) {
			LOGGER.error("系统异常:{}", e);
			return Result.newExceptionInstance();
		}
	}

	/**
	 * 代理商分組列表
	 */
	@RequestMapping("/api/buyer/orgGroupList")
	@ResponseBody
	public Result orgGroupList(String token) {
		Result result = new Result();
		try {

			OrgGroup entity = new OrgGroup();
			entity.setStatus("1");
			entity.setOrderBy(" a.sort asc ");
			List<OrgGroup> orgGroupList = orgGroupService.findList(entity);
			for(OrgGroup orgGroup : orgGroupList){
				OrgGroupMerchant orgGroupMerchant = new OrgGroupMerchant();
				orgGroupMerchant.setStatus("1");
				orgGroupMerchant.setGroupId(Long.valueOf(orgGroup.getId()));
				orgGroup.setMerchantList(orgGroupMerchantService.findList(orgGroupMerchant));
			}

			ResultObj resObj = new ResultObj();
			resObj.put("orgGroupList", orgGroupList);
			result.setObj(resObj);
			result.setCode(ResultCode.SUCCESS);
			return result;
		} catch (Exception e) {
			LOGGER.error("系统异常:{}", e);
			return Result.newExceptionInstance();
		}
	}

	/**
	 * 更改商品状态
	 */
	@RequestMapping("/api/buyer/changeProductStatus")
	@ResponseBody
	public Result changeProductStatus(@RequestParam String token,@RequestParam String productId, @RequestParam Integer operStatus) {
		Result result = new Result();
		try {
			
			result = appcodeService.checkToken(token);
			if(!result.isSuccess()){
				return result;
			}
			
			User user = userService.get(result.getObj().toString());
			
			Product product = new Product();
			product.setId(productId);
			product.setOperStatus(operStatus);
			productService.save(product);

			product = productService.get(productId);

			// 自动上下架
			if (operStatus == ProductStatus.OUT_SHELVES.getType()) {
				Set<String> xjSKUList = new HashSet<>();
				xjSKUList.add(product.getItemCode());
				productService.batchOutShelves(ProductSource.SUPPLY.getType(), xjSKUList);
				
				//下架日志
				ProductStatusLog productStatusLog = new ProductStatusLog();
				productStatusLog.setProductId(Long.valueOf(product.getId()));
				productStatusLog.setOperStatus(product.getOperStatus());
				productStatusLog.setOperUserId(Long.valueOf(user.getId()));
				productStatusLog.setOperator(user.getName());
				productStatusLog.setOperTime(new Date());
				productStatusLog.setCreateBy(user.getId());
				productStatusLogService.save(productStatusLog);
				
			}

			result.setMsg("操作成功");
			result.setCode(ResultCode.SUCCESS);
			return result;
		} catch (Exception e) {
			LOGGER.error("系统异常:{}", e);
			return Result.newExceptionInstance();
		}
	}

	/**
	 * 商品列表
	 */
	@RequestMapping("/api/buyer/productList")
	@ResponseBody
	public Result productList(@RequestParam String token, @RequestParam(defaultValue = "2") Integer source,
			String saleType, Integer operStatus, String itemName, Page<Product> page) {
		Result result = new Result();
		try {
			result = appcodeService.checkToken(token);
			if (!result.isSuccess()) {
				return result;
			}
			Product product = new Product();
			product.setSource(source);
			product.setSaleType(saleType);
			product.setOperStatus(operStatus);
			product.setBuyerId(Long.valueOf(result.getObj().toString()));
			product.setItemName(itemName);
			page.setEntity(product);
			page.setList(productService.findPage(page));

			ResultObj resObj = new ResultObj();
			resObj.put("page", MPageInfo.transform(page));
			result.setObj(resObj);
			result.setCode(ResultCode.SUCCESS);
			return result;
		} catch (Exception e) {
			LOGGER.error("系统异常:{}", e);
			return Result.newExceptionInstance();
		}
	}
	
	@RequestMapping("/api/confirmOrder")
	@ResponseBody
	public Result confirmOrder(@RequestParam String productId,@RequestParam String token) {
		Result result = new Result();

		try {
			result = appcodeService.checkToken(token);
			if (!result.isSuccess()) {
				return result;
			}
			
			Product product = productService.get(productId);
			if(SaleType.KH.getDescription().equals(product.getSaleType()) 
					&& product.getOperStatus() == ProductStatus.SHELVES.getType()){
				result.setMsg("砍货商品下架后才可确认采购");
				result.setCode(ResultCode.RETURN_FAILURE);
				return result;
			}
			
//			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			
			String itemCode = product.getItemCode();
			String shelvesDate = sdf.format(product.getShelvesDate());
			String supplyName = product.getSupplyName();
			String itemName = product.getItemName();
			int saleNum = product.getSaleNum();
//			String createDate = sdf.format(product.getCreateDate());
			String createDate = sdf.format(new Date());
			String buyer = product.getBuyerName();
			try{
				// sku
				ProductSku productSku = new ProductSku();
				productSku.setProductId(Long.valueOf(product.getId()));
				List<ProductSku> productSkus = productSkuService.findList(productSku);
				Set<String> specColorList = new HashSet<>();
				Set<String> specSizeList = new HashSet<>();
				if (CollectionUtils.isNotEmpty(productSkus)) {
					for (ProductSku productSku2 : productSkus) {
						specColorList.add(productSku2.getSpecColorText());
						specSizeList.add(productSku2.getSpecSizeText());
					}
				}
				String specColor = specColorList.size() > 0 ? Joiner.on(",").join(specColorList) : "";
				String specSize = specSizeList.size() > 0 ? Joiner.on(",").join(specSizeList) : "";
				
				LOGGER.info("推送简道云|创建订货商品请求参数:itemCode:"+itemCode+",shelvesDate:"+shelvesDate+",supplyName:"+supplyName+",itemName:"+itemName
						+",saleNum:"+saleNum+",specColor:"+specColor+",specSize:"+specSize+",createDate:"+createDate+",buyer:"+buyer);
				Map<String, Object> createMap = JianDaoProductAPI.create(itemCode, shelvesDate, supplyName, itemName, saleNum, specColor, specSize, createDate, buyer);
				LOGGER.info("推送简单云|创建订货商品返回参数:"+JSON.toJSONString(createMap));
				if(createMap == null || createMap.get("data") == null){
					result.setMsg("推送失败：简道云接口异常");
					result.setCode(ResultCode.RETURN_FAILURE);
					return result;
				}
			}catch(Exception e){
				result.setMsg("推送失败："+e.getMessage());
				result.setCode(ResultCode.RETURN_FAILURE);
				return result;
			}
			
			result.setMsg("操作成功");
			result.setCode(ResultCode.SUCCESS);
			return result;
		} catch (Exception e) {
			LOGGER.error("系统异常：{}", e);
			return Result.newExceptionInstance();
		}
		
	}
}
