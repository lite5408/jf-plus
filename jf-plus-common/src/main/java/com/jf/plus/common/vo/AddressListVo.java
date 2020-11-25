package com.jf.plus.common.vo;

public class AddressListVo {

	private Long id;
	private String token;
	private Integer isDefault; // 是否默认
	private String receiverName;//收货人
	private String receiverPhone;//收货人手机号
	private String jdAddress; //京东地址
	private String snAddress; //苏宁地址

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public Integer getIsDefault() {
		return isDefault;
	}
	public void setIsDefault(Integer isDefault) {
		this.isDefault = isDefault;
	}
	public String getReceiverName() {
		return receiverName;
	}
	public void setReceiverName(String receiverName) {
		this.receiverName = receiverName;
	}
	public String getReceiverPhone() {
		return receiverPhone;
	}
	public void setReceiverPhone(String receiverPhone) {
		this.receiverPhone = receiverPhone;
	}
	public String getJdAddress() {
		return jdAddress;
	}
	public void setJdAddress(String jdAddress) {
		this.jdAddress = jdAddress;
	}
	public String getSnAddress() {
		return snAddress;
	}
	public void setSnAddress(String snAddress) {
		this.snAddress = snAddress;
	}


}
