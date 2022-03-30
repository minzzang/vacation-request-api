package com.project.api.auth.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.api.exception.BusinessMessage;
import com.project.api.exception.ErrorResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType(APPLICATION_JSON_VALUE);

        ErrorResponse errorResponse = new ErrorResponse(BusinessMessage.REQUIRED_LOGIN.getMessage());

        OutputStream outputStream = response.getOutputStream();
        objectMapper.writeValue(outputStream, errorResponse);
        outputStream.flush();
    }
}
