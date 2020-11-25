package com.jf.plus.core.order.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jf.plus.core.order.dao.FinanceOrderDao;
import com.jf.plus.core.order.entity.FinanceOrder;

import cn.iutils.common.service.CrudService;

/**
* 对账订单明细表 Service层
* @author Tng
* @version 1.0
*/
@Service
@Transactional(readOnly = true)
public class FinanceOrderService extends CrudService<FinanceOrderDao, FinanceOrder> {

	@Transactional(readOnly = false)
	public int cancelFinanceOrder(List idsList) {
		return dao.cancelFinaceOrder(idsList);
	}

	@Transactional(readOnly = false)
	public int updateMatchStatus(String batchNo) {
		return dao.updateMatchStatus(batchNo);
	}

	@Transactional(readOnly = false)
	public int submit(String batchNo) {
		return dao.submit(batchNo);
		
	}

}
