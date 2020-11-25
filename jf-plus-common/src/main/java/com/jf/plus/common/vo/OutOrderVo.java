package com.jf.plus.common.vo;

import java.io.Serializable;

/**
 * 外部接口订单信息
 * @author Tng
 *
 */
public class OutOrderVo implements Serializable{
	/**
	 *
	 */
	private static final long serialVersionUID = -2857933113254368276L;

	private Integer isDebug;//是否调试

	private String outTradeNo;//外部接口订单号

	private Double freight;//运费

	private Integer source;//渠道来源

	public String getOutTradeNo() {
		return outTradeNo;
	}

	public void setOutTradeNo(String outTradeNo) {
		this.outTradeNo = outTradeNo;
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

	public Integer getIsDebug() {
		return isDebug;
	}

	public void setIsDebug(Integer isDebug) {
		this.isDebug = isDebug;
	}


}
