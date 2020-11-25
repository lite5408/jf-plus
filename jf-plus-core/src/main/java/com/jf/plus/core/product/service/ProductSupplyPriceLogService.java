package com.jf.plus.core.product.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jf.plus.core.product.dao.ProductSupplyPriceLogDao;
import com.jf.plus.core.product.entity.ProductSupplyPriceLog;

import cn.iutils.common.service.CrudService;

/**
*  Service层
* @author Tng
* @version 1.0
*/
@Service
@Transactional(readOnly = true)
public class ProductSupplyPriceLogService extends CrudService<ProductSupplyPriceLogDao, ProductSupplyPriceLog> {

}
