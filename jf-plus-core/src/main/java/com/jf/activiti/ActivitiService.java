//package com.jf.activiti;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Map;
//
//import org.activiti.bpmn.model.BpmnModel;
//import org.activiti.bpmn.model.UserTask;
//import org.activiti.engine.FormService;
//import org.activiti.engine.HistoryService;
//import org.activiti.engine.IdentityService;
//import org.activiti.engine.RepositoryService;
//import org.activiti.engine.RuntimeService;
//import org.activiti.engine.TaskService;
//import org.activiti.engine.history.HistoricActivityInstance;
//import org.activiti.engine.history.HistoricProcessInstance;
//import org.activiti.engine.history.HistoricTaskInstance;
//import org.activiti.engine.history.HistoricVariableInstance;
//import org.activiti.engine.repository.Deployment;
//import org.activiti.engine.repository.ProcessDefinition;
//import org.activiti.engine.runtime.Execution;
//import org.activiti.engine.runtime.ProcessInstance;
//import org.activiti.engine.task.Task;
//import org.apache.commons.lang3.StringUtils;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import com.jf.plus.common.core.enums.ActivitiStatus;
//import com.jf.plus.core.order.entity.Order;
//
//import cn.iutils.common.Page;
//
//@Service
//// @Transactional(readOnly = true)
//public class ActivitiService {
//
//	@Autowired
//	RepositoryService repositoryService;
//	@Autowired
//	RuntimeService runtimeService;
//	@Autowired
//	FormService formService;
//	@Autowired
//	IdentityService identityService;
//	@Autowired
//	TaskService taskService;
//	@Autowired
//	HistoryService historyService;
//
//	/**
//	 * 查询部署列表
//	 */
//	public List<Deployment> findDeploymentList() {
//		List<Deployment> depList = repositoryService.createDeploymentQuery().list();
//		return depList;
//	}
//
//	/**
//	 * 查询流程定义列表
//	 */
//	public List<ProcessDefinition> findProcessDefinitionList() {
//		List<ProcessDefinition> pdList = repositoryService.createProcessDefinitionQuery().list();
//		return pdList;
//	}
//
//	/**
//	 * 查询流程实例对象
//	 *
//	 * @param processDefinitionKey
//	 *            流程定义key
//	 * @return
//	 */
//	public List<ProcessInstance> findProcessInstanceList(String processDefinitionKey) {
//		List<ProcessInstance> piList = runtimeService.createProcessInstanceQuery()
//				.processDefinitionKey(processDefinitionKey).orderByProcessInstanceId().desc().list();
//		return piList;
//	}
//
//	/**
//	 * 查询流程实例对象
//	 *
//	 * @param processDefinitionKey
//	 *            流程定义key
//	 * @return
//	 */
//	public ProcessInstance getProcessInstance(String processInstanceId) {
//		ProcessInstance processInstance = runtimeService.createProcessInstanceQuery()
//				.processInstanceId(processInstanceId).singleResult();
//		return processInstance;
//	}
//
//	/**
//	 * 查询流程实例对象
//	 *
//	 * @param processDefinitionKey
//	 *            流程定义key
//	 * @return
//	 */
//	public HistoricProcessInstance getHisProcessInstance(String processInstanceId) {
//		HistoricProcessInstance processInstance = historyService.createHistoricProcessInstanceQuery()
//				.processInstanceId(processInstanceId).singleResult();
//		return processInstance;
//	}
//
//	/**
//	 * 删除部署/定义信息
//	 *
//	 * @param deploymentId
//	 *            流程部署ID
//	 */
//	public void delDeployment(String deploymentId) {
//		repositoryService.deleteDeployment(deploymentId, true);
//	}
//
//	/**
//	 * 删除流程实例
//	 *
//	 * @param processInstanceId
//	 *            流程实例ID
//	 */
//	public void delProcessInstance(String processInstanceId) {
//		runtimeService.deleteProcessInstance(processInstanceId, "我愿意");
//	}
//
//	/**
//	 * 启动流程实例
//	 *
//	 * @param processKey
//	 *            流程定义的key
//	 * @param variables
//	 *            流程变量
//	 * @return 流程实例
//	 */
//	public ProcessInstance startProcessInstance(String processKey, String businessKey, Map<String, Object> variables) {
//		/** 启动流程实例的同时，设置流程变量，使用流程变量用来指定任务的办理人，对应task.pbmn文件中#{userID} */
//		ProcessInstance pi = runtimeService.startProcessInstanceByKey(processKey, businessKey, variables);// 使用流程定义的key启动流程实例，key对应helloworld.bpmn文件中id的属性值，使用key值启动，默认是按照最新版本的流程定义启动
//		return pi;
//	}
//
//	/**
//	 * 查询个人任务列表
//	 *
//	 * @param assignee
//	 *            办理人
//	 * @return 任务列表
//	 */
//	public List<Task> findTaskListByAssignee(String assignee, int firstResult, int maxResults) {
//		List<Task> taskList = new ArrayList<>();
//		if (StringUtils.isNotBlank(assignee))
//			taskList = taskService.createTaskQuery().taskAssignee(assignee)// 指定个人任务查询，指定办理人
//			.orderByTaskId().desc().listPage(firstResult, maxResults);
//		else
//			taskList = taskService.createTaskQuery().orderByTaskId().desc().listPage(firstResult, maxResults);
//		return taskList;
//	}
//
//	/**
//	 * 查询个人任务
//	 *
//	 * @param processInstanceId
//	 *            流程ID
//	 * @return
//	 */
//	public Task findTaskListByProcessInstanceId(String processInstanceId) {
//		Task task = taskService.createTaskQuery().processInstanceId(processInstanceId).singleResult();
//		return task;
//	}
//
//	/**
//	 * 完成个人任务
//	 *
//	 * @param taskId
//	 *            任务ID
//	 * @param variables
//	 *            流程变量
//	 */
//	public void completeTaskSuquest(String taskId, Map<String, Object> variables) {
//		taskService.complete(taskId, variables);
//
//	}
//
//	/**
//	 * 委派任务
//	 *
//	 * @param taskId
//	 *            任务ID
//	 * @param userId
//	 *            指定委派人ID
//	 */
//	public void setAssigneeTask(String taskId, String userId) {
//		taskService.setAssignee(taskId, userId);
//	}
//
//	/**
//	 * 流程向下执行
//	 *
//	 * @param executionId
//	 *            流程实例ID
//	 */
//	public void downward(String executionId) {
//		runtimeService.signal(executionId);
//	}
//
//	/**
//	 * 获取Execution对象列表
//	 *
//	 * @param processInstanceId
//	 *            流程ID
//	 */
//	public List<Execution> findExecutionList(String processInstanceId) {
//
//		List<Execution> executionList = runtimeService.createExecutionQuery().processInstanceId(processInstanceId) // 每个流程的唯一标识
//				.list();
//		return executionList;
//	}
//
//	/**
//	 * 获取流程变量
//	 *
//	 * @param taskId
//	 *            任务ID
//	 * @param variableName
//	 *            变量key
//	 * @return
//	 */
//	public String getVariable(String taskId, String variableName) {
//		return (String) taskService.getVariable(taskId, variableName);
//	}
//
//	/**
//	 * 获取历史流程变量
//	 *
//	 * @param processInstanceId
//	 *            流程ID
//	 * @param variableName
//	 *            变量名
//	 * @return
//	 */
//	public List<HistoricVariableInstance> findHistoryProcessVariables(String processInstanceId) {
//		return historyService.createHistoricVariableInstanceQuery().processInstanceId(processInstanceId).list();
//	}
//
//	/**
//	 * 查询历史任务
//	 *
//	 * @param type
//	 * @param userId
//	 * @return
//	 */
//	public List<HistoricTaskInstance> findHistoryTaskList(Integer type, String userId, Page<Order> page) {
//		int firstResult = page.getPageNo() * page.getPageSize();
//		int maxResults = page.getPageSize();
//		List<HistoricTaskInstance> list = new ArrayList<>();
//		if (type == ActivitiStatus.FINISHED.getType()) {
//			list = historyService.createHistoricTaskInstanceQuery().orderByExecutionId().desc().taskAssignee(userId)
//					.finished().listPage(firstResult, maxResults);
//		} else if (type == ActivitiStatus.UNFINISHED.getType()) {
//			list = historyService.createHistoricTaskInstanceQuery().orderByExecutionId().desc().taskAssignee(userId)
//					.unfinished().listPage(firstResult, maxResults);
//		} else {
//			list = historyService.createHistoricTaskInstanceQuery().orderByExecutionId().desc().taskAssignee(userId)
//					.listPage(firstResult, maxResults);
//		}
//		return list;
//	}
//
//	/**
//	 * 查询历史活动
//	 *
//	 * @param type
//	 * @param processInstanceId
//	 * @return
//	 */
//	public List<HistoricActivityInstance> findHistoryActInstanceList(Integer type, String processInstanceId) {
//		List<HistoricActivityInstance> list = new ArrayList<>();
//		if (type == ActivitiStatus.FINISHED.getType()) {
//			list = historyService.createHistoricActivityInstanceQuery() // 创建历史活动实例查询
//					.processInstanceId(processInstanceId) // 指定流程实例id
//					.finished() // 查询已经完成的任务
//					.list();
//		} else if (type == ActivitiStatus.UNFINISHED.getType()) {
//			list = historyService.createHistoricActivityInstanceQuery() // 创建历史活动实例查询
//					.processInstanceId(processInstanceId) // 指定流程实例id
//					.unfinished() // 查询未完成的任务
//					.list();
//		} else {
//			list = historyService.createHistoricActivityInstanceQuery() // 创建历史活动实例查询
//					.processInstanceId(processInstanceId) // 指定流程实例id
//					.list();
//		}
//		return list;
//	}
//
//	/**
//	 * 获取流程定义下所有Task节点
//	 *
//	 * @param processDefinitionId
//	 */
//	public List<UserTask> findProcessDefinition(String processDefinitionId) {
//		BpmnModel model = repositoryService.getBpmnModel(processDefinitionId);
//		return model.getMainProcess().findFlowElementsOfType(UserTask.class);
//	}
//
//}
