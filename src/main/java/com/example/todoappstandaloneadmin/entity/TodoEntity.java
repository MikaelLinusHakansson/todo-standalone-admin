package com.example.todoappstandaloneadmin.entity;

import lombok.Data;

@Data
public class TodoEntity {
    private String id;
    private String name;
    private String date;
    private Boolean completed;

    public TodoEntity() {
    }

    public TodoEntity(String name, String date, Boolean completed, String id) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.completed = completed;
    }

    public TodoEntity(String name, String date, Boolean completed) {
        this.name = name;
        this.date = date;
        this.completed = completed;
    }
}
