package com.jf.plus.common.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 加密、解密相关操作                      
 * @Filename: EncryptUtil.java
 * @Version: 1.0
 * @Author: benio 虚竹
 * @Email: benio@nanjia.com
 *
 */
@SuppressWarnings("restriction")
public final class EncryptUtil {
    private static Logger log = LoggerFactory.getLogger(EncryptUtil.class);

    /**
     * 将字符串SHA1加密
     * @param plainText 需要加密的字符串
     * @return
     */
    public static String sha1(String plainText) {
        if (plainText == null) {
            return null;
        }
        try {
            char[] HEX_DIGITS = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c',
                    'd', 'e', 'f' };
            MessageDigest messageDigest = MessageDigest.getInstance("SHA1");
            messageDigest.update(plainText.getBytes());

            byte[] bytes = messageDigest.digest();
            int len = bytes.length;
            StringBuilder buf = new StringBuilder(len * 2);
            // 把密文转换成十六进制的字符串形式
            for (int j = 0; j < len; j++) {
                buf.append(HEX_DIGITS[(bytes[j] >> 4) & 0x0f]);
                buf.append(HEX_DIGITS[bytes[j] & 0x0f]);
            }
            return buf.toString();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 将字符串MD5
     * @param plainText 需要MD5的字符串
     * @return MD5字符串值
     */
    public static String md5(String plainText) {
        String result = "";
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(plainText.getBytes());
            byte b[] = md.digest();

            int i;

            StringBuffer buf = new StringBuffer("");
            for (int offset = 0; offset < b.length; offset++) {
                i = b[offset];
                if (i < 0)
                    i += 256;
                if (i < 16)
                    buf.append("0");
                buf.append(Integer.toHexString(i));
            }
            result = buf.toString();
        } catch (NoSuchAlgorithmException e) {
            log.error("can not computer MD5 for: " + plainText, e);
            return plainText;
        }
        return result;
    }

    /**
     * 将字符串转换成BASE64编码。
     * @param s
     * @return
     */
    public static String toBASE64(String s) {
        if (s == null)
            return null;
        try {
            return new String(Base64.encodeBase64(s.getBytes()));
        } catch (Exception e) {
            log.error("Can not convert string to base64:" + s, e);
            return null;
        }
    }

    /**
     * 将BASE64编码的字符串还原。
     * @param s
     * @return
     */
    public static String fromBASE64(String s) {
        if (s == null)
            return null;
        try {
            return new String(Base64.decodeBase64(s.getBytes()));
        } catch (Exception e) {
            log.error("Can not convert base64 to string:" + s, e);
            return null;
        }
    }
}
