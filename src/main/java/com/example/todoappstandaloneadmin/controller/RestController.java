package com.example.todoappstandaloneadmin.controller;

import com.example.todoappstandaloneadmin.dao.service.TodoDaoService;
import com.example.todoappstandaloneadmin.dto.TodoDto;
import com.example.todoappstandaloneadmin.entity.TodoEntity;
import com.example.todoappstandaloneadmin.repository.UserRepository;
import com.example.todoappstandaloneadmin.service.TodoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.nio.file.AccessDeniedException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


@org.springframework.web.bind.annotation.RestController
@CrossOrigin(origins = "*")
@RequestMapping("api/todo")
public class RestController {
    private final TodoService todoService;
    private final TodoDaoService todoDaoService;

    private final UserRepository userRepository;

    @Autowired
    public RestController(TodoService service, TodoDaoService todoDaoService, UserRepository userRepository) {
        this.todoService = service;
        this.todoDaoService = todoDaoService;
        this.userRepository = userRepository;
    }

    @GetMapping("/getall")
    public List<TodoDto> getAllTodos(@AuthenticationPrincipal UserDetails user) throws Exception {
        if (user == null) {
            throw new AccessDeniedException("User is not authenticated");
        }

        String username = user.getUsername();
        int userId = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Username not found"))
                .getId();

        List<TodoDto> todoDtos = new ArrayList<>();
        for (TodoEntity todo : todoService.getTodosByUserId(userId)) {
            TodoDto tempDto = new TodoDto(todo);
            todoDtos.add(tempDto);

        }
        /*List<TodoDto> todoDtos = new ArrayList<>();
        for (TodoEntity todoEntity : todoService.getAllTodos()) {
            TodoDto tempDto = new TodoDto(todoEntity);
            todoDtos.add(tempDto);
        }*/

        return todoDtos;
    }

    @GetMapping("/getbyid/{id}")
    public TodoDto getById(@PathVariable Long id) throws SQLException {
        return new TodoDto(todoService.getById(id));
    }

    @PostMapping("/add")
    public TodoDto addTodo(@RequestBody TodoDto task) throws SQLException {
        System.out.println("Adding task: ");
        System.out.println(task);

        TodoEntity something = new TodoEntity();

        something.setName(task.getName());
        something.setDate(task.getDate());
        something.setCompleted(task.getCompleted());
        something.setUser(userRepository.findByUsername(task.getUsername()).orElse(null));

        return new TodoDto(todoService.addTodo(something));
    }

    @PutMapping("/update/{id}")
    public TodoDto updateTask(@RequestBody TodoEntity todo, @PathVariable Long id) throws SQLException {
        return new TodoDto(todoService.updateById(todo, id));
    }

    @DeleteMapping("/delete/{id}")
    public void removeById(@PathVariable Long id) throws SQLException {
        todoService.removeTodo(id);
    }
}
