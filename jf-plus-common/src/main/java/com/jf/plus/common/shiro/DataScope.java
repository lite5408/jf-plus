
/**   
 * @Title: DataScope.java
 * @Package com.djt.common.shiro
 * @Description: (用一句话描述该文件做什么)
 * @author My   
 * @date 2016年1月30日 下午2:25:56
 * @version V1.0   
 */


package com.jf.plus.common.shiro;

import java.io.Serializable;

/**
 * @ClassName: DataScope
 * @Description: 数据权限对象
 * @author tangyh
 * @date 2016年1月30日 下午2:25:56
 * 
 */

public class DataScope implements Serializable{
	
	private static final long serialVersionUID = -883613269965908392L;
	/**
	 * 当前用户
	 */
	private Object user;
	/**
	 * 数据过滤sql
	 */
	private String dataFilterSql;

	public Object getUser() {
		return user;
	}

	public void setUser(Object user) {
		this.user = user;
	}

	public String getDataFilterSql() {
		return dataFilterSql;
	}

	public void setDataFilterSql(String dataFilterSql) {
		this.dataFilterSql = dataFilterSql;
	}

}
