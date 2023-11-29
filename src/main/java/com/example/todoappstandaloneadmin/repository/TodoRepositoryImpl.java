package com.example.todoappstandaloneadmin.repository;

import com.example.todoappstandaloneadmin.entity.TodoEntity;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class TodoRepositoryImpl implements TodoRepository{
    private final List<TodoEntity> todoEntities;

    public TodoRepositoryImpl(List<TodoEntity> todoEntities) {
        this.todoEntities = todoEntities;
    }

    @Override
    public List<TodoEntity> getAllTodos() {
        return this.todoEntities;
    }

    @Override
    public TodoEntity getById(Long id) {
        Optional<TodoEntity> findTodo = todoEntities.stream()
                .filter(findId -> findId.getId().equals(id))
                .findFirst();

        return findTodo.orElse(null);
    }

    @Override
    public void addTodo(TodoEntity todo) {
        todoEntities.add(todo);
    }

    @Override
    public TodoEntity removeTodo(Long id) {
        TodoEntity tempTodo = new TodoEntity("No match", "No match", false);

        for (TodoEntity listOfTodo : todoEntities) {

            if (listOfTodo.getId().equals(id)) {
                todoEntities.remove(listOfTodo);

                return listOfTodo;
            }
        }
        return tempTodo;
    }

    @Override
    public TodoEntity updateById(TodoEntity todo, Long id) {
        TodoEntity tempTodo = new TodoEntity("No match", "No match", false);

        for (TodoEntity listOfTodo : todoEntities) {

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
}
