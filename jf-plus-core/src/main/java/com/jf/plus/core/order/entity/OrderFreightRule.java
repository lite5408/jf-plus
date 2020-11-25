package com.jf.plus.core.order.entity;

import cn.iutils.sys.entity.DataEntity;

/**
 * 订单运费模板配置
 * 
 * @author Tng
 * @version 1.0
 */
public class OrderFreightRule extends DataEntity<OrderFreightRule> {

	private static final long serialVersionUID = 1L;

	private Integer source; // 来源：1京东 2供应商 3苏宁 5齐心
	private Integer type;// 收费方式：1按渠道 2按机构 3按商品
	private String typeVal;// 目标值
	private String freightRule;// api:从接口实时获取 rule:按规则配置
	private Double freeRule;// 包邮价格（含）
	private Double freight;// 运费金额

	public OrderFreightRule() {
		super();
	}

	public OrderFreightRule(String id) {
		super(id);
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getTypeVal() {
		return typeVal;
	}

	public void setTypeVal(String typeVal) {
		this.typeVal = typeVal;
	}

	public String getFreightRule() {
		return freightRule;
	}

	public void setFreightRule(String freightRule) {
		this.freightRule = freightRule;
	}

	public Double getFreeRule() {
		return freeRule;
	}

	public void setFreeRule(Double freeRule) {
		this.freeRule = freeRule;
	}

	public Double getFreight() {
		return freight;
	}

	public void setFreight(Double freight) {
		this.freight = freight;
	}

	public Integer getSource() {
		return source;
	}

	public void setSource(Integer source) {
		this.source = source;
	}

}
