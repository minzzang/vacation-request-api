package com.project.api.member.controller.dto;

import com.project.api.member.domain.MemberVacationInfo;
import lombok.Getter;

import java.util.List;

@Getter
public class GetVacationResponseDto {

    private List<GetVacationDetailResponseDto> list;

    private float totalVacationDays;

    private float remains;

    public GetVacationResponseDto(List<GetVacationDetailResponseDto> memberVacations, MemberVacationInfo memberVacationInfo) {
        this.list = memberVacations;
        this.totalVacationDays = memberVacationInfo.getTotalVacationDays();
        this.remains = memberVacationInfo.getRemains();
    }
}
