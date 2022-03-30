package com.project.api.member.enums;

import com.project.api.member.controller.dto.Calculable;
import com.project.api.utils.VacationCalculator;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;

@RequiredArgsConstructor
@Getter
public enum VacationType {

    ANNUAL(dto -> {
        LocalDate startDate = dto.getStartDate();
        LocalDate endDate = dto.getEndDate();
        int year = startDate.getYear();

        return VacationCalculator.calculate(year, startDate, endDate);
    }),
    HALF(dto -> 0.5f),
    HALF_HALF(dto -> 0.25f);

    private final Calculable calculable;

    public boolean isAnnual() {
        return this == ANNUAL;
    }
}
