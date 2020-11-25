package com.jf.plus.core.fund.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jf.plus.common.core.Result;
import com.jf.plus.common.core.ResultCode;
import com.jf.plus.core.account.dao.OrgAccountRechargeDao;
import com.jf.plus.core.account.dao.VoucherFlowDao;
import com.jf.plus.core.account.entity.OrgAccountRecharge;
import com.jf.plus.core.account.entity.VoucherFlow;

/**
 *
 * 券资金流水的业务逻辑service
 *
 * @author Tng
 *
 *
 */
@Service
public class VoucherFundExchangeFlowService extends FundExchangeFlowService {

	@Autowired
	VoucherFlowDao voucherFlowDao;

	@Autowired
	OrgAccountRechargeDao orgAccountRechargeDao;

	/**
	 * 订单下单流水
	 *
	 * @param o
	 * @return
	 */
	@Override
	public Result orderSubmitFlow(Object o) {
		Result result = new Result();
		// 券流水
		if (o instanceof VoucherFlow) {
			VoucherFlow voucherFlow = (VoucherFlow) o;
			voucherFlow.preInsert();
			voucherFlowDao.insert(voucherFlow);
		}

		// 订单流水
		if (o instanceof OrgAccountRecharge) {
			OrgAccountRecharge orgAccountRecharge = (OrgAccountRecharge) o;
			orgAccountRecharge.preInsert();
			orgAccountRechargeDao.insert(orgAccountRecharge);
		}
		result.setCode(ResultCode.SUCCESS);
		return result;
	}

	/**
	 * 订单取消流水
	 *
	 * @param o
	 * @return
	 */
	@Override
	public Result orderCancelFlow(Object o) {
		Result result = new Result();

		// 券退回流水
		if (o instanceof VoucherFlow) {
			VoucherFlow voucherFlow = (VoucherFlow) o;
			voucherFlow.preInsert();
			voucherFlowDao.insert(voucherFlow);
		}
		// 订单退回流水
		if (o instanceof OrgAccountRecharge) {
			OrgAccountRecharge orgAccountRecharge = (OrgAccountRecharge) o;
			orgAccountRecharge.preInsert();
			orgAccountRechargeDao.insert(orgAccountRecharge);
		}
		result.setCode(ResultCode.SUCCESS);
		return result;
	}

	/**
	 * 业务经理分发积分流水
	 *
	 * @param o
	 * @return
	 */
	@Override
	public Result voucherBussDistFlow(Object o) {
		Result result = new Result();

		if (o instanceof VoucherFlow) {
			VoucherFlow voucherFlow = (VoucherFlow) o;
			voucherFlow.preInsert();
			voucherFlowDao.insert(voucherFlow);
		}
		result.setCode(ResultCode.SUCCESS);
		return result;
	}

	/**
	 * 业务经理分发礼包流水
	 *
	 * @param o
	 * @return
	 */
	@Override
	public Result packBussDistFlow(Object o) {
		Result result = new Result();

		if (o instanceof VoucherFlow) {
			VoucherFlow voucherFlow = (VoucherFlow) o;
			voucherFlow.preInsert();
			voucherFlowDao.insert(voucherFlow);
		}
		result.setCode(ResultCode.SUCCESS);
		return result;
	}

	/**
	 * 用户电子券生成流水
	 *
	 * @param o
	 * @return
	 */
	@Override
	public Result createUserVoucherFlow(Object o) {
		Result result = new Result();

		if (o instanceof VoucherFlow) {
			VoucherFlow voucherFlow = (VoucherFlow) o;
			voucherFlow.preInsert();
			voucherFlowDao.insert(voucherFlow);
		}
		result.setCode(ResultCode.SUCCESS);
		return result;
	}

	/**
	 * 用户礼包券生成流水
	 *
	 * @param o
	 * @return
	 */
	@Override
	public Result createUserPacksFlow(Object o) {
		Result result = new Result();

		if (o instanceof VoucherFlow) {
			VoucherFlow voucherFlow = (VoucherFlow) o;
			voucherFlow.preInsert();
			voucherFlowDao.insert(voucherFlow);
		}
		result.setCode(ResultCode.SUCCESS);
		return result;
	}

}
