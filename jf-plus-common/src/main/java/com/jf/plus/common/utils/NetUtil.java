package com.jf.plus.common.utils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.commons.httpclient.params.HttpClientParams;
import org.apache.commons.httpclient.params.HttpConnectionManagerParams;

import com.jf.plus.common.config.Global;

public class NetUtil {
	public static char splitchar = ' ';
	public static MultiThreadedHttpConnectionManager manager = new MultiThreadedHttpConnectionManager();
	static HttpClient client = new HttpClient();
	static {
		init();
	}

	public static void init() {
		if (null != manager) {
			manager.shutdown();
			manager = null;
		}
		manager = new MultiThreadedHttpConnectionManager();
		System.setProperty("apache.commons.httpclient.cookiespec",
				"COMPATIBILITY");
		HttpConnectionManagerParams params = new HttpConnectionManagerParams();
		params.setDefaultMaxConnectionsPerHost(50);
		params.setMaxTotalConnections(1000);
		params.setConnectionTimeout(60000);
		params.setSoTimeout(60000);
		manager.setParams(params);
		manager.closeIdleConnections(60000);
		client.setHttpConnectionManager(manager);
		HttpClientParams cparams = new HttpClientParams();
		cparams.setConnectionManagerTimeout(60000);
		cparams.setSoTimeout(60000);
		client.setParams(cparams);
		
		boolean useProxy = Boolean.valueOf(Global.getConfig("http.proxy"));
		if(useProxy){
			client.getHostConfiguration().setProxy(Global.getConfig("http.proxy_ip"), Integer.valueOf(Global.getConfig("http.proxy_port")));
		}
	}

	public static String readURL(String urladdress) {
		return readURL(urladdress, "ISO-8859-1");
	}

	public static String readURL(String urladdress, String encoding) {
		while (urladdress.indexOf("&amp;") > -1) {
			urladdress = StringUtil.replace(urladdress, "&amp;", "&");
		}
		urladdress = encodeUrl(urladdress);
		GetMethod method = new GetMethod(urladdress);
		method.setFollowRedirects(true);
		method
				.setRequestHeader("User-Agent",
						"Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; .NET CLR 1.1.4322)");
		// method.setRequestHeader("X-Forwarded-For", "127.0.0.1");
		method.setRequestHeader("Accept-Language", "zh-cn");
		// method.setRequestHeader("Cookie", "pvid=1312542004");
		try {
			client.executeMethod(method);

			InputStream is = method.getResponseBodyAsStream();
			InputStreamReader isr = new InputStreamReader(is, "ISO-8859-1");
			BufferedReader in = new BufferedReader(isr);
			String strread = null;

			StringBuffer buf = new StringBuffer();
			while ((strread = in.readLine()) != null) {
				buf.append(strread);
				buf.append(splitchar);
			}
			in.close();
			is.close();
			isr.close();
			String str = buf.toString();
			if (StringUtil.isFine(encoding)
					&& !encoding.equalsIgnoreCase("ISO-8859-1")) {
				return new String(str.getBytes("ISO-8859-1"), encoding);
			}
			return new String(str.getBytes("ISO-8859-1"), getCharset(str));
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		} finally {
			method.releaseConnection();
			manager.deleteClosedConnections();
			if (manager.getConnectionsInPool() > 50) {
				init();
			}
		}
	}

	public static String postURL(String url, Map<String, LinkedList<String>> params) {
		PostMethod postMethod = postMethod(url, params);
		BufferedReader br = null;
		String result = "";
		try {
			int returnCode = client.executeMethod(postMethod);
			if (returnCode == HttpStatus.SC_NOT_IMPLEMENTED) {
				System.err.println("The Post method is not implemented by this URI");
				postMethod.getResponseBodyAsString();
			} else {
				br = new BufferedReader(new InputStreamReader(postMethod
						.getResponseBodyAsStream()));
				String readLine;
				while (((readLine = br.readLine()) != null)) 
				{
					result += readLine;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			//result = "POST FAILTRUE!";
			result = null;
		} finally {
			postMethod.releaseConnection();
			if (br != null){
				try {
					br.close();
				} catch (Exception fe) {
				}
			}
		}
		return result;
	}
	
	public static String postURLStr(String url, String raw) {
		PostMethod postMethod = postMethodStr(url, raw);
		BufferedReader br = null;
		String result = "";
		try {
			int returnCode = client.executeMethod(postMethod);
			if (returnCode == HttpStatus.SC_NOT_IMPLEMENTED) {
				System.err.println("The Post method is not implemented by this URI");
				postMethod.getResponseBodyAsString();
			} else {
				br = new BufferedReader(new InputStreamReader(postMethod
						.getResponseBodyAsStream()));
				String readLine;
				while (((readLine = br.readLine()) != null)) 
				{
					result += readLine;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			//result = "POST FAILTRUE!";
			result = null;
		} finally {
			postMethod.releaseConnection();
			if (br != null){
				try {
					br.close();
				} catch (Exception fe) {
					fe.printStackTrace();
				}
			}
		}
		return result;
	}

	private static PostMethod postMethod(String url,
			Map<String, LinkedList<String>> params) {
		PostMethod post = new PostMethod(url);
		post.setRequestHeader("Content-Type",
				"application/x-www-form-urlencoded;charset=utf-8");

		if (params != null) {
			//为了提高map遍历效率，建议使用下面方法
			for(Map.Entry<String, LinkedList<String>> entry : params.entrySet()){
				List<String> paramNameValues = entry.getValue();
				if (paramNameValues != null && !paramNameValues.isEmpty()) {
					for (String value : paramNameValues) {
//						System.out.println(entry.getKey()+" "+ value);
						post.addParameter(new NameValuePair(entry.getKey(), value));
					}
				}
			}
			/*for (Iterator<String> iter = params.keySet().iterator(); iter
					.hasNext();) {
				String paramName = iter.next();
				List<String> paramNameValues = params.get(paramName);
				if (paramNameValues != null && !paramNameValues.isEmpty()) {
					for (String value : paramNameValues) {
						post.addParameter(new NameValuePair(paramName, value));
					}
				}
			}*/
		}
		return post;
	}
	
	private static PostMethod postMethodStr(String url,String raw) {
		PostMethod post = new PostMethod(url);
		post.setRequestHeader("Content-Type",
				"application/x-www-form-urlencoded;charset=utf-8");

		if (raw != null) {
			try {
				post.setRequestEntity(new StringRequestEntity(raw, "application/x-www-form-urlencoded;charset=utf-8", "utf-8"));
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}

		}
		return post;
	}

	public static String getCharset(String str) {
		Pattern p = Pattern.compile("<meta[^>]*>", 2);
		Matcher m = p.matcher(str);
		while (m.find()) {
			String meta = m.group(0).toLowerCase();
			if (meta.indexOf("http-equiv") > -1
					&& meta.indexOf("content-type") > -1
					&& meta.indexOf("content") > -1
					&& meta.indexOf("text/html") > -1) {
				Pattern p2 = Pattern
						.compile("<meta(.*?)charset=(.*?)['\" ]{1}[^>]*>");
				Matcher m2 = p2.matcher(meta);
				if (m2.find()) {
					String charset = m2.group(2);
					if (charset.equals("gb2312")) {
						return "GBK";
					}
					return charset.toUpperCase();
				}
			}
		}
		return "GBK";
	}

	public static String encodeUrl(String url) {
		if (StringUtil.isFine(url)) {
			return url;
		}
		char[] cs = url.toCharArray();
		StringBuffer sb = new StringBuffer();
		try {
			for (int i = 0; i < cs.length; i++) {
				if (cs[i] > 255) {
					sb.append(URLEncoder.encode(String.valueOf(cs[i]), "GBK"));
				} else {
					sb.append(cs[i]);
				}
			}
			return sb.toString();
		} catch (Exception e) {
			return url;
		}
	}
	
	public static String getURLStr(String url){
		GetMethod getMethod = new GetMethod(url);
		BufferedReader br = null;
		String result = "";
		try {
			getMethod.setRequestHeader("Content-Type",
					"application/x-www-form-urlencoded;charset=utf-8");
			int returnCode = client.executeMethod(getMethod);
			if (returnCode == HttpStatus.SC_NOT_IMPLEMENTED) {
				System.err.println("The Get method is not implemented by this URI");
				getMethod.getResponseBodyAsString();
			} else {
				br = new BufferedReader(new InputStreamReader(getMethod
						.getResponseBodyAsStream()));
				String readLine;
				while (((readLine = br.readLine()) != null)) 
				{
					result += readLine;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			//result = "POST FAILTRUE!";
			result = null;
		} finally {
			getMethod.releaseConnection();
			if (br != null){
				try {
					br.close();
				} catch (Exception fe) {
					fe.printStackTrace();
				}
			}
		}
		return result;
	}

	public static void main(String[] args) 
	{
		String url="http://121.201.38.146/sendSMS.action";
		System.out.println(getURLStr(url+"?enterpriseID=16532&loginName=admin&password=df10ef8509dc176d733d59549e7dbfaf&mobiles=18607198919&content=%E6%82%A8%E7%9A%84%E9%AA%8C%E8%AF%81%E7%A0%81%E6%98%AF8850%E3%80%82"));
		
		
		
	}
}

