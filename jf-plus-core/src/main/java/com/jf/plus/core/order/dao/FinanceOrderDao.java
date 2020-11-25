package com.jf.plus.core.order.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.jf.plus.core.order.entity.FinanceOrder;

import cn.iutils.common.ICrudDao;
import cn.iutils.common.annotation.MyBatisDao;

/**
* 对账订单明细表 DAO接口
* @author Tng
* @version 1.0
*/
@MyBatisDao
public interface FinanceOrderDao extends ICrudDao<FinanceOrder> {
	int cancelFinaceOrder(@Param("orderIdList") List orderIdList);

	int updateMatchStatus(@Param("batchNo") String batchNo);

	int submit(@Param("batchNo") String batchNo);
}
