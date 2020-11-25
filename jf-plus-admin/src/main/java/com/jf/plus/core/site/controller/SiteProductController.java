package com.jf.plus.core.site.controller;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
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
import com.jf.plus.common.core.enums.ProductSource;
import com.jf.plus.common.core.enums.ProductStatus;
import com.jf.plus.core.mall.entity.MallChannelCate;
import com.jf.plus.core.mall.service.MallChannelCateService;
import com.jf.plus.core.product.entity.Product;
import com.jf.plus.core.product.service.ProductService;
import com.jf.plus.core.site.entity.SiteCate;
import com.jf.plus.core.site.entity.SiteProduct;
import com.jf.plus.core.site.service.SiteAdvertService;
import com.jf.plus.core.site.service.SiteCateService;
import com.jf.plus.core.site.service.SiteProductService;

import cn.iutils.common.BaseController;
import cn.iutils.common.Page;
import cn.iutils.common.utils.JStringUtils;
import cn.iutils.common.utils.UserUtils;

/**
 * 站点商品表 控制器
 * 
 * @author Tng
 * @version 1.0
 */
@Controller
@RequestMapping("${adminPath}/site/siteProduct")
public class SiteProductController extends BaseController {

	@Autowired
	private SiteProductService siteProductService;

	@Autowired
	private ProductService productService;

	@Autowired
	private SiteCateService siteCateService;

	@Autowired
	private SiteAdvertService siteAdvertService;

	@Autowired
	private MallChannelCateService mallChannelCateService;

	@ModelAttribute
	public SiteProduct get(@RequestParam(required = false) String id) {
		SiteProduct entity = null;
		if (JStringUtils.isNotBlank(id)) {
			entity = siteProductService.get(id);
		}
		if (entity == null) {
			entity = new SiteProduct();
		}
		return entity;
	}

	@RequestMapping(value = "/list")
	public String list(Model model, Product product, Page<Product> page, String province, String city, String county,
			String profitStart, String profitEnd, String supplyPriceStart, String supplyPriceEnd) {
		if (UserUtils.getMySite() == null) {
			model.addAttribute("errorMsg", "您没有管理站点权限，请联系管理员");
			return "error/400";
		}
		
		product.setOrgId(Long.valueOf(UserUtils.getLoginUser().getOrganizationId()));
		page.setEntity(product);

		page.getCondition().put("province", province);
		page.getCondition().put("city", city);
		page.getCondition().put("county", county);
		page.getCondition().put("profitStart", profitStart);
		page.getCondition().put("profitEnd", profitEnd);
		page.getCondition().put("supplyPriceStart", supplyPriceStart);
		page.getCondition().put("supplyPriceEnd", supplyPriceEnd);
		page.getCondition().put("filterPickStatusForSite", ProductStatus.TO_PICK.getType());
		page.getCondition().put("filterCurrSiteId", UserUtils.getMySite().getSiteId());
		page.getCondition().put("filterCurrOrgIdForSite", UserUtils.getLoginUser().getOrganizationId());//当前机构id

		List<Product> productList = productService.findPage(page);
		for (Product prod : productList) {
			prod.setOperStatus(ProductStatus.TO_PICK.getType());
//			if (isMyProductPool(prod.getProductNo())) {
//				prod.setOperStatus(ProductStatus.PICK.getType());
//			} else {
//				prod.setOperStatus(ProductStatus.TO_PICK.getType());
//			}
		}

		model.addAttribute("page", page.setList(productList));
		if (product.getSource() == ProductSource.SUPPLY.getType()) {
			return "site/siteProduct/list_supply";
		}
		return "site/siteProduct/list";
	}

	/**
	 * 判断是否在商品池中
	 * 
	 * @param productNo
	 * @return
	 */
	private boolean isMyProductPool(Long productNo) {
		Page<SiteProduct> page = new Page<SiteProduct>();
		SiteProduct product = new SiteProduct();
		product.setProductNo(productNo);
		product.setOrgId(Long.valueOf(UserUtils.getLoginUser().getOrganizationId()));
		product.setSiteId(Long.valueOf(UserUtils.getMySite().getSiteId()));
		page.setEntity(product);
		return siteProductService.countPageEntity(page) > 0;
	}

	/**
	 * 挑选商品
	 * 
	 * @param productId
	 * @param salePrice
	 * @return
	 */
	@RequestMapping(value = "/prePick")
	@ResponseBody
	public Result prePick(String productId, Double salePrice) {
		Result result = Result.newInstance();
		try {
			Product product = productService.get(productId);
			SiteProduct productPrePick = new SiteProduct();
			productPrePick.setProductId(Long.valueOf(productId));
			productPrePick.setProductNo(product.getProductNo());
			productPrePick.setOrgId(Long.valueOf(UserUtils.getLoginUser().getOrganizationId()));
			productPrePick.setSiteId(Long.valueOf(UserUtils.getMySite().getSiteId()));
			productPrePick.setSupplyPrice(product.getSupplyPrice());
			productPrePick.setMarkPrice(product.getMarkPrice());
			productPrePick.setSalePrice(salePrice);
			productPrePick.setOperStatus(ProductStatus.SHELVES.getType());
			Double profitPercent = ((productPrePick.getSalePrice() - productPrePick.getSupplyPrice())
					/ productPrePick.getSupplyPrice()) * 100;
			productPrePick.setProfitPercent(profitPercent);
			productPrePick.setSource(product.getSource());
			siteProductService.save(productPrePick);

			// 维护站点分类表数据
			initSiteCate(product.getItemCate());

			result.setCode(ResultCode.SUCCESS);
			result.setMsg("挑选成功");
			return result;
		} catch (Exception e) {
			logger.error("挑选失败：{}", e);
			return Result.newExceptionInstance();

		}
	}

	/**
	 * 站点挑选商品关联分类
	 * 
	 * @param cateIds
	 */
	private void initSiteCate(String cateIds) {
		if (StringUtils.isBlank(cateIds))
			return;

		String[] cates = cateIds.split(",");
		for (int i = cates.length - 1; i >= 0; i--) {

			// 获取关联分类
			MallChannelCate mallChannelCate = mallChannelCateService.get(cates[i]);
			if (mallChannelCate == null || StringUtils.isBlank(mallChannelCate.getProductCateIds())) {
				continue;
			}

			String productCateId = mallChannelCate.getProductCateIds();

			Page<SiteCate> page = new Page<SiteCate>();
			page.getCondition().put("siteId", UserUtils.getMySite().getSiteId());
			page.getCondition().put("cateId", productCateId);
			int count = siteCateService.count(page);
			if (count > 0) {
				continue;
			}

			SiteCate siteCate = new SiteCate();
			siteCate.setOrgId(Long.valueOf(UserUtils.getLoginUser().getOrganizationId()));
			siteCate.setSiteId(Long.valueOf(UserUtils.getMySite().getSiteId()));
			siteCate.setCateId(Long.valueOf(productCateId));
			siteCate.setCateName(mallChannelCate.getCatName());
			siteCateService.save(siteCate);
		}
	}

	/**
	 * 批量下架商品
	 * 
	 * @param product
	 */
	@ResponseBody
	@RequestMapping(value = "/batchOutShevlesProduct")
	public Result batchOutShevlesProduct(String[] siteProductIdArr) {
		Result r = Result.newInstance();
		try {
			for (String productId : siteProductIdArr) {
				SiteProduct siteProduct = siteProductService.get(productId);
				siteProduct.setOperStatus(ProductStatus.OUT_SHELVES.getType());
				siteProductService.save(siteProduct);
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
	 * @param siteProduct
	 * @param page
	 * @return
	 */
	@RequestMapping(value = "/list/my")
	public String myProductList(Model model, SiteProduct siteProduct, Page<SiteProduct> page, String province,
			String city, String county, String profitStart, String profitEnd, String supplyPriceStart,
			String supplyPriceEnd, String supplyName) {
		if (UserUtils.getMySite() == null) {
			model.addAttribute("errorMsg", "您没有管理站点权限，请联系管理员");
			return "error/400";
		}

		page.getCondition().put("channelItemCate", siteProduct.getItemCate());
		page.getCondition().put("channelItemCateName", siteProduct.getItemCateName());
		page.getCondition().put("orgId", UserUtils.getLoginUser().getOrganizationId());
		page.getCondition().put("siteId", UserUtils.getMySite().getSiteId());
		page.getCondition().put("operStatus", siteProduct.getOperStatus());
		page.getCondition().put("source", siteProduct.getSource());

		page.getCondition().put("province", province);
		page.getCondition().put("city", city);
		page.getCondition().put("county", county);
		page.getCondition().put("profitStart", profitStart);
		page.getCondition().put("profitEnd", profitEnd);
		page.getCondition().put("supplyPriceStart", supplyPriceStart);
		page.getCondition().put("supplyPriceEnd", supplyPriceEnd);
		page.getCondition().put("supplyName", supplyName);

		page.getCondition().put("itemName", siteProduct.getItemName());
		page.getCondition().put("itemCode", siteProduct.getItemCode());

		List<SiteProduct> productList = siteProductService.findPage(page);
		model.addAttribute("page", page.setList(productList));
		if (siteProduct.getSource() == ProductSource.SUPPLY.getType()) {
			return "site/siteProduct/list_my_supply";
		}
		return "site/siteProduct/list_my";
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
	public Result changeStatus(String[] ids, Integer operStatus) {
		Result result = Result.newInstance();
		try {
			for (String id : ids) {
				SiteProduct product = new SiteProduct();
				product.setId(id);
				product.setOperStatus(operStatus);
				siteProductService.save(product);
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
	public String create(SiteProduct siteProduct, Model model) {
		model.addAttribute("siteProduct", siteProduct);
		return "site/siteProduct/form";
	}

	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public String create(SiteProduct siteProduct, RedirectAttributes redirectAttributes) {
		siteProductService.save(siteProduct);
		addMessage(redirectAttributes, "新增成功");
		return "redirect:" + adminPath + "/site/siteProduct/update?id=" + siteProduct.getId();
	}

	@RequestMapping(value = "/update", method = RequestMethod.GET)
	public String update(SiteProduct siteProduct, Model model) {
		model.addAttribute("siteProduct", siteProduct);
		return "site/siteProduct/form";
	}

	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public String update(SiteProduct siteProduct, RedirectAttributes redirectAttributes) {
		siteProductService.save(siteProduct);
		addMessage(redirectAttributes, "修改成功");
		return "redirect:" + adminPath + "/site/siteProduct/update?id=" + siteProduct.getId();
	}

	@RequestMapping(value = "/{id}/delete", method = RequestMethod.GET)
	public String delete(@PathVariable("id") String id, int pageNo, int pageSize,
			RedirectAttributes redirectAttributes) {
		siteProductService.delete(id);
		addMessage(redirectAttributes, "删除成功");
		return "redirect:" + adminPath + "/site/siteProduct?pageNo=" + pageNo + "&pageSize=" + pageSize;
	}

	/**
	 * 选择商品
	 * 
	 * @param model
	 * @param siteProduct
	 * @param page
	 * @return
	 */
	@RequestMapping(value = "/select")
	public String select(Model model, SiteProduct siteProduct, String advertId, String items, Page<SiteProduct> page) {
		page.getCondition().put("siteId", UserUtils.getMySite().getSiteId());

		// 查询条件
		page.getCondition().put("operStatus", siteProduct.getOperStatus());
		page.getCondition().put("source", siteProduct.getSource());
		page.getCondition().put("itemCode", siteProduct.getItemCode());
		page.getCondition().put("itemName", siteProduct.getItemName());
		page.setOrderBy("id");

		// 过滤掉已经存在商品
		items = siteAdvertService.get(advertId).getItems();
		if (StringUtils.isNotBlank(items)) {
			page.getCondition().put("inz", "not in");
			page.getCondition().put("items", items.split(","));
		}
		List<SiteProduct> productList = siteProductService.findPage(page);
		model.addAttribute("page", page.setList(productList));
		model.addAttribute("advertId", advertId);
		model.addAttribute("items", items);
		return "select/siteProduct/select";
	}

	/**
	 * 批量挑选
	 * 
	 * @param productIds
	 * @param priceWay
	 * @param priceType
	 * @param priceAdjust
	 * @param priceVal
	 * @param priceUnit
	 * @return
	 */
	@RequestMapping("batchPrePick")
	@ResponseBody
	public Result batchPrePick(String productIds, String priceWay, String priceType, String priceAdjust,
			Double priceVal, String priceUnit) {
		Result result = Result.newInstance();
		try {
			List<SiteProduct> toPrePickList = new ArrayList<>();
			String[] productIdArry = productIds.split(",");
			for (String productId : productIdArry) {
				Product product = productService.get(productId);
				SiteProduct productPrePick = new SiteProduct();

				productPrePick.setProductId(Long.valueOf(productId));
				productPrePick.setProductNo(product.getProductNo());
				productPrePick.setOrgId(Long.valueOf(UserUtils.getLoginUser().getOrganizationId()));
				productPrePick.setSiteId(Long.valueOf(UserUtils.getMySite().getSiteId()));
				productPrePick.setOperStatus(ProductStatus.SHELVES.getType());
				productPrePick.setSource(product.getSource());
				productPrePick.setSupplyPrice(product.getSupplyPrice());
				productPrePick.setMarkPrice(product.getMarkPrice());

				if (priceWay.equals("eq")) {// 等于
					if (product != null && priceType.equals("supplyPrice")) {
						productPrePick.setSalePrice(product.getSupplyPrice());
					} else {
						productPrePick.setSalePrice(product.getMarkPrice());
					}
				} else {// 按照比例
					if (product != null && priceType.equals("supplyPrice")) {
						if (priceAdjust.equals("incr")) {
							if (priceUnit.equals("rmb")) {
								productPrePick.setSalePrice(product.getSupplyPrice() + priceVal);
							} else {
								productPrePick.setSalePrice(product.getSupplyPrice() * (1 + priceVal / 100));
							}
						} else {
							if (priceUnit.equals("rmb")) {
								productPrePick.setSalePrice(product.getSupplyPrice() - priceVal);
							} else {
								productPrePick.setSalePrice(product.getSupplyPrice() * (1 - priceVal / 100));
							}
						}
					} else {
						if (priceAdjust.equals("incr")) {
							if (priceUnit.equals("rmb")) {
								productPrePick.setSalePrice(product.getMarkPrice() + priceVal);
							} else {
								productPrePick.setSalePrice(product.getMarkPrice() * (1 + priceVal / 100));
							}
						} else {
							if (priceUnit.equals("rmb")) {
								productPrePick.setSalePrice(product.getMarkPrice() - priceVal);
							} else {
								productPrePick.setSalePrice(product.getMarkPrice() * (1 - priceVal / 100));
							}
						}
					}
				}

				// 销售价调整为整数（带小数进1）
				// productPrePick.setSalePrice(Math.ceil(productPrePick.getSalePrice()));

				// 计算利润比
				Double profitPercent = ((productPrePick.getSalePrice() - productPrePick.getSupplyPrice())
						/ productPrePick.getSupplyPrice()) * 100;
				productPrePick.setProfitPercent(profitPercent);

				if (productPrePick.getIsNewId()) {
					productPrePick.preInsert();
				} else {
					productPrePick.preUpdate();
				}

				toPrePickList.add(productPrePick);

				// 维护站点分类表数据
				initSiteCate(product.getItemCate());
			}

			siteProductService.saveBatch(toPrePickList);

			result.setCode(ResultCode.SUCCESS);
			result.setMsg("挑选成功");
			return result;
		} catch (Exception e) {
			logger.error("挑选失败：{}", e);
			result.setCode(ResultCode.SERVICE_EXCEPTION);
			result.setMsg("系统繁忙，请稍后再试");
			return result;

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
	public Result ajaxUpdate(SiteProduct product) {
		Result result = new Result();

		try {
			if (product.getSalePrice() != null) {
				product.setProfitPercent(
						(product.getSalePrice() - product.getSupplyPrice()) / product.getSupplyPrice() * 100);
			}
			siteProductService.save(product);

			result.setCode(ResultCode.SUCCESS);
			result.setMsg("操作成功");
			return result;
		} catch (Exception e) {
			logger.error("修改商品信息失败:{}", e);
			return Result.newExceptionInstance();
		}
	}
}
