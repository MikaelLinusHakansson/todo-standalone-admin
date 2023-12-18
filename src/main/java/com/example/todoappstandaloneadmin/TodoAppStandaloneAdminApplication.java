package com.example.todoappstandaloneadmin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TodoAppStandaloneAdminApplication {

	public static void main(String[] args) {
		SpringApplication.run(TodoAppStandaloneAdminApplication.class, args);
	// TODO if being serious with keeping JDBC, then remove JPA and go 100%
	}

}
