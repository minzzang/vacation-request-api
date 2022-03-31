package com.project.api.member.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface MemberVacationRepository extends JpaRepository<MemberVacation, Long> {

    List<MemberVacation> findAllByMemberVacationInfo(MemberVacationInfo info);
}
