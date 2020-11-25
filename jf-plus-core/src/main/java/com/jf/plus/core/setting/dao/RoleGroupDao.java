package com.jf.plus.core.setting.dao;

import java.util.List;

import com.jf.plus.core.setting.entity.RoleGroup;

import cn.iutils.common.ICrudDao;
import cn.iutils.common.annotation.MyBatisDao;

/**
* 角色组 DAO接口
* @author Tng
* @version 1.0
*/
@MyBatisDao
public interface RoleGroupDao extends ICrudDao<RoleGroup> {

	List<RoleGroup> findMyRoleGroupList(RoleGroup entity);

}
