package com.jf.plus.core.mallSetting.entity;

import cn.iutils.sys.entity.DataEntity;

/**
 * 商城分销配置表
 * @author Tng
 * @version 1.0
 */
public class MallSiteRule extends DataEntity<MallSiteRule>{

	private static final long serialVersionUID = 1L;

	private Long orgId;//
	private Integer distributeType;//分销类型(1:按折扣 2:按固定值)
	private Double distributeValue;//值
	private Integer allowExceedMarketPrice;//是否允许超出市场价(1是0否)
	private Double exceedRatio;//超出市场价比例
	private Integer productSource;//商品渠道(1:京东  2供应商  3苏宁 4严选 5齐心)

	public MallSiteRule() {
		super();
	}
	public MallSiteRule(String id){
		super(id);
	}

	public Long getOrgId(){
		return orgId;
	}

	public void setOrgId(Long orgId){
		this.orgId = orgId;
	}
	public Integer getDistributeType(){
		return distributeType;
	}

	public void setDistributeType(Integer distributeType){
		this.distributeType = distributeType;
	}
	public Double getDistributeValue(){
		return distributeValue;
	}

	public void setDistributeValue(Double distributeValue){
		this.distributeValue = distributeValue;
	}
	public Integer getAllowExceedMarketPrice(){
		return allowExceedMarketPrice;
	}

	public void setAllowExceedMarketPrice(Integer allowExceedMarketPrice){
		this.allowExceedMarketPrice = allowExceedMarketPrice;
	}
	public Double getExceedRatio(){
		return exceedRatio;
	}

	public void setExceedRatio(Double exceedRatio){
		this.exceedRatio = exceedRatio;
	}
	public Integer getProductSource(){
		return productSource;
	}

	public void setProductSource(Integer productSource){
		this.productSource = productSource;
	}
}
