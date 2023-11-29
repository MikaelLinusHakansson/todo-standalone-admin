package com.example.todoappstandaloneadmin.repository;

import com.example.todoappstandaloneadmin.entity.TodoEntity;

import java.util.List;

public interface TodoRepository {
    List<TodoEntity> getAllTodos();

    TodoEntity getById(Long id);

    void addTodo(TodoEntity todo);

    TodoEntity removeTodo(Long id);

    TodoEntity updateById(TodoEntity todo, Long id);
}
