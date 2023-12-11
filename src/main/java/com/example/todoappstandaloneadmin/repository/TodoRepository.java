package com.example.todoappstandaloneadmin.repository;

import com.example.todoappstandaloneadmin.entity.TodoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TodoRepository extends JpaRepository<TodoEntity, Long> {

}
