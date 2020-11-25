/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.jf.plus.common.utils;

import java.text.DecimalFormat;
import java.text.NumberFormat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 计算功能类
 * @author ZB
 * @version 2016-10-14
 */
public class CalculateUtils {

	private static final Logger LOG = LoggerFactory.getLogger(CalculateUtils.class);

	/**
	 * 计算百分比
	 */
	public static String getPercent(Double numerator, Double denominator) {
		String percentStr = "0.00%";
		try{
			if(numerator!=null && numerator>0 && denominator!=null && denominator>0){
				NumberFormat formatter = new DecimalFormat("0.00");
				Double percentDouble =new Double((numerator/denominator)*100);
				percentStr = formatter.format(percentDouble) + "%";
			}
			return percentStr;
		}catch(Exception e){
			e.printStackTrace();
			LOG.debug("【ERROR】--计算百分比错误！");
			return percentStr;					
		}
	}
	
	public static String subZeroAndDot(String s){    
        if(s.indexOf(".") > 0){    
            s = s.replaceAll("0+?$", "");//去掉多余的0    
            s = s.replaceAll("[.]$", "");//如最后一位是.则去掉    
        }    
        return s;    
    }
	
}
