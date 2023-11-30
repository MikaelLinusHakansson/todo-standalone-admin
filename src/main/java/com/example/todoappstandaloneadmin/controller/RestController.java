package com.example.todoappstandaloneadmin.controller;

import com.example.todoappstandaloneadmin.HttpEnums.HttpStatus;
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

    @GetMapping("/getbyid/{id}")
    public TodoEntity getById(@PathVariable Long id) {
        try {

            return theService.getById(id);
        }

        catch (ResponseStatusException exc) {

            throw new ResponseStatusException(HttpStatusCode.valueOf(404), exc.getReason());
        }
    }

    @PostMapping("/add")
    public TodoEntity addTodo(@RequestBody TodoEntity task) {
        return this.theService.addTodo(task);
    }

    @PutMapping("/update/{id}")
    public TodoEntity updateTask(@RequestBody TodoEntity todo, @PathVariable Long id) {
        return theService.updateById(todo, id);
    }

    @DeleteMapping("/delete/{id}")
    public TodoEntity removeById(@PathVariable Long id){
        if (theService.removeTodo(id) != null) {
            return theService.removeTodo((id));
        }
        return null;
    }
}
