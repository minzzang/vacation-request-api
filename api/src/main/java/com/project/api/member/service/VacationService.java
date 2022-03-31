package com.project.api.member.service;

import com.project.api.auth.AuthMember;
import com.project.api.exception.BusinessException;
import com.project.api.exception.BusinessMessage;
import com.project.api.member.controller.dto.VacationRequestDto;
import com.project.api.member.domain.Member;
import com.project.api.member.domain.MemberVacation;
import com.project.api.member.domain.MemberVacationInfo;
import com.project.api.member.domain.MemberVacationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class VacationService {

    private final MemberVacationInfoService memberVacationInfoService;
    private final MemberVacationRepository memberVacationRepository;

    public MemberVacation findById(Long id) {
        return memberVacationRepository.findById(id)
                .orElseThrow(() -> new BusinessException(BusinessMessage.NOT_FOUND_VACATION));
    }

    public List<MemberVacation> findAllVacation(MemberVacationInfo info) {
        return memberVacationRepository.findAllByMemberVacationInfo(info);
    }

    @Transactional
    public Long requestVacation(AuthMember authMember, VacationRequestDto requestDto) {
        Member member = authMember.getMember();
        MemberVacationInfo memberVacationInfo = memberVacationInfoService.findById(member.getId());

        MemberVacation vacation = requestDto.toEntity(memberVacationInfo);
        vacation.useVacation();

        memberVacationInfoService.save(memberVacationInfo);
        MemberVacation saved = memberVacationRepository.save(vacation);
        return saved.getId();
    }

    @Transactional
    public void cancelVacation(AuthMember authMember, Long id) {
        Member member = authMember.getMember();
        MemberVacation memberVacation = memberVacationRepository.findById(id)
                .orElseThrow(() -> new BusinessException(BusinessMessage.NOT_FOUND_VACATION));

        memberVacation.isCancelPossible(member.getId());
        memberVacationRepository.deleteById(id);

        memberVacation.cancel();
        MemberVacationInfo memberVacationInfo = memberVacation.getMemberVacationInfo();

        memberVacationInfoService.save(memberVacationInfo);
    }

}
