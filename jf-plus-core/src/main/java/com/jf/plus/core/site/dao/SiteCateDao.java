package com.jf.plus.core.site.dao;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import com.jf.plus.core.site.entity.SiteCate;
import cn.iutils.common.ICrudDao;
import cn.iutils.common.Page;
import cn.iutils.common.annotation.MyBatisDao;

/**
* 站点上架商品分类 DAO接口
* @author Tng
* @version 1.0
*/
@MyBatisDao
public interface SiteCateDao extends ICrudDao<SiteCate> {

	public List<SiteCate> findSiteCateList(@Param("page") Page<SiteCate> page);

	public int findSiteCateCount(@Param("page") Page<SiteCate> page);
	
}
