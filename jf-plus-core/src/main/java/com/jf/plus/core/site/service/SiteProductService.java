package com.jf.plus.core.site.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jf.plus.core.product.entity.ProductSku;
import com.jf.plus.core.site.dao.SiteProductDao;
import com.jf.plus.core.site.entity.SiteProduct;

import cn.iutils.common.Page;
import cn.iutils.common.service.CrudService;

/**
 * 站点商品表 Service层
 * 
 * @author Tng
 * @version 1.0
 */
@Service
@Transactional(readOnly = true)
public class SiteProductService extends CrudService<SiteProductDao, SiteProduct> {

	/**
	 * 查询站点商品列表
	 * 
	 * @param advertId
	 * @return
	 */
	public List<SiteProduct> findSiteProductByAdvertId(Page<SiteProduct> page) {
		page.setTotal(dao.findSiteProductByAdvertIdCount(page));
		return dao.findSiteProductByAdvertIdList(page);
	}

	/**
	 * 查询站点商品明细
	 * 
	 * @param id
	 * @return
	 */
	public SiteProduct getDetail(Long id) {
		return dao.getDetail(id);
	}

	/**
	 * 按照entity分页查询
	 * 
	 * @param page
	 * @return
	 */
	public int countPageEntity(Page<SiteProduct> page) {
		return dao.countPageEntity(page);
	}

	@Transactional(readOnly = false)
	public int saveBatch(List<SiteProduct> toPrePickList) {
		return dao.insertAll(toPrePickList);
	}

	public List<ProductSku> findSkuList(SiteProduct siteProduct) {
		return dao.findSkuList(siteProduct);
	}
	
	public List<String> findBrandList(SiteProduct siteProduct){
		return dao.findBrandList(siteProduct);
	}

}
