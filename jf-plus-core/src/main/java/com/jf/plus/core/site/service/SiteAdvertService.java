package com.jf.plus.core.site.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.iutils.common.service.CrudService;
import com.jf.plus.core.site.dao.SiteAdvertDao;
import com.jf.plus.core.site.entity.SiteAdvert;

/**
* 商城广告专题配置 Service层
* @author Tng
* @version 1.0
*/
@Service
@Transactional(readOnly = true)
public class SiteAdvertService extends CrudService<SiteAdvertDao, SiteAdvert> {

}
