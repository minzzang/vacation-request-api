package com.project.api.auth.controller;

import com.project.api.auth.controller.dto.AuthRequestDto;
import com.project.api.auth.controller.dto.AuthResponseDto;
import com.project.api.auth.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/api/auth")
    public ResponseEntity<?> login(@RequestBody @Valid AuthRequestDto dto) {
        String token = authService.login(dto.getEmail(), dto.getPassword());

        return ResponseEntity.ok(new AuthResponseDto(token));
    }
}
