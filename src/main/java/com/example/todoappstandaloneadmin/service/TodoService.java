package com.example.todoappstandaloneadmin.service;

import com.example.todoappstandaloneadmin.entity.TodoEntity;
import com.example.todoappstandaloneadmin.exceptions.badRequest.EntityNameNotFoundBadRequest;
import com.example.todoappstandaloneadmin.exceptions.notFound.EntityNotFoundException;
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
        return todoRepository.findAll();
    }

    public TodoEntity getById(Long id) {
        for (TodoEntity allTodo : getAllTodos()) {
            if (id.equals(allTodo.getId())) {
                return allTodo;
            }
        }

        throw new EntityNotFoundException("Could not find matching ID");
    }

    public void addTodo(TodoEntity task) {
        if (!task.getName().isBlank()) {
            todoRepository.save(task);
        }

        else {
            throw new EntityNameNotFoundBadRequest("Name is empty");
        }

    }

    public void updateById(TodoEntity todo, Long id) {
        if (todoRepository.findById(id).isPresent()) {
            todoRepository.save(todo);
        }

        else {
            throw new EntityNotFoundException("Could not find matching ID");
        }

    }

    public void removeTodo(Long id) {
        if (todoRepository.findById(id).isPresent()) {
            todoRepository.deleteById(id);
        }

        else {
            throw new EntityNotFoundException("Could not find matching ID");
        }
    }
}