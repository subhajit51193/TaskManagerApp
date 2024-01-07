package com.taskmanager.app.service;

import java.util.Set;

import com.taskmanager.app.entity.Task;
import com.taskmanager.app.exception.InvalidTaskException;


public interface TaskService {

	public String createTask(Task task,String email)throws InvalidTaskException;
	
	public Set<Task> getAllTasksByUser(String email);
	
	public Task getTaskByTaskId(String taskId, String email) throws InvalidTaskException;
	
	public String deleteTaskBytaskId(String taskId,String email)throws InvalidTaskException;
	
	public String updateTask(String taskId,Task task,String email)throws InvalidTaskException;
}
