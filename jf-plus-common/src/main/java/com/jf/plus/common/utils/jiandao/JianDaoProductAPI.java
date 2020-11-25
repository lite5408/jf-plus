package com.jf.plus.common.utils.jiandao;

import java.util.HashMap;
import java.util.Map;

public class JianDaoProductAPI {
	
	private final static String appId = "5a367ad994de5368331b1bd0";
	private final static String entryId = "5b32fb7658bc5170f29c297a";
	

	public static Map<String, Object> create(final String itemCode,final String shelvesDate,final String supplyName,
			final String itemName,final int saleNum, final String specColor, final String specSize, final String createDate,final String buyer) {
		JianDaoApiUtils.APIUtils api = new JianDaoApiUtils.APIUtils(appId, entryId);
		// 创建单条数据
		Map<String, Object> create = new HashMap<String, Object>(){
			{
				// 单行文本
				put("_widget_1530067828209", new HashMap<String, Object>() {
					{
						put("value", itemCode);
					}
				});
				put("_widget_1530067985065", new HashMap<String, Object>() {
					{
//						put("value", "2018-01-01T10:10:10.000Z");
						put("value", shelvesDate);
					}
				});
				put("_widget_1530067828181", new HashMap<String, Object>() {
					{
						put("value", supplyName);
					}
				});
				put("_widget_1530067828195", new HashMap<String, Object>() {
					{
						put("value", itemName);
					}
				});
				put("_widget_1530067985045", new HashMap<String, Object>() {
					{
						put("value", saleNum);
					}
				});
				put("_widget_1534239366449", new HashMap<String, Object>() {
					{
						put("value", specColor);
					}
				});
				put("_widget_1534239366465", new HashMap<String, Object>() {
					{
						put("value", specSize);
					}
				});
				put("_widget_1530067985145", new HashMap<String, Object>() {
					{
						put("value", createDate);
					}
				});
				put("_widget_1530076667106", new HashMap<String, Object>() {
					{
						put("value", buyer);
					}
				});
			}
		};
		Map<String, Object> createData = api.createData(create);
		return createData;
	}
}
