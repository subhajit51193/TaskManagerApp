package com.taskmanager.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
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
	
	@PostMapping("/create")
	public ResponseEntity<String> createTaskHandler(@RequestBody Task task,HttpServletRequest request) throws InvalidTaskException{
		
		String clientEmail = request.getAttribute("email").toString();
		String res = taskService.createTask(task,clientEmail);
		return new ResponseEntity<String>(res,HttpStatus.ACCEPTED);
	}
}
