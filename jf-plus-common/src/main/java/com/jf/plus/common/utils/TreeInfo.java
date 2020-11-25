package com.jf.plus.common.utils;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import com.alibaba.fastjson.annotation.JSONField;
import com.google.common.collect.Maps;

/**
 * @description：树形结构封装 (结合jqgrid)
 * @author：wh
 * @date：2016年1月30日 上午11:12:36
 */
public class TreeInfo implements Serializable{

    private List rows; //显示的记录  

    @JSONField(serialize = false)
    private Map<String, Object> condition = Maps.newHashMap(); //查询条件
    @JSONField(serialize = false)
    private String sort = "seq";// 排序字段
    @JSONField(serialize = false)
    private String order = "asc";// asc，desc mybatis Order 关键字

	public List getRows() {
        return rows;
    }

    public void setRows(List rows) {
        this.rows = rows;
    }

    public Map<String, Object> getCondition() {
        return condition;
    }

    public void setCondition(Map<String, Object> condition) {
        this.condition = condition;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

}
