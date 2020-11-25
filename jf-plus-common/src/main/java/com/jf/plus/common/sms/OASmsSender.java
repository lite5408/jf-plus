package com.jf.plus.common.sms;

import java.net.URLEncoder;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jf.plus.common.utils.NetUtil;

import cn.iutils.common.config.JConfig;

/**
 *
 * @ClassName: OASmsSender
 * @Description: 短信接口
 * @author tangyh
 * @date 2016年4月15日 下午2:52:13
 *
 */
public class OASmsSender extends SmsSender {

	private static final Logger LOG = LoggerFactory.getLogger(OASmsSender.class);

	private String url = JConfig.getConfig("sms.oa.url");

	private String corpAccount = JConfig.getConfig("sms.oa.corpAccount");

	private String account = JConfig.getConfig("sms.oa.account");

	private String password = JConfig.getConfig("sms.oa.password");

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String getContent() {
		return content;
	}

	@Override
	public void setContent(String content) {
		this.content = content;
	}

	@Override
	public String getMobile() {
		return mobile;
	}

	@Override
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	@Override
	public boolean send() {
		boolean success = false;
		try {
			StringBuffer stringBuffer = new StringBuffer();
			stringBuffer.append("corpAccount=").append(corpAccount);
			stringBuffer.append("&userAccount=").append(account);
			stringBuffer.append("&pwd=").append(DigestUtils.md5Hex(password));
			stringBuffer.append("&mobile=").append(mobile);
			stringBuffer.append("&content=").append(URLEncoder.encode(content, "UTF-8"));
			String ret = NetUtil.postURLStr(url, stringBuffer.toString());
			if (StringUtils.isNotBlank(ret)) {
				if (ret.indexOf("1#") > -1) {
					success = true;
				}
			}
		} catch (Exception e) {
			LOG.error("短信发送异常：{}", e);
		}
		return success;
	}
}
