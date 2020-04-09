package com.fqh.activitiDemo.studentLeave;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngineConfiguration;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.junit.jupiter.api.Test;

import java.util.List;

/**
 * @author fqh
 * @Description: ${todo}
 * @date 2020/4/2下午11:17
 */
public class TestStudentLeave {

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
                .name("学生请假流程").deploy();
        System.out.println(deploy.getId());
        System.out.println(deploy.getName());
        System.out.println(deploy.getDeploymentTime());
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

    /**
     * 完成任务
     */
    @Test
    public void complateTask(){
        processEngine.getTaskService().complete("20002");
    }

    /**
     * 查询任务
     */
    @Test
    public void findTodoProcessInstance(){
        ProcessInstance processInstance = processEngine.getRuntimeService()
                .createProcessInstanceQuery()
                .processInstanceId("15001")
                .singleResult();
        if(processInstance!=null){
            System.out.println("正在运行");
            System.out.println(processInstance.toString());
        }else{
            System.out.println("已经结束");
        }
    }

    /**
     * 历史流程列表
     */
    @Test
    public void historyTaskList(){
        List<HistoricTaskInstance> list = processEngine.getHistoryService()
                .createHistoricTaskInstanceQuery()
                .processInstanceId("15001")
                .finished().list();
        if(list.isEmpty()){
            System.out.println("空历史记录");
        }else{
            list.stream().forEach(his->{
                System.out.print("id:"+his.getId());
                System.out.print(" name:"+his.getName());
                System.out.print(" assignee:"+his.getAssignee());
                System.out.print(" owener:"+his.getOwner());
                System.out.print(" startTime:"+his.getStartTime());
                System.out.print(" endTime:"+his.getEndTime());
                System.out.println("");
            });
        }

    }

    /**
     * 历史流程列表
     */
    @Test
    public void historyProcessInstanceList(){
        List<HistoricProcessInstance> list = processEngine.getHistoryService().createHistoricProcessInstanceQuery().processInstanceId("15001").finished().list();
        if(list.isEmpty()){
            System.out.println("空历史记录");
        }else{
            list.stream().forEach(his->{
                System.out.print("id:"+his.getId());
                System.out.print(" name:"+his.getName());
                System.out.print(" startTime:"+his.getStartTime());
                System.out.print(" endTime:"+his.getEndTime());
                System.out.println("");
            });
        }

    }


}
