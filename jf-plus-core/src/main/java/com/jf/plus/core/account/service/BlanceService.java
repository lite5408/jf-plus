package com.jf.plus.core.account.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jf.plus.core.account.dao.BlanceDao;
import com.jf.plus.core.account.entity.Blance;

import cn.iutils.common.service.CrudService;

@Service
@Transactional(readOnly = false)
public class BlanceService extends CrudService<BlanceDao, Blance>{
	
	/**
	 * 获取机构可用余额
	 * @param orgId
	 * @return
	 */
	public Double getOrgBlance(String orgId){
		Double orgBlance = checkNullValue(dao.getOrgBlance(orgId));
		Double voucherNotUsed = checkNullValue(dao.getVoucherNotUsed(orgId));
		Double packsNotUsed = checkNullValue(dao.getPacksNotUsed(orgId));
		return Double.valueOf(orgBlance.doubleValue() - (voucherNotUsed.doubleValue() + packsNotUsed.doubleValue()));
	}
	
	/**
	 * 获取业务经理剩余集采额度
	 * @param orgId
	 * @return
	 */
	public Double getJCBlance(String userId){
		return checkNullValue(dao.getJCBlance(userId));
	}
	
	/**
	 * 获取业务经理剩余分发额度
	 * @param orgId
	 * @return
	 */
	public Double getFFBlance(String userId){
		return checkNullValue(dao.getFFBlance(userId));
	}
	
	/**
	 * 获取用户积分额度
	 * @param orgId
	 * @return
	 */
	public Double getJFBlance(String userId){
		return checkNullValue(dao.getJFBlance(userId));
	}
	
	public Double getUserOrgJFBlance(Blance blance){
		return checkNullValue(dao.getUserOrgJFBlance(blance));
	}
	
	private Double checkNullValue(Double value){
		if (value == null) {
			value = 0D;
		}
		return value;
	}
}
