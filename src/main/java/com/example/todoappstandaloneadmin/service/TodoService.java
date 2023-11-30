package com.example.todoappstandaloneadmin.service;

import com.example.todoappstandaloneadmin.entity.TodoEntity;
import com.example.todoappstandaloneadmin.repository.TodoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class TodoService {
    private final TodoRepository todoRepository;

    @Autowired
    public TodoService(TodoRepository repository) {
        this.todoRepository = repository;
    }

    public List<TodoEntity> getAllTodos() {
        try {

            return todoRepository.getAllTodos();
        }

        catch (ResponseStatusException exc) {

            throw new ResponseStatusException(HttpStatusCode.valueOf(404), exc.getReason());
        }
    }

    public TodoEntity getById(Long id) {
        return todoRepository.getById(id);
    }

    public TodoEntity addTodo(TodoEntity task) {
        try {
            return todoRepository.addTodo(task);
        }

        catch (ResponseStatusException exc) {
            throw new ResponseStatusException(HttpStatusCode.valueOf(404), exc.getReason());
        }
    }

    public TodoEntity removeTodo(Long id) {
        if (todoRepository.removeTodo(id) != null) {

            return todoRepository.removeTodo(id);
        }

        return null;
    }

    public TodoEntity updateById(TodoEntity todo, Long id) {
        if (todoRepository.updateById(todo, id) != null) {

            return todoRepository.updateById(todo, id);
        }

        return null;
    }
}
