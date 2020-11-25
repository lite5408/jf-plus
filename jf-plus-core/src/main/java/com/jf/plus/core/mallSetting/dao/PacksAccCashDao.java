package com.jf.plus.core.mallSetting.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.jf.plus.core.mallSetting.entity.PacksAccCash;

import cn.iutils.common.ICrudDao;
import cn.iutils.common.Page;
import cn.iutils.common.annotation.MyBatisDao;

/**
 * 礼包卡券表 DAO接口
 *
 * @author Tng
 * @version 1.0
 */
@MyBatisDao
public interface PacksAccCashDao extends ICrudDao<PacksAccCash> {

	public int countPacksAccCashUserPage(@Param("page") Page<PacksAccCash> page);

	public List<PacksAccCash> findPacksAccCashUserPage(@Param("page") Page<PacksAccCash> page);

	public PacksAccCash getEntityByBind(PacksAccCash entity);

}
