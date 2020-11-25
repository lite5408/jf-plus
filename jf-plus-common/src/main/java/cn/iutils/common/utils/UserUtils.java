package cn.iutils.common.utils;

import javax.servlet.http.HttpSession;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;

import com.jf.plus.common.vo.SiteVo;
import com.jf.plus.common.vo.SupplyerUserVo;

import cn.iutils.common.Servlets;
import cn.iutils.sys.entity.User;

/**
 * 用户管理工具
 *
 * @author cc
 *
 */
public class UserUtils {

	private static final String SESSION_USER = "session_user", SESSION_SITE = "session_site",
			SESSION_SUPPLY_USER = "session_supply_user";

	/**
	 * 用户服务对象
	 */
	// private static UserService userService =
	// SpringUtils.getBean(UserService.class);

	/**
	 * 获取当前访问用户名
	 *
	 * @return
	 */
	public static String getLoginUserName() {
		Subject subject = SecurityUtils.getSubject();
		String userName = (String) subject.getPrincipal();
		return userName;
	}

	/**
	 * 获取当前登录用户
	 *
	 * @return
	 */
	public static User getLoginUser() {
		// Subject subject = SecurityUtils.getSubject();
		// String userName = (String) subject.getPrincipal();
		// return userService.getUserByUserName(userName);
		HttpSession session = null;
		if (null == Servlets.getRequest())
			return null;
		session = Servlets.getRequest().getSession();
		return (User) session.getAttribute(SESSION_USER);
	}

	public static void setLoginUser(User user) {
		HttpSession session = Servlets.getRequest().getSession();
		session.setAttribute(SESSION_USER, user);
	}

	/**
	 * 获取当前登录的站点
	 *
	 * @return
	 */
	public static SiteVo getMySite() {
		HttpSession session = Servlets.getRequest().getSession();
		return (SiteVo) session.getAttribute(SESSION_SITE);
		// return new SiteVO("3");
	}

	public static void setMySite(SiteVo mySite) {
		HttpSession session = Servlets.getRequest().getSession();
		session.setAttribute(SESSION_SITE, mySite);
	}

	public static void setSupplyUser(SupplyerUserVo supplyerUserVo) {
		HttpSession session = Servlets.getRequest().getSession();
		session.setAttribute(SESSION_SUPPLY_USER, supplyerUserVo);
	}

	/**
	 * 获取当前登录的供应商
	 *
	 * @return
	 */
	public static SupplyerUserVo getSupplyUser() {
		HttpSession session = Servlets.getRequest().getSession();
		return (SupplyerUserVo) session.getAttribute(SESSION_SUPPLY_USER);
	}

}
