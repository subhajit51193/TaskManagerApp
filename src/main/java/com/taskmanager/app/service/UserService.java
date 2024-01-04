package com.taskmanager.app.service;

import java.util.Set;

import com.taskmanager.app.entity.Task;

public interface UserService {

	public Set<Task> getAlltasksFromDb();
}
