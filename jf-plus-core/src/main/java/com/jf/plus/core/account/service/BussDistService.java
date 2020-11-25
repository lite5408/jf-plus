package com.jf.plus.core.account.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.base.Joiner;
import com.jf.plus.common.core.Constants;
import com.jf.plus.common.core.Result;
import com.jf.plus.common.core.ResultCode;
import com.jf.plus.common.core.enums.AbstractType;
import com.jf.plus.common.core.enums.AccountType;
import com.jf.plus.common.core.enums.BoolStatus;
import com.jf.plus.common.core.enums.DealType;
import com.jf.plus.common.core.enums.DistSource;
import com.jf.plus.common.core.enums.Status;
import com.jf.plus.common.core.enums.UserType;
import com.jf.plus.common.utils.CardGen;
import com.jf.plus.common.utils.CodeGen;
import com.jf.plus.common.utils.DateUtils;
import com.jf.plus.common.vo.DistVo;
import com.jf.plus.core.account.dao.VoucherAccCashDao;
import com.jf.plus.core.account.dao.VoucherDao;
import com.jf.plus.core.account.entity.Voucher;
import com.jf.plus.core.account.entity.VoucherAccCash;
import com.jf.plus.core.account.entity.VoucherFlow;
import com.jf.plus.core.fund.service.VoucherFundExchangeFlowService;
import com.jf.plus.core.mallSetting.dao.PacksAccCashDao;
import com.jf.plus.core.mallSetting.entity.PacksAccCash;
import com.jf.plus.core.mallSetting.entity.PacksInfo;

import cn.iutils.common.Page;
import cn.iutils.sys.dao.IUserDao;
import cn.iutils.sys.entity.User;
import cn.iutils.sys.entity.enums.UserSource;
import cn.iutils.sys.service.PasswordHelper;

@Service
@Transactional(readOnly = true)
public class BussDistService {

	@Autowired
	private IUserDao iUserDao;

	@Autowired
	private VoucherDao voucherDao;

	@Autowired
	private VoucherAccCashDao voucherAccCashDao;

	@Autowired
	private PacksAccCashDao packsAccCashDao;

	@Autowired
	private PasswordHelper passwordHelper;

	@Autowired
	private CodeService codeService;

	@Autowired
	private VoucherFundExchangeFlowService voucherFundExchangeFlowService;

	/**
	 * 分发电子券
	 *
	 * @param distVo
	 * @param voucher
	 * @return
	 */
	@Transactional(readOnly = false)
	public Result distVoucher(DistVo distVo, Voucher voucher) {
		Result result = Result.newInstance();
		List<VoucherAccCash> voucherAccCashList = getDistVoucherList(distVo.getDistUserId());
		// 验证余额是否足够
		double availableBlance = 0D;
		for (VoucherAccCash voucherAccCash : voucherAccCashList) {
			availableBlance += voucherAccCash.getBlance();
		}
		if (distVo.getBlance() > availableBlance) {
			result.setCode(ResultCode.AMOUNT_LESS);
			result.setMsg("分发失败：您的分发额度不足");
			return result;
		}

		User member = new User();
		member.setMobile(distVo.getMobile());
		// member.setSource(UserSource.MEM.getType());
		member = iUserDao.getEntity(member);

		if (null == member)
			member = createMember(distVo);
		else
			updateOrgIds(member.getId(), distVo.getDistOrgId(), member.getOrganizationIds()); // 更新机构
		distVo.setMemberId(Long.valueOf(member.getId()));
		VoucherAccCash voucherAccCash = createVoucherAccCash(voucher, distVo); // 生成电子券
		VoucherFlow voucherFlow = new VoucherFlow();
		voucherFlow.setAccId(Long.valueOf(voucherAccCash.getId()));
		voucherFlow.setUserId(voucherAccCash.getBindUser());
		voucherFlow.setTargetId(0L);
		voucherFlow.setAccountType(AccountType.JF.getType());
		voucherFlow.setDealType(DealType.INCOME.getType());
		voucherFlow.setAbstractType(AbstractType.AMOUNT_DIST.getType());
		voucherFlow.setTradeAmount(voucherAccCash.getTotalBlance());
		voucherFlow.setTradeDate(new Date());
		voucherFlow.setRatio(voucher.getRatio());
		voucherFlow.setCreateBy(distVo.getDistUserId().toString());
		voucherFlow.setCreateDate(new Date());
		voucherFundExchangeFlowService.createUserVoucherFlow(voucherFlow);
		deduction(voucherAccCashList, voucherAccCash.getTotalBlance(), voucherAccCash.getId()); // 分发
		result.setCode(ResultCode.SUCCESS);
		return result;
	}

	/**
	 * 业务经理负充值
	 *
	 * @param distVo
	 * @param voucher
	 * @return
	 */
	@Transactional(readOnly = false)
	public Result negativeRecharge(DistVo distVo, Voucher voucher) {
		Result result = Result.newInstance();

		User member = new User();
		member.setMobile(distVo.getMobile());
		member = iUserDao.getEntity(member);

		double availableBlance = 0; // 可负充值额度
		List<VoucherAccCash> vlist = vaildNegativeVoucherList(Long.valueOf(member.getId()), distVo.getDistOrgId(),
				distVo.getDistUserId());
		for (VoucherAccCash voucherAccCash : vlist) {
			availableBlance += voucherAccCash.getBlance();
		}
		if (Math.abs(distVo.getBlance()) > availableBlance) { // 验证余额是否足够
			result.setCode(ResultCode.AMOUNT_LESS);
			result.setMsg("负充值失败：用户对应账户余额不足");
			return result;
		}
		distVo.setMemberId(Long.valueOf(member.getId()));
		VoucherAccCash voucherAccCash = createDistVoucherAccCash(voucher, distVo); // 生成分发券账户
		VoucherFlow voucherFlow = new VoucherFlow();
		voucherFlow.setAccId(Long.valueOf(voucherAccCash.getId()));
		voucherFlow.setUserId(voucherAccCash.getBindUser());
		voucherFlow.setTargetId(0L);
		voucherFlow.setAccountType(AccountType.FF.getType());
		voucherFlow.setDealType(DealType.INCOME.getType());
		voucherFlow.setAbstractType(AbstractType.AMOUNT_NEGATIVE.getType());
		voucherFlow.setTradeAmount(voucherAccCash.getTotalBlance());
		voucherFlow.setTradeDate(new Date());
		voucherFlow.setRatio(voucher.getRatio());
		voucherFlow.setCreateBy(distVo.getDistUserId().toString());
		voucherFlow.setCreateDate(new Date());
		voucherFundExchangeFlowService.createUserVoucherFlow(voucherFlow);
		negativeDeduction(vlist, voucherAccCash.getTotalBlance(), voucherAccCash.getId()); // 分发
		result.setCode(ResultCode.SUCCESS);
		return result;
	}

	/**
	 * 分发礼包
	 *
	 * @param distVo
	 * @param packsInfo
	 * @return
	 */
	@Transactional(readOnly = false)
	public Result distPacks(DistVo distVo, PacksInfo packsInfo) {
		Result result = Result.newInstance();
		List<VoucherAccCash> voucherAccCashList = getDistVoucherList(distVo.getDistUserId());
		// 验证余额是否足够
		double availableBlance = 0D;
		for (VoucherAccCash voucherAccCash : voucherAccCashList) {
			availableBlance += voucherAccCash.getBlance();
		}
		if (packsInfo.getSalePrice().doubleValue() > availableBlance) {
			result.setCode(ResultCode.AMOUNT_LESS);
			result.setMsg("分发失败：您的账户余额不足");
			return result;
		}

		User member = new User();
		member.setMobile(distVo.getMobile());
		// member.setSource(UserSource.MEM.getType());
		member = iUserDao.getEntity(member);

		if (null == member)
			member = createMember(distVo);
		else
			updateOrgIds(member.getId(), distVo.getDistOrgId(), member.getOrganizationIds()); // 更新机构
		distVo.setMemberId(Long.valueOf(member.getId()));
		PacksAccCash packsAccCash = createPacksAccCash(packsInfo, distVo); // 生成礼包券
		VoucherFlow voucherFlow = new VoucherFlow();
		voucherFlow.setAccId(Long.valueOf(packsAccCash.getId()));
		voucherFlow.setUserId(packsAccCash.getBindUser());
		voucherFlow.setTargetId(0L);
		voucherFlow.setAccountType(AccountType.LB.getType());
		voucherFlow.setDealType(DealType.INCOME.getType());
		voucherFlow.setAbstractType(AbstractType.AMOUNT_DIST.getType());
		voucherFlow.setTradeAmount(packsInfo.getSalePrice());
		voucherFlow.setTradeDate(new Date());
		voucherFlow.setRatio(packsInfo.getRatio());
		voucherFlow.setCreateBy(distVo.getDistUserId().toString());
		voucherFlow.setCreateDate(new Date());
		voucherFundExchangeFlowService.createUserPacksFlow(voucherFlow);
		deduction(voucherAccCashList, packsInfo.getSalePrice(), packsAccCash.getId()); // 分发
		result.setCode(ResultCode.SUCCESS);
		return result;
	}

	/**
	 * 获取用户可用分发券
	 *
	 * @param userId
	 * @return
	 */
	private List<VoucherAccCash> getDistVoucherList(Long userId) {
		Page<VoucherAccCash> page = new Page<>();
		page.getCondition().put("userId", userId);
		page.getCondition().put("accountType", AccountType.FF.getType());
		page.getCondition().put("operStatus", Status.NORMAL.getType());
		return voucherAccCashDao.findVoucherAccCashList(page);
	}

	/**
	 * 创建会员
	 *
	 * @param distVo
	 * @return
	 */
	public User createMember(DistVo distVo) {
		User member = new User();
		member.setOrganizationIds(distVo.getDistOrgId().toString());
		member.setUsername(distVo.getMobile());
		member.setName(distVo.getMobile());
		member.setPhone(distVo.getMobile());
		member.setMobile(distVo.getMobile());
		member.setPassword(distVo.getMobile().substring(5, 11));
		passwordHelper.encryptPassword(member);
		member.setLocked(false);
		member.setIsDept(false);
		member.setType(UserType.MEMBER.getType());
		member.setSource(UserSource.MEM.getType());
		member.setCreateBy("0");
		member.setCreateDate(new Date());
		member.setUpdateBy("0");
		member.setUpdateDate(new Date());
		member.setStatus(String.valueOf(Status.NORMAL.getType()));
		iUserDao.insert(member);

		member.setNo(CodeGen.getUserNo(member.getId()));
		iUserDao.update(member);

		return member;
	}

	/**
	 * 更新用户机构组
	 *
	 * @param userId
	 * @param distOrgId
	 * @param organizationIds
	 */
	private void updateOrgIds(String userId, Long distOrgId, String organizationIds) {
		String[] ids = organizationIds.split(",");
		List<String> orgIds = new ArrayList<>();
		boolean flag = false;
		for (String orgId : ids) {
			if (orgId.equals(distOrgId.toString())) {
				flag = true;
				break;
			}
			orgIds.add(orgId);
		}
		if (!flag) {
			orgIds.add(distOrgId.toString());
			User userUpdate = iUserDao.get(userId);
			userUpdate.setOrganizationIds(Joiner.on(",").join(orgIds));
			iUserDao.update(userUpdate);
		}
	}

	/**
	 * 生成电子券账户
	 *
	 * @param voucher
	 * @param distVo
	 * @return
	 */
	private VoucherAccCash createVoucherAccCash(Voucher voucher, DistVo distVo) {
		VoucherAccCash voucherAccCash = new VoucherAccCash();
		voucherAccCash.setVoucherId(Long.valueOf(voucher.getId()));
		voucherAccCash.setBatchNo(CardGen.getBatchNo()); // 批次号
		voucherAccCash.setAccountType(AccountType.JF.getType());
		voucherAccCash.setDistSource(DistSource.MANAGER_DIST.getType());
		voucherAccCash.setDistOrgId(distVo.getDistOrgId());
		voucherAccCash.setDistUserId(distVo.getDistUserId());
		voucherAccCash.setDelayCount(0);
		int account = codeService.genAccount(Constants.VOUCHER_SEQ);
		voucherAccCash.setCouponAccount("ZDFF" + CardGen.formatAccount(account)); // 卡号
		voucherAccCash.setCouponCode(CardGen.getCardAccPwd()); // 卡密
		voucherAccCash.setBindUser(distVo.getMemberId());
		voucherAccCash.setBindDate(new Date());
		voucherAccCash.setBlance(distVo.getBlance());
		voucherAccCash.setTotalBlance(distVo.getBlance());
		voucherAccCash.setValidStartDate(voucher.getValidStartDate());
		voucherAccCash.setValidEndDate(voucher.getValidEndDate());
		voucherAccCash.setOperStatus(Status.NORMAL.getType());
		voucherAccCash.setCreateBy(distVo.getDistUserId().toString());
		voucherAccCash.setCreateDate(new Date());
		voucherAccCash.setUpdateBy(distVo.getDistUserId().toString());
		voucherAccCash.setUpdateDate(new Date());
		voucherAccCash.setStatus(String.valueOf(Status.NORMAL.getType()));
		voucherAccCash.setRemarks(distVo.getRemarks());
		voucherAccCashDao.insert(voucherAccCash);
		return voucherAccCash;
	}

	/**
	 * 生成分发券账户
	 *
	 * @param voucher
	 * @param distVo
	 * @return
	 */
	private VoucherAccCash createDistVoucherAccCash(Voucher voucher, DistVo distVo) {
		VoucherAccCash voucherAccCash = new VoucherAccCash();
		voucherAccCash.setVoucherId(Long.valueOf(voucher.getId()));
		voucherAccCash.setBatchNo(CardGen.getBatchNo()); // 批次号
		voucherAccCash.setAccountType(AccountType.FF.getType());
		voucherAccCash.setDistSource(DistSource.MANAGER_DIST.getType());
		voucherAccCash.setDistOrgId(distVo.getDistOrgId());
		voucherAccCash.setDistUserId(distVo.getDistUserId());
		voucherAccCash.setDelayCount(0);
		int account = codeService.genAccount(Constants.VOUCHER_SEQ);
		voucherAccCash.setCouponAccount("ZDFF" + CardGen.formatAccount(account)); // 卡号
		voucherAccCash.setCouponCode(CardGen.getCardAccPwd()); // 卡密
		voucherAccCash.setBindUser(distVo.getDistUserId());
		voucherAccCash.setBindDate(new Date());
		voucherAccCash.setBlance(Math.abs(distVo.getBlance()));
		voucherAccCash.setTotalBlance(Math.abs(distVo.getBlance()));
		voucherAccCash.setValidStartDate(voucher.getValidStartDate());
		voucherAccCash.setValidEndDate(DateUtils.parseDate("2099-12-30 23:23:59"));
		voucherAccCash.setOperStatus(Status.NORMAL.getType());
		voucherAccCash.setCreateBy(distVo.getDistUserId().toString());
		voucherAccCash.setCreateDate(new Date());
		voucherAccCash.setUpdateBy(distVo.getDistUserId().toString());
		voucherAccCash.setUpdateDate(new Date());
		voucherAccCash.setStatus(String.valueOf(Status.NORMAL.getType()));
		voucherAccCash.setRemarks(distVo.getRemarks());
		voucherAccCashDao.insert(voucherAccCash);
		return voucherAccCash;
	}

	/**
	 * 使用电子券
	 *
	 * @param voucherAccCash
	 * @param blance
	 */
	private void updateVoucherAccCash(VoucherAccCash voucherAccCash, double blance) {
		voucherAccCash.setUpdateDate(new Date());
		voucherAccCash.setBlance(blance);
		voucherAccCashDao.update(voucherAccCash);
	}

	/**
	 * 业务经理分发
	 *
	 * @param voucherAccCashList
	 * @param toPayBlance
	 * @param accId
	 */
	private void deduction(List<VoucherAccCash> voucherAccCashList, double toPayBlance, String accId) {
		Double blance = 0D; // 券余额
		Voucher voucher = null;
		VoucherFlow voucherFlow = null;
		for (VoucherAccCash voucherAccCash : voucherAccCashList) {
			voucher = voucherDao.get(voucherAccCash.getVoucherId().toString());
			blance = voucherAccCash.getBlance();
			toPayBlance = toPayBlance - blance;
			if (toPayBlance > 0) {
				updateVoucherAccCash(voucherAccCash, 0D);
				voucherFlow = new VoucherFlow();
				voucherFlow.setAccId(Long.valueOf(voucherAccCash.getId()));
				voucherFlow.setUserId(voucherAccCash.getBindUser());
				voucherFlow.setTargetId(Long.valueOf(accId));
				voucherFlow.setAccountType(AccountType.FF.getType());
				voucherFlow.setDealType(DealType.EXPENDITURE.getType());
				voucherFlow.setAbstractType(AbstractType.AMOUNT_DIST.getType());
				voucherFlow.setTradeAmount(-blance);
				voucherFlow.setTradeDate(new Date());
				voucherFlow.setRatio(voucher.getRatio());
				voucherFlow.setCreateBy(voucherAccCash.getDistUserId().toString());
				voucherFlow.setCreateDate(new Date());
				voucherFundExchangeFlowService.voucherBussDistFlow(voucherFlow);// 记业务经理分发流水
			} else {
				updateVoucherAccCash(voucherAccCash, Math.abs(toPayBlance));
				voucherFlow = new VoucherFlow();
				voucherFlow.setAccId(Long.valueOf(voucherAccCash.getId()));
				voucherFlow.setUserId(voucherAccCash.getBindUser());
				voucherFlow.setTargetId(Long.valueOf(accId));
				voucherFlow.setAccountType(AccountType.FF.getType());
				voucherFlow.setDealType(DealType.EXPENDITURE.getType());
				voucherFlow.setAbstractType(AbstractType.AMOUNT_DIST.getType());
				voucherFlow.setTradeAmount(-(blance + toPayBlance));
				voucherFlow.setTradeDate(new Date());
				voucherFlow.setRatio(voucher.getRatio());
				voucherFlow.setCreateBy(voucherAccCash.getDistUserId().toString());
				voucherFlow.setCreateDate(new Date());
				voucherFundExchangeFlowService.voucherBussDistFlow(voucherFlow);// 记业务经理分发流水
				break; // <=0,跳出循环
			}
		}
	}

	/**
	 * 业务经理负充值
	 *
	 * @param voucherAccCashList
	 * @param toPayBlance
	 * @param accId
	 */
	private void negativeDeduction(List<VoucherAccCash> voucherAccCashList, double toPayBlance, String accId) {
		Double blance = 0D; // 券余额
		Voucher voucher = null;
		VoucherFlow voucherFlow = null;
		for (VoucherAccCash voucherAccCash : voucherAccCashList) {
			voucher = voucherDao.get(voucherAccCash.getVoucherId().toString());
			blance = voucherAccCash.getBlance();
			toPayBlance = toPayBlance - blance;
			if (toPayBlance > 0) {
				updateVoucherAccCash(voucherAccCash, 0D);
				voucherFlow = new VoucherFlow();
				voucherFlow.setAccId(Long.valueOf(voucherAccCash.getId()));
				voucherFlow.setUserId(voucherAccCash.getBindUser());
				voucherFlow.setTargetId(Long.valueOf(accId));
				voucherFlow.setAccountType(AccountType.JF.getType());
				voucherFlow.setDealType(DealType.EXPENDITURE.getType());
				voucherFlow.setAbstractType(AbstractType.AMOUNT_NEGATIVE.getType());
				voucherFlow.setTradeAmount(-blance);
				voucherFlow.setTradeDate(new Date());
				voucherFlow.setRatio(voucher.getRatio());
				voucherFlow.setCreateBy(voucherAccCash.getDistUserId().toString());
				voucherFlow.setCreateDate(new Date());
				voucherFundExchangeFlowService.voucherBussDistFlow(voucherFlow);// 记业务经理分发流水
			} else {
				updateVoucherAccCash(voucherAccCash, Math.abs(toPayBlance));
				voucherFlow = new VoucherFlow();
				voucherFlow.setAccId(Long.valueOf(voucherAccCash.getId()));
				voucherFlow.setUserId(voucherAccCash.getBindUser());
				voucherFlow.setTargetId(Long.valueOf(accId));
				voucherFlow.setAccountType(AccountType.JF.getType());
				voucherFlow.setDealType(DealType.EXPENDITURE.getType());
				voucherFlow.setAbstractType(AbstractType.AMOUNT_NEGATIVE.getType());
				voucherFlow.setTradeAmount(-(blance + toPayBlance));
				voucherFlow.setTradeDate(new Date());
				voucherFlow.setRatio(voucher.getRatio());
				voucherFlow.setCreateBy(voucherAccCash.getDistUserId().toString());
				voucherFlow.setCreateDate(new Date());
				voucherFundExchangeFlowService.voucherBussDistFlow(voucherFlow);// 记业务经理分发流水
				break; // <=0,跳出循环
			}
		}
	}

	/**
	 * 生成礼包账户
	 *
	 * @param packsInfo
	 * @param distVo
	 * @return
	 */
	private PacksAccCash createPacksAccCash(PacksInfo packsInfo, DistVo distVo) {
		PacksAccCash packsAccCash = new PacksAccCash();
		packsAccCash.setPacksId(Long.valueOf(packsInfo.getId()));
		packsAccCash.setBatchNo(CardGen.getBatchNo()); // 批次号
		packsAccCash.setDistOrgId(distVo.getDistOrgId());
		packsAccCash.setDistUserId(distVo.getDistUserId());
		packsAccCash.setDelayCount(0);
		int account = codeService.genAccount(Constants.PACKS_SEQ);
		packsAccCash.setCouponAccount("ZDFF" + CardGen.formatAccount(account)); // 卡号
		packsAccCash.setCouponCode(CardGen.getCardAccPwd()); // 卡密
		packsAccCash.setBindUser(distVo.getMemberId());
		packsAccCash.setBindDate(new Date());
		packsAccCash.setValidStartDate(packsInfo.getValidStartDate());
		packsAccCash.setValidEndDate(packsInfo.getValidEndDate());
		packsAccCash.setOperStatus(Status.NORMAL.getType());
		packsAccCash.setCreateBy(distVo.getDistUserId().toString());
		packsAccCash.setCreateDate(new Date());
		packsAccCash.setUpdateBy(distVo.getDistUserId().toString());
		packsAccCash.setUpdateDate(new Date());
		packsAccCash.setStatus(String.valueOf(Status.NORMAL.getType()));
		packsAccCash.setRemarks(distVo.getRemarks());
		packsAccCashDao.insert(packsAccCash);
		return packsAccCash;
	}

	/**
	 * 获取用可负充值券
	 *
	 * @param userId
	 * @param distOrgId
	 * @param distUserId
	 * @return
	 */
	private List<VoucherAccCash> vaildNegativeVoucherList(Long userId, Long distOrgId, Long distUserId) {
		VoucherAccCash voucherAccCash = new VoucherAccCash();
		voucherAccCash.setBindUser(userId);
		voucherAccCash.setDistOrgId(distOrgId);
		voucherAccCash.setDistUserId(distUserId);
		voucherAccCash.setDistSource(DistSource.MANAGER_DIST.getType());
		voucherAccCash.setAccountType(AccountType.JF.getType());
		voucherAccCash.setOperStatus(Status.NORMAL.getType());
		voucherAccCash.setIsValidity(BoolStatus.YES.getType());
		voucherAccCash.setHasBlance(BoolStatus.YES.getType());
		return voucherAccCashDao.findList(voucherAccCash);
	}

}
