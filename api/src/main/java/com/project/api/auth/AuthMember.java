package com.project.api.auth;

import com.project.api.member.domain.Member;
import lombok.Getter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collections;
import java.util.Set;

@Getter
public class AuthMember extends User {

    private Member member;

    public AuthMember(Member member) {
        super(member.getEmail(), member.getPassword(), Collections.singleton(new SimpleGrantedAuthority("ROLE_USER")));
        this.member = member;
    }

    public AuthMember(String username, String password, Set<SimpleGrantedAuthority> role) {
        super(username, password, role);
    }
}
