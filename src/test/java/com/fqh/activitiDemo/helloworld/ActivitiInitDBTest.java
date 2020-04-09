package com.fqh.activitiDemo.helloworld;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngineConfiguration;
import org.junit.jupiter.api.Test;

/**
 * @author fqh
 * @Description: activitiTest01
 * @date 2020/3/20下午4:18
 */
//@SpringBootTest
public class ActivitiInitDBTest {

  @Test
  public void testCreateTable(){
    ProcessEngineConfiguration pec = ProcessEngineConfiguration
        .createStandaloneProcessEngineConfiguration();
    //数据库设置
    pec.setJdbcDriver("com.mysql.cj.jdbc.Driver");
    //nullCatalogMeansCurrent=true很关键，mysqlDrive8.0 默认把nullCatalogMeansCurrent改为false了
    pec.setJdbcUrl("jdbc:mysql://47.92.144.90:3306/demo?autoReconnect=true&nullCatalogMeansCurrent=true");
    pec.setJdbcUsername("root");
    pec.setJdbcPassword("123abcABC!");
    pec.setDatabaseSchemaUpdate(ProcessEngineConfiguration.DB_SCHEMA_UPDATE_TRUE);
    ProcessEngine processEngine = pec.buildProcessEngine();
  }

  @Test
  public void createActivitiByXMLTest(){
    /*ProcessEngineConfiguration pec = ProcessEngineConfiguration
        .createProcessEngineConfigurationFromResource("activiti.cfg.xml");
    ProcessEngine processEngine = pec.buildProcessEngine();*/
    String str="FLUSH PRIVILEGES;";
    System.out.println(str.toLowerCase());
  }

}
