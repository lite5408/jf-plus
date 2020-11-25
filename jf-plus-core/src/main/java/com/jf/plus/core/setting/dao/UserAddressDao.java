package com.jf.plus.core.setting.dao;

import org.apache.ibatis.annotations.Param;

import com.jf.plus.core.setting.entity.UserAddress;

import cn.iutils.common.ICrudDao;
import cn.iutils.common.annotation.MyBatisDao;

/**
 * 用户地址表 DAO接口
 * @author Tng
 * @version 1.0
 */
@MyBatisDao
public interface UserAddressDao extends ICrudDao<UserAddress> {

	public UserAddress findDefaultAddress(@Param("userId") Long userId, @Param("source") Integer source);

	public void deleteByUserId(@Param("userId") Long userId,@Param("siteId") Long siteId);

	public UserAddress getUserAddress(@Param("userId") Long userId, @Param("siteId") Long siteId);
	
	public void setDefault(@Param("addrId") Long addrId,@Param("siteId") Long siteId);

}
