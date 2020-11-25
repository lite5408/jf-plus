package com.jf.plus.core.mall.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.jf.plus.core.mall.entity.MallSite;

import cn.iutils.common.ICrudDao;
import cn.iutils.common.Page;
import cn.iutils.common.annotation.MyBatisDao;

/**
 * 商城站点表 DAO接口
 * @author Tng
 * @version 1.0
 */
@MyBatisDao
public interface MallSiteDao extends ICrudDao<MallSite> {

	public List<MallSite> findListByUser(@Param("page") Page<MallSite> page);

}
