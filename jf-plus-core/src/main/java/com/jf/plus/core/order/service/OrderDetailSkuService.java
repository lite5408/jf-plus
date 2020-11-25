package com.jf.plus.core.order.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.iutils.common.Page;
import cn.iutils.common.service.CrudService;
import com.jf.plus.core.order.dao.OrderDetailSkuDao;
import com.jf.plus.core.order.entity.OrderDetailSku;

/**
* 订单明细表 Service层
* @author Tng
* @version 1.0
*/
@Service
@Transactional(readOnly = true)
public class OrderDetailSkuService extends CrudService<OrderDetailSkuDao, OrderDetailSku> {

	public List<OrderDetailSku> findDistSkuList(Page<OrderDetailSku> pageSku) {
		return dao.findDistSkuList(pageSku);
	}

	public List<OrderDetailSku> findSumPage(Page<OrderDetailSku> skuPage) {
		skuPage.setTotal(dao.countSum(skuPage));
		return dao.findSumPage(skuPage);
	}

}
