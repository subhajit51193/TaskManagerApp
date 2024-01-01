package com.taskmanager.app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.taskmanager.app.entity.Task;
import com.taskmanager.app.entity.TaskStatus;
import com.taskmanager.app.entity.User;
import com.taskmanager.app.exception.InvalidTaskException;
import com.taskmanager.app.helper.Helper;
import com.taskmanager.app.repository.TaskRepository;
import com.taskmanager.app.repository.UserRepository;


@Service
public class TaskServiceImpl implements TaskService{

	@Autowired
	private Helper helper;
	
	@Autowired
	private TaskRepository taskRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	/*
	 * Create Task
	 * 
	 * @param: Task -> Task object with required details
	 * @param: String -> Email of user
	 * 
	 * @return: String -> Positive message with taskName
	 * 
	 * @throws: InvalidTaskException -> This exception is thrown if null value is passed
	 */
	@Override
	public String createTask(Task task,String email) throws InvalidTaskException {
		
		if (task == null) {
			throw new InvalidTaskException("Invalid Task!!!");
		}
		else {
			User user = userRepository.findByEmail(email).get();
			task.setTaskId(helper.createRandomStringId());
			task.setTaskStatus(TaskStatus.PENDING);
			taskRepository.save(task);
			user.getTasks().add(task);
			userRepository.save(user);
			return "Your task "+ task.getTaskName()+" has been saved successfully";
		}
		

	}

}
