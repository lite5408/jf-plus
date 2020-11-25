package com.jf.plus.common.utils;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.URL;
import java.net.URLConnection;
import java.net.UnknownHostException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.servlet.http.HttpServletRequest;

import com.jf.plus.common.core.Result;
import com.jf.plus.common.core.ResultCode;

public class HttpUtils {
	/**
	 * http content type - jpg/jpeg
	 */
	public static String CONTENT_TYPE_JPEG = "image/jpeg";
	/**
	 * http content type - png
	 */
	public static String CONTENT_TYPE_PNG = "image/png";
	/**
	 * http content type - mp3
	 */
	public static String CONTENT_TYPE_MP3 = "audio/mp3";
	/**
	 * http content type - mp4
	 */
	public static String CONTENT_TYPE_MP4 = "video/mpeg4";
	/**
	 * http content type - html/htm
	 */
	public static String CONTENT_TYPE_HTML = "text/html";
	/**
	 * http content type - txt/json
	 */
	public static String CONTENT_TYPE_PLAIN = "text/plain";
	/**
	 * http content type - xml
	 */
	public static String CONTENT_TYPE_XML = "text/xml";
	/**
	 * http content type - amr
	 */
	public static String CONTENT_TYPE_AMR = "audio/amr";

	/**
	 * 下载网络文件
	 * 
	 * @param httpUrl
	 * @param saveFile
	 * @return 扩展名，如:.jpg
	 */
	public static Result download(String httpUrl, String saveFile) {
		Result result = Result.newInstance();
		InputStream is = null;
		FileOutputStream fs = null;
		try {
			URL url = new URL(httpUrl);
			int byteRead = 0;
			URLConnection conn = url.openConnection();
			is = conn.getInputStream();
			String extensionName = getExtensionName(conn.getContentType());
			if (StringUtils.isEmpty(extensionName)) {
				extensionName = ".jpg";
			}
			String newFileName = saveFile.substring(0, saveFile.lastIndexOf(".")) + extensionName;
			fs = new FileOutputStream(newFileName);

			byte[] buffer = new byte[1024];
			while ((byteRead = is.read(buffer)) != -1) {
				fs.write(buffer, 0, byteRead);
			}
			result.setCode(ResultCode.SUCCESS);
			result.setObj(newFileName);
		} catch (Exception ex) {
			ex.printStackTrace();
			result.setCode(ResultCode.SERVICE_EXCEPTION);
		} finally {

			try {
				if (is != null) {
					is.close();
				}
				if (fs != null) {
					fs.close();
				}
			} catch (Exception ex) {
				ex.printStackTrace();
				result.setCode(ResultCode.SERVICE_EXCEPTION);
			}
		}

		return result;
	}

	/**
	 * 根据content type，获取扩展名
	 * 
	 * @param contentType
	 * @return
	 */
	public static String getExtensionName(String contentType) {
		if (CONTENT_TYPE_JPEG.equalsIgnoreCase(contentType)) {
			return ".jpg";
		}
		if (CONTENT_TYPE_PNG.equalsIgnoreCase(contentType)) {
			return ".png";
		}
		if (CONTENT_TYPE_MP3.equalsIgnoreCase(contentType)) {
			return ".mp3";
		}
		if (CONTENT_TYPE_MP4.equalsIgnoreCase(contentType)) {
			return ".mp4";
		}
		if (CONTENT_TYPE_HTML.equalsIgnoreCase(contentType)) {
			return ".html";
		}
		if (CONTENT_TYPE_PLAIN.equalsIgnoreCase(contentType)) {
			return ".txt";
		}
		if (CONTENT_TYPE_XML.equalsIgnoreCase(contentType)) {
			return ".xml";
		}
		if (CONTENT_TYPE_AMR.equalsIgnoreCase(contentType)) {
			return ".amr";
		}
		return "";
	}
	
	private static TrustManager ignoreCertificationTrustManger = new X509TrustManager() {  
		  
	    private X509Certificate[] certificates;  
	  
	    @Override  
	    public void checkClientTrusted(X509Certificate certificates[],  
	            String authType) throws CertificateException {  
	        if (this.certificates == null) {  
	            this.certificates = certificates;  
	        }  
	    }  
	  
	    @Override  
	    public void checkServerTrusted(X509Certificate[] ax509certificate,  
	            String s) throws CertificateException {  
	        if (this.certificates == null) {  
	            this.certificates = ax509certificate;  
	        }  
	    }  
	  
	    @Override  
	    public X509Certificate[] getAcceptedIssuers() {  
	        return null;  
	    }  
	};
	
	public static Result downloadHttps(String httpsUrl,String saveFile){
		Result result = Result.newInstance();
		InputStream is = null;
		FileOutputStream fs = null;
		try{
		    URL url = new URL(httpsUrl);
		    HttpsURLConnection.setDefaultHostnameVerifier(new HostnameVerifier() {  
                @Override  
                public boolean verify(String hostname, SSLSession session) {  
                    return true;  
                }  
            });
		    HttpsURLConnection conn = ((HttpsURLConnection) url.openConnection());
		    
		    TrustManager[] tm = { ignoreCertificationTrustManger };
            SSLContext sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, tm, new java.security.SecureRandom());
            // 从上述SSLContext对象中得到SSLSocketFactory对象
            SSLSocketFactory ssf = sslContext.getSocketFactory();
		    conn.setSSLSocketFactory(ssf);
		    
		    
		    is = conn.getInputStream();
			String extensionName = getExtensionName(conn.getContentType());
			if (StringUtils.isEmpty(extensionName)) {
				extensionName = ".jpg";
			}
			String newFileName = saveFile.substring(0, saveFile.lastIndexOf(".")) + extensionName;
			fs = new FileOutputStream(newFileName);

			int byteRead = 0;
			byte[] buffer = new byte[512];
			while ((byteRead = is.read(buffer)) != -1) {
				fs.write(buffer, 0, byteRead);
			}
			result.setCode(ResultCode.SUCCESS);
			result.setObj(newFileName);
			
			conn.disconnect();
		}catch(Exception ex){
			ex.printStackTrace();
			result.setCode(ResultCode.SERVICE_EXCEPTION);
		} finally {
			try {
				if (is != null) {
					is.close();
				}
				if (fs != null) {
					fs.close();
				}
			} catch (Exception ex) {
				ex.printStackTrace();
				result.setCode(ResultCode.SERVICE_EXCEPTION);
			}
		}

		return result;
	}

	
	public static void main(String[] args) {
//		String httpsUrl = "https://mp.weixin.qq.com/cgi-bin/showqrcode?ticket=gQFJ8ToAAAAAAAAAASxodHRwOi8vd2VpeGluLnFxLmNvbS9xL0NVTTFGVXJsWFk0a2pNb2hLbTg1AAIE7JbaVgMEAAAAAA%3D%3D";
//		HttpUtils.downloadHttps(httpsUrl, "D://1.jpg");
	}
	
	
	/**
	 * 获取当前网络ip
	 * @param request
	 * @return
	 */
	public static String getIpAddr(HttpServletRequest request){
		String ipAddress = request.getHeader("x-forwarded-for");
			if(ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
				ipAddress = request.getHeader("Proxy-Client-IP");
			}
			if(ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
				ipAddress = request.getHeader("WL-Proxy-Client-IP");
			}
			if(ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
				ipAddress = request.getRemoteAddr();
				if(ipAddress.equals("127.0.0.1") || ipAddress.equals("0:0:0:0:0:0:0:1")){
					//根据网卡取本机配置的IP
					InetAddress inet=null;
					try {
						inet = InetAddress.getLocalHost();
					} catch (UnknownHostException e) {
						e.printStackTrace();
					}
					ipAddress= inet.getHostAddress();
				}
			}
			//对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割
			if(ipAddress!=null && ipAddress.length()>15){ //"***.***.***.***".length() = 15
				if(ipAddress.indexOf(",")>0){
					ipAddress = ipAddress.substring(0,ipAddress.indexOf(","));
				}
			}
			return ipAddress; 
	}
	
	
}
