//package com.jf.activiti.listener;
//
//import org.activiti.engine.delegate.DelegateTask;
//import org.activiti.engine.delegate.TaskListener;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.stereotype.Service;
//
//import com.jf.activiti.ActivitiService;
//import com.jf.plus.common.core.Result;
//import com.jf.plus.common.core.enums.ActivitiKey;
//import com.jf.plus.common.exception.ServiceException;
//import com.jf.plus.core.order.service.OrderService;
//
//import cn.iutils.common.spring.SpringUtils;
//
//@Service
//public class AuditFirstListener implements TaskListener {
//
//	/**
//	 *
//	 */
//	private static final long serialVersionUID = 1L;
//
//	private static Logger LOGGER = LoggerFactory.getLogger(AuditFirstListener.class);
//
//	OrderService orderService;
//	ActivitiService activitiService;
//
//	@Override
//	public void notify(DelegateTask delegateTask) {
//		try {
//			Result result = Result.newInstance();
//			orderService = SpringUtils.getBean(OrderService.class);
//			activitiService = SpringUtils.getBean(ActivitiService.class);
//			String processInstanceId = delegateTask.getProcessInstanceId();
//			String orderId = (String) delegateTask.getVariable(ActivitiKey.ORDERID.getType());
//			LOGGER.info("AuditFirstListener--流程：" + processInstanceId + ";" + delegateTask.getId() + ";" + orderId);
//			result = orderService.firstTask(processInstanceId, delegateTask.getId(), orderId);
//			if (!result.isSuccess()) {
//				LOGGER.error("AuditFirstListener异常:{}", result.getMsg());
//				throw new ServiceException(result.getMsg());
//			}
//		} catch (NumberFormatException e) {
//			LOGGER.error("AuditFirstListener异常:{}", e);
//			throw new ServiceException(e.getMessage());
//		}
//
//	}
//
//}
