package com.project.api.auth.service;

import com.project.api.auth.jwt.JwtTokenProvider;
import com.project.api.exception.BusinessException;
import com.project.api.exception.BusinessMessage;
import com.project.api.member.domain.Member;
import com.project.api.member.service.MemberService;
import com.project.api.utils.PasswordEncoderUtil;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class JwtAuthServiceTest {

    @InjectMocks
    private JwtAuthService jwtAuthService;

    @Mock
    private MemberService memberService;

    @Mock
    private JwtTokenProvider jwtTokenProvider;

    @Test
    @DisplayName("로그인 성공")
    public void login() {
        // given
        String email = "minzzang@gmail.com";
        String password = "1234";

        Member member = Member.builder()
                .email(email).password(PasswordEncoderUtil.encode(password))
                .build();

        String token = "token";

        given(memberService.findByEmail(email)).willReturn(member);
        given(jwtTokenProvider.generateToken(email, password)).willReturn(token);
        // when
        String result = jwtAuthService.login(email, password);
        // then
        assertThat(result).isEqualTo(token);
    }

    @Test
    @DisplayName("로그인 실패 (비밀번호 불일치)")
    public void loginFail() {
        // given
        String email = "minzzang@gmail.com";
        String password = "1234";

        Member member = Member.builder()
                .email(email).password(PasswordEncoderUtil.encode(password))
                .build();

        given(memberService.findByEmail(email)).willReturn(member);
        // when
        BusinessException businessException = assertThrows(BusinessException.class,
                () -> jwtAuthService.login(email, "123"));

        // then
        assertThat(businessException.getMessage()).isEqualTo(BusinessMessage.INVALID_LOGIN_INFO.getMessage());
    }

}