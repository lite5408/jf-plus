package com.jf.plus.common.pay.ycb;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class StringUtils {

    public static String transArrayToString(Object[] array) {
        StringBuilder builder = new StringBuilder();
        for (Object item : array) {
            builder.append(item);
        }
        return builder.toString();
    }

    public static String transArrayToString(Object[] array, String joinStr) {
        StringBuilder builder = new StringBuilder();
        for (Object item : array) {
            builder.append(item + joinStr);
        }
        return builder.toString();
    }

    public static String urlEncode(String url) {
        try {
            url = URLEncoder.encode(url, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return url;
    }

}
