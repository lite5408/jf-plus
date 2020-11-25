package cn.iutils.sys.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.iutils.common.Page;
import cn.iutils.common.service.CrudService;
import cn.iutils.sys.dao.IOrganizationDao;
import cn.iutils.sys.entity.Organization;

/**
 * 组织机构服务
 * 
 * @author cc
 */
@Service
@Transactional(readOnly = true)
public class OrganizationService extends CrudService<IOrganizationDao, Organization> {

    /**
     * 查询是否存在下级节点
     * @param organization
     * @return
     */
    public int findNext(Organization organization){
        return dao.findNext(organization);
    }

    /**
     * 根据parentIds查询当前和下级所有机构
     * @param organization
     * @return
     */
	public List<Organization> findAllLowList(Organization organization) {
		return dao.findAllLowList(organization);
	}

	/**
     * 根据parentIds更改所有下级和当前级别机构的积分比例
     * @param organization
     * @return
     */
	@Transactional(readOnly = false)
	public void changeRatioByParentIds(Organization organization) {
		dao.changeRatioByParentIds(organization);
	}

	public List<Organization> findByParentIds(String orgIds) {
		return dao.findByParentIds(orgIds);
	}

	

}
