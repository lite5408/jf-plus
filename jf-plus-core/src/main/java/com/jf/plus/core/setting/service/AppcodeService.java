package com.jf.plus.core.setting.service;

import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jf.plus.common.config.ApiConfig;
import com.jf.plus.common.core.Result;
import com.jf.plus.common.core.ResultCode;
import com.jf.plus.common.core.enums.Status;
import com.jf.plus.common.utils.DateUtils;
import com.jf.plus.core.setting.dao.AppcodeDao;
import com.jf.plus.core.setting.entity.Appcode;
import cn.iutils.common.service.CrudService;
import cn.iutils.sys.entity.User;

/**
 * 用户token表 Service层
 *
 * @author Tng
 * @version 1.0
 */
@Service
@Transactional(readOnly = true)
public class AppcodeService extends CrudService<AppcodeDao, Appcode> {

	/**
	 * 创建token
	 *
	 * @param user
	 * @return
	 */
	@Transactional(readOnly = false)
	public String createToken(User user) {
		String token = ApiConfig.getInstance().createSign(user.getUsername());

		Appcode appcode = new Appcode();
		appcode.setUserId(Long.valueOf(user.getId()));
		appcode.setToken(token);
		appcode.setExpiredDate(DateUtils.addHours(new Date(), 24*30));//一个月
		appcode.setMobile(user.getMobile());
		appcode.setCreateBy("0");
		appcode.setCreateDate(new Date());
		appcode.setUpdateBy("0");
		appcode.setUpdateDate(new Date());
		appcode.setStatus(String.valueOf(Status.NORMAL.getType()));
		dao.insert(appcode);
		return token;
	}

	/**
	 * 校验token
	 *
	 * @param token
	 * @return
	 */
	public Result checkToken(String token) {
		Result result = new Result();

		if (StringUtils.isBlank(token)) {
			result.setCode(ResultCode.TOKEN_TIMEOUT);
			result.setMsg("登录超时，请重新登录");
			return result;
		}

		Appcode appcode = new Appcode();
		appcode.setToken(token);
		appcode.setStatus(String.valueOf(Status.NORMAL.getType()));
		List<Appcode> appcodeList = dao.findList(appcode);
		if (CollectionUtils.isEmpty(appcodeList)) {
			result.setCode(ResultCode.TOKEN_TIMEOUT);
			result.setMsg("登录超时，请重新登录");
			return result;
		} else {
			appcode = appcodeList.get(0);
			if (appcode.getExpiredDate().getTime() < new Date().getTime()) {
				result.setCode(ResultCode.TOKEN_TIMEOUT);
				result.setMsg("登录超时，请重新登录");
				return result;
			}
			result.setObj(appcode.getUserId());
			result.setCode(ResultCode.SUCCESS);
			return result;
		}
	}

}
