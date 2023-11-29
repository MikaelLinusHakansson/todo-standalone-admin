package com.example.todoappstandaloneadmin.service;

import com.example.todoappstandaloneadmin.entity.TodoEntity;
import com.example.todoappstandaloneadmin.repository.TodoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

    public void addTodo(TodoEntity task) {
        todoRepository.addTodo(task);
    }

    public void removeTodo(Long id) {
        todoRepository.removeTodo(id);
    }

    public void updateById(TodoEntity todo, Long id) {
        todoRepository.updateById(todo, id);
    }

    public void getById(Long id) {  // TODO Return something?
        todoRepository.getById(id);
    }
}
