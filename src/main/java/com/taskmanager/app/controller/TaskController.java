package com.taskmanager.app.controller;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.taskmanager.app.entity.Task;
import com.taskmanager.app.exception.InvalidTaskException;
import com.taskmanager.app.service.TaskService;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/task")
public class TaskController {

	@Autowired
	private TaskService taskService;
	
	/*
	 * Create a task
	 * 
	 * @param: Task -> Task object with details
	 * @param: HttpServletRequest -> to get email via attribute of logged in user
	 * 
	 * @return: ResponseEntity -> Containing customized string
	 * 
	 * @throws: InvalidTaskException -> if task is not valid 
	 */
	@PostMapping("/create")
	public ResponseEntity<String> createTaskHandler(@RequestBody Task task,HttpServletRequest request) throws InvalidTaskException{
		
		String clientEmail = request.getAttribute("email").toString();
		String res = taskService.createTask(task,clientEmail);
		return new ResponseEntity<String>(res,HttpStatus.ACCEPTED);
	}
	
	/*
	 * Gets all task for a user
	 * 
	 * @param: HttpServletRequest -> to get email via attribute of logged in user
	 * 
	 * @return: ResponseEntity -> Containing set of tasks 
	 */
	@GetMapping("/all")
	public ResponseEntity<Set<Task>> getAllTasksByUserHandler(HttpServletRequest request){
		
		String clientEmail = request.getAttribute("email").toString();
		Set<Task> tasks = taskService.getAllTasksByUser(clientEmail);
		return new ResponseEntity<Set<Task>>(tasks,HttpStatus.OK);
	}
	
	/*
	 * Gets a task based on taskId
	 * 
	 * @param: String -> TaskId 
	 * @param: HttpServletRequest -> to get email via attribute of logged in user
	 * 
	 * @return: ResponseEntity -> Containing Task object
	 * 
	 * @throws: InvalidTaskException -> if task is not valid or not found
	 */
	@GetMapping("/{id}")
	public ResponseEntity<Task> getTaskByIdUserHandler(@PathVariable String id,HttpServletRequest request) throws InvalidTaskException{
		
		String clientEmail = request.getAttribute("email").toString();
		Task task = taskService.getTaskByTaskId(id, clientEmail);
		return new ResponseEntity<Task>(task,HttpStatus.OK);
	}
	
	/*
	 * Deletes a task based on taskId
	 * 
	 * @param: String -> TaskId
	 * @param: HttpServletRequest -> to get email via attribute of logged in user
	 * 
	 * @return: ResponseEntity -> Containing customized string
	 * 
	 * @throws: InvalidTaskException -> if task is not valid or not found
	 */
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<String> deleteTaskByTaskIdUserHandler(@PathVariable String id,HttpServletRequest request) throws InvalidTaskException{
		
		String clientEmail = request.getAttribute("email").toString();
		String res = taskService.deleteTaskBytaskId(id, clientEmail);
		return new ResponseEntity<String>(res,HttpStatus.ACCEPTED);
	}
	
	/*
	 * Updates a task
	 * 
	 * @param: String -> taskID
	 * @param: Task -> Task object with details
	 * @param: HttpServletRequest -> to get email via attribute of logged in user
	 * 
	 * @return: ResponseEntity -> Containing customized string
	 * 
	 * @throws: InvalidTaskException -> if task is not valid or not found
	 */
	@PutMapping("/update/{id}")
	public ResponseEntity<String> updateTaskUserHandler(@PathVariable String id,@RequestBody Task task,HttpServletRequest request) throws InvalidTaskException{
		
		String clientEmail = request.getAttribute("email").toString();
		String res = taskService.updateTask(id, task, clientEmail);
		return new ResponseEntity<String>(res,HttpStatus.ACCEPTED);
	}
}
