package com.jf.plus.core.order.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.jf.plus.core.order.entity.Order;

import cn.iutils.common.ICrudDao;
import cn.iutils.common.Page;
import cn.iutils.common.annotation.MyBatisDao;

/**
 * 订单主表 DAO接口
 * @author Tng
 * @version 1.0
 */
@MyBatisDao
public interface OrderDao extends ICrudDao<Order> {

	public List<Order> findOrderList(@Param("page") Page<Order> page);

	public int findOrderCount(@Param("page") Page<Order> page);

	public List<Order> findReportList(@Param("page") Page<Order> page);

	public int findReportCount(@Param("page") Page<Order> page);

	public Order getDetail(String orderNo);

	public int countSiteOrder(@Param("page") Page<Order> page);

	public List<Order> findPageSiteOrder(@Param("page") Page<Order> page);

	public int countSupplyOrder(@Param("page") Page<Order> page);

	public List<Order> findPageSupplyOrder(@Param("page") Page<Order> page);

	public int countOrderPage(@Param("page") Page<Order> page);

	public List<Order> findOrderPage(@Param("page") Page<Order> page);

	public int countOrderFlowPage(@Param("page") Page<Order> page);

	public List<Order> findOrderFlowPage(@Param("page") Page<Order> page);

	public int countJfOrderPage(@Param("page") Page<Order> page);

	public List<Order> findJfOrderPage(@Param("page") Page<Order> page);

	public Double sumOrderAmount(@Param("page") Page<Order> page);

	public List<Order> findBatchOrderList(Order o);

	public List<Order> findYXBatchOrderList(Order o);

	public int countBuyerOrder(@Param("page") Page<Order> page);

	public List<Order> findPageBuyerOrder(@Param("page") Page<Order> page);

	public Map<String, Object> sumOrderSaleReport(@Param("page") Page<Order> page);

	public int countOrderV1(@Param("page") Page<Order> page);

	public List<Order> findPageV1(@Param("page") Page<Order> page);

	public int countToDeliverOrder(@Param("page") Page<Order> page);

	public List<Order> findPageToDeliveryOrder(@Param("page")Page<Order> page);

	public Map<String, Object> sumOrderReport(@Param("page") Page<Order> page);

	public int countProductOrder(@Param("page") Page<Order> page);

	public List<Order> findPageProductOrder(@Param("page") Page<Order> page);

	public int updateEntity(Order toUpdateOrder);

	public List<Order> findPageBuyerSpecOrder(@Param("page") Page<Order> page);

	public Map<String, Object> sumDeliveryReport(@Param("page") Page<Order> page);

	public Map<String, Object> sumToDeliveryReport(@Param("page") Page<Order> page);

	public Map<String, Object> sumOrderBuyerReport(@Param("page") Page<Order> page);

	public int sumProdSales(@Param("page") Page<Order> page);
	

}
