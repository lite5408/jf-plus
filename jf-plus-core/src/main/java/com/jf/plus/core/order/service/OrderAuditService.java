package com.jf.plus.core.order.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jf.plus.common.core.enums.OrderAuditStatus;
import com.jf.plus.common.utils.DateUtils;
import com.jf.plus.core.order.dao.OrderAuditDao;
import com.jf.plus.core.order.dao.OrderAuditDetailDao;
import com.jf.plus.core.order.dao.OrderDao;
import com.jf.plus.core.order.dao.OrderDetailDao;
import com.jf.plus.core.order.entity.Order;
import com.jf.plus.core.order.entity.OrderAudit;
import com.jf.plus.core.order.entity.OrderAuditDetail;
import com.jf.plus.core.order.entity.OrderDetail;

import cn.iutils.common.DataScope;
import cn.iutils.common.Page;
import cn.iutils.common.service.CrudService;

/**
 * 订单审核表 Service层
 * @author Tng
 * @version 1.0
 */
@Service
@Transactional(readOnly = true)
public class OrderAuditService extends CrudService<OrderAuditDao, OrderAudit> {

	@Autowired
	private OrderDao orderDao;

	@Autowired
	private OrderDetailDao orderDetailDao;

	@Autowired
	private OrderAuditDetailDao orderAuditDetailDao;

	/**
	 * 创建审批单
	 *
	 * @param order
	 * @param expireDate
	 *            审核有效期
	 * @param taskProcessId
	 *            流程ID
	 * @return
	 */
	public void createOrderAudit(Long orderId, String taskProcessId) {
		Order order = orderDao.get(orderId.toString());
		Date expireDate = DateUtils.getOrderExpiredDate();
		OrderAudit oa = new OrderAudit();
		oa.setOrderNo(order.getOrderNo());
		oa.setOrgId(order.getOrgId());
		oa.setSiteId(order.getSiteId());
		oa.setTotalNum(order.getTotalNum());
		oa.setTotalAmount(order.getTotalAmount());
		oa.setTotalPoints(order.getTotalPoints());
		oa.setUserId(order.getUserId());
		oa.setExpireDate(expireDate);
		oa.setAuditStatus(OrderAuditStatus.LEVEL_1.getType());
//		oa.setAuditDate(new Date());
		oa.setTaskProcessId(taskProcessId);
		oa.setCreateBy(order.getCreateBy());
		oa.setCreateDate(new Date());
		oa.setUpdateBy(order.getUpdateBy());
		oa.setUpdateDate(new Date());
		dao.insert(oa);

		OrderDetail od = new OrderDetail();
		od.setOrderId(orderId);
		List<OrderDetail> odList = orderDetailDao.findList(od);

		for (OrderDetail orderDetail : odList) {
			OrderAuditDetail detail = new OrderAuditDetail();
			detail.setOrderAuditId(Long.valueOf(oa.getId()));
			detail.setOrderId(orderId);
			detail.setOrderNo(order.getOrderNo());
			detail.setItemId(orderDetail.getItemId());
			detail.setItemName(orderDetail.getItemName());
			detail.setSupplyId(orderDetail.getSupplyId());
			detail.setSupplyPrice(orderDetail.getSupplyPrice());
			detail.setSalePrice(orderDetail.getSalePrice());
			detail.setSalePoints(orderDetail.getSalePoints());
			detail.setSaleNum(orderDetail.getSaleNum());
			detail.setAmount(orderDetail.getAmount());
			detail.setCreateBy(order.getCreateBy());
			detail.setCreateDate(new Date());
			detail.setUpdateBy(order.getUpdateBy());
			detail.setUpdateDate(new Date());
			orderAuditDetailDao.insert(detail);
		}

	}

	/**
	 * 修改审批单
	 *
	 * @param orderId
	 * @param taskProcessId
	 * @param status
	 */
	@Transactional(readOnly = false)
	public void updateOrderAudit(Long orderId, String taskProcessId,Integer status) {
		Order order = orderDao.get(orderId.toString());
		OrderAudit orderAudit = new OrderAudit();
		orderAudit.setOrderNo(order.getOrderNo());
		orderAudit = dao.getEntity(orderAudit);
		orderAudit.setAuditStatus(status);
		dao.update(orderAudit);
	}

	/**
	 * 查询审批单
	 * @param page
	 * @return
	 */
	public List<OrderAudit> findAuditPage(Page<OrderAudit> page) {
		DataScope dataScope;
    	if(page!= null && page.getEntity() != null && (dataScope = page.getEntity().getDataScopeFilter()) != null){
    		dataScope.setDataScopeSql(dataScopeFilter(dataScope.getUser(),dataScope.getDataScopeEnum(),dataScope.getOrgAlias(),dataScope.getUserAlias()));
    	}
    	
		page.setTotal(dao.countAuditPage(page));
		return dao.findAuditPage(page);
	}
	
	public int countAuditPage(Page<OrderAudit> page) {
		DataScope dataScope;
    	if(page!= null && page.getEntity() != null && (dataScope = page.getEntity().getDataScopeFilter()) != null){
    		dataScope.setDataScopeSql(dataScopeFilter(dataScope.getUser(),dataScope.getDataScopeEnum(),dataScope.getOrgAlias(),dataScope.getUserAlias()));
    	}
    	
		return dao.countAuditPage(page);
	}

}
