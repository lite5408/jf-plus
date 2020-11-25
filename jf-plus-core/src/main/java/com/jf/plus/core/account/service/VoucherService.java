package com.jf.plus.core.account.service;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.jf.plus.core.account.dao.VoucherDao;
import com.jf.plus.core.account.entity.Voucher;
import cn.iutils.common.Page;
import cn.iutils.common.service.CrudService;
/**
 * 电子券信息表 Service层
 *
 * @author Tng
 * @version 1.0
 */
@Service
@Transactional(readOnly = true)
public class VoucherService extends CrudService<VoucherDao, Voucher> {

	public List<Voucher> findDistVoucherList(Page<Voucher> page) {
		return dao.findDistVoucherList(page);
	}

	public List<Voucher> findVoucherList(Page<Voucher> page) {
		page.setTotal(dao.findVoucherCount(page));
		return dao.findVoucherList(page);
	}

}
