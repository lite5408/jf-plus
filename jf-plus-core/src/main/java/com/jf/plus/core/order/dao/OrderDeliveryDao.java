package com.jf.plus.core.order.dao;

import cn.iutils.common.ICrudDao;
import cn.iutils.common.Page;
import cn.iutils.common.annotation.MyBatisDao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.jf.plus.core.order.entity.OrderDelivery;

/**
* 订单发货表 DAO接口
* @author Tng
* @version 1.0
*/
@MyBatisDao
public interface OrderDeliveryDao extends ICrudDao<OrderDelivery> {

	int updateEntity(OrderDelivery orderDelivery);

	int confirmOrderDetail(OrderDelivery orderDelivery);

	int countProductDelivery(@Param("page") Page<OrderDelivery> page);

	List<OrderDelivery> findProductDeliveryPage(@Param("page") Page<OrderDelivery> page);

	OrderDelivery getSumEntity(OrderDelivery orderDelivery);
}
