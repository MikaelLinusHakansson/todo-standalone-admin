package com.example.todoappstandaloneadmin.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.todoappstandaloneadmin.dto.TodoDto;
import com.example.todoappstandaloneadmin.entity.TodoEntity;
import com.example.todoappstandaloneadmin.entity.UserEntity;
import com.example.todoappstandaloneadmin.exceptions.badRequest.EntityNameNotFoundBadRequest;
import com.example.todoappstandaloneadmin.exceptions.notFound.EntityNotFoundException;
import com.example.todoappstandaloneadmin.repository.SqlTodoRepository;
import com.example.todoappstandaloneadmin.repository.UserRepository;

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

    public TodoEntity updateById(TodoDto todo) throws Exception {
        String username = sqlTodoRepository.findById(todo.getId()).get().getUsername();

        UserEntity user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Username not found"));

        if (!username.equals(user.getUsername())) {
            throw new AccessDeniedException("User does not have permission to modify this task");
        }

        if (todo.getName() == null || todo.getName().isBlank()) {
            todo.setName(sqlTodoRepository.findById(todo.getId()).get().getName());
        }

        if (todo.getDate() == null) {
            todo.setDate(sqlTodoRepository.findById(todo.getId()).get().getDate());
        }

        if (todo.getCompleted() == null) {
            todo.setCompleted(sqlTodoRepository.findById(todo.getId()).get().getCompleted());
        }

        if (todo.getUsername() == null) {
            todo.setUsername(sqlTodoRepository.findById(todo.getId()).get().getUsername());
        }

        TodoEntity existingTodo = new TodoEntity();
        existingTodo.update(todo, user);

        return sqlTodoRepository.save(existingTodo);
    }

    public void removeTodo(Long id) {
        sqlTodoRepository.deleteById(id);
    }
}