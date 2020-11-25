package com.jf.plus.core.product.entity;

import cn.iutils.sys.entity.DataEntity;

/**
 * 商品详情表
 * 
 * @author Tng
 * @version 1.0
 */
public class ProductDetail extends DataEntity<ProductDetail> {

	private static final long serialVersionUID = 1L;

	private Long productNo;// 商品标识
	private String itemCode;// 商品编码
	private String itemName;// 商品名称
	private String content;// 商品详情
	/**
	 * 自定义属性
	 */
	private Long siteProductId;

	public ProductDetail() {
		super();
	}

	public ProductDetail(String id) {
		super(id);
	}

	public Long getProductNo() {
		return productNo;
	}

	public void setProductNo(Long productNo) {
		this.productNo = productNo;
	}

	public String getItemCode() {
		return itemCode;
	}

	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Long getSiteProductId() {
		return siteProductId;
	}

	public void setSiteProductId(Long siteProductId) {
		this.siteProductId = siteProductId;
	}
	

}
