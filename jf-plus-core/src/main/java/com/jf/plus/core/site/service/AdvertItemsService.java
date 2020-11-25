package com.jf.plus.core.site.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jf.plus.core.site.dao.AdvertItemsDao;
import com.jf.plus.core.site.entity.AdvertItems;

import cn.iutils.common.service.CrudService;

/**
* 商城广告专题商品表 Service层
* @author Tng
* @version 1.0
*/
@Service
@Transactional(readOnly = true)
public class AdvertItemsService extends CrudService<AdvertItemsDao, AdvertItems> {

}
