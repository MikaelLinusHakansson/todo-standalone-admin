package com.example.todoappstandaloneadmin.dto;

import lombok.Data;

@Data
public class AuthorizationResponseDto {
    private String accessToken;
    private String tokenType = "Bearer ";

    public AuthorizationResponseDto(String accessToken) {
        this.accessToken = accessToken;
    }
}
