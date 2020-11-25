package com.jf.plus.core.account.service;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jf.plus.common.core.Constants;
import com.jf.plus.common.core.enums.AbstractType;
import com.jf.plus.common.core.enums.AccountType;
import com.jf.plus.common.core.enums.BoolStatus;
import com.jf.plus.common.core.enums.DealType;
import com.jf.plus.common.core.enums.DistSource;
import com.jf.plus.common.core.enums.Status;
import com.jf.plus.common.core.enums.UserType;
import com.jf.plus.common.core.enums.VoucherSource;
import com.jf.plus.common.utils.CardGen;
import com.jf.plus.common.utils.CodeGen;
import com.jf.plus.common.utils.DateUtils;
import com.jf.plus.core.account.dao.VoucherAccCashDao;
import com.jf.plus.core.account.entity.Blance;
import com.jf.plus.core.account.entity.Voucher;
import com.jf.plus.core.account.entity.VoucherAccCash;
import com.jf.plus.core.account.entity.VoucherFlow;
import com.jf.plus.core.fund.service.OrgFundExchangeFlowService;
import com.jf.plus.core.mall.service.MallSiteService;

import cn.iutils.common.DataScope;
import cn.iutils.common.Page;
import cn.iutils.common.service.CrudService;
import cn.iutils.common.utils.UserUtils;
import cn.iutils.sys.entity.User;
import cn.iutils.sys.entity.enums.UserSource;
import cn.iutils.sys.service.OrganizationService;
import cn.iutils.sys.service.PasswordHelper;
import cn.iutils.sys.service.UserService;

/**
 * 电子券卡号信息表 Service层
 *
 * @author Tng
 * @version 1.0
 */
@Service
@Transactional(readOnly = true)
public class VoucherAccCashService extends CrudService<VoucherAccCashDao, VoucherAccCash> {

	private final static String VOUCHER_END_DATE = "2099-12-30 23:23:59";

	private static Logger LOGGER = LoggerFactory.getLogger(VoucherAccCashService.class);

	@Autowired
	private VoucherService voucherService;

	@Autowired
	private MallSiteService mallSiteService;

	@Autowired
	private CodeService codeService;

	@Autowired
	private OrganizationService organizationService;

	@Autowired
	private VoucherFlowService voucherFlowService;

	@Autowired
	private UserService userService;

	@Autowired
	private BlanceService blanceService;
	
	@Autowired
	private PasswordHelper passwordHelper;
	
	@Autowired
	private OrgFundExchangeFlowService orgFundExchangeFlowService;

	public VoucherAccCash getEntityByBind(VoucherAccCash entity) {
		return dao.getEntityByBind(entity);
	}

	public Double findMyBlance(VoucherAccCash entity) {
		return dao.findBlance(entity);
	}

	public List<VoucherAccCash> findVoucherAccCashList(Page<VoucherAccCash> page) {
		return dao.findVoucherAccCashList(page);
	}

	public List<VoucherAccCash> findVoucherAccCashListByJc(Page<VoucherAccCash> page) {
		return dao.findVoucherAccCashListByJc(page);
	}

	public List<VoucherAccCash> findVoucherList(Page<VoucherAccCash> page) {
		page.setTotal(dao.findVoucherCount(page));
		return dao.findVoucherList(page);
	}

	public Double findMyDistributeBlance(VoucherAccCash entity) {
		return dao.findDistributeBlance(entity);
	}

	/**
	 * 充值
	 *
	 * @param voucherAccCash
	 * @return
	 */
	@Transactional(readOnly = false)
	public int recharge(VoucherAccCash voucherAccCash) {
		VoucherAccCash vac = new VoucherAccCash();
		vac.setDistOrgId(voucherAccCash.getDistOrgId());
		vac.setAccountType(AccountType.JC.getType());
		vac = getEntity(vac);
		Voucher voucherJC = new Voucher();
		if (vac == null) {
			voucherJC.setOrgId(voucherAccCash.getDistOrgId());
			voucherJC.setName("采购自定义面值券");
			voucherJC.setRatio(1);
			voucherJC.setValidStartDate(new Date());
			voucherJC.setValidEndDate(DateUtils.parseDate(VOUCHER_END_DATE));
			voucherJC.setSource(VoucherSource.CRASH.getType());
			voucherJC.setIsMobile(0);
			voucherJC.setPrice(-1D);//自定义面值
			voucherJC.setOperStatus(Status.NORMAL.getType());
			voucherJC.setStatus(Status.NORMAL.getType() + "");
			voucherJC.preInsert();
			voucherService.save(voucherJC);
		}

		voucherAccCash.setVoucherId(vac == null ? Long.valueOf(voucherJC.getId()) : vac.getVoucherId());
		voucherAccCash.setBatchNo(CodeGen.getBatchNo());
		voucherAccCash.setAccountType(AccountType.JC.getType());
		voucherAccCash.setDistUserId(Long.valueOf(UserUtils.getLoginUser().getId()));
		voucherAccCash.setDistSource(DistSource.ORGANIZATION_DIST.getType());
		voucherAccCash.setDelayCount(0);
		int couponAccount = codeService.genAccount(Constants.VOUCHER_SEQ);
		voucherAccCash.setCouponAccount(
				Constants.ZDFF_PREFIX.toUpperCase() + CardGen.formatAccount(couponAccount) + CardGen.getCardPwd());
		voucherAccCash.setCouponCode(CardGen.getCardAccPwd());
		voucherAccCash.setBindDate(new Date());
		voucherAccCash.setBlance(voucherAccCash.getTotalBlance());
		voucherAccCash.setValidStartDate(vac == null ? voucherJC.getValidStartDate() : vac.getValidStartDate());
		voucherAccCash.setValidEndDate(vac == null ? voucherJC.getValidEndDate() : vac.getValidEndDate());
		voucherAccCash.setOperStatus(Status.NORMAL.getType());
		voucherAccCash.setStatus(Status.NORMAL.getType() + "");
		voucherAccCash.preInsert();
		save(voucherAccCash);

		// 记录采购充值券流水
		VoucherFlow voucherFlow = new VoucherFlow();
		voucherFlow.setAccId(Long.valueOf(voucherAccCash.getId()));
		voucherFlow.setUserId(voucherAccCash.getBindUser());
		voucherFlow.setTargetId(0L);
		voucherFlow.setAccountType(AccountType.JC.getType());
		voucherFlow.setDealType(DealType.INCOME.getType());
		voucherFlow.setAbstractType(AbstractType.AMOUNT_DIST.getType());
		voucherFlow.setTradeAmount(voucherAccCash.getTotalBlance());
		voucherFlow.setTradeDate(new Date());
		voucherFlow.setRatio(1);
		voucherFlow.setRemarks(voucherAccCash.getRemarks());
		voucherFlow.setStatus(Status.NORMAL.getType() + "");
		orgFundExchangeFlowService.orgUserVoucherFlow(voucherFlow);

		return 1;
	}

	/**
	 * 集采额度转分发额度
	 *
	 * @param voucherAccCash
	 * @return
	 */
	@Transactional(readOnly = false)
	public int changeIntoFF(VoucherAccCash voucherAccCash) {
		Integer marketRatio = organizationService.get(voucherAccCash.getDistOrgId() + "").getMarketRatio();
		if(marketRatio == null){
			//没有配置机构的积分比例
			return -1;
		}
		// 获取修改的额度
		Double changeAmount = voucherAccCash.getTotalBlance();

		// 集采额度减
		VoucherAccCash jcVoucherAccCash = new VoucherAccCash();
		jcVoucherAccCash.setAccountType(AccountType.JC.getType());
		jcVoucherAccCash.setBindUser(voucherAccCash.getBindUser());
		jcVoucherAccCash.setDistOrgId(voucherAccCash.getDistOrgId());
		List<VoucherAccCash> jcVoucherAccCashList = findList(jcVoucherAccCash);
		Double countJCVoucherAccCash = 0D;// 用作记录扣除的券余额
		for (int i = 0; i < jcVoucherAccCashList.size(); i++) {
			countJCVoucherAccCash += jcVoucherAccCashList.get(i).getBlance().doubleValue();
			if (countJCVoucherAccCash > changeAmount) {
				Double blance = jcVoucherAccCashList.get(i).getBlance().doubleValue();
				jcVoucherAccCashList.get(i).setBlance(countJCVoucherAccCash - changeAmount);
				// 记流水
				VoucherFlow voucherFlow = new VoucherFlow();
				voucherFlow.setAccId(Long.valueOf(jcVoucherAccCashList.get(i).getId()));
				voucherFlow.setUserId(jcVoucherAccCashList.get(i).getBindUser());
				voucherFlow.setTargetId(Long.valueOf(jcVoucherAccCashList.get(i).getId()));
				voucherFlow.setAccountType(AccountType.JC.getType());
				voucherFlow.setDealType(DealType.EXPENDITURE.getType());
				voucherFlow.setAbstractType(AbstractType.AMOUNT_CONVERT.getType());
				voucherFlow.setTradeAmount(jcVoucherAccCashList.get(i).getBlance().doubleValue() - blance);
				voucherFlow.setTradeDate(new Date());
				voucherFlow.setRatio(1);
				voucherFlow.setCreateBy(UserUtils.getLoginUser().getId());
				voucherFlow.setCreateDate(new Date());
				voucherFlow.setUpdateBy(UserUtils.getLoginUser().getId());
				voucherFlow.setUpdateDate(new Date());
				voucherFlow.setRemarks("集采转分发--集采额度减少");
				voucherFlow.setStatus(Status.NORMAL.getType() + "");
				voucherFlowService.save(voucherFlow);

				save(jcVoucherAccCashList.get(i));
				break;
			} else {
				if (jcVoucherAccCashList.get(i).getBlance() != null
						&& jcVoucherAccCashList.get(i).getBlance().doubleValue() != 0D) {
					// 记录流水
					VoucherFlow voucherFlow = new VoucherFlow();
					voucherFlow.setAccId(Long.valueOf(jcVoucherAccCashList.get(i).getId()));
					voucherFlow.setUserId(jcVoucherAccCashList.get(i).getBindUser());
					voucherFlow.setTargetId(Long.valueOf(jcVoucherAccCashList.get(i).getId()));
					voucherFlow.setAccountType(AccountType.JC.getType());
					voucherFlow.setDealType(DealType.EXPENDITURE.getType());
					voucherFlow.setAbstractType(AbstractType.AMOUNT_CONVERT.getType());
					voucherFlow.setTradeAmount(0D - jcVoucherAccCashList.get(i).getBlance().doubleValue());
					voucherFlow.setTradeDate(new Date());
					voucherFlow.setRatio(1);
					voucherFlow.setCreateBy(UserUtils.getLoginUser().getId());
					voucherFlow.setCreateDate(new Date());
					voucherFlow.setUpdateBy(UserUtils.getLoginUser().getId());
					voucherFlow.setUpdateDate(new Date());
					voucherFlow.setRemarks("集采转分发--集采额度减少");
					voucherFlow.setStatus(Status.NORMAL.getType() + "");
					voucherFlowService.save(voucherFlow);

					jcVoucherAccCashList.get(i).setBlance(0D);
					save(jcVoucherAccCashList.get(i));

				}
			}
		}

		// 分发额度加
		VoucherAccCash vac = new VoucherAccCash();
		vac.setDistOrgId(voucherAccCash.getDistOrgId());
		vac.setAccountType(AccountType.FF.getType());
		vac = getEntity(vac);
		Voucher voucherFF = new Voucher();
		if (vac == null) {
			voucherFF.setOrgId(voucherAccCash.getDistOrgId());
			voucherFF.setName("分发自定义面值券");
			voucherFF.setRatio(marketRatio);
			voucherFF.setValidStartDate(new Date());
			voucherFF.setValidEndDate(DateUtils.parseDate(VOUCHER_END_DATE));
			voucherFF.setSource(VoucherSource.CRASH.getType());
			voucherFF.setIsMobile(0);
			voucherFF.setPrice(-1D);
			voucherFF.setOperStatus(Status.NORMAL.getType());
			voucherFF.setStatus(Status.NORMAL.getType() + "");
			voucherFF.setCreateBy(UserUtils.getLoginUser().getId());
			voucherFF.setCreateDate(new Date());
			voucherFF.setUpdateBy(UserUtils.getLoginUser().getId());
			voucherFF.setUpdateDate(new Date());
			voucherService.save(voucherFF);
		}

		voucherAccCash.setVoucherId(vac == null ? Long.valueOf(voucherFF.getId()) : vac.getVoucherId());
		voucherAccCash.setBatchNo(CodeGen.getBatchNo());
		voucherAccCash.setAccountType(AccountType.FF.getType());
		voucherAccCash.setDistSource(DistSource.ORGANIZATION_DIST.getType());
		voucherAccCash.setDistUserId(Long.valueOf(UserUtils.getLoginUser().getId()));
		voucherAccCash.setDelayCount(0);
		int couponAccount = codeService.genAccount(Constants.VOUCHER_SEQ);
		voucherAccCash.setCouponAccount(
				Constants.ZDFF_PREFIX.toUpperCase() + CardGen.formatAccount(couponAccount) + CardGen.getCardPwd());
		voucherAccCash.setCouponCode(CardGen.getCardAccPwd());
		voucherAccCash.setBindDate(new Date());
		voucherAccCash.setBlance(voucherAccCash.getTotalBlance());
		voucherAccCash.setValidStartDate(vac == null ? voucherFF.getValidStartDate() : vac.getValidStartDate());
		voucherAccCash.setValidEndDate(vac == null ? voucherFF.getValidEndDate() : vac.getValidEndDate());
		voucherAccCash.setOperStatus(Status.NORMAL.getType());
		voucherAccCash.setCreateBy(UserUtils.getLoginUser().getId());
		voucherAccCash.setCreateDate(voucherAccCash.getBindDate());
		voucherAccCash.setUpdateBy(UserUtils.getLoginUser().getId());
		voucherAccCash.setUpdateDate(voucherAccCash.getBindDate());
		voucherAccCash.setRemarks("集采转分发--分发额度增加券");
		voucherAccCash.setStatus(Status.NORMAL.getType() + "");
		save(voucherAccCash);

		VoucherFlow voucherFlow = new VoucherFlow();
		voucherFlow.setAccId(Long.valueOf(voucherAccCash.getId()));
		voucherFlow.setUserId(voucherAccCash.getBindUser());
		voucherFlow.setTargetId(Long.valueOf(voucherAccCash.getId()));
		voucherFlow.setAccountType(AccountType.FF.getType());
		voucherFlow.setDealType(DealType.INCOME.getType());
		voucherFlow.setAbstractType(AbstractType.AMOUNT_CONVERT.getType());
		voucherFlow.setTradeAmount(voucherAccCash.getTotalBlance());
		voucherFlow.setTradeDate(new Date());
		voucherFlow.setRatio(marketRatio);
		voucherFlow.setCreateBy(UserUtils.getLoginUser().getId());
		voucherFlow.setCreateDate(new Date());
		voucherFlow.setUpdateBy(UserUtils.getLoginUser().getId());
		voucherFlow.setUpdateDate(new Date());
		voucherFlow.setRemarks("集采转分发--分发额度增加");
		voucherFlow.setStatus(Status.NORMAL.getType() + "");
		voucherFlowService.save(voucherFlow);

		return 1;
	}

	/**
	 * 分发额度转集采额度
	 *
	 * @param voucherAccCash
	 * @return
	 */
	@Transactional(readOnly = false)
	public int changeIntoJC(VoucherAccCash voucherAccCash) {
		Integer marketRatio = organizationService.get(voucherAccCash.getDistOrgId() + "").getMarketRatio();
		if(marketRatio == null){
			//没有配置机构的积分比例
			return -1;
		}
		// 获取修改的额度
		Double changeAmount = voucherAccCash.getTotalBlance();

		// 分发额度减
		VoucherAccCash ffVoucherAccCash = new VoucherAccCash();
		ffVoucherAccCash.setAccountType(AccountType.FF.getType());
		ffVoucherAccCash.setBindUser(voucherAccCash.getBindUser());
		ffVoucherAccCash.setDistOrgId(voucherAccCash.getDistOrgId());
		List<VoucherAccCash> ffVoucherAccCashList = findList(ffVoucherAccCash);
		Double countJCVoucherAccCash = 0D;// 用作记录扣除的券余额
		for (int i = 0; i < ffVoucherAccCashList.size(); i++) {
			countJCVoucherAccCash += ffVoucherAccCashList.get(i).getBlance().doubleValue();
			if (countJCVoucherAccCash > changeAmount) {
				Double blance = ffVoucherAccCashList.get(i).getBlance().doubleValue();
				ffVoucherAccCashList.get(i).setBlance(countJCVoucherAccCash - changeAmount);
				// 记流水
				VoucherFlow voucherFlow = new VoucherFlow();
				voucherFlow.setAccId(Long.valueOf(ffVoucherAccCashList.get(i).getId()));
				voucherFlow.setUserId(ffVoucherAccCashList.get(i).getBindUser());
				voucherFlow.setTargetId(Long.valueOf(ffVoucherAccCashList.get(i).getId()));
				voucherFlow.setAccountType(AccountType.FF.getType());
				voucherFlow.setDealType(DealType.EXPENDITURE.getType());
				voucherFlow.setAbstractType(AbstractType.AMOUNT_CONVERT.getType());
				voucherFlow.setTradeAmount(ffVoucherAccCashList.get(i).getBlance().doubleValue() - blance);
				voucherFlow.setTradeDate(new Date());
				voucherFlow.setRatio(marketRatio);
				voucherFlow.setCreateBy(UserUtils.getLoginUser().getId());
				voucherFlow.setCreateDate(new Date());
				voucherFlow.setUpdateBy(UserUtils.getLoginUser().getId());
				voucherFlow.setUpdateDate(new Date());
				voucherFlow.setRemarks("分发转集采--分发额度减少");
				voucherFlow.setStatus(Status.NORMAL.getType() + "");
				voucherFlowService.save(voucherFlow);

				save(ffVoucherAccCashList.get(i));
				break;
			} else {
				if (ffVoucherAccCashList.get(i).getBlance() != null
						&& ffVoucherAccCashList.get(i).getBlance().doubleValue() != 0D) {
					// 记录流水
					VoucherFlow voucherFlow = new VoucherFlow();
					voucherFlow.setAccId(Long.valueOf(ffVoucherAccCashList.get(i).getId()));
					voucherFlow.setUserId(ffVoucherAccCashList.get(i).getBindUser());
					voucherFlow.setTargetId(Long.valueOf(ffVoucherAccCashList.get(i).getId()));
					voucherFlow.setAccountType(AccountType.FF.getType());
					voucherFlow.setDealType(DealType.EXPENDITURE.getType());
					voucherFlow.setAbstractType(AbstractType.AMOUNT_CONVERT.getType());
					voucherFlow.setTradeAmount(0D - ffVoucherAccCashList.get(i).getBlance().doubleValue());
					voucherFlow.setTradeDate(new Date());
					voucherFlow.setRatio(marketRatio);
					voucherFlow.setCreateBy(UserUtils.getLoginUser().getId());
					voucherFlow.setCreateDate(new Date());
					voucherFlow.setUpdateBy(UserUtils.getLoginUser().getId());
					voucherFlow.setUpdateDate(new Date());
					voucherFlow.setRemarks("分发转集采--分发额度减少");
					voucherFlow.setStatus(Status.NORMAL.getType() + "");
					voucherFlowService.save(voucherFlow);

					ffVoucherAccCashList.get(i).setBlance(0D);
					save(ffVoucherAccCashList.get(i));

				}
			}
		}

		// 集采额度加
		VoucherAccCash vac = new VoucherAccCash();
		vac.setDistOrgId(voucherAccCash.getDistOrgId());
		vac.setAccountType(AccountType.JC.getType());
		vac = getEntity(vac);
		Voucher voucherJC = new Voucher();
		if (vac == null) {
			voucherJC.setOrgId(voucherAccCash.getDistOrgId());
			voucherJC.setName("采购自定义面值券");
			voucherJC.setRatio(1);
			voucherJC.setValidStartDate(new Date());
			voucherJC.setValidEndDate(DateUtils.parseDate(VOUCHER_END_DATE));
			voucherJC.setSource(VoucherSource.CRASH.getType());
			voucherJC.setIsMobile(0);
			voucherJC.setPrice(-1D);
			voucherJC.setOperStatus(Status.NORMAL.getType());
			voucherJC.setStatus(Status.NORMAL.getType() + "");
			voucherJC.setCreateBy(UserUtils.getLoginUser().getId());
			voucherJC.setCreateDate(new Date());
			voucherJC.setUpdateBy(UserUtils.getLoginUser().getId());
			voucherJC.setUpdateDate(new Date());
			voucherService.save(voucherJC);
		}

		voucherAccCash.setVoucherId(vac == null ? Long.valueOf(voucherJC.getId()) : vac.getVoucherId());
		voucherAccCash.setBatchNo(CodeGen.getBatchNo());
		voucherAccCash.setAccountType(AccountType.JC.getType());
		voucherAccCash.setDistSource(DistSource.ORGANIZATION_DIST.getType());
		voucherAccCash.setDistUserId(Long.valueOf(UserUtils.getLoginUser().getId()));
		voucherAccCash.setDelayCount(0);
		int couponAccount = codeService.genAccount(Constants.VOUCHER_SEQ);
		voucherAccCash.setCouponAccount(
				Constants.ZDFF_PREFIX.toUpperCase() + CardGen.formatAccount(couponAccount) + CardGen.getCardPwd());
		voucherAccCash.setCouponCode(CardGen.getCardAccPwd());
		voucherAccCash.setBindDate(new Date());
		voucherAccCash.setBlance(voucherAccCash.getTotalBlance());
		voucherAccCash.setValidStartDate(vac == null ? voucherJC.getValidStartDate() : vac.getValidStartDate());
		voucherAccCash.setValidEndDate(vac == null ? voucherJC.getValidEndDate() : vac.getValidEndDate());
		voucherAccCash.setOperStatus(Status.NORMAL.getType());
		voucherAccCash.setCreateBy(UserUtils.getLoginUser().getId());
		voucherAccCash.setCreateDate(voucherAccCash.getBindDate());
		voucherAccCash.setUpdateBy(UserUtils.getLoginUser().getId());
		voucherAccCash.setUpdateDate(voucherAccCash.getBindDate());
		voucherAccCash.setRemarks("分发转集采--集采额度增加券");
		voucherAccCash.setStatus(Status.NORMAL.getType() + "");
		save(voucherAccCash);

		VoucherFlow voucherFlow = new VoucherFlow();
		voucherFlow.setAccId(Long.valueOf(voucherAccCash.getId()));
		voucherFlow.setUserId(voucherAccCash.getBindUser());
		voucherFlow.setTargetId(Long.valueOf(voucherAccCash.getId()));
		voucherFlow.setAccountType(AccountType.JC.getType());
		voucherFlow.setDealType(DealType.INCOME.getType());
		voucherFlow.setAbstractType(AbstractType.AMOUNT_CONVERT.getType());
		voucherFlow.setTradeAmount(voucherAccCash.getTotalBlance());
		voucherFlow.setTradeDate(new Date());
		voucherFlow.setRatio(1);
		voucherFlow.setCreateBy(UserUtils.getLoginUser().getId());
		voucherFlow.setCreateDate(new Date());
		voucherFlow.setUpdateBy(UserUtils.getLoginUser().getId());
		voucherFlow.setUpdateDate(new Date());
		voucherFlow.setRemarks("分发转集采--集采额度增加");
		voucherFlow.setStatus(Status.NORMAL.getType() + "");
		voucherFlowService.save(voucherFlow);

		return 1;
	}

	/**
	 * 站点给指定客户分发积分(兼容负充值)
	 *
	 * @param voucherAccCash
	 * @return
	 */
	@Transactional(readOnly = false)
	public int distribute(Voucher voucherJF,String distAmount,String mobile,String remarks) {
		try {
			User user = userService.getEntityByPhone(mobile);
			if (user == null) {
				user = new User();
				user.setOrganizationIds(voucherJF.getOrgId() + "");
				user.setUsername(mobile);
				user.setPassword(mobile.substring(mobile.length()-6));
				user.setPhone(mobile);
				user.setMobile(mobile);
				user.setType(UserType.MEMBER.getType());
				passwordHelper.encryptPassword(user);
				user.setSource(UserSource.MEM.getType());
				user.setOpenId(mobile);
				userService.save(user);
				
				user.setNo(CodeGen.getUserNo(user.getId()));
				userService.save(user);
			}
			double amount = Double.valueOf(distAmount).doubleValue();
			if (amount < 0D) {
				Blance blance = new Blance();
				blance.setUserId(user.getId());
				blance.setOrgId(voucherJF.getOrgId());
				if (blanceService.getUserOrgJFBlance(blance).doubleValue() + amount < 0D) {
					return -1;
				}
				VoucherAccCash vac = new VoucherAccCash();
				vac.setBindUser(Long.valueOf(user.getId()));
				vac.setDistOrgId(voucherJF.getOrgId());
				List<VoucherAccCash> vacList = findList(vac);
				for (VoucherAccCash voucherAccCash : vacList) {
					if(voucherAccCash.getBlance().doubleValue() + amount >= 0D){
						VoucherFlow voucherFlow = new VoucherFlow();
						voucherFlow.setAccId(Long.valueOf(voucherAccCash.getId()));
						voucherFlow.setUserId(voucherAccCash.getBindUser());
						voucherFlow.setTargetId(Long.valueOf(voucherAccCash.getId()));
						voucherFlow.setAccountType(AccountType.JF.getType());
						voucherFlow.setDealType(DealType.EXPENDITURE.getType());
						voucherFlow.setAbstractType(AbstractType.AMOUNT_NEGATIVE.getType());
						voucherFlow.setTradeAmount(amount);
						voucherFlow.setTradeDate(new Date());
						voucherFlow.setRatio(voucherJF.getRatio());
						voucherFlow.setCreateBy(UserUtils.getLoginUser().getId());
						voucherFlow.setCreateDate(new Date());
						voucherFlow.setUpdateBy(UserUtils.getLoginUser().getId());
						voucherFlow.setUpdateDate(new Date());
						voucherFlow.setRemarks("站点管理员负充值");
						voucherFlow.setStatus(Status.NORMAL.getType() + "");
						voucherFlowService.save(voucherFlow);
						
						voucherAccCash.setBlance(voucherAccCash.getBlance().doubleValue() + amount);
						voucherAccCash.setUpdateBy(UserUtils.getLoginUser().getId());
						voucherAccCash.setUpdateDate(new Date());
						save(voucherAccCash);
						return 1;
					}else {
						VoucherFlow voucherFlow = new VoucherFlow();
						voucherFlow.setAccId(Long.valueOf(voucherAccCash.getId()));
						voucherFlow.setUserId(voucherAccCash.getBindUser());
						voucherFlow.setTargetId(Long.valueOf(voucherAccCash.getId()));
						voucherFlow.setAccountType(AccountType.JF.getType());
						voucherFlow.setDealType(DealType.EXPENDITURE.getType());
						voucherFlow.setAbstractType(AbstractType.AMOUNT_NEGATIVE.getType());
						voucherFlow.setTradeAmount(0 - voucherAccCash.getBlance().doubleValue());
						voucherFlow.setTradeDate(new Date());
						voucherFlow.setRatio(voucherJF.getRatio());
						voucherFlow.setCreateBy(UserUtils.getLoginUser().getId());
						voucherFlow.setCreateDate(new Date());
						voucherFlow.setUpdateBy(UserUtils.getLoginUser().getId());
						voucherFlow.setUpdateDate(new Date());
						voucherFlow.setRemarks("站点管理员负充值");
						voucherFlow.setStatus(Status.NORMAL.getType() + "");
						voucherFlowService.save(voucherFlow);
						
						amount += voucherAccCash.getBlance().doubleValue();
						voucherAccCash.setBlance(0D);
						voucherAccCash.setUpdateBy(UserUtils.getLoginUser().getId());
						voucherAccCash.setUpdateDate(new Date());
						save(voucherAccCash);
					}
				}
			}

			// C端客户积分额度增加
			VoucherAccCash vac = new VoucherAccCash();
			vac.setVoucherId(Long.valueOf(voucherJF.getId()));
			vac.setBatchNo(CodeGen.getBatchNo());
			vac.setAccountType(AccountType.JF.getType());
			vac.setDistOrgId(mallSiteService.get(UserUtils.getMySite().getSiteId()).getOrgId());
			vac.setDistUserId(Long.valueOf(UserUtils.getLoginUser().getId()));
			vac.setDistSource(DistSource.SITE_DIST.getType());
			vac.setDelayCount(0);
			int couponAccount = codeService.genAccount(Constants.VOUCHER_SEQ);
			vac.setCouponAccount(Constants.ZDFF_PREFIX.toUpperCase() + CardGen.formatAccount(couponAccount));
			vac.setCouponCode(CardGen.getCardAccPwd());
			vac.setBindUser(Long.valueOf(user.getId()));
			vac.setBindDate(new Date());
			vac.setBlance(Double.valueOf(distAmount));
			vac.setTotalBlance(Double.valueOf(distAmount));
			vac.setValidStartDate(voucherJF.getValidStartDate());
			vac.setValidEndDate(voucherJF.getValidEndDate());
			vac.setOperStatus(Status.NORMAL.getType());
			vac.setCreateBy(vac.getDistUserId() + "");
			vac.setCreateDate(new Date());
			vac.setUpdateBy(vac.getDistUserId() + "");
			vac.setUpdateDate(new Date());
			vac.setRemarks(remarks);
			vac.setStatus(Status.NORMAL.getType() + "");
			save(vac);

			VoucherFlow voucherFlow = new VoucherFlow();
			voucherFlow.setAccId(Long.valueOf(vac.getId()));
			voucherFlow.setUserId(vac.getBindUser());
			voucherFlow.setTargetId(Long.valueOf(vac.getId()));
			voucherFlow.setAccountType(AccountType.JF.getType());
			voucherFlow.setDealType(DealType.INCOME.getType());
			voucherFlow.setAbstractType(AbstractType.AMOUNT_DIST.getType());
			voucherFlow.setTradeAmount(vac.getTotalBlance());
			voucherFlow.setTradeDate(new Date());
			voucherFlow.setRatio(voucherJF.getRatio());
			voucherFlow.setCreateBy(UserUtils.getLoginUser().getId());
			voucherFlow.setCreateDate(new Date());
			voucherFlow.setUpdateBy(UserUtils.getLoginUser().getId());
			voucherFlow.setUpdateDate(new Date());
			voucherFlow.setRemarks("分发客户电子券-" + remarks);
			voucherFlow.setStatus(Status.NORMAL.getType() + "");
			voucherFlowService.save(voucherFlow);

			return 1;
		} catch (Exception e) {
			LOGGER.info("分发积分异常：{}", e);
			return 0;
		}
	}

	public List<VoucherAccCash> findPointPage(Page<VoucherAccCash> page) {
		DataScope dataScope;
		if (page != null && page.getEntity() != null && (dataScope = page.getEntity().getDataScopeFilter()) != null) {
			dataScope.setDataScopeSql(dataScopeFilter(dataScope.getUser(), dataScope.getDataScopeEnum(),
					dataScope.getOrgAlias(), dataScope.getUserAlias()));
		}

		page.setTotal(dao.countPointPage(page));
		return dao.findPointPage(page);
	}

	/**
	 * 集采额度负充值
	 * @param voucherAccCash
	 * @return
	 */
	@Transactional(readOnly = false)
	public int negativeRecharge(VoucherAccCash voucherAccCash) {
		VoucherAccCash vac = new VoucherAccCash();
		vac.setBindUser(voucherAccCash.getBindUser());
		vac.setDistOrgId(voucherAccCash.getDistOrgId());
		vac.setDistSource(DistSource.ORGANIZATION_DIST.getType());
		vac.setAccountType(AccountType.JC.getType());
		vac.setOperStatus(Status.NORMAL.getType());
		vac.setIsValidity(BoolStatus.YES.getType());
		vac.setHasBlance(BoolStatus.YES.getType());
		List<VoucherAccCash> negativeList = dao.findList(vac);
		
		Double blance = 0D,toPayBlance = voucherAccCash.getTotalBlance(); // 券余额
		Voucher voucher = null;
		VoucherFlow voucherFlow = null;
		for (VoucherAccCash acc : negativeList) {
			voucher = voucherService.get(acc.getVoucherId().toString());
			blance = acc.getBlance();
			toPayBlance = Math.abs(toPayBlance) - blance;
			if (toPayBlance > 0) {
				updateVoucherAccCash(acc, 0D);
				voucherFlow = new VoucherFlow();
				voucherFlow.setAccId(Long.valueOf(acc.getId()));
				voucherFlow.setUserId(acc.getBindUser());
				voucherFlow.setTargetId(0L);
				voucherFlow.setAccountType(AccountType.JC.getType());
				voucherFlow.setDealType(DealType.EXPENDITURE.getType());
				voucherFlow.setAbstractType(AbstractType.AMOUNT_NEGATIVE.getType());
				voucherFlow.setTradeAmount(-blance);
				voucherFlow.setTradeDate(new Date());
				voucherFlow.setRatio(voucher.getRatio());
				voucherFlow.setCreateBy(acc.getDistUserId().toString());
				voucherFlow.setCreateDate(new Date());
				orgFundExchangeFlowService.orgUserVoucherFlow(voucherFlow);// 记业务经理分发流水
			} else {
				updateVoucherAccCash(acc, Math.abs(toPayBlance));
				voucherFlow = new VoucherFlow();
				voucherFlow.setAccId(Long.valueOf(acc.getId()));
				voucherFlow.setUserId(acc.getBindUser());
				voucherFlow.setTargetId(0L);
				voucherFlow.setAccountType(AccountType.JC.getType());
				voucherFlow.setDealType(DealType.EXPENDITURE.getType());
				voucherFlow.setAbstractType(AbstractType.AMOUNT_NEGATIVE.getType());
				voucherFlow.setTradeAmount(-(blance + toPayBlance));
				voucherFlow.setTradeDate(new Date());
				voucherFlow.setRatio(voucher.getRatio());
				voucherFlow.setCreateBy(acc.getDistUserId().toString());
				voucherFlow.setCreateDate(new Date());
				orgFundExchangeFlowService.orgUserVoucherFlow(voucherFlow);// 记业务经理分发流水
				break; // <=0,跳出循环
			}
		}
		
		return 1;
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
		dao.update(voucherAccCash);
	}

}
