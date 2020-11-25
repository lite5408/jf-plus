package cn.iutils.sys.dao;

import java.util.List;
import java.util.Set;

import org.apache.ibatis.annotations.Param;

import cn.iutils.common.ICrudDao;
import cn.iutils.common.annotation.MyBatisDao;
import cn.iutils.sys.entity.Role;

/**
 * 权限Dao接口
 * 
 * @author cc
 */
@MyBatisDao
public interface IRoleDao extends ICrudDao<Role> {

    /**
     * 获取权限资源ID
     * @param roleIds
     * @return
     */
    public List<Role> getRoles(@Param("roleIds") String[] roleIds);
    
    
    /**
     * 获取我的角色
     * @param entity
     * @return
     */
    public List<Role> findMyRoleList(Role entity);


	public Set<String> findRolesByGroups(@Param("groupIds") List<String> groupIds);


	public Set<String> findPermissionsByGroups(@Param("groupIds") List<String> groupIds);

}
