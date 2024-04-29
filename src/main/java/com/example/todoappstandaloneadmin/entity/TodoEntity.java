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

    public void setUsername(String username) {
        this.user.setUsername(username);
    }

    public String getUsername() {
        return this.user.getUsername();
    }

    public static TodoEntity valueOf(TodoDto todoDto) {
        TodoEntity todoEntity = new TodoEntity();

        todoEntity.setName(todoDto.getName());
        todoEntity.setDate(todoDto.getDate());
        todoEntity.setCompleted(todoDto.getCompleted());

        return todoEntity;
    }

    public static TodoEntity fromDto(TodoDto todoDto) {
        TodoEntity todoEntity = new TodoEntity();

        todoEntity.setId(todoDto.getId());
        todoEntity.setName(todoDto.getName());
        todoEntity.setDate(todoDto.getDate());
        todoEntity.setCompleted(todoDto.getCompleted());

        return todoEntity;
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
