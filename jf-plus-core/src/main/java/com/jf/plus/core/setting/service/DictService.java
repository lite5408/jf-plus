package com.jf.plus.core.setting.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.iutils.common.service.CrudService;
import com.jf.plus.core.setting.dao.DictDao;
import com.jf.plus.core.setting.entity.Dict;

/**
* 字典表 Service层
* @author Tng
* @version 1.0
*/
@Service
@Transactional(readOnly = true)
public class DictService extends CrudService<DictDao, Dict> {

}
