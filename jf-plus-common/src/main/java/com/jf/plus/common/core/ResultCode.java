package com.jf.plus.common.core;

/**
 * @author tangyh
 * @date 2016-01-14
 *
 */
public class ResultCode {
	// ********************* 公共层返回码 *********************
	public static final int SUCCESS = 200; // 成功

	public static final int RETURN_FAILURE = 400; // 失败

	public static final int FAILURE_EXISTS = 203; // 对象已存在

	public static final int FAILURE_NOT_EXISTS = 204; // 对象不存在

	public static final int LIKE_EXPIRED_ERROR = 401; // 链接失效,即链接超时

	public static final int PRIMARY_KEY_ERROR = 402; // 秘钥错误

	public static final int ILLEGAL_ARGUMENT_ERROR = 403; // 传递参数不合法

	public static final int ARGUMENT_LACK_ERROR = 405; // 参数缺失错误,不完整

	public static final int SING_ERROR = 406; // 数字签名有误

	public static final int XML_JSON_ERROR = 407; // 解析XML/JSON出错

	public static final int NOT_DATA = 408; // 暂无数据

	public static final int NETWORK_ERROR = 409; // 网络错误

	public static final int UP_MAXLENGTH = 410;// 超出最大长度

	public static final int MSG_INACTIVE = 411;// 消息失效

	public static final int TOKEN_FAILURE = 412;// token验证失败

	public static final int VERSION_ERROR = 413;// version不正确

	public static final int VERSION_NOT_CHANGE = 414;// version没有改变

	public static final int OPER_LIMIT = 415; // 超过最大次数限制

	public static final int TOKEN_TIMEOUT = 416; //TOKEN失效

	public static final int AMOUNT_LESS = 417;	//余额不足

	public static final int SERVICE_EXCEPTION = 500; // 服务错误

	public static final int NOT_LOGIN = 600;//未登录
}
