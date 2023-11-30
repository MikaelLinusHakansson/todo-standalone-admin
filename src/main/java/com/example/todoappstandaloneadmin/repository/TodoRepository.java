package com.example.todoappstandaloneadmin.repository;

import com.example.todoappstandaloneadmin.entity.TodoEntity;
import org.springframework.stereotype.Repository;

import java.util.List;
public interface TodoRepository {
    List<TodoEntity> getAllTodos();

    TodoEntity getById(String id);

    String addTodo(TodoEntity todo);

    String removeTodo(String id);

    String updateById(TodoEntity todo, String id);
}
