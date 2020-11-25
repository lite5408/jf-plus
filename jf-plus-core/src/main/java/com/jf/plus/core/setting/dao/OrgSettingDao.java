package com.jf.plus.core.setting.dao;

import com.jf.plus.core.setting.entity.OrgSetting;

import cn.iutils.common.ICrudDao;
import cn.iutils.common.annotation.MyBatisDao;

/**
* 机构配置表 DAO接口
* @author Tng
* @version 1.0
*/
@MyBatisDao
public interface OrgSettingDao extends ICrudDao<OrgSetting> {

}
