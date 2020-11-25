package com.jf.plus.common.vo;

public class ProductSkuVo {
	
	private String specColor;//颜色
    private String specColorText;//颜色值
    private String specSize;//尺码
    private String specSizeText;//尺码值
	public String getSpecColor() {
		return specColor;
	}
	public void setSpecColor(String specColor) {
		this.specColor = specColor;
	}
	public String getSpecColorText() {
		return specColorText;
	}
	public void setSpecColorText(String specColorText) {
		this.specColorText = specColorText;
	}
	public String getSpecSize() {
		return specSize;
	}
	public void setSpecSize(String specSize) {
		this.specSize = specSize;
	}
	public String getSpecSizeText() {
		return specSizeText;
	}
	public void setSpecSizeText(String specSizeText) {
		this.specSizeText = specSizeText;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ProductSkuVo other = (ProductSkuVo) obj;
		if (specColor == null) {
			if (other.specColor != null)
				return false;
		} else if (!specColor.equals(other.specColor))
			return false;
		if (specColorText == null) {
			if (other.specColorText != null)
				return false;
		} else if (!specColorText.equals(other.specColorText))
			return false;
		if (specSize == null) {
			if (other.specSize != null)
				return false;
		} else if (!specSize.equals(other.specSize))
			return false;
		if (specSizeText == null) {
			if (other.specSizeText != null)
				return false;
		} else if (!specSizeText.equals(other.specSizeText))
			return false;
		return true;
	}
	
	
    
}
