package com.jf.plus.core.product.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.iutils.common.service.CrudService;
import com.jf.plus.core.product.dao.ProductStockDao;
import com.jf.plus.core.product.entity.ProductStock;

/**
* 商品库存表 Service层
* @author Tng
* @version 1.0
*/
@Service
@Transactional(readOnly = true)
public class ProductStockService extends CrudService<ProductStockDao, ProductStock> {

}
