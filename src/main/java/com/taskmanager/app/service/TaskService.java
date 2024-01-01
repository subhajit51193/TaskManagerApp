package com.taskmanager.app.service;

import com.taskmanager.app.entity.Task;
import com.taskmanager.app.exception.InvalidTaskException;

public interface TaskService {

	public String createTask(Task task,String email)throws InvalidTaskException;
}
