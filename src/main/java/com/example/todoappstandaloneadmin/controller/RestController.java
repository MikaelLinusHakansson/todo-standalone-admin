package com.example.todoappstandaloneadmin.controller;

import com.example.todoappstandaloneadmin.entity.TodoEntity;
import com.example.todoappstandaloneadmin.exceptions.notFound.EntityNotFoundException;
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
        try {

            return new ResponseEntity(theService.getAllTodos(), HttpStatus.OK);
        }

        catch (EntityNotFoundException usersNotFound) {

            return new ResponseEntity(usersNotFound.getMessage(), HttpStatus.CONFLICT);
        }

    }

    @GetMapping("/getbyid/{id}")
    public ResponseEntity getById(@PathVariable String id) {
        try {

            return new ResponseEntity(theService.getById(id), HttpStatus.OK);
        }

        catch (EntityNotFoundException userNotFound) {

            return new ResponseEntity(userNotFound.getMessage(), HttpStatus.CONFLICT);
        }
    }

    @PostMapping("/add")
    public ResponseEntity addTodo(@RequestBody TodoEntity task) {
        try {

            return new ResponseEntity(theService.addTodo(task), HttpStatus.OK);
        }

        catch (EntityNotFoundException taskWasEmtpy) {


            return new ResponseEntity(taskWasEmtpy.getMessage(), HttpStatus.CONFLICT);
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity updateTask(@RequestBody TodoEntity todo, @PathVariable String id) {
        try {
            return new ResponseEntity(theService.updateById(todo, id), HttpStatus.OK);
        }

        catch (EntityNotFoundException taskNotFound) {

            return new ResponseEntity(taskNotFound.getMessage(), HttpStatus.CONFLICT);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity removeById(@PathVariable String id){
        try {

            return new ResponseEntity(theService.removeTodo(id), HttpStatus.OK);
        }

        catch (EntityNotFoundException userNotFound) {

            return new ResponseEntity(userNotFound.getMessage(), HttpStatus.CONFLICT);
        }
    }
}
