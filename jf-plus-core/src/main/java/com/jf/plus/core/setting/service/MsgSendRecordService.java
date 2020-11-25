package com.jf.plus.core.setting.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jf.plus.core.setting.dao.MsgSendRecordDao;
import com.jf.plus.core.setting.entity.MsgSendRecord;

import cn.iutils.common.service.CrudService;

/**
* 消息发送记录表 Service层
* @author Tng
* @version 1.0
*/
@Service
@Transactional(readOnly = true)
public class MsgSendRecordService extends CrudService<MsgSendRecordDao, MsgSendRecord> {

	@Transactional(readOnly = false)
	public int updateEntity(MsgSendRecord msgSendRecord1) {
		return dao.updateEntity(msgSendRecord1);
	}

}
