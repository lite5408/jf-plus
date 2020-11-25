package com.jf.plus.core.mall.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.iutils.common.service.CrudService;
import com.jf.plus.core.mall.dao.MallProductCateDao;
import com.jf.plus.core.mall.entity.MallProductCate;

/**
* 商城商品分类 Service层
* @author Tng
* @version 1.0
*/
@Service
@Transactional(readOnly = true)
public class MallProductCateService extends CrudService<MallProductCateDao, MallProductCate> {

}
