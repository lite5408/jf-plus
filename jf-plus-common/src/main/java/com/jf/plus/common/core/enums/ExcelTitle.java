package com.jf.plus.common.core.enums;

/**
 * @ClassName: ExcelTitle
 * @Description: EXCEL列表头类型
 * @author lh
 * @date 2016年4月17日 下午14:27:12
 * 
 */

public enum ExcelTitle {
	Order(1, "订单ID,订单号,购买人,收货人,总数量,总金额,兑换时间,地址,备注,状态"),
	SiteOrder(5, "订单ID,订单号,供应商,商品,数量,订单总额,供应价,销售价,兑换时间,备注,状态,购买人手机号,收货人,收货人手机号,收货地址,京东订单号,支付方式"),
	SupplyerOrder(6, "订单ID,订单号,站点,商品,数量,供应价,收货人,收货人手机号,收货地址,兑换时间,备注,状态"),
	OrderTrans(2, "订单编号,相关用户,订单类型,商品类型,商品,金额,时间"),
	PacksAccCash(3, "礼包券卡号,礼包券兑换码,已绑定用户,已绑定订单,是否已兑换,最新绑定时间,状态"),
	VoucherAccCash(4, "电子券卡号,电子券兑换码,余额,已绑定用户,已绑定订单,最新绑定时间"),
	OrderBatch(7,"收货人姓名,省,市,区,街道,详细地址,收货人手机,商品编号,商品名称,商品数量,商品订单渠道,失败原因"),
	OrderBatchModel(8,"收货人姓名,省,市,区,街道,详细地址,收货人手机,商品编号,商品名称,商品数量"),
	FinanceOrder(9,"系统订单号,金额,渠道单号,匹配状态,匹配描述"),
	FinanceOrder1(10,"系统订单号,订单人,机构,金额,采购事由,渠道单号,供应商,结算时间");
	
	private int type;
	private String description;

	public int getType()
	{
		return type;
	}

	public void setType(int type)
	{
		this.type = type;
	}

	public String getDescription()
	{
		return description;
	}

	public void setDescription(String description)
	{
		this.description = description;
	}

	private ExcelTitle(int type, String description)
	{
		this.type = type;
		this.description = description;
	}
	
	public static ExcelTitle getByType(int type)
	{
		for(ExcelTitle at:ExcelTitle.values()){
			if(at.type==type){
				return at;
			}
		}
		throw new IllegalArgumentException("Not supported client type:" + type); 
	}
}
