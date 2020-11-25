package com.jf.plus.core.setting.dao;

import java.util.List;

import com.jf.plus.core.setting.entity.District;

import cn.iutils.common.ICrudDao;
import cn.iutils.common.annotation.MyBatisDao;

/**
 * 地区表 DAO接口
 * @author Tng
 * @version 1.0
 */
@MyBatisDao
public interface DistrictDao extends ICrudDao<District> {

	public void batchAdd(List<District> list);

}
