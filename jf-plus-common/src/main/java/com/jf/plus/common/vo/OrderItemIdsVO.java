package com.jf.plus.common.vo;

import java.io.Serializable;

/**
 * 通用物流信息
 * @author Tng
 *
 */
public class OrderItemIdsVO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String msgTime;//操作时间
	
	private String content;//物流内容
	
	private String operator;//操作人
	
	private String distrName;//物流企业名称

	public String getMsgTime() {
		return msgTime;
	}

	public void setMsgTime(String msgTime) {
		this.msgTime = msgTime;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public String getDistrName() {
		return distrName;
	}

	public void setDistrName(String distrName) {
		this.distrName = distrName;
	}
	
	

}
