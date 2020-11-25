package com.jf.plus.core.account.dao;

import com.jf.plus.core.account.entity.Blance;

import cn.iutils.common.ICrudDao;
import cn.iutils.common.annotation.MyBatisDao;

/**
* 余额获取
* @author Tng
* @version 1.0
*/
@MyBatisDao
public interface BlanceDao extends ICrudDao<Blance> {

	Double getOrgBlance(String orgId);

	Double getJCBlance(String userId);
	
	Double getFFBlance(String userId);
	
	Double getJFBlance(String userId);

	Double getVoucherNotUsed(String orgId);
	
	Double getPacksNotUsed(String orgId);

	Double getUserOrgJFBlance(Blance blance);

}
