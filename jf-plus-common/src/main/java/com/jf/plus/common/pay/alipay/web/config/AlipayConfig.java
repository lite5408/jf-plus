package com.jf.plus.common.pay.alipay.web.config;

import com.jf.plus.common.core.Constants;

/* *
 *类名：AlipayConfig
 *功能：基础配置类
 *详细：设置帐户有关信息及返回路径
 *版本：3.4
 *修改日期：2016-03-08
 *说明：
 *以下代码只是为了方便商户测试而提供的样例代码，商户可以根据自己网站的需要，按照技术文档编写,并非一定要使用该代码。
 *该代码仅供学习和研究支付宝接口使用，只是提供一个参考。
 */

public class AlipayConfig {
	
//↓↓↓↓↓↓↓↓↓↓请在这里配置您的基本信息↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓

	// 合作身份者ID，签约账号，以2088开头由16位纯数字组成的字符串，查看地址：https://b.alipay.com/order/pidAndKey.htm
	public static String partner = "2088221830640145";
	
	// 收款支付宝账号，以2088开头由16位纯数字组成的字符串，一般情况下收款账号就是签约账号
	public static String seller_id = partner;

	//商户的私钥,需要PKCS8格式，RSA公私钥生成：https://doc.open.alipay.com/doc2/detail.htm?spm=a219a.7629140.0.0.nBDxfy&treeId=58&articleId=103242&docType=1
	public static String private_key = "MIICeQIBADANBgkqhkiG9w0BAQEFAASCAmMwggJfAgEAAoGBAMctuIBnt3srETk1BySifwPxrvISxTLWyY7XKYoQDmOx8NEcaO+QMaVbf5jWH5PeMrKQed1A6EX2OTvqiqYPFUOk28Ns6IKFJVQnBZ6wvfsVVS+hL7DfOUQdMZEC3AcPTi+ElNlghD9wtMOtHKWfH3ksrPDEQQMVcS3NsvjUpS53AgMBAAECgYEAkYl/6hte/1mtwR5VNNijdK9RRMKgYxB8wKnJSX+H9CLeMv3OU1YKYKgl6Ph0pGhUpRNaaGf4EL7kfTpYyxP0VWna2uom5CMvP6TquW7UdRLQIT0O+xIBtjM31PUlXHw78QSsuYV3BdSY8C+IFKatPSji7ditXDj2w1XEpaT63RkCQQDp3NcDikbcN1h2DHiNi7gG5hkpAxNq0L74M2ILEoraLehNWXuSCy7NEXWTrRfEk//tfQCSfokYeLKoN6SK1NrlAkEA2ghjEc7vJLI+UmsCg8FBTS2Ki1qw2ul+n3Oh4kdZ+XPhtEN8X5yXpwSMagpDluiqH7ch83DySn3rPfzPf2oiKwJBAKt2QSbVURLQeUlD53dQA0L7nWLWYHwmRH3Rnoajt64zjmh3vQPcsxmjPWNiu1Rjdnb2+V5u67FHfSWcdkVbvokCQQCu7TOuwfuT0X222iv2UUJ3DSauj2B9AgCZJUioJt7FCHf5DHvoqdXyJfAR05JOrPRw5ZjTm7aIlWX7Ia3IZ/ppAkEAkHGtAXsjgkcmIaER4Xu/MrYwSeIE044SBkHAQbfmvkqUJoZCvYgK+d3VbWwiaibJVgTDR3VxUWZJToR//zJVXg==";
	
	// 支付宝的公钥,查看地址：https://b.alipay.com/order/pidAndKey.htm
	public static String alipay_public_key  = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCnxj/9qwVfgoUh/y2W89L6BkRAFljhNhgPdyPuBV64bfQNN1PjbCzkIM6qRdKBoLPXmKKMiFYnkd6rAoprih3/PrQEB/VsW8OoM8fxn67UDYuyBTqA23MML9q1+ilIZwBC2AQ2UBVOrFXfFl75p6/B5KsiNG9zpgmLCUYuLkxpLQIDAQAB";

	// 服务器异步通知页面路径  需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
	public static String notify_url = Constants.URL_WEB + "/pay/alipay/notify.shtml";

	// 页面跳转同步通知页面路径 需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
	public static String return_url = Constants.URL_WEB + "/pay/alipay/return.shtml";

	// 签名方式
	public static String sign_type = "RSA";
	
	// 调试用，创建TXT日志文件夹路径，见AlipayCore.java类中的logResult(String sWord)打印方法。
	public static String log_path = "C:\\";
		
	// 字符编码格式 目前支持 gbk 或 utf-8
	public static String input_charset = "utf-8";
		
	// 支付类型 ，无需修改
	public static String payment_type = "1";
		
	// 调用的接口名，无需修改
	public static String service = "create_direct_pay_by_user";


//↑↑↑↑↑↑↑↑↑↑请在这里配置您的基本信息↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑
	
//↓↓↓↓↓↓↓↓↓↓ 请在这里配置防钓鱼信息，如果没开通防钓鱼功能，为空即可 ↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓
	
	// 防钓鱼时间戳  若要使用请调用类文件submit中的query_timestamp函数
	public static String anti_phishing_key = "";
	
	// 客户端的IP地址 非局域网的外网IP地址，如：221.0.0.1
	public static String exter_invoke_ip = "";
		
//↑↑↑↑↑↑↑↑↑↑请在这里配置防钓鱼信息，如果没开通防钓鱼功能，为空即可 ↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑
	
}

