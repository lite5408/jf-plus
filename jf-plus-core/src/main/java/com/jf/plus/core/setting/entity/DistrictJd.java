package com.jf.plus.core.setting.entity;

import cn.iutils.sys.entity.DataEntity;

/**
* 
* @author Tng
* @version 1.0
*/
public class DistrictJd extends DataEntity<DistrictJd>{

    private static final long serialVersionUID = 1L;

    private String districtId;//地区id
    private String channelId;//
    private String name;//地区名称
    private Integer type;//类型
    private String parentId;//父级ID
    private Integer source;//渠道

    public DistrictJd() {
        super();
    }
    public DistrictJd(String id){
        super(id);
    }

    public String getDistrictId(){
        return districtId;
    }

    public void setDistrictId(String districtId){
        this.districtId = districtId == null ? null : districtId.trim();
    }
    public String getChannelId(){
        return channelId;
    }

    public void setChannelId(String channelId){
        this.channelId = channelId == null ? null : channelId.trim();
    }
    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name == null ? null : name.trim();
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
        this.parentId = parentId == null ? null : parentId.trim();
    }
    public Integer getSource(){
        return source;
    }

    public void setSource(Integer source){
        this.source = source;
    }
}
