package com.project.api.member.service;

import com.project.api.exception.BusinessException;
import com.project.api.exception.BusinessMessage;
import com.project.api.member.domain.MemberVacationInfo;
import com.project.api.member.domain.MemberVacationInfoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberVacationInfoService {

    private final MemberVacationInfoRepository memberVacationInfoRepository;

    public MemberVacationInfo findById(Long id) {
        return memberVacationInfoRepository.findById(id)
                .orElseThrow(() -> new BusinessException(BusinessMessage.NOT_FOUND_VACATION_INFO));
    }

    public void save(MemberVacationInfo memberVacationInfo) {
        memberVacationInfoRepository.save(memberVacationInfo);
    }
}
