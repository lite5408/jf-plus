package com.jf.plus.core.setting.entity;

import java.util.List;

import com.alibaba.fastjson.annotation.JSONField;

import cn.iutils.sys.entity.DataEntity;

/**
 * 地区表
 * @author Tng
 * @version 1.0
 */
public class District extends DataEntity<District>{

	private static final long serialVersionUID = 1L;

	private String districtId;//地区id
	private String channelId;//渠道地区id
	private String name;//地区名称
	private Integer type;//类型
	private String parentId;//父级ID
	private Integer source;//渠道
	
	private List<District> districts;

	public District() {
		super();
	}
	public District(String id){
		super(id);
	}

	public String getDistrictId(){
		return districtId;
	}

	public void setDistrictId(String districtId){
		this.districtId = districtId;
	}
	public String getName(){
		return name;
	}

	public void setName(String name){
		this.name = name;
	}
	public Integer getType(){
		return type;
	}

	public void setType(Integer type){
		this.type = type;
	}
	public String getParentId(){
		return parentId;
	}

	public void setParentId(String parentId){
		this.parentId = parentId;
	}
	public Integer getSource(){
		return source;
	}

	public void setSource(Integer source){
		this.source = source;
	}

	public String getChannelId() {
		return channelId;
	}
	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}
	public List<District> getDistricts() {
		return districts;
	}
	public void setDistricts(List<District> districts) {
		this.districts = districts;
	}
	
	
}
