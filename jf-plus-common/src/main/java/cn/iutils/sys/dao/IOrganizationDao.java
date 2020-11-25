package cn.iutils.sys.dao;

import java.util.List;

import cn.iutils.common.ICrudDao;
import cn.iutils.common.annotation.MyBatisDao;
import cn.iutils.sys.entity.Organization;

/**
 * 组织机构Dao
 * 
 * @author cc
 */
@MyBatisDao
public interface IOrganizationDao extends ICrudDao<Organization> {

    /**
     * 查询是否存在下级节点
     * @param organization
     * @return
     */
    public int findNext(Organization organization);

    /**
     * 根据parentIds查询当前和下级所有机构
     * @param organization
     * @return
     */
	public List<Organization> findAllLowList(Organization organization);

	/**
     * 根据parentIds更改所有下级和当前级别机构的积分比例
     * @param organization
     * @return
     */
	public void changeRatioByParentIds(Organization organization);

	public List<Organization> findByParentIds(String orgIds);

}
