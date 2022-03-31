package com.project.api.member.controller.dto;

import com.project.api.member.domain.MemberVacation;
import com.project.api.member.enums.VacationType;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class GetVacationDetailResponseDto {

    private Long memberVacationId;

    private LocalDate startDate;

    private LocalDate endDate;

    private float use;

    private String comments;

    private VacationType vacationType;

    public GetVacationDetailResponseDto(MemberVacation memberVacation) {
        this.memberVacationId = memberVacation.getId();
        this.startDate = memberVacation.getStartDate();
        this.endDate = memberVacation.getEndDate();
        this.use = memberVacation.getUse();
        this.comments = memberVacation.getComments();
        this.vacationType = memberVacation.getVacationType();
    }
}
