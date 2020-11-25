package com.jf.plus.core.order.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jf.plus.core.order.dao.FinanceBookDao;
import com.jf.plus.core.order.entity.FinanceBook;

import cn.iutils.common.service.CrudService;

/**
* 财务对账簿表 Service层
* @author Tng
* @version 1.0
*/
@Service
@Transactional(readOnly = true)
public class FinanceBookService extends CrudService<FinanceBookDao, FinanceBook> {

	@Transactional(readOnly = true)
	public int batchUpdate(FinanceBook financeBook) {
		return dao.batchUpdate(financeBook);
	}

}
