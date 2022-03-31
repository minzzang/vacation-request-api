package com.project.api.auth.service;

import com.project.api.auth.jwt.JwtTokenProvider;
import com.project.api.member.domain.Member;
import com.project.api.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class JwtAuthService implements AuthService {

    private final MemberService memberService;

    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public String login(String email, String password) {
        Member member = memberService.findByEmail(email);
        member.checkPassword(password);

        return jwtTokenProvider.generateToken(email, password);
    }
}
