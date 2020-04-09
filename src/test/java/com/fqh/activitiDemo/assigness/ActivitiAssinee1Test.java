package com.fqh.activitiDemo.assigness;

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
 * @Description: 分配审批人-通过启动传递参数userId分配
 * @date 2020/4/6下午11:49
 */
public class ActivitiAssinee1Test {

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
                .addClasspathResource("changeAssignee.bpmn")
                .addClasspathResource("changeAssignee.png")
                .name("分配审批人").deploy();
        System.out.println(deploy.getId());
        System.out.println(deploy.getName());
        System.out.println(deploy.getDeploymentTime());
    }


    @Test
    public void start(){
        Map<String,Object> variables=new HashMap<>();
        variables.put("userId","老王");
        String processKey="changeAssignee";
        ProcessInstance processInstance = processEngine.getRuntimeService().startProcessInstanceByKey(processKey,variables);
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
        String assignee="老王";
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
        String taskId="45005";
        processEngine.getTaskService().complete(taskId);
    }
}
