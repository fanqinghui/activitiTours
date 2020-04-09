package com.fqh.activitiDemo.variable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngineConfiguration;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.junit.jupiter.api.Test;

/**
 * @author fqh
 * @Description: ${todo}
 * @date 2020/4/6下午11:49
 */
public class ActivitiVariable2Test {

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
                .addClasspathResource("activiti/studentLeave.bpmn")
                .addClasspathResource("activiti/studentLeave.png")
                .name("学生请假流程——变量").deploy();
        System.out.println(deploy.getId());
        System.out.println(deploy.getName());
        System.out.println(deploy.getDeploymentTime());
    }

    @Test
    public void start(){
        String processKey="studentLeaveProcess";
        Map<String,Object> variables=new HashMap<>();
        variables.put("flowName","流程名字");
        variables.put("fqh","clxl");
        ProcessInstance processInstance = processEngine.getRuntimeService()
            .startProcessInstanceByKey(processKey,variables);

        System.out.println(processInstance.getId());
        System.out.println(processInstance.getDescription());
        System.out.println(processInstance.getBusinessKey());
        System.out.println(processInstance.getName());
        System.out.println(processInstance.getProcessDefinitionKey());
        System.out.println(processInstance.getProcessDefinitionKey());
        System.out.println(processInstance.getProcessDefinitionVersion());
    }

    /**
     * 设置流程变量
     */
    @Test
    public void setRuntimeVariableValues (){
        String executionId="52501";
        RuntimeService runtimeService = processEngine.getRuntimeService();
        runtimeService.setVariable(executionId,"days",2);
        runtimeService.setVariable(executionId,"data","2020-05-01");
        runtimeService.setVariable(executionId,"reason","感冒了.。。");
        Student student=new Student(1L,"张三");
        runtimeService.setVariable(executionId, "student", student);
    }

    /**
     * 设置流程变量2--使用hashMap
     */
    @Test
    public void setRuntimeVariableValues2 (){
        String executionId="2501";
        RuntimeService runtimeService = processEngine.getRuntimeService();
        Map<String,Object> variables=new HashMap<>();
        variables.put("days",1 );
        variables.put("data","2020-04-08" );
        variables.put("reason","感冒了" );
        variables.put("student",new Student(1L,"张三"));
        runtimeService.setVariables(executionId, variables);;
    }

    /**
     * 设置流程变量
     */
    @Test
    public void getVariableValues (){
        String executionId="25001";
        RuntimeService runtimeService = processEngine.getRuntimeService();
        Integer days=(Integer) runtimeService.getVariable(executionId,"days");
        String data = runtimeService.getVariable(executionId, "data").toString();
        String reason = runtimeService.getVariable(executionId, "reason").toString();
        Student student=(Student) runtimeService.getVariable(executionId, "student");
        System.out.println("学生："+student+" 因为："+reason+" 从："+data +" 开始请假"+ days+"天");
    }

    @Test
    public void getInitVariableValues (){
        String taskId="30002";
        TaskService taskService = processEngine.getTaskService();
        Map<String, Object> variables = taskService.getVariables(taskId);
    }

    /**
     * 设置流程变量
     */
    @Test
    public void getVariableValues2 (){
        String excutionId="25001";
        RuntimeService runtimeService = processEngine.getRuntimeService();
        Map<String,Object> variables= runtimeService.getVariables(excutionId);
        Integer days=(Integer) variables.get("days");
        String data = variables.get("data").toString();
        String reason = variables.get( "reason").toString();
        Student student=(Student) variables.get( "student");
        System.out.println("学生："+student+" 因为："+reason+" 从："+data +" 开始请假"+ days+"天");
    }




    @Test
    public void taskingList(){
        String assignee="王五";
        List<Task> list = processEngine.getTaskService().createTaskQuery().taskAssignee(assignee).list();
        if(!list.isEmpty()){
            list.stream().forEach(task -> {
                System.out.println("任务ID："+task.getId());
                System.out.println("流程实例ID："+task.getProcessInstanceId());
                System.out.println("流程定义ID："+task.getProcessDefinitionId());
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
        String taskId="30002";
        processEngine.getTaskService().complete(taskId);
    }

    @Test
    public void complateTask2(){
        Map<String,Object> variables=new HashMap<>();
        variables.put("days",2 );
        variables.put("data","2020-04-08" );
        variables.put("reason","感冒了1111" );
        variables.put("student",new Student(2L,"张三1"));
        String taskId="25006";
        processEngine.getTaskService().complete(taskId,variables);
    }

}
