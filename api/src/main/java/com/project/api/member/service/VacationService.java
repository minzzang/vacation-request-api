package com.project.api.member.service;

import com.project.api.member.controller.dto.VacationRequestDto;
import com.project.api.member.domain.Member;
import com.project.api.member.domain.MemberVacation;
import com.project.api.member.domain.MemberVacationInfo;
import com.project.api.member.domain.MemberVacationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class VacationService {

    private final MemberService memberService;
    private final MemberVacationInfoService memberVacationInfoService;
    private final MemberVacationRepository memberVacationRepository;

    @Transactional
    public Long requestVacation(VacationRequestDto requestDto) {
        String email = "minzzang@gmail.com";
        Member member = memberService.findByEmail(email);

        MemberVacationInfo memberVacationInfo = memberVacationInfoService.findById(member.getId());

        MemberVacation vacation = requestDto.toEntity(memberVacationInfo);
        vacation.useVacation();

        memberVacationInfoService.save(memberVacationInfo);
        MemberVacation saved = memberVacationRepository.save(vacation);
        return saved.getId();
    }


}
