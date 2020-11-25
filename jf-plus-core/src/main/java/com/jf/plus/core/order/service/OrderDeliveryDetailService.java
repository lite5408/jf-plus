package com.jf.plus.core.order.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.iutils.common.service.CrudService;
import com.jf.plus.core.order.dao.OrderDeliveryDetailDao;
import com.jf.plus.core.order.entity.OrderDeliveryDetail;

/**
* 订单发货商品表 Service层
* @author Tng
* @version 1.0
*/
@Service
@Transactional(readOnly = true)
public class OrderDeliveryDetailService extends CrudService<OrderDeliveryDetailDao, OrderDeliveryDetail> {

}
