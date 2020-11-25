package com.jf.plus.common.vo;

import java.io.Serializable;
/**
 * @ClassName: DictVo
 * @Description: 字典VO
 * @author tangyh
 * @date 2016年2月1日 下午8:12:26
 *
 */
public class DictVo implements Serializable{

	
	/**
	 */
	
	private static final long serialVersionUID = -366159050722276493L;
	
	private Long id;

    private String value;

    private String label;

    private String type;

    private String description;

    private Integer sort;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	@Override
	public String toString() {
		return "DictVo [id=" + id + ", value=" + value + ", label=" + label + ", type=" + type + ", description="
				+ description + ", sort=" + sort + "]";
	}
    
    

	
	

}
