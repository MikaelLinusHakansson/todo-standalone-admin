package com.example.todoappstandaloneadmin.exceptions.badRequest;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class EntityNameNotFoundBadRequest extends RuntimeException{
    private String message;

    public EntityNameNotFoundBadRequest(String message) {

        super(message);
        this.message = message;
    }

    public EntityNameNotFoundBadRequest() {
    }
}
