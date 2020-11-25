package com.jf.plus.core.product.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.iutils.common.service.CrudService;
import com.jf.plus.core.product.dao.ProductStatusLogDao;
import com.jf.plus.core.product.entity.ProductStatusLog;

/**
* 商品状态变更表 Service层
* @author Tng
* @version 1.0
*/
@Service
@Transactional(readOnly = true)
public class ProductStatusLogService extends CrudService<ProductStatusLogDao, ProductStatusLog> {

}
