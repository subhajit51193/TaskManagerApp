package com.taskmanager.app.service;

import java.util.Optional;
import java.util.Set;

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

	/*
	 * Gets All tasks belongs to particular user
	 * 
	 * @param: String -> Email of user
	 * 
	 * @return: Set<Task> -> All tasks of user
	 */
	@Override
	public Set<Task> getAllTasksByUser(String email) {
		
		User user = userRepository.findByEmail(email).get();
		return user.getTasks();
	}

	/*
	 * Gets task by taskId for particular user
	 * 
	 * @param: String,String -> taskId of a user and email of user
	 * 
	 * @return: Task -> task object consisting details
	 * 
	 * @throws: InvalidTaskException -> Throws exception if task not found in database
	 * or task which user has chosen doesnot belong to user
	 */
	@Override
	public Task getTaskByTaskId(String taskId, String email) throws InvalidTaskException {
		
		Optional<Task> opt = taskRepository.findById(taskId);
		if (opt.isEmpty()) {
			throw new InvalidTaskException("No task Found");
		}
		User user = userRepository.findByEmail(email).get();
		Set<Task> alltasks = user.getTasks();
		if (!alltasks.contains(opt.get())) {
			throw new InvalidTaskException("Unauthorized access for selected task");
		}
		return opt.get();
	}

	/*
	 * Deletes task by taskId for a particular user
	 * 
	 * @param: String,string -> taskId and email of user
	 * 
	 * @return: String -> Customized message with positive response if task is successful
	 * 
	 * @throws InavalidTaskException -> throws exception if no task found on DB
	 * or user selected wrong task which doesnot belong to user
	 */
	@Override
	public String deleteTaskBytaskId(String taskId, String email) throws InvalidTaskException {
		
		Optional<Task> opt = taskRepository.findById(taskId);
		if (opt.isEmpty()) {
			throw new InvalidTaskException("No task Found");
		}
		User user = userRepository.findByEmail(email).get();
		Set<Task> alltasks = user.getTasks();
		if (!alltasks.contains(opt.get())) {
			throw new InvalidTaskException("Unauthorized access for selected task");
		}
		System.out.println(opt.get().getTaskName());
		taskRepository.delete(opt.get());
		return "Your selected task has been deleted successfully "+ user.getFirstName();
	}

	/*
	 * Update task details by user
	 * 
	 * @param: String -> TaskID of a user
	 * @param: Task -> Task object which user wants to update
	 * @param: String -> email of user
	 * 
	 * @return: String -> Return customized string with positive response if successful
	 * 
	 * @throws: InvalidtaskException -> throws exception if select task does not exists in database
	 * or it exists but user is not owner of that task
	 */
	@Override
	public String updateTask(String taskId, Task task, String email) throws InvalidTaskException {
		
		Optional<Task> opt = taskRepository.findById(taskId);
		if (opt.isEmpty()) {
			throw new InvalidTaskException("No task Found");
		}
		User user = userRepository.findByEmail(email).get();
		Set<Task> alltasks = user.getTasks();
		if (!alltasks.contains(opt.get())) {
			throw new InvalidTaskException("Unauthorized access for selected task");
		}
		Task foundtask = opt.get();
		if (task.getTaskName() != null) {
			foundtask.setTaskName(task.getTaskName());
		}
		if (task.getDescription() != null) {
			foundtask.setDescription(task.getDescription());
		}
		if (task.getTaskStatus() != null) {
			if (task.getTaskStatus().toString().equals("COMPLETED")) {
				foundtask.setTaskStatus(TaskStatus.COMPLETED);
			}
			if (task.getTaskStatus().toString().equals("PENDING")) {
				foundtask.setTaskStatus(TaskStatus.PENDING);
			}
		}
		taskRepository.save(foundtask);
		return "Your task details have been updated successfully "+ user.getFirstName();
	}
}
