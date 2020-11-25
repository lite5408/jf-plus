package com.jf.plus.core.weixin.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.iutils.common.service.CrudService;
import com.jf.plus.core.weixin.dao.DjtWeixinAccountDao;
import com.jf.plus.core.weixin.entity.DjtWeixinAccount;

/**
*  Service层
* @author Tng
* @version 1.0
*/
@Service
@Transactional(readOnly = true)
public class DjtWeixinAccountService extends CrudService<DjtWeixinAccountDao, DjtWeixinAccount> {

}
