//package com.jf.activiti.listener;
//
//import org.activiti.engine.delegate.DelegateTask;
//import org.activiti.engine.delegate.TaskListener;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.stereotype.Service;
//
//import com.jf.activiti.ActivitiService;
//import com.jf.plus.core.order.service.OrderService;
//
//import cn.iutils.common.spring.SpringUtils;
//
//@Service
//public class AuditFinishListener implements TaskListener {
//
//	/**
//	 *
//	 */
//	private static final long serialVersionUID = 1L;
//
//	private static Logger LOGGER = LoggerFactory.getLogger(AuditFinishListener.class);
//
//	OrderService orderService;
//	ActivitiService activitiService;
//
//	@Override
//	public void notify(DelegateTask delegateTask) {
//		try {
//			orderService = SpringUtils.getBean(OrderService.class);
//			activitiService = SpringUtils.getBean(ActivitiService.class);
//			String taskId = delegateTask.getId();
//			String orderId = activitiService.getProcessInstance(delegateTask.getProcessInstanceId()).getBusinessKey();
//			orderService.finishTask(taskId, orderId);
//		} catch (Exception e) {
//			LOGGER.error("AuditFinishListener异常:{}", e);
//			e.printStackTrace();
//		}
//	}
//
//}
