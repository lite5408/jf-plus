package com.jf.plus.core.setting.controller;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jf.plus.common.core.Result;
import com.jf.plus.common.core.ResultCode;
import com.jf.plus.common.core.enums.SmsSendStatus;
import com.jf.plus.common.core.enums.SmsType;
import com.jf.plus.common.utils.CodeGen;
import com.jf.plus.core.setting.entity.SmsQueue;
import com.jf.plus.core.setting.entity.SmsTemplate;
import com.jf.plus.core.setting.service.SmsQueueService;
import com.jf.plus.core.setting.service.SmsTemplateService;

import cn.iutils.common.BaseController;

/**
 * 短信控制器
 * 
 * @author Tng
 *
 */
@Controller
@RequestMapping("${adminPath}/sms")
public class SMSController extends BaseController {

	@Autowired
	private SmsTemplateService smsTemplateService;

	@Autowired
	private SmsQueueService smsQueueService;

	@RequestMapping("/sendCode")
	@ResponseBody
	public Result sendCode(@RequestParam String mobile, @RequestParam String token, String type, String param,
			HttpServletRequest request) {
		Result result = new Result();

		try {
			if (StringUtils.isBlank(token)) {
				result.setMsg("操作超时,请刷新页面后重新操作");
				return result;
			}

			if (request.getSession().getAttribute("SMS_TOKEN") == null) {
				result.setMsg("操作超时,请刷新页面后重新操作");
				return result;
			}

			// 比对token是否一样
			if (!request.getSession().getAttribute("SMS_TOKEN").toString().equals(token)) {
				result.setMsg("操作超时,请刷新页面后重新操作");
				return result;
			}

			String regMsgCode = CodeGen.getSmsRegCode();

			SmsTemplate entity = new SmsTemplate();
			entity.setType(SmsType.CONFIRM_ORDER.getType());
			entity = smsTemplateService.getEntity(entity);
			if (entity == null) {
				result.setCode(ResultCode.RETURN_FAILURE);
				result.setMsg("短信模板未定义");
				return result;
			}

			String smsContent = templateToContent(entity.getContent(), regMsgCode);

			SmsQueue smsQueue = new SmsQueue();
			smsQueue.setMobile(mobile);
			smsQueue.setType(SmsType.CONFIRM_ORDER.getType());
			smsQueue.setContent(smsContent);
			smsQueue.setCreateBy("0");
			smsQueue.setCreateDate(new Date());
			smsQueue.setStatus(String.valueOf(SmsSendStatus.TO_SEND.getType()));
			smsQueue.setRemarks(regMsgCode);
			smsQueueService.save(smsQueue);

			// 发送短信
			smsQueueService.handleMessage(smsQueue);

			if (smsQueue != null && "1".equals(smsQueue.getStatus())) {
				result.setCode(ResultCode.SUCCESS);
				result.setMsg("操作成功");
				return result;
			}
			result.setCode(ResultCode.RETURN_FAILURE);
			result.setMsg("操作失败");
			return result;
		} catch (Exception e) {
			logger.error("发送验证码异常:{}", e);
			return Result.newExceptionInstance();
		}
	}

	protected static String templateToContent(String temp, String... val) {
		for (int i = 0; i < val.length; i++) {
			temp = temp.replace("【变量" + i + "】", val[i]);
		}
		return temp;
	}
}
