package com.jf.plus.core.setting.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jf.plus.core.setting.dao.DistrictDao;
import com.jf.plus.core.setting.entity.District;

import cn.iutils.common.service.CrudService;

/**
 * 地区表 Service层
 *
 * @author Tng
 * @version 1.0
 */
@Service
@Transactional(readOnly = true)
public class DistrictService extends CrudService<DistrictDao, District> {

	@Transactional(readOnly = false)
	public void batchAdd(List<District> list) {
		dao.batchAdd(list);
	}

}
