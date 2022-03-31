package com.project.api.auth.controller.dto;

import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotBlank;

@Getter
@Builder
public class AuthRequestDto {

    @NotBlank(message = "아이디와 비밀번호를 확인해주세요.")
    private String email;

    @NotBlank(message = "아이디와 비밀번호를 확인해주세요.")
    private String password;
}
