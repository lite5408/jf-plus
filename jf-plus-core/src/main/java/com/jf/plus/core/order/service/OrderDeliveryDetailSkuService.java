package com.jf.plus.core.order.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.iutils.common.service.CrudService;
import com.jf.plus.core.order.dao.OrderDeliveryDetailSkuDao;
import com.jf.plus.core.order.entity.OrderDeliveryDetailSku;

/**
* 订单发货SKU表 Service层
* @author Tng
* @version 1.0
*/
@Service
@Transactional(readOnly = true)
public class OrderDeliveryDetailSkuService extends CrudService<OrderDeliveryDetailSkuDao, OrderDeliveryDetailSku> {

}
