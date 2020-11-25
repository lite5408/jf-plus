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
//import com.jf.plus.common.exception.ServiceException;
//import com.jf.plus.core.order.service.OrderService;
//
//import cn.iutils.common.spring.SpringUtils;
//
//@Service
//public class AuditSecondListener implements TaskListener {
//
//	/**
//	 *
//	 */
//	private static final long serialVersionUID = 1L;
//
//	private static Logger LOGGER = LoggerFactory.getLogger(AuditSecondListener.class);
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
//			String orderId = activitiService.getProcessInstance(processInstanceId).getBusinessKey();
//			LOGGER.info("AuditSecondListener--流程：" + processInstanceId + ";" + delegateTask.getId() + ";" + orderId);
//			result = orderService.secondTask(processInstanceId,delegateTask.getId(), orderId);
//			if (!result.isSuccess()) {
//				LOGGER.error("AuditSecondListener异常:{}", result.getMsg());
//				throw new ServiceException(result.getMsg());
//			}
//		} catch (NumberFormatException e) {
//			LOGGER.error("AuditSecondListener异常:{}", e);
//			e.printStackTrace();
//		}
//	}
//
//}
