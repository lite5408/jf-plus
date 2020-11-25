package com.jf.plus.core.order.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jf.plus.common.core.enums.SaleType;
import com.jf.plus.core.order.dao.OrderDetailDao;
import com.jf.plus.core.order.entity.OrderDetail;
import com.jf.plus.core.product.dao.ProductStockDao;

import cn.iutils.common.Page;
import cn.iutils.common.service.CrudService;

/**
 * 订单明细表 Service层
 * @author Tng
 * @version 1.0
 */
@Service
@Transactional(readOnly = true)
public class OrderDetailService extends CrudService<OrderDetailDao, OrderDetail> {

	@Autowired
	ProductStockDao productStockDao;
	/**
	 * 获取商品销量
	 *
	 * @param itemId
	 * @return
	 */
	public int getSaleNum(Long itemId){
		return dao.getSaleNum(itemId);
	}

	@Transactional(readOnly = false)
	public int backStockBySupply(String orderNo) {
		OrderDetail entity = new OrderDetail();
		entity.setOrderNo(orderNo);
		List<OrderDetail> orderDetailList = dao.findList(entity);
		int count = 0;
		for(OrderDetail orderDetail : orderDetailList){
			if(SaleType.KH.getDescription().equals(orderDetail.getSaleType())){
				count += productStockDao.backStock(orderDetail.getProductNo(), orderDetail.getSaleNum());
			}
		}
		return count;
	}
	
	public List<OrderDetail> findDeliveryList(OrderDetail orderDetail){
		return dao.findDeliveryList(orderDetail);
	}

	public int sumByEntity(OrderDetail orderDetail) {
		return dao.sumByEntity(orderDetail);
	}

	public List<OrderDetail> findToDistPage(Page<OrderDetail> page) {
		page.setTotal(dao.countToDist(page));
		return dao.findToDistPage(page);
	}

	public List<OrderDetail> findDistList(Page<OrderDetail> page) {
		return dao.findDistList(page);
	}

	public List<OrderDetail> findDeliveryPage(Page<OrderDetail> page) {
		page.setTotal(dao.countDelivery(page));
		return dao.findDeliveryPage(page);
	}

	@Transactional(readOnly = false)
	public int trackDetail(OrderDetail orderDetail) {
		return dao.trackDetail(orderDetail);
		
	}

	@Transactional(readOnly = false)
	public int distOrderNoDetail(OrderDetail orderDetail) {
		return dao.distOrderNoDetail(orderDetail);
	}

	@Transactional(readOnly = false)
	public int mergeOrderDetail(OrderDetail orderDetail) {
		return dao.mergeOrderDetail(orderDetail);
	}

	public List<OrderDetail> findReportPage(Page<OrderDetail> page) {
		page.setTotal(dao.countReportPage(page));
		return dao.findReportPage(page);
	}

}
