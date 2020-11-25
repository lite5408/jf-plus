package com.jf.plus.core.mallSetting.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.base.Joiner;
import com.jf.plus.common.core.Result;
import com.jf.plus.common.core.ResultCode;
import com.jf.plus.common.core.enums.Status;
import com.jf.plus.common.core.enums.UserType;
import com.jf.plus.common.vo.PersentVo;
import com.jf.plus.core.mallSetting.dao.PacksAccCashDao;
import com.jf.plus.core.mallSetting.dao.PacksPresentDao;
import com.jf.plus.core.mallSetting.entity.PacksAccCash;
import com.jf.plus.core.mallSetting.entity.PacksPresent;

import cn.iutils.common.DataScope;
import cn.iutils.common.Page;
import cn.iutils.common.service.CrudService;
import cn.iutils.sys.dao.IUserDao;
import cn.iutils.sys.entity.User;
import cn.iutils.sys.entity.enums.UserSource;
import cn.iutils.sys.service.PasswordHelper;

/**
 * 礼包卡券表 Service层
 *
 * @author Tng
 * @version 1.0
 */
@Service
@Transactional(readOnly = true)
public class PacksAccCashService extends CrudService<PacksAccCashDao, PacksAccCash> {

	@Autowired
	private IUserDao iUserDao;

	@Autowired
	private PasswordHelper passwordHelper;

	@Autowired
	private PacksPresentDao packsPresentDao;

	public PacksAccCash getEntityByBind(PacksAccCash entity) {
		return dao.getEntityByBind(entity);
	}

	public List<PacksAccCash> findPacksAccCashUserPage(Page<PacksAccCash> page) {
		DataScope dataScope;
		if (page != null && page.getEntity() != null && (dataScope = page.getEntity().getDataScopeFilter()) != null) {
			dataScope.setDataScopeSql(dataScopeFilter(dataScope.getUser(), dataScope.getDataScopeEnum(),
					dataScope.getOrgAlias(), dataScope.getUserAlias()));
		}

		page.setTotal(dao.countPacksAccCashUserPage(page));
		return dao.findPacksAccCashUserPage(page);
	}

	/**
	 * 礼包赠送
	 *
	 * @param packsAccCash
	 * @param mobile
	 * @return
	 */
	@Transactional(readOnly = false)
	public Result packsPresent(PacksAccCash packsAccCash, PersentVo persentVo) {
		Result result = Result.newInstance();
		User member = new User();
		member.setMobile(persentVo.getMobile());
		//		member.setSource(UserSource.MEM.getType());
		member = iUserDao.getEntity(member);
		if (null == member)
			member = createMember(packsAccCash.getDistOrgId(), persentVo.getMobile());
		else
			updateOrgIds(member.getId(), packsAccCash.getDistOrgId(), member.getOrganizationIds()); // 更新机构
		packsAccCash.setBindUser(Long.valueOf(member.getId()));
		packsAccCash.setBindDate(new Date());
		dao.update(packsAccCash);

		persentVo.setToId(Long.valueOf(member.getId()));
		persentVo.setPacksId(packsAccCash.getPacksId());
		// 写礼包赠送记录
		PacksPresent packsPresent = new PacksPresent();
		packsPresent.setFromId(persentVo.getFromId());
		packsPresent.setToId(persentVo.getToId());
		packsPresent.setPacksId(persentVo.getPacksId());
		packsPresent.setAccId(persentVo.getAccId());
		packsPresent.setPresentDate(new Date());
		packsPresent.setPresentText(persentVo.getPresentText());
		packsPresent.setPresentPhoto(persentVo.getPresentPhoto());
		packsPresent.setCreateBy("0");
		packsPresent.setCreateDate(new Date());
		packsPresent.setUpdateBy("0");
		packsPresent.setUpdateDate(new Date());
		packsPresent.setStatus(String.valueOf(Status.NORMAL.getType()));
		packsPresentDao.insert(packsPresent);
		result.setCode(ResultCode.SUCCESS);
		result.setObj(packsPresent.getId());
		return result;
	}

	/**
	 * 创建会员
	 *
	 * @param orgId
	 * @param mobile
	 * @return
	 */
	public User createMember(Long orgId, String mobile) {
		User member = new User();
		member.setOrganizationIds(orgId.toString());
		member.setUsername(mobile);
		member.setPhone(mobile);
		member.setMobile(mobile);
		member.setPassword(mobile.substring(5, 11));
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
			User userUpdate = new User();
			userUpdate.setId(userId);
			userUpdate.setOrganizationIds(Joiner.on(",").join(orgIds));
			iUserDao.update(userUpdate);
		}
	}

}
