//package server;
//
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//
//import com.jf.activiti.ActivitiService;
//
//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations = { "classpath*:/springmvc-servlet.xml", "classpath*:/applicationContext.xml" })
//public class Junit4Test {
//
//	@Autowired
//	ActivitiService activitiService;
//
//	@Test
//	public void Test1() {
//
//		//		List<Deployment> list = activitiService.findDeploymentList();
//		//		for (Deployment deployment : list) {
//		//			activitiService.delDeployment(deployment.getId());
//		//		}
//
//		//		activitiService.findProcessDefinitionList();
//
//		//		activitiService.startProcessInstance("myaudit", "businessKey1",null);
//
//		//		activitiService.findProcessInstanceList("myaudit");
//
//		// activitiService.delProcessInstance("30001");
//
//		//		activitiService.findTaskListByProcessInstanceId("90005");
//
//		//		activitiService.findTaskListByAssignee(null);
//
//		//		Map<String, Object> variables = new HashMap<>();
//		//		variables.put("flag", "true");
//		//		variables.put("admin1", "跳跳糖");
//		//		activitiService.completeTaskSuquest("120004", variables);
//
//		//		activitiService.findTaskListByAssignee("跳跳糖");
//
//		//		Map<String, Object> variables = new HashMap<>();
//		//		variables.put("audit1", "false");
//		//		activitiService.completeTaskSuquest("122505", variables);
//
//		//		activitiService.findTaskListByAssignee(null);
//
//		//		activitiService.findExecutionList("120001");
//		//		activitiService.downward("120001");
//	}
//
//}
