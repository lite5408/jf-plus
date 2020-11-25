package com.jf.plus.core.order.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.iutils.common.service.CrudService;
import com.jf.plus.core.order.dao.OrderAuditDetailDao;
import com.jf.plus.core.order.entity.OrderAuditDetail;

/**
* 审核单明细 Service层
* @author Tng
* @version 1.0
*/
@Service
@Transactional(readOnly = true)
public class OrderAuditDetailService extends CrudService<OrderAuditDetailDao, OrderAuditDetail> {

}
