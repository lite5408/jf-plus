package com.jf.plus.core.product.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jf.plus.core.product.dao.ProductDetailDao;
import com.jf.plus.core.product.entity.ProductDetail;

import cn.iutils.common.service.CrudService;

/**
 * 商品详情表 Service层
 * @author Tng
 * @version 1.0
 */
@Service
@Transactional(readOnly = true)
public class ProductDetailService extends CrudService<ProductDetailDao, ProductDetail> {

	@Transactional(readOnly = false)
	public int saveBatch(List<ProductDetail> details) {
		return dao.insertAll(details);
	}

}
