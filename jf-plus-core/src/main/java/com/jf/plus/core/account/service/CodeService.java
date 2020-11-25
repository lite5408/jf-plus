package com.jf.plus.core.account.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jf.plus.core.account.dao.CodeSeqDao;
import com.jf.plus.core.account.entity.CodeSeq;

import cn.iutils.common.service.CrudService;

@Service
@Transactional(readOnly = false)
public class CodeService extends CrudService<CodeSeqDao, CodeSeq>{

	public int genAccount(String type){
		return dao.genAccount(type);
	}

}
