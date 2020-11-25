package com.jf.plus.core.setting.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.iutils.common.service.CrudService;
import com.jf.plus.core.setting.dao.DistrictJdDao;
import com.jf.plus.core.setting.entity.DistrictJd;

/**
*  Service层
* @author Tng
* @version 1.0
*/
@Service
@Transactional(readOnly = true)
public class DistrictJdService extends CrudService<DistrictJdDao, DistrictJd> {

}
