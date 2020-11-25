package com.jf.plus.core.account.entity;

import cn.iutils.sys.entity.DataEntity;

/**
 * 序号生成
 * 
 * @author Tng
 * @version 1.0
 */
public class CodeSeq extends DataEntity<CodeSeq> {

	private static final long serialVersionUID = 1L;

	private String seqType;//序列号类型
	private Integer seqValue;//序列号值

	public CodeSeq() {
		super();
	}

	public CodeSeq(String id) {
		super(id);
	}

	public String getSeqType() {
		return seqType;
	}

	public void setSeqType(String seqType) {
		this.seqType = seqType;
	}

	public Integer getSeqValue() {
		return seqValue;
	}

	public void setSeqValue(Integer seqValue) {
		this.seqValue = seqValue;
	}

	

}
