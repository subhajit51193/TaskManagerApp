package com.taskmanager.app.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "tasks")
public class Task {

	@Id
	@Column(name = "task_id")
	private String taskId;
	
	@Column(name = "task_name")
	@NotBlank
	private String taskName;
	
	@Column(name = "description")
	@NotBlank
	private String description;
	
	@Column(name = "status")
	private TaskStatus taskStatus;
}
