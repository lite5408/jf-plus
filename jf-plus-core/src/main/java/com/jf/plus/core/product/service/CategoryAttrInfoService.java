package com.jf.plus.core.product.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.iutils.common.service.CrudService;
import com.jf.plus.core.product.dao.CategoryAttrInfoDao;
import com.jf.plus.core.product.entity.CategoryAttrInfo;

/**
* 分类属性值表 Service层
* @author Tng
* @version 1.0
*/
@Service
@Transactional(readOnly = true)
public class CategoryAttrInfoService extends CrudService<CategoryAttrInfoDao, CategoryAttrInfo> {

}
