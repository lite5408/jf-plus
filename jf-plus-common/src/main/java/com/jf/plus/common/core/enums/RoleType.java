package com.jf.plus.common.core.enums;

/**
 * 内置角色类型
 * @author Tng
 *
 */
public enum RoleType {
	USER_CG("sys:user:cg", "代理商经办"),
	ORDER_ORDER("sys:order:order", "订单查询"),
	MALL_SITE("sys:mall:site", "商城专员"),
	MALL_PRODUCT("sys:mall:product","商品维护"),
	MALL_SUPPLY("sys:mall:supplyer", "供应商管理"),
	ACCOUNT_USER("sys:account:user", "代理商经办额度充值 "),
	ACCOUNT_ORG("sys:account:org", "机构资金管理"),
	AUDIT_ORDER("sys:audit:order", "订单审批"),
	AUDIT_SETTING("sys:audit:settings","审批参数设置"),
	USER_GROUP("sys:user:usergroup", "角色组配置"),
	USER_ORG("sys:user:org","机构配置"),
	USER("sys:user:user", "用户管理"),
	BUSS_DIST("sys:buss:dist", "业务经理分发"),
	USER_BUYER("sys:user:buyer","商品发布"),
	USER_BUYER_KF("sys:user:buyer:kf","库房发布");

	private String type;

	private String description;

	private RoleType(String type, String description) {
		this.type = type;
		this.description = description;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}



}
