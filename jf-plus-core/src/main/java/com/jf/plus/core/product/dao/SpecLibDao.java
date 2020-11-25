package com.jf.plus.core.product.dao;

import com.jf.plus.core.product.entity.ProductSku;
import com.jf.plus.core.product.entity.SpecLib;

import cn.iutils.common.ICrudDao;
import cn.iutils.common.annotation.MyBatisDao;

/**
* 机构规格库 DAO接口
* @author Tng
* @version 1.0
*/
@MyBatisDao
public interface SpecLibDao extends ICrudDao<SpecLib> {
	String findSkuCode(ProductSku productSku);
}
