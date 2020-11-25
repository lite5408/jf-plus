package com.jf.plus.core.product.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.iutils.common.service.CrudService;
import com.jf.plus.core.product.dao.ProductSaleRuleMerchantDao;
import com.jf.plus.core.product.entity.ProductSaleRuleMerchant;

/**
* 销售规则分组机构表 Service层
* @author Tng
* @version 1.0
*/
@Service
@Transactional(readOnly = true)
public class ProductSaleRuleMerchantService extends CrudService<ProductSaleRuleMerchantDao, ProductSaleRuleMerchant> {

}
