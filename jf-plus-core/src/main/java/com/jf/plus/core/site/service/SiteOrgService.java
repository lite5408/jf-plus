package com.jf.plus.core.site.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.iutils.common.service.CrudService;
import com.jf.plus.core.site.dao.SiteOrgDao;
import com.jf.plus.core.site.entity.SiteOrg;

/**
* 站点组织关系表 Service层
* @author Tng
* @version 1.0
*/
@Service
@Transactional(readOnly = true)
public class SiteOrgService extends CrudService<SiteOrgDao, SiteOrg> {

}
