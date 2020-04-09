package com.fqh.activitiDemo.candidateGroup;

import org.activiti.engine.IdentityService;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngineConfiguration;
import org.activiti.engine.identity.Group;
import org.activiti.engine.identity.User;
import org.activiti.engine.impl.persistence.entity.GroupEntity;
import org.activiti.engine.impl.persistence.entity.UserEntity;
import org.junit.Test;

/**
 * @author fqh
 * @Description: 用户分组以及用户与分组关系测试类
 * @date 2020/4/9下午5:45
 */
public class ActivitiUserGroupMemberShipTest {

  ProcessEngine processEngine;

  {
    ProcessEngineConfiguration pec = ProcessEngineConfiguration
        .createProcessEngineConfigurationFromResource("activiti.cfg.xml");
    processEngine = pec.buildProcessEngine();
  }

  /**
   * 添加用户
   */
  @Test
  public void saveUser(){
    IdentityService identityService = processEngine.getIdentityService();
    User user=new UserEntity();
    user.setId("wangwu11");
    user.setEmail("zhangsan@mail.com");
    user.setFirstName("zhang");
    user.setLastName("san");
    identityService.saveUser(user);
  }

  /**
   * 查询用户
   */
  @Test
  public void findUser(){
    IdentityService identityService = processEngine.getIdentityService();
    User user = identityService.createUserQuery().userId("zhangsan").singleResult();
    System.out.println(user.getFirstName()+user.getLastName());
  }

  /**
   * 删除用户
   */
  @Test
  public void deleteUser(){
    IdentityService identityService = processEngine.getIdentityService();
    identityService.deleteUser("zhangsan1");
  }

  @Test
  public void saveGroup(){
    IdentityService identityService = processEngine.getIdentityService();
    Group group=new GroupEntity();
    group.setId("three");
    group.setName("三组");
    identityService.saveGroup(group);
  }


  @Test
  public void  findGroup(){
    IdentityService identityService = processEngine.getIdentityService();
    Group group = identityService.createGroupQuery().groupId("one").singleResult();
    System.out.println(group.getName());
  }


  @Test
  public void delGroup(){
    IdentityService identityService = processEngine.getIdentityService();
    identityService.deleteGroup("three");
  }


  @Test
  public void saveUserGroupMemberShip(){
    IdentityService identityService = processEngine.getIdentityService();
    identityService.createMembership("lisi1","two");
  }
  @Test
  public void delUserGroupMemberShip(){
    IdentityService identityService = processEngine.getIdentityService();
    identityService.deleteMembership("wangwu","three");
  }

}
