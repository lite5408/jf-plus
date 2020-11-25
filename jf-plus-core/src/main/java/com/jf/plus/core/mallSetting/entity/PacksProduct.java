package com.jf.plus.core.mallSetting.entity;

import org.springframework.util.StringUtils;

import com.jf.plus.common.core.Constants;
import com.jf.plus.common.core.enums.ProductSource;

import cn.iutils.common.config.JConfig;
import cn.iutils.sys.entity.DataEntity;

/**
 * 礼包商品表
 *
 * @author Tng
 * @version 1.0
 */
public class PacksProduct extends DataEntity<PacksProduct> {

	private static final long serialVersionUID = 1L;

	private Long packsId;// 礼包ID
	private Long itemId;// 站点商品ID
	private Integer itemSource;//站点商品来源
	private Integer sort;//排序

	/**
	 * 自定义属性
	 */
	private String productName;
	private String productPhoto;
	private Long supplyId;
	private String productPhotoUrl;

	public PacksProduct() {
		super();
	}

	public PacksProduct(String id) {
		super(id);
	}

	public Long getPacksId() {
		return packsId;
	}

	public void setPacksId(Long packsId) {
		this.packsId = packsId;
	}

	public Long getItemId() {
		return itemId;
	}

	public void setItemId(Long itemId) {
		this.itemId = itemId;
	}

	public Integer getItemSource() {
		return itemSource;
	}

	public void setItemSource(Integer itemSource) {
		this.itemSource = itemSource;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getProductPhoto() {
		return productPhoto;
	}

	public void setProductPhoto(String productPhoto) {
		this.productPhoto = productPhoto;
	}

	public String getProductPhotoUrl() {
		if (StringUtils.isEmpty(getProductPhoto()))
			return null;

		ProductSource productSource = ProductSource.getByType(getItemSource());

		switch (productSource) {
		case JD:
			return Constants.JD_URL_IMAGE + "/" + getProductPhoto();
		case QX:
			return getProductPhoto();
		case SN:
			return getProductPhoto();
		case SUPPLY:
			return JConfig.getConfig("url.image") + "/" + getProductPhoto();
		default:
			return getProductPhoto();
		}
	}

	public void setProductPhotoUrl(String productPhotoUrl) {
		this.productPhotoUrl = productPhotoUrl;
	}

	public Long getSupplyId() {
		return supplyId;
	}

	public void setSupplyId(Long supplyId) {
		this.supplyId = supplyId;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}
	
}
