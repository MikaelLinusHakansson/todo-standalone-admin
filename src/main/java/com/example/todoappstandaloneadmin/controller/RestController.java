package com.example.todoappstandaloneadmin.controller;

import com.example.todoappstandaloneadmin.dao.service.TodoDaoService;
import com.example.todoappstandaloneadmin.dto.TodoDto;
import com.example.todoappstandaloneadmin.entity.TodoEntity;
import com.example.todoappstandaloneadmin.service.TodoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

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

    /**
     * Get all todos for the given user.
     *
     * @param  user  the authenticated user
     * @return      the list of todo DTOs
     */
    @GetMapping("/getall")
    public List<TodoDto> getAllTodos(@AuthenticationPrincipal UserDetails user) throws Exception {
       return TodoDto.valuesOf(todoService.getTodosByUserId(user));
    }

    @GetMapping("/getbyid/{id}")
    public TodoDto getById(@PathVariable Long id) throws Exception {
        return TodoDto.fromEntity(todoService.getById(id));
    }

    /**
     * Add a new todo item to the database.
     *
     * @param  task The todo item to be added
     * @return      The added todo item
     */
    @PostMapping("/add")
    public TodoDto addTodo(@RequestBody TodoDto task) throws Exception {
        TodoEntity todo = TodoEntity.fromDto(task);

        TodoEntity savedTodo = todoService.addTodo(todo, task.getUsername());

        return TodoDto.fromEntity(savedTodo);
    }

    /**
     * Update a task for the given user.
     *
     * @param  userDetails   the user details
     * @param  todo         the updated todo entity
     * @param  id           the id of the todo entity
     * @return              the updated todo DTO
     */
    @PutMapping("/update")
    public TodoDto updateTask(@RequestBody TodoDto todo) throws Exception {
        TodoEntity updatedTodo = todoService.updateById(todo);
        return TodoDto.fromEntity(updatedTodo);
    }

    @DeleteMapping("/delete")
    public void remove(@RequestParam Long id) throws Exception {
        todoService.removeTodo(id);
    }
}
