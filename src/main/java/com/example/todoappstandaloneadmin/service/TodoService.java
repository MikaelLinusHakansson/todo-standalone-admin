package com.example.todoappstandaloneadmin.service;

import com.example.todoappstandaloneadmin.entity.TodoEntity;
import com.example.todoappstandaloneadmin.repository.TodoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TodoService {
    private final TodoRepository todoRepository;

    @Autowired
    public TodoService(TodoRepository repository) {
        this.todoRepository = repository;
    }

    public List<TodoEntity> getAllTodos() {
        return todoRepository.getAllTodos();
    }

    public TodoEntity getById(String id) {
        return todoRepository.getById(id);
    }

    public void addTodo(TodoEntity task) {
        todoRepository.addTodo(task);
    }

    public void updateById(TodoEntity todo, String id) {
        todoRepository.updateById(todo, id);
    }

    public void removeTodo(String id) {
        todoRepository.removeTodo(id);
    }
}
