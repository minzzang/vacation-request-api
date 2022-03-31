package com.project.api.auth.controller.dto;

import lombok.Getter;

@Getter
public class AuthResponseDto {

    private String accessToken;

    public AuthResponseDto(String accessToken) {
        this.accessToken = accessToken;
    }
}
