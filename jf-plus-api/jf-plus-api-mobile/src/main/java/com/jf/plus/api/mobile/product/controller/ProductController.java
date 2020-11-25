package com.jf.plus.api.mobile.product.controller;

import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jf.plus.api.mobile.controller.BaseController;
import com.jf.plus.common.core.Result;
import com.jf.plus.common.core.ResultCode;
import com.jf.plus.common.core.ResultObj;
import com.jf.plus.common.core.enums.ProductStatus;
import com.jf.plus.common.core.enums.UserType;
import com.jf.plus.common.utils.StringUtils;
import com.jf.plus.common.vo.SkuStockVo;
import com.jf.plus.core.channel.service.ProductAPIService;
import com.jf.plus.core.order.entity.OrderDetail;
import com.jf.plus.core.order.service.OrderDetailService;
import com.jf.plus.core.product.entity.ProductAttr;
import com.jf.plus.core.product.entity.ProductDetail;
import com.jf.plus.core.product.entity.ProductSku;
import com.jf.plus.core.product.service.ProductAttrService;
import com.jf.plus.core.product.service.ProductDetailService;
import com.jf.plus.core.site.entity.SiteCate;
import com.jf.plus.core.site.entity.SiteProduct;
import com.jf.plus.core.site.service.SiteCateService;
import com.jf.plus.core.site.service.SiteProductService;

import cn.iutils.common.Page;
import cn.iutils.common.utils.MPageInfo;
import cn.iutils.sys.entity.User;
import cn.iutils.sys.service.UserService;

@Controller
public class ProductController extends BaseController {

	private static Logger LOGGER = LoggerFactory.getLogger(ProductController.class);

	@Autowired
	private SiteCateService siteCateService;

	@Autowired
	private SiteProductService siteProductService;

	@Autowired
	private ProductAPIService productAPIService;

	@Autowired
	private ProductAttrService productAttrService;

	@Autowired
	private OrderDetailService orderDetailService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private ProductDetailService productDetailService;

	/**
	 * 获取商品列表
	 *
	 * @param siteId
	 * @param catId
	 * @param itemName
	 * @param page
	 * @return
	 */
	@RequestMapping(value = "/api/productList", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public Result productList(@RequestParam Long siteId, Long advertId, Long catId, String itemName, String px,
			String desc, Page<SiteProduct> page) {
		Result result = Result.newInstance();
		try {
			if (StringUtils.isNotBlank(px)) {
				StringBuilder sb = new StringBuilder();
				sb.append("a.sale_price ");
				if (StringUtils.isNotBlank(desc))
					sb.append(desc);
				else
					sb.append("desc");
				page.setOrderBy(sb.toString());
			}
			page.getCondition().put("siteId", siteId);
			page.getCondition().put("advertId", advertId);
			page.getCondition().put("catId", catId);
			page.getCondition().put("itemName", itemName);
			page.getCondition().put("operStatus", ProductStatus.SHELVES.getType());
			if (advertId != null) {
				page.setOrderBy("ai.id asc");
				page.setList(siteProductService.findSiteProductByAdvertId(page));
			} else {
				page.setList(siteProductService.findPage(page));
			}
			ResultObj resultObj = new ResultObj();
			resultObj.put("page", MPageInfo.transform(page));
			result.setObj(resultObj);
			result.setCode(ResultCode.SUCCESS);
			return result;
		} catch (Exception e) {
			LOGGER.error("获取商品列表失败：", e);
			result = Result.newExceptionInstance();
			return result;
		}
	}

	/**
	 * 商品高级搜索
	 *
	 * @return
	 */
	@RequestMapping(value = "/api/productList/advance", method = RequestMethod.POST)
	@ResponseBody
	public Result advanceProductList(String token,@RequestParam Long siteId, String itemName, String catId, Double startPrice,
			Double endPrice, String source, String brandName, String px, String desc, String advertId, String saleType,String season,
			String area, String buyerId, Integer buyerStatus, Page<SiteProduct> page) {
		Result result = new Result();

		try {

			result = appcodeService.checkToken(token);
			//筛选
			if(buyerStatus != null){
				if(!result.isSuccess()){
					return result;
				}
			}
			
			if(result.isSuccess()){
				User user = userService.get(result.getObj().toString());
				if(checkLoginPromise(UserType.SALER.getType(), user.getRoleGroupIds()).isSuccess()){//检查是否是代理商权限，是的话，需要过滤商品可见
					page.getCondition().put("buyerUserId", result.getObj().toString());
				}
			}
			
			
			if (StringUtils.isNotBlank(px)) {
				StringBuilder sb = new StringBuilder();
				if ("1".equals(px)) {
					sb.append("salePrice ");
				} else if ("2".equals(px)) {
					sb.append("createDate ");
				} else if ("3".equals(px)) {
					sb.append("saleNum ");
				}
				if (StringUtils.isNotBlank(desc))
					sb.append(desc);
				else
					sb.append("desc");
				page.setOrderBy(sb.toString());
			} else {
				page.setOrderBy(" IF(stock = 0 ,-999 , shelvesDate) DESC ");
			}

			page.getCondition().put("siteId", siteId);// 站点
			page.getCondition().put("catId", catId);// 分类
			page.getCondition().put("itemName", itemName);// 关键词
			page.getCondition().put("operStatus", ProductStatus.SHELVES.getType());
			page.getCondition().put("startPrice", startPrice);
			page.getCondition().put("endPrice", endPrice);
			page.getCondition().put("advertId", advertId);
			page.getCondition().put("saleType", saleType);
			page.getCondition().put("area", area);
			page.getCondition().put("season", season);
			page.getCondition().put("isKf",0);//是否库房
			if(StringUtils.isNotBlank(buyerId)){
				User user = userService.get(buyerId);
				Result cfResult  = checkLoginPromiseKf(user.getRoleGroupIds());
				if(cfResult.isSuccess()){
					page.getCondition().put("isKf", 1);
				}
			}
			page.getCondition().put("buyerId", buyerId);
			page.getCondition().put("buyerStatus", buyerStatus);// 购买状态

			if (StringUtils.isNotBlank(source)) {
				page.getCondition().put("sourceList", source.split(","));
			}
			if (StringUtils.isNotBlank(brandName)) {
				page.getCondition().put("brandName", brandName);
			}
			page.setList(siteProductService.findPage(page));

			ResultObj resultObj = new ResultObj();
			resultObj.put("page", MPageInfo.transform(page));
			result.setObj(resultObj);
			result.setCode(ResultCode.SUCCESS);
			result.setMsg("查询成功");
			return result;
		} catch (Exception e) {
			LOGGER.error("商品高级搜索异常:{}", e);
			return Result.newExceptionInstance();
		}
	}

	/**
	 * 品牌列表
	 *
	 * @param siteId
	 * @param itemName
	 * @return
	 */
	@RequestMapping(value = "/api/brandList", method = RequestMethod.POST)
	@ResponseBody
	public Result brandList(@RequestParam Long siteId) {
		Result result = new Result();

		try {

			SiteProduct siteProduct = new SiteProduct();
			siteProduct.setSiteId(siteId);
			;
			List<String> brandList = siteProductService.findBrandList(siteProduct);

			ResultObj resultObj = new ResultObj();
			resultObj.put("brandList", brandList);
			result.setObj(resultObj);

			result.setCode(ResultCode.SUCCESS);
			result.setMsg("操作成功");
			return result;
		} catch (Exception e) {
			LOGGER.error("查询品牌异常:{}", e);
			return Result.newExceptionInstance();
		}
	}

	/**
	 * 获取商品详情
	 *
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/api/productDetail", method = { RequestMethod.POST })
	@ResponseBody
	public Result productDetail(@RequestParam Long id) {
		Result result = Result.newInstance();
		try {
			ResultObj resultObj = new ResultObj();
			SiteProduct siteProduct = siteProductService.getDetail(id);
			if (siteProduct != null) {
				ProductAttr entity = new ProductAttr();
				entity.setProductId(siteProduct.getProductId());
				List<ProductAttr> productAttrList = productAttrService.findList(entity);
				siteProduct.setProductAttrList(productAttrList);// 属性
			}
			resultObj.put("entity", siteProduct);
			result.setObj(resultObj);
			result.setCode(ResultCode.SUCCESS);
			return result;
		} catch (Exception e) {
			LOGGER.error("获取商品详情失败：", e);
			result = Result.newExceptionInstance();
			return result;
		}
	}
	
	/**
	 * 获取商品内容详情
	 *
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/api/productContent", method = { RequestMethod.POST })
	@ResponseBody
	public Result productContent(@RequestParam Long id) {
		Result result = Result.newInstance();
		try {
			ProductDetail productDetail = new ProductDetail();
			productDetail.setSiteProductId(id);
			productDetail.setStatus("1");
			productDetail = productDetailService.getEntity(productDetail);
			
			
			ResultObj resultObj = new ResultObj();
			resultObj.put("id", id);
			resultObj.put("content", productDetail != null ? productDetail.getContent() : "");
			result.setObj(resultObj);
			result.setCode(ResultCode.SUCCESS);
			return result;
		} catch (Exception e) {
			LOGGER.error("获取商品详情失败：", e);
			result = Result.newExceptionInstance();
			return result;
		}
	}

	/**
	 * 获取商品实时价格
	 * 
	 * @param spIds
	 * @param token
	 * @return
	 */
	@RequestMapping(value = "/api/productPrice", method = { RequestMethod.POST })
	@ResponseBody
	public Result productPrice(@RequestParam Long siteId, @RequestParam String spIds, String token) {
		Result result = Result.newInstance();
		try {
//			Set<String> spidsSet = null;
//			if (StringUtils.isNotBlank(spIds)) {
//				String[] spids = spIds.split(",");
//				spidsSet = new HashSet<>(Arrays.asList(spids));
//				return productAPIService.syncPrice(siteId, spidsSet, token, 0);
//			}
			result.setCode(ResultCode.SUCCESS);
			return result;
		} catch (Exception e) {
			LOGGER.error("获取商品价格失败：", e);
			result = Result.newExceptionInstance();
			return result;
		}
	}

	/**
	 * 获取站点商品分类列表
	 *
	 * @param siteId
	 * @param page
	 * @return
	 */
	@RequestMapping(value = "/api/cateList", method = { RequestMethod.POST })
	@ResponseBody
	public Result cateList(@RequestParam Long siteId, Page<SiteCate> page) {
		Result result = Result.newInstance();
		try {
			page.setOrderBy("mpc.sort asc,mpc.id desc");
			page.setPageSize(10000);
			page.getCondition().put("siteId", siteId);
			page.getCondition().put("catPid", 1); // 一级分类
			List<SiteCate> siteCateList = siteCateService.findSiteCate(page);
			for (SiteCate siteCate : siteCateList) {
				page.getCondition().put("catPid", siteCate.getCateId()); // 子分类
				siteCate.setChildrens(siteCateService.findSiteCate(page));
			}
			page.setList(siteCateList);
			ResultObj resultObj = new ResultObj();
			resultObj.put("page", MPageInfo.transform(page));
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
	 * 商品库存查询
	 *
	 * @param stockVo
	 * @return
	 */
	@RequestMapping(value = "/api/productStock", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public Result productStock(@RequestBody SkuStockVo[] skus) {
		Result result = Result.newInstance();
		try {
			List<SkuStockVo> skuStockVos = Arrays.asList(skus);
			return productAPIService.checkStock(skuStockVos);
		} catch (Exception e) {
			LOGGER.error("获取商品库存失败：", e);
			result = Result.newExceptionInstance();
			return result;
		}
	}

	/**
	 * sku列表
	 * 
	 * @param siteProduct
	 *            id必传
	 * @return
	 */
	@RequestMapping(value = "/api/productSku", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public Result productSku(SiteProduct siteProduct) {
		Result result = Result.newInstance();
		try {
			List<ProductSku> productSkuList = siteProductService.findSkuList(siteProduct);
			result.setCode(ResultCode.SUCCESS);
			ResultObj resultObj = new ResultObj();
			resultObj.put("skuList", productSkuList);
			result.setObj(resultObj);
			return result;
		} catch (Exception e) {
			LOGGER.error("获取SKU失败：", e);
			result = Result.newExceptionInstance();
			return result;
		}
	}

	/**
	 * 用户购买商品数量接口
	 */
	@RequestMapping("/api/productBuyerNum")
	@ResponseBody
	public Result productBuyerNum(@RequestParam String token, @RequestParam Long itemId) {
		Result result = new Result();
		try {

			result = appcodeService.checkToken(token);
			if (!result.isSuccess()) {
				return result;
			}
			User user = new User();
			user.setId(result.getObj().toString());

			OrderDetail orderDetail = new OrderDetail();
			orderDetail.setItemId(itemId);
			orderDetail.setStatus("1");
			orderDetail.setUser(user);
			int num = orderDetailService.sumByEntity(orderDetail);

			ResultObj resObj = new ResultObj();
			resObj.put("buyerNum", num);
			result.setObj(resObj);
			result.setCode(ResultCode.SUCCESS);
			return result;
		} catch (Exception e) {
			LOGGER.error("系统异常:{}", e);
			return Result.newExceptionInstance();
		}
	}
	
	
	@RequestMapping("/api/product/buyHistory")
	@ResponseBody
	public Result productBuyHistory(@RequestParam String itemId, @RequestParam String token, Page<OrderDetail> page) {
		Result result = new Result();

		try {
			
			result = appcodeService.checkToken(token);
			if (!result.isSuccess()) {
				return result;
			}
			String orgId = userService.get(result.getObj().toString()).getOrganizationIds();
			page.setPageSize(Integer.MAX_VALUE);
			page.setOrderBy("a.id desc");
			page.getCondition().put("orgId", orgId);
			page.getCondition().put("itemId", itemId);
			page.getCondition().put("operStatusLike", "a.oper_status like '2_'");
			List<OrderDetail> orderDetails = orderDetailService.findPage(page );
			
			ResultObj resultObj = new ResultObj();
			resultObj.put("orderDetailList", orderDetails);
			result.setObj(resultObj);
			result.setCode(ResultCode.SUCCESS);
			return result;
		} catch (Exception e) {
			LOGGER.error("系统异常：{}", e);
			return Result.newExceptionInstance();
		}
	}

}
