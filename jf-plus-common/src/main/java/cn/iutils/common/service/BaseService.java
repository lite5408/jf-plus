package cn.iutils.common.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import cn.iutils.sys.entity.User;
import cn.iutils.sys.entity.enums.DataScopeEnum;

/**
 * Service基类
 * 
 * @author cc
 */
@Transactional(readOnly = true)
public abstract class BaseService {

	protected Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * 数据权限过滤
     * @param user
     * @return
     */
    public static String dataScopeFilter(User user,DataScopeEnum dataScopeEnum,String orgAlias,String userAlias){
    	StringBuffer dataScopeSql = new StringBuffer();
    	if(user != null){
    		switch (dataScopeEnum) {
			case all:
				
				break;

			case orgFollow:
				dataScopeSql.append(" AND ("+ orgAlias +".id = " + user.getOrganizationId() + " OR "+ orgAlias +".parent_ids LIKE '" + user.getOrganization().getParentIds() + user.getOrganizationId() + "/%')");
				break;
			case orgChild:
				dataScopeSql.append(" AND ("+ orgAlias +".parent_id = " + user.getOrganizationId() + " OR "+ orgAlias +".parent_ids LIKE '" + user.getOrganization().getParentIds() + user.getOrganizationId() + "/%')");
				break;
			case org:
				dataScopeSql.append(" AND ("+ orgAlias +".id = " + user.getOrganizationId() + ")");
				break;
			case self:
				dataScopeSql.append(" AND ("+ userAlias +".create_by = "+ user.getId() +" OR "+ userAlias +".update_by = "+ user.getId() +")");
				break;
				
			default:
				break;
			}
    		
    		return dataScopeSql.toString();
    	}
    	return null;
    }

}

