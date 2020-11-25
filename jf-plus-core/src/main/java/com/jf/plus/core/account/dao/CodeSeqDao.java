package com.jf.plus.core.account.dao;

import com.jf.plus.core.account.entity.CodeSeq;

import cn.iutils.common.ICrudDao;
import cn.iutils.common.annotation.MyBatisDao;

/**
* 流水号生成表
* @author Tng
* @version 1.0
*/
@MyBatisDao
public interface CodeSeqDao extends ICrudDao<CodeSeq> {

	int genAccount(String type);
	
}
