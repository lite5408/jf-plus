package com.jf.plus.common.utils;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.apache.commons.lang3.RandomUtils;

import com.alibaba.fastjson.JSON;

public class CardGen {

	static String pattern = "00000000";

	static java.text.DecimalFormat accountFormat = new java.text.DecimalFormat(pattern);

	/*
	 * 卡号
	 */
	public static String getCardCode(String prefix) {
		String threeRandom = String.valueOf(RandomUtils.nextInt(100000, 999999));

		return new StringBuffer().append(prefix.toUpperCase()).append(threeRandom).toString();
	}

	/*
	 * 密码 8位纯数字
	 */
	public static String getCardPwd() {
		String threeRandom = String.valueOf(RandomUtils.nextInt(10000000, 99999999));

		return threeRandom;
	}

	/*
	 * 密码(字母数字组合)
	 */
	public static String getCardPwd(int length) {
		String val = "";

		Random random = new Random();
		for (int i = 0; i < length; i++) {
			String charOrNum = random.nextInt(2) % 2 == 0 ? "char" : "num"; // 输出字母还是数字

			if ("char".equalsIgnoreCase(charOrNum)) // 字符串
			{
				// int choice = random.nextInt(2) % 2 == 0 ? 65 : 97;
				// //取得大写字母还是小写字母
				int choice = 97;
				val += (char) (choice + random.nextInt(26));
			} else if ("num".equalsIgnoreCase(charOrNum)) // 数字
			{
				val += String.valueOf(random.nextInt(10));
			}
		}

		return val;
	}

	/**
	 *
	 * @Title: formatAccount
	 * @Description: 格式化卡号（8位）
	 * @param account
	 */
	public static String formatAccount(int account) {
		return accountFormat.format(account);
	}


	/**
	 * 生成15位随机码：日期+3位毫秒+6位随机（字母和数字）
	 * @return
	 */
	public static String getCardAccPwd(){
		String r = DateUtils.formatDate(new Date(), "MMddssSSS");
		r = r + getCardPwd(6);
		return r;
	}

	/**
	 * 生成9位批次号：日期+3位毫秒
	 * @return
	 */
	public static String getBatchNo(){
		return DateUtils.formatDate(new Date(), "yyMMddHHmmSSS");
	}

	public static void main(String[] args) {
		Map<String, String> map = new HashMap<>();
		Map<String, String> repeatMap = new HashMap<>();

		for(int i = 0 ; i < 10000 ; i ++){
			String key = getCardAccPwd();
			if(map.get(key) != null){
				repeatMap.put(key, key);
			}
			map.put(key, key);
		}
		System.out.println("10000个卡密" + JSON.toJSONString(map));
		System.out.println("重复的：" + JSON.toJSONString(repeatMap));
	}


}
