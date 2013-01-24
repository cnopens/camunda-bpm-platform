package org.camunda.bpm.engine.rest.impl;

import java.util.ArrayList;
import java.util.List;

import org.activiti.engine.TaskService;
import org.activiti.engine.task.Task;
import org.activiti.engine.task.TaskQuery;
import org.camunda.bpm.engine.rest.TaskRestService;
import org.camunda.bpm.engine.rest.dto.TaskDto;
import org.camunda.bpm.engine.rest.dto.TaskQueryDto;

public class TaskRestServiceImpl extends AbstractEngineService implements TaskRestService {

  @Override
  public List<TaskDto> getTasks(TaskQueryDto queryDto) {
    TaskService taskService = processEngine.getTaskService();
    TaskQuery query = queryDto.toQuery(taskService);
    
    List<Task> matchingTasks = query.list();
    
    List<TaskDto> tasks = new ArrayList<TaskDto>();
    for (Task task : matchingTasks) {
      TaskDto returnTask = TaskDto.fromTask(task);
      tasks.add(returnTask);
    }
    
    return tasks;
  }

}