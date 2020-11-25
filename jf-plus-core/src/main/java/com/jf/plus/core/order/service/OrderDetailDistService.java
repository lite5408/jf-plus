package com.jf.plus.core.order.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.iutils.common.service.CrudService;
import com.jf.plus.core.order.dao.OrderDetailDistDao;
import com.jf.plus.core.order.entity.OrderDetailDist;

/**
* 订单分配表 Service层
* @author Tng
* @version 1.0
*/
@Service
@Transactional(readOnly = true)
public class OrderDetailDistService extends CrudService<OrderDetailDistDao, OrderDetailDist> {

}
