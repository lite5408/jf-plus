package com.jf.plus.core.order.entity;

import java.util.Date;
import java.util.List;

import org.springframework.util.StringUtils;

import com.alibaba.fastjson.annotation.JSONField;
import com.jf.plus.common.core.Constants;
import com.jf.plus.common.core.enums.ProductSource;

import cn.iutils.common.config.JConfig;
import cn.iutils.sys.entity.DataEntity;

/**
 * 订单主表
 *
 * @author Tng
 * @version 1.0
 */
public class Order extends DataEntity<Order> {

	private static final long serialVersionUID = 1L;

	private String orderNo;// 订单编号
	private Integer orderType;// 订单类型
	private Integer orderFrom;// 订单来源(1集采  2积分)
	private Integer ratio;// 积分比例
	private Long orgId;// 组织id
	private Long siteId;// 站点id
	private Long supplyId;// 供应商id
	private String supplyName;// 供应商名称
	private Integer totalNum;// 商品总数量
	private Double totalAmount;// 商品总金额
	private Double totalPoints;// 积分总价格
	private Double payAmount;// 支付总金额（商品总价+运费-优惠）
	private Double payPoints;// 支付总积分
	private Double paySupply; // 总供应价+运费
	private Long userId;// 用户id
	private String receiver;// 收货人
	private String receiverPhone;// 收货人号码
	private String province;// 省份
	private String city;// 城市
	private String town;// 区县
	private String county;// 镇|街道
	private String address;// 收货人地址
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	private Date cashtime;// 兑换时间
	private Integer operStatus;// 订单状态（0：新建 1：支付成功）
	private Integer source;// 订单来源(1:京东 2供应商）
	private String outTradeNo;// 第三方订单号
	private Double freight;// 运费
	private Integer ptype;// 1 是父订单 2 是子订单
	private Long pid;// 父订单id
	private Date splitDate;// 拆单日期
	private Integer trackState;// 物流状态 0 是新建 1 是妥投 2 是拒收
	private Integer receiptConfirm;// 确认收货
	private Long receiptMember;// 确认收货人
	private Date receiptDate;// 确认收货时间
	private String receiptOpertor;// 确认收货操作人
	private Long packsAccId; // 礼包账户ID

	/**
	 * 自定义属性
	 */
	private String specColorText;//颜色
	private String specSizeText;//尺码
	private String userName;// 用户姓名
	private String userNo;// 用户编号
	private String orgNo;// 机构编码
	private String orgName;// 机构名称
	private Long productNo;// 商品标识
	private String itemCode;// 商品编码
	private String itemName;// 商品名称
	private String photo;// 封面图
	private Integer detailCount;// 商品笔数
	private Double salePrice;// 销售价

	private Date startTime;
	private Date endTime;

	private List<OrderDetail> orderDetailList; // 明细列表

	private Long auditId; // 审批单ID
	private Integer auditStatus; // 审批状态
	private String auditReasons; // 审批事由

	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	private Date validate; // 有效期
	private String taskProcessId;// 工作流id

	private String[] items; // 查询属性

	private Date cashMonth; //兑换月份查询

	private List excludeSupply;//不包含的供应商

	private List excludeCate;//不包含的分类

	private Double rechargeAmount;// 机构实际扣款
	
	private Integer deliveryType;//发货状态
	
	private String brandName;//品牌名称
	
	private String itemCateName;//分类
	private String buyerName;//买手

	public Order() {
		super();
	}

	public Order(String id) {
		super(id);
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public Integer getOrderType() {
		return orderType;
	}

	public void setOrderType(Integer orderType) {
		this.orderType = orderType;
	}

	public Integer getOrderFrom() {
		return orderFrom;
	}

	public void setOrderFrom(Integer orderFrom) {
		this.orderFrom = orderFrom;
	}

	public Long getOrgId() {
		return orgId;
	}

	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}

	public Long getSiteId() {
		return siteId;
	}

	public void setSiteId(Long siteId) {
		this.siteId = siteId;
	}

	public Long getSupplyId() {
		return supplyId;
	}

	public void setSupplyId(Long supplyId) {
		this.supplyId = supplyId;
	}

	public Integer getTotalNum() {
		return totalNum;
	}

	public void setTotalNum(Integer totalNum) {
		this.totalNum = totalNum;
	}

	public Double getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(Double totalAmount) {
		this.totalAmount = totalAmount;
	}

	public Double getTotalPoints() {
		return totalPoints;
	}

	public void setTotalPoints(Double totalPoints) {
		this.totalPoints = totalPoints;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getReceiver() {
		return receiver;
	}

	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}

	public String getReceiverPhone() {
		return receiverPhone;
	}

	public void setReceiverPhone(String receiverPhone) {
		this.receiverPhone = receiverPhone;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getTown() {
		return town;
	}

	public void setTown(String town) {
		this.town = town;
	}

	public String getCounty() {
		return county;
	}

	public void setCounty(String county) {
		this.county = county;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
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

	public String getOutTradeNo() {
		return outTradeNo;
	}

	public void setOutTradeNo(String outTradeNo) {
		this.outTradeNo = outTradeNo;
	}

	public Double getFreight() {
		return freight;
	}

	public void setFreight(Double freight) {
		this.freight = freight;
	}

	public Integer getPtype() {
		return ptype;
	}

	public void setPtype(Integer ptype) {
		this.ptype = ptype;
	}

	public Long getPid() {
		return pid;
	}

	public void setPid(Long pid) {
		this.pid = pid;
	}

	public Date getSplitDate() {
		return splitDate;
	}

	public void setSplitDate(Date splitDate) {
		this.splitDate = splitDate;
	}

	public Integer getTrackState() {
		return trackState;
	}

	public void setTrackState(Integer trackState) {
		this.trackState = trackState;
	}

	public Integer getReceiptConfirm() {
		return receiptConfirm;
	}

	public void setReceiptConfirm(Integer receiptConfirm) {
		this.receiptConfirm = receiptConfirm;
	}

	public Long getReceiptMember() {
		return receiptMember;
	}

	public void setReceiptMember(Long receiptMember) {
		this.receiptMember = receiptMember;
	}

	public Date getReceiptDate() {
		return receiptDate;
	}

	public void setReceiptDate(Date receiptDate) {
		this.receiptDate = receiptDate;
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

	public String getPhoto() {
		if (StringUtils.isEmpty(photo))
			return null;
		ProductSource productSource = ProductSource.getByType(getSource());
		switch (productSource) {
		case JD:
			return Constants.JD_URL_IMAGE + "/" + photo;
		case QX:
			return photo;
		case SUPPLY:{
			String[] photos = photo.split(",");
			StringBuffer photosUrl = new StringBuffer();
			photosUrl.append(JConfig.getConfig("url.image") + "/" + photos[0]);
			
			return photosUrl.toString();
		}
		default:
			return photo;
		}
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public Integer getOperStatus() {
		return operStatus;
	}

	public void setOperStatus(Integer operStatus) {
		this.operStatus = operStatus;
	}

	public String getSupplyName() {
		return supplyName;
	}

	public void setSupplyName(String supplyName) {
		this.supplyName = supplyName;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public List<OrderDetail> getOrderDetailList() {
		return orderDetailList;
	}

	public void setOrderDetailList(List<OrderDetail> orderDetailList) {
		this.orderDetailList = orderDetailList;
	}

	public Long getAuditId() {
		return auditId;
	}

	public void setAuditId(Long auditId) {
		this.auditId = auditId;
	}

	public Integer getAuditStatus() {
		return auditStatus;
	}

	public void setAuditStatus(Integer auditStatus) {
		this.auditStatus = auditStatus;
	}

	public String getAuditReasons() {
		return auditReasons;
	}

	public void setAuditReasons(String auditReasons) {
		this.auditReasons = auditReasons;
	}

	public Date getValidate() {
		return validate;
	}

	public void setValidate(Date validate) {
		this.validate = validate;
	}

	public String getTaskProcessId() {
		return taskProcessId;
	}

	public void setTaskProcessId(String taskProcessId) {
		this.taskProcessId = taskProcessId;
	}

	public Integer getDetailCount() {
		return detailCount;
	}

	public void setDetailCount(Integer detailCount) {
		this.detailCount = detailCount;
	}

	public String getUserNo() {
		return userNo;
	}

	public void setUserNo(String userNo) {
		this.userNo = userNo;
	}

	public String[] getItems() {
		return items;
	}

	public void setItems(String[] items) {
		this.items = items;
	}

	public String getReceiptOpertor() {
		return receiptOpertor;
	}

	public void setReceiptOpertor(String receiptOpertor) {
		this.receiptOpertor = receiptOpertor;
	}

	public Double getPayAmount() {
		return payAmount;
	}

	public void setPayAmount(Double payAmount) {
		this.payAmount = payAmount;
	}

	public Double getPayPoints() {
		return payPoints;
	}

	public void setPayPoints(Double payPoints) {
		this.payPoints = payPoints;
	}

	public String getOrgNo() {
		return orgNo;
	}

	public void setOrgNo(String orgNo) {
		this.orgNo = orgNo;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public Double getSalePrice() {
		return salePrice;
	}

	public void setSalePrice(Double salePrice) {
		this.salePrice = salePrice;
	}

	public Integer getRatio() {
		return ratio;
	}

	public void setRatio(Integer ratio) {
		this.ratio = ratio;
	}

	public Long getPacksAccId() {
		return packsAccId;
	}

	public void setPacksAccId(Long packsAccId) {
		this.packsAccId = packsAccId;
	}
	public Date getCashMonth() {
		return cashMonth;
	}

	public void setCashMonth(Date cashMonth) {
		this.cashMonth = cashMonth;
	}

	public List getExcludeSupply() {
		return excludeSupply;
	}

	public void setExcludeSupply(List excludeSupply) {
		this.excludeSupply = excludeSupply;
	}

	public List getExcludeCate() {
		return excludeCate;
	}

	public void setExcludeCate(List excludeCate) {
		this.excludeCate = excludeCate;
	}

	public Double getPaySupply() {
		return paySupply;
	}

	public void setPaySupply(Double paySupply) {
		this.paySupply = paySupply;
	}

	public Double getRechargeAmount() {
		return rechargeAmount;
	}

	public void setRechargeAmount(Double rechargeAmount) {
		this.rechargeAmount = rechargeAmount;
	}

	public Integer getDeliveryType() {
		return deliveryType;
	}

	public void setDeliveryType(Integer deliveryType) {
		this.deliveryType = deliveryType;
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

	public String getBrandName() {
		return brandName;
	}

	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}

	public String getItemCateName() {
		return itemCateName;
	}

	public void setItemCateName(String itemCateName) {
		this.itemCateName = itemCateName;
	}

	public String getBuyerName() {
		return buyerName;
	}

	public void setBuyerName(String buyerName) {
		this.buyerName = buyerName;
	}
	
	
}
