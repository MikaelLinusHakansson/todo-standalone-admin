package com.example.todoappstandaloneadmin.controller;

import com.example.todoappstandaloneadmin.entity.TodoEntity;
import com.example.todoappstandaloneadmin.service.TodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@org.springframework.web.bind.annotation.RestController
public class RestController {
    private final TodoService theService;

    @Autowired
    public RestController(TodoService service) {
        this.theService = service;
    }

    @GetMapping("/getall")
    public List<TodoEntity> getAllTodos() {
        return theService.getAllTodos();
    }

    @PostMapping("/add")
    public void addTodo(@RequestBody TodoEntity task) {
        this.theService.addTodo(task);
    }

    @PutMapping("/update/{id}")
    public void updateTask(@RequestBody TodoEntity todo, @PathVariable Long id) {
        theService.updateById(todo, id);
    }

    @DeleteMapping("/delete/{id}")
    public void removeById(@PathVariable Long id){
        theService.removeTodo(id);
    }

    @GetMapping("/getbyid/{id}")
    public void getById(@PathVariable Long id) {
        theService.getById(id);
    }
}
