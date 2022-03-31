package com.project.api.auth.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.api.auth.controller.dto.AuthRequestDto;
import com.project.api.auth.service.AuthService;
import com.project.api.config.SecurityConfig;
import com.project.api.exception.BusinessMessage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.stream.Stream;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(
        controllers = AuthController.class,
        excludeFilters = {
                @ComponentScan.Filter(
                        type = FilterType.ASSIGNABLE_TYPE,
                        classes = SecurityConfig.class
                )
        }
)
class AuthControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private WebApplicationContext ctx;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private AuthService authService;

    @BeforeEach
    public void setUp() {
        mvc = MockMvcBuilders.webAppContextSetup(ctx)
                .build();
    }

    @Test
    @DisplayName("로그인 성공")
    public void login() throws Exception {
        // given
        String email = "minzzang@gmail.com";
        String password = "1234";

        AuthRequestDto dto = AuthRequestDto.builder()
                .email(email).password(password).build();

        String token = "token";

        given(authService.login(email, password)).willReturn(token);
        // when
        ResultActions result = mvc.perform(post("/api/auth")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)));

        // then
        result
                .andExpect(status().isOk())
                .andExpect(jsonPath("accessToken").value(token));
    }

    @ParameterizedTest
    @MethodSource
    @DisplayName("로그인 NotBlank 파라미터 검증")
    public void validateParameterForLogin(AuthRequestDto dto, HttpStatus httpStatus, String message) throws Exception {
        // when
        ResultActions result = mvc.perform(post("/api/auth")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)));

        // then
        result
                .andExpect(status().is(httpStatus.value()))
                .andExpect(jsonPath("message").value(message));
    }

    private static Stream<Arguments> validateParameterForLogin() {
        return Stream.of(
                Arguments.of(
                        AuthRequestDto.builder()
                                .email("minzzang@gmail.com")
                                .build(), HttpStatus.BAD_REQUEST, BusinessMessage.INVALID_LOGIN_INFO.getMessage()),

                Arguments.of(
                        AuthRequestDto.builder()
                                .password("1234")
                                .build(), HttpStatus.BAD_REQUEST, BusinessMessage.INVALID_LOGIN_INFO.getMessage())
        );
    }

}