package com.fqh.activitiDemo.muitiAssignee;

import java.util.ArrayList;
import java.util.List;
import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;

/**
 * @author fqh
 * @Description:
 * @date 2020/4/9上午11:04
 */
public class ActivitiMuitiAssigneeListener implements TaskListener {

  @Override
  public void notify(DelegateTask delegateTask) {
    /*delegateTask.setAssignee("老虎");*/
    List<String> list=new ArrayList<>();
    list.add("张三");
    list.add("李四");
    list.add("王五");
    delegateTask.addCandidateUsers(list);
  }
}
