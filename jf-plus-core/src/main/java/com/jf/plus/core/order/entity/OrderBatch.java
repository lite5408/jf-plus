package com.jf.plus.core.order.entity;

import java.util.Date;

import cn.iutils.sys.entity.DataEntity;

/**
* 
* @author Tng
* @version 1.0
*/
public class OrderBatch extends DataEntity<OrderBatch>{

    private static final long serialVersionUID = 1L;

    private String receiveName;//收件人姓名
    private String provinceName;//省份名称
    private String province;//省份编码
    private String cityName;//城市名称
    private String city;//城市编码
    private String countyName;//县区名称
    private String county;//县区编码
    private String townName;//街道名称
    private String town;//街道编码
    private String address;//收件人详细地址
    private String mobile;//收件人手机号
    private String itemCode;//商品编码（sku）
    private String itemName;//商品名称
    private Integer itemNum;//商品数量
    private String outTradeNo;//第三方订单号
    private String operStatus;//订单状态 0待下单;1下单完成;2下单失败
    private Date confirmDate;//下单时间
    private Integer source;//渠道来源
    private Integer supplyId;//供应商id
    private String batchNo;//批次号
    private String failureReason;//下单失败原因
    private String tradePerson;//下单人（工号_姓名）
    
    /**
     * 自定义属性
     */
    private String supplyName;
    

    public OrderBatch() {
        super();
    }
    public OrderBatch(String id){
        super(id);
    }

    public String getReceiveName(){
        return receiveName;
    }

    public void setReceiveName(String receiveName){
        this.receiveName = receiveName == null ? null : receiveName.trim();
    }
    public String getProvinceName(){
        return provinceName;
    }

    public void setProvinceName(String provinceName){
        this.provinceName = provinceName == null ? null : provinceName.trim();
    }
    public String getProvince(){
        return province;
    }

    public void setProvince(String province){
        this.province = province == null ? null : province.trim();
    }
    public String getCityName(){
        return cityName;
    }

    public void setCityName(String cityName){
        this.cityName = cityName == null ? null : cityName.trim();
    }
    public String getCity(){
        return city;
    }

    public void setCity(String city){
        this.city = city == null ? null : city.trim();
    }
    public String getCountyName(){
        return countyName;
    }

    public void setCountyName(String countyName){
        this.countyName = countyName == null ? null : countyName.trim();
    }
    public String getCounty(){
        return county;
    }

    public void setCounty(String county){
        this.county = county == null ? null : county.trim();
    }
    public String getTownName(){
        return townName;
    }

    public void setTownName(String townName){
        this.townName = townName == null ? null : townName.trim();
    }
    public String getTown(){
        return town;
    }

    public void setTown(String town){
        this.town = town == null ? null : town.trim();
    }
    public String getAddress(){
        return address;
    }

    public void setAddress(String address){
        this.address = address == null ? null : address.trim();
    }
    public String getMobile(){
        return mobile;
    }

    public void setMobile(String mobile){
        this.mobile = mobile == null ? null : mobile.trim();
    }
    public String getItemCode(){
        return itemCode;
    }

    public void setItemCode(String itemCode){
        this.itemCode = itemCode == null ? null : itemCode.trim();
    }
    public String getItemName(){
        return itemName;
    }

    public void setItemName(String itemName){
        this.itemName = itemName == null ? null : itemName.trim();
    }
    public Integer getItemNum(){
        return itemNum;
    }

    public void setItemNum(Integer itemNum){
        this.itemNum = itemNum;
    }
    public String getOutTradeNo(){
        return outTradeNo;
    }

    public void setOutTradeNo(String outTradeNo){
        this.outTradeNo = outTradeNo == null ? null : outTradeNo.trim();
    }
    public String getOperStatus(){
        return operStatus;
    }

    public void setOperStatus(String operStatus){
        this.operStatus = operStatus == null ? null : operStatus.trim();
    }
    public Date getConfirmDate(){
        return confirmDate;
    }

    public void setConfirmDate(Date confirmDate){
        this.confirmDate = confirmDate;
    }
    public Integer getSource(){
        return source;
    }

    public void setSource(Integer source){
        this.source = source;
    }
	public String getBatchNo() {
		return batchNo;
	}
	public void setBatchNo(String batchNo) {
		this.batchNo = batchNo;
	}
	public String getFailureReason() {
		return failureReason;
	}
	public void setFailureReason(String failureReason) {
		this.failureReason = failureReason;
	}
	public String getTradePerson() {
		return tradePerson;
	}
	public void setTradePerson(String tradePerson) {
		this.tradePerson = tradePerson;
	}
	public Integer getSupplyId() {
		return supplyId;
	}
	public void setSupplyId(Integer supplyId) {
		this.supplyId = supplyId;
	}
	public String getSupplyName() {
		return supplyName;
	}
	public void setSupplyName(String supplyName) {
		this.supplyName = supplyName;
	}
	
    
}
