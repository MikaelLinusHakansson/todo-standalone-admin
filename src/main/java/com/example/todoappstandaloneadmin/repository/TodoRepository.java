package com.example.todoappstandaloneadmin.repository;

import com.example.todoappstandaloneadmin.entity.TodoEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TodoRepository extends CrudRepository<TodoEntity, Long> {
    List<TodoEntity> findByUserId(int userId) throws Exception;
}
