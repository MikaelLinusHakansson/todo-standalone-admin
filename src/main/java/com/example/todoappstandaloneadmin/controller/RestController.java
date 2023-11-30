package com.example.todoappstandaloneadmin.controller;

import com.example.todoappstandaloneadmin.entity.TodoEntity;
import com.example.todoappstandaloneadmin.service.TodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@org.springframework.web.bind.annotation.RestController
public class RestController {
    private final TodoService theService;

    @Autowired
    public RestController(TodoService service) {
        this.theService = service;
    }

    @GetMapping("/getall")
    public ResponseEntity getAllTodos() {

        return new ResponseEntity(theService.getAllTodos(), HttpStatus.OK);
    }

    @GetMapping("/getbyid/{id}")
    public ResponseEntity getById(@PathVariable String id) {

        return new ResponseEntity(theService.getById(id), HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity addTodo(@RequestBody TodoEntity task) {

        return new ResponseEntity(theService.addTodo(task), HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity updateTask(@RequestBody TodoEntity todo, @PathVariable String id) {

        return new ResponseEntity(theService.updateById(todo, id), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity removeById(@PathVariable String id){

        return new ResponseEntity(theService.removeTodo(id), HttpStatus.OK);
    }
}
