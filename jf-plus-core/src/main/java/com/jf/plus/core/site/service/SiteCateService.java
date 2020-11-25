package com.jf.plus.core.site.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jf.plus.core.site.dao.SiteCateDao;
import com.jf.plus.core.site.entity.SiteCate;

import cn.iutils.common.Page;
import cn.iutils.common.service.CrudService;

/**
* 站点上架商品分类 Service层
* @author Tng
* @version 1.0
*/
@Service
@Transactional(readOnly = true)
public class SiteCateService extends CrudService<SiteCateDao, SiteCate> {

	/**
	 * 查询站点商品分类列表
	 * 
	 * @param page
	 * @return
	 */
	public List<SiteCate> findSiteCate(Page<SiteCate> page) {
		page.setTotal(dao.findSiteCateCount(page));
        return dao.findSiteCateList(page);
	}
	
}
