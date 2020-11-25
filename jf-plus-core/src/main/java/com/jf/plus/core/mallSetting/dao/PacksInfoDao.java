package com.jf.plus.core.mallSetting.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.jf.plus.core.mallSetting.entity.PacksInfo;

import cn.iutils.common.ICrudDao;
import cn.iutils.common.Page;
import cn.iutils.common.annotation.MyBatisDao;

/**
 * 礼包信息表 DAO接口
 * @author Tng
 * @version 1.0
 */
@MyBatisDao
public interface PacksInfoDao extends ICrudDao<PacksInfo> {

	public List<PacksInfo> findDistPacksList(@Param("page") Page<PacksInfo> page);

	public List<PacksInfo> findPackList(@Param("page") Page<PacksInfo> page);

	public int findPackCount(@Param("page") Page<PacksInfo> page);

	public List<PacksInfo> findPresentPackList(@Param("page") Page<PacksInfo> page);

	public int findPresentPackCount(@Param("page") Page<PacksInfo> page);

}
