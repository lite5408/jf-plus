package com.jf.plus.core.order.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.iutils.common.Page;
import cn.iutils.common.service.CrudService;
import com.jf.plus.core.order.dao.OrderDeliveryExportDao;
import com.jf.plus.core.order.entity.OrderDeliveryExport;

/**
* 订单导出表 Service层
* @author Tng
* @version 1.0
*/
@Service
@Transactional(readOnly = true)
public class OrderDeliveryExportService extends CrudService<OrderDeliveryExportDao, OrderDeliveryExport> {

	public List<OrderDeliveryExport> findBuyerPage(Page<OrderDeliveryExport> page) {
		page.setTotal(dao.countBuyer(page));
		return dao.findBuyerPage(page);
	}

	public Map<String, Object> sumExport(Page<OrderDeliveryExport> page) {
		return dao.sumExport(page);
	}

	public Map<String, Object> countExport(Page<OrderDeliveryExport> page) {
		return dao.countExport(page);
	}

}
