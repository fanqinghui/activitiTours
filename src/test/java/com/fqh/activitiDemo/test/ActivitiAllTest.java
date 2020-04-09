package com.fqh.activitiDemo.test;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngineConfiguration;
import org.activiti.engine.TaskService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

/**
 * @author fqh
 * @Description: ${todo}
 * @date 2020/3/23上午9:31
 */
public class ActivitiAllTest {

    ProcessEngine processEngine;

    {
        System.out.println("init process begin");
        ProcessEngineConfiguration pec = ProcessEngineConfiguration.createStandaloneProcessEngineConfiguration();
        //数据库设置
        pec.setJdbcDriver("com.mysql.cj.jdbc.Driver");
        //nullCatalogMeansCurrent=true很关键，mysqlDrive8.0 默认把nullCatalogMeansCurrent改为false了
        pec.setJdbcUrl("jdbc:mysql://localhost:3306/demo2?autoReconnect=true&nullCatalogMeansCurrent=true");
        pec.setJdbcUsername("root");
        pec.setJdbcPassword("111111");
        pec.setDatabaseSchemaUpdate(ProcessEngineConfiguration.DB_SCHEMA_UPDATE_TRUE);
        processEngine = pec.buildProcessEngine();
        System.out.println("init process end");
    }

    @Test
    public void deployProcess() {
        Deployment deploy = processEngine.getRepositoryService().createDeployment().
                addClasspathResource("leave.bpmn").addClasspathResource("leave.png")
                .name("复习流程部署").deploy();
        Assert.assertNotNull(deploy);
        System.out.println(deploy.getTenantId());
        //startProcess(deploy.getId());
    }
    //启动流程
    public void startProcess(String processId) {
        System.out.println(processId);
        ProcessInstance my_first_process = processEngine.getRuntimeService().startProcessInstanceByKey("my_first_process");
        Assert.assertNotNull(my_first_process);
        System.out.println(my_first_process.getId());
        System.out.println(my_first_process.getName());
        System.out.println(my_first_process.getBusinessKey());
        System.out.println(my_first_process.getDescription());
        System.out.println(my_first_process.getTenantId());
    }

    //代办-fqh
    @Test
    public void workListAndDealProcess(){
        TaskService taskService = processEngine.getTaskService();
        List<Task> taskList = taskService.createTaskQuery().taskAssignee("admin").list();
        if(!taskList.isEmpty()){
            for(Task task:taskList){
                System.out.println(task.getId());
                System.out.println(task.getAssignee());
                System.out.println(task.getDelegationState());
                System.out.println(task.getCategory());
                System.out.println(task.getDueDate());
                System.out.println(task.getCreateTime());
                System.out.println(task.getProcessInstanceId());
                taskService.complete(task.getId());
            }
        }
    }
}
