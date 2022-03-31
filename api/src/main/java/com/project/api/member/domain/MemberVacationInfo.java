package com.project.api.member.domain;

import com.project.api.common.BaseEntity;
import com.project.api.exception.BusinessException;
import com.project.api.exception.BusinessMessage;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberVacationInfo extends BaseEntity {

    @Id
    private Long memberId;

    private float totalVacationDays;

    private float remains;

    public void useVacation(float use) {
        remains -= use;
        checkRestOfVacation();
    }

    public void checkRestOfVacation() {
        if (remains < 1) {
            throw new BusinessException(BusinessMessage.NO_VACATION_LEFT);
        }
    }

    public void cancel(float use) {
        remains += use;
    }

    public boolean isMyVacation(Long id) {
        return memberId.equals(id);
    }
}
