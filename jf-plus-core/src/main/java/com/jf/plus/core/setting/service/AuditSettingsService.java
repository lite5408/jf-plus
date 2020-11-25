package com.jf.plus.core.setting.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jf.plus.core.setting.dao.AuditSettingsDao;
import com.jf.plus.core.setting.entity.AuditSettings;

import cn.iutils.common.service.CrudService;

/**
 * 审批设置 Service层
 * 
 * @author Tng
 * @version 1.0
 */
@Service
@Transactional(readOnly = true)
public class AuditSettingsService extends CrudService<AuditSettingsDao, AuditSettings> {
	
	@Transactional(readOnly = false)
	public int updateFull(AuditSettings auditSettings) {
		return dao.updateFull(auditSettings);
	}
}
