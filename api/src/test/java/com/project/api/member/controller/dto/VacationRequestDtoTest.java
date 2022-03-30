package com.project.api.member.controller.dto;

import com.project.api.member.enums.VacationType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.time.LocalDate;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class VacationRequestDtoTest {

    @DisplayName("시작일과 종료일로 휴가 일 수를 계산")
    @ParameterizedTest
    @MethodSource
    public void calculateVacationDays(VacationRequestDto dto, float use) {
        // when
        dto.calculateVacationDays();
        // then
        assertThat(dto.getUse()).isEqualTo(use);
    }

    private static Stream<Arguments> calculateVacationDays() {
        return Stream.of(
                Arguments.of(
                        VacationRequestDto.builder()
                                .vacationType(VacationType.HALF_HALF).build(), 0.25f),

                Arguments.of(
                        VacationRequestDto.builder()
                                .vacationType(VacationType.HALF).build(), 0.5f),

                Arguments.of(
                        VacationRequestDto.builder()
                                .vacationType(VacationType.ANNUAL)
                                .startDate(LocalDate.of(2022, 3, 4))
                                .endDate(LocalDate.of(2022, 3, 10)).build(), 4.0f),

                Arguments.of(
                        VacationRequestDto.builder()
                                .vacationType(VacationType.ANNUAL)
                                .startDate(LocalDate.of(2022, 9, 8))
                                .endDate(LocalDate.of(2022, 9, 13)).build(), 2.0f)
        );
    }
}