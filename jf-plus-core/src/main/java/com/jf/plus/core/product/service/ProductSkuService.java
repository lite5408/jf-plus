package com.jf.plus.core.product.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.iutils.common.service.CrudService;
import com.jf.plus.core.product.dao.ProductSkuDao;
import com.jf.plus.core.product.entity.ProductSku;

/**
*  Service层
* @author Tng
* @version 1.0
*/
@Service
@Transactional(readOnly = true)
public class ProductSkuService extends CrudService<ProductSkuDao, ProductSku> {

}
