package com.jf.plus.core.order.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jf.plus.common.core.Result;
import com.jf.plus.common.core.ResultCode;
import com.jf.plus.common.core.enums.OrderAuditStatus;
import com.jf.plus.common.core.enums.OrderTrackState;
import com.jf.plus.core.order.dao.OrderDao;
import com.jf.plus.core.order.dao.OrderDistribDao;
import com.jf.plus.core.order.entity.Order;
import com.jf.plus.core.order.entity.OrderDistrib;

import cn.iutils.common.service.CrudService;

/**
 * Service层
 *
 * @author Tng
 * @version 1.0
 */
@Service
@Transactional(readOnly = true)
public class OrderDistribService extends CrudService<OrderDistribDao, OrderDistrib> {

	@Autowired
	private OrderDao orderDao;

	/**
	 * 批量更新物流信息
	 *
	 * @param list
	 * @return
	 */
	@Transactional(readOnly = false)
	public Result batchUpdateLogistics(List<OrderDistrib> list, String supplyId) {
		Result result = Result.newInstance();
		Order order = null;
		Order updateOrder = null;
		for (OrderDistrib orderDistrib : list) {
			order = new Order();
			order.setOrderNo(orderDistrib.getOrderNo());
			order.setSupplyId(Long.valueOf(supplyId));
			order.setOperStatus(OrderAuditStatus.TORECEIVE.getType());
			order = orderDao.getEntity(order);
			if (order != null) {
				orderDistrib.setOrderId(Long.valueOf(order.getId()));
				dao.insert(orderDistrib);

				updateOrder = new Order();
				updateOrder.setId(order.getId());
				updateOrder.setTrackState(OrderTrackState.DELIVERED.getType());
				updateOrder.setUpdateDate(new Date());
				orderDao.update(updateOrder);
			}
		}
		result.setCode(ResultCode.SUCCESS);
		return result;
	}

}
