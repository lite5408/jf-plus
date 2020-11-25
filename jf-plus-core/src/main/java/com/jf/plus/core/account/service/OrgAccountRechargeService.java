package com.jf.plus.core.account.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.iutils.common.service.CrudService;
import com.jf.plus.core.account.dao.OrgAccountRechargeDao;
import com.jf.plus.core.account.entity.OrgAccountRecharge;

/**
* 组织资金账户交易流水表 Service层
* @author Tng
* @version 1.0
*/
@Service
@Transactional(readOnly = true)
public class OrgAccountRechargeService extends CrudService<OrgAccountRechargeDao, OrgAccountRecharge> {

}
