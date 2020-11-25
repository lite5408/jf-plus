package com.jf.plus.core.product.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.iutils.common.service.CrudService;
import com.jf.plus.core.product.dao.CategoryAttrDao;
import com.jf.plus.core.product.entity.CategoryAttr;

/**
* 分类属性定义表 Service层
* @author Tng
* @version 1.0
*/
@Service
@Transactional(readOnly = true)
public class CategoryAttrService extends CrudService<CategoryAttrDao, CategoryAttr> {

}
