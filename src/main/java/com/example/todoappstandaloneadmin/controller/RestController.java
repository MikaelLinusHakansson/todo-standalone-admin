package com.example.todoappstandaloneadmin.controller;

import com.example.todoappstandaloneadmin.entity.TodoEntity;
import com.example.todoappstandaloneadmin.service.TodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@org.springframework.web.bind.annotation.RestController
@CrossOrigin(origins = "*")  // TODO change the ports. 5174 for work 5173 for home "http://localhost:5173/"
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
        return theService.getById(id);
    }

    @PostMapping("/add")
    public ResponseEntity<String> addTodo(@RequestBody TodoEntity task) {
        theService.addTodo(task);
        return new ResponseEntity<>("", HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateTask(@RequestBody TodoEntity todo, @PathVariable Long id) {
        theService.updateById(todo, id);
        return new ResponseEntity<>("", HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> removeById(@PathVariable Long id){
        theService.removeTodo(id);
        return new ResponseEntity<>("", HttpStatus.OK);
    }
}
