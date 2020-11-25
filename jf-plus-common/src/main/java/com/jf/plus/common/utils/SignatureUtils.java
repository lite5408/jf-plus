package com.jf.plus.common.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * 
 * @ClassName: SignatureUtils
 * @Description: 签名工具类
 * @author Tng
 * @date 2016年8月10日 上午10:39:14
 *
 */
public class SignatureUtils {
	
	private static final Logger logger = LoggerFactory.getLogger(SignatureUtils.class);
	
	/**
	 * 生成数字签名字符串。
	 * @param signParams 签名参数
	 * @param primaryKey 签名秘钥
	 * @return 数字签名字符串
	 */
	public static String buildSign(Map<String, Object> signParams, String primaryKey) 
	{
	    if(signParams == null || signParams.isEmpty())
	    {
	        return "";
	    }
		// 过滤签名参数中的空值参数，按照参数名称排序后拼装签名字符串
	    Map<String, String> sParaNew = ParaFilter(signParams);
		String signString = CreateLinkString(sParaNew);
		// 签名字符串拼接安全秘钥
		signString = signString + primaryKey;
		logger.info("参与加密的串："+signString); 
		// 将签名字符串进行MD5加密后返回
		signString = EncryptUtil.md5(signString);
		
		return signString;
	}
	
	/** 
	 * 功能：除去数组中的空值和签名参数
	 * @param sArray 签名参数组
	 * @return 去掉空值与签名参数后的新签名参数组
	 */
	public static Map<String, String> ParaFilter(Map<String, Object> sArray){
		Map<String, String> sArrayNew = new HashMap<String, String>();
		
		List<String> keys = new ArrayList<String>(sArray.keySet());
		for(int i = 0; i < keys.size(); i++){
			String key = (String) keys.get(i);
			String value = (String) sArray.get(key);
			
			if( value == null || value.equals("") || key.equalsIgnoreCase("sign") || key.equalsIgnoreCase("sign_type")){
				continue;
			}
			sArrayNew.put(key, value);
		}
		return sArrayNew;
	}
	
	/** 
	 * 功能：把数组所有元素排序，并按照“参数=参数值”的模式用“&”字符拼接成字符串
	 * @param params 需要排序并参与字符拼接的参数组
	 * @return 拼接后字符串
	 */
	public static String CreateLinkString(Map params){
		List keys = new ArrayList(params.keySet());
		Collections.sort(keys);

		String prestr = "";

		for (int i = 0; i < keys.size(); i++) {
			String key = (String) keys.get(i);
			String value = (String) params.get(key);

			if (i == keys.size() - 1) {//拼接时，不包括最后一个&字符
				prestr = prestr + key + "=" + value;
			} else {
				prestr = prestr + key + "=" + value + "&";
			}
		}

		return prestr;
	}
	
	private static final String RANDOM_CHARS = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
	
	/**
	 * 获得一个随机的字符串
	 * @param len
	 * @return
	 */
	public static String getRandomString(int len) 
	{
		StringBuilder buf = new StringBuilder(len + 1);
		
		Random rand = new Random();
		for (int i = 0; i < len; i++) {
			buf.append(RANDOM_CHARS.charAt(rand.nextInt(RANDOM_CHARS.length())));
		}
		return buf.toString();
	}
	
	public static String uuid() {
		return UUID.randomUUID().toString().replaceAll("-", "");
	}
	
	public static void main(String[] args)
	{
		System.out.println(SignatureUtils.getRandomString(6));
		System.out.println(System.currentTimeMillis());
		System.out.println(uuid());
		
	}
	
}

