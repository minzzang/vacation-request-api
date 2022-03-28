package com.project.api.member.domain;

import com.project.api.common.BaseEntity;
import com.project.api.member.enums.VacationRequestStatus;
import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Getter
public class MemberVacationDetail extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private MemberVacation memberVacation;

    private LocalDate startDate;

    private LocalDate endDate;

    private int use;

    private String comments;

    @Enumerated(EnumType.STRING)
    private VacationRequestStatus vacationRequestStatus;
}
