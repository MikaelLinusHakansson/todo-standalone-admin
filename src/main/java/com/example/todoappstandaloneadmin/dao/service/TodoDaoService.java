package com.example.todoappstandaloneadmin.dao.service;

import com.example.todoappstandaloneadmin.dao.repository.Dao;
import com.example.todoappstandaloneadmin.dao.repository.TodoDaoImpl;
import com.example.todoappstandaloneadmin.entity.TodoEntity;
import com.example.todoappstandaloneadmin.exceptions.badRequest.EntityNameNotFoundBadRequest;
import com.example.todoappstandaloneadmin.exceptions.notFound.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.*;
import java.util.List;

@Service
public class TodoDaoService {
    private static final TodoDaoImpl todoDaoRepository = new TodoDaoImpl();
    private final Dao daoRepo;

    @Autowired
    public TodoDaoService(Dao dao) {
        this.daoRepo = dao;
    }

    public List<TodoEntity> getAll() throws SQLException {
        return daoRepo.getAll();
    }

    public TodoEntity getById(Long id) throws SQLException {
        if (daoRepo.getById(id).getId() != null) {
            return daoRepo.getById(id);
        }
        throw new EntityNotFoundException();
    }

    public TodoEntity add(TodoEntity todo) throws SQLException {
        if (!todo.getName().isBlank()) {
            return daoRepo.add(todo);
        }
        throw new EntityNameNotFoundBadRequest();
    }

    public TodoEntity update(TodoEntity todo, Long id) throws SQLException {
        return daoRepo.update(todo, id);
    }

    public void delete(Long id) throws SQLException {
        daoRepo.delete(id);
    }
}
