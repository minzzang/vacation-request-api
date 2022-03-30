package com.project.api.member.service;

import com.project.api.exception.BusinessException;
import com.project.api.exception.BusinessMessage;
import com.project.api.member.controller.dto.VacationRequestDto;
import com.project.api.member.domain.Member;
import com.project.api.member.domain.MemberVacation;
import com.project.api.member.domain.MemberVacationInfo;
import com.project.api.member.domain.MemberVacationRepository;
import com.project.api.member.enums.VacationType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class VacationServiceTest {

    @InjectMocks
    private VacationService vacationService;

    @Mock
    private MemberService memberService;

    @Mock
    private MemberVacationInfoService memberVacationInfoService;

    @Mock
    private MemberVacationRepository memberVacationRepository;

    @Test
    @DisplayName("휴가 신청하기")
    public void requestVacation() {
        // given
        Member member = Member.builder().id(1L).build();
        MemberVacationInfo memberVacationInfo = MemberVacationInfo.builder()
                .memberId(1L).totalVacationDays(15).remains(15).build();

        MemberVacation memberVacation = MemberVacation.builder().id(1L).build();

        VacationRequestDto dto = VacationRequestDto.builder()
                .startDate(LocalDate.now()).endDate(LocalDate.now().plusDays(1))
                .use(1).vacationType(VacationType.ANNUAL).build();

        given(memberService.findByEmail(any())).willReturn(member);
        given(memberVacationInfoService.findById(1L)).willReturn(memberVacationInfo);
        given(memberVacationRepository.save(any())).willReturn(memberVacation);
        // when
        Long id = vacationService.requestVacation(dto);
        // then
        assertThat(id).isEqualTo(1L);
        assertThat(memberVacationInfo.getRemains()).isEqualTo(14);
    }

    @Test
    @DisplayName("휴가 신청 실패(남은 휴가 없음)")
    public void requestVacationForFail() {
        // given
        Member member = Member.builder().id(1L).build();
        MemberVacationInfo memberVacationInfo = MemberVacationInfo.builder()
                .memberId(1L).totalVacationDays(15).remains(2).build();

        VacationRequestDto dto = VacationRequestDto.builder()
                .startDate(LocalDate.now()).endDate(LocalDate.now().plusDays(3))
                .use(3).vacationType(VacationType.ANNUAL).build();

        given(memberService.findByEmail(any())).willReturn(member);
        given(memberVacationInfoService.findById(1L)).willReturn(memberVacationInfo);
        // when
        BusinessException exception = assertThrows(BusinessException.class,
                () -> vacationService.requestVacation(dto));
        // then
        assertThat(exception.getMessage()).isEqualTo(BusinessMessage.NO_VACATION_LEFT.getMessage());
    }

}