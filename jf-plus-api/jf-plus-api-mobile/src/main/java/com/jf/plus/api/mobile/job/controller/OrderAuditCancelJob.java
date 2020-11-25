//package com.jf.plus.api.mobile.job.controller;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.stereotype.Component;
//
//import com.jf.plus.core.order.service.OrderAuditService;
//import com.jf.plus.core.order.service.OrderService;
//
///**
// * 订单过期自动取消
// * 
// */
//@Component
//public class OrderAuditCancelJob {
//	@Autowired
//	OrderAuditService orderAuditService;
//
//	@Autowired
//	OrderService orderService;
//
//	private final static Logger LOGGER = LoggerFactory.getLogger(OrderAuditCancelJob.class);
//
//	/**
//	 * 每天凌晨12:30执行
//	 * 
//	 * <pre>
//	 * 最近5次运行时间: 
//	 * 2017/9/27 0:30:00 
//	 * 2017/9/28 0:30:00 
//	 * 2017/9/29 0:30:00 
//	 * 2017/9/30 0:30:00 
//	 * 2017/10/1 0:30:00
//	 * </pre>
//	 */
//	@Scheduled(cron = "0 30 0 * * ?")
//	public void execute() {
//		
//	}
//}
