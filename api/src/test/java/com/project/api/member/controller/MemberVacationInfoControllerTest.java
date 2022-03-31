package com.project.api.member.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.api.config.SecurityConfig;
import com.project.api.exception.BusinessMessage;
import com.project.api.member.controller.dto.VacationRequestDto;
import com.project.api.member.enums.VacationType;
import com.project.api.member.service.VacationService;
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

import java.time.LocalDate;
import java.util.stream.Stream;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(
        controllers = MemberVacationController.class,
        excludeFilters = {
                @ComponentScan.Filter(
                        type = FilterType.ASSIGNABLE_TYPE,
                        classes = SecurityConfig.class
                )
        }
)
class MemberVacationInfoControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private WebApplicationContext ctx;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private VacationService vacationService;

    @BeforeEach
    public void setUp() {
        mvc = MockMvcBuilders.webAppContextSetup(ctx)
                .build();
    }

    @Test
    @DisplayName("휴가 신청하기")
    public void requestVacation() throws Exception {
        // given
        VacationRequestDto dto = VacationRequestDto.builder()
                .startDate(LocalDate.now())
                .endDate(LocalDate.now().plusDays(1))
                .use(1)
                .vacationType(VacationType.ANNUAL)
                .build();

        given(vacationService.requestVacation(any(), any())).willReturn(1L);
        // when
        ResultActions result = mvc.perform(post("/api/member/vacation")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)));

        // then
        result
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", "/api/member/vacation/1"));
    }

    @ParameterizedTest
    @MethodSource
    @DisplayName("휴가 NotNull 파라미터 검증")
    public void validateParameterForRequestVacation(VacationRequestDto dto, HttpStatus httpStatus, String message) throws Exception {
        // when
        ResultActions result = mvc.perform(post("/api/member/vacation")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)));

        // then
        result
                .andExpect(status().is(httpStatus.value()))
                .andExpect(jsonPath("message").value(message));
    }

    private static Stream<Arguments> validateParameterForRequestVacation() {
        return Stream.of(
                Arguments.of(
                        VacationRequestDto.builder()
                                .vacationType(VacationType.ANNUAL)
                                .build(), HttpStatus.BAD_REQUEST, "시작일을 입력 해주세요"),

                Arguments.of(
                        VacationRequestDto.builder()
                                .startDate(LocalDate.now())
                                .build(), HttpStatus.BAD_REQUEST, "휴가 타입을 입력 해주세요 (연차:ANNUAL, 반차:HALF, 반반차:HALF_HALF)")
        );
    }

    @Test
    @DisplayName("휴가 신청 실패 (휴가 시작일 > 휴가 종료일 경우)")
    public void requestVacationForFail() throws Exception {
        // given
        VacationRequestDto dto = VacationRequestDto.builder()
                .startDate(LocalDate.now().plusDays(1))
                .endDate(LocalDate.now())
                .use(1)
                .vacationType(VacationType.ANNUAL)
                .build();

        given(vacationService.requestVacation(any(), any())).willReturn(1L);
        // when
        ResultActions result = mvc.perform(post("/api/member/vacation")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)));

        // then
        result
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("message").value(BusinessMessage.INVALID_VACATION_DATE.getMessage()));
    }

    @Test
    @DisplayName("휴가 취소하기")
    public void cancelVacation() throws Exception {
        // when
        ResultActions result = mvc.perform(delete("/api/member/vacation/1"));

        // then
        result
                .andExpect(status().isNoContent());
    }

}