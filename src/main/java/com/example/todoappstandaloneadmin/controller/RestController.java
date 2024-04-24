package com.example.todoappstandaloneadmin.controller;

import com.example.todoappstandaloneadmin.dao.service.TodoDaoService;
import com.example.todoappstandaloneadmin.dto.TodoDto;
import com.example.todoappstandaloneadmin.entity.TodoEntity;
import com.example.todoappstandaloneadmin.entity.UserEntity;
import com.example.todoappstandaloneadmin.repository.UserRepository;
import com.example.todoappstandaloneadmin.service.TodoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.nio.file.AccessDeniedException;
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

        return todoService.getTodosByUserId(userId);
    }

    @GetMapping("/getbyid/{id}")
    public TodoDto getById(@PathVariable Long id) throws Exception {
        return new TodoDto(todoService.getById(id));
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

        todo.setUser(userRepository.findByUsername(task.getUsername()).orElse(null));

        TodoEntity savedTodo = todoService.addTodo(todo);

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
    @PutMapping("/update/{id}")
    public TodoDto updateTask(@AuthenticationPrincipal UserDetails userDetails, @RequestBody TodoDto todo, @PathVariable Long id) throws AccessDeniedException {
        TodoEntity existingTodo = todoService.getById(id);

        String username = userDetails.getUsername();
        UserEntity user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Username not found"));

        if (!existingTodo.getUser().getUsername().equals(user.getUsername())) {
            throw new AccessDeniedException("User does not have permission to modify this task");
        }

        existingTodo.update(todo);

        TodoEntity updatedTodo = todoService.updateById(existingTodo, id);

        return TodoDto.fromEntity(updatedTodo);
    }

    @DeleteMapping("/delete/{id}")
    public void removeById(@PathVariable Long id) throws Exception {
        todoService.removeTodo(id);
    }
}
