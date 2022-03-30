package com.project.api.member.service;

import com.project.api.exception.BusinessException;
import com.project.api.exception.BusinessMessage;
import com.project.api.member.domain.Member;
import com.project.api.member.domain.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {

    private final MemberRepository memberRepository;

    public Member findByEmail(String email) {
        return memberRepository.findByEmail(email)
                .orElseThrow(() -> new BusinessException(BusinessMessage.NOT_FOUND_MEMBER));
    }

}
