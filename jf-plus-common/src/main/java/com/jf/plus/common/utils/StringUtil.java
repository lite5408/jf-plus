package com.jf.plus.common.utils;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

public class StringUtil {

	public static boolean isFine(String str) {
		return str != null && str.length() > 0;
	}

	/**
	 * s中的s1替换成s2
	 * 
	 * @param s
	 * @param s1
	 * @param s2
	 * @return
	 */
	public static final String replace(String s, String s1, String s2) {
		if (s == null)
			return null;
		int i = 0;
		if ((i = s.indexOf(s1, i)) >= 0) {
			char ac[] = s.toCharArray();
			char ac1[] = s2.toCharArray();
			int j = s1.length();
			StringBuffer sb = new StringBuffer(ac.length);
			sb.append(ac, 0, i).append(ac1);
			i += j;
			int k;
			for (k = i; (i = s.indexOf(s1, i)) > 0; k = i) {
				sb.append(ac, k, i - k).append(ac1);
				i += j;
			}
			sb.append(ac, k, ac.length - k);
			return sb.toString();
		} else {
			return s;
		}
	}
	
	/**
	 * s中的s1替换成s2，如果有分隔符，同时替换
	 * 
	 * @param s
	 * @param s1
	 * @param s2
	 * @return
	 */
	public static String replaceSeparator(String s,String s1,String s2,String separator){
		if(s == null){
			return null;
		}
		
		String[] strArry = s.split(separator);
		List<String> strList = new ArrayList<>();
		for(String str : strArry){
			if(str.equals(s1)){
				if(StringUtils.isNotBlank(s2)){
					strList.add(s2);
				}
			}else{
				strList.add(str);
			}
		}
		
		return StringUtils.join(strList,separator);
	}

	/**
	 * 得到一个字符串的长度,显示的长度,一个汉字或日韩文长度为2,英文字符长度为1
	 * 
	 * @param String
	 *            s 需要得到长度的字符串
	 * 
	 * @return int 得到的字符串长度
	 */
	public static int length(String s) {
		if (s == null)
			return 0;
		char[] c = s.toCharArray();
		int len = 0;
		for (int i = 0; i < c.length; i++) {
			len++;
			if (!isLetter(c[i])) {
				len++;
			}
		}
		return len;
	}
	
	
	/**
     * 获取字符串的长度，中文占一个字符,英文数字占半个字符
     *
     * @param value  指定的字符串          
     * @return 字符串的长度
     */
    public static double getLength(String value) {
        double valueLength = 0;
        String chinese = "[\u4e00-\u9fa5]";
        // 获取字段值的长度，如果含中文字符，则每个中文字符长度为2，否则为1
        for (int i = 0; i < value.length(); i++) {
            // 获取一个字符
            String temp = value.substring(i, i + 1);
            // 判断是否为中文字符
            if (temp.matches(chinese)) {
                // 中文字符长度为1
                valueLength += 1;
            } else {
                // 其他字符长度为0.5
                valueLength += 0.5;
            }
        }
        //进位取整
        return  Math.ceil(valueLength);
    }

	/**
	 * 判断是否是字符
	 * 
	 * @param c
	 * @return
	 */
	public static boolean isLetter(char c) {
		int k = 0x80;
		return c / k == 0 ? true : false;
	}

	public static void main(String[] args) {
		System.out.println(replaceSeparator("0,5,15", "5", "",","));
	}
}
