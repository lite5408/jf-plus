package com.jf.plus.core.product.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.iutils.common.service.CrudService;
import com.jf.plus.core.product.dao.SpecLibDao;
import com.jf.plus.core.product.entity.SpecLib;

/**
* 机构规格库 Service层
* @author Tng
* @version 1.0
*/
@Service
@Transactional(readOnly = true)
public class SpecLibService extends CrudService<SpecLibDao, SpecLib> {

}
