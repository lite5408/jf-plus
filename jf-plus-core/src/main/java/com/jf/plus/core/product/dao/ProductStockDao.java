package com.jf.plus.core.product.dao;

import org.apache.ibatis.annotations.Param;

import com.jf.plus.core.product.entity.ProductStock;

import cn.iutils.common.ICrudDao;
import cn.iutils.common.annotation.MyBatisDao;

/**
* 商品库存表 DAO接口
* @author Tng
* @version 1.0
*/
@MyBatisDao
public interface ProductStockDao extends ICrudDao<ProductStock> {

	int minusStock(@Param("productNo") Long productNo,@Param("num") Integer saleNum);
	
	int backStock(@Param("productNo") Long productNo,@Param("num") Integer saleNum);
}
