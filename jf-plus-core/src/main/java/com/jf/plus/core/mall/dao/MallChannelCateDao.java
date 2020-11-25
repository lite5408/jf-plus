package com.jf.plus.core.mall.dao;

import java.util.List;
import com.jf.plus.core.mall.entity.MallChannelCate;
import cn.iutils.common.ICrudDao;
import cn.iutils.common.annotation.MyBatisDao;

/**
 * 渠道商品分类表 DAO接口
 * @author Tng
 * @version 1.0
 */
@MyBatisDao
public interface MallChannelCateDao extends ICrudDao<MallChannelCate> {

	public int batchInsert(List<MallChannelCate> list);

}
