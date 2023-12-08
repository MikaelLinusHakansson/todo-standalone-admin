package com.example.todoappstandaloneadmin.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import org.springframework.validation.annotation.Validated;

@Data
@Entity
@Validated
public class TodoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String date;
    private Boolean completed;

    public TodoEntity() {
    }

    public TodoEntity(String name, String date) {
        this.name = name;
        this.date = date;
    }

    public TodoEntity(String name, String date, Boolean completed) {
        this.name = name;
        this.date = date;
        this.completed = completed;
    }
}
