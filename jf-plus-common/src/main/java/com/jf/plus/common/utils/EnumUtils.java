package com.jf.plus.common.utils;

import java.lang.reflect.Method;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.cglib.core.EmitUtils;

public class EnumUtils {

	private static Logger logger = LoggerFactory.getLogger(EmitUtils.class);
			
	public static String getText(String className,Integer enumVal){
		try{
			Class clazz = Class.forName(className);
			Method getType = clazz.getMethod("getType"); 
	        Method getDescription = clazz.getMethod("getDescription"); 
	        //得到enum的所有实例 
	        Object[] enums = clazz.getEnumConstants();
	        
	        for(Object object : enums){
	        	if(((Integer)getType.invoke(object)).intValue() == enumVal.intValue()){
	        		return (String) getDescription.invoke(object);
	        	}
	        }
		} catch (Exception e) {
			logger.error("获取枚举失败：{}", e);
		}
		return StringUtils.EMPTY;
	}
	
	public static String getTextString(String className,String enumVal){
		try{
			Class clazz = Class.forName(className);
			Method getType = clazz.getMethod("getType"); 
	        Method getDescription = clazz.getMethod("getDescription"); 
	        //得到enum的所有实例 
	        Object[] enums = clazz.getEnumConstants();
	        
	        for(Object object : enums){
	        	if(((String)getType.invoke(object)).equals(enumVal)){
	        		return (String) getDescription.invoke(object);
	        	}
	        }
		} catch (Exception e) {
			logger.error("获取枚举失败：{}", e);
		}
		return StringUtils.EMPTY;
	}
}
