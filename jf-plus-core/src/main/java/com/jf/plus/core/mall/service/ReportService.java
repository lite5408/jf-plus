/**
 * 
 */
package com.jf.plus.core.mall.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jf.plus.core.order.dao.ReportDao;
import com.jf.plus.core.order.entity.Order;

/**
 * 报表Service类
 * @author Tng
 *
 */
@Service
@Transactional(readOnly = true)
public class ReportService{
	
	@Autowired
	private ReportDao reportDao;
	
	/**
	 * 商品销售报表
	 * @param order
	 * @return
	 */
	public List<Map<String,Object>> findSaleReport(Order order){
		return reportDao.findSaleReport(order);
	}
	
	/**
	 * 商品销售报表
	 * @param order
	 * @return
	 */
	public List<Map<String,Object>> findSupplyReport(Order order){
		return reportDao.findSupplyReport(order);
	}

	/**
	 * 分类销售榜单
	 * @param order
	 * @return
	 */
	public List<Map<String, Object>> findTopCateReport(Order order) {
		return reportDao.findTopCateReport(order);
	}

	/**
	 * 供应商销售榜单
	 * @param order
	 * @return
	 */
	public List<Map<String, Object>> findTopSupplyReport(Order order) {
		return reportDao.findTopSupplyReport(order);
	}
	
	/**
	 * 查询其他销售数据
	 * @param order
	 * @return
	 */
	public List<Map<String, Object>> findTopOtherReport(Order order){
		return reportDao.findTopOtherReport(order);
	}
}
