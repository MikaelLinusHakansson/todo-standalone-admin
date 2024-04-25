package com.example.todoappstandaloneadmin.entity;

import com.example.todoappstandaloneadmin.dto.TodoDto;
import jakarta.persistence.*;
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

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

    public TodoEntity(Long id, String name, String date, Boolean completed) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.completed = completed;
    }

    public void setUserEntity(UserEntity user) {
        this.user = user;
    }

    public static TodoEntity fromEntity(TodoEntity todoEntity) {
        return new TodoEntity(todoEntity.getName(), todoEntity.getDate(), todoEntity.getCompleted());
    }

    public static TodoEntity valueOf(TodoDto todoDto) {
        return new TodoEntity(todoDto.getName(), todoDto.getDate(), todoDto.getCompleted());
    }

    public static TodoEntity fromDto(TodoDto todoDto) {
        return new TodoEntity(todoDto.getId(), todoDto.getName(), todoDto.getDate(), todoDto.getCompleted());
    }

    public TodoEntity update(TodoDto todoDTO, UserEntity user) {
        this.id = todoDTO.getId();
        this.name = todoDTO.getName();
        this.date = todoDTO.getDate();
        this.completed = todoDTO.getCompleted();
        this.user = user;
        return this;
    }
}
