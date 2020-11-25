package com.jf.plus.core.order.dao;

import cn.iutils.common.ICrudDao;
import cn.iutils.common.Page;
import cn.iutils.common.annotation.MyBatisDao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.jf.plus.core.order.entity.OrderDeliveryExport;

/**
* 订单导出表 DAO接口
* @author Tng
* @version 1.0
*/
@MyBatisDao
public interface OrderDeliveryExportDao extends ICrudDao<OrderDeliveryExport> {

	List<OrderDeliveryExport> findBuyerPage(@Param("page") Page<OrderDeliveryExport> page);
	
	int countBuyer(@Param("page") Page<OrderDeliveryExport> page);

	Map<String, Object> sumExport(@Param("page") Page<OrderDeliveryExport> page);

	Map<String, Object> countExport(@Param("page") Page<OrderDeliveryExport> page);

}
