package com.project.api.member.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.api.exception.BusinessMessage;
import com.project.api.member.controller.dto.VacationRequestDto;
import com.project.api.member.enums.VacationType;
import com.project.api.member.service.VacationService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.time.LocalDate;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = MemberVacationController.class)
class MemberVacationInfoControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MemberVacationController memberVacationController;

    @MockBean
    private VacationService vacationService;

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

        given(vacationService.requestVacation(any())).willReturn(1L);
        // when
        ResultActions result = mvc.perform(post("/api/member/vacation")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)));

        // then
        result
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", "/api/member/vacation/1"));
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

        given(vacationService.requestVacation(any())).willReturn(1L);
        // when
        ResultActions result = mvc.perform(post("/api/member/vacation")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)));

        // then
        result
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("message").value(BusinessMessage.INVALID_VACATION_DATE.getMessage()));
    }

}