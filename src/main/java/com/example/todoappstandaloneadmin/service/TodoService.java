package com.example.todoappstandaloneadmin.service;

import com.example.todoappstandaloneadmin.dto.TodoDto;
import com.example.todoappstandaloneadmin.entity.TodoEntity;
import com.example.todoappstandaloneadmin.exceptions.badRequest.EntityNameNotFoundBadRequest;
import com.example.todoappstandaloneadmin.exceptions.notFound.EntityNotFoundException;
import com.example.todoappstandaloneadmin.repository.TodoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TodoService {
    private final TodoRepository todoRepository;

    @Autowired
    public TodoService(TodoRepository repository) {
        this.todoRepository = repository;
    }

    public List<TodoDto> getAllTodos() {
        List<TodoDto> todoDtos = new ArrayList<>();

        for (TodoEntity allTodo : todoRepository.findAll()) {
            TodoDto todoDto = new TodoDto();
            todoDto.setId(allTodo.getId());
            todoDto.setName(allTodo.getName());
            todoDto.setDate(allTodo.getDate());
            todoDto.setCompleted(allTodo.getCompleted());

            todoDtos.add(todoDto);
        }
        return todoDtos;
    }

    public TodoDto getById(Long id) {
        for (TodoDto allTodo : getAllTodos()) {
            if (id.equals(allTodo.getId())) {
                return allTodo;
            }
        }
        throw new EntityNotFoundException("Could not find matching ID");
    }

    public TodoDto addTodo(TodoEntity task) {
        if (!task.getName().isBlank()) {
            todoRepository.save(task);

            TodoDto todoDto = new TodoDto();
            todoDto.setId(task.getId());
            todoDto.setName(task.getName());
            todoDto.setDate(task.getDate());
            todoDto.setCompleted(task.getCompleted());

            return todoDto;
        }

        else {
            throw new EntityNameNotFoundBadRequest("Name is empty");
        }
    }

    public TodoDto updateById(TodoEntity todo, Long id) {
        if (todoRepository.findById(id).isPresent()) {
            todoRepository.save(todo);

            TodoDto todoDto = new TodoDto();
            todoDto.setId(todo.getId());
            todoDto.setName(todo.getName());
            todoDto.setDate(todo.getDate());
            todoDto.setCompleted(todo.getCompleted());

            return todoDto;
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