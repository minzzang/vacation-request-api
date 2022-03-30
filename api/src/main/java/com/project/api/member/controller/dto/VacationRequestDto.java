package com.project.api.member.controller.dto;

import com.project.api.exception.BusinessException;
import com.project.api.exception.BusinessMessage;
import com.project.api.member.domain.MemberVacationInfo;
import com.project.api.member.domain.MemberVacation;
import com.project.api.member.enums.VacationType;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Getter
@NoArgsConstructor
public class VacationRequestDto {

    @NotNull(message = "시작일을 입력 해주세요")
    private LocalDate startDate;
    private LocalDate endDate;
    private float use;
    private String comments;
    @NotNull(message = "휴가 타입을 입력 해주세요 (연차:ANNUAL, 반차:HALF, 반반차:HALF_HALF)")
    private VacationType vacationType;

    @Builder
    public VacationRequestDto(LocalDate startDate, LocalDate endDate, float use, String comments, VacationType vacationType) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.use = use;
        this.comments = comments;
        this.vacationType = vacationType;
    }

    public void calculateVacationDays() {
        validate();
        use = vacationType.getCalculable().calculate(this);
    }

    private void validate() {
        if (vacationType.isAnnual()) {
            if (endDate == null || startDate.isAfter(endDate)) throw new BusinessException(BusinessMessage.INVALID_VACATION_DATE);
        }
        if (endDate == null) endDate = startDate;
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
