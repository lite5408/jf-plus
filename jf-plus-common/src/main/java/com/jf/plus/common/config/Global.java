/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.jf.plus.common.config;

import java.util.Map;

import com.google.common.collect.Maps;
import com.jf.plus.common.utils.PropertiesLoader;
import com.jf.plus.common.utils.StringUtils;

/**
 * 全局配置类
 * @author ThinkGem
 * @version 2014-06-25
 */
public class Global {

	/**
	 * 当前对象实例
	 */
	private static Global global = new Global();
	
	/**
	 * 保存全局属性值
	 */
	private static Map<String, String> map = Maps.newHashMap();
	
	/**
	 * 属性文件加载对象
	 */
	private static PropertiesLoader loader = new PropertiesLoader("file:/home/resin/webser/cg-plus/config/jf-plus-admin.properties",
			"file:/home/resin/webser/cg-plus/config/jf-plus-common.properties");
//	private static PropertiesLoader loader = new PropertiesLoader("file:/home/resin/webser/jf-plus/config/jf-plus-admin.properties",
//			"file:/home/resin/webser/jf-plus/config/jf-plus-common.properties");
	/**
	 * 显示/隐藏
	 */
	public static final String SHOW = "1";
	public static final String HIDE = "0";

	/**
	 * 是/否
	 */
	public static final String YES = "1";
	public static final String NO = "0";
	
	/**
	 * 对/错
	 */
	public static final String TRUE = "true";
	public static final String FALSE = "false";
	
	/**
	 * 上传文件基础虚拟路径
	 */
	public static final String USERFILES_BASE_URL = "/userfiles/";
	
	/**
	 * 验证码失效时间，单位是秒
	 */
	public static final int VEFIY_CODE_TIMEOUT = 10 * 60 * 1000;

	/**
	 * 获取当前对象实例
	 */
	public static Global getInstance() {
		return global;
	}
	
	/**
	 * 获取配置
	 * @see ${fns:getConfig('adminPath')}
	 */
	public static String getConfig(String key) {
		String value = map.get(key);
		if (value == null){
			value = loader.getProperty(key);
			map.put(key, value != null ? value : StringUtils.EMPTY);
		}
		return value;
	}
	
}
