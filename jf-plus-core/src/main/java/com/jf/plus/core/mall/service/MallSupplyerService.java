package com.jf.plus.core.mall.service;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jf.plus.common.exception.ServiceException;
import com.jf.plus.core.mall.dao.MallSupplyerDao;
import com.jf.plus.core.mall.entity.MallSupplyer;

import cn.iutils.common.service.CrudService;
import cn.iutils.sys.service.PasswordHelper;

/**
* 供应商表 Service层
* @author Tng
* @version 1.0
*/
@Service
@Transactional(readOnly = true)
public class MallSupplyerService extends CrudService<MallSupplyerDao, MallSupplyer> {
	
	@Autowired
	PasswordHelper passwordHelper;
	
	@Transactional(readOnly = false)
	public void changePassword(String supplyId, String password,
			String newPassword) throws Exception{
		MallSupplyer mallSupplyer = dao.get(supplyId);
		String oldPassword = mallSupplyer.getAdminPwd();
		String myPassword = DigestUtils.md5Hex(password);
		if (oldPassword.equals(myPassword)) {
			mallSupplyer.setAdminPwd(DigestUtils.md5Hex(newPassword));
			save(mallSupplyer);
		} else {
			throw new ServiceException("原密码错误");
		}
	}
}
