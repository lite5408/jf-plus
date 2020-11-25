package com.jf.plus.core.mallSetting.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.iutils.common.service.CrudService;
import com.jf.plus.core.mallSetting.dao.PacksPresentDao;
import com.jf.plus.core.mallSetting.entity.PacksPresent;

/**
* 礼包赠送记录 Service层
* @author Tng
* @version 1.0
*/
@Service
@Transactional(readOnly = true)
public class PacksPresentService extends CrudService<PacksPresentDao, PacksPresent> {

}
