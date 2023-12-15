package com.example.todoappstandaloneadmin.dao;

import com.example.todoappstandaloneadmin.entity.TodoEntity;
import org.springframework.stereotype.Service;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Service
public class TodoDao {
    private final static String dbUrl = "jdbc:mysql://localhost:3306/local_server";
    private final static String user = "root";
    private final static String password = "Linsalainen5931";

    public TodoDao() {
    }

    public static List<TodoEntity> getAll() throws Exception {
        try (
                Connection myConn = DriverManager.getConnection(dbUrl, user, password);
                Statement myStatement = myConn.createStatement();
                ResultSet myRs = myStatement.executeQuery("select * from todo_entity");)
        {
            List<TodoEntity> todoEntityList = new ArrayList<>();

            while(myRs.next()) {
                TodoEntity todoEntity = new TodoEntity();

                todoEntity.setId(myRs.getLong("id"));
                todoEntity.setName(myRs.getString("name"));
                todoEntity.setDate(myRs.getString("date"));
                todoEntity.setCompleted(myRs.getBoolean("completed"));

                todoEntityList.add(todoEntity);
            }

            return todoEntityList;
        }

        catch(Exception exception) {
            exception.printStackTrace();
            throw exception;
        }
    }

    public static TodoEntity getById(Long id) {
        TodoEntity todo = new TodoEntity();
        try (
                Connection myConn = DriverManager.getConnection(dbUrl, user, password);
                PreparedStatement myStatement = myConn.prepareStatement("select * from todo_entity where id = ?");)
        {
            myStatement.setLong(1, id);

            try (ResultSet myRs = myStatement.executeQuery();) {
                while (myRs.next()) {
                    todo.setCompleted(myRs.getBoolean("completed"));
                    todo.setId(myRs.getLong("id"));
                    todo.setName(myRs.getString("name"));
                    todo.setDate(myRs.getString("date"));
                }
            }
        }

        catch (Exception exception) {
            exception.printStackTrace();
        }

        return todo;
    }

    public static TodoEntity add(TodoEntity todo) throws SQLException {
        try (
                Connection myConn = DriverManager.getConnection(dbUrl, user, password);
                PreparedStatement myPreparedStatement = myConn.prepareStatement
                        ("insert into todo_entity " +
                                "(completed, name, date) " +
                                "values (?, ?, ?)",
                                Statement.RETURN_GENERATED_KEYS))
        {
            myPreparedStatement.setBoolean(1, todo.getCompleted());
            myPreparedStatement.setString(2, todo.getName());
            myPreparedStatement.setString(3, todo.getDate());

            int rowsAffected = myPreparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                try (ResultSet myRs = myPreparedStatement.getGeneratedKeys()) {
                    if (myRs.next()) {
                        todo.setId(myRs.getLong(1));
                    }
                }

                catch (Exception exception) {
                    exception.getStackTrace();
                }
            }

            return todo;
        }

        catch(Exception exception) {
            exception.printStackTrace();
            throw exception;
        }
    }

    public static TodoEntity update(TodoEntity task, Long id) throws SQLException {
        try (
                Connection myConn = DriverManager.getConnection(dbUrl, user, password);
                PreparedStatement myPreparedStatement = myConn.prepareStatement(
                        "update todo_entity " +
                                "set completed=?, name=?, date=? where id=?");)
        {
            boolean completedvalue = task.getCompleted() != null ? task.getCompleted() : false;

            myPreparedStatement.setBoolean(1, completedvalue);
            myPreparedStatement.setString(2, task.getName());
            myPreparedStatement.setString(3, task.getDate());
            myPreparedStatement.setLong(4, id);

            int rowsAffected = myPreparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                task.setId(id);
            }

            return task;
        }

        catch (Exception exception) {
            exception.getStackTrace();
            throw exception;
        }
    }

    public static void delete(Long id) throws SQLException {
        try (
                Connection myConn = DriverManager.getConnection(dbUrl, user, password);
                PreparedStatement myStatement = myConn.prepareStatement("delete from todo_entity where id=?");)
        {
            myStatement.setLong(1, id);
            int rowsAffected = myStatement.executeUpdate();
        }

        catch (Exception exception) {
            exception.printStackTrace();
            throw exception;
        }
    }
}
