package com.example.todoappstandaloneadmin.dao.repository;

import com.example.todoappstandaloneadmin.entity.TodoEntity;
import com.example.todoappstandaloneadmin.exceptions.badRequest.EntityNameNotFoundBadRequest;
import com.example.todoappstandaloneadmin.exceptions.notFound.EntityNotFoundException;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Component
public class TodoDaoImpl implements Dao {
    private final String dbUrl = "jdbc:mysql://localhost:3306/local_server";
    private final String dbUser = "root";
    private final String dbPass = "Linsalainen5931";

    @Override
    public List<TodoEntity> getAll() throws SQLException {
        List<TodoEntity> todoEntityList = new ArrayList<>();
        try (
                Connection conn = DriverManager.getConnection(dbUrl, dbUser, dbPass);
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery("select * from todo_entity");
        ) {
            while (rs.next()) {
                TodoEntity tempTodo = new TodoEntity();

                tempTodo.setCompleted(rs.getBoolean("completed"));
                tempTodo.setId(rs.getLong("Id"));
                tempTodo.setName(rs.getString("name"));
                tempTodo.setDate(rs.getString("date"));

                todoEntityList.add(tempTodo);
            }
        }
        return todoEntityList;
    }

    @Override
    public TodoEntity getById(Long id) throws SQLException {
        TodoEntity todoEntity = new TodoEntity();

        try (
                Connection conn = DriverManager.getConnection(dbUrl, dbUser, dbPass);
                PreparedStatement preparedStmt = conn.prepareStatement("select * from todo_entity where id = ?");
        ) {
            preparedStmt.setLong(1, id);

            try (ResultSet rs = preparedStmt.executeQuery()) {
                while (rs.next()) {
                    todoEntity.setId(rs.getLong("id"));
                    todoEntity.setCompleted(rs.getBoolean("completed"));
                    todoEntity.setName(rs.getString("name"));
                    todoEntity.setDate(rs.getString("date"));
                }
            }

            if (todoEntity.getId() == null) {
                throw new EntityNotFoundException();
            }
        }
        return todoEntity;
    }

    @Override
    public TodoEntity add(TodoEntity todo) throws SQLException {
        try (
                Connection conn = DriverManager.getConnection(dbUrl, dbUser, dbPass);
                PreparedStatement preparedStatement = conn.prepareStatement(
                        "insert into todo_entity " + "(completed, name, date) " +
                                "values (?, ?, ?)", Statement.RETURN_GENERATED_KEYS)) {

            preparedStatement.setBoolean(1, todo.getCompleted());
            preparedStatement.setString(2, todo.getName());
            preparedStatement.setString(3, todo.getDate());

            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                try (ResultSet rs = preparedStatement.getGeneratedKeys()) {
                    if (rs.next()) {
                        todo.setId(rs.getLong(1));
                    }
                }
                return todo;
            }
        }
        throw new EntityNameNotFoundBadRequest();
    }

    @Override
    public TodoEntity update(TodoEntity todo, Long id) throws SQLException {
        if (!todo.getName().isBlank()) {
            try (
                    Connection conn = DriverManager.getConnection(dbUrl, dbUser, dbPass);
                    PreparedStatement preparedStatement = conn.prepareStatement(
                            "update todo_entity " +
                                    "set completed=?, name=?, date=? where id=?")
            ) {
                boolean completedValue = todo.getCompleted() != null ? todo.getCompleted() : false;

                preparedStatement.setBoolean(1, Boolean.TRUE.equals(todo.getCompleted()));
                preparedStatement.setString(2, todo.getName());
                preparedStatement.setString(3, todo.getDate());
                preparedStatement.setLong(4, id);

                int rowsAffected = preparedStatement.executeUpdate();
                if (rowsAffected > 0) {
                    todo.setId(id);
                    return todo;
                }
                throw new EntityNotFoundException();
            }
        }
        throw new EntityNameNotFoundBadRequest();
    }

    @Override
    public void delete(Long id) throws SQLException {
        try (
                Connection conn = DriverManager.getConnection(dbUrl, dbUser, dbPass);
                PreparedStatement preparedStatement = conn.prepareStatement("delete from todo_entity where id=?")
        ) {
            preparedStatement.setLong(1, id);

            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows > 0) {
                return;
            }
            throw new EntityNotFoundException();
        }
    }
}
