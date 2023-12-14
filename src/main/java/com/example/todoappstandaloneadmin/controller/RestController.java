package com.example.todoappstandaloneadmin.controller;

import com.example.todoappstandaloneadmin.dto.TodoDto;
import com.example.todoappstandaloneadmin.entity.TodoEntity;
import com.example.todoappstandaloneadmin.service.TodoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@org.springframework.web.bind.annotation.RestController
@CrossOrigin(origins = "*")
public class RestController {
    private final TodoService todoService;

    @Autowired
    public RestController(TodoService service) {
        this.todoService = service;
    }

    @GetMapping("/getall")
    public List<TodoDto> getAllTodos() {
        return todoService.getAllTodos();
    }

    @GetMapping("/getbyid/{id}")
    public TodoDto getById(@PathVariable Long id) {
        return todoService.getById(id);
    }

    @PostMapping("/add")
    public TodoDto addTodo(@RequestBody TodoEntity task) {
        return  todoService.addTodo(task);
    }

    @PutMapping("/update/{id}")
    public TodoDto updateTask(@RequestBody TodoEntity todo, @PathVariable Long id) {
        return todoService.updateById(todo, id);
    }

    @DeleteMapping("/delete/{id}")
    public void removeById(@PathVariable Long id){
        todoService.removeTodo(id);
    }
}
