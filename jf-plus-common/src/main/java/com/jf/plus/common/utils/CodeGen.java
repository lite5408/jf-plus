package com.jf.plus.common.utils;

import java.util.Date;

import org.apache.commons.lang3.RandomUtils;

import cn.iutils.common.utils.DateUtils;

public class CodeGen {
	static String merchant_pattern = "000000";

	static java.text.DecimalFormat merchantFormat = new java.text.DecimalFormat(merchant_pattern);
	
	
	/*
	 * 会员编码
	 */
	public static String getMemberCode(){
		String date = DateUtils.getDate("yyyyMMddssSSS");
		String threeRandom = String.valueOf(RandomUtils.nextInt(100, 999));
		return new StringBuffer().append("M")
				.append(date).append(threeRandom).toString();
	}
	
	/*
	 * 生成订单号
	 */
	public static String getOrderNo(){
		String date = DateUtils.getDate("yyyyMMddhhmmSSS");
		String threeRandom = String.valueOf(RandomUtils.nextInt(10000, 99999));
		return new StringBuffer().append("DD")
				.append(date).append(threeRandom).toString();
	}
	
	/*
	 * 供应商订单号
	 */
	public static String getOrderNoBySupplyer(){
		String date = DateUtils.getDate("yyyyMMddhhmmss");
		String threeRandom = String.valueOf(RandomUtils.nextInt(10000, 99999));
		return new StringBuffer().append("DS")
				.append(date).append(threeRandom).toString();
	}
	
	/*
	 * 供应商订单号
	 */
	public static String getUserNo(String userId){
		String no = "00000000".substring(userId.length()) + userId;
		return "M" + DateUtils.getYear() + no;
	}
	
	public static boolean isJdOrder(String orderNo){
		if(orderNo == null)
			return false;
		if(orderNo.startsWith("DD"))
			return true;
		
		return false;
	}
	
	/*
	 * 生成子订单号
	 */
	public static String getOrderSubNo(){
		String date = DateUtils.getDate("yyyyMMddhhmmss");
		String threeRandom = String.valueOf(RandomUtils.nextInt(10000, 99999));
		return new StringBuffer().append("SD")
				.append(date).append(threeRandom).toString();
	}

	/*
	 * 密码
	 */
	public static String randPassword(){
		String random = String.valueOf(RandomUtils.nextInt(1000, 9999));
		return random;
	}
	
	/*
	 * 注册验证码
	 */
	public static String getSmsRegCode(){
		String random = String.valueOf(RandomUtils.nextInt(1000, 9999));
		return random;
	}
	
	/*
	 * 生成套餐购买记录流水单号
	 */
	public static String getPackagesRecordNo(){
		String date = DateUtils.getDate("yyyyMMddhhmmss");
		String threeRandom = String.valueOf(RandomUtils.nextInt(10000, 99999));
		return new StringBuffer().append("DP")
				.append(date).append(threeRandom).toString();
	}

	
	/**
	 * 格式化商户卡号
	 * @param merchantCode
	 * @return
	 */
	public static String formatMerchantCode(int merchantCode){
		return merchantFormat.format(merchantCode);
	}

	/**
	 * 编码左侧补0
	 * @param str
	 * @param strLength
	 * @return
	 */
	public static String addZeroForNum(String str, int strLength) {
	    int strLen = str.length();
	    StringBuffer sb = null;
	     while (strLen < strLength) {
	           sb = new StringBuffer();
	           sb.append("0").append(str);// 左补0
	           str = sb.toString();
	           strLen = str.length();
	     }
	    return str;
	}
	
	/*
	 * 生成订单号
	 */
	public static String getDistCashApplyNo(){
		String date = DateUtils.getDate("yyyyMMddhhmmss");
		String threeRandom = String.valueOf(RandomUtils.nextInt(10000, 99999));
		return new StringBuffer().append("TX")
				.append(date).append(threeRandom).toString();
	}
	
	/**
	 * 生成批次号
	 * @return
	 */
	public static String getBatchNo(){
		String batchNo = DateUtils.formatDate(new Date(), "yyMMddHHmmSSS") + RandomUtils.nextInt(100, 999);
		return batchNo;
	}

}
