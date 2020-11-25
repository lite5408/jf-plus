
package com.jf.plus.common.core.enums;

public enum MethodValue {

	YANXUAN_CALLBACK_METHOD_REGISTER("yanxuan.callback.method.register","渠道自助注册回调"),
	YANXUAN_CALLBACK_METHOD_LIST("yanxuan.callback.method.list","获取渠道已注册的方法名回调"),
	YANXUAN_CALLBACK_ORDER_CANCEL("yanxuan.callback.order.cancel","订单取消回调"),
	YANXUAN_NOTIFICATION_ORDER_DELIVERED("yanxuan.notification.order.delivered","订单包裹物流绑单回调"),
	YANXUAN_NOTIFICATION_ORDER_EXCEPTIONAL("yanxuan.notification.order.exceptional","订单异常回调"),
	YANXUAN_NOTIFICATION_INVENTORY_COUNT_CHANGED("yanxuan.notification.inventory.count.changed","渠道SKU库存划拨回调"),
	YANXUAN_NOTIFICATION_SKU_ALARM_CLOSE("yanxuan.notification.sku.alarm.close","渠道SKU低库存预警通知回调"),
	YANXUAN_NOTIFICATION_SKU_CLOSE("yanxuan.notification.sku.close","渠道SKU停售通知回调"),
	YANXUAN_NOTIFICATION_SKU_REOPEN("yanxuan.notification.sku.reopen","渠道SKU再次开售通知回调"),
	YANXUAN_NOTIFICATION_INVENTORY_COUNT_CHECK("yanxuan.notification.inventory.count.check","渠道SKU库存校准回调"),
	YANXUAN_NOTIFICATION_ORDER_REFUND_ADDRESS("yanxuan.notification.order.refund.address","退货地址回调"),
	YANXUAN_NOTIFICATION_ORDER_REFUND_REJECT("yanxuan.notification.order.refund.reject","严选拒绝退货回调"),
	YANXUAN_NOTIFICATION_ORDER_REFUND_EXPRESS_CONFIRM("yanxuan.notification.order.refund.express.confirm","退货包裹确认收货回调"),
	YANXUAN_NOTIFICATION_ORDER_REFUND_SYSTEM_CANCEL("yanxuan.notification.order.refund.system.cancel","严选系统取消退货回调"),
	YANXUAN_NOTIFICATION_ORDER_REFUND_RESULT("yanxuan.notification.order.refund.result","退款结果回调");
	
	private String type;
	private String description;

	public String getType()
	{
		return type;
	}

	public void setType(String type)
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

	private MethodValue(String type, String description)
	{
		this.type = type;
		this.description = description;
	}

	public static MethodValue getByType(String type)
	{
		for(MethodValue at:MethodValue.values()){
			String type2 = at.type;
			if(at.type.equals(type)){
				return at;
			}
		}
		throw new IllegalArgumentException("Not supported client type:" + type);
	}
}
