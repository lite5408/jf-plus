package com.jf.plus.core.setting.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jf.plus.core.setting.dao.RoleGroupDao;
import com.jf.plus.core.setting.entity.RoleGroup;

import cn.iutils.common.service.CrudService;

/**
* 角色组 Service层
* @author Tng
* @version 1.0
*/
@Service
@Transactional(readOnly = true)
public class RoleGroupService extends CrudService<RoleGroupDao, RoleGroup> {

	public List<RoleGroup> findMyRoleGroupList(RoleGroup entity) {
		return dao.findMyRoleGroupList(entity);
	}

}
