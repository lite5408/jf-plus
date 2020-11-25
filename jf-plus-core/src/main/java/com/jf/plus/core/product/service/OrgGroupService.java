package com.jf.plus.core.product.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.iutils.common.service.CrudService;
import com.jf.plus.core.product.dao.OrgGroupDao;
import com.jf.plus.core.product.entity.OrgGroup;

/**
* 供应商分组表 Service层
* @author Tng
* @version 1.0
*/
@Service
@Transactional(readOnly = true)
public class OrgGroupService extends CrudService<OrgGroupDao, OrgGroup> {

}
