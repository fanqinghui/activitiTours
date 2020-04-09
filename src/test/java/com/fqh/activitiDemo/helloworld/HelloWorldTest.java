package com.fqh.activitiDemo.helloworld;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class HelloWorldTest {

    ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();

    @Test
    void firstProcess() {
        //获取默认流程引擎配置，会自动读取activiti.cfg.xml
        deploy();
        //start();
    }


    void deploy() {
        //部署完毕会把数据存入数据库
        Deployment deploy = processEngine.getRepositoryService()//获取相关service
                .createDeployment()//创建部署
                .addClasspathResource("upload.bpmn")//加载资源文件
                .addClasspathResource("upload.png")
                .name("数据上传")
                .deploy();
        System.out.println("process id:" + deploy.getId() + " process name: " + deploy.getName());
    }

    /**
     * 流程启动
     */
    void start() {
        RuntimeService runtimeService = processEngine.getRuntimeService();
        ProcessInstance my_first_process = runtimeService.startProcessInstanceByKey("my_first_process");
        System.out.println(my_first_process.getId());
    }

    //查看任务
    @Test
    void findTask() {
        List<Task> taskList = processEngine.getTaskService().createTaskQuery().taskAssignee("admin").list();
        if (!taskList.isEmpty()) {
            for (Task task : taskList) {
                System.out.println("任务Id:" + task.getId());
                System.out.println("任务名称:" + task.getName());
                System.out.println("任务时间:" + task.getCreateTime());
                System.out.println("任务委派人:" + task.getAssignee());
                System.out.println("流程ID:" + task.getProcessInstanceId());
            }
        }
    }

    //完成任务
    @Test
    void  complateTask(){
        processEngine.getTaskService()
                .complete("8");
        findTask();
    }

}
