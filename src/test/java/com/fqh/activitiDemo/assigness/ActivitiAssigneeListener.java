package com.fqh.activitiDemo.assigness;

import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;

/**
 * @author fqh
 * @Description:
 * @date 2020/4/9上午11:04
 */
public class ActivitiAssigneeListener implements TaskListener {

  @Override
  public void notify(DelegateTask delegateTask) {
    delegateTask.setAssignee("老虎");
  }
}
