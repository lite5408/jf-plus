//package com.jf.activiti.listener;
//
//import org.activiti.engine.delegate.DelegateTask;
//import org.activiti.engine.delegate.TaskListener;
//import org.activiti.engine.runtime.ProcessInstance;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.stereotype.Service;
//
//import com.jf.activiti.ActivitiService;
//import com.jf.plus.common.core.enums.ActivitiKey;
//import com.jf.plus.common.core.enums.ActivitiValue;
//import com.jf.plus.common.core.enums.OrderAuditStatus;
//import com.jf.plus.core.order.service.OrderService;
//
//import cn.iutils.common.spring.SpringUtils;
//
//@Service
//public class AuditCancelListener implements TaskListener {
//
//	/**
//	 *
//	 */
//	private static final long serialVersionUID = 1L;
//
//	private static Logger LOGGER = LoggerFactory.getLogger(AuditCancelListener.class);
//
//	ActivitiService activitiService;
//	OrderService orderService;
//
//	@Override
//	public void notify(DelegateTask delegateTask) {
//		try {
//			activitiService = SpringUtils.getBean(ActivitiService.class);
//			orderService = SpringUtils.getBean(OrderService.class);
//			int status = 0;
//			ProcessInstance processInstance = activitiService.getProcessInstance(delegateTask.getProcessInstanceId());
//			String orderId = processInstance.getBusinessKey();
//			if (ActivitiValue.TRUE.getType().equals(delegateTask.getVariable(ActivitiKey.CANCEL.getType())))
//				status = OrderAuditStatus.CANCEL.getType();
//			else if (ActivitiValue.TRUE.getType().equals(delegateTask.getVariable(ActivitiKey.EXPIRED.getType())))
//				status = OrderAuditStatus.EXPIRE.getType();
//			else
//				status = OrderAuditStatus.NOTPASS.getType();
//			orderService.orderCancel(delegateTask.getId(), Long.valueOf(orderId), status,
//					(String) delegateTask.getVariable(ActivitiKey.REASONS.getType()));
//		} catch (Exception e) {
//			LOGGER.error("AuditCancelListener异常:{}", e);
//			e.printStackTrace();
//		}
//	}
//
//}
