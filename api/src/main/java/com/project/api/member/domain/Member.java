package com.project.api.member.domain;

import com.project.api.common.BaseEntity;
import com.project.api.exception.BusinessException;
import com.project.api.exception.BusinessMessage;
import com.project.api.utils.PasswordEncoderUtil;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Member extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String email;

    private String password;

    public void checkPassword(String rawPassword) {
        if (!PasswordEncoderUtil.matches(rawPassword, password)) {
            throw new BusinessException(BusinessMessage.INVALID_LOGIN_INFO);
        }
    }
}
