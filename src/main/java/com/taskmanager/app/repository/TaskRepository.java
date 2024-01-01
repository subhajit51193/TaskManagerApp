package com.taskmanager.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.taskmanager.app.entity.Task;

public interface TaskRepository extends JpaRepository<Task, String>{

}
