package com.jf.plus.core.order.entity;

import java.util.Date;
import java.util.List;

import org.springframework.util.StringUtils;

import com.jf.plus.common.core.Constants;
import com.jf.plus.common.core.enums.ProductSource;

import cn.iutils.common.config.JConfig;
import cn.iutils.sys.entity.DataEntity;

/**
 * 订单明细表
 *
 * @author Tng
 * @version 1.0
 */
public class OrderDetail extends DataEntity<OrderDetail> {

	private static final long serialVersionUID = 1L;

	private Long orderId;// 订单ID
	private String orderNo;// 订单号
	private String orderSubno;// 订单子单号
	private Long itemId;// 站点商品id
	private String itemName;// 商品名称
	private Long supplyId;// 供应商id
	private Double supplyPrice;// 供应价
	private Double salePrice;// 销售价
	private Integer saleNum;// 销售数量
	private Double amount;// 销售金额
	private Double salePoints;// 销售积分价
	private Integer freezeNum;// 冻结库存量
	private Integer operStatus;// 状态
	private Long cancelUserId;//取消用户
	private String cancelOperator;//取消订单操作人
	private Date cancelDate;//取消时间 
	private String isDist;//是否分配
	private Integer distStock;//分配总库存
	private Integer distNum;//实际分配数量
	private String distOperator;//分配人
	private Date distDate;//分配时间
	private Integer trackState;//发货状态
	private String batchNo;//分配批次号
	private String distOrderNo;//分配的订单号

	/** 自定义属性 **/
	private Long productNo;// 商品标识
	private String productPic;// 商品图片
	private Date cashtime;// 下单时间
	private Integer source; // 来源
	private String supplyName; // 供应商名称
	private List<OrderDetailSku> orderDetailSkus;//商品SKU列表
	private Integer targetOperStatus;//更新状态
	private Integer toTrackNum;//未发货的数量
	private String itemCode;//商品编码
	private String buyerId;//买手ID
	private String buyerName;//买手名称
	private String saleType;//销售类型
	private String orgName;//机构名称
	private Long orgId;//机构ID
	private Integer pOperStatus;//商品状态
	private Integer toDistNum;//待分配数量
	private String brandName;//品牌名称
	private String shelvesDate;//发布时间
	private String shelvesDateRange;//发布时间筛选范围
	private String userName;//下单用户
	
	

	public OrderDetail() {
		super();
	}

	public OrderDetail(String id) {
		super(id);
	}

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getOrderSubno() {
		return orderSubno;
	}

	public void setOrderSubno(String orderSubno) {
		this.orderSubno = orderSubno;
	}

	public Long getItemId() {
		return itemId;
	}

	public void setItemId(Long itemId) {
		this.itemId = itemId;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public Long getSupplyId() {
		return supplyId;
	}

	public void setSupplyId(Long supplyId) {
		this.supplyId = supplyId;
	}

	public Double getSupplyPrice() {
		return supplyPrice;
	}

	public void setSupplyPrice(Double supplyPrice) {
		this.supplyPrice = supplyPrice;
	}

	public Double getSalePrice() {
		return salePrice;
	}

	public void setSalePrice(Double salePrice) {
		this.salePrice = salePrice;
	}

	public Integer getSaleNum() {
		return saleNum;
	}

	public void setSaleNum(Integer saleNum) {
		this.saleNum = saleNum;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public Double getSalePoints() {
		return salePoints;
	}

	public void setSalePoints(Double salePoints) {
		this.salePoints = salePoints;
	}

	public Integer getFreezeNum() {
		return freezeNum;
	}

	public void setFreezeNum(Integer freezeNum) {
		this.freezeNum = freezeNum;
	}

	public Long getProductNo() {
		return productNo;
	}

	public void setProductNo(Long productNo) {
		this.productNo = productNo;
	}

	public String getProductPic() {
		if (StringUtils.isEmpty(productPic))
			return null;
		ProductSource productSource = ProductSource.getByType(getSource());
		switch (productSource) {
		case JD:
			return Constants.JD_URL_IMAGE + "/" + productPic;
		case QX:
			return productPic;
		case SUPPLY:{
			String[] photos = productPic.split(",");
			StringBuffer photosUrl = new StringBuffer();
			for(String s : photos){
				photosUrl.append(JConfig.getConfig("url.image") + "/" + s).append(",");
			}
			return photosUrl.substring(0, photosUrl.length() - 1);
		}
		default:
			return productPic;
		}
	}

	public void setProductPic(String productPic) {
		this.productPic = productPic;
	}

	public Date getCashtime() {
		return cashtime;
	}

	public void setCashtime(Date cashtime) {
		this.cashtime = cashtime;
	}

	public Integer getSource() {
		return source;
	}

	public void setSource(Integer source) {
		this.source = source;
	}

	public String getSupplyName() {
		return supplyName;
	}

	public void setSupplyName(String supplyName) {
		this.supplyName = supplyName;
	}

	public List<OrderDetailSku> getOrderDetailSkus() {
		return orderDetailSkus;
	}

	public void setOrderDetailSkus(List<OrderDetailSku> orderDetailSkus) {
		this.orderDetailSkus = orderDetailSkus;
	}

	public Integer getOperStatus() {
		return operStatus;
	}

	public void setOperStatus(Integer operStatus) {
		this.operStatus = operStatus;
	}

	public Long getCancelUserId() {
		return cancelUserId;
	}

	public void setCancelUserId(Long cancelUserId) {
		this.cancelUserId = cancelUserId;
	}

	public String getCancelOperator() {
		return cancelOperator;
	}

	public void setCancelOperator(String cancelOperator) {
		this.cancelOperator = cancelOperator;
	}

	public Date getCancelDate() {
		return cancelDate;
	}

	public void setCancelDate(Date cancelDate) {
		this.cancelDate = cancelDate;
	}

	public Integer getTargetOperStatus() {
		return targetOperStatus;
	}

	public void setTargetOperStatus(Integer targetOperStatus) {
		this.targetOperStatus = targetOperStatus;
	}

	public Integer getToTrackNum() {
		return toTrackNum;
	}

	public void setToTrackNum(Integer toTrackNum) {
		this.toTrackNum = toTrackNum;
	}

	public String getItemCode() {
		return itemCode;
	}

	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}

	public String getBuyerId() {
		return buyerId;
	}

	public void setBuyerId(String buyerId) {
		this.buyerId = buyerId;
	}

	public String getBuyerName() {
		return buyerName;
	}

	public void setBuyerName(String buyerName) {
		this.buyerName = buyerName;
	}

	public String getSaleType() {
		return saleType;
	}

	public void setSaleType(String saleType) {
		this.saleType = saleType;
	}

	public String getIsDist() {
		return isDist;
	}

	public void setIsDist(String isDist) {
		this.isDist = isDist;
	}

	public Integer getDistStock() {
		return distStock;
	}

	public void setDistStock(Integer distStock) {
		this.distStock = distStock;
	}

	public Integer getDistNum() {
		return distNum;
	}

	public void setDistNum(Integer distNum) {
		this.distNum = distNum;
	}

	public String getDistOperator() {
		return distOperator;
	}

	public void setDistOperator(String distOperator) {
		this.distOperator = distOperator;
	}

	public Date getDistDate() {
		return distDate;
	}

	public void setDistDate(Date distDate) {
		this.distDate = distDate;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public Long getOrgId() {
		return orgId;
	}

	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}

	public Integer getTrackState() {
		return trackState;
	}

	public void setTrackState(Integer trackState) {
		this.trackState = trackState;
	}

	public Integer getpOperStatus() {
		return pOperStatus;
	}

	public void setpOperStatus(Integer pOperStatus) {
		this.pOperStatus = pOperStatus;
	}

	public String getBatchNo() {
		return batchNo;
	}

	public void setBatchNo(String batchNo) {
		this.batchNo = batchNo;
	}

	public Integer getToDistNum() {
		return toDistNum;
	}

	public void setToDistNum(Integer toDistNum) {
		this.toDistNum = toDistNum;
	}

	public String getDistOrderNo() {
		return distOrderNo;
	}

	public void setDistOrderNo(String distOrderNo) {
		this.distOrderNo = distOrderNo;
	}

	public String getBrandName() {
		return brandName;
	}

	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}

	public String getShelvesDate() {
		return shelvesDate;
	}

	public void setShelvesDate(String shelvesDate) {
		this.shelvesDate = shelvesDate;
	}

	public String getShelvesDateRange() {
		return shelvesDateRange;
	}

	public void setShelvesDateRange(String shelvesDateRange) {
		this.shelvesDateRange = shelvesDateRange;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	

}
