package com.example.todoappstandaloneadmin.repository;

import com.example.todoappstandaloneadmin.HttpEnums.HttpStatus;
import com.example.todoappstandaloneadmin.entity.TodoEntity;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Repository;
import org.springframework.web.server.ResponseStatusException;

import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class TodoRepositoryImpl implements TodoRepository {
    private final List<TodoEntity> todoEntities;

    public TodoRepositoryImpl(List<TodoEntity> todoEntities) {
        this.todoEntities = todoEntities;
    }

    @Override
    public List<TodoEntity> getAllTodos() {
        if (todoEntities.size() < 1) {

            return this.todoEntities;
        }

        throw new ResponseStatusException(HttpStatusCode.valueOf(404), HttpStatus.HTTP_STATUS_404.name());
    }

    @Override
    public TodoEntity getById(Long id) {
        for (TodoEntity todoEntity : todoEntities) {

            if (id.equals(todoEntity.getId())) {
                 return todoEntity;
            }
        }


        throw new ResponseStatusException(HttpStatusCode.valueOf(404), "Entity Not Found");
    }

    @Override
    public TodoEntity addTodo(TodoEntity todo) {
        if (todo == null) {

            throw new ResponseStatusException(HttpStatusCode.valueOf(404), "Entity Not Found");
        }

        todoEntities.add(todo);

        return todo;
    }

    @Override
    public TodoEntity removeTodo(Long id) {
        for (TodoEntity listOfTodo : todoEntities) {

            if (listOfTodo.getId().equals(id)) {
                todoEntities.remove(listOfTodo);

                return listOfTodo;
            }
        }

        throw new ResponseStatusException(HttpStatusCode.valueOf(404), "Entity Not Found");
    }

    @Override
    public TodoEntity updateById(TodoEntity todo, Long id) {
        for (TodoEntity listOfTodo : todoEntities) {

            if (listOfTodo.getId().equals(id)) {
                listOfTodo.setId(todo.getId());
                listOfTodo.setName(todo.getName());
                listOfTodo.setDate(todo.getDate());
                listOfTodo.setCompleted(todo.getCompleted());

                return listOfTodo;
            }
        }

        throw new ResponseStatusException(HttpStatusCode.valueOf(404), "Entity Not Found");
    }
}
