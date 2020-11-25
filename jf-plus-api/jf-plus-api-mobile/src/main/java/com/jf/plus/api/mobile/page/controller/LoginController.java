package com.jf.plus.api.mobile.page.controller;

import java.net.URLEncoder;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.github.sd4324530.fastweixin.api.OauthAPI;
import com.github.sd4324530.fastweixin.api.config.ApiConfig;
import com.github.sd4324530.fastweixin.api.response.OauthGetTokenResponse;
import com.jf.plus.api.mobile.controller.BaseController;
import com.jf.plus.common.config.Global;
import com.jf.plus.common.config.WXApiConfigSingle;
import com.jf.plus.common.core.Constants;
import com.jf.plus.common.core.Result;
import com.jf.plus.common.core.ResultCode;
import com.jf.plus.common.core.ResultObj;
import com.jf.plus.common.core.enums.AccountType;
import com.jf.plus.common.core.enums.RoleType;
import com.jf.plus.common.core.enums.Status;
import com.jf.plus.common.core.enums.UserType;
import com.jf.plus.common.utils.CookieUtils;
import com.jf.plus.common.utils.Encodes;
import com.jf.plus.common.utils.YSTUtils;
import com.jf.plus.common.vo.AouthUser;
import com.jf.plus.core.account.entity.OrgAccount;
import com.jf.plus.core.account.entity.VoucherAccCash;
import com.jf.plus.core.account.service.OrgAccountService;
import com.jf.plus.core.account.service.VoucherAccCashService;
import com.jf.plus.core.mall.entity.MallSite;
import com.jf.plus.core.mall.service.MallSiteService;
import com.jf.plus.core.mallSetting.entity.UserExtInfo;
import com.jf.plus.core.mallSetting.service.UserExtInfoService;

import cn.iutils.common.Page;
import cn.iutils.common.utils.MPageInfo;
import cn.iutils.common.utils.UserUtils;
import cn.iutils.sys.entity.Organization;
import cn.iutils.sys.entity.User;
import cn.iutils.sys.entity.enums.UserSource;
import cn.iutils.sys.service.PasswordHelper;
import cn.iutils.sys.service.RoleService;
import cn.iutils.sys.service.UserService;

@Controller
public class LoginController extends BaseController {

	private static Logger LOGGER = LoggerFactory.getLogger(LoginController.class);

	@Autowired
	private MallSiteService mallSiteService;

	@Autowired
	private VoucherAccCashService voucherAccCashService;

	@Autowired
	private UserService userService;

	@Autowired
	private OrgAccountService orgAccountService;
	
	@Autowired
	private RoleService roleService;
	
	@Autowired
	private UserExtInfoService userExtInfoService;

	/**
	 * 用户登录
	 *
	 * @param username
	 * @param password
	 * @param page
	 * @return
	 */
	@RequestMapping(value = "/api/login", method = { RequestMethod.POST })
	@ResponseBody
	public Result login(@RequestParam(required = true) String username, @RequestParam(required = true) String password,Integer type, 
				HttpServletRequest request,HttpServletResponse response) {
		Result result = Result.newInstance();
		try {
			if(type == null){
				type = UserType.SALER.getType();
			}
			// 判断工号有效性
			User user = userService.getByUserName(username);
			if (user == null) {
				result.setCode(ResultCode.RETURN_FAILURE);
				result.setMsg("账号未注册");
				return result;
			}
			
			
			result = checkLoginPromise(type,user.getRoleGroupIds());
			if(!result.isSuccess()){
				return result;
			}
			
			// 判断密码是否正确
			String pwd = user.getPassword();
			user.setPassword(password);
			if (!pwd.equals(new PasswordHelper().getPassword(user))) {
				result.setCode(ResultCode.RETURN_FAILURE);
				result.setMsg("密码不正确");
				return result;
			}
			

			//绑定微信
			String wxOpenId = CookieUtils.getCookie(request, Constants.COOKIE_WX_OPENID);
			if(StringUtils.isNotBlank(wxOpenId)){
				UserExtInfo userExtInfo = new UserExtInfo();
				userExtInfo.setUserId(Long.valueOf(user.getId()));
				userExtInfo = userExtInfoService.getEntity(userExtInfo);
				if(userExtInfo != null){
					if(StringUtils.isNotBlank(userExtInfo.getPlatUserId())){//该账号已绑定过微信，请先解绑
						result.setCode(ResultCode.RETURN_FAILURE);
						result.setMsg("该账号已绑定过微信，请先解绑");
						return result;
					}
					userExtInfo.setPlatUserId(wxOpenId);
					userExtInfoService.save(userExtInfo);
				}else{
					userExtInfo = new UserExtInfo();
					userExtInfo.setPlatUserId(wxOpenId);
					userExtInfo.setOrgId(Long.valueOf(user.getOrganizationIds().split(",")[0]));
					userExtInfo.setMobile(user.getPhone());
					userExtInfo.setUserId(Long.valueOf(user.getId()));
					userExtInfo.setSource(1);
					userExtInfo.setCreateBy(user.getId());
					userExtInfo.setCreateDate(new Date());
					userExtInfo.setStatus("1");
					userExtInfoService.save(userExtInfo);
				}
				
				//登录成功后，删除微信cookie
				CookieUtils.setCookie(response, Constants.COOKIE_WX_OPENID, "");
			}
			
			
			
			UserUtils.setLoginUser(user); // 设置当前用户
			ResultObj resultObj = new ResultObj();
			resultObj.put("user", user);
			resultObj.put("token", createToken(user));
			result.setObj(resultObj);
			result.setCode(ResultCode.SUCCESS);
			return result;
		} catch (Exception e) {
			LOGGER.error("用户登录异常：", e);
			result = Result.newExceptionInstance();
			return result;
		}
	}

	/**
	 * APP授权登录
	 *
	 * @param data
	 * @param token
	 * @param page
	 * @return
	 */
	@RequestMapping(value = "/api/oauthLogin", method = { RequestMethod.POST })
	@ResponseBody
	public Result oauthLogin(@RequestParam(required = true) String data, @RequestParam(required = true) String token,
			Page<MallSite> page, HttpServletRequest request) {
		Result result = Result.newInstance();
		try {

			LOGGER.info("data:" + data);
			LOGGER.info("token:" + token);

			// data = URLDecoder.decode(data, "UTF-8");
			if (!YSTUtils.VerifyData(data, token)) {
				result.setCode(ResultCode.SING_ERROR);
				result.setMsg("数字签名有误");
				return result;
			}

			AouthUser aouthUser = JSON.parseObject(Encodes.decodeBase64String(data), AouthUser.class);
			if (StringUtils.isBlank(aouthUser.getSapID())) {
				result.setCode(ResultCode.FAILURE_NOT_EXISTS);
				result.setMsg("您没有访问权限");
				return result;
			}

			// 判断工号有效性
			User user = userService.getByUserName(aouthUser.getSapID(), UserSource.SYS.getType());
			if (user == null) {
				result.setCode(ResultCode.RETURN_FAILURE);
				result.setMsg("您没有访问权限");
				return result;
			}

			page.getCondition().put("userName", aouthUser.getSapID());
			List<MallSite> mallSiteList = mallSiteService.findListByUser(page);
			if (CollectionUtils.isEmpty(mallSiteList)) {
				result.setMsg("您暂无站点登录权限");
				result.setCode(ResultCode.RETURN_FAILURE);
				return result;
			}
			page.setList(mallSiteList);

			UserUtils.setLoginUser(user); // 设置当前用户
			ResultObj resultObj = new ResultObj();
			resultObj.put("page", MPageInfo.transform(page));
			resultObj.put("user", user);
			resultObj.put("token", createToken(user));
			result.setObj(resultObj);
			result.setCode(ResultCode.SUCCESS);
			return result;
		} catch (Exception e) {
			LOGGER.error("授权登录异常：", e);
			result = Result.newExceptionInstance();
			return result;
		}
	}

	/**
	 * 审核端-APP授信登录
	 *
	 * @param data
	 * @param token
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/api/oauthLogin_audit", method = { RequestMethod.POST })
	@ResponseBody
	public Result oauthLoginAudit(@RequestParam(required = true) String data,
			@RequestParam(required = true) String token, HttpServletRequest request) {
		Result result = Result.newInstance();
		try {
			LOGGER.info("====审批授信登录====");
			LOGGER.info("data:" + data);
			LOGGER.info("token:" + token);

			// data = URLDecoder.decode(data, "UTF-8");
			if (!YSTUtils.VerifyData(data, token)) {
				result.setCode(ResultCode.SING_ERROR);
				result.setMsg("数字签名有误");
				return result;
			}

			AouthUser aouthUser = JSON.parseObject(Encodes.decodeBase64String(data), AouthUser.class);
			if (StringUtils.isBlank(aouthUser.getSapID())) {
				result.setCode(ResultCode.FAILURE_NOT_EXISTS);
				result.setMsg("您没有访问权限");
				return result;
			}

			// 判断工号有效性
			User user = userService.getByUserName(aouthUser.getSapID(), UserSource.SYS.getType());
			if (user == null) {
				result.setCode(ResultCode.RETURN_FAILURE);
				result.setMsg("您没有访问权限");
				return result;
			}

			UserUtils.setLoginUser(user); // 设置当前用户
			ResultObj resultObj = new ResultObj();
			resultObj.put("user", user);
			resultObj.put("token", createToken(user));
			result.setObj(resultObj);
			result.setCode(ResultCode.SUCCESS);
			return result;
		} catch (Exception e) {
			LOGGER.error("授权登录异常：", e);
			result = Result.newExceptionInstance();
			return result;
		}
	}

	/**
	 * APP统一授权登录
	 *
	 * @param data
	 * @param token
	 * @param page
	 * @return
	 */
	@RequestMapping(value = "/mall/sso", method = { RequestMethod.GET })
	public String mallSso(@RequestParam(required = true) String data, @RequestParam(required = true) String token) {
		LOGGER.info("====授信请求参数====");
		LOGGER.info("data:{}", data);
		LOGGER.info("token:{}", token);

		StringBuffer redirectURL = new StringBuffer();

		if (YSTUtils.VerifyData(data, token)) {
			String decodeData = Encodes.decodeBase64String(data);
			LOGGER.info("data明文:{}", decodeData);
			AouthUser aouthUser = JSON.parseObject(decodeData, AouthUser.class);
			if (StringUtils.isNotBlank(aouthUser.getSapID())) {
				User user = new User();
				user.setUsername(aouthUser.getSapID());
				// 判断订单审批角色
				user.setRole(RoleType.AUDIT_ORDER.getType());
				user = userService.getEntity(user);
				if (user != null) {
					redirectURL.append(Global.getConfig("url.audit.sso"));
				}
			}
		}

		// 发生异常或者非订单审批角色，默认跳商城首页
		if (redirectURL.length() == 0) {
			redirectURL.append(Global.getConfig("url.mobile.sso"));
		}

		try {
			data = URLEncoder.encode(data, "UTF-8");
		} catch (Exception e) {
			// Nothing todo;
		}

		redirectURL.append("?data=").append(data).append("&token=").append(token);
		LOGGER.info("====跳转参数====");
		LOGGER.info("redirectURL:{}", redirectURL.toString());

		return "redirect:" + redirectURL.toString();
	}

	/**
	 * 查询用户可用券
	 *
	 * @param stockVo
	 * @return
	 */
	@RequestMapping(value = "/api/findUserVoucher", method = { RequestMethod.POST })
	@ResponseBody
	public Result findUserVoucher(@RequestParam String token, @RequestParam Long siteId) {
		Result result = Result.newInstance();
		try {
			result = this.checkToken(token); // 验证token有效性
			if (!result.isSuccess())
				return result;
			Page<VoucherAccCash> page = new Page<>();
			page.getCondition().put("siteId", siteId);
			page.getCondition().put("userId", result.getObj());
			page.getCondition().put("accountType", AccountType.JC.getType());
			page.getCondition().put("operStatus", Status.NORMAL.getType());
			List<VoucherAccCash> vList = voucherAccCashService.findVoucherAccCashListByJc(page);
			double availablePoints = 0;
			for (VoucherAccCash voucherAccCash : vList) {
				availablePoints += voucherAccCash.getBlance() * voucherAccCash.getRatio();
			}
			ResultObj resultObj = new ResultObj();
			resultObj.put("availablePoints", availablePoints);
			result.setObj(resultObj);
			result.setCode(ResultCode.SUCCESS);
			return result;
		} catch (Exception e) {
			LOGGER.error("获取用户余额失败：", e);
			result = Result.newExceptionInstance();
			return result;
		}
	}

	@RequestMapping(value = "/api/findOrgAccount", method = { RequestMethod.POST })
	@ResponseBody
	public Result findOrgAccount(@RequestParam String token) {
		Result result = Result.newInstance();
		try {
			result = this.checkToken(token); // 验证token有效性
			if (!result.isSuccess())
				return result;

			Double availablePoints = 0D;
			User user = userService.get(result.getObj().toString());
			Organization organization = user.getOrganization();
			OrgAccount entity = new OrgAccount();
			if (organization != null && organization.getId() != null) {
				entity.setOrgId(Long.valueOf(organization.getId()));
				entity = orgAccountService.getEntity(entity);
				if (entity != null) {
					availablePoints = entity.getPurchaseBlance();
				}
			}

			ResultObj resultObj = new ResultObj();
			resultObj.put("availablePoints", availablePoints);
			result.setObj(resultObj);
			result.setCode(ResultCode.SUCCESS);
			return result;
		} catch (Exception e) {
			LOGGER.error("获取机构余额失败：", e);
			result = Result.newExceptionInstance();
			return result;
		}
	}

	/**
	 * token校验接口
	 */
	@RequestMapping(value = "/api/checkToken", method = { RequestMethod.POST })
	@ResponseBody
	public Result checkToken_v1(@RequestParam String token) {
		Result result = Result.newInstance();
		try {
			return this.checkToken(token);
		} catch (Exception e) {
			LOGGER.error("Token校验失败：", e);
			result = Result.newExceptionInstance();
			return result;
		}
	}
	
	@RequestMapping("/api/weixin/auth")
	@ResponseBody
	public Result weixinAuth(@RequestParam(required = false) String code, @RequestParam(required = false) String state,Integer type,
			HttpServletRequest request, HttpServletResponse response) {
		Result result = new Result();
		LOGGER.info("code:{},state:{}", code, state);
		try {
			if(StringUtils.isEmpty(code)){
				result.setCode(ResultCode.ARGUMENT_LACK_ERROR);
				result.setMsg("参数缺失");
				return result;
			}
			String openId = "";
			ApiConfig config = WXApiConfigSingle.getInstance().getApiConfig();
			OauthAPI oauth = new OauthAPI(config);
			OauthGetTokenResponse oauthGetTokenResponse = oauth.getToken(code);
			openId = oauthGetTokenResponse.getOpenid();

			ResultObj resultObj = new ResultObj();
			// 绑定openId
			if (StringUtils.isNotBlank(openId)) {
				
				UserExtInfo userExtInfo = new UserExtInfo();
				userExtInfo.setPlatUserId(openId);
				userExtInfo.setStatus("1");
				userExtInfo = userExtInfoService.getEntity(userExtInfo);
				if(userExtInfo == null){
					CookieUtils.setCookie(response, Constants.COOKIE_WX_OPENID ,openId);
					resultObj.put("isBind", 0);
				}else{
					User user = userService.get(userExtInfo.getUserId()+"");
					if(type != null && type == UserType.BUYER.getType()){//代理商授信登录到了买手端，要验证权限
						result = checkLoginPromise(type, user.getRoleGroupIds());
						if(!result.isSuccess()){
							return result;
						}
					}
					
					resultObj.put("user", user);
					resultObj.put("isBind", 1);
					resultObj.put("token", createToken(user));
				}
			}else{
				resultObj.put("isBind", 0);
			}
			result.setObj(resultObj);
			result.setCode(ResultCode.SUCCESS);
			return result;
		} catch (Exception e) {
			LOGGER.error("微信授权失败：", e);
			return Result.newExceptionInstance();
		}
	}

	public static void main(String[] args) {
		User u = new User();
		u.setPassword("123456");
		u.setUsername("00001");
		u.setSalt("89dcf70ae4c0a8001521b6875aa44b16");
		System.out.println(new PasswordHelper().getPassword(u));
		System.out.println(DigestUtils.md5Hex("123456"));
	}

}
