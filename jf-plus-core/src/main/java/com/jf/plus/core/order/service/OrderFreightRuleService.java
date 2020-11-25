package com.jf.plus.core.order.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.iutils.common.service.CrudService;
import com.jf.plus.core.order.dao.OrderFreightRuleDao;
import com.jf.plus.core.order.entity.OrderFreightRule;

/**
* 订单运费模板配置 Service层
* @author Tng
* @version 1.0
*/
@Service
@Transactional(readOnly = true)
public class OrderFreightRuleService extends CrudService<OrderFreightRuleDao, OrderFreightRule> {

}
