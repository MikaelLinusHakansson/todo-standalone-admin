package com.example.todoappstandaloneadmin.dto;

import com.example.todoappstandaloneadmin.entity.TodoEntity;
import lombok.Data;

@Data
public class TodoDto {
    private Long id;
    private String name;
    private String date;
    private Boolean completed;
    private String username;

    public TodoDto(String name, String date, Boolean completed, String username) {
        this.name = name;
        this.date = date;
        this.completed = completed;
        this.username = username;
    }

    public TodoDto(TodoEntity todoEntity) {
        this.id = todoEntity.getId();
        this.name = todoEntity.getName();
        this.date = todoEntity.getDate();
        this.completed = todoEntity.getCompleted();
        this.username = todoEntity.getUser().getUsername();
    }

    public TodoDto() {
    }

    public String getUsername() {
        return username;
    }

    public static TodoDto fromEntity(TodoEntity todoEntity) {
        return new TodoDto(todoEntity);
    }
}