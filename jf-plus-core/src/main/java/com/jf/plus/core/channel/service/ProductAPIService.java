package com.jf.plus.core.channel.service;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.jf.plus.common.core.Result;
import com.jf.plus.common.core.ResultCode;
import com.jf.plus.common.core.enums.ProductSource;
import com.jf.plus.common.core.enums.ProductStatus;
import com.jf.plus.common.core.enums.SaleType;
import com.jf.plus.common.vo.SkuStockVo;
import com.jf.plus.core.mall.service.MallSiteService;
import com.jf.plus.core.mallSetting.service.MallSiteRuleService;
import com.jf.plus.core.product.entity.ProductStock;
import com.jf.plus.core.product.service.ProductDetailService;
import com.jf.plus.core.product.service.ProductService;
import com.jf.plus.core.product.service.ProductStockService;
import com.jf.plus.core.product.service.ProductSupplyPriceLogService;
import com.jf.plus.core.setting.service.AppcodeService;
import com.jf.plus.core.setting.service.UserAddressService;
import com.jf.plus.core.site.entity.SiteProduct;
import com.jf.plus.core.site.service.SiteProductService;

/**
 *
 * 商品渠道通用接口
 *
 * @version 1.0
 * @author Tng
 *
 */
@Service
public class ProductAPIService {

	private final static Logger LOGGER = Logger.getLogger(ProductAPIService.class);

	@Autowired
	ProductService productService;

	@Autowired
	SiteProductService siteProductService;

	@Autowired
	ProductStockService productStockService;

	@Autowired
	ProductDetailService productDetailService;
	
	@Autowired
	AppcodeService appcodeService;
	
	@Autowired
	UserAddressService userAddressService;
	
	@Autowired
	MallSiteService mallSiteService;
	
	@Autowired
	ProductSupplyPriceLogService productSupplyPriceLogService;
	
	@Autowired
	MallSiteRuleService mallSiteRuleService;


	/**
	 * 查询商品库存/判断上下架
	 *
	 * @param skus
	 * @return
	 */
	public Result checkStock(List<SkuStockVo> skus) {
		Result result = new Result();
		try {

			if (CollectionUtils.isEmpty(skus)) {
				result.setCode(ResultCode.RETURN_FAILURE);
				result.setMsg("sku不能为空");
				return result;
			}

			Integer source = skus.get(0).getSource();
			if (source == null) {
				result.setCode(ResultCode.RETURN_FAILURE);
				result.setMsg("sku渠道不能为空");
				return result;
			}

			ProductSource productSource = ProductSource.getByType(source);
			Assert.notNull(productSource, "productSource can not be null");

			switch (productSource) {
			case SUPPLY:
				for (SkuStockVo sku : skus) {
					sku = checkStockSupply(sku);
				}
				break;
			default:
				break;
			}

			result.setObj(skus);
			result.setCode(ResultCode.SUCCESS);
			return result;
		} catch (Exception e) {
			LOGGER.error("查询库存异常:{}", e);
			return Result.newExceptionInstance();
		}
	}


	private SkuStockVo checkStockSupply(SkuStockVo sku) {
		// 判断上下架
		SiteProduct siteProduct = siteProductService.get(sku.getItemId());
		if (siteProduct != null && Integer.valueOf(siteProduct.getOperStatus()) == ProductStatus.OUT_SHELVES.getType()) {
			sku.setStatus(SkuStockVo.OUT_SHELVES);
			return sku;
		}
		
		//砍货商品限制库存
		if(SaleType.KH.getDescription().equals(siteProduct.getSaleType())){
			
	
			ProductStock entity = new ProductStock();
			entity.setProductNo(siteProduct.getProductNo());
			ProductStock stock = productStockService.getEntity(entity);
			//不等于-1 或者小于小于购买数量
			if (stock == null || (stock.getStockNum() != -1 && stock.getStockNum() < Integer.valueOf(sku.getNum()))) {
				sku.setStatus(SkuStockVo.LACK_STOCK);
				return sku;
			}else{
				sku.setStatus(SkuStockVo.HAS_STOCK);
				sku.setStockNum(stock.getStockNum()+"");
			}
		}

		sku.setStatus(SkuStockVo.HAS_STOCK);
		return sku;
	}
}
