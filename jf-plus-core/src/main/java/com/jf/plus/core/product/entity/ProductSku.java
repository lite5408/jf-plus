package com.jf.plus.core.product.entity;

import cn.iutils.sys.entity.DataEntity;

/**
* 
* @author Tng
* @version 1.0
*/
public class ProductSku extends DataEntity<ProductSku>{

    private static final long serialVersionUID = 1L;

    private Long productId;//商品ID
    private String itemCode;//商品编码
    private String specCode;//SKU编码
    private String specColor;//颜色
    private String specColorText;//颜色值
    private String specSize;//尺码
    private String specSizeText;//尺码值
    
    //自定义属性
    private Integer shopNum;//购买数量

    public ProductSku() {
        super();
    }
    public ProductSku(String id){
        super(id);
    }

    public Long getProductId(){
        return productId;
    }

    public void setProductId(Long productId){
        this.productId = productId;
    }
    public String getItemCode(){
        return itemCode;
    }

    public void setItemCode(String itemCode){
        this.itemCode = itemCode == null ? null : itemCode.trim();
    }
    public String getSpecCode(){
        return specCode;
    }

    public void setSpecCode(String specCode){
        this.specCode = specCode == null ? null : specCode.trim();
    }
    public String getSpecColor(){
        return specColor;
    }

    public void setSpecColor(String specColor){
        this.specColor = specColor == null ? null : specColor.trim();
    }
    public String getSpecColorText(){
        return specColorText;
    }

    public void setSpecColorText(String specColorText){
        this.specColorText = specColorText == null ? null : specColorText.trim();
    }
    public String getSpecSize(){
        return specSize;
    }

    public void setSpecSize(String specSize){
        this.specSize = specSize == null ? null : specSize.trim();
    }
    public String getSpecSizeText(){
        return specSizeText;
    }

    public void setSpecSizeText(String specSizeText){
        this.specSizeText = specSizeText == null ? null : specSizeText.trim();
    }
	public Integer getShopNum() {
		return shopNum;
	}
	public void setShopNum(Integer shopNum) {
		this.shopNum = shopNum;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ProductSku other = (ProductSku) obj;
		if (productId == null) {
			if (other.productId != null)
				return false;
		} else if (!productId.equals(other.productId))
			return false;
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
