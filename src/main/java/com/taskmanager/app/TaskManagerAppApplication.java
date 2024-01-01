package com.taskmanager.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import com.taskmanager.app.config.RsaKeyProperties;

@EnableConfigurationProperties(RsaKeyProperties.class)
@SpringBootApplication
public class TaskManagerAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(TaskManagerAppApplication.class, args);
	}

}
