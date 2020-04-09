package com.fqh.activitiDemo.muitiAssignee;

import java.util.List;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngineConfiguration;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.junit.jupiter.api.Test;

/**
 * @author fqh
 * @Description: 分配审批人
 * @date 2020/4/6下午11:49
 */
public class ActivitiMuitiAssinee2Test {

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
                .addClasspathResource("multiAssignees2.bpmn")
                .addClasspathResource("multiAssignees2.png")
                .name("分配多位审批人-通过Listener").deploy();
        System.out.println(deploy.getId());
        System.out.println(deploy.getName());
        System.out.println(deploy.getDeploymentTime());
    }


    @Test
    public void start(){
        String processKey="mutiAssignee2";
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
        String assignee="赵六";
        List<Task> list = processEngine.getTaskService().createTaskQuery().taskCandidateUser(assignee).list();
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
        String taskId="90004";
        processEngine.getTaskService().complete(taskId);
    }
}
