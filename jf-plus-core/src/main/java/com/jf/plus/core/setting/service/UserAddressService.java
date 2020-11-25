package com.jf.plus.core.setting.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jf.plus.core.setting.dao.UserAddressDao;
import com.jf.plus.core.setting.entity.UserAddress;

import cn.iutils.common.service.CrudService;

/**
 * 用户地址表 Service层
 *
 * @author Tng
 * @version 1.0
 */
@Service
@Transactional(readOnly = true)
public class UserAddressService extends CrudService<UserAddressDao, UserAddress> {

	/**
	 * 根据用户ID删除
	 *
	 * @param userId
	 */
	@Transactional(readOnly = false)
	public void deleteByUserId(Long userId, Long siteId) {
		dao.deleteByUserId(userId, siteId);
	}
	
	/**
	 * 设置默认地址
	 *
	 * @param addrId
	 * @param siteId
	 */
	@Transactional(readOnly = false)
	public void setDefault(Long addrId, Long siteId) {
		dao.setDefault(addrId, siteId);
	}

}
