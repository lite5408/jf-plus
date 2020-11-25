package com.jf.plus.core.product.entity;

import cn.iutils.sys.entity.DataEntity;

public class ProductJdPool extends DataEntity<ProductJdPool> {

	private static final long serialVersionUID = 1L;

	private String poolNum;// 商品池编码
	private String poolName;// 商品池名称

	public ProductJdPool() {
		super();
	}

	public ProductJdPool(String id) {
		super(id);
	}

	public String getPoolNum() {
		return poolNum;
	}

	public void setPoolNum(String poolNum) {
		this.poolNum = poolNum;
	}

	public String getPoolName() {
		return poolName;
	}

	public void setPoolName(String poolName) {
		this.poolName = poolName;
	}

}
