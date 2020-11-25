package com.jf.plus.core.product.dao;

import java.util.List;
import com.jf.plus.core.product.entity.ProductDetail;
import cn.iutils.common.ICrudDao;
import cn.iutils.common.annotation.MyBatisDao;

/**
 * 商品详情表 DAO接口
 * @author Tng
 * @version 1.0
 */
@MyBatisDao
public interface ProductDetailDao extends ICrudDao<ProductDetail> {

	int insertAll(List<ProductDetail> list);

}
