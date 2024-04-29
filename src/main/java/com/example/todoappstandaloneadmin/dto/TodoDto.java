package com.example.todoappstandaloneadmin.dto;

import com.example.todoappstandaloneadmin.entity.TodoEntity;
import lombok.Data;

import java.util.List;
import java.util.ArrayList;

@Data
public class TodoDto {
    private Long id;
    private String name;
    private String date;
    private Boolean completed;
    private String username;

    public TodoDto() {
    }

    public String getUsername() {
        return username;
    }

    public Long getId() {
        return id;
    }

    public static TodoDto fromEntity(TodoEntity todoEntity) {
        TodoDto todoDto = new TodoDto();

        todoDto.setId(todoEntity.getId());
        todoDto.setName(todoEntity.getName());
        todoDto.setDate(todoEntity.getDate());
        todoDto.setCompleted(todoEntity.getCompleted());

        if (!todoEntity.getUser().getUsername().isBlank()) {
            todoDto.setUsername(todoEntity.getUser().getUsername());
        }

        return todoDto;
    }

    public static List<TodoDto> valuesOf(List<TodoEntity> todoEntities) {
        List<TodoDto> todoDTOS = new ArrayList<>();

        for (TodoEntity todoEntity : todoEntities) {
            todoDTOS.add(TodoDto.fromEntity(todoEntity));
        }

        return todoDTOS;
    }
}