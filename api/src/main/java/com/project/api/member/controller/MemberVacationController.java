package com.project.api.member.controller;

import com.project.api.auth.AuthMember;
import com.project.api.member.controller.dto.VacationRequestDto;
import com.project.api.member.service.VacationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequiredArgsConstructor
public class MemberVacationController {

    private final VacationService vacationService;

    @PostMapping("/api/member/vacation")
    public ResponseEntity<?> requestVacation(@AuthenticationPrincipal AuthMember authMember,
                                             @RequestBody @Valid VacationRequestDto requestDto) {
        requestDto.calculateVacationDays();
        Long id = vacationService.requestVacation(authMember, requestDto);

        return ResponseEntity
                .created(URI.create("/api/member/vacation/" + id))
                .build();
    }

    @DeleteMapping("/api/member/vacation/{id}")
    public ResponseEntity<?> cancelVacation(@AuthenticationPrincipal AuthMember authMember,
                                            @PathVariable Long id) {
        vacationService.cancelVacation(authMember, id);

        return ResponseEntity.noContent().build();
    }

}

