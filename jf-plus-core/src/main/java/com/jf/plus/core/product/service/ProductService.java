package com.jf.plus.core.product.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONArray;
import com.google.common.base.Joiner;
import com.jf.plus.common.core.Constants;
import com.jf.plus.common.core.Result;
import com.jf.plus.common.core.ResultCode;
import com.jf.plus.common.core.enums.ProductStatus;
import com.jf.plus.common.utils.CardGen;
import com.jf.plus.common.utils.StringUtils;
import com.jf.plus.common.vo.PrductAttrVo;
import com.jf.plus.common.vo.ProductSkuVo;
import com.jf.plus.common.vo.ProductVo;
import com.jf.plus.core.account.service.CodeService;
import com.jf.plus.core.mall.dao.MallProductCateDao;
import com.jf.plus.core.mall.dao.MallSupplyerDao;
import com.jf.plus.core.mall.entity.MallProductCate;
import com.jf.plus.core.mall.entity.MallSupplyer;
import com.jf.plus.core.order.entity.Order;
import com.jf.plus.core.product.dao.MallProductBrandDao;
import com.jf.plus.core.product.dao.ProductAttrDao;
import com.jf.plus.core.product.dao.ProductDao;
import com.jf.plus.core.product.dao.ProductDetailDao;
import com.jf.plus.core.product.dao.ProductSaleRuleDao;
import com.jf.plus.core.product.dao.ProductSaleRuleMerchantDao;
import com.jf.plus.core.product.dao.ProductSkuDao;
import com.jf.plus.core.product.dao.ProductStockDao;
import com.jf.plus.core.product.dao.SpecLibDao;
import com.jf.plus.core.product.entity.MallProductBrand;
import com.jf.plus.core.product.entity.OrgGroup;
import com.jf.plus.core.product.entity.OrgGroupMerchant;
import com.jf.plus.core.product.entity.Product;
import com.jf.plus.core.product.entity.ProductAttr;
import com.jf.plus.core.product.entity.ProductDetail;
import com.jf.plus.core.product.entity.ProductSaleRule;
import com.jf.plus.core.product.entity.ProductSaleRuleMerchant;
import com.jf.plus.core.product.entity.ProductSku;
import com.jf.plus.core.product.entity.ProductStock;

import cn.iutils.common.DataScope;
import cn.iutils.common.Page;
import cn.iutils.common.service.CrudService;

/**
 * 商品表 Service层
 *
 * @author Tng
 * @version 1.0
 */
@Service
@Transactional(readOnly = true)
public class ProductService extends CrudService<ProductDao, Product> {

	@Autowired
	ProductStockDao productStockDao;
	@Autowired
	MallSupplyerDao mallSupplyerDao;
	@Autowired
	ProductDetailDao productDetailDao;
	@Autowired
	ProductSkuDao productSkuDao;
	@Autowired
	ProductAttrDao productAttrDao;
	@Autowired
	ProductSaleRuleDao productSaleRuleDao;
	@Autowired
	CodeService codeService;
	@Autowired
	MallProductCateDao mallProductCateDao;
	@Autowired
	MallProductBrandDao mallProductBrandDao;
	@Autowired
	SpecLibDao specLibDao;
	@Autowired
	ProductSaleRuleMerchantDao productSaleRuleMerchantDao;
	
	
	
	public List<Product> findbrandNamePage(Page<Product> page) {
    	DataScope dataScope;
    	if(page!= null && page.getEntity() != null && (dataScope = page.getEntity().getDataScopeFilter()) != null){
    		dataScope.setDataScopeSql(dataScopeFilter(dataScope.getUser(),dataScope.getDataScopeEnum(),dataScope.getOrgAlias(),dataScope.getUserAlias()));
    	}
    	
        return dao.findbrandNamePage(page);
    }

	/**
	 * 扣减库存
	 *
	 * @param productNo
	 * @param saleNum
	 * @return
	 */
	@Transactional(readOnly = false)
	public int minusStock(Long productNo, Integer saleNum) {
		return productStockDao.minusStock(productNo, saleNum);
	}

	@Transactional(readOnly = false)
	public int saveBatch(List<Product> toPrePickList) {
		return dao.insertAll(toPrePickList);
	}

	@Transactional(readOnly = false)
	public void batchOutShelves(int source, Set<String> xjSKUList) {
		for(String itemCode : xjSKUList){
			dao.outShelves(source,itemCode);
		}
	}
	
	@Transactional(readOnly = false)
	public void outShelves(int source, String xjSKU) {
		dao.outShelves(source,xjSKU);
	}

	@Transactional(readOnly = false)
	public void autoShelvesProduct(Product existsProduct) {
		dao.shelvesProduct(existsProduct.getSource(),existsProduct.getItemCode());
		
	}
	
	@Transactional(readOnly = false)
	public void batchSnycProductPrice(Product syncPriceProduct){
		dao.syncPriceProduct(syncPriceProduct.getSource(),syncPriceProduct.getItemCode(),syncPriceProduct.getSupplyPrice(),syncPriceProduct.getMarkPrice());
	}

	public List<Product> findSupplyPage(Page<Product> page) {
		DataScope dataScope;
    	if(page!= null && page.getEntity() != null && (dataScope = page.getEntity().getDataScopeFilter()) != null){
    		dataScope.setDataScopeSql(dataScopeFilter(dataScope.getUser(),dataScope.getDataScopeEnum(),dataScope.getOrgAlias(),dataScope.getUserAlias()));
    	}
    	
        page.setTotal(dao.countSupply(page));
        return dao.findSupplyPage(page);
	}

	public Product getTopProduct(Product product) {
		return dao.getTopProduct(product);
	}

	public List<Product> findTopList(Product product) {
		return dao.findTopList(product);
	}

	public List<Product> findTopPage(Page<Product> page) {
		DataScope dataScope;
    	if(page!= null && page.getEntity() != null && (dataScope = page.getEntity().getDataScopeFilter()) != null){
    		dataScope.setDataScopeSql(dataScopeFilter(dataScope.getUser(),dataScope.getDataScopeEnum(),dataScope.getOrgAlias(),dataScope.getUserAlias()));
    	}
    	
        page.setTotal(dao.countTop(page));
        return dao.findTopPage(page);
	}

	@Transactional(readOnly = false)
	public Result submitProduct(ProductVo productVo) {
			Result result = new Result();
			
			//商品信息
			Product product = new Product();
			product.setPhoto(productVo.getPhotos());
			product.setSaleType(productVo.getSaleType());
			product.setItemName(productVo.getItemName());
			product.setContent(productVo.getContent());
			product.setItemCate(productVo.getCateId());
			product.setItemCateName(productVo.getCateName());
			product.setSupplyId(productVo.getSupplyId());
			product.setSupplyName(productVo.getSupplyName());
			product.setBrandName(productVo.getBrand());
			product.setArea(productVo.getArea());
			product.setYearth(productVo.getYearth());
			product.setSeason(productVo.getSeason());
			product.setSupplyPrice(productVo.getSalePrice());
			product.setMarkPrice(productVo.getSalePrice());
			product.setSalePrice(productVo.getSalePrice());
			product.setInPrice(productVo.getInPrice());
			product.setZjPrice(productVo.getZjPrice());
			product.setMgrPrice(productVo.getMgrPrice());
			product.setSource(productVo.getSource());
			product.setType(1);
			product.setOperStatus(ProductStatus.TO_AUDIT.getType());
			product.setBuyerId(productVo.getBuyerId());
			product.setBuyerName(productVo.getBuyerName());

			//生成编码
			product.setItemCode(genItemCode(product));
			
			MallSupplyer supplyer = mallSupplyerDao.get(productVo.getSupplyId() +"");
			product.setOrgId(supplyer.getOrgId());
			product.setOrgName(supplyer.getOrgName());
			product.setStatus("1");
			product.setCreateBy(productVo.getBuyerId()+"");
			product.setCreateDate(new Date());
			save(product);
			
			//详情及库存
			ProductDetail productDetail = new ProductDetail();
			productDetail.setItemCode(product.getItemCode());
			productDetail.setItemName(product.getItemName());
			productDetail.setContent(productVo.getContent());
			productDetail.setProductNo(Long.valueOf(product.getId()));
			productDetail.setStatus("1");
			productDetail.setCreateBy(productVo.getBuyerId()+"");
			productDetail.setCreateDate(new Date());
			productDetailDao.insert(productDetail);
			
			ProductStock productStock = new ProductStock();
			productStock.setProductNo(Long.valueOf(product.getId()));
			productStock.setItemCode(product.getItemCode());
			productStock.setStockNum(productVo.getStock() == null ? -1 : productVo.getStock());//-1不限制库存
			productStock.setStatus("1");
			productStock.setCreateBy(productVo.getBuyerId()+"");
			productStock.setCreateDate(new Date());
			productStockDao.insert(productStock);
			
			product.setProductNo(Long.valueOf(product.getId()));
			save(product);
			
			//SKU及属性
			if(CollectionUtils.isNotEmpty(productVo.getSkus())){
				for(ProductSkuVo productSkuVo: productVo.getSkus()){
					ProductSku productSku = new ProductSku();
					productSku.setProductId(Long.valueOf(product.getId()));

					productSku.setItemCode(product.getItemCode());//商品编码
					productSku.setSpecCode(genSpecCode(product,productSku));//SKU编码
				    productSku.setSpecColor(productSkuVo.getSpecColor());//颜色
				    productSku.setSpecColorText(productSkuVo.getSpecColorText());//颜色值
				    productSku.setSpecSize(productSkuVo.getSpecSize());//尺码
				    productSku.setSpecSizeText(productSkuVo.getSpecSizeText());//尺码值
					productSku.setStatus("1");
					productSku.setCreateBy(productVo.getBuyerId()+"");
					productSku.setCreateDate(new Date());
					productSkuDao.insert(productSku);
				}
			}
			if(CollectionUtils.isNotEmpty(productVo.getAttrs())){
				for(PrductAttrVo productAttrVo: productVo.getAttrs()){
					ProductAttr productAttr = new ProductAttr();
					productAttr.setProductId(Long.valueOf(product.getId()));
					productAttr.setAttrId(productAttrVo.getAttrId());
					productAttr.setAttrInfo(productAttrVo.getAttrInfo());
					productAttr.setAttrText(productAttrVo.getAttrText());
					productAttr.setStatus("1");
					productAttr.setCreateBy(productVo.getBuyerId()+"");
					productAttr.setCreateDate(new Date());
					productAttrDao.insert(productAttr);
				}
			}
			
			//销售规格
			ProductSaleRule productSaleRule = new ProductSaleRule();
			productSaleRule.setShipmentDate(productVo.getShipmentDate());
			productSaleRule.setLimitStock(productVo.getLimitStock() == null ? -1: productVo.getLimitStock());;
			productSaleRule.setSaleNotice(productVo.getSaleNotice());
			productSaleRule.setSaleNoticeUnit(productVo.getSaleNoticeUnit());
			productSaleRule.setEndDate(productVo.getEndDate());
			//productSaleRule.setOrgGroups(productVo.getOrgGroups());
			productSaleRule.setProductId(Long.valueOf(product.getId()));
			productSaleRule.setSaleType(productVo.getSaleType());
			productSaleRule.setStatus("1");
			productSaleRule.setCreateBy(productVo.getBuyerId()+"");
			productSaleRule.setCreateDate(new Date());
			productSaleRuleDao.insert(productSaleRule);
			
			List<String> orgGroupIds = new ArrayList<>();
			if(StringUtils.isNotBlank(productVo.getOrgGroups())){
				logger.info("发布商品分组：{}",productVo.getOrgGroups());
				
				List<OrgGroup> orgGroupList = JSONArray.parseArray(productVo.getOrgGroups(), OrgGroup.class);
				for(OrgGroup orgGroup : orgGroupList){
					orgGroupIds.add(orgGroup.getId());
					
					if(CollectionUtils.isNotEmpty(orgGroup.getMerchantList())){
						for(OrgGroupMerchant merchant: orgGroup.getMerchantList()){
							ProductSaleRuleMerchant productSaleRuleMerchant = new ProductSaleRuleMerchant();
							productSaleRuleMerchant.setProductId(Long.valueOf(product.getId()));
							productSaleRuleMerchant.setSaleRuleId(Long.valueOf(productSaleRule.getId()));
							productSaleRuleMerchant.setOrgGroup(orgGroup.getId());
							productSaleRuleMerchant.setGroupMerchant(merchant.getMerchantId()+"");
							productSaleRuleMerchant.setStatus("1");
							productSaleRuleMerchant.setCreateBy(productVo.getBuyerId()+"");
							productSaleRuleMerchant.setCreateDate(new Date());
							
							productSaleRuleMerchantDao.insert(productSaleRuleMerchant);
						}
					}
				}
				//更新id
				productSaleRule.setOrgGroups(Joiner.on(",").join(orgGroupIds));
				productSaleRuleDao.update(productSaleRule);
				
			}
			
			result.setObj(product.getId());
			result.setCode(ResultCode.SUCCESS);
			result.setMsg("提交成功");
			return result;
	}
	
	@Transactional(readOnly = false)
	public Result updateProduct(ProductVo productVo) {
			Result result = new Result();
			
			//商品信息
			Product product = new Product();
			product.setId(productVo.getProductId());
			product.setPhoto(productVo.getPhotos());
			product.setSaleType(productVo.getSaleType());
			product.setItemName(productVo.getItemName());
			product.setContent(productVo.getContent());
			product.setItemCate(productVo.getCateId());
			product.setItemCateName(productVo.getCateName());
			product.setSupplyId(productVo.getSupplyId());
			product.setSupplyName(productVo.getSupplyName());
			product.setBrandName(productVo.getBrand());
			product.setArea(productVo.getArea());
			product.setYearth(productVo.getYearth());
			product.setSeason(productVo.getSeason());
			product.setSupplyPrice(productVo.getSalePrice());
			product.setMarkPrice(productVo.getSalePrice());
			product.setSalePrice(productVo.getSalePrice());
			product.setInPrice(productVo.getInPrice());
			product.setZjPrice(productVo.getZjPrice());
			product.setMgrPrice(productVo.getMgrPrice());
			product.setSource(productVo.getSource());
			product.setUpdateBy(productVo.getBuyerId()+"");
			product.setUpdateDate(new Date());
			//生成编码
			//product.setItemCode(genItemCode(product));
			
			save(product);
			
			//详情及库存
			ProductDetail productDetail = new ProductDetail();
			productDetail.setProductNo(Long.valueOf(product.getId()));
			productDetail = productDetailDao.getEntity(productDetail);
			
			productDetail.setContent(productVo.getContent());
			productDetail.setUpdateBy(productVo.getBuyerId()+"");
			productDetail.setUpdateDate(new Date());
			productDetailDao.update(productDetail);
			
			ProductStock productStock = new ProductStock();
			productStock.setProductNo(Long.valueOf(product.getId()));
			productStock = productStockDao.getEntity(productStock);
			productStock.setStockNum(productVo.getStock() == null ? -1 : productVo.getStock());//-1不限制库存
			productStock.setUpdateBy(productVo.getBuyerId()+"");
			productStock.setUpdateDate(new Date());
			productStockDao.update(productStock);
			
			
			
			ProductSku productSku = new ProductSku();
			productSku.setProductId(Long.valueOf(product.getId()));
			productSkuDao.deleteEntity(productSku);
			
			
			if(CollectionUtils.isNotEmpty(productVo.getSkus())){
				for(ProductSkuVo productSkuVo:  productVo.getSkus()){
					ProductSku newProductSku = new ProductSku();
					newProductSku.setProductId(Long.valueOf(product.getId()));

					newProductSku.setItemCode(product.getItemCode());//商品编码
					newProductSku.setSpecCode(genSpecCode(product,newProductSku));//SKU编码
				    newProductSku.setSpecColor(productSkuVo.getSpecColor());//颜色
				    newProductSku.setSpecColorText(productSkuVo.getSpecColorText());//颜色值
				    newProductSku.setSpecSize(productSkuVo.getSpecSize());//尺码
				    newProductSku.setSpecSizeText(productSkuVo.getSpecSizeText());//尺码值
				    newProductSku.setStatus("1");
				    newProductSku.setCreateBy(productVo.getBuyerId()+"");
				    newProductSku.setCreateDate(new Date());
					productSkuDao.insert(newProductSku);
				}
			}
			
			ProductAttr productAttr = new ProductAttr();
			productAttr.setProductId(Long.valueOf(product.getId()));
			productAttrDao.deleteEntity(productAttr);
			
			if(CollectionUtils.isNotEmpty(productVo.getAttrs())){
				//插入新增的SKU
				for(PrductAttrVo productAttrVo : productVo.getAttrs()){
					ProductAttr newProductAttr = new ProductAttr();
					newProductAttr.setProductId(Long.valueOf(product.getId()));
					newProductAttr.setAttrId(productAttrVo.getAttrId());
					newProductAttr.setAttrInfo(productAttrVo.getAttrInfo());
					newProductAttr.setAttrText(productAttrVo.getAttrText());
					newProductAttr.setStatus("1");
					newProductAttr.setCreateBy(productVo.getBuyerId()+"");
					newProductAttr.setCreateDate(new Date());
					
					productAttrDao.insert(newProductAttr);
				}
			}
			
			//销售规格
			ProductSaleRule productSaleRule = new ProductSaleRule();
			productSaleRule.setProductId(Long.valueOf(product.getId()));
			productSaleRule = productSaleRuleDao.getEntity(productSaleRule);
			
			productSaleRule.setShipmentDate(productVo.getShipmentDate());
			productSaleRule.setLimitStock(productVo.getLimitStock() == null ? -1: productVo.getLimitStock());;
			productSaleRule.setSaleNotice(productVo.getSaleNotice());
			productSaleRule.setSaleNoticeUnit(productVo.getSaleNoticeUnit());
			productSaleRule.setEndDate(productVo.getEndDate());
			//productSaleRule.setOrgGroups(productVo.getOrgGroups());
			productSaleRule.setUpdateBy(productVo.getBuyerId()+"");
			productSaleRule.setUpdateDate(new Date());
			productSaleRuleDao.update(productSaleRule);
			
			List<String> orgGroupIds = new ArrayList<>();
			if(StringUtils.isNotBlank(productVo.getOrgGroups())){
				logger.info("发布商品分组：{}",productVo.getOrgGroups());
				
				List<OrgGroup> orgGroupList = JSONArray.parseArray(productVo.getOrgGroups(), OrgGroup.class);
				for(OrgGroup orgGroup : orgGroupList){
					orgGroupIds.add(orgGroup.getId());
					
					if(CollectionUtils.isNotEmpty(orgGroup.getMerchantList())){
						//先删除再添加
						ProductSaleRuleMerchant productSaleRuleMerchantDelete = new ProductSaleRuleMerchant();
						productSaleRuleMerchantDelete.setSaleRuleId(Long.valueOf(productSaleRule.getId()));
						productSaleRuleMerchantDelete.setOrgGroup(orgGroup.getId());
						productSaleRuleMerchantDao.deleteEntity(productSaleRuleMerchantDelete);
						
						for(OrgGroupMerchant merchant: orgGroup.getMerchantList()){
							ProductSaleRuleMerchant productSaleRuleMerchant = new ProductSaleRuleMerchant();
							productSaleRuleMerchant.setProductId(Long.valueOf(product.getId()));
							productSaleRuleMerchant.setSaleRuleId(Long.valueOf(productSaleRule.getId()));
							productSaleRuleMerchant.setOrgGroup(orgGroup.getId());
							productSaleRuleMerchant.setGroupMerchant(merchant.getMerchantId()+"");
							productSaleRuleMerchant.setStatus("1");
							productSaleRuleMerchant.setCreateBy(productVo.getBuyerId()+"");
							productSaleRuleMerchant.setCreateDate(new Date());
							
							productSaleRuleMerchantDao.insert(productSaleRuleMerchant);
						}
					}
				}
				//更新id
				productSaleRule.setOrgGroups(Joiner.on(",").join(orgGroupIds));
				productSaleRuleDao.update(productSaleRule);
				
			}
			
			
			product = dao.get(product.getId());
			if(product.getOperStatus() == ProductStatus.OUT_SHELVES.getType() || product.getOperStatus() == ProductStatus.AUDIT_FAIL.getType()){
				product.setOperStatus(ProductStatus.TO_AUDIT.getType());
				save(product);//下架编辑后，自动提交审核
			}
			
			result.setObj(product.getId());
			result.setCode(ResultCode.SUCCESS);
			result.setMsg("提交成功");
			return result;
	}
	
	
	/**
	 * 生成商品编码
	 * @param product
	 * @return
	 */
	private String genItemCode(Product product){
		//生成编码
		String lastItemCateId = product.getItemCate().substring(product.getItemCate().lastIndexOf(",") + 1);
		MallProductCate productCate = mallProductCateDao.get(lastItemCateId);
		String catCode = "";
		if(productCate != null){
			catCode = productCate.getCatCode();
		}
		String brandCode = "";
		if(StringUtils.isNotBlank(product.getBrandName())){
			MallProductBrand mallProductBrand = new MallProductBrand();
			mallProductBrand.setBrandName(product.getBrandName());
			mallProductBrand = mallProductBrandDao.getEntity(mallProductBrand );
			brandCode = mallProductBrand.getBrandCode();
		}
		
		return formatItemCode(catCode, brandCode);
	}
	
	/**
	 * 商品编码（品牌编码+分类编码+8位流水号）
	 * @param catCode
	 * @param brandCode
	 * @return
	 */
	private String formatItemCode(String catCode,String brandCode){
		StringBuffer stringBuffer = new StringBuffer();
		stringBuffer.append(brandCode).append(catCode);
		int account = codeService.genAccount(Constants.PRODUCT_SEQ);
		stringBuffer.append(CardGen.formatAccount(account));
		return stringBuffer.toString();
	}
	
	/**
	 * SKU编码（商品编码+SKU颜色编号1+SKU尺寸编码2）
	 * @param product
	 * @param productSku
	 * @return
	 */
	private String genSpecCode(Product product , ProductSku productSku){
		StringBuffer stringBuffer = new StringBuffer();
		stringBuffer.append(product.getItemCode());
		String skuCode = specLibDao.findSkuCode(productSku);
		stringBuffer.append(skuCode);
		return stringBuffer.toString();
	}

	@Transactional(readOnly = false)
	public int productMsg(String productId, String orgGroupId) {
		return dao.productMsg(productId, orgGroupId);
		
	}
	
	@Transactional(readOnly = false)
	public int orderMsg(String productId) {
		return dao.orderMsg(productId);
		
	}

	@Transactional(readOnly = false)
	public Double calcMgrPrice(ProductVo productVo) {
		return dao.calcMgrPrice(productVo.getCateName(),productVo.getSaleType(),productVo.getInPrice() + productVo.getZjPrice());
	}

	public Integer getSaleNum(String productId) {
		 return dao.getSaleNum(productId);
	}

	public int sumProdCount(Page<Order> page) {
		return dao.sumProdCount(page);
	}

	public Map<String, Object> buyerProdReport(Page<Map<String, Object>> page) {
		return dao.buyerProdReport(page);
	}

	public Map<String, Object> buyerOrderReport(Page<Map<String, Object>> page) {
		return dao.buyerOrderReport(page);
	}

}
