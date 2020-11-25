package com.jf.plus.common.pay.ycb;

import java.util.Arrays;

public class WxMd5Signature {
    private static final String JOIN_CHAR = "&";

    public static String generateSign(String[] params, String apiKey) {
        Arrays.sort(params);
        String stringSignTemp = StringUtils.transArrayToString(params, JOIN_CHAR) + "key=" + apiKey;
        return MD5Util.MD5(stringSignTemp).toUpperCase();
    }
}
