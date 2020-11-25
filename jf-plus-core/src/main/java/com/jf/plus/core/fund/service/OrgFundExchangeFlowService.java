package com.jf.plus.core.fund.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jf.plus.common.core.Result;
import com.jf.plus.common.core.ResultCode;
import com.jf.plus.common.core.enums.AbstractType;
import com.jf.plus.common.core.enums.DealType;
import com.jf.plus.core.account.dao.OrgAccountRechargeDao;
import com.jf.plus.core.account.dao.VoucherFlowDao;
import com.jf.plus.core.account.entity.OrgAccountRecharge;
import com.jf.plus.core.account.entity.VoucherFlow;

/**
 * 
 * 机构用户资金流水的业务逻辑service
 * 
 * @author Tng
 * 
 *
 */
@Service
public class OrgFundExchangeFlowService extends FundExchangeFlowService {
	
	@Autowired
	OrgAccountRechargeDao orgAccountRechargeDao;
	
	@Autowired
	VoucherFlowDao voucherFlowDao;
	
	/**
	 * 机构充值流水
	 * 
	 * @param o
	 * @return
	 */
	public Result orgAccountFlow(Object o) {
		Result result = new Result();
		if(o instanceof OrgAccountRecharge){
			OrgAccountRecharge outOrgAccountRecharge = (OrgAccountRecharge) o;//转出账户流水
			//记录转出账户流水
			outOrgAccountRecharge.preInsert();
			orgAccountRechargeDao.insert(outOrgAccountRecharge);
			
			//记录收入账户流水
			OrgAccountRecharge inOrgAccountRecharge = new OrgAccountRecharge();
			inOrgAccountRecharge.setAbstractType(AbstractType.AMOUNT_DIST.getType());
			inOrgAccountRecharge.setAccountId(outOrgAccountRecharge.getTargetId());//账户
			inOrgAccountRecharge.setAccountType("purchase");
			inOrgAccountRecharge.setDealType(DealType.INCOME.getType());
			inOrgAccountRecharge.setAmount(Math.abs(outOrgAccountRecharge.getAmount()));
			inOrgAccountRecharge.setAttachments("");
			inOrgAccountRecharge.setOperTime(new Date());
			inOrgAccountRecharge.setTargetId(0L);//转入账户（自己本身统一设0）
			inOrgAccountRecharge.preInsert();
			inOrgAccountRecharge.setRemarks(outOrgAccountRecharge.getRemarks());
			orgAccountRechargeDao.insert(inOrgAccountRecharge);
		}
		result.setCode(ResultCode.SUCCESS);
		result.setMsg("操作成功");
		return result;
	}
	
	/**
	 * 采购/业务经理额度分发流水
	 * 
	 * @param o
	 * @return
	 */
	public Result orgUserVoucherFlow(Object o) {
		Result result = new Result();
		if(o instanceof VoucherFlow){
			VoucherFlow voucherFlow = (VoucherFlow) o;//转出账户流水
			//记录转出账户流水
			voucherFlow.preInsert();
			voucherFlowDao.insert(voucherFlow);
		}
		result.setCode(ResultCode.SUCCESS);
		return result;
	}

	/**
	 * 额度互拨流水
	 * 
	 * @param o
	 * @return
	 */
	public Result orgUserVoucherTransfer(Object o) {
		Result result = new Result();

		return result;
	}

}
