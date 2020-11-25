package com.jf.plus.core.order.dao;

import com.jf.plus.core.order.entity.FinanceBook;

import cn.iutils.common.ICrudDao;
import cn.iutils.common.annotation.MyBatisDao;

/**
* 财务对账簿表 DAO接口
* @author Tng
* @version 1.0
*/
@MyBatisDao
public interface FinanceBookDao extends ICrudDao<FinanceBook> {

	int batchUpdate(FinanceBook financeBook);

}
