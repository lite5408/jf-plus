package com.jf.plus.core.order.dao;

import cn.iutils.common.ICrudDao;
import cn.iutils.common.annotation.MyBatisDao;
import com.jf.plus.core.order.entity.OrderRecords;

/**
* 订单支付记录 DAO接口
* @author Tng
* @version 1.0
*/
@MyBatisDao
public interface OrderRecordsDao extends ICrudDao<OrderRecords> {

}
