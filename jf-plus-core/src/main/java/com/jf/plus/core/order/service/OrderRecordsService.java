package com.jf.plus.core.order.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.iutils.common.service.CrudService;
import com.jf.plus.core.order.dao.OrderRecordsDao;
import com.jf.plus.core.order.entity.OrderRecords;

/**
* 订单支付记录 Service层
* @author Tng
* @version 1.0
*/
@Service
@Transactional(readOnly = true)
public class OrderRecordsService extends CrudService<OrderRecordsDao, OrderRecords> {

}
