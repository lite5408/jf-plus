package com.jf.plus.core.weixin.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.iutils.common.service.CrudService;
import com.jf.plus.core.weixin.dao.DjtWeixinMenuDao;
import com.jf.plus.core.weixin.entity.DjtWeixinMenu;

/**
* 资源 Service层
* @author Tng
* @version 1.0
*/
@Service
@Transactional(readOnly = true)
public class DjtWeixinMenuService extends CrudService<DjtWeixinMenuDao, DjtWeixinMenu> {

}
