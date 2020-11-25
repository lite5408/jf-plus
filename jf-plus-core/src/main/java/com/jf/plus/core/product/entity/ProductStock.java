package com.jf.plus.core.product.entity;

import cn.iutils.sys.entity.DataEntity;

/**
* 商品库存表
* @author Tng
* @version 1.0
*/
public class ProductStock extends DataEntity<ProductStock>{

    private static final long serialVersionUID = 1L;

    private Long productNo;//
    private String itemCode;//商品编码
    private Integer stockNum;//库存
    private Integer distStock;//回货总库存

    public ProductStock() {
        super();
    }
    public ProductStock(String id){
        super(id);
    }

    public Long getProductNo(){
        return productNo;
    }

    public void setProductNo(Long productNo){
        this.productNo = productNo;
    }
    public String getItemCode(){
        return itemCode;
    }

    public void setItemCode(String itemCode){
        this.itemCode = itemCode;
    }
    public Integer getStockNum(){
        return stockNum;
    }

    public void setStockNum(Integer stockNum){
        this.stockNum = stockNum;
    }
	public Integer getDistStock() {
		return distStock;
	}
	public void setDistStock(Integer distStock) {
		this.distStock = distStock;
	}
    
    
}
