/**
 * Copyright (c) 2005-2012 springside.org.cn
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.jf.plus.common.utils;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.jf.plus.common.config.Global;
import com.jf.plus.common.core.Constants;

/**
 * Http与Servlet工具类.
 * 
 * @author calvin/thinkgem
 * @version 2014-8-19
 */
public class Servlets {
	// 静态文件后缀
	private final static String[] staticFiles = StringUtils.split(Global.getConfig("web.staticFile"), ",");
	// 动态映射URL后缀
	private final static String urlSuffix = Global.getConfig("web.view.suffix");

	/**
	 * 获取当前请求对象
	 * 
	 * @return
	 */
	public static HttpServletRequest getRequest() {
		try {
			return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * 判断访问URI是否是静态文件请求
	 * 
	 * @throws Exception
	 */
	public static boolean isStaticFile(String uri) {
		if (staticFiles == null) {
			try {
				throw new Exception("检测到“app.properties”中没有配置“web.staticFile”属性。配置示例：\n#静态文件后缀\n"
						+ "web.staticFile=.css,.js,.png,.jpg,.gif,.jpeg,.bmp,.ico,.swf,.psd,.htc,.crx,.xpi,.exe,.ipa,.apk");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		if (StringUtils.endsWithAny(uri, staticFiles) && !StringUtils.endsWithAny(uri, urlSuffix)
				&& !StringUtils.endsWithAny(uri, ".jsp") && !StringUtils.endsWithAny(uri, ".java")) {
			return true;
		}
		return false;
	}

}
