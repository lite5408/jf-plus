package com.jf.plus.core.product.dao;

import cn.iutils.common.ICrudDao;
import cn.iutils.common.annotation.MyBatisDao;

import java.util.List;

import com.jf.plus.core.product.entity.ProductJd;

/**
*  DAO接口
* @author Tng
* @version 1.0
*/
@MyBatisDao
public interface ProductJdDao extends ICrudDao<ProductJd> {

	int insertAll(List<ProductJd> list);

}
