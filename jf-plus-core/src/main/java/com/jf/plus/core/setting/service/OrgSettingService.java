package com.jf.plus.core.setting.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jf.plus.core.setting.dao.OrgSettingDao;
import com.jf.plus.core.setting.entity.OrgSetting;

import cn.iutils.common.service.CrudService;

/**
* 机构配置表 Service层
* @author Tng
* @version 1.0
*/
@Service
@Transactional(readOnly = true)
public class OrgSettingService extends CrudService<OrgSettingDao, OrgSetting> {

}
