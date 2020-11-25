package com.jf.plus.core.mall.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.iutils.common.Page;
import cn.iutils.common.service.CrudService;
import com.jf.plus.core.mall.dao.MallSiteDao;
import com.jf.plus.core.mall.entity.MallSite;

/**
* 商城站点表 Service层
* @author Tng
* @version 1.0
*/
@Service
@Transactional(readOnly = true)
public class MallSiteService extends CrudService<MallSiteDao, MallSite> {

	/**
	 * 根据用户查询商城列表
	 * 
	 * @param page
	 * @return
	 */
    public List<MallSite> findListByUser(Page<MallSite> page) {
        return dao.findListByUser(page);
    }
	
}
