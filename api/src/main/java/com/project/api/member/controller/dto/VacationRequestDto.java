package com.project.api.member.controller.dto;

import com.project.api.exception.BusinessException;
import com.project.api.exception.BusinessMessage;
import com.project.api.member.domain.MemberVacationInfo;
import com.project.api.member.domain.MemberVacation;
import com.project.api.member.enums.VacationType;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
public class VacationRequestDto {

    private LocalDate startDate;
    private LocalDate endDate;
    private float use;
    private String comments;
    private VacationType vacationType;

    @Builder
    public VacationRequestDto(LocalDate startDate, LocalDate endDate, float use, String comments, VacationType vacationType) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.use = use;
        this.comments = comments;
        this.vacationType = vacationType;
    }

    public void validate() {
        if (vacationType.isAnnual()) {
            if (startDate.isAfter(endDate)) throw new BusinessException(BusinessMessage.INVALID_VACATION_DATE);
        }
    }

    public MemberVacation toEntity(MemberVacationInfo memberVacationInfo) {
        return MemberVacation.builder()
                .memberVacationInfo(memberVacationInfo)
                .startDate(startDate)
                .endDate(endDate)
                .use(use)
                .comments(comments)
                .vacationType(vacationType)
                .build();
    }


}
