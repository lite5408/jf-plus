package com.jf.plus.core.setting.entity;

import java.util.List;

import com.alibaba.fastjson.annotation.JSONField;
import com.jf.plus.common.vo.AddressVo;

import cn.iutils.sys.entity.DataEntity;

/**
 * 用户地址表
 * @author Tng
 * @version 1.0
 */
public class UserAddress extends DataEntity<UserAddress>{

	private static final long serialVersionUID = 1L;

	private Long userId;//用户id
	private String receiverName;//收货人
	private String receiverPhone;//收货人手机号
	private String receiverEmail;//收货人邮箱
	private String address;//收货地址
	private String addressDetail;//详细地址
	private Long siteId;// 站点id
	private Integer isDefault; // 是否默认

	/**
	 * 自定义属性
	 */
	private List<AddressVo> voList; // 新增时需要
	private String token; // 新增时需要
	@JSONField(serialize=false)
	private String jdAddress;
	@JSONField(serialize=false)
	private String gysAddress;
	@JSONField(serialize=false)
	private String snAddress;
	@JSONField(serialize=false)
	private String yxAddress;
	@JSONField(serialize=false)
	private String qxAddress;

	public UserAddress() {
		super();
	}
	public UserAddress(String id){
		super(id);
	}

	public Long getUserId(){
		return userId;
	}

	public void setUserId(Long userId){
		this.userId = userId;
	}
	public String getReceiverName(){
		return receiverName;
	}

	public void setReceiverName(String receiverName){
		this.receiverName = receiverName;
	}
	public String getReceiverPhone(){
		return receiverPhone;
	}

	public void setReceiverPhone(String receiverPhone){
		this.receiverPhone = receiverPhone;
	}
	public String getReceiverEmail(){
		return receiverEmail;
	}

	public void setReceiverEmail(String receiverEmail){
		this.receiverEmail = receiverEmail;
	}

	public String getAddress(){
		return address;
	}

	public void setAddress(String address){
		this.address = address;
	}

	public Long getSiteId() {
		return siteId;
	}

	public void setSiteId(Long siteId) {
		this.siteId = siteId;
	}
	public String getAddressDetail() {
		return addressDetail;
	}

	public void setAddressDetail(String addressDetail) {
		this.addressDetail = addressDetail;
	}
	public List<AddressVo> getVoList() {
		return voList;
	}
	public void setVoList(List<AddressVo> voList) {
		this.voList = voList;
	}
	public String getJdAddress() {
		return jdAddress;
	}
	public void setJdAddress(String jdAddress) {
		this.jdAddress = jdAddress;
	}
	public String getGysAddress() {
		return gysAddress;
	}
	public void setGysAddress(String gysAddress) {
		this.gysAddress = gysAddress;
	}
	public String getSnAddress() {
		return snAddress;
	}
	public void setSnAddress(String snAddress) {
		this.snAddress = snAddress;
	}
	public String getYxAddress() {
		return yxAddress;
	}
	public void setYxAddress(String yxAddress) {
		this.yxAddress = yxAddress;
	}
	public String getQxAddress() {
		return qxAddress;
	}
	public void setQxAddress(String qxAddress) {
		this.qxAddress = qxAddress;
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
}
