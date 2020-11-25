package com.jf.plus.core.setting.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.iutils.common.service.CrudService;
import com.jf.plus.core.setting.dao.MsgTemplateDao;
import com.jf.plus.core.setting.entity.MsgTemplate;

/**
* 消息模板表 Service层
* @author Tng
* @version 1.0
*/
@Service
@Transactional(readOnly = true)
public class MsgTemplateService extends CrudService<MsgTemplateDao, MsgTemplate> {

}
