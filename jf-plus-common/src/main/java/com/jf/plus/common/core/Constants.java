package com.jf.plus.common.core;

import com.jf.plus.common.config.Global;

/**
 * @ClassName: Constants
 * @Description: 常量类
 * @author Tng
 * @date 2016年1月30日 下午12:01:55
 *
 */

public class Constants {

	public final static Integer RESOURCE_MENU = 0; // 菜单

	public final static Integer RESOURCE_BUTTON = 1; // 按钮

	public static final int DEFAULT_PAGE_SIZE = 100; // 默认显示的页数

	public final static String GYJ = "supAmount"; // 总供应价

	public final static String XSJ = "payAmount"; // 总供应价

	public final static String PROCESSID = Global.getConfig("processid");

	/**
	 * session用户变量名
	 */
	public final static String SESSION_USER = "SESSION_USER";
	/**
	 * session 会员变量名
	 */
	public final static String SESSION_MEMBER = "SESSION_MEMBER";

	/**
	 * session 微信用戶信息变量名
	 */
	public final static String SESSION_WX_USERINFO = "SESSION_WX_USERINFO";

	/**
	 * APPKEY
	 */
	public final static String APP_KEY = Global.getConfig("app.appkey");

	public static final String REDIS_KEY_PREFIX = Global.getConfig("redis.prefixKey");

	/**
	 * 上传文件主目录
	 */
	public static final String UPLOAD_HOME_PATH = Global.getConfig("image.path.home");

	public static final String UPLOAD_ARTICLE_PATH = Global.getConfig("image.path.article");

	public static final String UPLOAD_BANNER_PATH = Global.getConfig("image.path.banner");

	public static final String UPLOAD_COURSE_PATH = Global.getConfig("image.path.course");

	public static final String UPLOAD_USER_PATH = Global.getConfig("image.path.user");

	public static final String UPLOAD_QRCODE_PATH = Global.getConfig("image.path.qrcode");

	/**
	 * 商品图片目录
	 */
	public static final String UPLOAD_PRODUCT_PATH = Global.getConfig("image.path.product");
	/**
	 * 商户图片目录
	 */
	public static final String UPLOAD_MERCHANT_PATH = Global.getConfig("image.path.merchant");
	/**
	 * 礼包图片目录
	 */
	public static final String UPLOAD_PACKSINFO_PATH = Global.getConfig("image.path.packsinfo");
	/**
	 * 商品分类图标目录
	 */
	public static final String UPLOAD_CATE_PATH = Global.getConfig("image.path.cate");
	/**
	 * 广告图片目录
	 */
	public static final String UPLOAD_ADVERT_PATH = Global.getConfig("image.path.advert");
	/**
	 * 专题图片目录
	 */
	public static final String UPLOAD_SPECIAL_PATH = Global.getConfig("image.path.special");
	/**
	 * 移动端域名
	 */
	public final static String URL_MOBILE = Global.getConfig("url.mobile");

	/**
	 * 移动端域名
	 */
	public final static String URL_WEB = Global.getConfig("url.web");

	/**
	 * 图片服务域名
	 */
	public final static String URL_IMAGE = Global.getConfig("url.image");

	/**
	 * 微信支付统一授权URL
	 */
	public final static String URL_WX_AUTH = Global.getConfig("url.wxauth");

	/**
	 * 微信支付统一授权URL
	 */
	public final static String URL_WXPAY = Global.getConfig("url.wxpay");

	/**
	 * 签到二维码
	 */
	public static final String URL_SIGNIN = Global.getConfig("url.sigin");

	/**
	 * 微信open id
	 */
	public static final String COOKIE_WX_OPENID = "WX_OPEN_ID";

	public static final String EXCEPTION_MSG = "系统繁忙，请稍后再试";

	/**
	 * 超级管理员角色ID
	 */
	public static final Long ADMIN_ROLE_ID = 1L;

	/**
	 * 购物车后缀
	 */
	public static final String CART_SUFFIX = "_SHOP_CART";

	/**
	 * token过期
	 */
	public static final String TOKEN_EXPIRED = "token_expired";

	public static final String ALIDAYU_URL = Global.getConfig("alidayu.url");

	public static final String ALIDAYU_APPKEY = Global.getConfig("alidayu.appkey");

	public static final String ALIDAYU_SECRET = Global.getConfig("alidayu.secret");

	public static final String ALIDAYU_SIGN_NAME = Global.getConfig("alidayu.signname");

	public static final String ALIDAYU_TMPID = Global.getConfig("alidayu.tmpid");

	/**
	 * 获取微信appId
	 *
	 */
	public final static String APPID = Global.getConfig("wx.appId");

	/**
	 * 获取微信appSecret
	 *
	 */
	public final static String APPSECRET = Global.getConfig("wx.appSecret");
	/**
	 * 微信商戶Id
	 *
	 */
	public final static String MCHID = Global.getConfig("wx.mchId");
	/**
	 * 微信商戶Key
	 *
	 */
	public final static String MCHKEY = Global.getConfig("wx.mchKey");
	/**
	 * 获取微信token
	 *
	 */
	public final static String WXTOKEN = Global.getConfig("wx.token");

	/**
	 * 获取对接第三方URL
	 *
	 */
	public final static String WXURL = Global.getConfig("wx.url");
	/**
	 * 获取微信aeskey
	 *
	 */
	public final static String WXAESKEY = Global.getConfig("wx.aeskey");

	public static final String PERSON_COMPANY = "个人";

	public static final String FULL_USER_INFO = URL_WEB + "/Perfect";

	/**
	 * 京东商品接口参数
	 */
	public static final String JD_APP_KEY = Global.getConfig("jd.app_key");

	public static final String JD_APP_SECRET = Global.getConfig("jd.app_secret");

	public static final String JD_USERNAME = Global.getConfig("jd.username");

	public static final String JD_PASSWORD = Global.getConfig("jd.password");

	public static final String JD_ERROR_RESPONSE = "errorResponse";
	
	public static final String JD_CLIENT_ID = Global.getConfig("jd.client_id");
	
	public static final String JD_CLIENT_SECRET = Global.getConfig("jd.client_secret");

	/**
	 * 京东忽略商品的字段
	 */
	public static final String JD_IGNORE_PRODUCT = Global.getConfig("jd.ignore_product");
	
	/**
	 * 京东图片地址
	 */
	public final static String JD_URL_IMAGE = Global.getConfig("jd.image.url");

	/**
	 * 严选接口参数
	 */
	public static final String YX_URL = Global.getConfig("yx.url");

	public static final String YX_APP_KEY = Global.getConfig("yx.appkey");

	public static final String YX_APP_SECRET = Global.getConfig("yx.appSecret");

	/**
	 * 苏宁接口参数
	 */

	public static final String SN_URL = Global.getConfig("sn.url");

	public static final String SN_APP_KEY = Global.getConfig("sn.appkey");

	public static final String SN_APP_SECRET = Global.getConfig("sn.appSecret");

	public static final String SN_FLAG = Global.getConfig("sn.flag");
	
	public static final String SN_COMPANY_TITLE = Global.getConfig("sn.company");
	
	/**
	 * 苏宁忽略商品的字段
	 */
	public static final String SN_IGNORE_PRODUCT = Global.getConfig("sn.ignore_product");

	/**
	 * 充值接口参数
	 */
	public static final String CZ_URL = Global.getConfig("cz.url");

	public static final String CZ_MERCHANT_ID = Global.getConfig("cz.merchantid");

	public static final String CZ_API_KEY = Global.getConfig("cz.apikey");

	/**
	 * 齐心接口参数
	 */

	public static final String QX_URL = Global.getConfig("qx.url");

	public static final String QX_SUPCODE = Global.getConfig("qx.supcode");

	public static final String QX_TOKEN = Global.getConfig("qx.token");

	public static final String QX_SIGN_KEY = Global.getConfig("qx.signkey");

	public static final String QX_SYSTEMID = Global.getConfig("qx.systemid");

	public static final String QX_SYSTEMNAME = Global.getConfig("qx.systemname");

	public static final String QX_COMP_NAME = Global.getConfig("qx.compname");

	/**
	 * 自动分发
	 */
	public static final String ZDFF_PREFIX = "ZDFF";

	/**
	 * 电子券
	 */
	public static final String VOUCHER_SEQ = "voucher";
	
	/**
	 * 礼包
	 */
	public static final String PACKS_SEQ = "packs";
	
	/**
	 * 商品
	 */
	
	public static final String PRODUCT_SEQ="product";
	
	/**
	 * 规格
	 */
	public static final String SKU_SEQ = "sku";

	/**
	 * 京东预占库存有效期
	 */
	public static final Integer JD_VALIDATE = 30;

	/**
	 * 一事通公钥
	 */
	public static final String SSOPKIDEV = "classpath:SsoPKIDEV.cer";

}
