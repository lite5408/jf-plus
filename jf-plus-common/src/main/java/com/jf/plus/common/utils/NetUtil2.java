package com.jf.plus.common.utils;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.utils.HttpClientUtils;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jf.plus.common.config.Global;

/**
 * HTTP请求客户端操作类，基于org.apache.http.client包4.4.x版本实现
 */
public final class NetUtil2 {

	public static final String TIMEOUT_CODE = "CODE:504|GATEWAY TIMEOUT";

	/**
	 * 默认连接超时时间(毫秒) 由于目前的设计原因，该变量定义为静态的，超时时间不能针对每一次的请求做定制 备选优化方案：
	 * 1.考虑是否重新设计这个工具类，每次请求都需要创建一个实例; 2.请求方法里加入超时时间参数
	 * 或者说是否没必要定制,30秒是一个比较适中的选择，但有些请求可能就是需要快速给出结果T_T
	 */
	public static final int CONNECT_TIMEOUT = 30 * 1000;
	/**
	 * 日志输出组件
	 */
	private static final Logger LOG = LoggerFactory.getLogger(NetUtil2.class);
	private static final Charset UTF_8 = Charset.forName("UTF-8");

	/**
	 * 私有化构造器 不允许外界创建实例
	 */
	private NetUtil2() {
		LOG.warn("Oh,my god!!!How do you call this method?!");
		LOG.warn("You shouldn't create me!!!");
		LOG.warn("Look my doc again!!!");
	}

	public static String post(String url, String paramData) {
		return doRequest(RequestMethod.POST, url, paramData, false);
	}

	public static String postJSON(String url, String paramJSON) {
		return doRequest(RequestMethod.POST, url, paramJSON, true);
	}
	
	public static boolean isConnectTimeOut(String ret){
		return StringUtils.isBlank(ret) || ret.equals(TIMEOUT_CODE);
	}

	/**
	 * 发起HTTP GET同步请求 jdk8使用函数式方式处理请求结果 jdk6使用内部类方式处理请求结果
	 *
	 * @param url
	 *            请求对应的URL地址
	 * @param paramMap
	 *            GET请求所带参数Map，即URL地址问号后面所带的键值对，很蛋疼的实现方式，后续得改进，还没什么好的方案
	 * @param callback
	 *            请求收到响应后回调函数，参数有2个，第一个为resultCode，即响应码，比如200为成功，404为不存在，
	 *            500为服务器发生错误； 第二个为resultJson,即响应回来的数据报文
	 */
	public static String get(String url, Map<String, String> paramMap) {
		String paramData = null;
		if (null != paramMap && !paramMap.isEmpty()) {
			StringBuilder buffer = new StringBuilder();
			// 根据传进来的参数拼url后缀- -!
			for (Map.Entry<String, String> param : paramMap.entrySet()) {
				buffer.append(param.getKey()).append("=").append(param.getValue()).append("&");
			}
			// 去掉最后一个&符号
			paramData = buffer.substring(0, buffer.length() - 1);
		}
		return doRequest(RequestMethod.GET, url, paramData, false);
	}

	public static String get(String url) {
		return doRequest(RequestMethod.GET, url, null, false);
	}

	/**
	 * 处理HTTP请求 基于org.apache.http.client包做了简单的二次封装
	 *
	 * @param method
	 *            HTTP请求类型
	 * @param url
	 *            请求对应的URL地址
	 * @param paramData
	 *            请求所带参数，目前支持JSON格式的参数
	 * @param fileList
	 *            需要一起发送的文件列表
	 * @param callback
	 *            请求收到响应后回调函数，参数有2个，第一个为resultCode，即响应码，比如200为成功，404为不存在，
	 *            500为服务器发生错误； 第二个为resultJson,即响应回来的数据报文
	 */
	private static String doRequest(final RequestMethod method, final String url, final String paramData,
			boolean IS_JSON) {
		// 如果url没有传入，则直接返回
		if (null == url || url.isEmpty()) {
			LOG.warn("The url is null or empty!!You must give it to me!OK?");
			return null;
		}

		LOG.debug("-----------------请求地址:{}-----------------", url);
		// 配置请求参数
		RequestConfig config = null;
		// 是否使用代理
		if (Global.getConfig("http.proxy") != null && Boolean.valueOf(Global.getConfig("http.proxy"))) {
			HttpHost proxy = new HttpHost(Global.getConfig("http.proxy_ip"),
					Integer.valueOf(Global.getConfig("http.proxy_port")));
			config = RequestConfig.custom().setProxy(proxy).setConnectionRequestTimeout(CONNECT_TIMEOUT / 3)
					.setConnectTimeout(CONNECT_TIMEOUT).setSocketTimeout(CONNECT_TIMEOUT).build();
		} else {
			config = RequestConfig.custom().setConnectionRequestTimeout(CONNECT_TIMEOUT)
					.setConnectTimeout(CONNECT_TIMEOUT).setSocketTimeout(CONNECT_TIMEOUT).build();
		}
		CloseableHttpClient client = HttpClientBuilder.create().setDefaultRequestConfig(config).build();

		HttpUriRequest request = null;
		switch (method) {
		case GET:
			String getUrl = url;
			if (null != paramData) {
				getUrl += "?" + paramData;
			}
			request = new HttpGet(getUrl);
			break;
		case POST:
			LOG.debug("请求入参:");
			LOG.debug(paramData);
			request = new HttpPost(url);
			if (null != paramData) {
				ContentType contentType = ContentType.create("application/x-www-form-urlencoded", "UTF-8");// 中文转码一定要加
				if (IS_JSON) {
					contentType = ContentType.APPLICATION_JSON;
				}
				StringEntity stringEntity = new StringEntity(paramData, contentType);
				((HttpPost) request).setEntity(stringEntity);
			}
			break;
		case PUT:
		case DELETE:
		default:
			LOG.warn("-----------------请求类型:{} 暂不支持-----------------", method.toString());
			break;
		}
		CloseableHttpResponse response = null;
		try {
			long start = System.currentTimeMillis();
			// 发起请求
			response = client.execute(request);
			long time = System.currentTimeMillis() - start;
			LOG.debug("本次请求'{}'耗时:{}ms", url.substring(url.lastIndexOf("/") + 1, url.length()), time);
			int resultCode = response.getStatusLine().getStatusCode();
			HttpEntity entity = response.getEntity();
			String resultJson = EntityUtils.toString(entity, UTF_8);
			// 返回码200，请求成功；其他情况都为请求出现错误
			if (HttpStatus.SC_OK == resultCode) {
				LOG.debug("-----------------请求成功-----------------");
				LOG.debug("响应结果:");
				LOG.debug(resultJson);
				return resultJson;
			} else {
				return null;
			}
		}catch (ClientProtocolException e) {
			LOG.error("ClientProtocolException:", e);
			LOG.warn("-----------------请求出现异常:{}-----------------", e.toString());
			return TIMEOUT_CODE;
		} catch (IOException e) {
			LOG.error("IOException:", e);
			LOG.warn("-----------------请求出现IO异常:{}-----------------", e.toString());
			return TIMEOUT_CODE;
		} catch (Exception e) {
			LOG.error("Exception:", e);
			LOG.warn("-----------------请求出现其他异常:{}-----------------", e.toString());
			return TIMEOUT_CODE;
		} finally {
			// abort the request
			if (null != request && !request.isAborted()) {
				request.abort();
			}
			// close the connection
			HttpClientUtils.closeQuietly(client);
			HttpClientUtils.closeQuietly(response);
		}
	}

	/**
	 * 标识HTTP请求类型枚举
	 *
	 * @author peiyu
	 * @since 1.0
	 */
	enum RequestMethod {
		/**
		 * HTTP GET请求 一般对应的是查询业务接口
		 */
		GET,
		/**
		 * HTTP POST请求 一般对应的是新增业务接口 只是一般都通用这个请求方式来处理一切接口了T_T
		 */
		POST,
		/**
		 * HTTP PUT请求，用的太少，暂不支持 一般对应的是更新业务接口
		 */
		PUT,
		/**
		 * HTTP DELETE请求，用的太少，暂不支持 一般对应的是删除业务接口
		 */
		DELETE
	}

	public static void main(String[] args) {
		System.out.println(post("http://127.0.0.1:8088/api/districtList", "source=1&parentId=0&key=VOP驿车宝"));
		// System.out.println(postJSON("http://127.0.0.1:8088/api/addressSave",
		// "{userId:1,receiverName:\"你好\"}"));
	}
}
