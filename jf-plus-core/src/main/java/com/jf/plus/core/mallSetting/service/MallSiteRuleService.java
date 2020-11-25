package com.jf.plus.core.mallSetting.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.iutils.common.service.CrudService;
import com.jf.plus.core.mallSetting.dao.MallSiteRuleDao;
import com.jf.plus.core.mallSetting.entity.MallSiteRule;

/**
* 商城分销配置表 Service层
* @author Tng
* @version 1.0
*/
@Service
@Transactional(readOnly = true)
public class MallSiteRuleService extends CrudService<MallSiteRuleDao, MallSiteRule> {

}
