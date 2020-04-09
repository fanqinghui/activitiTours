package com.fqh.activitiDemo.gateway;

import com.fqh.activitiDemo.variable.Student;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngineConfiguration;
import org.activiti.engine.TaskService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.junit.jupiter.api.Test;

/**
 * @author 排他网关测试
 * @Description: 普通网关测试
 * @date 2020/4/6下午11:49
 */
public class ActivitiGatewayTest {

    ProcessEngine processEngine;
    {
        ProcessEngineConfiguration pec = ProcessEngineConfiguration.createProcessEngineConfigurationFromResource("activiti.cfg.xml");
        processEngine = pec.buildProcessEngine();
    }
    /**
     * 部署请假流程
     */
    @Test
    public void deploy(){
        Deployment deploy = processEngine.getRepositoryService().createDeployment()
                .addClasspathResource("activiti/studentLeave2.bpmn")
                .addClasspathResource("activiti/studentLeave2.png")
                .name("学生请假流程——网关").deploy();
        System.out.println(deploy.getId());
        System.out.println(deploy.getName());
        System.out.println(deploy.getDeploymentTime());
    }

    /**
     * 设置流程变量
     */
    @Test
    public void setVariableValues (){
        String taskId="35004";
        TaskService taskService = processEngine.getTaskService();
        taskService.setVariable(taskId,"days",2);
        taskService.setVariable(taskId,"data","2020-04-01");
        taskService.setVariable(taskId,"reason","感冒了");
        Student student=new Student(1L,"张三");
        taskService.setVariable(taskId, "student", student);
    }

    /**
     * 设置流程变量
     */
    @Test
    public void getVariableValues (){
        String taskId="47502";
        TaskService taskService = processEngine.getTaskService();
        Integer days=(Integer) taskService.getVariable(taskId,"days");
        String data = taskService.getVariable(taskId, "data").toString();
        String reason = taskService.getVariable(taskId, "reason").toString();
        Student student=(Student) taskService.getVariable(taskId, "student");
        System.out.println("学生："+student+" 因为："+reason+" 从："+data +" 开始请假"+ days+"天");
    }



    @Test
    public void start(){
        String processKey="studentLeaveProcess";
        ProcessInstance processInstance = processEngine.getRuntimeService().startProcessInstanceByKey(processKey);
        System.out.println(processInstance.getId());
        System.out.println(processInstance.getDescription());
        System.out.println(processInstance.getBusinessKey());
        System.out.println(processInstance.getName());
        System.out.println(processInstance.getProcessDefinitionKey());
        System.out.println(processInstance.getProcessDefinitionKey());
        System.out.println(processInstance.getProcessDefinitionVersion());
    }

    @Test
    public void taskingList(){
        String assignee="王五";
        List<Task> list = processEngine.getTaskService().createTaskQuery().taskAssignee(assignee).list();
        if(!list.isEmpty()){
            list.stream().forEach(task -> {
                System.out.println(task.getId());
                System.out.println(task.getOwner());
                System.out.println(task.getAssignee());
                System.out.println(task.getName());
                System.out.println(task.getCreateTime());
            });
        }else {
            System.out.println("待审批任务为空");
        }
    }

    @Test
    public void complateTask(){
        String taskId="62504";
        processEngine.getTaskService().complete(taskId);
    }


    @Test
    public void complateTask1(){
        String taskId="55002";
        Map<String,Object> aviables=new HashMap<>();
        aviables.put("msg","重要情况");
        processEngine.getTaskService().complete(taskId,aviables);
    }

    @Test
    public void complateTask2(){
        Map<String,Object> aviables=new HashMap<>();
        aviables.put("msg","一般情况");
        String taskId="65002";
        processEngine.getTaskService().complete(taskId,aviables);
    }

}
