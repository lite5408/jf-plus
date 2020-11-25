package com.jf.plus.core.product.dao;

import cn.iutils.common.ICrudDao;
import cn.iutils.common.annotation.MyBatisDao;
import com.jf.plus.core.product.entity.ProductStatusLog;

/**
* 商品状态变更表 DAO接口
* @author Tng
* @version 1.0
*/
@MyBatisDao
public interface ProductStatusLogDao extends ICrudDao<ProductStatusLog> {

}
