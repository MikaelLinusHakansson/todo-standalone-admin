package com.example.todoappstandaloneadmin.controller;

import com.example.todoappstandaloneadmin.dao.TodoDao;
import com.example.todoappstandaloneadmin.dto.TodoDto;
import com.example.todoappstandaloneadmin.entity.TodoEntity;
import com.example.todoappstandaloneadmin.service.TodoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.ArrayList;
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
    public List<TodoDto> getAllTodos() throws Exception {
        List<TodoDto> todoDtos = new ArrayList<>();

        for (TodoEntity todoEntity : TodoDao.getAll()) {
            TodoDto tempDto = new TodoDto(todoEntity);
            todoDtos.add(tempDto);
        }

        return todoDtos;
    }

    @GetMapping("/getbyid/{id}")
    public TodoDto getById(@PathVariable Long id) {
        return new TodoDto(TodoDao.getById(id));
    }

    @PostMapping("/add")
    public TodoDto addTodo(@RequestBody TodoEntity task) throws SQLException {
        return new TodoDto(TodoDao.add(task));
    }

    @PutMapping("/update/{id}")
    public TodoDto updateTask(@RequestBody TodoEntity todo, @PathVariable Long id) throws SQLException {
        return new TodoDto(TodoDao.update(todo, id));
    }

    @DeleteMapping("/delete/{id}")
    public void removeById(@PathVariable Long id) throws SQLException {
        TodoDao.delete(id);
    }
}
