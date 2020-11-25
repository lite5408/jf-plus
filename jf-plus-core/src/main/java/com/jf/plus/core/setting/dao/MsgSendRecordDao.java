package com.jf.plus.core.setting.dao;

import cn.iutils.common.ICrudDao;
import cn.iutils.common.annotation.MyBatisDao;
import com.jf.plus.core.setting.entity.MsgSendRecord;

/**
* 消息发送记录表 DAO接口
* @author Tng
* @version 1.0
*/
@MyBatisDao
public interface MsgSendRecordDao extends ICrudDao<MsgSendRecord> {

	int updateEntity(MsgSendRecord msgSendRecord1);

}
