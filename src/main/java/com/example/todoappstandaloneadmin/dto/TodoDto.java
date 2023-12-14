package com.example.todoappstandaloneadmin.dto;

import lombok.Data;
@Data
public class TodoDto {
    private Long id;
    private String name;
    private String date;
    private Boolean completed;

    public TodoDto(Long id, String name, String date, Boolean completed) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.completed = completed;
    }

    public TodoDto(String name, String date, Boolean completed) {
        this.name = name;
        this.date = date;
        this.completed = completed;
    }

    public TodoDto() {
    }
}
