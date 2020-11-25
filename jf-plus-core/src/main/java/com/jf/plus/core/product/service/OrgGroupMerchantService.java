package com.jf.plus.core.product.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jf.plus.core.product.dao.OrgGroupMerchantDao;
import com.jf.plus.core.product.entity.OrgGroupMerchant;

import cn.iutils.common.service.CrudService;

/**
* 供应商分组表 Service层
* @author Tng
* @version 1.0
*/
@Service
@Transactional(readOnly = true)
public class OrgGroupMerchantService extends CrudService<OrgGroupMerchantDao, OrgGroupMerchant> {

}
