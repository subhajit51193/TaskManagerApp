package com.taskmanager.app.controller;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.taskmanager.app.entity.Task;
import com.taskmanager.app.service.UserService;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

	@Autowired
	private UserService userService;
	
	/*
	 * Gets every tasks on database - specific to user with ROLE_ADMIN
	 * 
	 * @return: ResponseEntity -> Set of tasks
	 */
//	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/allTasks")
	public ResponseEntity<Set<Task>> getAllTasksFromDbHandler(){
		
		Set<Task> allTasks = userService.getAlltasksFromDb();
		return new ResponseEntity<Set<Task>>(allTasks,HttpStatus.OK);
	}
}
