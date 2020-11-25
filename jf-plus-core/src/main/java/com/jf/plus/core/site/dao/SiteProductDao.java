package com.jf.plus.core.site.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.jf.plus.core.product.entity.ProductSku;
import com.jf.plus.core.site.entity.SiteProduct;

import cn.iutils.common.ICrudDao;
import cn.iutils.common.Page;
import cn.iutils.common.annotation.MyBatisDao;

/**
 * 站点商品表 DAO接口
 * @author Tng
 * @version 1.0
 */
@MyBatisDao
public interface SiteProductDao extends ICrudDao<SiteProduct> {

	public List<SiteProduct> findSiteProductByAdvertIdList(@Param("page") Page<SiteProduct> page);

	public int findSiteProductByAdvertIdCount(@Param("page") Page<SiteProduct> page);

	public SiteProduct getDetail(Long id);

	public int countPageEntity(@Param("page") Page<SiteProduct> page);

	public int insertAll(List<SiteProduct> list);

	public List<ProductSku> findSkuList(SiteProduct siteProduct);
	
	public List<String> findBrandList(SiteProduct siteProduct);

}
