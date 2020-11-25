package cn.iutils.common;

import java.io.Serializable;

import cn.iutils.sys.entity.User;
import cn.iutils.sys.entity.enums.DataScopeEnum;

public class DataScope implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8672223639132168652L;
	
	private User user;
	private DataScopeEnum dataScopeEnum;// 数据权限
	private String orgAlias;// 组织机构别名
	private String userAlias;// 用户表别名
	private String dataScopeSql;// 数据权限过滤sql

	public DataScope() {
		super();
	}

	public DataScope(User user, DataScopeEnum dataScopeEnum, String orgAlias, String userAlias) {
		super();
		this.user = user;
		this.dataScopeEnum = dataScopeEnum;
		this.orgAlias = orgAlias;
		this.userAlias = userAlias;
	}

	public DataScopeEnum getDataScopeEnum() {
		return dataScopeEnum;
	}

	public void setDataScopeEnum(DataScopeEnum dataScopeEnum) {
		this.dataScopeEnum = dataScopeEnum;
	}

	public String getOrgAlias() {
		return orgAlias;
	}

	public void setOrgAlias(String orgAlias) {
		this.orgAlias = orgAlias;
	}

	public String getUserAlias() {
		return userAlias;
	}

	public void setUserAlias(String userAlias) {
		this.userAlias = userAlias;
	}

	public String getDataScopeSql() {
		return dataScopeSql;
	}

	public void setDataScopeSql(String dataScopeSql) {
		this.dataScopeSql = dataScopeSql;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}
