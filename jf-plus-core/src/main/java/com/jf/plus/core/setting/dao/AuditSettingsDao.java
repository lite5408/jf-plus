package com.jf.plus.core.setting.dao;

import cn.iutils.common.ICrudDao;
import cn.iutils.common.annotation.MyBatisDao;
import com.jf.plus.core.setting.entity.AuditSettings;

/**
* 审批设置 DAO接口
* @author Tng
* @version 1.0
*/
@MyBatisDao
public interface AuditSettingsDao extends ICrudDao<AuditSettings> {
	int updateFull(AuditSettings auditSettings);
}
