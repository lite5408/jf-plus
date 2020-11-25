package com.jf.plus.core.order.dao;

import cn.iutils.common.ICrudDao;
import cn.iutils.common.annotation.MyBatisDao;
import com.jf.plus.core.order.entity.OrderBatch;

/**
*  DAO接口
* @author Tng
* @version 1.0
*/
@MyBatisDao
public interface OrderBatchDao extends ICrudDao<OrderBatch> {
	int updateAreaAndStatus(OrderBatch orderBatch);

	int updateAllOrderBatchToErr(OrderBatch orderBatch);
	
	int updateSNAreaAndStatus(OrderBatch orderBatch);

	int updateOrderBatch(OrderBatch orderBatch);

}
