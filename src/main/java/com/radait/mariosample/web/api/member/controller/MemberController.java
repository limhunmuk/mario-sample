package com.radait.mariosample.web.api.member.controller;

import com.radait.mariosample.web.api.member.dto.MemberDetailDto;
import com.radait.mariosample.web.api.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/api/members")
    public ResponseEntity<String> getMembers(){

        MemberDetailDto dto = memberService.findById(1);
        System.out.println("dto.getNickNm() + \" : \" + dto.getMemberId() = " + dto.getNickNm() + " : " + dto.getMemberId());
        return ResponseEntity.ok().body(dto.getNickNm() + " : " + dto.getMemberId());
    }
}
