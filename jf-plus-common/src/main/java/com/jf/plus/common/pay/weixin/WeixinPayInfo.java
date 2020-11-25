package com.jf.plus.common.pay.weixin;

/**
 * 
 * @ClassName: WeixinPayInfo
 * @Description: 微信支付信息
 * @author hbh
 * @date 2016-3-19 下午4:47:49
 * 
 */
public class WeixinPayInfo {

	private String orderId; // 订单号
	private Integer totalFee; // 金额，以分为单位
	private String spbillCreateIp; // 订单生成的机器 IP
	private String notifyUrl; // 这里notify_url是
								// 支付完成后微信发给该链接信息，可以判断会员是否支付成功，改变订单状态等
	private String body; // 商品描述根据情况修改
	private String openId; // 微信用户对一个公众号唯一
	private String tradeType;//交易类型
	/**
	 * @return the orderId
	 */
	public String getOrderId() {
		return orderId;
	}

	/**
	 * @param orderId
	 *            the orderId to set
	 */
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	/**
	 * @return 获取 金额 以分为单位
	 */
	public Integer getTotalFee() {
		return totalFee;
	}

	/**
	 * @param 设置
	 *            金额 以分为单位
	 */
	public void setTotalFee(Integer totalFee) {
		this.totalFee = totalFee;
	}

	/**
	 * @return the spbillCreateIp
	 */
	public String getSpbillCreateIp() {
		return spbillCreateIp;
	}

	/**
	 * @param spbillCreateIp
	 *            the spbillCreateIp to set
	 */
	public void setSpbillCreateIp(String spbillCreateIp) {
		this.spbillCreateIp = spbillCreateIp;
	}

	/**
	 * @return the notifyUrl
	 */
	public String getNotifyUrl() {
		return notifyUrl;
	}

	/**
	 * @param notifyUrl
	 *            the notifyUrl to set
	 */
	public void setNotifyUrl(String notifyUrl) {
		this.notifyUrl = notifyUrl;
	}

	/**
	 * @return the body
	 */
	public String getBody() {
		return body;
	}

	/**
	 * @param body
	 *            the body to set
	 */
	public void setBody(String body) {
		this.body = body;
	}

	/**
	 * @return the openId
	 */
	public String getOpenId() {
		return openId;
	}

	/**
	 * @param openId
	 *            the openId to set
	 */
	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public String getTradeType() {
		return tradeType;
	}

	public void setTradeType(String tradeType) {
		this.tradeType = tradeType;
	}

	
	
}
