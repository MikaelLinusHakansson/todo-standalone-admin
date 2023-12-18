package com.example.todoappstandaloneadmin.dao.repository;

import com.example.todoappstandaloneadmin.entity.TodoEntity;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.util.List;

@Repository
public interface Dao {
    List<TodoEntity> getAll() throws SQLException;
    TodoEntity getById(Long id) throws SQLException;
    TodoEntity add(TodoEntity todo) throws SQLException;
    TodoEntity update(TodoEntity todo, Long id) throws SQLException;
    void delete(Long id) throws SQLException;
}
