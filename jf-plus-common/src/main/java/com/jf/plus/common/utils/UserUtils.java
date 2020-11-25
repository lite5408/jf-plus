
/**   
 * @Title: UserUtils.java
 * @Package com.jf.plus.common.utils
 * @Description: (用一句话描述该文件做什么)
 * @author My   
 * @date 2016年1月30日 下午2:33:09
 * @version V1.0   
 */


package com.jf.plus.common.utils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.jf.plus.common.core.Constants;

/**
 * @ClassName: UserUtils
 * @Description: 用户工具类
 * @author tang
 * @date 2016年1月30日 下午2:33:09
 * 
 */

public class UserUtils {
	
	private static HttpSession getSession(){
		HttpServletRequest request = Servlets.getRequest();
		return request.getSession();
	}
	
	public static Object getCurrentUser(){
    	return getSession().getAttribute(Constants.SESSION_USER);
	}
	
	public static void setCurrentUser(Object currentUser){
		getSession().setAttribute(Constants.SESSION_USER, currentUser);
	}
}
