package com.project.api.member.controller;

import com.project.api.member.controller.dto.VacationRequestDto;
import com.project.api.member.service.VacationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequiredArgsConstructor
public class MemberVacationController {

    private final VacationService vacationService;

    @PostMapping("/api/member/vacation")
    public ResponseEntity<?> requestVacation(@RequestBody VacationRequestDto requestDto) {
        requestDto.validate();
        Long id = vacationService.requestVacation(requestDto);

        return ResponseEntity
                .created(URI.create("/api/member/vacation/" + id))
                .build();
    }

    @DeleteMapping("/api/member/vacation/{id}")
    public ResponseEntity<?> cancelVacation(@PathVariable Long id) {
        vacationService.cancelVacation(id);

        return ResponseEntity.noContent().build();
    }

}

