package com.fqh.activitiDemo.gateway;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngineConfiguration;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.junit.jupiter.api.Test;

/**
 * @author fqh
 * @Description: 排他网关测试
 * @date 2020/4/6下午11:49
 */
public class ActivitiExclusiveGatewayTest {

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
                .addClasspathResource("activiti/studentLeave3.bpmn")
                .addClasspathResource("activiti/studentLeave3.png")
                .name("学生请假流程——排他网关").deploy();
        System.out.println(deploy.getId());
        System.out.println(deploy.getName());
        System.out.println(deploy.getDeploymentTime());
    }


    @Test
    public void start(){
        String processKey="studentLeaveProcess3";
        /*Map<String,Object> maps=new HashMap<>();
        maps.put("days", 1);
        ProcessInstance processInstance = processEngine.getRuntimeService()
            .startProcessInstanceByKey(processKey,maps);*/

        ProcessInstance processInstance = processEngine.getRuntimeService()
            .startProcessInstanceByKey(processKey);
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
        String assignee="赵六";
        List<Task> list = processEngine.getTaskService().createTaskQuery().taskAssignee(assignee).list();
        if(!list.isEmpty()){
            list.stream().forEach(task -> {
                System.out.println(task.getId());
                System.out.println(task.getOwner());
                System.out.println(task.getAssignee());
                System.out.println(task.getName());
                System.out.println(task.getCreateTime());
                Integer days= (Integer) processEngine.getTaskService().getVariable(task.getId(), "days");
                System.out.println("请假："+days+"天");
            });
        }else {
            System.out.println("待审批任务为空");
        }
    }

    @Test
    public void complateTask(){
        String taskId="22504";
        processEngine.getTaskService().complete(taskId);
    }


    @Test
    public void complateTask1(){
        String taskId="17504";
        Map<String,Object> aviables=new HashMap<>();
        aviables.put("days",15);
        processEngine.getTaskService().complete(taskId,aviables);
    }
}
