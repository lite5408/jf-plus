package com.jf.plus.core.setting.dao;

import cn.iutils.common.ICrudDao;
import cn.iutils.common.annotation.MyBatisDao;
import com.jf.plus.core.setting.entity.SmsQueue;

/**
* 短信队列表 DAO接口
* @author Tng
* @version 1.0
*/
@MyBatisDao
public interface SmsQueueDao extends ICrudDao<SmsQueue> {

}
