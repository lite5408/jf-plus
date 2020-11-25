package com.jf.plus.core.order.entity;

import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.jf.plus.common.core.Constants;

import cn.iutils.sys.entity.DataEntity;

/**
 * 订单发货商品表
 * 
 * @author Tng
 * @version 1.0
 */
public class OrderDeliveryDetail extends DataEntity<OrderDeliveryDetail> {

	private static final long serialVersionUID = 1L;

	private String orderNo;// 订单编号
	private Long deliveryId;// 订单发货id
	private String itemCode;// 商品编码
	private String itemName;// 商品名称
	private Integer distNum;//配货数量
	private Integer deliveryNum;// 发货数量

	/**
	 * 自定义属性
	 */
	private String photoUrl;// 商品图片
	private Double salePrice;// 销售价格
	private String buyerName;// 买手名称
	private String buyerId;// 买手id
	private String saleType;// 类型
	private String ckNo;// 包裹号
	private String specColorText;//颜色
	private String specSizeText;//尺码

	private List<OrderDetailSku> orderDetailSkus;// 商品SKU列表
	private List<OrderDeliveryDetailSku> orderDeliveryDetailSkus;// 商品SKU列表

	public OrderDeliveryDetail() {
		super();
	}

	public OrderDeliveryDetail(String id) {
		super(id);
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo == null ? null : orderNo.trim();
	}

	public Long getDeliveryId() {
		return deliveryId;
	}

	public void setDeliveryId(Long deliveryId) {
		this.deliveryId = deliveryId;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName == null ? null : itemName.trim();
	}

	public Integer getDeliveryNum() {
		return deliveryNum;
	}

	public void setDeliveryNum(Integer deliveryNum) {
		this.deliveryNum = deliveryNum;
	}

	public String getPhotoUrl() {
		if(StringUtils.isNotBlank(photoUrl)){
			return Constants.URL_IMAGE + "/" + photoUrl.split(",")[0];
		}
		return photoUrl;
	}

	public void setPhotoUrl(String photoUrl) {
		this.photoUrl = photoUrl;
	}

	public String getItemCode() {
		return itemCode;
	}

	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}

	public Double getSalePrice() {
		return salePrice;
	}

	public void setSalePrice(Double salePrice) {
		this.salePrice = salePrice;
	}

	public String getBuyerName() {
		return buyerName;
	}

	public void setBuyerName(String buyerName) {
		this.buyerName = buyerName;
	}

	public String getBuyerId() {
		return buyerId;
	}

	public void setBuyerId(String buyerId) {
		this.buyerId = buyerId;
	}

	public String getSaleType() {
		return saleType;
	}

	public void setSaleType(String saleType) {
		this.saleType = saleType;
	}

	public List<OrderDetailSku> getOrderDetailSkus() {
		return orderDetailSkus;
	}

	public void setOrderDetailSkus(List<OrderDetailSku> orderDetailSkus) {
		this.orderDetailSkus = orderDetailSkus;
	}

	public String getCkNo() {
		return ckNo;
	}

	public void setCkNo(String ckNo) {
		this.ckNo = ckNo;
	}

	public List<OrderDeliveryDetailSku> getOrderDeliveryDetailSkus() {
		return orderDeliveryDetailSkus;
	}

	public void setOrderDeliveryDetailSkus(List<OrderDeliveryDetailSku> orderDeliveryDetailSkus) {
		this.orderDeliveryDetailSkus = orderDeliveryDetailSkus;
	}

	public String getSpecColorText() {
		return specColorText;
	}

	public void setSpecColorText(String specColorText) {
		this.specColorText = specColorText;
	}

	public String getSpecSizeText() {
		return specSizeText;
	}

	public void setSpecSizeText(String specSizeText) {
		this.specSizeText = specSizeText;
	}

	public Integer getDistNum() {
		return distNum;
	}

	public void setDistNum(Integer distNum) {
		this.distNum = distNum;
	}
	
	
	
	

}
