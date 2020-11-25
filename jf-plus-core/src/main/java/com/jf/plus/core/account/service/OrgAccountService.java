package com.jf.plus.core.account.service;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jf.plus.common.core.Result;
import com.jf.plus.common.core.ResultCode;
import com.jf.plus.common.core.enums.AbstractType;
import com.jf.plus.common.core.enums.DealType;
import com.jf.plus.core.account.dao.OrgAccountDao;
import com.jf.plus.core.account.dao.OrgAccountRechargeDao;
import com.jf.plus.core.account.entity.OrgAccount;
import com.jf.plus.core.account.entity.OrgAccountRecharge;
import com.jf.plus.core.fund.service.OrgFundExchangeFlowService;

import cn.iutils.common.service.CrudService;
import cn.iutils.common.utils.UserUtils;
import cn.iutils.sys.dao.IOrganizationDao;
import cn.iutils.sys.entity.Organization;

/**
 * 组织资金账户表 Service层
 *
 * @author Tng
 * @version 1.0
 */
@Service
@Transactional(readOnly = true)
public class OrgAccountService extends CrudService<OrgAccountDao, OrgAccount> {

	@Autowired
	OrgAccountRechargeDao orgAccountRechargeDao;

	@Autowired
	IOrganizationDao organizationDao;
	
	@Autowired
	OrgFundExchangeFlowService orgFundExchangeFlowService;

	/**
	 * 获取当前组织资金账户
	 *
	 * @return
	 */
	public OrgAccount getLoginUserOrgAccount() {
		String organizationId = UserUtils.getLoginUser().getOrganizationId();
		OrgAccount curOrgAccount = new OrgAccount();
		curOrgAccount.setOrgId(Long.valueOf(organizationId));
		curOrgAccount = dao.getEntity(curOrgAccount);
		return curOrgAccount;
	}
	
	public OrgAccount getOrgAccountByOrgId(String organizationId) {
		OrgAccount curOrgAccount = new OrgAccount();
		curOrgAccount.setOrgId(Long.valueOf(organizationId));
		curOrgAccount = dao.getEntity(curOrgAccount);
		return curOrgAccount;
	}

	/**
	 * 充值
	 *
	 * @param orgAccount
	 * @return
	 */
	@Transactional(readOnly = false)
	public int recharge(OrgAccount orgAccount) {
		Double rechargeAmount = orgAccount.getPurchaseBlance();// 充值余额
		Organization organization = organizationDao.get(orgAccount.getOrganizationId()+"");
		OrgAccount parentOrgAccount = getOrgAccountByOrgId(organization.getParentId());//被充值机构的父机构额度
		
		int operRet = 1;
		if (parentOrgAccount.getPurchaseBlance() <= 0 
				|| parentOrgAccount.getPurchaseBlance() < rechargeAmount) {
			operRet = 0;
		}

		if (operRet == 1) {
			//收入账户操作
			if (StringUtils.isBlank(orgAccount.getId())) {
				// 充值
				orgAccount.setPurchaseTotalAmount(rechargeAmount);
				save(orgAccount);
			} else {
				OrgAccount dbOrgAccount = dao.get(orgAccount.getId());
				orgAccount.setPurchaseBlance(dbOrgAccount.getPurchaseBlance() + rechargeAmount);
				orgAccount.setPurchaseTotalAmount(dbOrgAccount.getPurchaseTotalAmount() + rechargeAmount);
				save(orgAccount);
			}
			//支出账户操作
			parentOrgAccount.setPurchaseBlance(parentOrgAccount.getPurchaseBlance() - rechargeAmount);
			parentOrgAccount.setRemarks(orgAccount.getRemarks());
			save(parentOrgAccount);
			
			//构建一条支出交易流水
			OrgAccountRecharge outOrgAccountRecharge = new OrgAccountRecharge();
			outOrgAccountRecharge.setAbstractType(AbstractType.AMOUNT_DIST.getType());
			outOrgAccountRecharge.setAccountId(Long.valueOf(parentOrgAccount.getId()));//转出账户
			outOrgAccountRecharge.setAccountType("purchase");
			outOrgAccountRecharge.setDealType(DealType.EXPENDITURE.getType());
			outOrgAccountRecharge.setAmount(-rechargeAmount);
			outOrgAccountRecharge.setAttachments(orgAccount.getAttachments());
			outOrgAccountRecharge.setOperTime(new Date());
			outOrgAccountRecharge.setTargetId(Long.valueOf(orgAccount.getId()));//转入账户
			outOrgAccountRecharge.setRemarks(orgAccount.getRemarks());
			//生成交易流水
			orgFundExchangeFlowService.orgAccountFlow(outOrgAccountRecharge);
			
		}

		return operRet;
	}

	/**
	 * 批量保存
	 *
	 * @param successList
	 * @return
	 */
	@Transactional(readOnly = false)
	public Result batchSave(List<OrgAccount> successList) {
		Result result = new Result();

		for (OrgAccount orgAccount : successList) {
			recharge(orgAccount);
		}

		result.setCode(ResultCode.SUCCESS);
		result.setMsg("操作成功");
		return result;
	}

	/**
	 * 查询机构剩余额度
	 * @param organizationId
	 * @return
	 */
	public Double getPurchaseBlance(String organizationId) {
		Double blance = 0D;
		OrgAccount orgAccount = new OrgAccount();
		orgAccount.setOrgId(Long.valueOf(organizationId));
		orgAccount = dao.getEntity(orgAccount);
		if(orgAccount == null){
			return blance;
		}
		return blance;
	}

}