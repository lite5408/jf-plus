package com.jf.plus.core.mallSetting.service;

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
import com.jf.plus.common.core.enums.DealType;
import com.jf.plus.common.core.enums.Status;
import com.jf.plus.common.core.enums.UserType;
import com.jf.plus.common.utils.CardGen;
import com.jf.plus.common.utils.CodeGen;
import com.jf.plus.common.utils.DateUtils;
import com.jf.plus.core.account.entity.VoucherFlow;
import com.jf.plus.core.account.service.CodeService;
import com.jf.plus.core.account.service.VoucherFlowService;
import com.jf.plus.core.mallSetting.dao.PacksInfoDao;
import com.jf.plus.core.mallSetting.entity.PacksAccCash;
import com.jf.plus.core.mallSetting.entity.PacksInfo;

import cn.iutils.common.Page;
import cn.iutils.common.service.CrudService;
import cn.iutils.common.utils.UserUtils;
import cn.iutils.sys.entity.User;
import cn.iutils.sys.entity.enums.UserSource;
import cn.iutils.sys.service.PasswordHelper;
import cn.iutils.sys.service.UserService;

/**
 * 礼包信息表 Service层
 *
 * @author Tng
 * @version 1.0
 */
@Service
@Transactional(readOnly = true)
public class PacksInfoService extends CrudService<PacksInfoDao, PacksInfo> {

	private static Logger LOGGER = LoggerFactory.getLogger(PacksInfoService.class);

	@Autowired
	private CodeService codeService;

	@Autowired
	private UserService userService;

	@Autowired
	private PacksAccCashService packsAccCashService;

	@Autowired
	private VoucherFlowService voucherFlowService;

	@Autowired
	private PasswordHelper passwordHelper;

	public List<PacksInfo> findDistPacksList(Page<PacksInfo> page) {
		return dao.findDistPacksList(page);
	}

	@Transactional(readOnly = false)
	public int bindUser(String packsInfoId, String mobile, String remarks) {
		try {
			PacksInfo packsInfo = dao.get(packsInfoId);

			User user = userService.getEntityByPhone(mobile);
			if (user == null) {
				user = new User();
				user.setOrganizationIds(packsInfo.getOrgId() + "");
				user.setUsername(mobile);
				user.setPassword(mobile.substring(mobile.length()-5));
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

			// C端客户礼包额度增加
			PacksAccCash packsAccCash = new PacksAccCash();
			packsAccCash.setPacksId(Long.valueOf(packsInfo.getId()));
			packsAccCash.setBatchNo(CodeGen.getBatchNo());
			packsAccCash.setDistOrgId(packsInfo.getOrgId());
			packsAccCash.setDistUserId(Long.valueOf(UserUtils.getLoginUser().getId()));
			packsAccCash.setDelayCount(0);
			int couponAccount = codeService.genAccount(Constants.PACKS_SEQ);
			packsAccCash.setCouponAccount(Constants.ZDFF_PREFIX.toUpperCase() + CardGen.formatAccount(couponAccount));
			packsAccCash.setCouponCode(CardGen.getCardAccPwd());
			packsAccCash.setBindUser(Long.valueOf(user.getId()));
			packsAccCash.setBindDate(new Date());
			packsAccCash.setValidStartDate(packsInfo.getValidStartDate());
			packsAccCash.setValidEndDate(packsInfo.getValidEndDate());
			packsAccCash.setOperStatus(Status.NORMAL.getType());
			packsAccCash.setCreateBy(UserUtils.getLoginUser().getId());
			packsAccCash.setCreateDate(new Date());
			packsAccCash.setUpdateBy(UserUtils.getLoginUser().getId());
			packsAccCash.setUpdateDate(new Date());
			packsAccCash.setRemarks(remarks);
			packsAccCash.setStatus(Status.NORMAL.getType() + "");
			packsAccCashService.save(packsAccCash);

			VoucherFlow voucherFlow = new VoucherFlow();
			voucherFlow.setAccId(Long.valueOf(packsAccCash.getId()));
			voucherFlow.setUserId(packsAccCash.getBindUser());
			voucherFlow.setTargetId(Long.valueOf(packsAccCash.getId()));
			voucherFlow.setAccountType(AccountType.LB.getType());
			voucherFlow.setDealType(DealType.INCOME.getType());
			voucherFlow.setAbstractType(AbstractType.AMOUNT_DIST.getType());
			voucherFlow.setTradeAmount(packsInfo.getSalePrice());
			voucherFlow.setTradeDate(new Date());
			voucherFlow.setRatio(packsInfo.getRatio());
			voucherFlow.setCreateBy(UserUtils.getLoginUser().getId());
			voucherFlow.setCreateDate(new Date());
			voucherFlow.setUpdateBy(UserUtils.getLoginUser().getId());
			voucherFlow.setUpdateDate(new Date());
			voucherFlow.setRemarks("分发客户礼包-" + remarks);
			voucherFlow.setStatus(Status.NORMAL.getType() + "");
			voucherFlowService.save(voucherFlow);

			return 1;
		} catch (Exception e) {
			LOGGER.info("分发礼包异常：{}", e);
			return 0;
		}
	}

	public List<PacksInfo> findPackList(Page<PacksInfo> page) {
		page.setTotal(dao.findPackCount(page));
		return dao.findPackList(page);
	}

	public List<PacksInfo> findPresentPackList(Page<PacksInfo> page) {
		page.setTotal(dao.findPresentPackCount(page));
		return dao.findPresentPackList(page);
	}


}
