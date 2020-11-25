package com.jf.plus.common.pay.weixin;

import java.io.ByteArrayInputStream;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.github.sd4324530.fastweixin.message.util.MessageBuilder;
import com.github.sd4324530.fastweixin.util.NetWorkCenter;
import com.jf.plus.common.utils.EncryptUtil;
import com.jf.plus.common.utils.NetUtil2;

/**
 * 
 * @ClassName: WeixinPayUtil
 * @Description: 微信支付工具类
 * @author hbh
 * @date 2016-3-19 下午4:57:14
 * 
 */
public class WeixinPayUtil {
	private static final Logger log = LoggerFactory.getLogger(WeixinPayUtil.class);

	private static Object Server;
	
	private static String prepayResult;

	/**
	 * 把对象转换成字符串
	 * 
	 * @param obj
	 * @return String 转换成字符串,若对象为null,则返回空字符串.
	 */
	public static String toString(Object obj) {
		if (obj == null)
			return "";

		return obj.toString();
	}

	/**
	 * 把对象转换为int数值.
	 * 
	 * @param obj
	 *            包含数字的对象.
	 * @return int 转换后的数值,对不能转换的对象返回0。
	 */
	public static int toInt(Object obj) {
		int a = 0;
		try {
			if (obj != null)
				a = Integer.parseInt(obj.toString());
		} catch (Exception e) {

		}
		return a;
	}

	/**
	 * 获取当前时间 yyyyMMddHHmmss
	 * 
	 * @return String
	 */
	public static String getCurrTime() {
		Date now = new Date();
		SimpleDateFormat outFormat = new SimpleDateFormat("yyyyMMddHHmmss");
		String s = outFormat.format(now);
		return s;
	}

	/**
	 * 获取当前日期 yyyyMMdd
	 * 
	 * @param date
	 * @return String
	 */
	public static String formatDate(Date date) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
		String strDate = formatter.format(date);
		return strDate;
	}

	/**
	 * 取出一个指定长度大小的随机正整数.
	 * 
	 * @param length
	 *            int 设定所取出随机数的长度。length小于11
	 * @return int 返回生成的随机数。
	 */
	public static int buildRandom(int length) {
		int num = 1;
		double random = Math.random();
		if (random < 0.1) {
			random = random + 0.1;
		}
		for (int i = 0; i < length; i++) {
			num = num * 10;
		}
		return (int) ((random * num));
	}

	/**
	 * 获取编码字符集
	 * 
	 * @param request
	 * @param response
	 * @return String
	 */

	public static String getCharacterEncoding(HttpServletRequest request, HttpServletResponse response) {

		if (null == request || null == response) {
			return "gbk";
		}

		String enc = request.getCharacterEncoding();
		if (null == enc || "".equals(enc)) {
			enc = response.getCharacterEncoding();
		}

		if (null == enc || "".equals(enc)) {
			enc = "gbk";
		}

		return enc;
	}

	public static String URLencode(String content) {

		String URLencode;

		URLencode = replace(Server.equals(content), "+", "%20");

		return URLencode;
	}

	private static String replace(boolean equals, String string, String string2) {

		return null;
	}

	/**
	 * 获取unix时间，从1970-01-01 00:00:00开始的秒数
	 * 
	 * @param date
	 * @return long
	 */
	public static long getUnixTime(Date date) {
		if (null == date) {
			return 0;
		}

		return date.getTime() / 1000;
	}

	public static String QRfromGoogle(String chl) {
		int widhtHeight = 300;
		String EC_level = "L";
		int margin = 0;
		String QRfromGoogle;
		chl = URLencode(chl);

		QRfromGoogle = "http://chart.apis.google.com/chart?chs=" + widhtHeight + "x" + widhtHeight + "&cht=qr&chld="
				+ EC_level + "|" + margin + "&chl=" + chl;

		return QRfromGoogle;
	}

	/**
	 * 时间转换成字符串
	 * 
	 * @param date
	 *            时间
	 * @param formatType
	 *            格式化类型
	 * @return String
	 */
	public static String date2String(Date date, String formatType) {
		SimpleDateFormat sdf = new SimpleDateFormat(formatType);
		return sdf.format(date);
	}

	/**
	 * 获取预支付ID
	 * 
	 * @param url
	 * @param xmlParams
	 * @return
	 */
	public static String getPrepayID(String url, String xmlParams) {
		// 微信返回的结果报文
		Document prepayIdXml = null;
		try {
			
			NetWorkCenter.post(url, xmlParams, new NetWorkCenter.ResponseCallback() {
				@Override
	            public void onResponse(int resultCode, String resultJson) {
	                if (HttpStatus.SC_OK == resultCode) {
	                    log.info("获取getPreparyID:{}", resultJson);
	                    prepayResult = resultJson;
	                }
	            }
	        });
			if (prepayResult.indexOf("FAIL") != -1) {// 获取失败
				return null;
			}
			ByteArrayInputStream is = new ByteArrayInputStream(prepayResult.getBytes());
			DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = builderFactory.newDocumentBuilder();
			prepayIdXml = builder.parse(is);
			// 返回prepay_id
			return WeixinPayUtil.getTagValue(prepayIdXml.getDocumentElement(), "prepay_id");
		} catch (Exception ex) {
			log.error("获取预支付ID时，出现未知异常：", ex);
			return null;
		}
	}

	/**
	 * 获取扫码支付二維碼生成url
	 * 
	 * @param url
	 * @param xmlParams
	 * @return
	 */
	public static String getCodeUrl(String url, String xmlParams) {
		// 微信返回的结果报文
		Document prepayIdXml = null;
		try {
			String result = NetUtil2.post(url, xmlParams);
			if(NetUtil2.isConnectTimeOut(result)){
				return null;
			}
			
			log.info(result);
			if (result.indexOf("FAIL") != -1) {// 获取失败
				return null;
			}
			ByteArrayInputStream is = new ByteArrayInputStream(result.getBytes());
			DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = builderFactory.newDocumentBuilder();
			prepayIdXml = builder.parse(is);
			log.info("code_url:" + result);
			// 返回prepay_id
			return WeixinPayUtil.getTagValue(prepayIdXml.getDocumentElement(), "code_url");
		} catch (Exception ex) {
			log.error("获取预支付ID时，出现未知异常：", ex);
			return null;
		}
	}

	/**
	 * 创建md5摘要,规则是:按参数名称a-z排序,遇到空值的参数不参加签名。
	 */
	public String createSign(SortedMap<String, String> packageParams) {
		StringBuffer sb = new StringBuffer();
		Set es = packageParams.entrySet();
		Iterator it = es.iterator();
		while (it.hasNext()) {
			Map.Entry entry = (Map.Entry) it.next();
			String k = (String) entry.getKey();
			String v = (String) entry.getValue();
			if (null != v && !"".equals(v) && !"sign".equals(k) && !"key".equals(k)) {
				sb.append(k + "=" + v + "&");
			}
		}
		String sign = EncryptUtil.md5(sb.toString()).toUpperCase();
		// String sign = DigestUtils.md5Hex(sb.toString()).toUpperCase();
		return sign;

	}

	/**
	 * 根据xml标签名称，获取xml的值
	 * 
	 * @param element
	 * @param tagName
	 * @return
	 */
	public static String getTagValue(Element element, String tagName) {
		NodeList nodeList = element.getElementsByTagName(tagName);
		if (nodeList == null) {
			return null;
		}
		Element subElement = (Element) nodeList.item(0);
		return subElement == null ? null : subElement.getTextContent();
	}
	
	/**
	 * 将document对象转换为字符串
	 * 
	 * @param doc
	 * @return
	 */
	public static String documentToString(Document doc) {
		try {
			StringWriter sw = new StringWriter();
			TransformerFactory tf = TransformerFactory.newInstance();
			Transformer transformer = tf.newTransformer();
			transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "no");
			transformer.setOutputProperty(OutputKeys.METHOD, "xml");
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");

			transformer.transform(new DOMSource(doc), new StreamResult(sw));
			return sw.toString();
		} catch (Exception ex) {
			throw new RuntimeException("Error converting document to String", ex);
		}
	}

	public static String retNotity(String return_code, String return_msg) {
		StringBuffer success = new StringBuffer("");
		MessageBuilder messageBuilder = new MessageBuilder(success.toString());
		messageBuilder.addData("return_code", return_code);
		messageBuilder.addData("return_msg", return_msg);
		success.append("<xml>");
		success.append(messageBuilder.toString());
		success.append("</xml>");
		return success.toString();
	}
	
	/**
	 * 获取随机字符串
	 * 
	 * @return
	 */
	public static String getNonceStr() {
		// 随机数
		String currTime = getCurrTime();
		// 8位日期
		String strTime = currTime.substring(8, currTime.length());
		// 四位随机数
		String strRandom = buildRandom(4) + "";
		// 10位序列号,可以自行调整。
		return strTime + strRandom;
	}

}
