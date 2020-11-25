//package com.jf.plus.api.mobile.activiti.controller;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//
//import org.activiti.bpmn.model.UserTask;
//import org.activiti.engine.history.HistoricActivityInstance;
//import org.activiti.engine.history.HistoricProcessInstance;
//import org.activiti.engine.history.HistoricTaskInstance;
//import org.activiti.engine.history.HistoricVariableInstance;
//import org.apache.commons.lang3.StringUtils;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.ResponseBody;
//
//import com.jf.activiti.ActivitiService;
//import com.jf.plus.api.mobile.controller.BaseController;
//import com.jf.plus.common.core.Result;
//import com.jf.plus.common.core.ResultCode;
//import com.jf.plus.common.core.ResultObj;
//import com.jf.plus.common.core.enums.ActivitiKey;
//import com.jf.plus.common.core.enums.ActivitiStatus;
//import com.jf.plus.common.core.enums.ActivitiValue;
//import com.jf.plus.common.core.enums.BoolStatus;
//import com.jf.plus.common.vo.MyProcessVo;
//import com.jf.plus.core.order.entity.Order;
//import com.jf.plus.core.order.entity.OrderAudit;
//import com.jf.plus.core.order.entity.OrderDetail;
//import com.jf.plus.core.order.service.OrderDetailService;
//import com.jf.plus.core.order.service.OrderService;
//
//import cn.iutils.common.Page;
//import cn.iutils.common.utils.MPageInfo;
//import cn.iutils.sys.service.UserService;
//
//@Controller
//public class ActivitiController extends BaseController {
//
//	private static Logger LOGGER = LoggerFactory.getLogger(ActivitiController.class);
//
//	@Autowired
//	private ActivitiService activitiService;
//
//	@Autowired
//	private OrderService orderService;
//
//	@Autowired
//	private OrderDetailService orderDetailService;
//
//	@Autowired
//	private UserService userService;
//
//	/**
//	 * 查询任务列表
//	 *
//	 * @param token
//	 * @param status
//	 * @param page
//	 * @return
//	 */
//	@RequestMapping(value = "/api/act/findHistoryTaskList", method = { RequestMethod.POST })
//	@ResponseBody
//	public Result findHistoryTaskList(@RequestParam String token, OrderAudit orderAudit, Page<Order> page) {
//		Result result = Result.newInstance();
//		try {
//			result = this.checkToken(token); // 验证token有效性
//			if (result.getCode().intValue() != ResultCode.SUCCESS)
//				return result;
//			List<HistoricTaskInstance> taskList = activitiService.findHistoryTaskList(orderAudit.getAuditStatus(),
//					((Long) result.getObj()).toString(), page);
//			StringBuilder items = new StringBuilder();
//			for (HistoricTaskInstance historicTaskInstance : taskList) {
//				items.append(historicTaskInstance.getProcessInstanceId());
//				items.append(",");
//			}
//			if (StringUtils.isNotBlank(items)) {
//				Order orderQuery = new Order();
//				orderQuery.setItems(items.toString().split(","));
//				List<Order> orderList = orderService.findList(orderQuery);
//				for (Order order : orderList) {
//					Page<OrderDetail> dPage = new Page<>(0, 3);
//					dPage.getCondition().put("orderNo", order.getOrderNo());
//					order.setOrderDetailList(orderDetailService.findPage(dPage));
//				}
//				page.setList(orderList);
//			}
//			ResultObj resultObj = new ResultObj();
//			resultObj.put("page", MPageInfo.transform(page));
//			result.setObj(resultObj);
//			result.setCode(ResultCode.SUCCESS);
//			return result;
//		} catch (Exception e) {
//			LOGGER.error("获取任务列表失败：", e);
//			result = Result.newExceptionInstance();
//			return result;
//		}
//	}
//
//	/**
//	 * 查询活动列表
//	 *
//	 * @param processInstanceId
//	 * @param status
//	 * @param page
//	 * @return
//	 */
//	@RequestMapping(value = "/api/act/findHistoryActInstanceList", method = { RequestMethod.POST })
//	@ResponseBody
//	public Result findHistoryActInstanceList(@RequestParam String processInstanceId) {
//		Result result = Result.newInstance();
//		try {
//			List<HistoricActivityInstance> actList = activitiService
//					.findHistoryActInstanceList(ActivitiStatus.ALL.getType(), processInstanceId);
//			List<MyProcessVo> voList = createVo(processInstanceId, actList);
//			ResultObj resultObj = new ResultObj();
//			resultObj.put("voList", voList);
//			result.setObj(resultObj);
//			result.setCode(ResultCode.SUCCESS);
//			return result;
//		} catch (Exception e) {
//			LOGGER.error("获取活动列表失败：", e);
//			result = Result.newExceptionInstance();
//			return result;
//		}
//	}
//
//	/**
//	 * 创建流程图VO
//	 *
//	 * @param processInstanceId
//	 * @param actList
//	 * @return
//	 */
//	private List<MyProcessVo> createVo(String processInstanceId, List<HistoricActivityInstance> actList) {
//
//		String isLast = "";
//		int count = 0;
//		HashMap<String, String> activityMap = new HashMap<>();
//		HashMap<String, String> variableMap = new HashMap<>();
//
//		for (HistoricActivityInstance historicActivityInstance : actList) {
//			activityMap.put(historicActivityInstance.getActivityName(), historicActivityInstance.getAssignee());
//			if (historicActivityInstance.getEndTime() == null) {
//				isLast = actList.get(actList.size() - 1).getActivityName();
//			}
//		}
//
//		List<MyProcessVo> voList = new ArrayList<>();
//		MyProcessVo vo = null;
//
//		// 获取流程变量
//		List<HistoricVariableInstance> vList = activitiService.findHistoryProcessVariables(processInstanceId);
//		for (HistoricVariableInstance historicVariableInstance : vList) {
//			variableMap.put(historicVariableInstance.getVariableName(), historicVariableInstance.getValue().toString());
//		}
//
//		String audit1 = variableMap.get(ActivitiKey.AUDIT_1.getType());
//		String audit2 = variableMap.get(ActivitiKey.AUDIT_2.getType());
//		String cancel = variableMap.get(ActivitiKey.CANCEL.getType());
//
//		HistoricProcessInstance processInstance = activitiService.getHisProcessInstance(processInstanceId);
//		if (processInstance != null) {
//			String processDefinitionId = processInstance.getProcessDefinitionId();
//			List<UserTask> taskList = activitiService.findProcessDefinition(processDefinitionId);
//			for (UserTask userTask : taskList) {
//				if (count > 2)
//					break;
//				vo = new MyProcessVo();
//				vo.setId(userTask.getId());
//				vo.setName(userTask.getName());
//				if (StringUtils.isNotBlank(activityMap.get(userTask.getName())))
//					vo.setAssignee(userService.get(activityMap.get(userTask.getName())).getName());
//				if (userTask.getName().equals("采购员下单")) {
//					if (ActivitiValue.TRUE.getType().equals(cancel)) {
//						vo.setStatus(ActivitiKey.CANCEL.getDescription());
//						vo.setIsLast(BoolStatus.YES.getType());
//					}
//				} else if (userTask.getName().equals("一级审批")) {
//					if (ActivitiValue.TRUE.getType().equals(audit1))
//						vo.setStatus(ActivitiValue.TRUE.getDescription());
//					else if (ActivitiValue.FALSE.getType().equals(audit1) && StringUtils.isBlank(cancel)) {
//						vo.setStatus(ActivitiValue.FALSE.getDescription());
//						vo.setIsLast(BoolStatus.YES.getType());
//					}
//				} else if (userTask.getName().equals("二级审批")) {
//					if (ActivitiValue.TRUE.getType().equals(audit2))
//						vo.setStatus(ActivitiValue.TRUE.getDescription());
//					else if (ActivitiValue.FALSE.getType().equals(audit2)) {
//						vo.setStatus(ActivitiValue.FALSE.getDescription());
//						vo.setIsLast(BoolStatus.YES.getType());
//					}
//				}
//				if (StringUtils.isNotBlank(isLast))
//					if (isLast.equals(userTask.getName()))
//						vo.setIsLast(BoolStatus.YES.getType());
//					else
//						vo.setIsLast(BoolStatus.NO.getType());
//				voList.add(vo);
//				count++;
//			}
//		}
//		return voList;
//	}
//
//}
