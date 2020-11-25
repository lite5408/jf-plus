package cn.iutils.common.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import cn.iutils.common.BaseEntity;
import cn.iutils.common.DataScope;
import cn.iutils.common.ICrudDao;
import cn.iutils.common.Page;
import cn.iutils.sys.entity.Organization;

/**
 * Service基类
 *
 * @author cc
 */
@Transactional(readOnly = true)
public abstract class CrudService<D extends ICrudDao<T>, T extends BaseEntity<T>>
        extends BaseService {

    /**
     * 持久层对象
     */
    @Autowired
    protected D dao;

    /**
     * 获取单条数据
     *
     * @param id
     * @return
     */
    public T get(String id) {
        return dao.get(id);
    }

    /**
     * 获取单条数据
     *
     * @param entity
     * @return
     */
    public T getEntity(T entity) {
        return dao.getEntity(entity);
    }

    /**
     * 查询列表数据
     *
     * @param entity
     * @return
     */
    public List<T> findList(T entity) {
    	DataScope dataScope;
    	if(entity != null && (dataScope = entity.getDataScopeFilter()) != null){
    		dataScope.setDataScopeSql(dataScopeFilter(dataScope.getUser(),dataScope.getDataScopeEnum(),dataScope.getOrgAlias(),dataScope.getUserAlias()));
    	}
    	
        return dao.findList(entity);
    }

    /**
     * 查询总数
     *
     * @return
     */
    public int count(Page<T> page) {
    	DataScope dataScope;
    	if(page!= null && page.getEntity() != null && (dataScope = page.getEntity().getDataScopeFilter()) != null){
    		dataScope.setDataScopeSql(dataScopeFilter(dataScope.getUser(),dataScope.getDataScopeEnum(),dataScope.getOrgAlias(),dataScope.getUserAlias()));
    	}
    	
        return dao.count(page);
    }

    /**
     * 查询分页数据
     *
     * @param page
     * @return
     */
    public List<T> findPage(Page<T> page) {
    	DataScope dataScope;
    	if(page!= null && page.getEntity() != null && (dataScope = page.getEntity().getDataScopeFilter()) != null){
    		dataScope.setDataScopeSql(dataScopeFilter(dataScope.getUser(),dataScope.getDataScopeEnum(),dataScope.getOrgAlias(),dataScope.getUserAlias()));
    	}
    	
        page.setTotal(dao.count(page));
        return dao.findPage(page);
    }

    /**
     * 保存数据（插入或更新）
     *
     * @param entity
     */
    @Transactional(readOnly = false)
    public int save(T entity) {
        if (entity.getIsNewId()) {
            entity.preInsert();
            return dao.insert(entity);
        } else {
            entity.preUpdate();
            return dao.update(entity);
        }
    }

    /**
     * 删除数据
     *
     * @param entity
     */
    @Transactional(readOnly = false)
    public int delete(T entity) {
        return dao.delete(entity);
    }

    /**
     * 删除数据
     *
     * @param id
     */
    @Transactional(readOnly = false)
    public int delete(String id) {
        return dao.delete(id);
    }
    
    
    @Transactional(readOnly = false)
    public int deleteEntity(T entity) {
        return dao.deleteEntity(entity);
    }
    
    public int findUniqueCount( T entity) {
		return dao.findUniqueCount(entity);
	}

}
