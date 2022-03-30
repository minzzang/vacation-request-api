package com.project.api.member.domain;

import com.project.api.common.BaseEntity;
import com.project.api.exception.BusinessException;
import com.project.api.exception.BusinessMessage;
import com.project.api.member.enums.VacationType;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class MemberVacation extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private MemberVacationInfo memberVacationInfo;

    private LocalDate startDate;

    private LocalDate endDate;

    private float use;

    private String comments;

    @Enumerated(EnumType.STRING)
    private VacationType vacationType;

    public void cancel() {
        memberVacationInfo.cancel(use);
    }

    public void useVacation() {
        memberVacationInfo.useVacation(use);
    }

    public void isCancelPossible() {
        LocalDate now = LocalDate.now();
        if (now.isEqual(startDate) || now.isAfter(startDate)) {
            throw new BusinessException(BusinessMessage.CANCEL_NOT_POSSIBLE);
        }
    }
}
