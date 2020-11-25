package com.jf.plus.common.pay.ycb;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.RandomStringUtils;

import com.jf.plus.common.config.Global;

public class YcbPaySubmit {
    
	private static String charater = "abcdefghijklmnopqrstuvwxyz1234567890";

//    private static final String ALIPAY_GATEWAY_NEW = "http://ycb51.f3322.net:83/payment/gateway?";
//	   private static final String ALIPAY_GATEWAY_NEW = "http://192.168.0.66/payment/gateway?";
	   private static final String ALIPAY_GATEWAY_NEW = Global.getConfig("ycb.pay_gateway");
    /**
     * 
     * 过滤特殊字符
     *
     */
    private static String paraFilter(String str){
    	str = str.replaceAll("\\s*", "");
    	String regEx="[`~!@#$%^&*()+=|{}':;',//[//].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";
    	Pattern   p   =   Pattern.compile(regEx);        
        Matcher   m   =   p.matcher(str);        
        return   m.replaceAll("").trim();       
    	
    }
    private static Map<String, String> buildRequestPara(String apiKey, String merchantId,Map<String, String> sParaTemp) {
    	String nonce_str = RandomStringUtils.random(3,charater);
    	
    	sParaTemp.put("nonce_str", nonce_str);
    	sParaTemp.put("merchant_id", merchantId);
    	sParaTemp.put("notify_url", sParaTemp.get("notify_url"));
    	String subject = sParaTemp.get("subject");
    	subject = paraFilter(subject);
    	sParaTemp.put("subject", subject);
    	String mysign = WxMd5Signature.generateSign(new String[]{"merchant_id=" + merchantId, 
    			"trade_no="+sParaTemp.get("trade_no"), 
    			"amount="+sParaTemp.get("amount"), 
    			"subject="+subject, 
    			"nonce_str="+nonce_str, 
    			"notify_url="+sParaTemp.get("notify_url")}, apiKey);

        //签名结果与签名方式加入请求提交参数组中
    	sParaTemp.put("sign", mysign);

        return sParaTemp;
    }
    
    
    /**
     * 建立请求，以表单HTML形式构造（默认）
     * @param sParaTemp 请求参数数组
     * @param strMethod 提交方式。两个值可选：post、get
     * @param strButtonName 确认按钮显示文字
     * @return 提交表单HTML文本
     */
    public static String buildRequest(Map<String, String> sParaTemp, String strMethod, String strButtonName ,String apiKey, String merchantId, String serviceId) {
        //待请求参数数组
        Map<String, String> sPara = buildRequestPara(apiKey, merchantId, sParaTemp);
        List<String> keys = new ArrayList<String>(sPara.keySet());

        StringBuffer sbHtml = new StringBuffer();

        sbHtml.append("<form id=\"ycbpaysubmit\" name=\"ycbpaysubmit\" action=\"" + ALIPAY_GATEWAY_NEW
                      + " method=\"" + strMethod
                      + "\">");
        //加入serviceId
        sbHtml.append("<input type=\"hidden\" name=\"service_id\" value=\"" + serviceId + "\"/>");
        for (int i = 0; i < keys.size(); i++) {
            String name = (String) keys.get(i);
            String value = (String) sPara.get(name);

            sbHtml.append("<input type=\"hidden\" name=\"" + name + "\" value=\"" + value + "\"/>");
        }

        //submit按钮控件请不要含有name属性
        sbHtml.append("<input type=\"submit\" value=\"" + strButtonName + "\" style=\"display:none;\"></form>");
        sbHtml.append("<script>document.forms['ycbpaysubmit'].submit();</script>");

        return sbHtml.toString();
    }
    
    public static void main(String[] args) {
		String trade_no = "DD2016092807180290845";
		String subject = "京东超市洽洽坚果炒";
		String amount = "59.9";
		Map<String, String> sParamTemp = new HashMap<>();
		sParamTemp.put("trade_no", trade_no);
		sParamTemp.put("subject", subject);
		sParamTemp.put("amount", amount);
		
//		String reString = YcbPaySubmit.buildRequest(sParamTemp, "get", "确定");
//		System.out.println(reString);
    	
	}
}
