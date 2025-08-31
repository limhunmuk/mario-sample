package com.radait.mariosample.web.api.member.controller;

import com.radait.mariosample.web.api.common.entity.PageResponse;
import com.radait.mariosample.web.api.member.dto.MemberDetailDto;
import com.radait.mariosample.web.api.member.dto.MemberSearchDto;
import com.radait.mariosample.web.api.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    /**
     * 전체 회원 조회
     * @return
     */
    @GetMapping("/api/members")
    public ResponseEntity<PageResponse<MemberDetailDto>> getMembers(MemberSearchDto condition) {

        PageResponse<MemberDetailDto> pageMember = memberService.findAll(condition);
        return ResponseEntity.ok(pageMember);
    }


}
