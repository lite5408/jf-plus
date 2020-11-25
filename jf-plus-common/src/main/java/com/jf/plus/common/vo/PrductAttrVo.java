package com.jf.plus.common.vo;

public class PrductAttrVo {

	private Long attrId;//属性ID
    private String attrText;//属性名
    private String attrInfo;//属性值
	public Long getAttrId() {
		return attrId;
	}
	public void setAttrId(Long attrId) {
		this.attrId = attrId;
	}
	public String getAttrText() {
		return attrText;
	}
	public void setAttrText(String attrText) {
		this.attrText = attrText;
	}
	public String getAttrInfo() {
		return attrInfo;
	}
	public void setAttrInfo(String attrInfo) {
		this.attrInfo = attrInfo;
	}

	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PrductAttrVo other = (PrductAttrVo) obj;
		if (attrId == null) {
			if (other.attrId != null)
				return false;
		} else if (!attrId.equals(other.attrId))
			return false;
		if (attrInfo == null) {
			if (other.attrInfo != null)
				return false;
		} else if (!attrInfo.equals(other.attrInfo))
			return false;
		if (attrText == null) {
			if (other.attrText != null)
				return false;
		} else if (!attrText.equals(other.attrText))
			return false;
		return true;
	}
    
	
    
}
