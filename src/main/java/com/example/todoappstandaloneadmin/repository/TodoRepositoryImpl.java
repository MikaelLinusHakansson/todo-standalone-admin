package com.example.todoappstandaloneadmin.repository;

import com.example.todoappstandaloneadmin.entity.TodoEntity;
import com.example.todoappstandaloneadmin.exceptions.badRequest.EntityNameNotFoundBadRequest;
import com.example.todoappstandaloneadmin.exceptions.notFound.EntityNotFoundException;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TodoRepositoryImpl implements TodoRepository {
    private final List<TodoEntity> todoEntities;

    public TodoRepositoryImpl(List<TodoEntity> todoEntities) {
        this.todoEntities = todoEntities;
    }

    @Override
    public List<TodoEntity> getAllTodos() {
        return todoEntities;
    }

    @Override
    public TodoEntity getById(String id) {
        for (TodoEntity todoEntity : todoEntities) {
            if (id.equals(todoEntity.getId())) {
                 return todoEntity;
            }
        }

        throw new EntityNotFoundException();
    }

    @Override
    public String addTodo(TodoEntity todo) {
        if (!todo.getName().isEmpty()) {
            todoEntities.add(todo);
            return "Added todo " + todo.getId();
        }

        throw new EntityNameNotFoundBadRequest();
    }

    @Override
    public String updateById(TodoEntity todo, String id) {
        for (TodoEntity listOfTodo : todoEntities) {
            if (id.equals(listOfTodo.getId())) {
                if (todo.getName().isEmpty()) {
                    throw new EntityNameNotFoundBadRequest();
                }

                listOfTodo.setId(todo.getId());
                listOfTodo.setName(todo.getName());
                listOfTodo.setDate(todo.getDate());
                listOfTodo.setCompleted(todo.getCompleted());

                return "Task with id " + id + " was updated";
            }
        }

        throw new EntityNotFoundException();
    }

    @Override
    public void removeTodo(String id) {
        for (TodoEntity listOfTodo : todoEntities) {
            if (id.equals(listOfTodo.getId())) {
                todoEntities.remove(listOfTodo);
                return;
            }
        }

        throw new EntityNotFoundException();
    }
}
