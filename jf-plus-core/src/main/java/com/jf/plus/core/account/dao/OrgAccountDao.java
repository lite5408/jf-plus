package com.jf.plus.core.account.dao;

import cn.iutils.common.ICrudDao;
import cn.iutils.common.annotation.MyBatisDao;
import com.jf.plus.core.account.entity.OrgAccount;

/**
* 组织资金账户表 DAO接口
* @author Tng
* @version 1.0
*/
@MyBatisDao
public interface OrgAccountDao extends ICrudDao<OrgAccount> {

	int updateBlance(OrgAccount orgAccount);
	
}
