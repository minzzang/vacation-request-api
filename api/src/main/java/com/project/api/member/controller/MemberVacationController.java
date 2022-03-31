package com.project.api.member.controller;

import com.project.api.auth.AuthMember;
import com.project.api.member.controller.dto.GetVacationDetailResponseDto;
import com.project.api.member.controller.dto.GetVacationResponseDto;
import com.project.api.member.controller.dto.VacationRequestDto;
import com.project.api.member.domain.MemberVacation;
import com.project.api.member.domain.MemberVacationInfo;
import com.project.api.member.service.MemberVacationInfoService;
import com.project.api.member.service.VacationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

import static java.util.stream.Collectors.toList;

@RestController
@RequiredArgsConstructor
public class MemberVacationController {

    private final VacationService vacationService;
    private final MemberVacationInfoService memberVacationInfoService;

    @GetMapping("/api/member/vacation/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {
        MemberVacation memberVacation = vacationService.findById(id);

        return ResponseEntity.ok(new GetVacationDetailResponseDto(memberVacation));
    }

    @GetMapping("/api/member/vacation")
    public ResponseEntity<?> findAll(@AuthenticationPrincipal AuthMember authMember) {
        MemberVacationInfo memberVacationInfo = memberVacationInfoService.find(authMember);
        List<GetVacationDetailResponseDto> memberVacations = vacationService.findAllVacation(memberVacationInfo).stream()
                .map(GetVacationDetailResponseDto::new).collect(toList());

        return ResponseEntity.ok(new GetVacationResponseDto(memberVacations, memberVacationInfo));
    }

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

