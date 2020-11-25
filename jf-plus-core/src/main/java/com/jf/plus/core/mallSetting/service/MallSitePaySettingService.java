package com.jf.plus.core.mallSetting.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.iutils.common.service.CrudService;
import com.jf.plus.core.mallSetting.dao.MallSitePaySettingDao;
import com.jf.plus.core.mallSetting.entity.MallSitePaySetting;

/**
* 站点支付配置表 Service层
* @author Tng
* @version 1.0
*/
@Service
@Transactional(readOnly = true)
public class MallSitePaySettingService extends CrudService<MallSitePaySettingDao, MallSitePaySetting> {

}
