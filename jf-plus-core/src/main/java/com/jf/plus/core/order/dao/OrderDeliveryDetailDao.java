package com.jf.plus.core.order.dao;

import cn.iutils.common.ICrudDao;
import cn.iutils.common.annotation.MyBatisDao;
import com.jf.plus.core.order.entity.OrderDeliveryDetail;

/**
* 订单发货商品表 DAO接口
* @author Tng
* @version 1.0
*/
@MyBatisDao
public interface OrderDeliveryDetailDao extends ICrudDao<OrderDeliveryDetail> {

}
