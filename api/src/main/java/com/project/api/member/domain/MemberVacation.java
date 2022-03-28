package com.project.api.member.domain;

import com.project.api.common.BaseEntity;
import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
public class MemberVacation extends BaseEntity {

    @Id
    @Column(unique = true, nullable = false)
    private Long id;

    @MapsId
    @OneToOne
    @JoinColumn(name = "member_id")
    private Member member;

    private int totalVacationDays;

    private int use;

}
