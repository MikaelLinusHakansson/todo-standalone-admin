package com.example.todoappstandaloneadmin.controller;

import com.example.todoappstandaloneadmin.dao.service.TodoDaoService;
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
@RequestMapping("api/todo")
public class RestController {
    private final TodoService todoService;
    private final TodoDaoService todoDaoService;

    @Autowired
    public RestController(TodoService service, TodoDaoService todoDaoService) {
        this.todoService = service;
        this.todoDaoService = todoDaoService;
    }

    @GetMapping("/getall")
    public List<TodoDto> getAllTodos() throws Exception {
        List<TodoDto> todoDtos = new ArrayList<>();

        for (TodoEntity todoEntity : todoDaoService.getAll()) {
            TodoDto tempDto = new TodoDto(todoEntity);
            todoDtos.add(tempDto);
        }

        return todoDtos;
    }

    @GetMapping("/getbyid/{id}")
    public TodoDto getById(@PathVariable Long id) throws SQLException {
        return new TodoDto(todoDaoService.getById(id));
    }

    @PostMapping("/add")
    public TodoDto addTodo(@RequestBody TodoEntity task) throws SQLException {
        return new TodoDto(todoDaoService.add(task));
    }

    @PutMapping("/update/{id}")
    public TodoDto updateTask(@RequestBody TodoEntity todo, @PathVariable Long id) throws SQLException {
        return new TodoDto(todoDaoService.update(todo, id));
    }

    @DeleteMapping("/delete/{id}")
    public void removeById(@PathVariable Long id) throws SQLException {
        todoDaoService.delete(id);
    }
}
