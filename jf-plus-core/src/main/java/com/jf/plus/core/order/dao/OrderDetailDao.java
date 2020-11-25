package com.jf.plus.core.order.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.jf.plus.core.order.entity.OrderDetail;

import cn.iutils.common.ICrudDao;
import cn.iutils.common.Page;
import cn.iutils.common.annotation.MyBatisDao;

/**
 * 订单明细表 DAO接口
 *
 * @author Tng
 * @version 1.0
 */
@MyBatisDao
public interface OrderDetailDao extends ICrudDao<OrderDetail> {

	public int countSiteOrderDetail(@Param("page") Page<OrderDetail> page);

	public List<OrderDetail> findPageSiteOrderDetail(@Param("page") Page<OrderDetail> page);

	public int getSaleNum(@Param("itemId") Long itemId);

	public int updateByEntity(OrderDetail orderDetail);
	
	public List<OrderDetail> findDeliveryList(OrderDetail orderDetail);

	public int sumByEntity(OrderDetail orderDetail);

	public int countToDist(@Param("page") Page<OrderDetail> page);

	public List<OrderDetail> findToDistPage(@Param("page") Page<OrderDetail> page);

	public List<OrderDetail> findDistList(@Param("page") Page<OrderDetail> page);

	public int countDelivery(@Param("page") Page<OrderDetail> page);

	public List<OrderDetail> findDeliveryPage(@Param("page") Page<OrderDetail> page);

	public int trackDetail(OrderDetail orderDetail);

	public int distOrderNoDetail(OrderDetail orderDetail);

	public int mergeOrderDetail(OrderDetail orderDetail);

	public int countReportPage(@Param("page") Page<OrderDetail> page);

	public List<OrderDetail> findReportPage(@Param("page") Page<OrderDetail> page);

}
