package com.jf.plus.core.mallSetting.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jf.plus.core.mallSetting.dao.MallSiteRuleRecordDao;
import com.jf.plus.core.mallSetting.entity.MallSiteRuleRecord;

import cn.iutils.common.service.CrudService;

/**
*  Service层
* @author Tng
* @version 1.0
*/
@Service
@Transactional(readOnly = true)
public class MallSiteRuleRecordService extends CrudService<MallSiteRuleRecordDao, MallSiteRuleRecord> {

}
