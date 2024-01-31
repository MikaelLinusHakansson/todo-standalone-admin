package com.example.todoappstandaloneadmin.controller;

import com.example.todoappstandaloneadmin.dao.service.TodoDaoService;
import com.example.todoappstandaloneadmin.dto.TodoDto;
import com.example.todoappstandaloneadmin.entity.TodoEntity;
import com.example.todoappstandaloneadmin.entity.UserEntity;
import com.example.todoappstandaloneadmin.exceptions.notFound.EntityNotFoundException;
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

    /**
     * Get all todos for the given user.
     *
     * @param  user  the authenticated user
     * @return      the list of todo DTOs
     */
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
        return todoDtos;
    }

    @GetMapping("/getbyid/{id}")
    public TodoDto getById(@PathVariable Long id) throws SQLException {
        return new TodoDto(todoService.getById(id));
    }

    /**
     * Add a new todo item to the database.
     *
     * @param  task The todo item to be added
     * @return      The added todo item
     */
    @PostMapping("/add")
    public TodoDto addTodo(@RequestBody TodoDto task) throws SQLException {
        TodoEntity todo = new TodoEntity();

        todo.setName(task.getName());
        todo.setDate(task.getDate());
        todo.setCompleted(task.getCompleted());
        todo.setUser(userRepository.findByUsername(task.getUsername()).orElse(null));

        return new TodoDto(todoService.addTodo(todo));
    }

    /**
     * Update a task for the given user.
     *
     * @param  userDetails   the user details
     * @param  todo         the updated todo entity
     * @param  id           the id of the todo entity
     * @return              the updated todo DTO
     */
    @PutMapping("/update/{id}")
    public TodoDto updateTask(@AuthenticationPrincipal UserDetails userDetails, @RequestBody TodoEntity todo, @PathVariable Long id) throws AccessDeniedException {
        TodoEntity existingTodo = todoService.getById(id);

        if (existingTodo == null) {
            throw new EntityNotFoundException("Todo not found with id: " + id);
        }

        String username = userDetails.getUsername();
        UserEntity user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Username not found"));

        if (!existingTodo.getUser().getUsername().equals(user.getUsername())) {
            throw new AccessDeniedException("User does not have permission to modify this task");
        }

        existingTodo.setName(todo.getName());
        existingTodo.setDate(todo.getDate());
        existingTodo.setCompleted(todo.getCompleted());

        TodoEntity updatedTodo = todoService.updateById(existingTodo, id);

        return new TodoDto(updatedTodo);
    }

    @DeleteMapping("/delete/{id}")
    public void removeById(@PathVariable Long id) throws SQLException {
        todoService.removeTodo(id);
    }
}
