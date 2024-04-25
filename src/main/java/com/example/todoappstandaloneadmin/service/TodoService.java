package com.example.todoappstandaloneadmin.service;

import com.example.todoappstandaloneadmin.dto.TodoDto;
import com.example.todoappstandaloneadmin.entity.TodoEntity;
import com.example.todoappstandaloneadmin.entity.UserEntity;
import com.example.todoappstandaloneadmin.exceptions.badRequest.EntityNameNotFoundBadRequest;
import com.example.todoappstandaloneadmin.exceptions.notFound.EntityNotFoundException;
import com.example.todoappstandaloneadmin.repository.SqlTodoRepository;
import com.example.todoappstandaloneadmin.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.nio.file.AccessDeniedException;
import java.util.List;

@Service
public class TodoService {
    private final SqlTodoRepository sqlTodoRepository;
    private final UserRepository userRepository;

    @Autowired
    public TodoService(SqlTodoRepository sqlRepository, UserRepository userRepository) {
        this.userRepository = userRepository;
        sqlTodoRepository = sqlRepository;
    }

    public TodoEntity getById(Long id) {
        return sqlTodoRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Could not find matching ID"));
    }

    public List<TodoEntity> getTodosByUserId(UserDetails user) throws Exception {
        String username = user.getUsername();

        if (user == null) {
            throw new UsernameNotFoundException("Authentication failed");
        }

        int userId = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Username not found"))
                .getId();

        return sqlTodoRepository.findByUserId(userId);
    }

    public TodoEntity addTodo(TodoEntity task, String username) {
        task.setUser(userRepository.findByUsername(username).orElse(null));

        if (task.getUser().getUsername() != null) {
            if (!task.getName().isBlank()) {
                sqlTodoRepository.save(task);

                return task;

            }

            else {
                throw new EntityNameNotFoundBadRequest("Name is empty");
            }
        }

        else {
            throw new UsernameNotFoundException("Username not found");
        }
    }

    public TodoEntity updateById(TodoDto todo, UserDetails userDetails) throws Exception {
        String username = userDetails.getUsername();

        UserEntity user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Username not found"));

        if (!todo.getUsername().equals(user.getUsername())) {
            throw new AccessDeniedException("User does not have permission to modify this task");
        }

        TodoEntity existingTodo = new TodoEntity();
        existingTodo.update(todo, user);

        return sqlTodoRepository.save(existingTodo);
    }

    public void removeTodo(Long id) {
        sqlTodoRepository.deleteById(id);
    }
}