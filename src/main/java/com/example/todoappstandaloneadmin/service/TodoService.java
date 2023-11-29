package com.example.todoappstandaloneadmin.service;

import com.example.todoappstandaloneadmin.entity.TodoEntity;
import com.example.todoappstandaloneadmin.repository.TodoRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TodoService implements TodoRepository {
    private final List<TodoEntity> listOfTodos;

    public TodoService() {
        this.listOfTodos = new ArrayList<>();
    }

    public List<TodoEntity> getAllTodos() {
        return this.listOfTodos;
    }

    public void addTodo(TodoEntity task) {
        this.listOfTodos.add(task);
    }

    @Override
    public TodoEntity removeTodo(Long id) {
        TodoEntity tempTodo = new TodoEntity("No match", "No match", false);

        for (TodoEntity listOfTodo : this.listOfTodos) {

            if (listOfTodo.getId().equals(id)) {
                this.listOfTodos.remove(listOfTodo);

                return listOfTodo;
            }
        }
        return tempTodo;
    }

    @Override
    public TodoEntity updateById(TodoEntity todo, Long id) {
        TodoEntity tempTodo = new TodoEntity("No match", "No match", false);

        for (TodoEntity listOfTodo : this.listOfTodos) {

            if (listOfTodo.getId().equals(id)) {
                listOfTodo.setId(todo.getId());
                listOfTodo.setName(todo.getName());
                listOfTodo.setDate(todo.getDate());
                listOfTodo.setCompleted(todo.getCompleted());

                return listOfTodo;
            }
        }

        return tempTodo;
    }

    public TodoEntity getById(Long id) {
        Optional<TodoEntity> findTodo = listOfTodos.stream()
                .filter(findId -> findId.getId().equals(id))
                .findFirst();

        return findTodo.orElse(null);
    }
}
