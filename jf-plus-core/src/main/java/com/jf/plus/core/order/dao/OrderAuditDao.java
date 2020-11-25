package com.jf.plus.core.order.dao;

import cn.iutils.common.ICrudDao;
import cn.iutils.common.Page;
import cn.iutils.common.annotation.MyBatisDao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.jf.plus.core.order.entity.OrderAudit;

/**
* 订单审核表 DAO接口
* @author Tng
* @version 1.0
*/
@MyBatisDao
public interface OrderAuditDao extends ICrudDao<OrderAudit> {

	List<OrderAudit> findAuditPage(@Param("page") Page<OrderAudit> page);

	int countAuditPage(@Param("page") Page<OrderAudit> page);

}
