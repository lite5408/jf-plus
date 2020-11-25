package com.jf.plus.core.order.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.iutils.common.Page;
import cn.iutils.common.service.CrudService;
import com.jf.plus.core.order.dao.OrderDeliveryDao;
import com.jf.plus.core.order.entity.OrderDelivery;

/**
* 订单发货表 Service层
* @author Tng
* @version 1.0
*/
@Service
@Transactional(readOnly = true)
public class OrderDeliveryService extends CrudService<OrderDeliveryDao, OrderDelivery> {

	@Transactional(readOnly = false)
	public int confirmOrderDetail(OrderDelivery orderDelivery) {
		return dao.confirmOrderDetail(orderDelivery);
		
	}

	public List<OrderDelivery> findProductDeliveryPage(Page<OrderDelivery> page) {
		page.setTotal(dao.countProductDelivery(page));
		return dao.findProductDeliveryPage(page);
	}

	public OrderDelivery getSumEntity(OrderDelivery orderDelivery) {
		return dao.getSumEntity(orderDelivery);
	}

}
