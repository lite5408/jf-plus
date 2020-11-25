package com.jf.plus.core.account.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jf.plus.core.account.dao.VoucherFlowDao;
import com.jf.plus.core.account.entity.VoucherFlow;

import cn.iutils.common.Page;
import cn.iutils.common.service.CrudService;

/**
 * 电子券卡号交易记录表 Service层
 * @author Tng
 * @version 1.0
 */
@Service
@Transactional(readOnly = true)
public class VoucherFlowService extends CrudService<VoucherFlowDao, VoucherFlow> {

	public VoucherFlow findAccRecentOrderFlow(VoucherFlow voucherFlow) {
		return dao.findAccRecentOrderFlow(voucherFlow);
	}

	public List<VoucherFlow> findFlowList(Page<VoucherFlow> page) {
		page.setTotal(dao.findFlowCount(page));
		return dao.findFlowList(page);
	}

}
