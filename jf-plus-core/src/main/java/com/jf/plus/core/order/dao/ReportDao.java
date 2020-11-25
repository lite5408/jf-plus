package com.jf.plus.core.order.dao;

import java.util.List;

import java.util.Map;

import com.jf.plus.core.order.entity.Order;

import cn.iutils.common.annotation.MyBatisDao;

/**
* 报表DAO
* @author Tng
* @version 1.0
*/
@MyBatisDao
public interface ReportDao {
	/**
	 * 商品销售报表
	 * @param order
	 * @return
	 */
	List<Map<String,Object>> findSaleReport(Order order);
	
	/**
	 * 按照供应商统计销售报表
	 * @param order
	 * @return
	 */
	List<Map<String,Object>> findSupplyReport(Order order);

	/**
	 * 品类销量榜单报表
	 * @param order
	 * @return
	 */
	List<Map<String, Object>> findTopCateReport(Order order);

	/**
	 * 供应商销售榜单
	 * @param order
	 * @return
	 */
	List<Map<String, Object>> findTopSupplyReport(Order order);
	
	/**
	 * 专查其他销售额度
	 * @param order
	 * @return
	 */
	List<Map<String, Object>> findTopOtherReport(Order order);
}
