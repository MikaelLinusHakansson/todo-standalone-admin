package com.example.todoappstandaloneadmin.repository;

import com.example.todoappstandaloneadmin.entity.TodoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
public interface TodoRepository extends JpaRepository<TodoEntity, Long> {
    /*List<TodoEntity> getAllTodos();

    TodoEntity getById(String id);

    void addTodo(TodoEntity todo);

    void removeTodo(String id);

    void updateById(TodoEntity todo, String id);*/
}
