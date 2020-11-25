package com.jf.plus.core.weixin.entity;

import cn.iutils.sys.entity.DataEntity;

/**
* 资源
* @author Tng
* @version 1.0
*/
public class DjtWeixinMenu extends DataEntity<DjtWeixinMenu>{

    private static final long serialVersionUID = 1L;

    private Integer weixinAccountId;//
    private String type;//
    private String name;//
    private String url;//
    private String key;//
    private Integer pid;//
    private String pids;//
    private Integer seq;//

    public DjtWeixinMenu() {
        super();
    }
    public DjtWeixinMenu(String id){
        super(id);
    }

    public Integer getWeixinAccountId(){
        return weixinAccountId;
    }

    public void setWeixinAccountId(Integer weixinAccountId){
        this.weixinAccountId = weixinAccountId;
    }
    public String getType(){
        return type;
    }

    public void setType(String type){
        this.type = type == null ? null : type.trim();
    }
    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name == null ? null : name.trim();
    }
    public String getUrl(){
        return url;
    }

    public void setUrl(String url){
        this.url = url == null ? null : url.trim();
    }
    public String getKey(){
        return key;
    }

    public void setKey(String key){
        this.key = key == null ? null : key.trim();
    }
    public Integer getPid(){
        return pid;
    }

    public void setPid(Integer pid){
        this.pid = pid;
    }
    public String getPids(){
        return pids;
    }

    public void setPids(String pids){
        this.pids = pids == null ? null : pids.trim();
    }
    public Integer getSeq(){
    return seq;
    }

    public void setSeq(Integer seq){
    this.seq = seq;
    }
}
