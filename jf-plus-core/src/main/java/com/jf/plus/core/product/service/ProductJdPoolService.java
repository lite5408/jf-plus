package com.jf.plus.core.product.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jf.plus.core.product.dao.ProductJdPoolDao;
import com.jf.plus.core.product.entity.ProductJdPool;

import cn.iutils.common.service.CrudService;

@Service
@Transactional(readOnly = true)
public class ProductJdPoolService extends CrudService<ProductJdPoolDao, ProductJdPool> {

}
