package com.jf.plus.core.order.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.jf.plus.core.order.entity.OrderDetailSku;

import cn.iutils.common.ICrudDao;
import cn.iutils.common.Page;
import cn.iutils.common.annotation.MyBatisDao;

/**
* 订单明细表 DAO接口
* @author Tng
* @version 1.0
*/
@MyBatisDao
public interface OrderDetailSkuDao extends ICrudDao<OrderDetailSku> {

	List<OrderDetailSku> findDistSkuList(@Param("page") Page<OrderDetailSku> pageSku);

	int countSum(@Param("page") Page<OrderDetailSku> skuPage);

	List<OrderDetailSku> findSumPage(@Param("page") Page<OrderDetailSku> skuPage);

}
