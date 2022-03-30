package com.project.api.auth.jwt;

import com.project.api.auth.AuthMember;
import com.project.api.exception.BusinessException;
import com.project.api.exception.BusinessMessage;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.security.Key;
import java.util.Collections;
import java.util.Date;

@Component
public class JwtTokenProvider {

    private static final String AUTHORITIES_KEY = "Authorization";

    private static final String TOKEN = "Token ";

    private static final String PASSWORD = "password";

    private static final long ACCESS_TOKEN_EXPIRE_TIME = 1000 * 60 * 30;

    private static final int TOKEN_START_INDEX = 6;

    private final Key key;

    public JwtTokenProvider(@Value("${jwt.secret}") String secret) {
        byte[] keyBytes = Decoders.BASE64.decode(secret);
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    public String generateToken(String email, String password) {
        return Jwts.builder()
                .setSubject(email)
                .claim(PASSWORD, password)
                .setExpiration(new Date(System.currentTimeMillis() + ACCESS_TOKEN_EXPIRE_TIME))
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();
    }

    public String resolveToken(HttpServletRequest request) {
        String header = request.getHeader(AUTHORITIES_KEY);
        if (StringUtils.hasText(header) && header.startsWith(TOKEN)) {
            return header.substring(TOKEN_START_INDEX);
        }
        return null;
    }

    public Authentication getAuthentication(String token) {
        Claims claims = parseClaims(token);
        UserDetails user = new AuthMember(claims.getSubject(), "" , Collections.singleton(new SimpleGrantedAuthority("ROLE_USER")));

        return new UsernamePasswordAuthenticationToken(user, claims.get(PASSWORD).toString());
    }

    private Claims parseClaims(String token) {
        try {
            return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
        } catch (ExpiredJwtException e) {
            throw new BusinessException(BusinessMessage.REQUIRED_LOGIN);
        }
    }
}
