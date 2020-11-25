package com.jf.plus.core.product.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.iutils.common.service.CrudService;
import com.jf.plus.core.product.dao.ProductSaleRuleDao;
import com.jf.plus.core.product.entity.ProductSaleRule;

/**
* 销售规则 Service层
* @author Tng
* @version 1.0
*/
@Service
@Transactional(readOnly = true)
public class ProductSaleRuleService extends CrudService<ProductSaleRuleDao, ProductSaleRule> {

}
