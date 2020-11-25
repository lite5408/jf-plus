package com.jf.plus.core.mallSetting.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.iutils.common.service.CrudService;
import com.jf.plus.core.mallSetting.dao.PacksProductDao;
import com.jf.plus.core.mallSetting.entity.PacksProduct;

/**
* 礼包商品表 Service层
* @author Tng
* @version 1.0
*/
@Service
@Transactional(readOnly = true)
public class PacksProductService extends CrudService<PacksProductDao, PacksProduct> {

}
