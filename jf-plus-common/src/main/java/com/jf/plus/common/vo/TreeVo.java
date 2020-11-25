package com.jf.plus.common.vo;

import java.io.Serializable;
import java.util.Date;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 
 * @ClassName: TreeVo
 * @Description: (树形结构的通用Vo)
 * @author wanh
 * @date 2016年2月1日 上午11:24:41
 * 
 */
public class TreeVo implements Serializable {

	private static final long serialVersionUID = 238217468539532194L;

	private Integer id;
	
	private String catId;

	private String code;

	private String name;

	private Integer pid;

	private Integer seq;

	private Integer createBy;

	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	private Date createDate;

	private Integer updateBy;

	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	private Date updateDate;

	private Byte status;

	private Integer level;

	private boolean isLeaf;

	private boolean loaded;

	private boolean expanded;

	private String userName;

	private String modifyName;

	private String catName;// 类目名称

	private Integer source;// 类目来源，1：京东 2：自建

	public String getModifyName() {
		return modifyName;
	}

	public void setModifyName(String modifyName) {
		this.modifyName = modifyName;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getPid() {
		return pid;
	}

	public void setPid(Integer pid) {
		this.pid = pid;
	}

	public Integer getSeq() {
		return seq;
	}

	public void setSeq(Integer seq) {
		this.seq = seq;
	}

	public Integer getCreateBy() {
		return createBy;
	}

	public void setCreateBy(Integer createBy) {
		this.createBy = createBy;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Integer getUpdateBy() {
		return updateBy;
	}

	public void setUpdateBy(Integer updateBy) {
		this.updateBy = updateBy;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	public boolean isLeaf() {
		return isLeaf;
	}

	public void setLeaf(boolean isLeaf) {
		this.isLeaf = isLeaf;
	}

	public boolean isLoaded() {
		return loaded;
	}

	public void setLoaded(boolean loaded) {
		this.loaded = loaded;
	}

	public boolean isExpanded() {
		return expanded;
	}

	public void setExpanded(boolean expanded) {
		this.expanded = expanded;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCatName() {
		return catName;
	}

	public void setCatName(String catName) {
		this.catName = catName;
	}

	public Byte getStatus() {
		return status;
	}

	public void setStatus(Byte status) {
		this.status = status;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Integer getSource() {
		return source;
	}

	public void setSource(Integer source) {
		this.source = source;
	}

	public String getCatId() {
		return catId;
	}

	public void setCatId(String catId) {
		this.catId = catId;
	}

	@Override
	public String toString() {
		return "TreeVo [id=" + id + ", code=" + code + ", name=" + name + ", pid=" + pid + ", seq=" + seq
				+ ", createBy=" + createBy + ", createDate=" + createDate + ", updateBy=" + updateBy + ", updateDate="
				+ updateDate + ", status=" + status + ", level=" + level + ", isLeaf=" + isLeaf + ", loaded=" + loaded
				+ ", expanded=" + expanded + ", userName=" + userName + ", modifyName=" + modifyName + "]";
	}

}
