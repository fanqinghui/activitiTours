package com.fqh.activitiDemo.repository;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngineConfiguration;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * @author fqh
 * @Description: ${todo}
 * @date 2020/3/23上午11:03
 */
public class ActivitiRepositoryServiceTest {


    ProcessEngine processEngine;
    {
        System.out.println("init process begin");
        ProcessEngineConfiguration pec = ProcessEngineConfiguration.createStandaloneProcessEngineConfiguration();
        //数据库设置
        pec.setJdbcDriver("com.mysql.cj.jdbc.Driver");
        //nullCatalogMeansCurrent=true很关键，mysqlDrive8.0 默认把nullCatalogMeansCurrent改为false了
        pec.setJdbcUrl("jdbc:mysql://loacalhost:3306/demo?autoReconnect=true&nullCatalogMeansCurrent=true");
        pec.setJdbcUsername("root");
        pec.setJdbcPassword("111111");
        pec.setDatabaseSchemaUpdate(ProcessEngineConfiguration.DB_SCHEMA_UPDATE_TRUE);
        processEngine = pec.buildProcessEngine();
        System.out.println("init process end");
    }
    //查询部署
    @Test
    public void testList(){
        List<Deployment> deploymentList = processEngine.getRepositoryService()
                .createDeploymentQuery()
                .processDefinitionKey("my_first_process").list();
        if(!deploymentList.isEmpty()){
            for(Deployment de:deploymentList){
                System.out.println(de.getId());
                System.out.println(de.getName());
                System.out.println(de.getCategory());
                System.out.println(de.getDeploymentTime());
            }
        }
    }

    /**
     * 查询所有定义的流程list
     */
    @Test
    public void testDefinedList(){
        List<ProcessDefinition> definitionList = processEngine.getRepositoryService()
                .createProcessDefinitionQuery()
                .processDefinitionKey("my_first_process")
                .orderByProcessDefinitionVersion().asc()
                .list();
        if(!definitionList.isEmpty()){
            for(ProcessDefinition de:definitionList){
                System.out.println(de.getId());
                System.out.println(de.getName());
                System.out.println(de.getKey());
                System.out.println(de.getDiagramResourceName());
                System.out.println(de.getVersion());
                System.out.println(de.getCategory());
                System.out.println("======================");
            }
        }
    }

    /**
     * 查询单个流程定义
     */
    @Test
    public void testDefinedById(){
        ProcessDefinition de = processEngine.getRepositoryService()
                .createProcessDefinitionQuery()
                .processDefinitionId("my_first_process:1:4")
                .orderByProcessDefinitionVersion().asc()
                .singleResult();
            System.out.println(de.getId());
            System.out.println(de.getName());
            System.out.println(de.getKey());
            System.out.println(de.getDiagramResourceName());
            System.out.println(de.getVersion());
            System.out.println(de.getCategory());
            System.out.println("======================");
    }

    /**
     * 查询所有流程的最新版本部署记录
     */
    @Test
    public void testGetLastVersionDefined(){
        List<ProcessDefinition> definitionList = processEngine.getRepositoryService().createProcessDefinitionQuery()
                .orderByProcessDefinitionVersion().asc().list();
        Map<String,ProcessDefinition> map= new HashMap<>();
        for(ProcessDefinition definition:definitionList){
            map.put(definition.getKey(), definition);
        }
        List<ProcessDefinition> des=new LinkedList<>(map.values());
        for(ProcessDefinition de:des){
            System.out.println(de.getId());
            System.out.println(de.getName());
            System.out.println(de.getKey());
            System.out.println(de.getDiagramResourceName());
            System.out.println(de.getVersion());
            System.out.println(de.getCategory());
            System.out.println("======================");
        }
    }
}
