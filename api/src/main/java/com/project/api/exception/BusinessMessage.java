package com.project.api.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
@Getter
public enum BusinessMessage {

    INVALID_VACATION_DATE(HttpStatus.BAD_REQUEST, "올바른 휴가 신청날짜를 입력해주세요"),

    NOT_FOUND_MEMBER(HttpStatus.NOT_FOUND, "멤버를 찾을 수 없습니다."),
    NOT_FOUND_VACATION_INFO(HttpStatus.NOT_FOUND, "휴가 정보를 찾을 수 없습니다."),

    CANCEL_NOT_POSSIBLE(HttpStatus.CONFLICT, "휴가 시작 이후에는 휴가를 취소할 수 없습니다"),
    NO_VACATION_LEFT(HttpStatus.CONFLICT, "휴가 신청일이 남은 휴가보다 많습니다.");

    private final HttpStatus httpStatus;
    private final String message;
}
