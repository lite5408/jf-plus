package com.jf.plus.core.setting.service;

import java.util.Date;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.iutils.common.service.CrudService;
import cn.iutils.common.utils.DateUtils;

import com.jf.plus.common.core.enums.SmsSendStatus;
import com.jf.plus.common.sms.SmsSender;
import com.jf.plus.common.sms.SmsSenderFactory;
import com.jf.plus.core.setting.dao.SmsQueueDao;
import com.jf.plus.core.setting.entity.SmsQueue;

/**
 * 短信队列表 Service层
 *
 * @author Tng
 * @version 1.0
 */
@Service
@Transactional(readOnly = true)
public class SmsQueueService extends CrudService<SmsQueueDao, SmsQueue> {

	@Transactional(readOnly = false)
	public void handleMessage(SmsQueue smsQueue) {
		if (smsQueue != null) {
			SmsSender smsSender = SmsSenderFactory.buildOASmsSender();
			smsSender.setContent(smsQueue.getContent());
			smsSender.setMobile(smsQueue.getMobile());
			boolean success = smsSender.send();
			if (success) {
				smsQueue.setStatus(String.valueOf(SmsSendStatus.SEND.getType()));
				smsQueue.setExpiredTime(DateUtils.addHours(new Date(), 2));
			} else {
				smsQueue.setStatus(String.valueOf(SmsSendStatus.FAIL_SEND.getType()));
			}
			smsQueue.setProcessTime(new Date());
			save(smsQueue);
		}
	}

}
